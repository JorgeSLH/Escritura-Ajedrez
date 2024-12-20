package Piezas;

import main.Tablero;

import java.awt.image.BufferedImage;

public class Reina extends Pieza {
    public Reina(Tablero tablero, int col, int row, boolean esBlanca) {
        super(tablero);
        this.col = col;
        this.row = row;
        this.xPos = col * tablero.tamanoCasilla;
        this.yPos = row * tablero.tamanoCasilla;

        this.esBlanca = esBlanca;
        this.nombre = "Reina";

        this.sprite = hoja.getSubimage(1 * escalaHoja, esBlanca ? 0 : escalaHoja, escalaHoja, escalaHoja).getScaledInstance(tablero.tamanoCasilla, tablero.tamanoCasilla, BufferedImage.SCALE_SMOOTH);
    }

    @Override
    public boolean esMovimientoValido(int col, int row) {
        return this.col == col || this.row == row || Math.abs(this.col - col) == Math.abs(this.row - row);
    }

    @Override
    public boolean movimientoColisionaConPieza(int col, int row) {
        if (this.col == col || this.row == row) {
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
        } else {
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
        }
        return false;
    }
}