package Modelo;

public class Inky extends Enemigo implements Runnable {

    public static int MOVI_ALEAT = 0;
    public static int MOVI_PAC = 1;
    private int estadoDireccion;
    private double distanciaBlinky;
    
    //estamos cargando al fantasma inky(medio como azul claro), su estado agresivo y su estado mortal

    public Inky() {
        super(new String[]{"inky.png", "vulnerable_ghost.png"});
        this.estadoDireccion = MOVI_ALEAT;
    }

    public double getDistanciaBlinky() {
        return distanciaBlinky;
    }

    public void setDistanciaBlinky(double x1, double y1, double x2, double y2) {
        this.distanciaBlinky = Math.hypot(x1 - x2, y1 - y2);
    }

    public void regresarUltimaPos() {
        this.pos.volver();
    }

    public int getEstadoDireccion() {
        return estadoDireccion;
    }

    public void setEstadoDireccion(int estadoDireccion) {
        this.estadoDireccion = estadoDireccion;
    }

    @Override
    public void run() {
        while (true) {

            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
            if (distanciaBlinky < 4) {
                estadoDireccion = MOVI_PAC;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    System.err.println(ex.getMessage());
                }
                estadoDireccion = MOVI_ALEAT;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }
}
