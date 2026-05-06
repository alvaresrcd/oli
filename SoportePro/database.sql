CREATE DATABASE IF NOT EXISTS soportepro_db;
USE soportepro_db;

-- Tabla de Clientes
CREATE TABLE IF NOT EXISTS clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    tel VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(100),
    tipo ENUM('Regular', 'Frecuente') DEFAULT 'Regular',
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de Inventario (Refacciones)
CREATE TABLE IF NOT EXISTS inventario (
    id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    sku VARCHAR(50) NOT NULL UNIQUE,
    price DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    maxStock INT NOT NULL DEFAULT 0,
    category VARCHAR(50)
);

-- Tabla de Órdenes de Servicio
CREATE TABLE IF NOT EXISTS ordenes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    cliente_id INT NOT NULL,
    equipo VARCHAR(100) NOT NULL,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    serie VARCHAR(50),
    falla TEXT NOT NULL,
    estatus ENUM('Pendiente', 'En Proceso', 'Terminado') DEFAULT 'Pendiente',
    prioridad ENUM('Normal', 'Alta', 'Urgente') DEFAULT 'Normal',
    costo_base DECIMAL(10, 2) DEFAULT 150.00,
    diagnostico TEXT,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);

-- Tabla Intermedia para Componentes usados en una Orden
CREATE TABLE IF NOT EXISTS orden_componentes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    orden_id INT NOT NULL,
    inventario_id VARCHAR(10) NOT NULL,
    precio_al_momento DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (orden_id) REFERENCES ordenes(id),
    FOREIGN KEY (inventario_id) REFERENCES inventario(id)
);

-- Insertar Datos Iniciales de Inventario
INSERT INTO inventario (id, name, sku, price, stock, maxStock, category) VALUES
('INV001', 'Pantalla LCD 15.6" FHD', 'SCR-156-FHD', 850.00, 8, 20, 'Pantallas'),
('INV002', 'Teclado Laptop Universal', 'KEY-LAP-UNI', 320.00, 3, 15, 'Teclados'),
('INV003', 'Batería 4-Celdas 45Wh', 'BAT-45WH-4C', 680.00, 5, 12, 'Baterías'),
('INV004', 'RAM DDR4 8GB 3200MHz', 'RAM-8G-DDR4', 420.00, 12, 25, 'Memoria'),
('INV005', 'SSD NVMe 256GB', 'SSD-256-NVM', 750.00, 7, 20, 'Almacenamiento'),
('INV006', 'Ventilador CPU 70mm', 'FAN-CPU-70', 180.00, 2, 10, 'Refrigeración'),
('INV007', 'Cable SATA 50cm', 'CBL-SATA-50', 45.00, 20, 30, 'Cables'),
('INV008', 'Pasta Térmica Premium', 'THM-PAST-5G', 65.00, 1, 15, 'Consumibles'),
('INV009', 'Cargador Universal 65W', 'CHG-65W-UNI', 390.00, 6, 10, 'Cargadores'),
('INV010', 'Tarjeta WiFi AC 1200', 'NET-WIFI-AC', 290.00, 9, 15, 'Redes')
ON DUPLICATE KEY UPDATE name=VALUES(name), price=VALUES(price), stock=VALUES(stock), maxStock=VALUES(maxStock), category=VALUES(category);
