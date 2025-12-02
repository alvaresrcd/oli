package vista;

import controlador.ControladorReservaciones;
import modelo.Reservacion;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author Jules
 * @date 2024-12-02
 */
public class VistaPrincipal extends JFrame {

    private ControladorReservaciones controlador;
    private JTable tablaReservaciones;
    private DefaultTableModel modeloTabla;
    private JTextField txtNombre, txtCelular, txtDia, txtHora;
    private JButton btnAgregar, btnModificar, btnCancelar;

    public VistaPrincipal(ControladorReservaciones controlador) {
        this.controlador = controlador;

        setTitle("Sistema de Reservaciones de Restaurante");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
        actualizarTabla();
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(panelPrincipal);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Opciones");
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> System.exit(0));
        menuArchivo.add(itemSalir);
        menuBar.add(menuArchivo);
        setJMenuBar(menuBar);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos de la Reservación"));

        // --- Refactorización para el diseñador de Eclipse ---
        // Se crea un nuevo objeto GridBagConstraints para cada componente
        // para evitar la advertencia "Different parents for double associations".

        // Fila 0: Nombre
        GridBagConstraints gbc_lblNombre = new GridBagConstraints();
        gbc_lblNombre.insets = new Insets(5, 5, 5, 5);
        gbc_lblNombre.gridx = 0;
        gbc_lblNombre.gridy = 0;
        gbc_lblNombre.anchor = GridBagConstraints.WEST;
        panelFormulario.add(new JLabel("Nombre:"), gbc_lblNombre);

        GridBagConstraints gbc_txtNombre = new GridBagConstraints();
        gbc_txtNombre.insets = new Insets(5, 5, 5, 5);
        gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtNombre.gridx = 1;
        gbc_txtNombre.gridy = 0;
        txtNombre = new JTextField(15);
        panelFormulario.add(txtNombre, gbc_txtNombre);

        // Fila 1: Celular
        GridBagConstraints gbc_lblCelular = new GridBagConstraints();
        gbc_lblCelular.insets = new Insets(5, 5, 5, 5);
        gbc_lblCelular.gridx = 0;
        gbc_lblCelular.gridy = 1;
        gbc_lblCelular.anchor = GridBagConstraints.WEST;
        panelFormulario.add(new JLabel("Celular:"), gbc_lblCelular);

        GridBagConstraints gbc_txtCelular = new GridBagConstraints();
        gbc_txtCelular.insets = new Insets(5, 5, 5, 5);
        gbc_txtCelular.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtCelular.gridx = 1;
        gbc_txtCelular.gridy = 1;
        txtCelular = new JTextField(15);
        panelFormulario.add(txtCelular, gbc_txtCelular);

        // Fila 2: Día
        GridBagConstraints gbc_lblDia = new GridBagConstraints();
        gbc_lblDia.insets = new Insets(5, 5, 5, 5);
        gbc_lblDia.gridx = 0;
        gbc_lblDia.gridy = 2;
        gbc_lblDia.anchor = GridBagConstraints.WEST;
        panelFormulario.add(new JLabel("Día (YYYY-MM-DD):"), gbc_lblDia);

        GridBagConstraints gbc_txtDia = new GridBagConstraints();
        gbc_txtDia.insets = new Insets(5, 5, 5, 5);
        gbc_txtDia.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtDia.gridx = 1;
        gbc_txtDia.gridy = 2;
        txtDia = new JTextField(15);
        panelFormulario.add(txtDia, gbc_txtDia);

        // Fila 3: Hora
        GridBagConstraints gbc_lblHora = new GridBagConstraints();
        gbc_lblHora.insets = new Insets(5, 5, 5, 5);
        gbc_lblHora.gridx = 0;
        gbc_lblHora.gridy = 3;
        gbc_lblHora.anchor = GridBagConstraints.WEST;
        panelFormulario.add(new JLabel("Hora (HH:MM):"), gbc_lblHora);

