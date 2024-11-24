package Piezas;

import main.Tablero;
import main.Movimiento;

import java.awt.image.BufferedImage;

public class Rey extends Pieza {
    public Rey(Tablero tablero, int col, int row, boolean esBlanca) {
        super(tablero);
        this.col = col;
        this.row = row;
        this.xPos = col * tablero.tamanoCasilla;
        this.yPos = row * tablero.tamanoCasilla;

        this.esBlanca = esBlanca;
        this.nombre = "Rey";

        this.sprite = hoja.getSubimage(0, esBlanca ? 0 : escalaHoja, escalaHoja, escalaHoja).getScaledInstance(tablero.tamanoCasilla, tablero.tamanoCasilla, BufferedImage.SCALE_SMOOTH);
    }

    @Override
    public boolean esMovimientoValido(int col, int row) {
        return Math.abs((col - this.col) * (row - this.row)) == 1 || Math.abs(col - this.col) + Math.abs(row - this.row) == 1 || puedeEnrocar(col, row);
    }

    public boolean puedeEnrocar(int col, int row) {
        if (this.row == row) {
            if (col == 6) {
                Pieza torre = tablero.obtenerPieza(7, row);
                if (torre != null && torre.esPrimerMovimiento && esPrimerMovimiento) {
                    return tablero.obtenerPieza(5, row) == null &&
                            tablero.obtenerPieza(6, row) == null &&
                            !tablero.verificadorJaque.esReyEnJaque(new Movimiento(tablero, this, 5, row));
                }
            } else if (col == 2) {
                Pieza torre = tablero.obtenerPieza(0, row);
                if (torre != null && torre.esPrimerMovimiento && esPrimerMovimiento) {
                    return tablero.obtenerPieza(3, row) == null &&
                            tablero.obtenerPieza(2, row) == null &&
                            tablero.obtenerPieza(1, row) == null &&
                            !tablero.verificadorJaque.esReyEnJaque(new Movimiento(tablero, this, 3, row));
                }
            }
        }
        return false;
    }
}