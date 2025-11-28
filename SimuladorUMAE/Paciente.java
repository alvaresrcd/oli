/**
 * Descripcion: Simulador de atencion de pacientes de una Unidad de Medicina de Alta Especialidad.
 * Analisis: Este programa simula la gestion de pacientes en una clinica, permitiendo
 * registrar, mostrar, buscar y atender pacientes. Los datos se guardan en un archivo de texto.
 * La interfaz grafica esta desarrollada con JFrame.
 * Autor: Jules
 * Fecha: 28 de noviembre de 2024
 */

package SimuladorUMAE;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Paciente {
    private int numeroAtencion;
    private String nombre;
    private int edad;
    private String areaMedica;
    private String tipoAtencion;
    private LocalDateTime fechaAtencion; // Nuevo campo

    // Constructor para nuevos pacientes (sin fecha de atencion)
    public Paciente(int numeroAtencion, String nombre, int edad, String areaMedica, String tipoAtencion) {
        this.numeroAtencion = numeroAtencion;
        this.nombre = nombre;
        this.edad = edad;
        this.areaMedica = areaMedica;
        this.tipoAtencion = tipoAtencion;
        this.fechaAtencion = null; // explicitamente nulo
    }

    // Constructor para pacientes atendidos (con fecha de atencion)
    public Paciente(int numeroAtencion, String nombre, int edad, String areaMedica, String tipoAtencion, LocalDateTime fechaAtencion) {
        this(numeroAtencion, nombre, edad, areaMedica, tipoAtencion);
        this.fechaAtencion = fechaAtencion;
    }

    // Getters
    public int getNumeroAtencion() { return numeroAtencion; }
    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    public String getAreaMedica() { return areaMedica; }
    public String getTipoAtencion() { return tipoAtencion; }
    public LocalDateTime getFechaAtencion() { return fechaAtencion; }

    // Setter para la fecha de atencion
    public void setFechaAtencion(LocalDateTime fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    // Formato para el archivo "Por_atender.txt" (sin fecha)
    public String toPorAtenderFileString() {
        return numeroAtencion + "," + nombre + "," + edad + "," + areaMedica + "," + tipoAtencion;
    }

    // Formato para el archivo "pacientes.txt" (con fecha)
    public String toAtendidosFileString() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return numeroAtencion + "," + nombre + "," + edad + "," + areaMedica + "," + tipoAtencion + "," + fechaAtencion.format(formatter);
    }

    // Metodo estatico para crear un Paciente desde una linea del archivo (detecta el formato)
    public static Paciente fromFileString(String line) {
        String[] parts = line.split(",");
        try {
            int numeroAtencion = Integer.parseInt(parts[0]);
            String nombre = parts[1];
            int edad = Integer.parseInt(parts[2]);
            String areaMedica = parts[3];
            String tipoAtencion = parts[4];

            if (parts.length == 5) { // Formato "Por atender"
                return new Paciente(numeroAtencion, nombre, edad, areaMedica, tipoAtencion);
            } else if (parts.length == 6) { // Formato "Atendidos"
                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                LocalDateTime fecha = LocalDateTime.parse(parts[5], formatter);
                return new Paciente(numeroAtencion, nombre, edad, areaMedica, tipoAtencion, fecha);
            }
        } catch (NumberFormatException | DateTimeParseException e) {
            System.err.println("Error al parsear la linea del archivo: " + line);
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("No. Atencion: ").append(numeroAtencion).append("\n");
        sb.append("Nombre: ").append(nombre).append("\n");
        sb.append("Edad: ").append(edad).append("\n");
        sb.append("Area Medica: ").append(areaMedica).append("\n");
        sb.append("Tipo de Atencion: ").append(tipoAtencion).append("\n");
        if (fechaAtencion != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy, HH:mm");
            sb.append("Fecha de Atencion: ").append(fechaAtencion.format(formatter)).append("\n");
        }
        sb.append("-------------------------------------\n");
        return sb.toString();
    }
}
