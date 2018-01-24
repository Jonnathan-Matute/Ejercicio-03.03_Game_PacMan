package Controlador;

import java.io.Serializable;


public class Posicion implements Serializable {
    /* 
      los elementos estan posicionados en un gridLayout(integers)
       Pero, el movimiento usa floats(lo que hace que sea continuo).
       Razon por la que x, y son de dipo double
       x y y varia de 0 a TAMANIO_CELDA*NUM_CELDA.
       La posicion real del pixel es convertia por la clase dibujar
       Como consecuencia, cualquier elemento tiene una medida de 1x1(x, y). */
    private double x;
    private double y;

    private double previaX;
    private double previaY;

    public Posicion(double x, double y) {
        this.setPosition(x, y);
    }
    public final boolean setPosition(double x, double y) {
        int factor = (int) Math.pow(10, Constantes.CAMINAR_LUGAR + 1);
        x = (double) Math.round(x * factor) / factor;
        y = (double) Math.round(y * factor) / factor;

        if (x < 0 || x > Controlador.Constantes.NUM_CELDA - 1) {
            return false;
        }
        previaX = this.x;
        this.x = x;

        if (y < 0 || y > Controlador.Constantes.NUM_CELDA - 1) {
            return false;
        }
        previaY = this.y;
        this.y = y;
        return true;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    public boolean volver() {
        return this.setPosition(previaX, previaY);
    }
    // Pacman
    public boolean moverseArriba() {
        return this.setPosition(this.getX() - Constantes.MOVER_ESPACIO, this.getY());
    }
    public boolean moverseAbajo() {
        return this.setPosition(this.getX() + Constantes.MOVER_ESPACIO, this.getY());
    }

    public boolean moverseDerecha() {
        return this.setPosition(this.getX(), this.getY() + Constantes.MOVER_ESPACIO);
    }
    public boolean moverseIzquierda() {
        return this.setPosition(this.getX(), this.getY() - Constantes.MOVER_ESPACIO);
    }
    // Enemigo
    public boolean moverArribaEnemigo() {
        return this.setPosition(this.getX() - Constantes.CAMINAR_1PASO_ENEMIGO, this.getY());
    }
    public boolean moverabajoEnemigo() {
        return this.setPosition(this.getX() + Constantes.CAMINAR_1PASO_ENEMIGO, this.getY());
    }
    public boolean moverDerechaEnemigo() {
        return this.setPosition(this.getX(), this.getY() + Constantes.CAMINAR_1PASO_ENEMIGO);
    }
    public boolean moverIzquierdaEnemigo() {
	return this.setPosition(this.getX(), this.getY() - Constantes.CAMINAR_1PASO_ENEMIGO);
    }
    // relentizar enemigo
    public boolean arribaLentoEnemigo() {
        return this.setPosition(this.getX() - Constantes.CAMINAR_1PASO_LENTO_ENEMIGO, this.getY());
    }
    public boolean abajoLentoEnemigo() {
        return this.setPosition(this.getX() + Constantes.CAMINAR_1PASO_LENTO_ENEMIGO, this.getY());
    }
    public boolean derechaLenteEnemigo() {
        return this.setPosition(this.getX(), this.getY() + Constantes.CAMINAR_1PASO_LENTO_ENEMIGO);
    }
    public boolean izquierdaLentoEnemigo() {
	return this.setPosition(this.getX(), this.getY() - Constantes.CAMINAR_1PASO_LENTO_ENEMIGO);
    }
}
