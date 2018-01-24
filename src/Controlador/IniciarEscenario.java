package Controlador;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import Vista.Escenario;

/**
 *
 * @author Alex Reinoso
 */
public class IniciarEscenario extends Escenario {

    private Image imgStart;
    private Image imgLoad;
    private Image imgExit;
    private Image fondo;

    public IniciarEscenario() {
        try {
            this.imgStart = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Constantes.CAMINO + "button_start.png");
            this.imgLoad = Toolkit.getDefaultToolkit().getImage(new File(".").getCanonicalPath() + Constantes.CAMINO + "button_load.png");
            this.imgExit = Toolkit.getDefaultToolkit().getImage(new File(".").getCanonicalPath() + Constantes.CAMINO + "button_exit.png");
            this.fondo = Toolkit.getDefaultToolkit().getImage(new File(".").getCanonicalPath() + Constantes.CAMINO + "background_pacman1.jpg");
        } catch (IOException e) {
            System.err.println("Error: Imagenes de pantalla no encontradas\n " + e.getMessage());
        }
    }

    @Override
    public void paintScene(Graphics g) {
        int aux = Constantes.TAMANIO_CELDA * Constantes.NUM_CELDA;
        g.fillRect(0, 0, aux, aux + 50);
        g.drawImage(fondo, 0, 0, aux, aux + 50, null);
        g.drawImage(imgStart, (aux / 2) - 110, 100, 220, 60, null);
        g.drawImage(imgLoad, (aux / 2) - 115, 200, 230, 60, null);
        g.drawImage(imgExit, (aux / 2) - 115, 290, 230, 70, null);
    }

    @Override
    protected void drawSceneFinal() {
    }

}
