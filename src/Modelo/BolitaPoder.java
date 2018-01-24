package Modelo;

import java.awt.Graphics;
import Controlador.Dibujar;

/**
 *
 * @author Daniel Peralta
 */

public class BolitaPoder extends Elemento {

    private int puntos;

    // Construtor
    public BolitaPoder(final String imagenNombre, final int puntos) {
        super(new String[]{imagenNombre}, 0, 2);
        this.puntos = puntos;
        this.isTransposable = false;
    }

    // Construtor
    public BolitaPoder(final String imagenNombre, final int punto, final double x, final double y) {
        super(new String[]{imagenNombre}, 0, 2);
        this.puntos = punto;
        this.isTransposable = false;
        this.setPosicion(x, y);
    }

    @Override
    public void autoDraw(Graphics g) {
        Dibujar.draw(g, imageIcon, pos.getY(), pos.getX());
    }
}
