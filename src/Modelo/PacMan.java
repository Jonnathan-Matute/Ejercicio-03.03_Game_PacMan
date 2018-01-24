package Modelo;

import Controlador.Dibujar;
import java.awt.Graphics;
import java.io.Serializable;

public class PacMan extends Elemento implements Serializable, Runnable {

    public static final int PARAR = 0;
    public static final int MOVER_IZQUIERDA = 1;
    public static final int MOVER_DERECHA = 2;
    public static final int MOVER_ARRIBA = 3;
    public static final int MOVER_ABAJO = 4;

    private int MOVER_DERECHA_ESTADO = 0;
    private int MOVER_ABAJO_ESTADO = 2;
    private int MOVER_IZQUIERDA_ESTADO = 4;
    private int MOVER_ARRIBA_ESTADO = 6;

    private int movDireccion = PARAR;
    private int movBefDireccion = PARAR;
    private int vida = 3;

    private int puntaje;
    private int aux_puntaje;

    private boolean vuelta;
    private int siguienteDireccion; //código con la proxima direccion de pacman

    public PacMan() {
        super(new String[]{"pacman_right.png", "pacman_right2.png", "pacman_down.png", "pacman_down2.png",
            "pacman_left.png", "pacman_left2.png", "pacman_up.png", "pacman_up2.png"}, 0, 1);
        this.isVisible = true;
        this.isTransposable = false;
        this.puntaje = 0;
        this.aux_puntaje = 0;
    }

    public void cambiarDireccion(int dir) {
        setImageIcon(dir);
    }

    @Override
    public void autoDraw(Graphics g) {
        Dibujar.draw(g, imageIcon, pos.getY(), pos.getX());
    }

    public void volverUltimaPosicion() {
        this.pos.volver();
    }

    public void setVuelta(boolean vuelta) {
        this.vuelta = vuelta;
    }

    public boolean getVuelta() {
        return vuelta;
    }

    public void setSiguienteDireccion(int siguienteDireccion) {
        this.siguienteDireccion = siguienteDireccion;
    }

    public void setMovDireccion(int direccion) {
        movDireccion = direccion;
    }

    public boolean overlapBall(final Elemento elem) {
        double xDist = Math.abs(elem.pos.getX() - this.pos.getX());
        double yDist = Math.abs(elem.pos.getY() - this.pos.getY());

        return (xDist < 0.45 && yDist < 0.45);
    }

    public void mover() {
        boolean flag = false; //flag para checar si entro en el if de abajo
        //si pacman esta en una posicion entera, itera si se presioan alguna tecla
        if (vuelta && this.isPosInteger()) {
            this.setMovDireccion(siguienteDireccion);
            vuelta = false;
            flag = true;
        }
        //verifica la proxima direccion de pacman y realiza el cambio
        switch (movDireccion) {
            case MOVER_IZQUIERDA:
                if (flag) {
                    this.cambiarDireccion(3);
                }
                this.moveIzquierda();
                break;

            case MOVER_DERECHA:
                if (flag) {
                    this.cambiarDireccion(0);
                }
                this.moveDerecha();
                break;

            case MOVER_ARRIBA:
                if (flag) {
                    this.cambiarDireccion(4);
                }
                this.moverArriba();
                break;

            case MOVER_ABAJO:
                if (flag) {
                    this.cambiarDireccion(2);
                }
                this.moverAbajo();
                break;

            default:
                break;
        }
    }

    // Agregar vidas
    public void addVida() {
        vida++;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public void removerVida() {
        vida--;
    }

    public int getVida() {
        return vida;
    }

    public int getMovDireccion() {
        return movDireccion;
    }

    public void setMovBefDireccion(int movBefDireccion) {
        this.movBefDireccion = movBefDireccion;
    }

    public int getPuntaje() {
        return this.puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public void resetPuntaje() {
        this.aux_puntaje -= 10000;
    }

    public void resetPuntajeTotal() {
        this.aux_puntaje = 0;
        this.puntaje = 0;
    }

    public void puntosHechos(int puntos) {
        this.puntaje += puntos;
        this.aux_puntaje += puntos;
    }

    public int getAux_Puntaje() {
        return aux_puntaje;
    }

    @Override
    public void run() {
        while (true) {

            try {
                Thread.sleep(150);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }

            // A cada 0.3s sucede una actualizacion de la imagen de pacman, para hacerle ver como si abre y cierra la boca
            switch (movDireccion) {
                case MOVER_DERECHA:
                    if (MOVER_DERECHA_ESTADO == 0) {
                        cambiarDireccion(1);
                        MOVER_DERECHA_ESTADO = 1;
                    } else {
                        cambiarDireccion(0);
                        MOVER_DERECHA_ESTADO = 0;
                    }
                    break;

                case MOVER_ABAJO:
                    if (MOVER_ABAJO_ESTADO == 2) {
                        cambiarDireccion(3);
                        MOVER_ABAJO_ESTADO = 3;
                    } else {
                        cambiarDireccion(2);
                        MOVER_ABAJO_ESTADO = 2;
                    }
                    break;

                case MOVER_IZQUIERDA:
                    if (MOVER_IZQUIERDA_ESTADO == 4) {
                        cambiarDireccion(5);
                        MOVER_IZQUIERDA_ESTADO = 5;
                    } else {
                        cambiarDireccion(4);
                        MOVER_IZQUIERDA_ESTADO = 4;
                    }
                    break;

                case MOVER_ARRIBA:
                    if (MOVER_ARRIBA_ESTADO == 6) {
                        cambiarDireccion(7);
                        MOVER_ARRIBA_ESTADO = 7;
                    } else {
                        cambiarDireccion(6);
                        MOVER_ARRIBA_ESTADO = 6;
                    }
                    break;

                case PARAR:
                    switch (movBefDireccion) {
                        case MOVER_DERECHA:
                            if (MOVER_DERECHA_ESTADO == 0) {
                                cambiarDireccion(1);
                                MOVER_DERECHA_ESTADO = 1;
                            } else {
                                cambiarDireccion(0);
                                MOVER_DERECHA_ESTADO = 0;
                            }
                            break;

                        case MOVER_ABAJO:
                            if (MOVER_ABAJO_ESTADO == 2) {
                                cambiarDireccion(3);
                                MOVER_ABAJO_ESTADO = 3;
                            } else {
                                cambiarDireccion(2);
                                MOVER_ABAJO_ESTADO = 2;
                            }
                            break;

                        case MOVER_IZQUIERDA:
                            if (MOVER_IZQUIERDA_ESTADO == 4) {
                                cambiarDireccion(5);
                                MOVER_IZQUIERDA_ESTADO = 5;
                            } else {
                                cambiarDireccion(4);
                                MOVER_IZQUIERDA_ESTADO = 4;
                            }
                            break;

                        case MOVER_ARRIBA:
                            if (MOVER_ARRIBA_ESTADO == 6) {
                                cambiarDireccion(7);
                                MOVER_ARRIBA_ESTADO = 7;
                            } else {
                                cambiarDireccion(6);
                                MOVER_ARRIBA_ESTADO = 6;
                            }
                            break;
                    }
                    break;
            }
        }
    }

}
