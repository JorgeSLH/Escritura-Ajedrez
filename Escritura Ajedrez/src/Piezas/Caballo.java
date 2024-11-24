package Piezas;

import main.Tablero;

import java.awt.image.BufferedImage;

public class Caballo extends Pieza {
    public Caballo(Tablero tablero, int col, int row, boolean esBlanca) {
        super(tablero);
        this.col = col;
        this.row = row;
        this.xPos = col * tablero.tamanoCasilla;
        this.yPos = row * tablero.tamanoCasilla;

        this.esBlanca = esBlanca;
        this.nombre = "Caballo";

        this.sprite = hoja.getSubimage(3 * escalaHoja, esBlanca ? 0 : escalaHoja, escalaHoja, escalaHoja).getScaledInstance(tablero.tamanoCasilla, tablero.tamanoCasilla, BufferedImage.SCALE_SMOOTH);
    }

    @Override
    public boolean esMovimientoValido(int col, int row) {
        return Math.abs(col - this.col) * Math.abs(row - this.row) == 2;
    }
}