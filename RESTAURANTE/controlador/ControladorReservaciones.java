package RESTAURANTE.controlador;

import RESTAURANTE.modelo.Reservacion;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jules
 * @date 2024-12-02
 *
 *       Descripción:
 *       Clase controladora que gestiona la lógica de negocio para las reservaciones.
 *       Se encarga de interactuar con el archivo de texto para la persistencia de datos
 *       y maneja la lista de reservaciones en memoria.
 *       Utiliza la técnica de programación estructurada para organizar la lógica en métodos.
 */
public class ControladorReservaciones {

    // --- ATRIBUTOS ---
    // Estructura de datos: ArrayList para almacenar las reservaciones en memoria.
    private List<Reservacion> reservaciones;
    // Ruta completa al archivo de persistencia. Se inicializa en el constructor.
    private final String RUTA_ARCHIVO;

    // --- CONSTRUCTOR ---
    public ControladorReservaciones() {
        this.reservaciones = new ArrayList<>();

        // --- Lógica de ruta de archivo robusta ---
        // Se construye una ruta en el directorio home del usuario para evitar problemas de ruta relativa.
        String userHome = System.getProperty("user.home");
        // File.separator se asegura de usar '/' o '\' según el sistema operativo.
        String rutaDirectorioDatos = userHome + File.separator + ".RestauranteApp";
        File directorioDatos = new File(rutaDirectorioDatos);

        // Si el directorio de datos (ej: C:\Users\TuUsuario\.RestauranteApp) no existe, se crea.
        if (!directorioDatos.exists()) {
            directorioDatos.mkdirs(); // mkdirs() crea también los directorios padres si es necesario.
        }

        // Se establece la ruta final del archivo.
        this.RUTA_ARCHIVO = rutaDirectorioDatos + File.separator + "reservaciones.txt";

        // Se asegura de que el archivo exista. Si no, lo crea.
        File archivo = new File(this.RUTA_ARCHIVO);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                 System.err.println("Error crítico al crear el archivo de datos: " + e.getMessage());
            }
        }

        // Al iniciar, se cargan las reservaciones desde el archivo de texto.
        cargarReservaciones();
    }

    // --- MÉTODOS CRUD ---

    /**
     * Procedimiento (Tipo 2: No retorna valor, con parámetros)
     * Agrega una nueva reservación a la lista y guarda los cambios en el archivo.
     *
     * @param reservacion La nueva reservación a agregar.
     */
    public void agregarReservacion(Reservacion reservacion) {
        reservaciones.add(reservacion);
        guardarReservaciones();
    }

    /**
     * Función (Tipo 3: Retorna valor, con parámetros)
     * Busca una reservación en la lista por el número de celular.
     *
     * @param celular El número de celular para buscar.
     * @return El objeto Reservacion si se encuentra, o null si no existe.
     */
    public Reservacion buscarReservacion(String celular) {
        // Estructura de repetición: for-each para iterar sobre la lista.
        for (Reservacion r : reservaciones) {
            // Estructura de decisión: if para comparar el celular.
            // Operador relacional (equals) para comparar cadenas.
            if (r.getCelular().equals(celular)) {
                return r;
            }
        }
        return null; // Retorna null si no se encuentra.
    }

    /**
     * Función (Tipo 3: Retorna valor, con parámetros)
     * Modifica los datos de una reservación existente.
     *
     * @param celular      El celular de la reservación a modificar.
     * @param nuevoDia     El nuevo día para la reservación.
     * @param nuevaHora    La nueva hora para la reservación.
     * @return true si la modificación fue exitosa, false en caso contrario.
     */
    public boolean modificarReservacion(String celular, String nuevoDia, String nuevaHora) {
        Reservacion aModificar = buscarReservacion(celular);
        // Operador lógico (!=) para verificar si la reservación fue encontrada.
        if (aModificar != null) {
            aModificar.setDia(nuevoDia);
            aModificar.setHora(nuevaHora);
            guardarReservaciones();
            return true;
        }
        return false;
    }

    /**
     * Función (Tipo 3: Retorna valor, con parámetros)
     * Elimina una reservación de la lista usando el número de celular.
     *
     * @param celular El celular de la reservación a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public boolean cancelarReservacion(String celular) {
        Reservacion aEliminar = buscarReservacion(celular);
        if (aEliminar != null) {
            reservaciones.remove(aEliminar);
            guardarReservaciones();
            return true;
        }
        return false;
    }

    /**
     * Función (Tipo 1: Retorna valor, sin parámetros)
     * Proporciona una copia de la lista de reservaciones.
     *
     * @return Una lista de todas las reservaciones.
     */
    public List<Reservacion> getReservaciones() {
        return new ArrayList<>(reservaciones);
    }

    // --- MÉTODOS DE PERSISTENCIA DE ARCHIVOS ---

    /**
     * Procedimiento (Tipo 4: No retorna valor, sin parámetros)
     * Lee las reservaciones desde el archivo de texto y las carga en la lista.
     * Utiliza manejo de excepciones para controlar errores de lectura.
     */
    private void cargarReservaciones() {
        // Estructura de manejo de excepciones: try-catch-finally.
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            // Estructura de repetición: while para leer el archivo línea por línea.
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                // Operador relacional (==) para verificar la cantidad de datos.
                if (datos.length == 4) {
                    Reservacion r = new Reservacion(datos[0], datos[1], datos[2], datos[3]);
                    reservaciones.add(r);
                }
            }
        } catch (FileNotFoundException e) {
            // Este bloque ahora es menos probable que se ejecute porque el constructor ya crea el archivo.
            // Sin embargo, se mantiene como una salvaguarda.
             System.err.println("No se pudo encontrar el archivo de datos: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de reservaciones: " + e.getMessage());
        }
    }

    /**
     * Procedimiento (Tipo 4: No retorna valor, sin parámetros)
     * Escribe la lista completa de reservaciones en el archivo de texto,
     * sobrescribiendo su contenido.
     */
    private void guardarReservaciones() {
        // Se utiliza try-with-resources para asegurar que el FileWriter se cierre automáticamente.
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_ARCHIVO, false))) {
            for (Reservacion r : reservaciones) {
                bw.write(r.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar las reservaciones en el archivo: " + e.getMessage());
        }
    }
}
