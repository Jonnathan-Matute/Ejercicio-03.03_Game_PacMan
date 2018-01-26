package Controlador;

import java.io.File;

public class Constantes {
    
    //final static es para poder ser usado dentro de metodos en otras clases sin tener que estarlos llamando o instanciando
    public static final int TAMANIO_CELDA = 30;
    public static final int NUM_CELDA = 19;
    public static final int CAMINAR_LUGAR = 1;
    public static final int RETRASAR = 5;

    public static final int TIMER_CEREZA = 50000;

    public static final int TIMER_FRESA = 75000;

    public static final int TAMANIO_BOLITA = 3;

    public static final double MOVER_1PASO_FRUTA = 0.01;

    public static final double CAMINAR_1PASO_ENEMIGO = 0.02;
    
    public static final double CAMINAR_1PASO_LENTO_ENEMIGO = 0.01;

    public static final double MOVER_ESPACIO = 0.05;
    
    public static final String CAMINO = File.separator + "imgs" + File.separator;
}
