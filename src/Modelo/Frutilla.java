package Modelo;

/**
 *
 * @author Daniel Peralta
 */
public class Frutilla extends Fruta {
    
    //los puntos por comerse esta fruta son 300, su aparicion es 10000 ms
    public Frutilla() {
        super("strawberry.png");
        this.puntos = 300;
        this.duracion = 10000;
    }
}
