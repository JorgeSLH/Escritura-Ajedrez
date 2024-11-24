package main;

import Piezas.*;

public class VerificadorJaque {

    Tablero tablero;

    public VerificadorJaque(Tablero tablero) {
        this.tablero = tablero;
    }


    private boolean golpeadoPorAlfil(int col, int fila, Pieza rey, int colRey, int filaRey, int valCol, int valFila) {
        for (int i = 1; i < 8; i++) {
            if (colRey - (i * valCol) == col && filaRey - (i * valFila) == fila) {
                break;
            }
            Pieza pieza = tablero.obtenerPieza(colRey - (i * valCol), filaRey - (i * valFila));
            if (pieza != null && pieza != tablero.piezaSeleccionada) {
                if (!tablero.mismoEquipo(pieza, rey) && (pieza.nombre.equals("Alfil") || pieza.nombre.equals("Reina"))) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private boolean golpeadoPorCaballo(int col, int fila, Pieza rey, int colRey, int filaRey) {
        return verificarCaballo(tablero.obtenerPieza(colRey - 1, filaRey - 2), rey, col, fila) ||
                verificarCaballo(tablero.obtenerPieza(colRey + 1, filaRey - 2), rey, col, fila) ||
                verificarCaballo(tablero.obtenerPieza(colRey + 2, filaRey - 1), rey, col, fila) ||
                verificarCaballo(tablero.obtenerPieza(colRey + 2, filaRey + 1), rey, col, fila) ||
                verificarCaballo(tablero.obtenerPieza(colRey + 1, filaRey + 2), rey, col, fila) ||
                verificarCaballo(tablero.obtenerPieza(colRey - 1, filaRey + 2), rey, col, fila) ||
                verificarCaballo(tablero.obtenerPieza(colRey - 2, filaRey + 1), rey, col, fila) ||
                verificarCaballo(tablero.obtenerPieza(colRey - 2, filaRey - 1), rey, col, fila);
    }

    private boolean verificarCaballo(Pieza p, Pieza k, int col, int fila) {
        return p != null && !tablero.mismoEquipo(p, k) && p.nombre.equals("Caballo") && !(p.col == col && p.row == fila);
    }

    public boolean esReyEnJaque(Movimiento movimiento) {
        Pieza rey = tablero.encontrarRey(movimiento.pieza.esBlanca);
        assert rey != null;

        int colRey = rey.col;
        int filaRey = rey.row;

        if (tablero.piezaSeleccionada != null && tablero.piezaSeleccionada.nombre.equals("Rey")) {
            colRey = movimiento.nuevaCol;
            filaRey = movimiento.nuevaRow;
        }

        return golpeadoPorTorre(movimiento.nuevaCol, movimiento.nuevaRow, rey, colRey, filaRey, 0, 1) ||
                golpeadoPorTorre(movimiento.nuevaCol, movimiento.nuevaRow, rey, colRey, filaRey, 1, 0) ||
                golpeadoPorTorre(movimiento.nuevaCol, movimiento.nuevaRow, rey, colRey, filaRey, 0, -1) ||
                golpeadoPorTorre(movimiento.nuevaCol, movimiento.nuevaRow, rey, colRey, filaRey, -1, 0) ||

                golpeadoPorAlfil(movimiento.nuevaCol, movimiento.nuevaRow, rey, colRey, filaRey, -1, -1) ||
                golpeadoPorAlfil(movimiento.nuevaCol, movimiento.nuevaRow, rey, colRey, filaRey, 1, -1) ||
                golpeadoPorAlfil(movimiento.nuevaCol, movimiento.nuevaRow, rey, colRey, filaRey, 1, 1) ||
                golpeadoPorAlfil(movimiento.nuevaCol, movimiento.nuevaRow, rey, colRey, filaRey, -1, 1) ||

                golpeadoPorCaballo(movimiento.nuevaCol, movimiento.nuevaRow, rey, colRey, filaRey) ||
                golpeadoPorPeon(movimiento.nuevaCol, movimiento.nuevaRow, rey, colRey, filaRey) ||
                golpeadoPorRey(rey, colRey, filaRey);
    }

    private boolean golpeadoPorTorre(int col, int fila, Pieza rey, int colRey, int filaRey, int valCol, int valFila) {
        for (int i = 1; i < 8; i++) {
            if (colRey + (i * valCol) == col && filaRey + (i * valFila) == fila) {
                break;
            }
            Pieza pieza = tablero.obtenerPieza(colRey + (i * valCol), filaRey + (i * valFila));
            if (pieza != null && pieza != tablero.piezaSeleccionada) {
                if (!tablero.mismoEquipo(pieza, rey) && (pieza.nombre.equals("Torre") || pieza.nombre.equals("Reina"))) {
                    return true;
                }
                break;
            }
        }
        return false;
    }



    private boolean golpeadoPorRey(Pieza rey, int colRey, int filaRey) {
        return verificarRey(tablero.obtenerPieza(colRey - 1, filaRey - 1), rey) ||
                verificarRey(tablero.obtenerPieza(colRey + 1, filaRey - 1), rey) ||
                verificarRey(tablero.obtenerPieza(colRey, filaRey - 1), rey) ||
                verificarRey(tablero.obtenerPieza(colRey - 1, filaRey), rey) ||
                verificarRey(tablero.obtenerPieza(colRey + 1, filaRey), rey) ||
                verificarRey(tablero.obtenerPieza(colRey - 1, filaRey + 1), rey) ||
                verificarRey(tablero.obtenerPieza(colRey + 1, filaRey + 1), rey) ||
                verificarRey(tablero.obtenerPieza(colRey, filaRey + 1), rey);
    }

    private boolean verificarRey(Pieza p, Pieza k) {
        return p != null && !tablero.mismoEquipo(p, k) && p.nombre.equals("Rey");
    }

    private boolean golpeadoPorPeon(int col, int fila, Pieza rey, int colRey, int filaRey) {
        int valorColor = rey.esBlanca ? -1 : 1;
        return verificarPeon(tablero.obtenerPieza(colRey + 1, filaRey + valorColor), rey, col, fila) ||
                verificarPeon(tablero.obtenerPieza(colRey - 1, filaRey + valorColor), rey, col, fila);
    }

    private boolean verificarPeon(Pieza p, Pieza k, int col, int fila) {
        return p != null && !tablero.mismoEquipo(p, k) && p.nombre.equals("Peón");
    }

    public boolean esJuegoTerminado(Pieza rey) {
        for (Pieza pieza : tablero.listaPiezas) {
            if (tablero.mismoEquipo(pieza, rey)) {
                tablero.piezaSeleccionada = pieza == rey ? rey : null;
                for (int fila = 0; fila < tablero.filas; fila++) {
                    for (int col = 0; col < tablero.columnas; col++) {
                        Movimiento movimiento = new Movimiento(tablero, pieza, col, fila);
                        if (tablero.esMovimientoValido(movimiento)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}