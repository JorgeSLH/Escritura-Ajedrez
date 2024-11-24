package Control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MarcoMetadatos extends JFrame {
    private MetadatosJuego metadatos;

    // Campos del formulario
    private static JTextField campoEvento;
    private static JTextField campoSitio;
    private static JTextField campoFecha;
    private static JTextField campoRonda;
    private static JTextField campoJugadorBlanco;
    private static JTextField campoJugadorNegro;
    private static JTextField campoResultado;
    private JTextArea areaMetadatos;

    public MarcoMetadatos(MetadatosJuego metadatos) {
        this.metadatos = metadatos;
        initUI();
    }

    private void initUI() {
        setTitle("Metadatos de la Partida de Ajedrez");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel del formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(8, 2, 10, 10));

        // Agregar etiquetas y campos
        panelFormulario.add(new JLabel("Evento:"));
        campoEvento = new JTextField(metadatos.obtenerEvento());
        panelFormulario.add(campoEvento);

        panelFormulario.add(new JLabel("Sitio:"));
        campoSitio = new JTextField(metadatos.obtenerSitio());
        panelFormulario.add(campoSitio);

        panelFormulario.add(new JLabel("Fecha (YYYY.MM.DD):"));
        campoFecha = new JTextField(metadatos.obtenerFecha());
        panelFormulario.add(campoFecha);

        panelFormulario.add(new JLabel("Ronda:"));
        campoRonda = new JTextField(metadatos.obtenerRonda());
        panelFormulario.add(campoRonda);

        panelFormulario.add(new JLabel("Jugador Blanco:"));
        campoJugadorBlanco = new JTextField(metadatos.obtenerJugadorBlanco());
        panelFormulario.add(campoJugadorBlanco);

        panelFormulario.add(new JLabel("Jugador Negro:"));
        campoJugadorNegro = new JTextField(metadatos.obtenerJugadorNegro());
        panelFormulario.add(campoJugadorNegro);

        panelFormulario.add(new JLabel("Resultado:"));
        campoResultado = new JTextField(metadatos.obtenerResultado());
        panelFormulario.add(campoResultado);

        // Botones
        JButton botonGuardar = new JButton("Guardar");
        JButton botonMostrar = new JButton("Mostrar Metadatos");

        panelFormulario.add(botonGuardar);
        panelFormulario.add(botonMostrar);

        // Área de visualización de metadatos
        areaMetadatos = new JTextArea();
        areaMetadatos.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaMetadatos);

        // Agregar componentes al marco
        add(panelFormulario, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Acciones de los botones
        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarMetadatos();
            }
        });

        botonMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarMetadatos();
            }
        });
    }

    private void guardarMetadatos() {
        metadatos.establecerEvento(campoEvento.getText());
        metadatos.establecerSitio(campoSitio.getText());
        metadatos.establecerFecha(campoFecha.getText());
        metadatos.establecerRonda(campoRonda.getText());
        metadatos.establecerJugadorBlanco(campoJugadorBlanco.getText());
        metadatos.establecerJugadorNegro(campoJugadorNegro.getText());
        metadatos.establecerResultado(campoResultado.getText());
        JOptionPane.showMessageDialog(this, "¡Metadatos guardados exitosamente!");
    }

    private void mostrarMetadatos() {
        areaMetadatos.setText(metadatos.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Inicializar metadatos con valores por defecto
            MetadatosJuego metadatos = new MetadatosJuego("", "", "", "", "", "", "");
            MarcoMetadatos marco = new MarcoMetadatos(metadatos);
            marco.setVisible(true);
        });
    }
}