package modelo;

/**
 * @author Jules
 * @date 2024-12-02
 *
 *       Descripción:
 *       La clase Reservacion representa una entidad de reservación en el sistema del restaurante.
 *       Almacena la información del cliente y la fecha y hora de la reservación.
 *       Esta clase utiliza la técnica de programación orientada a objetos para encapsular los datos.
 *
 *       Atributos:
 *       - nombre: El nombre de la persona que realiza la reservación.
 *       - celular: El número de celular de contacto.
 *       - dia: La fecha de la reservación (ej. "2024-12-25").
 *       - hora: La hora de la reservación (ej. "20:00").
 */
public class Reservacion {

    // --- ATRIBUTOS ---
    // Atributos privados para encapsular los datos de la reservación.
    private String nombre;
    private String celular;
    private String dia;
    private String hora;

    // --- CONSTRUCTOR ---
    /**
     * Constructor para inicializar un objeto Reservacion con todos sus datos.
     * Se utilizan parámetros para recibir los valores iniciales.
     *
     * @param nombre  El nombre del cliente.
     * @param celular El número de celular del cliente.
     * @param dia     El día de la reservación.
     * @param hora    La hora de la reservación.
     */
    public Reservacion(String nombre, String celular, String dia, String hora) {
        // Operadores de asignación (=) para inicializar los atributos de la instancia.
        this.nombre = nombre;
        this.celular = celular;
        this.dia = dia;
        this.hora = hora;
    }

    // --- MÉTODOS GETTER Y SETTER ---
    // Métodos públicos para acceder y modificar los atributos privados,
    // siguiendo el principio de encapsulación.

    /**
     * Función (Tipo 1: Retorna valor, sin parámetros)
     * Obtiene el nombre del cliente.
     *
     * @return El nombre del cliente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Procedimiento (Tipo 2: No retorna valor, con parámetros)
     * Establece el nombre del cliente.
     *
     * @param nombre El nuevo nombre a establecer.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Función (Tipo 1: Retorna valor, sin parámetros)
     * Obtiene el número de celular del cliente.
     *
     * @return El número de celular.
     */
    public String getCelular() {
        return celular;
    }

    /**
     * Procedimiento (Tipo 2: No retorna valor, con parámetros)
     * Establece el número de celular del cliente.
     *
     * @param celular El nuevo número de celular.
     */
    public void setCelular(String celular) {
        this.celular = celular;
    }

    /**
     * Función (Tipo 1: Retorna valor, sin parámetros)
     * Obtiene el día de la reservación.
     *
     * @return El día de la reservación.
     */
    public String getDia() {
        return dia;
    }

    /**
     * Procedimiento (Tipo 2: No retorna valor, con parámetros)
     * Establece el día de la reservación.
     *
     * @param dia El nuevo día de la reservación.
     */
    public void setDia(String dia) {
        this.dia = dia;
    }

    /**
     * Función (Tipo 1: Retorna valor, sin parámetros)
     * Obtiene la hora de la reservación.
     *
     * @return La hora de la reservación.
     */
    public String getHora() {
        return hora;
    }

    /**
     * Procedimiento (Tipo 2: No retorna valor, con parámetros)
     * Establece la hora de la reservación.
     *
     * @param hora La nueva hora de la reservación.
     */
    public void setHora(String hora) {
        this.hora = hora;
    }

    // --- MÉTODO PARA PERSISTENCIA ---
    /**
     * Función (Tipo 1: Retorna valor, sin parámetros)
     * Convierte el objeto Reservacion a un formato de cadena CSV para guardarlo en un archivo.
     * Se utiliza el operador aritmético (+) para la concatenación de cadenas.
     *
     * @return Una cadena con los datos de la reservación separados por comas.
     */
    @Override
    public String toString() {
        return nombre + "," + celular + "," + dia + "," + hora;
    }
}
