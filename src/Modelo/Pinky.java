package Modelo;

/**
 *
 * @author Jonnathan Matute
 */

public class Pinky extends Enemigo implements Runnable {

    public static int MOVER_ALEAT = 0;
    public static int MOVER_PAC = 1;

    private int estadoDireccion;

    public Pinky() {
        super(new String[]{"pinky.png", "vulnerable_ghost.png"});
        setMoveDireccion(Enemigo.MOVER_DERECHA);
        this.estadoDireccion = MOVER_PAC;
    }

    public int getEstadoDireccion() {
        return estadoDireccion;
    }

    public void setEstadoDireccion(int estadoDireccion) {
        this.estadoDireccion = estadoDireccion;
    }

    public void volverUltimaPosicion() {
        this.pos.volver();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            if (estadoDireccion == MOVER_ALEAT) {
                estadoDireccion = MOVER_PAC;
            } else {
                // Seguir pacman
                estadoDireccion = MOVER_ALEAT;
            }
        }
    }

}
