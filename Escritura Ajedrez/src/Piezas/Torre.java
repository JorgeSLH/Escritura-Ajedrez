package Piezas;

import main.Tablero;

import java.awt.image.BufferedImage;

public class Torre extends Pieza {
    public Torre(Tablero tablero, int col, int row, boolean esBlanca) {
        super(tablero);
        this.col = col;
        this.row = row;
        this.xPos = col * tablero.tamanoCasilla;
        this.yPos = row * tablero.tamanoCasilla;

        this.esBlanca = esBlanca;
        this.nombre = "Torre";

        this.sprite = hoja.getSubimage(4 * escalaHoja, esBlanca ? 0 : escalaHoja, escalaHoja, escalaHoja).getScaledInstance(tablero.tamanoCasilla, tablero.tamanoCasilla, BufferedImage.SCALE_SMOOTH);
    }

    @Override
    public boolean esMovimientoValido(int col, int row) {
        return this.col == col || this.row == row;
    }

    @Override
    public boolean movimientoColisionaConPieza(int col, int row) {
        // Izquierda
        if (this.col > col) {
            for (int c = this.col - 1; c > col; c--) {
                if (tablero.obtenerPieza(c, this.row) != null) {
                    return true;
                }
            }
        }
        // Derecha
        if (this.col < col) {
            for (int c = this.col + 1; c < col; c++) {
                if (tablero.obtenerPieza(c, this.row) != null) {
                    return true;
                }
            }
        }
        // Arriba
        if (this.row > row) {
            for (int r = this.row - 1; r > row; r--) {
                if (tablero.obtenerPieza(this.col, r) != null) {
                    return true;
                }
            }
        }
        // Abajo
        if (this.row < row) {
            for (int r = this.row + 1; r < row; r++) {
                if (tablero.obtenerPieza(this.col, r) != null) {
                    return true;
                }
            }
        }
        return false;
    }
}