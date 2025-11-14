/**
 * Descripcion del problema: Realizar un control de estaturas del jardín de niños "Niños Felices",
 * el cual cuenta con 98 niños. Se requiere almacenar nombre, edad (3 a 6 años) y estatura en cm.
 * Analisis: Se creará una clase Nino para representar los datos de cada niño.
 * Esta clase encapsulará los atributos nombre, edad y estatura, y proporcionará
 * métodos para acceder y modificarlos (getters y setters).
 * Autor: Jules
 * Fecha: 14/11/2025
 */
public class Nino {
    private String nombre;
    private int edad;
    private double estatura;

    // Constructor
    public Nino(String nombre, int edad, double estatura) {
        this.nombre = nombre;
        this.edad = edad;
        this.estatura = estatura;
    }

    // Métodos obtener (getters)
    public String obtenerNombre() {
        return nombre;
    }

    public int obtenerEdad() {
        return edad;
    }

    public double obtenerEstatura() {
        return estatura;
    }

    // Métodos establecer (setters)
    public void establecerNombre(String nombre) {
        this.nombre = nombre;
    }

    public void establecerEdad(int edad) {
        this.edad = edad;
    }

    public void establecerEstatura(double estatura) {
        this.estatura = estatura;
    }
}
