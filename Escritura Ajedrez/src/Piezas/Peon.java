package Piezas;

import main.Tablero;

import java.awt.image.BufferedImage;

public class Peon extends Pieza {
    public Peon(Tablero tablero, int col, int row, boolean esBlanca) {
        super(tablero);
        this.col = col;
        this.row = row;
        this.xPos = col * tablero.tamanoCasilla;
        this.yPos = row * tablero.tamanoCasilla;

        this.esBlanca = esBlanca;
        this.nombre = "Peón";

        this.sprite = hoja.getSubimage(5 * escalaHoja, esBlanca ? 0 : escalaHoja, escalaHoja, escalaHoja).getScaledInstance(tablero.tamanoCasilla, tablero.tamanoCasilla, BufferedImage.SCALE_SMOOTH);
    }

    @Override
    public boolean esMovimientoValido(int col, int row) {
        int indiceColor = esBlanca ? 1 : -1;

        // Mover peón 1
        if (this.col == col && row == this.row - indiceColor && tablero.obtenerPieza(col, row) == null) {
            return true;
        }

        // Mover peón 2
        if (esPrimerMovimiento && this.col == col && row == this.row - indiceColor * 2 && tablero.obtenerPieza(col, row) == null && tablero.obtenerPieza(col, row + indiceColor) == null) {
            return true;
        }

        // Captura izquierda
        if (col == this.col - 1 && row == this.row - indiceColor && tablero.obtenerPieza(col, row) != null) {
            return true;
        }

        // Captura derecha
        if (col == this.col + 1 && row == this.row - indiceColor && tablero.obtenerPieza(col, row) != null) {
            return true;
        }

        // En passant izquierda
        if (tablero.obtenerNumeroCasilla(col, row) == tablero.casillaEnPassant && col == this.col - 1 && row == this.row - indiceColor && tablero.obtenerPieza(col, row + indiceColor) != null) {
            return true;
        }
        // En passant derecha
        if (tablero.obtenerNumeroCasilla(col, row) == tablero.casillaEnPassant && col == this.col + 1 && row == this.row - indiceColor && tablero.obtenerPieza(col, row + indiceColor) != null) {
            return true;
        }
        return false;
    }
}
