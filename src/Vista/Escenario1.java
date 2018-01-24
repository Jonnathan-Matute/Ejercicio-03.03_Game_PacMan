package Vista;

import Modelo.Bolita;
import Modelo.Elemento;
import Modelo.BolitaPoder;
import Modelo.Pared;
import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import Controlador.Constantes;

/**
 *
 * @author Jonnathan Matute
 */
public class Escenario1 extends Escenario {

    public Escenario1() {
        this.drawSceneFinal();
    }

    @Override
    public void paintScene(Graphics g) {
        g.fillRect(0, 0, Constantes.TAMANIO_CELDA * Constantes.NUM_CELDA, Constantes.TAMANIO_CELDA * Constantes.NUM_CELDA + 50);

        Iterator<Elemento> it_wall = paredes.listIterator();
        while (it_wall.hasNext()) {
            it_wall.next().autoDraw(g);
        }

        Iterator<Bolita> it = bolitas.listIterator();
        while (it.hasNext()) {
            it.next().autoDraw(g);
        }

        // Dibuja bolitas con poderes
        Iterator<BolitaPoder> it2 = bolitaPoder.listIterator();
        while (it2.hasNext()) {
            it2.next().autoDraw(g);
        }

        // Calcula los puntos del nivel
        puntos = (tBolitas - bolitas.size()) * 10;
    }

    @Override
    protected void drawSceneFinal() {
        // Ler cenario
        Scanner mapRead;
        try {
            mapRead = new Scanner(new FileInputStream("./src/maps/map1.txt"));
            for (int i = 0; i < Constantes.NUM_CELDA; i++) {
                for (int j = 0; j < Constantes.NUM_CELDA; j++) {
                    mapa[i][j] = (int) mapRead.nextByte();
                }
            }
            mapRead.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Erro no arqv");
            Logger.getLogger(Escenario1.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int x = 0; x < Constantes.NUM_CELDA; x++) {
            for (int y = 0; y < Constantes.NUM_CELDA; y++) {
                switch (mapa[x][y]) {
                    case 0:
                        this.bolitas.add(new Bolita("ball.png", 10, x, y));
                        break;
                    case 1:
                        this.paredes.add(new Pared("brick.png", x, y));
                        break;
                    case 2:
                        this.bolitaPoder.add(new BolitaPoder("power_pellet.png", 50, x, y));
                        break;
                    default:
                        break;
                }
            }
        }
        mapa[1][1] = 0;

        tBolitas = bolitas.size();
    }
}