        GridBagConstraints gbc_txtHora = new GridBagConstraints();
        gbc_txtHora.insets = new Insets(5, 5, 5, 5);
        gbc_txtHora.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtHora.gridx = 1;
        gbc_txtHora.gridy = 3;
        txtHora = new JTextField(15);
        panelFormulario.add(txtHora, gbc_txtHora);

        // Fila 4: Panel de Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnCancelar);
        GridBagConstraints gbc_panelBotones = new GridBagConstraints();
        gbc_panelBotones.insets = new Insets(5, 5, 5, 5);
        gbc_panelBotones.gridx = 0;
        gbc_panelBotones.gridy = 4;
        gbc_panelBotones.gridwidth = 2;
        panelFormulario.add(panelBotones, gbc_panelBotones);

        // Fila 5: Imagen
        GridBagConstraints gbc_lblImagen = new GridBagConstraints();
        gbc_lblImagen.insets = new Insets(5, 5, 5, 5);
        gbc_lblImagen.gridx = 0;
        gbc_lblImagen.gridy = 5;
        gbc_lblImagen.gridwidth = 2;
        java.net.URL imgUrl = getClass().getResource("/recursos/icono_restaurante.png");
        JLabel lblImagen;
        if (imgUrl != null) {
            ImageIcon icono = new ImageIcon(imgUrl);
            Image imagen = icono.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            lblImagen = new JLabel(new ImageIcon(imagen));
        } else {
            lblImagen = new JLabel("Imagen no encontrada");
            System.err.println("Error: No se pudo encontrar el recurso de imagen.");
        }
        panelFormulario.add(lblImagen, gbc_lblImagen);

        panelPrincipal.add(panelFormulario, BorderLayout.WEST);

        String[] columnas = {"Nombre", "Celular", "Día", "Hora"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaReservaciones = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaReservaciones);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        btnAgregar.addActionListener(e -> agregarReservacion());
        btnModificar.addActionListener(e -> modificarReservacion());
        btnCancelar.addActionListener(e -> cancelarReservacion());

        tablaReservaciones.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaReservaciones.getSelectedRow() != -1) {
                int filaSeleccionada = tablaReservaciones.getSelectedRow();
                txtNombre.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
                txtCelular.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
                txtDia.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
                txtHora.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
            }
        });
    }

    private void agregarReservacion() {
        try {
            String nombre = txtNombre.getText();
            String celular = txtCelular.getText();
            String dia = txtDia.getText();
            String hora = txtHora.getText();

            if (nombre.isEmpty() || celular.isEmpty() || dia.isEmpty() || hora.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            controlador.agregarReservacion(new Reservacion(nombre, celular, dia, hora));
            actualizarTabla();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Reservación agregada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al agregar la reservación: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarReservacion() {
        int filaSeleccionada = tablaReservaciones.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una reservación de la tabla para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String celular = modeloTabla.getValueAt(filaSeleccionada, 1).toString();
        String nuevoDia = txtDia.getText();
        String nuevaHora = txtHora.getText();

        if (controlador.modificarReservacion(celular, nuevoDia, nuevaHora)) {
            actualizarTabla();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Reservación modificada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo modificar la reservación.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelarReservacion() {
        int filaSeleccionada = tablaReservaciones.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una reservación de la tabla para cancelar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String celular = modeloTabla.getValueAt(filaSeleccionada, 1).toString();
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea cancelar esta reservación?", "Confirmar Cancelación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (controlador.cancelarReservacion(celular)) {
                actualizarTabla();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Reservación cancelada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo encontrar o cancelar la reservación.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        List<Reservacion> reservaciones = controlador.getReservaciones();
        for (Reservacion r : reservaciones) {
            Object[] fila = {r.getNombre(), r.getCelular(), r.getDia(), r.getHora()};
            modeloTabla.addRow(fila);
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtCelular.setText("");
        txtDia.setText("");
        txtHora.setText("");
        tablaReservaciones.clearSelection();
    }
}
