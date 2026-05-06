# SoportePro - Taller de Soporte Técnico

Este proyecto es un sistema de gestión para un taller de soporte técnico, conectado a una base de datos MySQL.

## Requisitos

- Node.js instalado.
- MySQL Server y MySQL Workbench.

## Configuración de la Base de Datos

1. Abre MySQL Workbench.
2. Abre el archivo `database.sql` incluido en la carpeta raíz del proyecto.
3. Ejecuta el script completo para crear la base de datos `soportepro_db`, las tablas y cargar el inventario inicial.

## Configuración del Servidor

1. En la carpeta `SoportePro`, instala las dependencias:
   ```bash
   npm install
   ```
2. Renombra el archivo `.env` (si es necesario) y asegúrate de poner tus credenciales de MySQL:
   ```env
   DB_HOST=localhost
   DB_USER=tu_usuario
   DB_PASSWORD=tu_contraseña
   DB_NAME=soportepro_db
   PORT=3000
   ```

## Ejecución

Para iniciar el servidor:
```bash
node server.js
```

Luego, abre tu navegador en `http://localhost:3000` para ver la aplicación.

## Estructura del Proyecto

- `server.js`: Servidor Node.js con Express y conexión a MySQL.
- `public/index.html`: Interfaz de usuario (Frontend) refactorizada para usar la API.
- `database.sql`: Script de creación y carga de la base de datos.
- `.env`: Variables de entorno para configuración sensible.
