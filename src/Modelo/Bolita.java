package Modelo;

import java.awt.Graphics;
import Controlador.Dibujar;

/**
 *
 * @author Daniel Peralta
 */
public class Bolita extends Elemento {

    protected int punto;

    // Construtor
    public Bolita(final String imagenNombre, final int punto) {
        super(new String[]{imagenNombre}, 0, 2);
        this.punto = punto;
        this.isTransposable = false;
    }

    // Construtor
    public Bolita(final String imagenNombre, final int punto, final double x, final double y) {
        super(new String[]{imagenNombre}, 0, 2);
        this.punto = punto;
        this.isTransposable = false;
        this.setPosicion(x, y);
    }

    @Override
    public void autoDraw(Graphics g) {
        Dibujar.draw(g, imageIcon, pos.getY(), pos.getX());
    }
}
