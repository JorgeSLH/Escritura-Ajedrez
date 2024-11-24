// Principal.java
package main;

import Control.MetadatosJuego;
import Control.EscritorPGN;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class Principal {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Crear un nuevo JFrame para contener el formulario de metadatos y el tablero de ajedrez
        JFrame marco = new JFrame("Chess Master");
        marco.getContentPane().setBackground(new Color(40, 40, 40));
        marco.setLayout(new BorderLayout());
        marco.setMinimumSize(new Dimension(1200, 800));
        marco.setLocationRelativeTo(null);

        // Recoger metadatos del usuario
        MetadatosJuego metadatos = pedirMetadatos(marco);
        if (metadatos == null) {
            System.out.println("La entrada de metadatos fue cancelada.");
            return;
        }

        // Crear el tablero de ajedrez y el registro de movimientos
        Tablero tablero = new Tablero();

        // Panel contenedor con padding
        JPanel contenedor = new JPanel(new BorderLayout(20, 0)); // Añadido espacio entre componentes
        contenedor.setBackground(new Color(40, 40, 40));
        contenedor.setBorder(new EmptyBorder(20, 20, 20, 20));


        // Panel para el tablero con borde elevado y sombra
        JPanel panelTablero = new JPanel(new BorderLayout());
        panelTablero.setBackground(new Color(30, 30, 30));
        panelTablero.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60), 2),
                new EmptyBorder(10, 10, 10, 10)
        ));
        panelTablero.add(tablero, BorderLayout.CENTER);

        contenedor.add(panelTablero, BorderLayout.CENTER);
        marco.add(contenedor, BorderLayout.CENTER);

        JPanel panelMovimientos = new JPanel(new BorderLayout());
        panelMovimientos.setBackground(new Color(30, 30, 30));
        panelMovimientos.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60), 2),
                new EmptyBorder(10, 10, 10, 10)
        ));
        panelMovimientos.setPreferredSize(new Dimension(250, 0));


//MOVIMIENTOS JPANEL
        JLabel tituloMovimientos = new JLabel("Movimientos PGN");
        tituloMovimientos.setForeground(Color.WHITE);
        tituloMovimientos.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tituloMovimientos.setBorder(new EmptyBorder(0, 0, 10, 0));
        tituloMovimientos.setHorizontalAlignment(SwingConstants.CENTER);
        panelMovimientos.add(tituloMovimientos, BorderLayout.NORTH);


        // Área de texto para mostrar los movimientos
        JTextArea areaMovimientos = new JTextArea();
        areaMovimientos.setEditable(false);
        areaMovimientos.setBackground(new Color(45, 45, 45));
        areaMovimientos.setForeground(Color.WHITE);
        areaMovimientos.setFont(new Font("Consolas", Font.PLAIN, 14));
        areaMovimientos.setBorder(new EmptyBorder(10, 10, 10, 10));
