package RESTAURANTE.vista;

import RESTAURANTE.controlador.ControladorReservaciones;
import RESTAURANTE.modelo.Reservacion;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author Jules
 * @date 2024-12-02
 *
 *       Descripción:
 *       Clase que construye y gestiona la interfaz gráfica de usuario (GUI) para el sistema de reservaciones.
 *       Extiende JFrame y contiene todos los componentes visuales como botones, campos de texto y tablas.
 *       Utiliza la técnica de programación orientada a eventos para responder a las interacciones del usuario.
 */
public class VistaPrincipal extends JFrame {

    // --- ATRIBUTOS ---
    private ControladorReservaciones controlador;

    // Componentes de la GUI
    private JTable tablaReservaciones;
    private DefaultTableModel modeloTabla;
    private JTextField txtNombre, txtCelular, txtDia, txtHora;
    private JButton btnAgregar, btnModificar, btnCancelar;

    // --- CONSTRUCTOR ---
    public VistaPrincipal(ControladorReservaciones controlador) {
        this.controlador = controlador;

        // Configuración de la ventana principal
        setTitle("Sistema de Reservaciones de Restaurante");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en la pantalla

        // Inicializar componentes
        inicializarComponentes();

        // Cargar datos iniciales
        actualizarTabla();
    }

    /**
     * Procedimiento (Tipo 4: No retorna valor, sin parámetros)
     * Inicializa y organiza todos los componentes de la interfaz gráfica.
     */
    private void inicializarComponentes() {
        // --- PANEL PRINCIPAL ---
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(panelPrincipal);

        // --- MENÚ ---
        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Opciones");
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> System.exit(0));
        menuArchivo.add(itemSalir);
        menuBar.add(menuArchivo);
        setJMenuBar(menuBar);

        // --- PANEL DE FORMULARIO (Izquierda) ---
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos de la Reservación"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etiquetas y Campos de texto

        // Fila 0: Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panelFormulario.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        txtNombre = new JTextField(15);
        panelFormulario.add(txtNombre, gbc);

        // Fila 1: Celular
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(new JLabel("Celular:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        txtCelular = new JTextField(15);
        panelFormulario.add(txtCelular, gbc);

        // Fila 2: Día
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(new JLabel("Día (YYYY-MM-DD):"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        txtDia = new JTextField(15);
        panelFormulario.add(txtDia, gbc);

        // Fila 3: Hora
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelFormulario.add(new JLabel("Hora (HH:MM):"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        txtHora = new JTextField(15);
        panelFormulario.add(txtHora, gbc);

        // Fila 4: Panel de Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnCancelar);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panelFormulario.add(panelBotones, gbc);

        // Fila 5: Imagen
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        ImageIcon icono = new ImageIcon("RESTAURANTE/recursos/icono_restaurante.png");
        Image imagen = icono.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel lblImagen = new JLabel(new ImageIcon(imagen));
        panelFormulario.add(lblImagen, gbc);

        panelPrincipal.add(panelFormulario, BorderLayout.WEST);

        // --- PANEL DE TABLA (Derecha) ---
        String[] columnas = {"Nombre", "Celular", "Día", "Hora"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaReservaciones = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaReservaciones);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        // --- MANEJO DE EVENTOS ---
        btnAgregar.addActionListener(e -> agregarReservacion());
        btnModificar.addActionListener(e -> modificarReservacion());
        btnCancelar.addActionListener(e -> cancelarReservacion());

        // Evento para cargar datos del formulario al seleccionar una fila
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

    /**
     * Procedimiento que se ejecuta al presionar el botón "Agregar".
     * Recoge los datos del formulario, los valida y llama al controlador.
     */
    private void agregarReservacion() {
        try {
            // Entrada de datos
            String nombre = txtNombre.getText();
            String celular = txtCelular.getText();
            String dia = txtDia.getText();
            String hora = txtHora.getText();

            // Uso de operadores lógicos (||) para validar que los campos no estén vacíos.
            if (nombre.isEmpty() || celular.isEmpty() || dia.isEmpty() || hora.isEmpty()) {
                // Ventana creada por JOptionPane
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Uso de una función del controlador
            controlador.agregarReservacion(new Reservacion(nombre, celular, dia, hora));
            actualizarTabla();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Reservación agregada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            // Manejo de excepciones
            JOptionPane.showMessageDialog(this, "Ocurrió un error al agregar la reservación: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Procedimiento para manejar la modificación de una reservación.
     */
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

    /**
     * Procedimiento para manejar la cancelación de una reservación.
     */
    private void cancelarReservacion() {
        int filaSeleccionada = tablaReservaciones.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una reservación de la tabla para cancelar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String celular = modeloTabla.getValueAt(filaSeleccionada, 1).toString();
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea cancelar esta reservación?", "Confirmar Cancelación", JOptionPane.YES_NO_OPTION);

        // Estructura de decisión 'if'
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

    /**
     * Procedimiento que actualiza la JTable con la lista de reservaciones del controlador.
     */
    private void actualizarTabla() {
        // Limpiar tabla actual
        modeloTabla.setRowCount(0);

        // Cargar nuevos datos
        List<Reservacion> reservaciones = controlador.getReservaciones();
        // Estructura de repetición 'for-each'
        for (Reservacion r : reservaciones) {
            Object[] fila = {r.getNombre(), r.getCelular(), r.getDia(), r.getHora()};
            modeloTabla.addRow(fila);
        }
    }

    /**
     * Procedimiento para limpiar los campos de texto del formulario.
     */
    private void limpiarCampos() {
        txtNombre.setText("");
        txtCelular.setText("");
        txtDia.setText("");
        txtHora.setText("");
        tablaReservaciones.clearSelection();
    }
}
