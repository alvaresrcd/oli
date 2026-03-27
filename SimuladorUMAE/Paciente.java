/**
 * Descripcion: Simulador de atencion de pacientes de una Unidad de Medicina de Alta Especialidad.
 * Analisis: Este programa simula la gestion de pacientes en una clinica, permitiendo
 * registrar, mostrar, buscar y atender pacientes. Los datos se guardan en un archivo de texto.
 * La interfaz grafica esta desarrollada con JFrame.
 * Autor: Jules
 * Fecha: 27 de noviembre de 2024
 */

package SimuladorUMAE;

public class Paciente {
    private int numeroAtencion;
    private String nombre;
    private int edad;
    private String areaMedica;
    private String tipoAtencion;

    public Paciente(int numeroAtencion, String nombre, int edad, String areaMedica, String tipoAtencion) {
        this.numeroAtencion = numeroAtencion;
        this.nombre = nombre;
        this.edad = edad;
        this.areaMedica = areaMedica;
        this.tipoAtencion = tipoAtencion;
    }

    // Getters
    public int getNumeroAtencion() {
        return numeroAtencion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getAreaMedica() {
        return areaMedica;
    }

    public String getTipoAtencion() {
        return tipoAtencion;
    }

    // Metodo para convertir a formato de texto para el archivo
    public String toFileString() {
        return numeroAtencion + "," + nombre + "," + edad + "," + areaMedica + "," + tipoAtencion;
    }

    // Metodo estatico para crear un Paciente desde una linea del archivo
    public static Paciente fromFileString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 5) {
            try {
                int numeroAtencion = Integer.parseInt(parts[0]);
                String nombre = parts[1];
                int edad = Integer.parseInt(parts[2]);
                String areaMedica = parts[3];
                String tipoAtencion = parts[4];
                return new Paciente(numeroAtencion, nombre, edad, areaMedica, tipoAtencion);
            } catch (NumberFormatException e) {
                System.err.println("Error al parsear la linea del archivo: " + line);
                return null;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "No. Atencion: " + numeroAtencion + "\n" +
               "Nombre: " + nombre + "\n" +
               "Edad: " + edad + "\n" +
               "Area Medica: " + areaMedica + "\n" +
               "Tipo de Atencion: " + tipoAtencion + "\n" +
               "-------------------------------------\n";
    }
}
