package main;

import Piezas.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import Control.RegistroMovimientos;

public class Tablero extends JPanel {
    private JTextArea areaMovimientos;
    public int tamanoCasilla = 85;
    int columnas = 8;
    int filas = 8;
    RegistroMovimientos registroMovimientos = new RegistroMovimientos(this);
    ArrayList<Pieza> listaPiezas = new ArrayList<>();

    public Pieza piezaSeleccionada;
    public boolean esTurnoBlanco = true;
    public boolean juegoTerminado = false;
    Entrada entrada = new Entrada(this);
    int contadorDesplazamientos = 0;
    public int casillaEnPassant = -1;
    public VerificadorJaque verificadorJaque = new VerificadorJaque(this);

    // Colores elegantes para el tablero
    private final Color colorCasillaClara = new Color(240, 217, 181);
    private final Color colorCasillaOscura = new Color(181, 136, 99);
    private final Color colorResaltado = new Color(130, 151, 105, 200);
    private final Color colorBorde = new Color(120, 80, 50);

    public Tablero() {
        this.setPreferredSize(new Dimension(columnas * tamanoCasilla, filas * tamanoCasilla));
        this.addMouseListener(entrada);
        this.addMouseMotionListener(entrada);
        this.setBorder(BorderFactory.createLineBorder(colorBorde, 3));
        this.setBackground(colorBorde);
        agregarPiezas();
    }



    public Pieza obtenerPieza(int col, int fila) {
        for (Pieza pieza : listaPiezas) {
            if (pieza.col == col && pieza.row == fila) {
                return pieza;
            }
        }
        return null;
    }

    public void realizarMovimiento(Movimiento movimiento) {
        // Si es un peón
        if (movimiento.pieza.nombre.equals("Peón")) {
            moverPeon(movimiento);
        } else if (movimiento.pieza.nombre.equals("Rey")) {
            moverRey(movimiento);
        }

        // Actualizar posición de la pieza
        movimiento.pieza.col = movimiento.nuevaCol;
        movimiento.pieza.row = movimiento.nuevaRow;
        movimiento.pieza.xPos = movimiento.nuevaCol * tamanoCasilla;
        movimiento.pieza.yPos = movimiento.nuevaRow * tamanoCasilla;
        movimiento.pieza.esPrimerMovimiento = false;

        // Capturar pieza si es necesario
        capturar(movimiento.captura);

        // Registrar el movimiento
        registroMovimientos.registrarMovimiento(movimiento);
        contadorDesplazamientos++;

        // Cambiar turno y actualizar estado
        esTurnoBlanco = !esTurnoBlanco;
        actualizarEstadoJuego();

        // Actualizar el área de texto si existe
        if (areaMovimientos != null) {
            areaMovimientos.setText(registroMovimientos.obtenerPGN());
            areaMovimientos.setCaretPosition(areaMovimientos.getDocument().getLength());
        }
    }

    private void moverRey(Movimiento movimiento) {
        if (Math.abs(movimiento.pieza.col - movimiento.nuevaCol) == 2) {
            Pieza torre = (movimiento.pieza.col < movimiento.nuevaCol) ? obtenerPieza(7, movimiento.pieza.row) : obtenerPieza(0, movimiento.pieza.row);
            torre.col = (movimiento.pieza.col < movimiento.nuevaCol) ? 5 : 3;
            torre.xPos = torre.col * tamanoCasilla;
        }
    }

    private void moverPeon(Movimiento movimiento) {
        int indiceColor = movimiento.pieza.esBlanca ? 1 : -1;

        // En passant
        if (obtenerNumeroCasilla(movimiento.nuevaCol, movimiento.nuevaRow) == casillaEnPassant) {
            movimiento.captura = obtenerPieza(movimiento.nuevaCol, movimiento.nuevaRow + indiceColor);
        }
        if (Math.abs(movimiento.pieza.row - movimiento.nuevaRow) == 2) {
            casillaEnPassant = obtenerNumeroCasilla(movimiento.nuevaCol, movimiento.nuevaRow + indiceColor);
        } else {
            casillaEnPassant = -1;
        }

        // Promociones
        indiceColor = movimiento.pieza.esBlanca ? 0 : 7;
        if (movimiento.nuevaRow == indiceColor) {
            promoverPeon(movimiento);
        }
    }

    private void promoverPeon(Movimiento movimiento) {
        listaPiezas.add(new Reina(this, movimiento.nuevaCol, movimiento.nuevaRow, movimiento.pieza.esBlanca));
        capturar(movimiento.pieza);
    }

    public void capturar(Pieza pieza) {
        listaPiezas.remove(pieza);
    }

