package Controlador;

import Modelo.Bolita;
import Modelo.BolitaPoder;
import Modelo.Blinky;
import Modelo.Cereza;
import Modelo.Clyde;
import Modelo.Elemento;
import Modelo.Enemigo;
import Modelo.Fruta;
import Modelo.Inky;
import Modelo.PacMan;
import Modelo.Pinky;
import Modelo.Frutilla;
import Vista.Escenario;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Jonnathan Matute
 */
public class ControlJuego {

    private int mult = 0;

    // Dibuja todos los elementos del juego
    public void dibujarTodosElementos(Escenario escenario, ArrayList<Elemento> elemArray, Graphics g, int control) {
        escenario.paintScene(g);
        //desecha otros elementos
        if (control != 0 && control != 4) {
            Iterator<Elemento> it = elemArray.listIterator();
            while (it.hasNext()) {
                it.next().autoDraw(g);
            }
        }
    }

    public boolean procesarTodosElementos(Escenario escenario, ArrayList<Elemento> e, ArrayList<Enemigo> enemigos) {
        if (e.isEmpty()) {
            return false;
        }
        PacMan pPacMan = (PacMan) e.get(0);

        // Enemigos
        Blinky blinky = (Blinky) enemigos.get(0);
        Inky inky = (Inky) enemigos.get(1);
        Pinky pinky = (Pinky) enemigos.get(2);
        Clyde clyde = (Clyde) enemigos.get(3);

        // Verifica las colisiones con pacman y el escenario
        if (pPacMan.superposicion(escenario.getParedes())) {
            pPacMan.volverUltimaPosicion();
            pPacMan.setMovBefDireccion(pPacMan.getMovDireccion());
            pPacMan.setMovDireccion(PacMan.PARAR);
            return false;
        }
        // Verifica la colision entre blinky y el escenario
        if (blinky.superposicion(escenario.getParedes())) {
            // /cambia la posicion de blinky cuando choca con los muros
            blinky.backToLastPosition();
            setInvtMovDireccionBlinky(blinky, pPacMan);
        }
        // Verifia colision de pinky con el escenario
        if (pinky.superposicion(escenario.getParedes())) {
            // reinicia el movimiento de pinky cuando choca con paredes
            pinky.volverUltimaPosicion();
            setInvtMovDirectionPinky(pinky);
            // si hay colision, pinky se pone en estado aleatorio
            pinky.setEstadoDireccion(Pinky.MOVER_ALEAT);
        }
        // Verifica colisoion de inky con escenario 
        if (inky.superposicion(escenario.getParedes())) {
            // fija el movimiento de pinky cuando ocurre una colision
            inky.regresarUltimaPos();
            setInvtMovDireccionInky(inky);
        }
        //  Verifica colision de inky con escenario
        if (clyde.superposicion(escenario.getParedes())) {

            // fija el movimiento de Clyde cuando hay colision
            clyde.volverPosicionUltima();
            setInvMovDirectionClyde(clyde);
        }
        // Verificar que pacman se comio una bolita
        Iterator<Bolita> it = escenario.getBolitas().listIterator();
        while (it.hasNext()) {
            if (pPacMan.overlapBall(it.next())) {
                it.remove();
                pPacMan.puntosHechos(10);
                break;
            }
        }
        Iterator<BolitaPoder> it2 = escenario.getBolitaPoder().listIterator();
        while (it2.hasNext()) {
            if (pPacMan.overlapBall(it2.next())) {
                mult = 1;
                pPacMan.puntosHechos(50);
                it2.remove();
                blinky.setEstado(Enemigo.PERSEGUIR); //hade que persigan a pacman
                inky.setEstado(Enemigo.PERSEGUIR);
                pinky.setEstado(Enemigo.PERSEGUIR);
                clyde.setEstado(Enemigo.PERSEGUIR);
                TimerTask vulnerable = new TimerTask() {
                    @Override
                    public void run() { //para correr despues de muerto
                        if (blinky.getEstado() == Enemigo.PERSEGUIR) {
                            blinky.setEstado(Enemigo.CASA);
                        }
                        if (inky.getEstado() == Enemigo.PERSEGUIR) {
                            inky.setEstado(Enemigo.CASA);
                        }
                        if (pinky.getEstado() == Enemigo.PERSEGUIR) {
                            pinky.setEstado(Enemigo.CASA);
                        }
                        if (clyde.getEstado() == Enemigo.PERSEGUIR) {
                            clyde.setEstado(Enemigo.CASA);
                        }
                    }
                };
                Timer timer = new Timer();
                timer.schedule(vulnerable, 7000);
                break;
            }
        }
        Iterator<BolitaPoder> it2_power = escenario.getBolitaPoder().listIterator();
        while (it2_power.hasNext()) {
            if (pPacMan.overlapBall(it2_power.next())) {
                it2_power.remove();
                blinky.setEstado(2); //coloca em vulnerable
                inky.setEstado(2);
                pinky.setEstado(2);
                clyde.setEstado(2);
                TimerTask vulnerable = new TimerTask() {
                    @Override
                    public void run() { //coloca en normal a los fantasmas, pueden matar a pacman
                        if (blinky.getEstado() == 2) {
                            blinky.setEstado(1);
                        }
                        if (inky.getEstado() == 2) {
                            inky.setEstado(1);
                        }
                        if (pinky.getEstado() == 2) {
                            pinky.setEstado(1);
                        }
                        if (clyde.getEstado() == 2) {
                            clyde.setEstado(1);
                        }
                    }
                };
                Timer timer = new Timer();
                timer.schedule(vulnerable, 7000);
                break;
            }
        }
        // variable que detecta si hubo colision entre pacman y enemigo
        boolean aux = false;
        Elemento eTemp;
        // Verifica colision entre pacman y algun otro elemento
        for (int i = 1; i < e.size(); i++) {
            eTemp = e.get(i);
            if (!eTemp.isTransposable() && pPacMan.superposicion(eTemp)) {
                // verificar si el elemento es un enemigo
                if (eTemp instanceof Enemigo) {
                    switch (((Enemigo) eTemp).getEstado()) {
                        case 1:
                            aux = true;
                            pPacMan.volverUltimaPosicion();
                            pPacMan.setMovDireccion(PacMan.PARAR);
                            break;
                        case 2:
                            pPacMan.puntosHechos(200 * mult);
                            if (eTemp instanceof Blinky) {
                                blinky.setEstado(3);
                            }
                            if (eTemp instanceof Inky) {
                                inky.setEstado(3);
                            }
                            if (eTemp instanceof Pinky) {
                                pinky.setEstado(3);
                            }
                            if (eTemp instanceof Clyde) {
                                clyde.setEstado(3);
                            }
                            mult *= 2;
                            break;
                        default:
                            break;
                    }
                } else if (eTemp instanceof Fruta) {
                    if (eTemp instanceof Cereza) {
                        pPacMan.puntosHechos(100);
                    }
                    if (eTemp instanceof Frutilla) {
                        pPacMan.puntosHechos(300);
                    }
                    e.remove(eTemp);
                } else {
                    e.remove(eTemp);
                }
            }
        }

        if (pPacMan.getAux_Puntaje() >= 10000) {
            pPacMan.resetPuntaje();
            pPacMan.addVida();
        }

        // para el movimiento de pacman
        pPacMan.mover();

        // Mover los enemigos
        blinky.mover();
        pinky.mover();
        inky.mover();
        clyde.mover();

        return aux;
    }

