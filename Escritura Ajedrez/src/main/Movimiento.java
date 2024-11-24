package main;

import Piezas.*;

public class Movimiento {
    public int colAntigua;
    public int filaAntigua;
    public int nuevaCol;
    public int nuevaRow;
    public boolean esCaptura;

    public Pieza pieza;
    public Pieza captura;

    public Movimiento(Tablero tablero, Pieza pieza, int nuevaCol, int nuevaFila) {
        this.colAntigua = pieza.col;
        this.filaAntigua = pieza.row;
        this.nuevaCol = nuevaCol;
        this.nuevaRow = nuevaFila;

        this.pieza = pieza;
        this.captura = tablero.obtenerPieza(nuevaCol, nuevaFila);

        esCaptura = captura != null;
    }
}