package Modelo;

import java.awt.Graphics;
import Controlador.Dibujar;

/**
 *
 * @Jorge Pizarro
 */
public class Pared extends Elemento {

    public Pared(String imagen, double x, double y) {
        super(new String[]{imagen}, 0, 5);
        this.isVisible = true;
        this.isTransposable = false;
        this.setPosicion(x, y);
    }

    @Override
    public void autoDraw(Graphics g) {
        Dibujar.draw(g, imageIcon, pos.getY(), pos.getX());
    }

}
