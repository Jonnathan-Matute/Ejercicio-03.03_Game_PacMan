package Vista;

import Modelo.Bolita;
import Modelo.Elemento;
import Modelo.BolitaPoder;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import Controlador.Constantes;

/**
 *
 * @author Alex Reinoso
 */
public abstract class Escenario {

    protected int[][] mapa;
    protected List<Bolita> bolitas;
    protected List<BolitaPoder> bolitaPoder;
    protected List<Elemento> paredes;
    protected int tBolitas;
    protected int puntos;
    protected Image ladrillo;

    public Escenario() {
        this.mapa = new int[Constantes.NUM_CELDA][Constantes.NUM_CELDA];
        this.bolitas = new ArrayList<>();
        this.paredes = new ArrayList<>();
        this.bolitaPoder = new ArrayList<>();
        this.puntos = 0;

        // Bordes
        for (int i = 0; i < Constantes.NUM_CELDA; i++) {
            mapa[i][0] = 1;
            mapa[0][i] = 1;
            mapa[i][Constantes.NUM_CELDA - 1] = 1;
            mapa[Constantes.NUM_CELDA - 1][i] = 1;
        }
    }

    public void setBlock(String imageName) {
        // Imagem
        try {
            this.ladrillo = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Constantes.CAMINO + imageName);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // Obtener escenario
    public int[][] getMapa() {
        return mapa;
    }

    public int mapa(int x, int y) {
        return mapa[x][y];
    }

    // Pintar Escenario
    public abstract void paintScene(Graphics g);

    // Termina de definir Escenario
    protected abstract void drawSceneFinal();

    public List<Bolita> getBolitas() {
        return bolitas;
    }

    public void setBolitas(List<Bolita> bolitas) {
        this.bolitas = bolitas;
    }

    public void setBolitaPoder(List<BolitaPoder> bolitaPoder) {
        this.bolitaPoder = bolitaPoder;
    }

    public List<Elemento> getParedes() {
        return paredes;
    }

    public void setParedes(List<Elemento> paredes) {
        this.paredes = paredes;
    }

    public int getTotalBolitas() {
        return this.bolitas.size();
    }

    public int getPuntos() {
        return puntos;
    }

    public List<BolitaPoder> getBolitaPoder() {
        return bolitaPoder;
    }

    public int getTotalBolitaPoder() {
        return this.bolitaPoder.size();
    }
}
