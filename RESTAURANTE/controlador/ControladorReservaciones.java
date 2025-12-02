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
    private List<Reservacion> reservaciones;
    private final String RUTA_ARCHIVO;

    // --- CONSTRUCTOR ---
    public ControladorReservaciones() {
        this.reservaciones = new ArrayList<>();

        String userHome = System.getProperty("user.home");
        String rutaDirectorioDatos = userHome + File.separator + ".RestauranteApp";
        File directorioDatos = new File(rutaDirectorioDatos);

        if (!directorioDatos.exists()) {
            directorioDatos.mkdirs();
        }

        this.RUTA_ARCHIVO = rutaDirectorioDatos + File.separator + "reservaciones.txt";

        File archivo = new File(this.RUTA_ARCHIVO);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                 System.err.println("Error crítico al crear el archivo de datos: " + e.getMessage());
            }
        }

        cargarReservaciones();
    }

    // --- MÉTODOS CRUD ---
    public void agregarReservacion(Reservacion reservacion) {
        reservaciones.add(reservacion);
        guardarReservaciones();
    }

    public Reservacion buscarReservacion(String celular) {
        for (Reservacion r : reservaciones) {
            if (r.getCelular().equals(celular)) {
                return r;
            }
        }
        return null;
    }

    public boolean modificarReservacion(String celular, String nuevoDia, String nuevaHora) {
        Reservacion aModificar = buscarReservacion(celular);
        if (aModificar != null) {
            aModificar.setDia(nuevoDia);
            aModificar.setHora(nuevaHora);
            guardarReservaciones();
            return true;
        }
        return false;
    }

    public boolean cancelarReservacion(String celular) {
        Reservacion aEliminar = buscarReservacion(celular);
        if (aEliminar != null) {
            reservaciones.remove(aEliminar);
            guardarReservaciones();
            return true;
        }
        return false;
    }

    public List<Reservacion> getReservaciones() {
        return new ArrayList<>(reservaciones);
    }

    // --- MÉTODOS DE PERSISTENCIA DE ARCHIVOS ---
    private void cargarReservaciones() {
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 4) {
                    Reservacion r = new Reservacion(datos[0], datos[1], datos[2], datos[3]);
                    reservaciones.add(r);
                }
            }
        } catch (FileNotFoundException e) {
             System.err.println("No se pudo encontrar el archivo de datos: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de reservaciones: " + e.getMessage());
        }
    }

    private void guardarReservaciones() {
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
