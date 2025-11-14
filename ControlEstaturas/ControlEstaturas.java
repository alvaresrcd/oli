import javax.swing.JOptionPane;

/**
 * Descripcion del problema: Realizar un control de estaturas del jardín de niños "Niños Felices",
 * el cual cuenta con 98 niños. Se requiere almacenar nombre, edad (3 a 6 años) y estatura en cm,
 * y ofrecer un menú para mostrar, buscar, filtrar y modificar datos.
 * Analisis: La clase ControlEstaturas contendrá el método main y la lógica principal del programa.
 * Se usará un arreglo de objetos Nino para almacenar los datos. La interacción con el usuario
 * se realizará mediante JOptionPane, con manejo de excepciones para validar las entradas.
 * Autor: Jules
 * Fecha: 14/11/2025
 */
public class ControlEstaturas {
    // Arreglo para almacenar los 98 niños
    private static final Nino[] ninos = new Nino[98];
    private static int contadorNinos = 0; // Para saber cuántos niños se han registrado

    public static void main(String[] args) {
        // Llenar los datos de los 98 niños al iniciar
        llenarDatos();

        int opcion;
        do {
            try {
                String menu = "MENU DE OPCIONES\n"
                        + "1. Mostrar todos los datos\n"
                        + "2. Buscar por nombre\n"
                        + "3. Mostrar por edad\n"
                        + "4. Mostrar por estatura\n"
                        + "5. Modificar un registro\n"
                        + "6. Salir";
                opcion = Integer.parseInt(JOptionPane.showInputDialog(menu));

                switch (opcion) {
                    case 1:
                        mostrarTodos();
                        break;
                    case 2:
                        buscarPorNombre();
                        break;
                    case 3:
                        mostrarPorEdad();
                        break;
                    case 4:
                        mostrarPorEstatura();
                        break;
                    case 5:
                        modificarRegistro();
                        break;
                    case 6:
                        JOptionPane.showMessageDialog(null, "Saliendo del programa...");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opción no válida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error: Ingrese un número válido para la opción.");
                opcion = 0; // Para que el bucle continúe
            }
        } while (opcion != 6);
    }

    public static void llenarDatos() {
        JOptionPane.showMessageDialog(null, "A continuación, ingrese los datos para los 98 niños.");
        while (contadorNinos < 98) {
            try {
                String nombre = JOptionPane.showInputDialog("Ingrese el nombre del niño #" + (contadorNinos + 1));
                if (nombre == null || nombre.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío.");
                    continue;
                }

                int edad = 0;
                boolean edadValida = false;
                while (!edadValida) {
                    try {
                        edad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la edad del niño #" + (contadorNinos + 1) + " (entre 3 y 6)"));
                        if (edad >= 3 && edad <= 6) {
                            edadValida = true;
                        } else {
                            JOptionPane.showMessageDialog(null, "La edad debe estar entre 3 y 6 años.");
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Error: Ingrese un número válido para la edad.");
                    }
                }

                double estatura = 0;
                boolean estaturaValida = false;
                while (!estaturaValida) {
                    try {
                        estatura = Double.parseDouble(JOptionPane.showInputDialog("Ingrese la estatura en cm del niño #" + (contadorNinos + 1)));
                        if (estatura > 0) {
                            estaturaValida = true;
                        } else {
                            JOptionPane.showMessageDialog(null, "La estatura debe ser un número positivo.");
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Error: Ingrese un número válido para la estatura.");
                    }
                }

                ninos[contadorNinos] = new Nino(nombre, edad, estatura);
                contadorNinos++;

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocurrió un error inesperado. Intente de nuevo.");
            }
        }
    }

    public static void mostrarTodos() {
        if (contadorNinos == 0) {
            JOptionPane.showMessageDialog(null, "No hay niños registrados.");
            return;
        }

        StringBuilder sb = new StringBuilder("Lista de todos los niños:\n\n");
        for (int i = 0; i < contadorNinos; i++) {
            sb.append("Registro #").append(i + 1).append("\n");
            sb.append("Nombre: ").append(ninos[i].obtenerNombre()).append("\n");
            sb.append("Edad: ").append(ninos[i].obtenerEdad()).append(" años\n");
            sb.append("Estatura: ").append(ninos[i].obtenerEstatura()).append(" cm\n\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public static void buscarPorNombre() {
        if (contadorNinos == 0) {
            JOptionPane.showMessageDialog(null, "No hay niños registrados.");
            return;
        }

        String nombreBusqueda = JOptionPane.showInputDialog("Ingrese el nombre del niño a buscar:");
        if (nombreBusqueda == null || nombreBusqueda.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre de búsqueda no puede estar vacío.");
            return;
        }

        StringBuilder sb = new StringBuilder("Resultados de la búsqueda para '" + nombreBusqueda + "':\n\n");
        boolean encontrado = false;
        for (int i = 0; i < contadorNinos; i++) {
            if (ninos[i].obtenerNombre().equalsIgnoreCase(nombreBusqueda.trim())) {
                sb.append("Registro #").append(i + 1).append("\n");
                sb.append("Edad: ").append(ninos[i].obtenerEdad()).append(" años\n");
                sb.append("Estatura: ").append(ninos[i].obtenerEstatura()).append(" cm\n\n");
                encontrado = true;
            }
        }

        if (encontrado) {
            JOptionPane.showMessageDialog(null, sb.toString());
        } else {
            JOptionPane.showMessageDialog(null, "No se encontraron niños con el nombre '" + nombreBusqueda + "'.");
        }
    }

    public static void mostrarPorEdad() {
        if (contadorNinos == 0) {
            JOptionPane.showMessageDialog(null, "No hay niños registrados.");
            return;
        }

        int edadBusqueda = 0;
        boolean edadValida = false;
        while (!edadValida) {
            try {
                edadBusqueda = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la edad a buscar (entre 3 y 6):"));
                if (edadBusqueda >= 3 && edadBusqueda <= 6) {
                    edadValida = true;
                } else {
                    JOptionPane.showMessageDialog(null, "La edad debe estar entre 3 y 6 años.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error: Ingrese un número válido para la edad.");
            }
        }

        StringBuilder sb = new StringBuilder("Niños con " + edadBusqueda + " años:\n\n");
        boolean encontrado = false;
        for (int i = 0; i < contadorNinos; i++) {
            if (ninos[i].obtenerEdad() == edadBusqueda) {
                sb.append("Nombre: ").append(ninos[i].obtenerNombre()).append("\n");
                sb.append("Estatura: ").append(ninos[i].obtenerEstatura()).append(" cm\n\n");
                encontrado = true;
            }
        }

        if (encontrado) {
            JOptionPane.showMessageDialog(null, sb.toString());
        } else {
            JOptionPane.showMessageDialog(null, "No se encontraron niños de " + edadBusqueda + " años.");
        }
    }

    public static void mostrarPorEstatura() {
        if (contadorNinos == 0) {
            JOptionPane.showMessageDialog(null, "No hay niños registrados.");
            return;
        }

        double estaturaBusqueda = 0;
        boolean estaturaValida = false;
        while (!estaturaValida) {
            try {
                estaturaBusqueda = Double.parseDouble(JOptionPane.showInputDialog("Ingrese la estatura en cm a buscar:"));
                if (estaturaBusqueda > 0) {
                    estaturaValida = true;
                } else {
                    JOptionPane.showMessageDialog(null, "La estatura debe ser un número positivo.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error: Ingrese un número válido para la estatura.");
            }
        }

        StringBuilder sb = new StringBuilder("Niños con " + estaturaBusqueda + " cm de estatura:\n\n");
        boolean encontrado = false;
        for (int i = 0; i < contadorNinos; i++) {
            if (ninos[i].obtenerEstatura() == estaturaBusqueda) {
                sb.append("Nombre: ").append(ninos[i].obtenerNombre()).append("\n");
                sb.append("Edad: ").append(ninos[i].obtenerEdad()).append(" años\n\n");
                encontrado = true;
            }
        }

        if (encontrado) {
            JOptionPane.showMessageDialog(null, sb.toString());
        } else {
            JOptionPane.showMessageDialog(null, "No se encontraron niños con " + estaturaBusqueda + " cm de estatura.");
        }
    }

    public static void modificarRegistro() {
        if (contadorNinos == 0) {
            JOptionPane.showMessageDialog(null, "No hay niños registrados para modificar.");
            return;
        }

        int registro = 0;
        boolean registroValido = false;
        while (!registroValido) {
            try {
                registro = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de registro a modificar (1 a " + contadorNinos + "):"));
                if (registro >= 1 && registro <= contadorNinos) {
                    registroValido = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Número de registro fuera de rango.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error: Ingrese un número válido para el registro.");
            }
        }

        int index = registro - 1;
        Nino ninoAModificar = ninos[index];

        String menuModificar = "Qué desea modificar para " + ninoAModificar.obtenerNombre() + "?\n"
                + "1. Nombre\n"
                + "2. Edad\n"
                + "3. Estatura\n"
                + "4. Cancelar";

        try {
            int opcion = Integer.parseInt(JOptionPane.showInputDialog(menuModificar));
            switch (opcion) {
                case 1:
                    String nuevoNombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre:", ninoAModificar.obtenerNombre());
                    if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                        ninoAModificar.establecerNombre(nuevoNombre);
                        JOptionPane.showMessageDialog(null, "Nombre modificado con éxito.");
                    } else {
                        JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío. No se realizaron cambios.");
                    }
                    break;
                case 2:
                    int nuevaEdad = 0;
                    boolean edadValida = false;
                    while (!edadValida) {
                        try {
                            nuevaEdad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la nueva edad (3 a 6):", ninoAModificar.obtenerEdad()));
                            if (nuevaEdad >= 3 && nuevaEdad <= 6) {
                                ninoAModificar.establecerEdad(nuevaEdad);
                                JOptionPane.showMessageDialog(null, "Edad modificada con éxito.");
                                edadValida = true;
                            } else {
                                JOptionPane.showMessageDialog(null, "La edad debe estar entre 3 y 6 años.");
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Error: Ingrese un número válido.");
                        }
                    }
                    break;
                case 3:
                    double nuevaEstatura = 0;
                    boolean estaturaValida = false;
                    while (!estaturaValida) {
                        try {
                            nuevaEstatura = Double.parseDouble(JOptionPane.showInputDialog("Ingrese la nueva estatura en cm:", ninoAModificar.obtenerEstatura()));
                            if (nuevaEstatura > 0) {
                                ninoAModificar.establecerEstatura(nuevaEstatura);
                                JOptionPane.showMessageDialog(null, "Estatura modificada con éxito.");
                                estaturaValida = true;
                            } else {
                                JOptionPane.showMessageDialog(null, "La estatura debe ser un número positivo.");
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Error: Ingrese un número válido.");
                        }
                    }
                    break;
                case 4:
                    JOptionPane.showMessageDialog(null, "Modificación cancelada.");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: Opción inválida.");
        }
    }
}
