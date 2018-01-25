package Vista;

import Controlador.IniciarEscenario;
import Controlador.ControlJuego;
import Modelo.Guardar;
import Modelo.Blinky;
import Modelo.PacMan;
import Modelo.Elemento;
import Modelo.Cereza;
import Modelo.Clyde;
import Modelo.Enemigo;
import Modelo.Inky;
import Modelo.Pinky;
import Modelo.Frutilla;
import Controlador.Constantes;
import Controlador.Dibujar;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PantallaJuego extends JFrame implements KeyListener, MouseListener {

    private PacMan pacMan;

    private Blinky blinky;
    private Clyde clyde;
    private Inky inky;
    private Pinky pinky;

    private Frutilla strawberry;
    private Cereza cherry;

    private ArrayList<Elemento> elemArray;
    private ArrayList<Enemigo> enemys;

    private final ControlJuego controller = new ControlJuego();
    private final Random random = new Random();
    private final Executor executor_scene_1;

    private Escenario scene;

    private Image imgLife;
    private Image imgScore;
    private Image imgNum0, imgNum1, imgNum2, imgNum3,
            imgNum4, imgNum5, imgNum6, imgNum7, imgNum8, imgNum9;

    private int controlScene;

    // Construtor
    public PantallaJuego() {
        Dibujar.setPantallaJuego(this);
        initComponents();
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.setSize(Constantes.NUM_CELDA * Constantes.TAMANIO_CELDA + getInsets().left + getInsets().right,
                Constantes.NUM_CELDA * Constantes.TAMANIO_CELDA + getInsets().top + getInsets().bottom + 50);

        // Lista de elementos
        this.elemArray = new ArrayList<>();
        this.enemys = new ArrayList<>();

        // Pacman
        this.pacMan = new PacMan();
        this.addElement(pacMan);

        // Blinky
        this.blinky = new Blinky();
        this.elemArray.add(blinky);
        this.enemys.add(blinky);

        // Inky
        this.inky = new Inky();
        this.elemArray.add(inky);
        this.enemys.add(inky);

        // Pinky
        this.pinky = new Pinky();
        this.elemArray.add(pinky);
        this.enemys.add(pinky);

        // Clyde
        this.clyde = new Clyde();
        this.elemArray.add(clyde);
        this.enemys.add(clyde);

        this.strawberry = new Frutilla();
        this.cherry = new Cereza();

        try {
            this.imgScore = Toolkit.getDefaultToolkit().getImage(new File(".").getCanonicalPath() + Constantes.CAMINO + "button_score.png");
            this.imgLife = Toolkit.getDefaultToolkit().getImage(new File(".").getCanonicalPath() + Constantes.CAMINO + "pacman_right.png");

            this.imgNum0 = Toolkit.getDefaultToolkit().getImage(new File(".").getCanonicalPath() + Constantes.CAMINO + "num0.png");
            this.imgNum1 = Toolkit.getDefaultToolkit().getImage(new File(".").getCanonicalPath() + Constantes.CAMINO + "num1.png");
            this.imgNum2 = Toolkit.getDefaultToolkit().getImage(new File(".").getCanonicalPath() + Constantes.CAMINO + "num2.png");
            this.imgNum3 = Toolkit.getDefaultToolkit().getImage(new File(".").getCanonicalPath() + Constantes.CAMINO + "num3.png");
            this.imgNum4 = Toolkit.getDefaultToolkit().getImage(new File(".").getCanonicalPath() + Constantes.CAMINO + "num4.png");
            this.imgNum5 = Toolkit.getDefaultToolkit().getImage(new File(".").getCanonicalPath() + Constantes.CAMINO + "num5.png");
            this.imgNum6 = Toolkit.getDefaultToolkit().getImage(new File(".").getCanonicalPath() + Constantes.CAMINO + "num6.png");
            this.imgNum7 = Toolkit.getDefaultToolkit().getImage(new File(".").getCanonicalPath() + Constantes.CAMINO + "num7.png");
            this.imgNum8 = Toolkit.getDefaultToolkit().getImage(new File(".").getCanonicalPath() + Constantes.CAMINO + "num8.png");
            this.imgNum9 = Toolkit.getDefaultToolkit().getImage(new File(".").getCanonicalPath() + Constantes.CAMINO + "num9.png");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        this.executor_scene_1 = Executors.newCachedThreadPool();
        this.executor_scene_1.execute(pinky);
        this.executor_scene_1.execute(pacMan);
        this.executor_scene_1.execute(inky);

        // Crea escenario
        this.controlScene = 0;
        newScene(controlScene);
    }

    private void newScene(int scene) {
        switch (scene) {

            case 0:
                this.scene = new IniciarEscenario();
                this.pacMan.setVida(3);
                this.pacMan.resetPuntajeTotal();
                break;

            case 1:
                this.scene = new Escenario1();
                this.scene.setBlock("brick.png");
                resetEnemyPac();
                int aux1,
                 aux2;
                do {
                    aux1 = random.nextInt(Constantes.NUM_CELDA - 1);
                    aux2 = random.nextInt(Constantes.NUM_CELDA - 1);
                } while (this.scene.mapa(aux1, aux2) == 1);

                this.strawberry.setPosicion(aux1, aux2);
                this.addElement(strawberry);

                do {
                    aux1 = random.nextInt(Constantes.NUM_CELDA - 1);
                    aux2 = random.nextInt(Constantes.NUM_CELDA - 1);
                } while (this.scene.mapa(aux1, aux2) == 1);

                this.cherry.setPosicion(aux1, aux2);
                this.addElement(cherry);
                break;

            case 2:
                this.scene = new Escenario2();
                this.scene.setBlock("brick.png");
                resetEnemyPac();
                do {
                    aux1 = random.nextInt(Constantes.NUM_CELDA - 1);
                    aux2 = random.nextInt(Constantes.NUM_CELDA - 1);
                } while (this.scene.mapa(aux1, aux2) == 1);

                this.strawberry.setPosicion(aux1, aux2);
                this.addElement(strawberry);
                do {
                    aux1 = random.nextInt(Constantes.NUM_CELDA - 1);
                    aux2 = random.nextInt(Constantes.NUM_CELDA - 1);
                } while (this.scene.mapa(aux1, aux2) == 1);

                this.cherry.setPosicion(aux1, aux2);
                this.addElement(cherry);
                break;

            case 3:
                this.scene = new Escenario3();
                this.scene.setBlock("brick.png");
                resetEnemyPac();
                do {
                    aux1 = random.nextInt(Constantes.NUM_CELDA - 1);
                    aux2 = random.nextInt(Constantes.NUM_CELDA - 1);
                } while (this.scene.mapa(aux1, aux2) == 1);

                this.strawberry.setPosicion(aux1, aux2);
                this.addElement(strawberry);
                do {
                    aux1 = random.nextInt(Constantes.NUM_CELDA - 1);
                    aux2 = random.nextInt(Constantes.NUM_CELDA - 1);
                } while (this.scene.mapa(aux1, aux2) == 1);

                this.cherry.setPosicion(aux1, aux2);
                this.addElement(cherry);

                break;

            case 4:
                this.scene = new PantallaGameOver();
                break;

            case 5:

                break;
        }
    }

    public final void addElement(Elemento elem) {
        elemArray.add(elem);
    }

    public void removeElement(Elemento elem) {
        elemArray.remove(elem);
    }

    @Override
    public void paint(Graphics gOld) {
        Graphics g = getBufferStrategy().getDrawGraphics();
        Graphics g2 = g.create(getInsets().right, getInsets().top,
                getWidth() - getInsets().left, getHeight() - getInsets().bottom);

        this.controller.dibujarTodosElementos(scene, elemArray, g2, controlScene);

        if (controlScene != 0 && controlScene != 4) {

            setBlinkyMovDirection();

            setPinkyMovDirection();

            setInkyMovDirection();

            setClydeMovDirection();

            if (controller.procesarTodosElementos(scene, elemArray, enemys)) {

                pacMan.removerVida();

                resetEnemyPac();

                if (pacMan.getVida() == 0) {
                    System.out.println("Entra");
                    this.controlScene = 4;
                    newScene(controlScene);
                    return;
                }
            }

            if (scene.getBolitas().isEmpty() && scene.getBolitaPoder().isEmpty()) {
                controlScene++;
                newScene(controlScene);
            }

            int aux = Constantes.TAMANIO_CELDA * Constantes.NUM_CELDA;

            for (int i = 0; i < pacMan.getVida(); i++) {
                g2.drawImage(imgLife, 10 + (32 * i), aux + 10, 30, 30, null);
            }

            if (elemArray.contains(strawberry)) {
                g2.drawImage(strawberry.getImgElement().getImage(), 140, aux + 7, 30, 33, null);
            }

            if (elemArray.contains(cherry)) {
                g2.drawImage(cherry.getImgElement().getImage(), 180, aux + 7, 30, 33, null);
            }

            g2.drawImage(imgScore, 340, aux + 2, 75, 45, null);

            String score = Integer.toString(pacMan.getPuntaje());

            for (int i = 0; i < score.length(); i++) {
                switch (score.charAt(i)) {
                    case '0':
                        g2.drawImage(imgNum0, 410 + (30 * i), aux + 8, 30, 30, null);
                        break;
                    case '1':
                        g2.drawImage(imgNum1, 415 + (30 * i), aux + 8, 30, 30, null);
                        break;
                    case '2':
                        g2.drawImage(imgNum2, 415 + (30 * i), aux + 8, 30, 30, null);
                        break;
                    case '3':
                        g2.drawImage(imgNum3, 415 + (30 * i), aux + 8, 30, 30, null);
                        break;
                    case '4':
                        g2.drawImage(imgNum4, 415 + (30 * i), aux + 8, 30, 30, null);
                        break;
                    case '5':
                        g2.drawImage(imgNum5, 415 + (30 * i), aux + 8, 30, 30, null);
                        break;
                    case '6':
                        g2.drawImage(imgNum6, 415 + (30 * i), aux + 8, 30, 30, null);
                        break;
                    case '7':
                        g2.drawImage(imgNum7, 415 + (30 * i), aux + 8, 30, 30, null);
                        break;
                    case '8':
                        g2.drawImage(imgNum8, 415 + (30 * i), aux + 8, 30, 30, null);
                        break;
                    case '9':
                        g2.drawImage(imgNum9, 415 + (30 * i), aux + 8, 30, 30, null);
                        break;
                    default:
                        break;
                }
            }
        }

        g.dispose();
        g2.dispose();
        if (!getBufferStrategy().contentsLost()) {
            getBufferStrategy().show();
        }
    }

    private void setBlinkyMovDirection() {
        switch (pacMan.getMovDireccion()) {
            case PacMan.MOVER_ABAJO:

                if (pacMan.getPos().getX() > blinky.getPos().getX()) {
                    blinky.setMoveDireccion(Enemigo.MOVER_ABAJO);
                } else {
                    blinky.setMoveDireccion(Enemigo.MOVER_ARRIBA);
                }
                break;

            case PacMan.MOVER_ARRIBA:

                if (pacMan.getPos().getX() > blinky.getPos().getX()) {
                    blinky.setMoveDireccion(Enemigo.MOVER_ABAJO);
                } else {
                    blinky.setMoveDireccion(Enemigo.MOVER_ARRIBA);
                }
                break;

            case PacMan.MOVER_IZQUIERDA:

                if (pacMan.getPos().getY() > blinky.getPos().getY()) {
                    blinky.setMoveDireccion(Enemigo.MOVER_DERECHA);
                } else {
                    blinky.setMoveDireccion(Enemigo.MOVER_IZQUIERDA);
                }
                break;

            case PacMan.MOVER_DERECHA:

                if (pacMan.getPos().getY() > blinky.getPos().getY()) {
                    blinky.setMoveDireccion(Enemigo.MOVER_DERECHA);
                } else {
                    blinky.setMoveDireccion(Enemigo.MOVER_IZQUIERDA);
                }
                break;
        }
    }

    // Movimiento Inky
    private void setInkyMovDirection() {

        inky.setDistanciaBlinky(inky.getPos().getX(), inky.getPos().getY(),
                blinky.getPos().getX(), blinky.getPos().getY());

        if (inky.getDistanciaBlinky() < 4 && inky.getEstadoDireccion() == Inky.MOVI_PAC) {
            switch (blinky.getMovDireccion()) {
                case Blinky.MOVER_ABAJO:

                    if (blinky.getPos().getX() > inky.getPos().getX()) {
                        inky.setMoveDireccion(Enemigo.MOVER_ABAJO);
                    } else {
                        inky.setMoveDireccion(Enemigo.MOVER_ARRIBA);
                    }
                    break;

                case Blinky.MOVER_ARRIBA:

                    if (blinky.getPos().getX() > inky.getPos().getX()) {
                        inky.setMoveDireccion(Enemigo.MOVER_ABAJO);
                    } else {
                        inky.setMoveDireccion(Enemigo.MOVER_ARRIBA);
                    }
                    break;

                case Blinky.MOVER_IZQUIERDA:

                    if (blinky.getPos().getY() > inky.getPos().getY()) {
                        inky.setMoveDireccion(Enemigo.MOVER_DERECHA);
                    } else {
                        inky.setMoveDireccion(Enemigo.MOVER_IZQUIERDA);
                    }
                    break;

                case Blinky.MOVER_DERECHA:

                    if (blinky.getPos().getY() > inky.getPos().getY()) {
                        inky.setMoveDireccion(Enemigo.MOVER_DERECHA);
                    } else {
                        inky.setMoveDireccion(Enemigo.MOVER_IZQUIERDA);
                    }
                    break;

                default:
                    break;
            }
        }
    }

    // Movimiento Pinky
    private void setPinkyMovDirection() {
        switch (pacMan.getMovDireccion()) {
            case PacMan.MOVER_ABAJO:
                if (pinky.getEstadoDireccion() == Pinky.MOVER_PAC) {
                    pinky.setMoveDireccion(Enemigo.MOVER_ABAJO);
                }
                break;

            case PacMan.MOVER_ARRIBA:
                if (pinky.getEstadoDireccion() == Pinky.MOVER_PAC) {
                    pinky.setMoveDireccion(Enemigo.MOVER_ARRIBA);
                }
                break;

            case PacMan.MOVER_IZQUIERDA:
                if (pinky.getEstadoDireccion() == Pinky.MOVER_PAC) {
                    pinky.setMoveDireccion(Enemigo.MOVER_IZQUIERDA);
                }
                break;

            case PacMan.MOVER_DERECHA:
                if (pinky.getEstadoDireccion() == Pinky.MOVER_PAC) {
                    pinky.setMoveDireccion(Enemigo.MOVER_DERECHA);
                }
                break;
        }
    }

    // Movimiento Clyde
    private void setClydeMovDirection() {

        clyde.setDistanciaPacman(clyde.getPos().getX(), clyde.getPos().getY(),
                pacMan.getPos().getX(), pacMan.getPos().getY());

        if (clyde.getDistanciaPacman() > 5) {
            switch (pacMan.getMovDireccion()) {
                case PacMan.MOVER_ABAJO:

                    if (pacMan.getPos().getX() > clyde.getPos().getX()) {
                        clyde.setMoveDireccion(Enemigo.MOVER_ABAJO);
                    } else {
                        clyde.setMoveDireccion(Enemigo.MOVER_ARRIBA);
                    }

                    break;

                case PacMan.MOVER_ARRIBA:

                    if (pacMan.getPos().getX() > clyde.getPos().getX()) {
                        clyde.setMoveDireccion(Enemigo.MOVER_ABAJO);
                    } else {
                        clyde.setMoveDireccion(Enemigo.MOVER_ARRIBA);
                    }

                    break;

                case PacMan.MOVER_IZQUIERDA:

                    if (pacMan.getPos().getY() > clyde.getPos().getY()) {
                        clyde.setMoveDireccion(Enemigo.MOVER_DERECHA);
                    } else {
                        clyde.setMoveDireccion(Enemigo.MOVER_IZQUIERDA);
                    }

                    break;

                case PacMan.MOVER_DERECHA:

                    if (pacMan.getPos().getY() > blinky.getPos().getY()) {
                        clyde.setMoveDireccion(Enemigo.MOVER_DERECHA);
                    } else {
                        clyde.setMoveDireccion(Enemigo.MOVER_IZQUIERDA);
                    }
                    break;
            }
        }
    }

    public void go() {
        TimerTask repaint = new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        };

        TimerTask timerStrawberry = new TimerTask() {
            @Override
            public void run() {
                if (controlScene != 0 && controlScene != 4) {
                    if (!strawberry.isVisible()) {

                        int aux1, aux2;
                        do {
                            aux1 = random.nextInt(Constantes.NUM_CELDA - 1);
                            aux2 = random.nextInt(Constantes.NUM_CELDA - 1);
                        } while (scene.mapa(aux1, aux2) == 1);

                        strawberry.setPosicion(aux1, aux2);

                        strawberry.setVisible(true);
                        strawberry.setTransposable(false);

                    } else {

                        strawberry.setVisible(false);
                        strawberry.setTransposable(true);
                    }
                }
            }
        };
        TimerTask timerCherry = new TimerTask() {
            @Override
            public void run() {
                if (controlScene != 0 && controlScene != 4) {
                    if (!cherry.isVisible()) {
                        int aux1, aux2;
                        do {
                            aux1 = random.nextInt(Constantes.NUM_CELDA - 1);
                            aux2 = random.nextInt(Constantes.NUM_CELDA - 1);
                        } while (scene.mapa(aux1, aux2) == 1);

                        strawberry.setPosicion(aux1, aux2);

                        cherry.setVisible(true);
                        cherry.setTransposable(false);

                    } else {

                        cherry.setVisible(false);
                        cherry.setTransposable(true);
                    }
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(repaint, 0, Constantes.RETRASAR);
        timer.schedule(timerStrawberry, Constantes.TIMER_FRESA, 15000);
        timer.schedule(timerCherry, Constantes.TIMER_CEREZA, 15000);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int aux = controlScene;
        switch (aux) {
            case 0:
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE:
                        controlScene = 1;
                        newScene(controlScene);
                        break;

                    case KeyEvent.VK_Q:
                        if (JOptionPane.showConfirmDialog(null,
                                "¿ Desea realmente salir ?", "Salir", JOptionPane.YES_NO_OPTION) == 0) {
                            System.exit(0);
                        }
                        break;

                    case KeyEvent.VK_S:
                        //ObjectInputStream Score;
                        /*try {
                            Score = new ObjectInputStream(new FileInputStream("./src/data/save"));
                            Guardar saveClass = (Guardar) Score.readObject();
                            Score.close();

                            scene = new Escenario1();
                            scene.setBlock("brick.png");

                            pacMan = saveClass.pacMan;
                            blinky = saveClass.blinky;
                            inky = saveClass.inky;
                            pinky = saveClass.pinky;
                            clyde = saveClass.clyde;
                            cherry = saveClass.cereza;
                            strawberry = saveClass.frutilla;
                            elemArray = saveClass.elemArray;
                            enemys = saveClass.enemigos;
                            scene.setBolitas(saveClass.bolitas);
                            scene.setBolitaPoder(saveClass.bolitasPoder);
                            scene.setParedes(saveClass.paredes);
                            this.controlScene = saveClass.controlScenario;

                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(PantallaJuego.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException | ClassNotFoundException ex) {
                            Logger.getLogger(PantallaJuego.class.getName()).log(Level.SEVERE, null, ex);
                        }*/
                        break;
                }
                break;

            case 4:
                if (e.getKeyCode() == KeyEvent.VK_Q) {
                    if (JOptionPane.showConfirmDialog(null,
                            "¿ Desea realmente salir ?", "Salir", JOptionPane.YES_NO_OPTION) == 0) {
                        System.exit(0);
                    }
                }
                break;

            default:
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        pacMan.setVuelta(true);
                        pacMan.setSiguienteDireccion(PacMan.MOVER_ARRIBA);
                        break;

                    case KeyEvent.VK_DOWN:
                        pacMan.setVuelta(true);
                        pacMan.setSiguienteDireccion(PacMan.MOVER_ABAJO);
                        break;

                    case KeyEvent.VK_LEFT:
                        pacMan.setVuelta(true);
                        pacMan.setSiguienteDireccion(PacMan.MOVER_IZQUIERDA);
                        break;

                    case KeyEvent.VK_RIGHT:
                        pacMan.setVuelta(true);
                        pacMan.setSiguienteDireccion(PacMan.MOVER_DERECHA);
                        break;

                    case KeyEvent.VK_SPACE:
                        pacMan.setVuelta(true);
                        pacMan.setSiguienteDireccion(PacMan.PARAR);
                        break;

                    case KeyEvent.VK_S:
                        /*try {
                            Guardar saveClass = new Guardar();
                            
                            /*saveClass.pacMan = pacMan;
                            saveClass.blinky = blinky;
                            saveClass.inky = inky;
                            saveClass.pinky = pinky;
                            saveClass.clyde = clyde;
                            saveClass.cereza = cherry;
                            saveClass.frutilla = strawberry;
                            saveClass.elemArray = elemArray;
                            saveClass.enemigos = enemys;
                            saveClass.bolitas = scene.getBolitas();
                            saveClass.bolitasPoder = scene.getBolitaPoder();
                            saveClass.paredes = scene.getParedes();
                            saveClass.controlScenario = this.controlScene;
                            //ObjectOutputStream save = new ObjectOutputStream(new FileOutputStream("./src/data/save"));
                            //save.writeObject(saveClass);
                            //save.close();
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(PantallaJuego.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(PantallaJuego.class.getName()).log(Level.SEVERE, null, ex);
                        }*/

                    default:
                        break;
                }

                break;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ejercicio-03.03_Game_PacMan");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocation(new java.awt.Point(20, 20));
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int aux = controlScene;
        switch (aux) {
            case 0:
                int a1 = (Constantes.NUM_CELDA * Constantes.TAMANIO_CELDA) / 2;
                int x1 = e.getPoint().x;
                int y1 = e.getPoint().y;

                if ((100 <= y1 && y1 <= 160) && (a1 - 110 <= x1 && x1 <= a1 + 110)) {
                    controlScene = 1;
                    newScene(controlScene);

                } else if ((200 <= y1 && y1 <= 260) && (a1 - 110 <= x1 && x1 <= a1 + 110)) {

                    //ObjectInputStream load;
                    /*try {
                        load = new ObjectInputStream(new FileInputStream("./src/data/save"));
                        Guardar saveClass = (Guardar) load.readObject();
                        load.close();*/

                        scene = new Escenario1();
                        scene.setBlock("brick.png");

                        /*pacMan = saveClass.pacMan;
                        blinky = saveClass.blinky;
                        inky = saveClass.inky;
                        pinky = saveClass.pinky;
                        clyde = saveClass.clyde;
                        cherry = saveClass.cereza;
                        strawberry = saveClass.frutilla;
                        elemArray = saveClass.elemArray;
                        enemys = saveClass.enemigos;
                        scene.setBolitas(saveClass.bolitas);
                        scene.setBolitaPoder(saveClass.bolitasPoder);
                        scene.setParedes(saveClass.paredes);
                        this.controlScene = saveClass.controlScenario;

                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(PantallaJuego.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(PantallaJuego.class.getName()).log(Level.SEVERE, null, ex);
                    }*/

                } else if ((300 <= y1 && y1 <= 360) && (a1 - 110 <= x1 && x1 <= a1 + 110)) {

                    if (JOptionPane.showConfirmDialog(null,
                            "¿ Desea realmente salir ?", "Salir", JOptionPane.YES_NO_OPTION) == 0) {
                        System.exit(0);
                    }
                }

                break;

            case 4:
                int a2 = (Constantes.NUM_CELDA * Constantes.TAMANIO_CELDA) / 2;
                int x2 = e.getPoint().x;
                int y2 = e.getPoint().y;

                if ((350 <= y2 && y2 <= 410) && (a2 - 210 <= x2 && x2 <= a2 - 10)) {
                    controlScene = 0;
                    newScene(controlScene);
                } else if ((350 <= y2 && y2 <= 410) && (a2 + 10 <= x2 && x2 <= a2 + 210)) {

                    if (JOptionPane.showConfirmDialog(null,
                            "¿ Desea realmente salir ?", "Salir", JOptionPane.YES_NO_OPTION) == 0) {
                        System.exit(0);
                    }
                }
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    public void resetEnemyPac() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        this.pacMan.setPosicion(1, 1);
        this.pinky.setPosicion(9, 9);
        this.blinky.setPosicion(9, 9);
        this.clyde.setPosicion(9, 9);
        this.inky.setPosicion(9, 9);
    }
}
