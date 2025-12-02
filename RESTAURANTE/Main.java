package RESTAURANTE;

import RESTAURANTE.controlador.ControladorReservaciones;
import RESTAURANTE.vista.VistaPrincipal;

import javax.swing.SwingUtilities;

/**
 * @author Jules
 * @date 2024-12-02
 *
 *       Descripción:
 *       Clase principal que contiene el punto de entrada (método main) de la aplicación.
 *       Se encarga de inicializar el controlador y la vista, y de lanzar la
 *       interfaz gráfica de usuario.
 */
public class Main {

    /**
     * Método principal que inicia la aplicación.
     *
     * @param args Argumentos de la línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        // Se utiliza SwingUtilities.invokeLater para asegurar que la GUI se cree y
        // se actualice en el hilo de despacho de eventos (Event Dispatch Thread o EDT).
        // Esta es la forma recomendada para iniciar aplicaciones Swing.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // 1. Crear una instancia del controlador.
                //    Este se encargará de la lógica y la carga de datos.
                ControladorReservaciones controlador = new ControladorReservaciones();

                // 2. Crear una instancia de la vista principal, pasándole el controlador.
                VistaPrincipal vista = new VistaPrincipal(controlador);

                // 3. Hacer visible la ventana.
                vista.setVisible(true);
            }
        });
    }
}
