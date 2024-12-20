package Piezas;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import main.Tablero;
public class Pieza {

    public int row, col;
    public int xPos, yPos;

    public boolean esBlanca;
    public String nombre;
    public int valor;

    public boolean esPrimerMovimiento = true;

    BufferedImage hoja;
    {
        try {
            hoja = ImageIO.read(ClassLoader.getSystemResourceAsStream("res/pieces.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected int escalaHoja = hoja.getWidth() / 6;
    Image sprite;

    Tablero tablero;

    public Pieza(Tablero tablero) {
        this.tablero = tablero;
    }

    public boolean esMovimientoValido(int col, int row) {
        return true;
    }

    public boolean movimientoColisionaConPieza(int col, int row) {
        return false;
    }

    public void pintar(Graphics2D g2d) {
        g2d.drawImage(sprite, xPos, yPos, null);
    }
}