    private void setInvtMovDireccionBlinky(Enemigo enemigo, PacMan pPacMan) {

        // Definir nueva direccion
        switch (enemigo.getMovDireccion()) {
            case Enemigo.MOVER_IZQUIERDA:
                if (pPacMan.getPos().getX() > enemigo.getPos().getX()) {
                    enemigo.setMoveDireccion(Enemigo.MOVER_ABAJO);
                } else {
                    enemigo.setMoveDireccion(Enemigo.MOVER_ARRIBA);
                }
                break;

            case Enemigo.MOVER_DERECHA:
                if (pPacMan.getPos().getX() > enemigo.getPos().getX()) {
                    enemigo.setMoveDireccion(Enemigo.MOVER_ABAJO);
                } else {
                    enemigo.setMoveDireccion(Enemigo.MOVER_ARRIBA);
                }
                break;

            case Enemigo.MOVER_ABAJO:
                if (pPacMan.getPos().getY() > enemigo.getPos().getY()) {
                    enemigo.setMoveDireccion(Enemigo.MOVER_DERECHA);
                } else {
                    enemigo.setMoveDireccion(Enemigo.MOVER_IZQUIERDA);
                }
                break;

            case Enemigo.MOVER_ARRIBA:
                if (pPacMan.getPos().getY() > enemigo.getPos().getY()) {
                    enemigo.setMoveDireccion(Enemigo.MOVER_DERECHA);
                } else {
                    enemigo.setMoveDireccion(Enemigo.MOVER_IZQUIERDA);
                }
                break;
        }
    }

