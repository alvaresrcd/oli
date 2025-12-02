package controlador;

import modelo.Reservacion;
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
    // Variable local para definir el nombre del archivo de persistencia.
    private final String NOMBRE_ARCHIVO = "reservaciones.txt";

    // --- CONSTRUCTOR ---
    public ControladorReservaciones() {
        this.reservaciones = new ArrayList<>();
        // Al iniciar, se intenta cargar las reservaciones desde el archivo de texto.
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
        try (BufferedReader br = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
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
            // Si el archivo no existe, no es un error, simplemente no hay datos que cargar.
            // Se puede crear el archivo vacío para futuras operaciones.
            try {
                new File(NOMBRE_ARCHIVO).createNewFile();
            } catch (IOException ioException) {
                System.err.println("Error al crear el archivo: " + ioException.getMessage());
            }
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
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO, false))) {
            for (Reservacion r : reservaciones) {
                bw.write(r.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar las reservaciones en el archivo: " + e.getMessage());
        }
    }
}
