package Control;

public class MetadatosJuego {
    private String evento;
    private String sitio;
    private String fecha;
    private String ronda;
    private String jugadorBlanco;
    private String jugadorNegro;
    private String resultado;

    public MetadatosJuego(String texto, String textoSitio, String textoFecha, String textoRonda, String textoJugadorBlanco, String textoJugadorNegro, String textoResultado) {
        this.evento = texto;
        this.sitio = textoSitio;
        this.fecha = textoFecha;
        this.ronda = textoRonda;
        this.jugadorBlanco = textoJugadorBlanco;
        this.jugadorNegro = textoJugadorNegro;
        this.resultado = textoResultado; // Resultado por defecto para partidas en curso
    }

    // Métodos Getters y Setters


    public void establecerRonda(String ronda) {
        this.ronda = ronda;
    }

    public String obtenerJugadorBlanco() {
        return jugadorBlanco;
    }

    public void establecerJugadorBlanco(String jugadorBlanco) {
        this.jugadorBlanco = jugadorBlanco;
    }

    public String obtenerJugadorNegro() {
        return jugadorNegro;
    }

    public void establecerJugadorNegro(String jugadorNegro) {
        this.jugadorNegro = jugadorNegro;
    }

    public String obtenerResultado() {
        return resultado;
    }
    public String obtenerEvento() {
        return evento;
    }

    public void establecerEvento(String evento) {
        this.evento = evento;
    }

    public String obtenerSitio() {
        return sitio;
    }

    public void establecerSitio(String sitio) {
        this.sitio = sitio;
    }

    public String obtenerFecha() {
        return fecha;
    }

    public void establecerFecha(String fecha) {
        this.fecha = fecha;
    }

    public String obtenerRonda() {
        return ronda;
    }
    public void establecerResultado(String resultado) {
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return "[Evento \"" + evento + "\"]\n" +
                "[Sitio \"" + sitio + "\"]\n" +
                "[Fecha \"" + fecha + "\"]\n" +
                "[Ronda \"" + ronda + "\"]\n" +
                "[Blanco \"" + jugadorBlanco + "\"]\n" +
                "[Negro \"" + jugadorNegro + "\"]\n" +
                "[Resultado \"" + resultado + "\"]";
    }
}