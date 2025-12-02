package RESTAURANTE;

/**
 * @author Jules
 * @date 2024-12-02
 */
public class Reservacion {

    private String nombre;
    private String celular;
    private String dia;
    private String hora;

    public Reservacion(String nombre, String celular, String dia, String hora) {
        this.nombre = nombre;
        this.celular = celular;
        this.dia = dia;
        this.hora = hora;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return nombre + "," + celular + "," + dia + "," + hora;
    }
}