    // fijar el movimiento de pinky(nombre del fantasma rosado)
    private void setInvtMovDirectionPinky(Enemigo enemy) {

        int aux = (int) (Math.random() * 10) % 2;
        switch (enemy.getMovDireccion()) {
            case Enemigo.MOVER_IZQUIERDA:
                if (aux == 0) {
                    enemy.setMoveDireccion(Enemigo.MOVER_ARRIBA);
                } else {
                    enemy.setMoveDireccion(Enemigo.MOVER_ABAJO);
                }
                break;
            case Enemigo.MOVER_DERECHA:
                if (aux == 0) {
                    enemy.setMoveDireccion(Enemigo.MOVER_ARRIBA);
                } else {
                    enemy.setMoveDireccion(Enemigo.MOVER_ABAJO);
                }
                break;

            case Enemigo.MOVER_ABAJO:
                if (aux == 0) {
                    enemy.setMoveDireccion(Enemigo.MOVER_IZQUIERDA);
                } else {
                    enemy.setMoveDireccion(Enemigo.MOVER_DERECHA);
                }
                break;

            case Enemigo.MOVER_ARRIBA:
                if (aux == 0) {
                    enemy.setMoveDireccion(Enemigo.MOVER_ARRIBA);
                } else {
                    enemy.setMoveDireccion(Enemigo.MOVER_ABAJO);
                }
                break;
        }
    }

//mover Clyde(nombre de fantasma)
    private void setInvMovDirectionClyde(Enemigo enemy) {
        int aux = (int) (Math.random() * 10) % 2;
        switch (enemy.getMovDireccion()) {
            case Enemigo.MOVER_IZQUIERDA:
                if (aux == 0) {
                    enemy.setMoveDireccion(Enemigo.MOVER_ARRIBA);
                } else {
                    enemy.setMoveDireccion(Enemigo.MOVER_ABAJO);
                }
                break;

            case Enemigo.MOVER_DERECHA:
                if (aux == 0) {
                    enemy.setMoveDireccion(Enemigo.MOVER_ARRIBA);
                } else {
                    enemy.setMoveDireccion(Enemigo.MOVER_ABAJO);
                }
                break;

            case Enemigo.MOVER_ABAJO:
                if (aux == 0) {
                    enemy.setMoveDireccion(Enemigo.MOVER_IZQUIERDA);
                } else {
                    enemy.setMoveDireccion(Enemigo.MOVER_DERECHA);
                }
                break;

            case Enemigo.MOVER_ARRIBA:
                if (aux == 0) {
                    enemy.setMoveDireccion(Enemigo.MOVER_ARRIBA);
                } else {
                    enemy.setMoveDireccion(Enemigo.MOVER_ABAJO);
                }
                break;
        }
    }

    // fijar movimiento de inky
    private void setInvtMovDireccionInky(Inky inky) {

        int rand = (int) (Math.random() * 10) % 2;

        switch (inky.getMovDireccion()) {
            case Inky.MOVER_IZQUIERDA:
                if (rand == 0) {
                    inky.setMoveDireccion(Inky.MOVER_ARRIBA);
                } else {
                    inky.setMoveDireccion(Inky.MOVER_ABAJO);
                }
                break;

            case Inky.MOVER_DERECHA:
                if (rand == 0) {
                    inky.setMoveDireccion(Inky.MOVER_ARRIBA);
                } else {
                    inky.setMoveDireccion(Inky.MOVER_ABAJO);
                }
                break;

            case Inky.MOVER_ARRIBA:
                if (rand == 0) {
                    inky.setMoveDireccion(Inky.MOVER_IZQUIERDA);
                } else {
                    inky.setMoveDireccion(Inky.MOVER_DERECHA);
                }
                break;

            case Inky.MOVER_ABAJO:
                if (rand == 0) {
                    inky.setMoveDireccion(Inky.MOVER_IZQUIERDA);
                } else {
                    inky.setMoveDireccion(Inky.MOVER_DERECHA);
                }
                break;
        }
    }
}
