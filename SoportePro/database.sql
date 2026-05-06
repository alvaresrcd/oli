CREATE DATABASE IF NOT EXISTS taller_soporte;
USE taller_soporte;

-- 1. Tabla de Usuarios (Para el login y perfil del sidebar)
CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    rol VARCHAR(50) DEFAULT 'Gerente de Taller',
    email VARCHAR(100) UNIQUE,
    password VARCHAR(255) NOT NULL,
    iniciales VARCHAR(5) -- Ej: 'Ad' para el avatar
);

-- 2. Tabla de Clientes
CREATE TABLE clientes (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre_completo VARCHAR(150) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    email VARCHAR(100),
    tipo_cliente ENUM('Regular', 'Frecuente') DEFAULT 'Regular',
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Tabla de Equipos
CREATE TABLE equipos (
    id_equipo INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT,
    tipo_equipo VARCHAR(50), -- Laptop, PC, etc.
    marca VARCHAR(50),
    modelo VARCHAR(50),
    numero_serie VARCHAR(100),
    color VARCHAR(30),
    accesorios TEXT,
    FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente) ON DELETE CASCADE
);

-- 4. Tabla de Inventario (Refacciones)
CREATE TABLE inventario (
    id_item INT AUTO_INCREMENT PRIMARY KEY,
    nombre_item VARCHAR(100) NOT NULL,
    sku VARCHAR(50) UNIQUE,
    stock_actual INT DEFAULT 0,
    stock_minimo INT DEFAULT 5, -- Para detectar "Stock Crítico"
    precio_unitario DECIMAL(10, 2) NOT NULL
);

-- 5. Tabla de Órdenes de Servicio
CREATE TABLE ordenes_servicio (
    id_orden INT AUTO_INCREMENT PRIMARY KEY,
    id_equipo INT,
    id_usuario INT, -- Técnico o Admin que recibe
    descripcion_falla TEXT NOT NULL,
    prioridad ENUM('Normal', 'Alta', 'Urgente') DEFAULT 'Normal',
    estatus ENUM('Pendiente', 'En Proceso', 'Terminado') DEFAULT 'Pendiente',
    costo_diagnostico DECIMAL(10, 2) DEFAULT 0.00,
    observaciones_adicionales TEXT,
    total_reparacion DECIMAL(10, 2) DEFAULT 0.00,
    fecha_entrada TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_entrega DATETIME NULL,
    FOREIGN KEY (id_equipo) REFERENCES equipos(id_equipo),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

-- 6. Detalle de Reparación (Piezas usadas en cada orden)
CREATE TABLE detalle_reparacion_insumos (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_orden INT,
    id_item INT,
    cantidad INT DEFAULT 1,
    precio_al_momento DECIMAL(10, 2), -- Precio de la pieza cuando se usó
    FOREIGN KEY (id_orden) REFERENCES ordenes_servicio(id_orden) ON DELETE CASCADE,
    FOREIGN KEY (id_item) REFERENCES inventario(id_item)
);
