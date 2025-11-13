import javax.swing.JOptionPane;

/**
 * Descripción: Clase principal que contiene el menú de la aplicación.
 * Análisis: Esta clase inicia la aplicación, muestra el menú principal y
 * gestiona las opciones del usuario, interactuando con la clase Inventario
 * para ejecutar las operaciones correspondientes.
 * Autor: Jules
 * Fecha: 13/11/2025
 */
public class Menu {
    public static void main(String[] args) {
        Inventario inventario = new Inventario();
        String opcion;

        do {
            opcion = JOptionPane.showInputDialog(
                null,
                "Menú Principal\n\n" +
                "1. Ingresar un nuevo CD\n" +
                "2. Mostrar CDs\n" +
                "3. Mostrar por nombre del artista\n" +
                "4. Venta\n" +
                "5. Salir\n\n" +
                "Seleccione una opción:",
                "Control de Inventario de CDs",
                JOptionPane.PLAIN_MESSAGE
            );

            if (opcion == null) {
                // El usuario cerró el diálogo, salir del programa
                break;
            }

            switch (opcion) {
                case "1":
                    inventario.ingresarCD();
                    break;
                case "2":
                    inventario.mostrarCDs();
                    break;
                case "3":
                    inventario.mostrarPorArtista();
                    break;
                case "4":
                    inventario.venta();
                    break;
                case "5":
                    JOptionPane.showMessageDialog(null, "Saliendo del programa.", "Adiós", JOptionPane.INFORMATION_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida. Intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }

        } while (!"5".equals(opcion));
    }
}