// Configurar el wrap de líneas
        areaMovimientos.setLineWrap(true);
        areaMovimientos.setWrapStyleWord(true);

        // Scroll para el área de movimientos
        JScrollPane scrollMovimientos = new JScrollPane(areaMovimientos);
        scrollMovimientos.setBorder(null);
        scrollMovimientos.getViewport().setBackground(new Color(45, 45, 45));
        // Configurar las políticas de scroll
        scrollMovimientos.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollMovimientos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        // Personalizar la barra de desplazamiento

        scrollMovimientos.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(70, 70, 70);
                this.trackColor = new Color(45, 45, 45);
            }
        });

        panelMovimientos.add(scrollMovimientos, BorderLayout.CENTER);
        tablero.asignarAreaMovimientos(areaMovimientos);
        contenedor.add(panelTablero, BorderLayout.CENTER);
        contenedor.add(panelMovimientos, BorderLayout.EAST);
        marco.add(contenedor, BorderLayout.CENTER);
        marco.setVisible(true);
        //TERMINACION DE MOVIMIENTOS


        JMenuBar barraMenu = new JMenuBar();
        barraMenu.setBackground(new Color(50, 50, 50));
        barraMenu.setBorder(BorderFactory.createEmptyBorder());

        JMenu menuArchivo = new JMenu("Archivo");
        menuArchivo.setForeground(Color.WHITE);
        JMenuItem itemGuardarPGN = new JMenuItem("Guardar PGN");
        itemGuardarPGN.setBackground(new Color(50, 50, 50));
        itemGuardarPGN.setForeground(Color.WHITE);

        itemGuardarPGN.addActionListener(e -> {
            JFileChooser selectorArchivos = new JFileChooser();
            selectorArchivos.setDialogTitle("Guardar Archivo PGN");
            int seleccionUsuario = selectorArchivos.showSaveDialog(marco);
            if (seleccionUsuario == JFileChooser.APPROVE_OPTION) {
                File archivo = selectorArchivos.getSelectedFile();
                EscritorPGN escritorPGN = new EscritorPGN(metadatos, tablero.registroMovimientos);
                escritorPGN.escribirEnArchivo(archivo.getAbsolutePath());
            }
        });

        menuArchivo.add(itemGuardarPGN);
        barraMenu.add(menuArchivo);
        marco.setJMenuBar(barraMenu);

        marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        marco.setVisible(true);
    }

    private static MetadatosJuego pedirMetadatos(JFrame marco) {
        // Panel principal con diseño elegante
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 15, 15));
        panel.setBackground(new Color(40, 40, 40));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Estilo para las etiquetas y campos
        Font fuenteEtiqueta = new Font("Segoe UI", Font.BOLD, 14);
        Font fuenteCampo = new Font("Segoe UI", Font.PLAIN, 14);

        // Crear campos de entrada con estilo
        JTextField campoEvento = crearCampoEstilizado("Evento Predeterminado", fuenteCampo);
        JTextField campoSitio = crearCampoEstilizado("Sitio Predeterminado", fuenteCampo);
        JTextField campoFecha = crearCampoEstilizado("2024.11.20", fuenteCampo);
        JTextField campoRonda = crearCampoEstilizado("1", fuenteCampo);
        JTextField campoJugadorBlanco = crearCampoEstilizado("Jugador Blanco", fuenteCampo);
        JTextField campoJugadorNegro = crearCampoEstilizado("Jugador Negro", fuenteCampo);
        JTextField campoResultado = crearCampoEstilizado("*", fuenteCampo);

        // Agregar etiquetas y campos al panel
        agregarCampoConEtiqueta(panel, "Evento:", campoEvento, fuenteEtiqueta);
        agregarCampoConEtiqueta(panel, "Sitio:", campoSitio, fuenteEtiqueta);
        agregarCampoConEtiqueta(panel, "Fecha:", campoFecha, fuenteEtiqueta);
        agregarCampoConEtiqueta(panel, "Ronda:", campoRonda, fuenteEtiqueta);
        agregarCampoConEtiqueta(panel, "Jugador Blanco:", campoJugadorBlanco, fuenteEtiqueta);
        agregarCampoConEtiqueta(panel, "Jugador Negro:", campoJugadorNegro, fuenteEtiqueta);
        agregarCampoConEtiqueta(panel, "Resultado:", campoResultado, fuenteEtiqueta);

        // Configurar el diálogo personalizado
        UIManager.put("OptionPane.background", new Color(40, 40, 40));
        UIManager.put("Panel.background", new Color(40, 40, 40));

        // Mostrar el diálogo
        int opcion = JOptionPane.showConfirmDialog(marco, panel, "Configuración de Partida",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        // Si se presiona OK, devolver los metadatos
        if (opcion == JOptionPane.OK_OPTION) {
            return new MetadatosJuego(
                    campoEvento.getText(),
                    campoSitio.getText(),
                    campoFecha.getText(),
                    campoRonda.getText(),
                    campoJugadorBlanco.getText(),
                    campoJugadorNegro.getText(),
                    campoResultado.getText()
            );
        }

        return null;
    }

    private static JTextField crearCampoEstilizado(String textoDefault, Font fuente) {
        JTextField campo = new JTextField(textoDefault);
        campo.setFont(fuente);
        campo.setForeground(Color.WHITE);
        campo.setBackground(new Color(60, 60, 60));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 80)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return campo;
    }

    private static void agregarCampoConEtiqueta(JPanel panel, String textoEtiqueta,
                                                JTextField campo, Font fuenteEtiqueta) {
        JLabel etiqueta = new JLabel(textoEtiqueta);
        etiqueta.setFont(fuenteEtiqueta);
        etiqueta.setForeground(Color.WHITE);
        panel.add(etiqueta);
        panel.add(campo);
    }
}
