package Controlador;

import Vista.PantallaJuego;
import java.awt.Graphics;
import javax.swing.ImageIcon;

/**
 * 
 *
 * @author Daniel Peralta
 */
public class Dibujar {

    static PantallaJuego pantalla;

    public static PantallaJuego getPantallaJuego() {
        return pantalla;
    }

    public static void setPantallaJuego(PantallaJuego pantalla) {
        pantalla = pantalla;
    }
    

    //dibuja o genera los elementos dentro del laberinto, se basa en los datos para tamaño de celda para ingresar
    //las imagenes que seran parte del laberinto
    public static void draw(Graphics g, ImageIcon imageIcon, double y, double x) {
        imageIcon.paintIcon(pantalla, g, (int) Math.round(y * Constantes.TAMANIO_CELDA), (int) Math.round(x * Constantes.TAMANIO_CELDA));
    }
}
