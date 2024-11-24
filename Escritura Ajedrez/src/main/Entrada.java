package main;

import Piezas.Pieza;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Constructor de interacción con el ratón
public class Entrada extends MouseAdapter {

    Tablero tablero;

    public Entrada(Tablero tablero) {
        this.tablero = tablero;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int col = e.getX() / tablero.tamanoCasilla;
        int fila = e.getY() / tablero.tamanoCasilla;

        Pieza piezaXY = tablero.obtenerPieza(col, fila);
        if (piezaXY != null) {
            tablero.piezaSeleccionada = piezaXY;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (tablero.piezaSeleccionada != null) {
            tablero.piezaSeleccionada.xPos = e.getX() - tablero.tamanoCasilla / 2;
            tablero.piezaSeleccionada.yPos = e.getY() - tablero.tamanoCasilla / 2;

            tablero.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int col = e.getX() / tablero.tamanoCasilla;
        int fila = e.getY() / tablero.tamanoCasilla;

        if (tablero.piezaSeleccionada != null) {
            Movimiento movimiento = new Movimiento(tablero, tablero.piezaSeleccionada, col, fila);
            if (tablero.esMovimientoValido(movimiento)) {
                tablero.realizarMovimiento(movimiento);
            } else {
                tablero.piezaSeleccionada.xPos = tablero.piezaSeleccionada.col * tablero.tamanoCasilla;
                tablero.piezaSeleccionada.yPos = tablero.piezaSeleccionada.row * tablero.tamanoCasilla;
            }
        }

        tablero.piezaSeleccionada = null;
        tablero.repaint();
    }
}