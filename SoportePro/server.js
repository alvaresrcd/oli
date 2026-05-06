const express = require('express');
const cors = require('cors');
const pool = require('./db');
require('dotenv').config();

const app = express();
app.use(cors());
app.use(express.json());

// --- ROUTES ---

// 1. Get Dashboard Metrics
app.get('/api/metrics', async (req, res) => {
  try {
    const [total] = await pool.query('SELECT COUNT(*) as count FROM ordenes_servicio');
    const [pending] = await pool.query('SELECT COUNT(*) as count FROM ordenes_servicio WHERE estatus = "Pendiente"');
    const [inprogress] = await pool.query('SELECT COUNT(*) as count FROM ordenes_servicio WHERE estatus = "En Proceso"');
    const [done] = await pool.query('SELECT COUNT(*) as count FROM ordenes_servicio WHERE estatus = "Terminado"');
    const [revenue] = await pool.query('SELECT SUM(total_reparacion) as total FROM ordenes_servicio WHERE estatus = "Terminado"');

    res.json({
      total: total[0].count,
      pending: pending[0].count,
      inprogress: inprogress[0].count,
      done: done[0].count,
      revenue: revenue[0].total || 0
    });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// 2. Get Recent Orders
app.get('/api/recent-orders', async (req, res) => {
  try {
    const [rows] = await pool.query(`
      SELECT o.id_orden, c.nombre_completo as cliente, o.estatus, o.fecha_entrada
      FROM ordenes_servicio o
      JOIN equipos e ON o.id_equipo = e.id_equipo
      JOIN clientes c ON e.id_cliente = c.id_cliente
      ORDER BY o.fecha_entrada DESC
      LIMIT 5
    `);
    res.json(rows);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// 3. Register New Order (Complete Workflow)
app.post('/api/ordenes', async (req, res) => {
  const connection = await pool.getConnection();
  try {
    await connection.beginTransaction();

    const { cliente, equipo, orden } = req.body;

    // a. Insert or find client
    const [clientResult] = await connection.query(
      'INSERT INTO clientes (nombre_completo, telefono, email, tipo_cliente) VALUES (?, ?, ?, ?)',
      [cliente.nombre, cliente.telefono, cliente.email, cliente.tipo]
    );
    const id_cliente = clientResult.insertId;

    // b. Insert equipment
    const [equipoResult] = await connection.query(
      'INSERT INTO equipos (id_cliente, tipo_equipo, marca, modelo, numero_serie, color, accesorios) VALUES (?, ?, ?, ?, ?, ?, ?)',
      [id_cliente, equipo.tipo, equipo.marca, equipo.modelo, equipo.serie, equipo.color, equipo.accesorios]
    );
    const id_equipo = equipoResult.insertId;

    // c. Insert order
    const [ordenResult] = await connection.query(
      'INSERT INTO ordenes_servicio (id_equipo, descripcion_falla, prioridad, costo_diagnostico, observaciones_adicionales) VALUES (?, ?, ?, ?, ?)',
      [id_equipo, orden.falla, orden.prioridad, orden.costo, orden.observaciones]
    );

    await connection.commit();
    res.status(201).json({ id_orden: ordenResult.insertId, message: 'Orden registrada con éxito' });
  } catch (error) {
    await connection.rollback();
    res.status(500).json({ error: error.message });
  } finally {
    connection.release();
  }
});

// 4. Get Inventory
app.get('/api/inventario', async (req, res) => {
  try {
    const [rows] = await pool.query('SELECT * FROM inventario');
    res.json(rows);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Servidor SoportePro corriendo en http://localhost:${PORT}`);
});
