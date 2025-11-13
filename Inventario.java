import javax.swing.JOptionPane;

/**
 * Descripción: Clase que gestiona el inventario de CDs.
 * Análisis: Esta clase se encarga de las operaciones principales como agregar,
 * mostrar, buscar y vender CDs, utilizando un arreglo de objetos CD.
 * La interacción con el usuario se realiza mediante JOptionPane.
 * Autor: Jules
 * Fecha: 13/11/2025
 */
public class Inventario {
    private CD[] cds;
    private int contador;

    public Inventario() {
        cds = new CD[50];
        contador = 0;
    }

    public void ingresarCD() {
        if (contador >= cds.length) {
            JOptionPane.showMessageDialog(null, "El inventario está lleno.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del CD:", "Ingresar CD", JOptionPane.QUESTION_MESSAGE);
            String artista = JOptionPane.showInputDialog(null, "Ingrese el nombre del artista:", "Ingresar CD", JOptionPane.QUESTION_MESSAGE);
            int anio = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el año:", "Ingresar CD", JOptionPane.QUESTION_MESSAGE));
            double precio = Double.parseDouble(JOptionPane.showInputDialog(null, "Ingrese el precio:", "Ingresar CD", JOptionPane.QUESTION_MESSAGE));
            int existencia = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese la cantidad en existencia:", "Ingresar CD", JOptionPane.QUESTION_MESSAGE));

            CD nuevoCD = new CD();
            nuevoCD.setNombre(nombre);
            nuevoCD.setArtista(artista);
            nuevoCD.setAnio(anio);
            nuevoCD.setPrecio(precio);
            nuevoCD.setExistencia(existencia);

            cds[contador] = nuevoCD;
            contador++;

            JOptionPane.showMessageDialog(null, "CD agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Dato inválido. Por favor, ingrese un número válido.", "Error de formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrarCDs() {
        if (contador == 0) {
            JOptionPane.showMessageDialog(null, "No hay CDs en el inventario.", "Inventario Vacío", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder lista = new StringBuilder("Lista de CDs:\n\n");
        for (int i = 0; i < contador; i++) {
            lista.append("CD ").append(i + 1).append(":\n");
            lista.append("  Nombre: ").append(cds[i].getNombre()).append("\n");
            lista.append("  Artista: ").append(cds[i].getArtista()).append("\n");
            lista.append("  Año: ").append(cds[i].getAnio()).append("\n");
            lista.append("  Precio: $").append(cds[i].getPrecio()).append("\n");
            lista.append("  Existencia: ").append(cds[i].getExistencia()).append("\n\n");
        }
        JOptionPane.showMessageDialog(null, lista.toString(), "Inventario de CDs", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarPorArtista() {
        if (contador == 0) {
            JOptionPane.showMessageDialog(null, "No hay CDs en el inventario.", "Inventario Vacío", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String artistaBuscado = JOptionPane.showInputDialog(null, "Ingrese el nombre del artista:", "Buscar por Artista", JOptionPane.QUESTION_MESSAGE);
        StringBuilder encontrados = new StringBuilder();
        boolean hallado = false;

        for (int i = 0; i < contador; i++) {
            if (cds[i].getArtista().equalsIgnoreCase(artistaBuscado)) {
                encontrados.append("Nombre: ").append(cds[i].getNombre()).append("\n");
                encontrados.append("Artista: ").append(cds[i].getArtista()).append("\n");
                encontrados.append("Año: ").append(cds[i].getAnio()).append("\n");
                encontrados.append("Precio: $").append(cds[i].getPrecio()).append("\n");
                encontrados.append("Existencia: ").append(cds[i].getExistencia()).append("\n\n");
                hallado = true;
            }
        }

        if (hallado) {
            JOptionPane.showMessageDialog(null, encontrados.toString(), "CDs de " + artistaBuscado, JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontraron CDs del artista: " + artistaBuscado, "No Encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void venta() {
        if (contador == 0) {
            JOptionPane.showMessageDialog(null, "No hay CDs para vender.", "Inventario Vacío", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String cdBuscado = JOptionPane.showInputDialog(null, "Ingrese el nombre del CD a vender:", "Venta de CD", JOptionPane.QUESTION_MESSAGE);
        CD cdEncontrado = null;

        for (int i = 0; i < contador; i++) {
            if (cds[i].getNombre().equalsIgnoreCase(cdBuscado)) {
                cdEncontrado = cds[i];
                break;
            }
        }

        if (cdEncontrado == null) {
            JOptionPane.showMessageDialog(null, "El CD no se encuentra en el inventario.", "No Encontrado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int cantidad = Integer.parseInt(JOptionPane.showInputDialog(null, "CD: " + cdEncontrado.getNombre() + "\nExistencia: " + cdEncontrado.getExistencia() + "\n\n¿Cuántos desea comprar?", "Venta", JOptionPane.QUESTION_MESSAGE));

            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a cero.", "Cantidad Inválida", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (cantidad > cdEncontrado.getExistencia()) {
                JOptionPane.showMessageDialog(null, "No hay suficientes existencias. Solo hay " + cdEncontrado.getExistencia() + " disponibles.", "Stock Insuficiente", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double total = cantidad * cdEncontrado.getPrecio();
            double pago = Double.parseDouble(JOptionPane.showInputDialog(null, "El total a pagar es: $" + total + "\n\nIngrese el monto del pago:", "Confirmar Venta", JOptionPane.QUESTION_MESSAGE));

            if (pago < total) {
                JOptionPane.showMessageDialog(null, "El pago es insuficiente.", "Error de Pago", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double cambio = pago - total;
            cdEncontrado.setExistencia(cdEncontrado.getExistencia() - cantidad);

            JOptionPane.showMessageDialog(null, "Venta exitosa:\n\n" +
                    "Total a pagar: $" + total + "\n" +
                    "Pagado: $" + pago + "\n" +
                    "Cambio: $" + cambio + "\n\n" +
                    "Existencia restante: " + cdEncontrado.getExistencia(),
                    "Venta Completada", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Dato inválido. Por favor, ingrese un número.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
}
