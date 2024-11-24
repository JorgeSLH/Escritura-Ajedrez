package Control;

import Piezas.*;
import java.util.ArrayList;
import java.util.List;
import main.Movimiento;
import main.Tablero;
import main.VerificadorJaque;

public class RegistroMovimientos {
    private final Tablero tablero;
    public List<String> movimientos;
    private int contadorMovimientos;
    private StringBuilder movimientoActual;

    public RegistroMovimientos(Tablero tablero) {
        this.tablero = tablero;
        this.movimientos = new ArrayList<>();
        this.contadorMovimientos = 1;
        this.movimientoActual = new StringBuilder();
    }

    public void registrarMovimiento(Movimiento movimiento) {
        String movimientoPGN = convertirAPGN(movimiento);
        System.out.println("Registrando movimiento: " + movimientoPGN);

        // Agregar '+' para jaque o '#' para jaque mate
        if (tablero.verificadorJaque.esReyEnJaque(movimiento)) {
            Pieza reyOponente = tablero.encontrarRey(!movimiento.pieza.esBlanca);
            if (reyOponente != null && tablero.verificadorJaque.esJuegoTerminado(reyOponente)) {
                movimientoPGN += "#";
            } else {
                movimientoPGN += "+";
            }
        }

        // Manejar el registro de movimientos
        if (movimiento.pieza.esBlanca) {
            // Inicio de un nuevo turno
            movimientoActual = new StringBuilder();
            movimientoActual.append(contadorMovimientos).append(". ").append(movimientoPGN);
        } else {
            // Completar el turno con el movimiento de las negras
            movimientoActual.append(" ").append(movimientoPGN);
            movimientos.add(movimientoActual.toString());
            contadorMovimientos++;
        }
    }

    private String convertirAPGN(Movimiento movimiento) {
        // Manejar enroque
        if (esEnroqueLargo(movimiento)) {
            return "O-O-O";
        } else if (esEnroqueCorto(movimiento)) {
            return "O-O";
        }

        // Manejar movimientos regulares y capturas
        String notacionPieza = obtenerNotacionPieza(movimiento.pieza);
        String letraColumna = String.valueOf((char) ('a' + movimiento.nuevaCol));
        String simboloCaptura = (movimiento.captura != null) ? "x" : "";

        // Caso especial para capturas de peones
        if (movimiento.pieza.nombre.equalsIgnoreCase("peón") && movimiento.captura != null) {
            String letraColumnaInicio = String.valueOf((char) ('a' + movimiento.colAntigua));
            return letraColumnaInicio + simboloCaptura + letraColumna + (8 - movimiento.nuevaRow);
        }

        return notacionPieza + simboloCaptura + letraColumna + (8 - movimiento.nuevaRow);
    }



    public String obtenerPGN() {
        StringBuilder pgn = new StringBuilder();

        // Agregar todos los movimientos completados
        for (String movimiento : movimientos) {
            pgn.append(movimiento).append(" ");
        }

        // Agregar el movimiento actual si existe (solo blancas han movido)
        if (movimientoActual.length() > 0) {
            pgn.append(movimientoActual);
        }

        return pgn.toString().trim();
    }

    public void mostrarMovimientos() {
        System.out.println(obtenerPGN());
    }
    private boolean esEnroqueCorto(Movimiento movimiento) {
        return movimiento.pieza.nombre.equalsIgnoreCase("rey") &&
                Math.abs(movimiento.nuevaCol - movimiento.colAntigua) == 2 &&
                movimiento.nuevaCol > movimiento.colAntigua;
    }

    private boolean esEnroqueLargo(Movimiento movimiento) {
        return movimiento.pieza.nombre.equalsIgnoreCase("rey") &&
                Math.abs(movimiento.nuevaCol - movimiento.colAntigua) == 2 &&
                movimiento.nuevaCol < movimiento.colAntigua;
    }

    private String obtenerNotacionPieza(Pieza pieza) {
        switch (pieza.nombre.toLowerCase()) {
            case "rey": return "K";
            case "reina": return "Q";
            case "torre": return "R";
            case "alfil": return "B";
            case "caballo": return "N";
            default: return "";
        }
    }
}