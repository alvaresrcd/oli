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
import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class VentanaPrincipal extends JFrame {
    private JTextArea areaTextoPacientes;
    private ArrayList<Paciente> listaPacientes = new ArrayList<>();
    private int proximoNumeroAtencion = 1;
    private final String archivoPacientes = "Por_atender.txt";

    public VentanaPrincipal() {
        setTitle("Simulador de Atencion de Pacientes - UMAE");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Para controlar el cierre
        setLocationRelativeTo(null);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                salir();
            }
        });

        cargarPacientes();

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menuArchivo = new JMenu("Archivo");
        menuBar.add(menuArchivo);
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> salir());
        menuArchivo.add(itemSalir);

        JMenu menuPacientes = new JMenu("Pacientes");
        menuBar.add(menuPacientes);
        JMenuItem itemNuevoPaciente = new JMenuItem("Nuevo Paciente");
        itemNuevoPaciente.addActionListener(e -> abrirVentanaNuevoPaciente());
        menuPacientes.add(itemNuevoPaciente);
        JMenuItem itemMostrarTodos = new JMenuItem("Mostrar todos los Pacientes");
        itemMostrarTodos.addActionListener(e -> mostrarPacientes());
        menuPacientes.add(itemMostrarTodos);
        JMenuItem itemBuscar = new JMenuItem("Buscar Paciente");
        itemBuscar.addActionListener(e -> buscarPaciente());
        menuPacientes.add(itemBuscar);
        JMenuItem itemAtender = new JMenuItem("Atender Paciente");
        itemAtender.addActionListener(e -> atenderPaciente());
        menuPacientes.add(itemAtender);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        add(panelPrincipal);

        ImageIcon imagen = new ImageIcon("hospital.png");
        JLabel etiquetaImagen = new JLabel();
        if (imagen.getImage() != null && imagen.getIconWidth() > 0) {
            etiquetaImagen.setIcon(imagen);
        } else {
            etiquetaImagen.setText("Imagen no encontrada: hospital.png");
        }
        panelPrincipal.add(etiquetaImagen, BorderLayout.NORTH);

        areaTextoPacientes = new JTextArea();
        areaTextoPacientes.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaTextoPacientes);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        mostrarPacientes();
    }

    public void agregarPaciente(String nombre, int edad, String areaMedica, String tipoAtencion) {
        Paciente nuevoPaciente = new Paciente(proximoNumeroAtencion++, nombre, edad, areaMedica, tipoAtencion);
        listaPacientes.add(nuevoPaciente);
        guardarPacientes();
        mostrarPacientes();
        JOptionPane.showMessageDialog(this, "Paciente registrado con exito.\nNumero de Atencion: " + nuevoPaciente.getNumeroAtencion());
    }

    private void abrirVentanaNuevoPaciente() {
        VentanaNuevoPaciente ventanaNuevo = new VentanaNuevoPaciente(this);
        ventanaNuevo.setVisible(true);
    }

    private boolean isListaVacia() {
        return listaPacientes.isEmpty();
    }

    private void mostrarPacientes() {
        areaTextoPacientes.setText("");
        if (isListaVacia()) {
            areaTextoPacientes.setText("No hay pacientes por atender.");
            return;
        }
        for (Paciente p : listaPacientes) {
            areaTextoPacientes.append(p.toString());
        }
    }

    private void atenderPaciente() {
        if (isListaVacia()) {
            JOptionPane.showMessageDialog(this, "No hay pacientes en la lista para atender.", "Lista Vacia", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Paciente pacienteAtendido = listaPacientes.remove(0);
        guardarPacientes();
        mostrarPacientes();
        JOptionPane.showMessageDialog(this, "Se ha atendido al paciente:\n" + pacienteAtendido.getNombre(), "Paciente Atendido", JOptionPane.INFORMATION_MESSAGE);
    }

    private void buscarPaciente() {
        String[] opciones = {"Nombre", "Numero de Atencion", "Area Medica"};
        String criterio = (String) JOptionPane.showInputDialog(this, "Seleccione el criterio de busqueda:",
                "Buscar Paciente", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (criterio == null) return;

        String valorBusqueda = JOptionPane.showInputDialog(this, "Ingrese el " + criterio + " a buscar:");

        if (valorBusqueda == null || valorBusqueda.trim().isEmpty()) return;

        ArrayList<Paciente> resultados = new ArrayList<>();
        switch (criterio) {
            case "Nombre":
                resultados = listaPacientes.stream()
                        .filter(p -> p.getNombre().equalsIgnoreCase(valorBusqueda.trim()))
                        .collect(Collectors.toCollection(ArrayList::new));
                break;
            case "Numero de Atencion":
                try {
                    int num = Integer.parseInt(valorBusqueda.trim());
                    resultados = listaPacientes.stream()
                            .filter(p -> p.getNumeroAtencion() == num)
                            .collect(Collectors.toCollection(ArrayList::new));
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Numero de atencion invalido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                break;
            case "Area Medica":
                resultados = listaPacientes.stream()
                        .filter(p -> p.getAreaMedica().equalsIgnoreCase(valorBusqueda.trim()))
                        .collect(Collectors.toCollection(ArrayList::new));
                break;
        }

        areaTextoPacientes.setText("");
        if (resultados.isEmpty()) {
            areaTextoPacientes.setText("No se encontraron pacientes con ese criterio.");
        } else {
            areaTextoPacientes.setText("Resultados de la busqueda:\n\n");
            for (Paciente p : resultados) {
                areaTextoPacientes.append(p.toString());
            }
        }
    }

    private void guardarPacientes() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivoPacientes))) {
            pw.println(proximoNumeroAtencion);
            for (Paciente p : listaPacientes) {
                pw.println(p.toFileString());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar los datos de pacientes.", "Error de Archivo", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarPacientes() {
        File archivo = new File(archivoPacientes);
        if (archivo.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea = br.readLine();
                if (linea != null && !linea.isEmpty()) {
                    proximoNumeroAtencion = Integer.parseInt(linea);
                }
                listaPacientes.clear();
                while ((linea = br.readLine()) != null) {
                    Paciente p = Paciente.fromFileString(linea);
                    if (p != null) {
                        listaPacientes.add(p);
                    }
                }
            } catch (IOException | NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error al cargar los datos. Se iniciara una lista vacia.", "Error de Archivo", JOptionPane.ERROR_MESSAGE);
                listaPacientes = new ArrayList<>();
                proximoNumeroAtencion = 1;
            }
        }
    }

    private void salir() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Esta seguro de que desea salir?",
                "Confirmar Salida", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }
    }
}
