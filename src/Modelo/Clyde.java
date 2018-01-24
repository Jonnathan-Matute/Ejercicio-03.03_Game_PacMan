package Modelo;

/**
 *
 * @author Alex Reinoso
 */
public class Clyde extends Enemigo {

    public static int MOVER_ALEAT = 0;
    public static int MOVER_PAC = 1;
    private int estadoDIRECCION;
    private double distanciaPacman;

    public Clyde() {
        super(new String[]{"clyde.png", "vulnerable_ghost.png"});
        this.estadoDIRECCION = MOVER_ALEAT;
    }

    public void volverPosicionUltima() {
        this.pos.volver();
    }

    public double getDistanciaPacman() {
        return distanciaPacman;
    }

    public void setDistanciaPacman(double x1, double y1, double x2, double y2) {
        this.distanciaPacman = Math.hypot(x1 - x2, y1 - y2);
    }

    public int getEstadoDirection() {
        return estadoDIRECCION;
    }

    public void setEstadoDirection(int estadoDireccion) {
        this.estadoDIRECCION = estadoDireccion;
    }

}
