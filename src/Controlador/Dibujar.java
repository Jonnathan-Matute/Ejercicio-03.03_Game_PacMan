package Controlador;

import Vista.PantallaJuego;
import java.awt.Graphics;
import javax.swing.ImageIcon;

/**
 * 
 *
 * @Alex Reinoso
 */
public class Dibujar {

    static PantallaJuego pantalla;

    public static PantallaJuego getPantallaJuego() {
        return pantalla;
    }

    public static void setPantallaJuego(PantallaJuego pantalla) {
        pantalla = pantalla;
    }

    public static void draw(Graphics g, ImageIcon imageIcon, double y, double x) {
        imageIcon.paintIcon(pantalla, g, (int) Math.round(y * Constantes.TAMANIO_CELDA), (int) Math.round(x * Constantes.TAMANIO_CELDA));
    }
}
