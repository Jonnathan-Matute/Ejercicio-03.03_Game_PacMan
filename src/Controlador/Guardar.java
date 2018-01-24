package Controlador;

import Modelo.Bolita;
import Modelo.Blinky;
import Modelo.Cereza;
import Modelo.Clyde;
import Modelo.Elemento;
import Modelo.Enemigo;
import Modelo.Inky;
import Modelo.PacMan;
import Modelo.Pinky;
import Modelo.BolitaPoder;
import Modelo.Frutilla;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex Reinoso
 */
public class Guardar implements Serializable {

    public PacMan pacMan;

    public Clyde clyde;
    public Blinky blinky;
    public Inky inky;
    public Pinky pinky;

    public Frutilla frutilla;
    public Cereza cereza;

    public ArrayList<Elemento> elemArray;
    public ArrayList<Enemigo> enemigos;
    public List<Elemento> paredes;

    public List<Bolita> bolitas;
    public List<BolitaPoder> bolitasPoder;

    public int controlScenario;

    public Guardar() {
    }
}
