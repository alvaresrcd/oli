/**
 * Descripción: Clase que representa un CD con sus atributos.
 * Análisis: Esta clase encapsula los datos de un CD, como nombre, artista,
 * año, precio y existencias. Proporciona métodos para obtener y establecer
 * estos valores.
 * Autor: Jules
 * Fecha: 13/11/2025
 */
public class CD {
    private String nombre;
    private String artista;
    private int anio;
    private double precio;
    private int existencia;

    // Métodos obtener (getters)
    public String getNombre() {
        return nombre;
    }

    public String getArtista() {
        return artista;
    }

    public int getAnio() {
        return anio;
    }

    public double getPrecio() {
        return precio;
    }

    public int getExistencia() {
        return existencia;
    }

    // Métodos establecer (setters)
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }
}
