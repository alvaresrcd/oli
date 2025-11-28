/**
 * Descripcion: Simulador de atencion de pacientes de una Unidad de Medicina de Alta Especialidad.
 * Analisis: Este programa simula la gestion de pacientes en una clinica, permitiendo
 * registrar, mostrar, buscar y atender pacientes. Los datos se guardan en un archivo de texto.
 * La interfaz grafica esta desarrollada con JFrame.
 * Autor: Jules
 * Fecha: 28 de noviembre de 2024
 */

package SimuladorUMAE;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VentanaPrincipal extends JFrame {
    private JTextArea areaTextoPacientes;

    private ArrayList<Paciente> listaPacientesPorAtender = new ArrayList<>();
    private ArrayList<Paciente> listaPacientesAtendidos = new ArrayList<>();

    private int proximoNumeroAtencion = 1;

    private final String archivoPacientesPorAtender = "Por_atender.txt";
    private final String archivoPacientesAtendidos = "pacientes.txt";

    public VentanaPrincipal() {
        setTitle("Simulador de Atencion de Pacientes - UMAE");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                salir();
            }
        });

        cargarPacientesPorAtender();
        cargarPacientesAtendidos();

        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> salir());
        menuArchivo.add(itemSalir);
        menuBar.add(menuArchivo);

        JMenu menuPacientes = new JMenu("Pacientes");
        JMenuItem itemNuevoPaciente = new JMenuItem("Nuevo Paciente");
        itemNuevoPaciente.addActionListener(e -> abrirVentanaNuevoPaciente());
        menuPacientes.add(itemNuevoPaciente);

        JMenuItem itemAtenderPaciente = new JMenuItem("Atender Paciente");
        itemAtenderPaciente.addActionListener(e -> atenderPaciente());
        menuPacientes.add(itemAtenderPaciente);

        JMenuItem itemMostrarSiguiente = new JMenuItem("Mostrar Siguiente Paciente");
        itemMostrarSiguiente.addActionListener(e -> mostrarSiguientePaciente());
        menuPacientes.add(itemMostrarSiguiente);
        menuBar.add(menuPacientes);

        JMenu menuConsultas = new JMenu("Consultas");
        JMenuItem itemMostrarPorAtender = new JMenuItem("Mostrar Pacientes por Atender");
        itemMostrarPorAtender.addActionListener(e -> mostrarPacientesPorAtender());
        menuConsultas.add(itemMostrarPorAtender);

        JMenuItem itemBuscarAtendido = new JMenuItem("Buscar Paciente Atendido");
        itemBuscarAtendido.addActionListener(e -> buscarPaciente());
        menuConsultas.add(itemBuscarAtendido);

        JMenuItem itemReporte = new JMenuItem("Reporte de Pacientes Atendidos");
        itemReporte.addActionListener(e -> generarReporte());
        menuConsultas.add(itemReporte);
        menuBar.add(menuConsultas);

        setJMenuBar(menuBar);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        add(panelPrincipal);

        ImageIcon imagenOriginal = new ImageIcon("background.jpeg");
        JLabel etiquetaImagen = new JLabel();
        if (imagenOriginal.getImage() != null && imagenOriginal.getIconWidth() > 0) {
            Image imagenEscalada = imagenOriginal.getImage().getScaledInstance(800, 150, Image.SCALE_SMOOTH);
            etiquetaImagen.setIcon(new ImageIcon(imagenEscalada));
        } else {
            etiquetaImagen.setText("Imagen no encontrada: background.jpeg");
        }
        panelPrincipal.add(etiquetaImagen, BorderLayout.NORTH);

        areaTextoPacientes = new JTextArea();
        areaTextoPacientes.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaTextoPacientes);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        mostrarPacientesPorAtender();
    }

    private void abrirVentanaNuevoPaciente() {
        VentanaNuevoPaciente ventanaNuevo = new VentanaNuevoPaciente(this);
        ventanaNuevo.setVisible(true);
    }

    public void agregarPaciente(String nombre, int edad, String areaMedica, String tipoAtencion) {
        Paciente nuevoPaciente = new Paciente(proximoNumeroAtencion++, nombre, edad, areaMedica, tipoAtencion);
        listaPacientesPorAtender.add(nuevoPaciente);
        guardarPacientesPorAtender();
        mostrarPacientesPorAtender();
        JOptionPane.showMessageDialog(this, "Paciente registrado con exito.\nNumero de Atencion: " + nuevoPaciente.getNumeroAtencion());
    }

    private void atenderPaciente() {
        if (isListaPorAtenderVacia()) {
            JOptionPane.showMessageDialog(this, "No hay pacientes en la lista para atender.", "Lista Vacia", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Paciente pacienteAtendido = listaPacientesPorAtender.remove(0);
        String mensaje = "Atendiendo al paciente:\n\n" + pacienteAtendido.toString();
        JOptionPane.showMessageDialog(this, mensaje, "Paciente Atendido", JOptionPane.INFORMATION_MESSAGE);

        pacienteAtendido.setFechaAtencion(LocalDateTime.now());
        listaPacientesAtendidos.add(pacienteAtendido);

        guardarPacientesPorAtender();
        guardarPacientesAtendidos();

        mostrarPacientesPorAtender();
    }

    private void mostrarSiguientePaciente() {
        if (isListaPorAtenderVacia()) {
            areaTextoPacientes.setText("No hay pacientes por atender.");
            return;
        }
        areaTextoPacientes.setText("--- Proximo Paciente a ser Atendido ---\n\n");
        areaTextoPacientes.append(listaPacientesPorAtender.get(0).toString());
    }

    private void mostrarPacientesPorAtender() {
        areaTextoPacientes.setText("");
        if (isListaPorAtenderVacia()) {
            areaTextoPacientes.setText("No hay pacientes por atender.");
            return;
        }
        areaTextoPacientes.setText("--- Lista de Pacientes por Atender ---\n\n");
        for (Paciente p : listaPacientesPorAtender) {
            areaTextoPacientes.append(p.toString());
        }
    }

    private boolean isListaPorAtenderVacia() {
        return listaPacientesPorAtender.isEmpty();
    }

    private void buscarPaciente() {
        if (listaPacientesAtendidos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay pacientes atendidos para buscar.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] opciones = {"Nombre", "Numero de Atencion", "Area Medica"};
        String criterio = (String) JOptionPane.showInputDialog(this, "Seleccione el criterio de busqueda:",
                "Buscar Paciente Atendido", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (criterio == null) return;

        String valorBusqueda = JOptionPane.showInputDialog(this, "Ingrese el " + criterio + " a buscar:");

        if (valorBusqueda == null || valorBusqueda.trim().isEmpty()) return;

        ArrayList<Paciente> resultados = new ArrayList<>();
        switch (criterio) {
            case "Nombre":
                resultados = listaPacientesAtendidos.stream()
                        .filter(p -> p.getNombre().equalsIgnoreCase(valorBusqueda.trim()))
                        .collect(Collectors.toCollection(ArrayList::new));
                break;
            case "Numero de Atencion":
                try {
                    int num = Integer.parseInt(valorBusqueda.trim());
                    resultados = listaPacientesAtendidos.stream()
                            .filter(p -> p.getNumeroAtencion() == num)
                            .collect(Collectors.toCollection(ArrayList::new));
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Numero de atencion invalido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                break;
            case "Area Medica":
                resultados = listaPacientesAtendidos.stream()
                        .filter(p -> p.getAreaMedica().equalsIgnoreCase(valorBusqueda.trim()))
                        .collect(Collectors.toCollection(ArrayList::new));
                break;
        }

        areaTextoPacientes.setText("");
        if (resultados.isEmpty()) {
            areaTextoPacientes.setText("No se encontraron pacientes atendidos con ese criterio.");
        } else {
            areaTextoPacientes.setText("--- Resultados de la Busqueda ---\n\n");
            for (Paciente p : resultados) {
                areaTextoPacientes.append(p.toString());
            }
        }
    }

    private void generarReporte() {
        if (listaPacientesAtendidos.isEmpty()) {
            areaTextoPacientes.setText("No hay pacientes atendidos para generar un reporte.");
            return;
        }

        StringBuilder reporte = new StringBuilder();
        reporte.append("--- Reporte de Pacientes Atendidos ---\n\n");

        Map<LocalDate, List<Paciente>> pacientesPorDia = listaPacientesAtendidos.stream()
            .collect(Collectors.groupingBy(p -> p.getFechaAtencion().toLocalDate()));

        DateTimeFormatter formatterFecha = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy");

        pacientesPorDia.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> {
                LocalDate dia = entry.getKey();
                List<Paciente> pacientesDelDia = entry.getValue();

                reporte.append("Dia ").append(dia.format(formatterFecha)).append("\n");

                Map<String, Long> conteoPorEspecialidad = pacientesDelDia.stream()
                    .collect(Collectors.groupingBy(Paciente::getAreaMedica, Collectors.counting()));

                conteoPorEspecialidad.forEach((especialidad, cantidad) -> {
                    reporte.append(String.format("  %d pacientes de %s\n", cantidad, especialidad));
                });
                reporte.append("\n");
            });

        areaTextoPacientes.setText(reporte.toString());
    }

    private void salir() {
        JOptionPane.showMessageDialog(this, "Gracias por utilizar el Simulador UMAE. ¡Hasta pronto!", "Despedida", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    // --- Manejo de archivos ---
    private void guardarPacientesPorAtender() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivoPacientesPorAtender))) {
            pw.println(proximoNumeroAtencion);
            for (Paciente p : listaPacientesPorAtender) {
                pw.println(p.toPorAtenderFileString());
            }
        } catch (IOException e) { JOptionPane.showMessageDialog(this, "Error al guardar los pacientes por atender.", "Error de Archivo", JOptionPane.ERROR_MESSAGE); }
    }

    private void cargarPacientesPorAtender() {
        File archivo = new File(archivoPacientesPorAtender);
        if (archivo.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea = br.readLine();
                if (linea != null && !linea.isEmpty()) { proximoNumeroAtencion = Integer.parseInt(linea); }
                listaPacientesPorAtender.clear();
                while ((linea = br.readLine()) != null) {
                    Paciente p = Paciente.fromFileString(linea);
                    if (p != null) { listaPacientesPorAtender.add(p); }
                }
            } catch (IOException | NumberFormatException e) { JOptionPane.showMessageDialog(this, "Error al cargar los pacientes por atender.", "Error de Archivo", JOptionPane.ERROR_MESSAGE); }
        }
    }

    private void guardarPacientesAtendidos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivoPacientesAtendidos))) {
            for (Paciente p : listaPacientesAtendidos) {
                pw.println(p.toAtendidosFileString());
            }
        } catch (IOException e) { JOptionPane.showMessageDialog(this, "Error al guardar los pacientes atendidos.", "Error de Archivo", JOptionPane.ERROR_MESSAGE); }
    }

    private void cargarPacientesAtendidos() {
        File archivo = new File(archivoPacientesAtendidos);
        if (archivo.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                listaPacientesAtendidos.clear();
                String linea;
                while ((linea = br.readLine()) != null) {
                    Paciente p = Paciente.fromFileString(linea);
                    if (p != null) { listaPacientesAtendidos.add(p); }
                }
            } catch (IOException e) { JOptionPane.showMessageDialog(this, "Error al cargar los pacientes atendidos.", "Error de Archivo", JOptionPane.ERROR_MESSAGE); }
        }
    }
}
