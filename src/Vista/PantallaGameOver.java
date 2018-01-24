package Vista;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import Controlador.Constantes;

/**
 *
 * @author Alex Reinoso
 */
public class PantallaGameOver extends Escenario {

    private Image imgStart;
    private Image imgExit;
    private Image fondo;

    public PantallaGameOver() {
        try {
            this.imgStart = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Constantes.CAMINO + "button_start.png");
            this.imgExit = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Constantes.CAMINO + "button_exit.png");
            this.fondo = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Constantes.CAMINO + "background_game_over.jpg");
        } catch (IOException e) {
            System.err.println("Error " + e.getMessage());
        }
    }

    @Override
    public void paintScene(Graphics g) {
        int aux = Constantes.TAMANIO_CELDA * Constantes.NUM_CELDA;
        g.fillRect(0, 0, aux, aux + 50);
        g.drawImage(fondo, 0, 0, aux, aux + 50, null);
        g.drawImage(imgStart, (aux / 2) - 210, 350, 200, 60, null);
        g.drawImage(imgExit, (aux / 2) + 10, 340, 200, 70, null);
    }

    @Override
    protected void drawSceneFinal() {
    }

}
