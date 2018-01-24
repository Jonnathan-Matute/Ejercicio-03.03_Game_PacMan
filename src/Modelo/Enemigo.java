package Modelo;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;
import Controlador.Dibujar;

/**
 *
 * @author Alex Reinoso
 */
public abstract class Enemigo extends Elemento {

    public static final int MOVER_IZQUIERDA = 1;
    public static final int MOVER_DERECHA = 2;
    public static final int MOVER_ARRIBA = 3;
    public static final int MOVER_ABAJO = 4;
    public static final int CASA = 1;
    public static final int PERSEGUIR = 2;
    public static final int PELIGRO = 3;
    protected Point pacman_pos;
    protected int[][] mapa;
    private int movDireccion;
    private int estado;

    public Enemigo(String[] imgs) {
        super(imgs, 0, 3);
        this.isVisible = true;
        this.isTransposable = false;
        this.pacman_pos = new Point();
        this.estado = 1;
    }

    public void setMoveDireccion(int movDireccion) {
        this.movDireccion = movDireccion;
    }

    public int getMovDireccion() {
        return movDireccion;
    }

    public void setEstado(int Estado) {
        this.estado = Estado;
    }

    public int getEstado() {
        return estado;
    }

    public void mover() {
        switch (movDireccion) {
            case MOVER_IZQUIERDA:
                if (estado == PERSEGUIR) {
                    this.izquierdaLentoEnemigo();
                } else {
                    this.moverIzEnemigo();
                }
                break;
            case MOVER_DERECHA:
                if (estado == PERSEGUIR) {
                    this.derechaLenteEnemigo();
                } else {
                    this.moveDerechaEnemigo();
                }
                break;
            case MOVER_ARRIBA:
                if (estado == PERSEGUIR) {
                    this.arribaLentoEnemigo();
                } else {
                    this.moverArribaEnemigo();
                }
                break;
            case MOVER_ABAJO:
                if (estado == PERSEGUIR) {
                    this.abajoLentoEnemigo();
                } else {
                    this.moverAbajoEnemigo();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void autoDraw(Graphics g) {
        switch (this.getEstado()) {
            case 1:
                Dibujar.draw(g, this.direcciones[0], pos.getY(), pos.getX());
                break;
            case 2:
                Dibujar.draw(g, this.direcciones[1], pos.getY(), pos.getX());
                break;
            case 3:
                this.setVisible(false);
                TimerTask revive = new TimerTask() {
                    @Override
                    public void run() {
                        setEstado(1);
                    }
                };
                Timer timer = new Timer();
                timer.schedule(revive, 5000);
        }
    }

    public void setPacman_pos(final int x, final int y) {
        this.pacman_pos.x = x;
        this.pacman_pos.y = y;
    }

    public void setMapa(int[][] mapa) {
        this.mapa = mapa;
    }
}
