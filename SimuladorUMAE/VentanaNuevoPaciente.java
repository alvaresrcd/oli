/**
 * Descripcion: Simulador de atencion de pacientes de una Unidad de Medicina de Alta Especialidad.
 * Analisis: Este programa simula la gestion de pacientes en una clinica, permitiendo
 * registrar, mostrar, buscar y atender pacientes. Los datos se guardan en un archivo de texto.
 * La interfaz grafica esta desarrollada con JFrame.
 * Autor: Jules
 * Fecha: 27 de noviembre de 2024
 */

package SimuladorUMAE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaNuevoPaciente extends JFrame {
    private JTextField campoNombre;
    private JTextField campoEdad;
    private JComboBox<String> comboAreaMedica;
    private JComboBox<String> comboTipoAtencion;
    private JButton botonGuardar;
    private VentanaPrincipal ventanaPrincipal;

    public VentanaNuevoPaciente(VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;

        setTitle("Registrar Nuevo Paciente");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Usar GridBagLayout para un diseno mas robusto
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fila 1: Etiqueta Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Nombre:"), gbc);

        // Fila 1: Campo Nombre
        gbc.gridx = 1;
        gbc.gridy = 0;
        campoNombre = new JTextField(20);
        add(campoNombre, gbc);

        // Fila 2: Etiqueta Edad
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Edad:"), gbc);

        // Fila 2: Campo Edad
        gbc.gridx = 1;
        gbc.gridy = 1;
        campoEdad = new JTextField(20);
        add(campoEdad, gbc);

        // Fila 3: Etiqueta Area Medica
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Area Medica:"), gbc);

        // Fila 3: Combo Area Medica
        gbc.gridx = 1;
        gbc.gridy = 2;
        String[] areas = {"Obstetricia", "Pediatria", "Ortopedia", "Cirugia General", "Odontologia"};
        comboAreaMedica = new JComboBox<>(areas);
        add(comboAreaMedica, gbc);

        // Fila 4: Etiqueta Tipo Atencion
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Tipo de Atencion:"), gbc);

        // Fila 4: Combo Tipo Atencion
        gbc.gridx = 1;
        gbc.gridy = 3;
        String[] tipos = {"Diagnostico de enfermedad", "Seguimiento", "Curacion"};
        comboTipoAtencion = new JComboBox<>(tipos);
        add(comboTipoAtencion, gbc);

        // Fila 5: Boton Guardar
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        botonGuardar = new JButton("Guardar");
        add(botonGuardar, gbc);

        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarPaciente();
            }
        });
    }

    private void guardarPaciente() {
        String nombre = campoNombre.getText();
        String edadStr = campoEdad.getText();
        String areaMedica = (String) comboAreaMedica.getSelectedItem();
        String tipoAtencion = (String) comboTipoAtencion.getSelectedItem();

        if (nombre.isEmpty() || edadStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int edad = Integer.parseInt(edadStr);
            ventanaPrincipal.agregarPaciente(nombre, edad, areaMedica, tipoAtencion);
            dispose(); // Cierra la ventana de registro
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La edad debe ser un numero valido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
