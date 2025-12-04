package RESTAURANTE;

import javax.swing.SwingUtilities;

/**
 * @author Jules
 * @date 2024-12-02
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ControladorReservaciones controlador = new ControladorReservaciones();
                VistaPrincipal vista = new VistaPrincipal(controlador);
                vista.setVisible(true);
            }
        });
    }
}
