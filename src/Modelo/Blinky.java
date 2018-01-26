package Modelo;

/**
 *
 * @author Jonnathan Matute
 */
public class Blinky extends Enemigo {

    public Blinky() {
        super(new String[]{"blinky.png", "vulnerable_ghost.png"});
        setMoveDireccion(Enemigo.MOVER_ARRIBA);
    }
    
    //regresa el fantasma blinky a la ultima posicion
    public void backToLastPosition() {
        this.pos.volver();
    }
}
