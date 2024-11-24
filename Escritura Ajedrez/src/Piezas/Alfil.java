package Piezas;

import main.Tablero;

import java.awt.image.BufferedImage;

public class Alfil extends Pieza {
    public Alfil(Tablero tablero, int col, int row, boolean esBlanca) {
        super(tablero);
        this.col = col;
        this.row = row;
        this.xPos = col * tablero.tamanoCasilla;
        this.yPos = row * tablero.tamanoCasilla;

        this.esBlanca = esBlanca;
        this.nombre = "Alfil";

        this.sprite = hoja.getSubimage(2 * escalaHoja, esBlanca ? 0 : escalaHoja, escalaHoja, escalaHoja).getScaledInstance(tablero.tamanoCasilla, tablero.tamanoCasilla, BufferedImage.SCALE_SMOOTH);
    }

    @Override
    public boolean esMovimientoValido(int col, int row) {
        return Math.abs(this.col - col) == Math.abs(this.row - row);
    }

    @Override
    public boolean movimientoColisionaConPieza(int col, int row) {
        // Diagonal superior izquierda
        if (this.col > col && this.row > row) {
            for (int i = 1; i < Math.abs(this.col - col); i++) {
                if (tablero.obtenerPieza(this.col - i, this.row - i) != null) {
                    return true;
                }
            }
        }
        // Diagonal superior derecha
        if (this.col < col && this.row > row) {
            for (int i = 1; i < Math.abs(this.col - col); i++) {
                if (tablero.obtenerPieza(this.col + i, this.row - i) != null) {
                    return true;
                }
            }
        }
        // Diagonal inferior izquierda
        if (this.col > col && this.row < row) {
            for (int i = 1; i < Math.abs(this.col - col); i++) {
                if (tablero.obtenerPieza(this.col - i, this.row + i) != null) {
                    return true;
                }
            }
        }
        // Diagonal inferior derecha
        if (this.col < col && this.row < row) {
            for (int i = 1; i < Math.abs(this.col - col); i++) {
                if (tablero.obtenerPieza(this.col + i, this.row + i) != null) {
                    return true;
                }
            }
        }
        return false;
    }
}