require('dotenv').config();
const express = require('express');
const mysql = require('mysql2/promise');
const cors = require('cors');

const app = express();
const port = process.env.PORT || 3000;

// Middleware
app.use(cors());
app.use(express.json());
app.use(express.static('public'));

// Database connection pool
const pool = mysql.createPool({
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_NAME,
    waitForConnections: true,
    connectionLimit: 10,
    queueLimit: 0
});

// Test connection
pool.getConnection()
    .then(conn => {
        console.log('Connected to MySQL database');
        conn.release();
    })
    .catch(err => {
        console.error('Error connecting to database:', err.message);
        console.log('Ensure MySQL is running and credentials in .env are correct.');
    });

// --- API ROUTES ---

// GET Inventory
app.get('/api/inventory', async (req, res) => {
    try {
        const [rows] = await pool.query('SELECT * FROM inventario');
        res.json(rows);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

// GET Orders (with client info and components)
app.get('/api/orders', async (req, res) => {
    try {
        // Fetch all orders with client info
        const [orders] = await pool.query(`
            SELECT o.*, c.nombre as cliente_nombre, c.tel as cliente_tel, c.email as cliente_email, c.tipo as cliente_tipo
            FROM ordenes o
            JOIN clientes c ON o.cliente_id = c.id
            ORDER BY o.fecha DESC
        `);

        // Fetch components for all orders
        const [components] = await pool.query(`
            SELECT oc.*, i.name as component_name
            FROM orden_componentes oc
            JOIN inventario i ON oc.inventario_id = i.id
        `);

        // Group components by order_id
        const ordersWithComponents = orders.map(order => {
            return {
                ...order,
                componentes: components
                    .filter(comp => comp.orden_id === order.id)
                    .map(comp => ({
                        invId: comp.inventario_id,
                        name: comp.component_name,
                        price: parseFloat(comp.precio_al_momento)
                    }))
            };
        });

        res.json(ordersWithComponents);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

// POST Create new Order (and client if new)
app.post('/api/orders', async (req, res) => {
    const conn = await pool.getConnection();
    try {
        await conn.beginTransaction();
        const { cliente, orden } = req.body;

        // 1. Handle Client
        let [clientRows] = await conn.query('SELECT id FROM clientes WHERE tel = ?', [cliente.tel]);
        let clientId;
        if (clientRows.length > 0) {
            clientId = clientRows[0].id;
            // Optionally update existing client info
            await conn.query('UPDATE clientes SET nombre = ?, email = ?, tipo = ? WHERE id = ?',
                [cliente.nombre, cliente.email, cliente.tipo, clientId]);
        } else {
            const [result] = await conn.query(
                'INSERT INTO clientes (nombre, tel, email, tipo) VALUES (?, ?, ?, ?)',
                [cliente.nombre, cliente.tel, cliente.email, cliente.tipo]
            );
            clientId = result.insertId;
        }

        // 2. Insert Order
        const today = new Date().toISOString().split('T')[0];
        const [orderResult] = await conn.query(
            'INSERT INTO ordenes (fecha, cliente_id, equipo, marca, modelo, serie, falla, estatus, prioridad, costo_base, diagnostico) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)',
            [today, clientId, orden.equipo, orden.marca, orden.modelo, orden.serie, orden.falla, 'Pendiente', orden.prioridad, orden.costoBase || 150, '']
        );
        const orderId = orderResult.insertId;

        await conn.commit();
        res.status(201).json({ message: 'Order created', id: orderId });
    } catch (err) {
        await conn.rollback();
        res.status(500).json({ error: err.message });
    } finally {
        conn.release();
    }
});

// PUT Update Order (status, diagnosis, components)
app.get('/api/orders/:id', async (req, res) => {
    try {
        const [rows] = await pool.query('SELECT * FROM ordenes WHERE id = ?', [req.params.id]);
        if (rows.length === 0) return res.status(404).json({ message: 'Order not found' });
        res.json(rows[0]);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

app.put('/api/orders/:id', async (req, res) => {
    const conn = await pool.getConnection();
    try {
        await conn.beginTransaction();
        const { estatus, diagnostico, componentes } = req.body;
        const orderId = req.params.id;

        // 1. Update Order main info
        await conn.query(
            'UPDATE ordenes SET estatus = ?, diagnostico = ? WHERE id = ?',
            [estatus, diagnostico, orderId]
        );

        // 2. Update Components
        // First, get currently registered components to "refund" stock
        const [oldComponents] = await conn.query('SELECT inventario_id FROM orden_componentes WHERE orden_id = ?', [orderId]);
        for (const comp of oldComponents) {
            await conn.query('UPDATE inventario SET stock = stock + 1 WHERE id = ?', [comp.inventario_id]);
        }

        // Remove old associations
        await conn.query('DELETE FROM orden_componentes WHERE orden_id = ?', [orderId]);

        // Add new associations and decrease stock
        for (const comp of componentes) {
            await conn.query(
                'INSERT INTO orden_componentes (orden_id, inventario_id, precio_al_momento) VALUES (?, ?, ?)',
                [orderId, comp.invId, comp.price]
            );
            await conn.query('UPDATE inventario SET stock = stock - 1 WHERE id = ?', [comp.invId]);
        }

        await conn.commit();
        res.json({ message: 'Order updated successfully' });
    } catch (err) {
        await conn.rollback();
        res.status(500).json({ error: err.message });
    } finally {
        conn.release();
    }
});

app.listen(port, () => {
    console.log(`Server running at http://localhost:${port}`);
});
