package Modelo;

import java.awt.Graphics;
import Controlador.Dibujar;

/**
 *
 * @author Daniel Peralta
 */
public abstract class Fruta extends Elemento {

    protected int puntos;
    protected int duracion;

    public Fruta(String nombreIcono) {
        super(new String[]{nombreIcono}, 0, 4);
        // Condiciones iniciales de la fruta
        this.isTransposable = true;
        this.isVisible = false;
    }

    public int getPuntos() {
        return puntos;
    }

    public int getDuracion() {
        return duracion;
    }

    public void decrementarDuracion() {
        this.duracion--;
    }

    @Override
    public void autoDraw(Graphics g) {
        if (isVisible) {
            Dibujar.draw(g, this.imageIcon, pos.getY(), pos.getX());
        }
    }
}
