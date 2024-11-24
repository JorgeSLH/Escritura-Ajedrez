package Control;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class EscritorPGN {

    private MetadatosJuego metadatos;
    private RegistroMovimientos registroMovimientos;

    public EscritorPGN(MetadatosJuego metadatos, RegistroMovimientos registroMovimientos) {
        this.metadatos = metadatos;
        this.registroMovimientos = registroMovimientos;
    }

    /**
     * Escribir los datos PGN en un archivo.
     * @param nombreArchivo Nombre del archivo para guardar el PGN.
     */
    public void escribirEnArchivo(String nombreArchivo) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(nombreArchivo))) {
            // Escribir metadatos
            escritor.write(metadatos.toString());
            escritor.newLine();
            escritor.newLine();

            // Escribir movimientos
            String movimientosPGN = registroMovimientos.obtenerPGN();
            if (movimientosPGN.isEmpty()) {
                System.out.println("No hay movimientos para escribir."); // Mensaje de depuración
            } else {
                escritor.write(movimientosPGN);
                escritor.newLine();
            }

            System.out.println("Archivo PGN guardado exitosamente como: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
}