    public boolean esMovimientoValido(Movimiento movimiento) {
        if (juegoTerminado) return false;
        if (movimiento.pieza.esBlanca != esTurnoBlanco) return false;
        if (mismoEquipo(movimiento.pieza, movimiento.captura)) return false;
        if (!movimiento.pieza.esMovimientoValido(movimiento.nuevaCol, movimiento.nuevaRow)) return false;
        if (movimiento.pieza.movimientoColisionaConPieza(movimiento.nuevaCol, movimiento.nuevaRow)) return false;
        if (verificadorJaque.esReyEnJaque(movimiento)) return false;

        return true;
    }
    public boolean mismoEquipo(Pieza p1, Pieza p2) {
        if (p1 == null || p2 == null) return false;
        return p1.esBlanca == p2.esBlanca;
    }

    public int obtenerNumeroCasilla(int col, int fila) {
        return fila * columnas + col;
    }

    public Pieza encontrarRey(boolean esBlanco) {
        for (Pieza pieza : listaPiezas) {
            if (esBlanco == pieza.esBlanca && pieza.nombre.equals("Rey")) {
                return pieza;
            }
        }
        return null;
    }
    public void asignarAreaMovimientos(JTextArea area) {
        this.areaMovimientos = area;
    }


    public void agregarPiezas() {
        // Piezas negras
        listaPiezas.add(new Caballo(this, 1, 0, false));
        listaPiezas.add(new Caballo(this, 6, 0, false));
        listaPiezas.add(new Torre(this, 0, 0, false));
        listaPiezas.add(new Torre(this, 7, 0, false));
        listaPiezas.add(new Alfil(this, 2, 0, false));
        listaPiezas.add(new Alfil(this, 5, 0, false));
        listaPiezas.add(new Reina(this, 3, 0, false));
        listaPiezas.add(new Rey(this, 4, 0, false));
        for (int i = 0; i < columnas; i++) {
            listaPiezas.add(new Peon(this, i, 1, false));
        }

        // Piezas blancas
        listaPiezas.add(new Caballo(this, 1, 7, true));
        listaPiezas.add(new Caballo(this, 6, 7, true));
        listaPiezas.add(new Torre(this, 0, 7, true));
        listaPiezas.add(new Torre(this, 7, 7, true));
        listaPiezas.add(new Alfil(this, 2, 7, true));
        listaPiezas.add(new Alfil(this, 5, 7, true));
        listaPiezas.add(new Reina(this, 3, 7, true));
        listaPiezas.add(new Rey(this, 4, 7, true));
        for (int i = 0; i < columnas; i++) {
            listaPiezas.add(new Peon(this, i, 6, true));
        }
    }

    private void actualizarEstadoJuego() {
        Pieza rey = encontrarRey(esTurnoBlanco);
        if (verificadorJaque.esJuegoTerminado(rey)) {
            if (verificadorJaque.esReyEnJaque(new Movimiento(this, rey, rey.col, rey.row))) {
                System.out.println(esTurnoBlanco ? "Las negras ganan" : "Las blancas ganan");
            } else {
                System.out.println("Empate");
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Configurar renderizado para mejor calidad
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Pintar el tablero
        for (int r = 0; r < filas; r++) {
            for (int c = 0; c < columnas; c++) {
                g2d.setColor((c + r) % 2 == 0 ? colorCasillaClara : colorCasillaOscura);
                g2d.fillRect(c * tamanoCasilla, r * tamanoCasilla, tamanoCasilla, tamanoCasilla);
            }
        }

        // Pintar los resaltados con efecto de transparencia
        if (piezaSeleccionada != null) {
            for (int r = 0; r < filas; r++) {
                for (int c = 0; c < columnas; c++) {
                    if (esMovimientoValido(new Movimiento(this, piezaSeleccionada, c, r))) {
                        g2d.setColor(colorResaltado);
                        g2d.fillRect(c * tamanoCasilla, r * tamanoCasilla, tamanoCasilla, tamanoCasilla);
                    }
                }
            }
        }

        // Pintar las piezas con sombras suaves
        for (Pieza pieza : listaPiezas) {
            pieza.pintar(g2d);
        }

        // Dibujar coordenadas del tablero
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 14));
        for (int i = 0; i < 8; i++) {
            // Números (filas)
            g2d.setColor((i % 2 == 0) ? colorCasillaOscura : colorCasillaClara);
            g2d.drawString(String.valueOf(8 - i), 5, i * tamanoCasilla + 20);

            // Letras (columnas)
            g2d.setColor(((i + 7) % 2 == 0) ? colorCasillaOscura : colorCasillaClara);
            g2d.drawString(String.valueOf((char)('A' + i)), i * tamanoCasilla + tamanoCasilla - 15,
                    filas * tamanoCasilla - 5);
        }
    }

}
