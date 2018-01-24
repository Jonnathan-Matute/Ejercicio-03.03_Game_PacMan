package Modelo;

import Controlador.Constantes;
import Controlador.Posicion;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.swing.ImageIcon;

/**
 *
 * @author Jonnathan Matute
 */
public abstract class Elemento implements Serializable {

    protected ImageIcon[] direcciones;
    protected ImageIcon imageIcon;
    protected Posicion pos;
    protected boolean isTransposable;
    protected boolean isVisible;

    // Tipo de elemento
    // 1 Pacman
    // 2 Bolita
    // 3 Enemigo
    // 4 Fruta
    // 5 Pared
    private final int typeElement;

    protected Elemento(String[] imageNombre, int dir, int tipoElemento) {
        this.pos = new Posicion(1, 1);
        this.isTransposable = true;

        direcciones = new ImageIcon[imageNombre.length];

        for (int i = 0; i < imageNombre.length; i++) {
            direcciones[i] = getImageIcon(imageNombre[i]);
        }

        this.typeElement = tipoElemento;

        setImageIcon(dir);
    }

    private ImageIcon getImageIcon(String imagenNombre) {
        try {
            ImageIcon imageIconFunc = new ImageIcon(new java.io.File(".").getCanonicalPath() + Constantes.CAMINO + imagenNombre);
            Image img = imageIconFunc.getImage();
            BufferedImage bi;
            Graphics g;

            // Crea tamanios diferentes de imagenes
            switch (this.typeElement) {
                // Pacman
                case 1:
                    bi = new BufferedImage(Constantes.TAMANIO_CELDA - 10, Constantes.TAMANIO_CELDA - 10, BufferedImage.TYPE_INT_ARGB);
                    g = bi.createGraphics();
                    g.drawImage(img, 0, 0, Constantes.TAMANIO_CELDA - 10, Constantes.TAMANIO_CELDA - 10, null);
                    break;

                default:
                    bi = new BufferedImage(Constantes.TAMANIO_CELDA, Constantes.TAMANIO_CELDA, BufferedImage.TYPE_INT_ARGB);
                    g = bi.createGraphics();
                    g.drawImage(img, 0, 0, Constantes.TAMANIO_CELDA, Constantes.TAMANIO_CELDA, null);
                    break;
            }
            imageIconFunc = new ImageIcon(bi);

            return imageIconFunc;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    protected final void setImageIcon(int dir) {
        imageIcon = direcciones[dir];
    }

    public boolean superposicion(final Elemento elem) {
        double xDist = Math.abs(elem.pos.getX() - this.pos.getX());
        double yDist = Math.abs(elem.pos.getY() - this.pos.getY());

        return (xDist < 0.85 && yDist < 0.85);
    }

    public boolean superposicion(final List<Elemento> elem) {
        Iterator<Elemento> it = elem.listIterator();
        while (it.hasNext()) {
            Pared w = (Pared) it.next();
            double xDist = Math.abs(w.pos.getX() - this.pos.getX());
            double yDist = Math.abs(w.pos.getY() - this.pos.getY());

            if (xDist < 1.0 && yDist < 1.0) {
                return true;
            }
        }

        return false;
    }

    public String getStringPosicion() {
        return ("(" + pos.getX() + ", " + pos.getY() + ")");
    }

    public boolean setPosicion(double x, double y) {
        return pos.setPosition(x, y);
    }

    public boolean isTransposable() {
        return isTransposable;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setTransposable(boolean isTransposable) {
        this.isTransposable = isTransposable;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    abstract public void autoDraw(Graphics g);

    // Pacman
    public boolean moverArriba() {
        return this.pos.moverseArriba();
    }

    public boolean moverAbajo() {
        return this.pos.moverseAbajo();
    }

    public boolean moveDerecha() {
        return this.pos.moverseDerecha();
    }

    public boolean moveIzquierda() {
        return this.pos.moverseIzquierda();
    }

    // Enemigo
    public boolean moverArribaEnemigo() {
        return this.pos.moverArribaEnemigo();
    }

    public boolean moverAbajoEnemigo() {
        return this.pos.moverabajoEnemigo();
    }

    public boolean moveDerechaEnemigo() {
        return this.pos.moverDerechaEnemigo();
    }

    public boolean moverIzEnemigo() {
        return this.pos.moverIzquierdaEnemigo();
    }

    // relentizar enemigo
    public boolean arribaLentoEnemigo() {
        return this.pos.arribaLentoEnemigo();
    }

    public boolean abajoLentoEnemigo() {
        return this.pos.abajoLentoEnemigo();
    }

    public boolean derechaLenteEnemigo() {
        return this.pos.derechaLenteEnemigo();
    }

    public boolean izquierdaLentoEnemigo() {
        return this.pos.izquierdaLentoEnemigo();
    }

    public Posicion getPos() {
        return pos;
    }

    public ImageIcon getImgElement() {
        return imageIcon;
    }

    protected boolean isPosInteger() {
        int x = (int) pos.getX();
        int y = (int) pos.getY();

        if ((x - pos.getX()) == 0 && (y - pos.getY()) == 0) {
            return true;
        } else {
            return false;
        }
    }
}
