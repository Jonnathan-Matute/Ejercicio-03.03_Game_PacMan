package Vista;

import Controlador.IniciarEscenario;
import Controlador.ControlJuego;
import Controlador.Guardar;
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

    // Controle de tela
    // 0 - Tela inicial
    // 1 - Primeira tela
    // 2 - Segunda tela
    // 3 - Terceira tela
    // 4 - Tela de fim do jogo
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

        // Cria cenario
        this.controlScene = 0;
        newScene(controlScene);
    }

    // Define qual será e Cria cenario com todos os seus elementos
    private void newScene(int scene) {
        switch (scene) {
            // Tela Inicial
            case 0:
                this.scene = new IniciarEscenario();

                // Total de vidas do pacman
                this.pacMan.setVida(3);

                // Resetar pontos
                this.pacMan.resetPuntajeTotal();

                break;

            // Tela 1
            case 1:
                this.scene = new Escenario1();
                this.scene.setBlock("brick.png");

                // Reseta posições
                resetEnemyPac();

                // Determinar posição para frutilla
                int aux1,
                 aux2;
                do {
                    aux1 = random.nextInt(Constantes.NUM_CELDA - 1);
                    aux2 = random.nextInt(Constantes.NUM_CELDA - 1);
                } while (this.scene.mapa(aux1, aux2) == 1);

                this.strawberry.setPosicion(aux1, aux2);
                this.addElement(strawberry);

                // Determinar posição para cereza
                do {
                    aux1 = random.nextInt(Constantes.NUM_CELDA - 1);
                    aux2 = random.nextInt(Constantes.NUM_CELDA - 1);
                } while (this.scene.mapa(aux1, aux2) == 1);

                this.cherry.setPosicion(aux1, aux2);
                this.addElement(cherry);

                break;

            // Tela 2
            case 2:
                this.scene = new Escenario2();
                this.scene.setBlock("brick.png");

                // Resetar posição
                resetEnemyPac();

                // Determinar posição para frutilla
                do {
                    aux1 = random.nextInt(Constantes.NUM_CELDA - 1);
                    aux2 = random.nextInt(Constantes.NUM_CELDA - 1);
                } while (this.scene.mapa(aux1, aux2) == 1);

                this.strawberry.setPosicion(aux1, aux2);
                this.addElement(strawberry);

                // Determinar posição para cereza
                do {
                    aux1 = random.nextInt(Constantes.NUM_CELDA - 1);
                    aux2 = random.nextInt(Constantes.NUM_CELDA - 1);
                } while (this.scene.mapa(aux1, aux2) == 1);

                this.cherry.setPosicion(aux1, aux2);
                this.addElement(cherry);

                break;

            // Tela 3
            case 3:
                this.scene = new Escenario3();
                this.scene.setBlock("brick.png");

                // Resetar posição
                resetEnemyPac();

                // Determinar posição para frutilla
                do {
                    aux1 = random.nextInt(Constantes.NUM_CELDA - 1);
                    aux2 = random.nextInt(Constantes.NUM_CELDA - 1);
                } while (this.scene.mapa(aux1, aux2) == 1);

                this.strawberry.setPosicion(aux1, aux2);
                this.addElement(strawberry);

                // Determinar posição para cereza
                do {
                    aux1 = random.nextInt(Constantes.NUM_CELDA - 1);
                    aux2 = random.nextInt(Constantes.NUM_CELDA - 1);
                } while (this.scene.mapa(aux1, aux2) == 1);

                this.cherry.setPosicion(aux1, aux2);
                this.addElement(cherry);

                break;

            // Game Over
            case 4:
                this.scene = new PantallaGameOver();
                break;

            case 5:

                break;
        }
    }

    // Adicionar elementos na lista
    public final void addElement(Elemento elem) {
        elemArray.add(elem);
    }

    // Remover elementos na lista
    public void removeElement(Elemento elem) {
        elemArray.remove(elem);
    }

    @Override
    public void paint(Graphics gOld) {
        Graphics g = getBufferStrategy().getDrawGraphics();
        Graphics g2 = g.create(getInsets().right, getInsets().top,
                getWidth() - getInsets().left, getHeight() - getInsets().bottom);

        // Pintar elementos
        this.controller.dibujarTodosElementos(scene, elemArray, g2, controlScene);

        // Se nao for a tela inicial nem a final
        if (controlScene != 0 && controlScene != 4) {

            // Controla o movimento do blinky
            setBlinkyMovDirection();

            // Controla o movimento do pinky
            setPinkyMovDirection();

            // Controla o movimento do inky
            setInkyMovDirection();

            // Controla o movimento do clyde
            setClydeMovDirection();

            // Verificar colisao entre elementos
            if (controller.procesarTodosElementos(scene, elemArray, enemys)) {

                // Remove uma vida do pacman
                pacMan.removerVida();

                // Retorna posições iniciais
                resetEnemyPac();

                // Verifica se acabou as vidas
                if (pacMan.getVida() == 0) {
                    System.out.println("Entrou");
                    this.controlScene = 4;
                    newScene(controlScene);
                    return;
                }
            }

            // Verifica se comeu todas as bolinhas
            if (scene.getBolitas().isEmpty() && scene.getBolitaPoder().isEmpty()) {
                controlScene++;
                newScene(controlScene);
            }

            // Desenhar informações
            int aux = Constantes.TAMANIO_CELDA * Constantes.NUM_CELDA;

            // Vidas
            for (int i = 0; i < pacMan.getVida(); i++) {
                g2.drawImage(imgLife, 10 + (32 * i), aux + 10, 30, 30, null);
            }

            // Frutas
            if (elemArray.contains(strawberry)) {
                g2.drawImage(strawberry.getImgElement().getImage(), 140, aux + 7, 30, 33, null);
            }

            if (elemArray.contains(cherry)) {
                g2.drawImage(cherry.getImgElement().getImage(), 180, aux + 7, 30, 33, null);
            }

            // Pontuação
            g2.drawImage(imgScore, 340, aux + 2, 75, 45, null);

            // Determinar pontos
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

    // Movimentar Blinky
    private void setBlinkyMovDirection() {
        // Verifica movimentação do blinky
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

    // Movimenta Inky
    private void setInkyMovDirection() {

        // Se a distância foi menor que 4, se mover igual ao Blinky.
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

    // Movimenta Pinky
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

    // Movimenta Clyde
    private void setClydeMovDirection() {

        // Se a distância foi menor que 4, se mover igual ao Blinky.
        clyde.setDistanciaPacman(clyde.getPos().getX(), clyde.getPos().getY(),
                pacMan.getPos().getX(), pacMan.getPos().getY());

        // Se a distancia for menor que 4, segue pacman
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
        // Timer para pintar a tela
        TimerTask repaint = new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        };

        // Time para frutilla
        TimerTask timerStrawberry = new TimerTask() {
            @Override
            public void run() {
                if (controlScene != 0 && controlScene != 4) {
                    if (!strawberry.isVisible()) {

                        // Determinar uma nova posição para frutilla
                        // a cada nova aparição
                        int aux1, aux2;
                        do {
                            aux1 = random.nextInt(Constantes.NUM_CELDA - 1);
                            aux2 = random.nextInt(Constantes.NUM_CELDA - 1);
                        } while (scene.mapa(aux1, aux2) == 1);

                        strawberry.setPosicion(aux1, aux2);

                        // Deixar fruta visivel
                        strawberry.setVisible(true);
                        strawberry.setTransposable(false);

                    } else {

                        // Deixar fruta invisivel
                        strawberry.setVisible(false);
                        strawberry.setTransposable(true);
                    }
                }
            }
        };

        // Time para cereza
        TimerTask timerCherry = new TimerTask() {
            @Override
            public void run() {
                if (controlScene != 0 && controlScene != 4) {
                    if (!cherry.isVisible()) {

                        // Determinar uma nova posição para cereza
                        // a cada nova aparição
                        int aux1, aux2;
                        do {
                            aux1 = random.nextInt(Constantes.NUM_CELDA - 1);
                            aux2 = random.nextInt(Constantes.NUM_CELDA - 1);
                        } while (scene.mapa(aux1, aux2) == 1);

                        strawberry.setPosicion(aux1, aux2);

                        // Deixar fruta visivel
                        cherry.setVisible(true);
                        cherry.setTransposable(false);

                    } else {

                        // Deixar fruta invisivel
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
            // Tela Inicial
            case 0:
                switch (e.getKeyCode()) {
                    //Caso o usuário digite espaço pausa o pacman
                    case KeyEvent.VK_SPACE:
                        controlScene = 1;
                        newScene(controlScene);
                        break;
                    
                    //Caso o usuário digite Q pergunta se deseja sair do jogo
                    case KeyEvent.VK_Q:
                        if (JOptionPane.showConfirmDialog(null,
                                "Deseja realmente sair ?", "Sair", JOptionPane.YES_NO_OPTION) == 0) {
                            System.exit(0);
                        }
                        break;
                    
                    //Caso o usuário digite L executa a operação de salvamento
                    case KeyEvent.VK_L:
                        ObjectInputStream load;
                        try {
                            load = new ObjectInputStream(new FileInputStream("./src/data/save"));
                            Guardar saveClass = (Guardar) load.readObject();
                            load.close();

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
                        }
                        break;
                }
                break;

            // Tela Final
            case 4:
                if (e.getKeyCode() == KeyEvent.VK_Q) {
                    if (JOptionPane.showConfirmDialog(null,
                            "Deseja realmente sair ?", "Sair", JOptionPane.YES_NO_OPTION) == 0) {
                        System.exit(0);
                    }
                }
                break;

            // Qualquer outra tela
            default:
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        // Setar movimentação do pacman para cima
                        pacMan.setVuelta(true);
                        pacMan.setSiguienteDireccion(PacMan.MOVER_ARRIBA);
                        break;

                    case KeyEvent.VK_DOWN:
                        // Setar movimentação do pacman para baixo
                        pacMan.setVuelta(true);
                        pacMan.setSiguienteDireccion(PacMan.MOVER_ABAJO);
                        break;

                    case KeyEvent.VK_LEFT:
                        // Setar movimentaçao do pacman para a esquerda
                        pacMan.setVuelta(true);
                        pacMan.setSiguienteDireccion(PacMan.MOVER_IZQUIERDA);
                        break;

                    case KeyEvent.VK_RIGHT:
                        // Setar movimentação do pacman para a direita
                        pacMan.setVuelta(true);
                        pacMan.setSiguienteDireccion(PacMan.MOVER_DERECHA);
                        break;

                    case KeyEvent.VK_SPACE:
                        // Pausa o pacman
                        pacMan.setVuelta(true);
                        pacMan.setSiguienteDireccion(PacMan.PARAR);
                        break;
                    
                    //Executa a operação de salvamento do jogo
                    case KeyEvent.VK_S:
                        try {
                            Guardar saveClass = new Guardar();
                            saveClass.pacMan = pacMan;
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
                            ObjectOutputStream save = new ObjectOutputStream(new FileOutputStream("./src/data/save"));
                            save.writeObject(saveClass);
                            save.close();
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(PantallaJuego.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(PantallaJuego.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    default:
                        break;
                }

                break;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SCC0604 - Pacman");
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
            // Tela inicial
            case 0:
                // Verifica se clicou em algum botao
                int a1 = (Constantes.NUM_CELDA * Constantes.TAMANIO_CELDA) / 2;
                int x1 = e.getPoint().x;
                int y1 = e.getPoint().y;

                // iniciar jogo
                if ((100 <= y1 && y1 <= 160) && (a1 - 110 <= x1 && x1 <= a1 + 110)) {
                    controlScene = 1;
                    newScene(controlScene);

                } else if ((200 <= y1 && y1 <= 260) && (a1 - 110 <= x1 && x1 <= a1 + 110)) {

                    // Iniciar jogo salvo
                    ObjectInputStream load;
                    try {
                        load = new ObjectInputStream(new FileInputStream("./src/data/save"));
                        Guardar saveClass = (Guardar) load.readObject();
                        load.close();

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
                    }

                } else if ((300 <= y1 && y1 <= 360) && (a1 - 110 <= x1 && x1 <= a1 + 110)) {

                    // Sair
                    if (JOptionPane.showConfirmDialog(null,
                            "Deseja realmente sair ?", "Sair", JOptionPane.YES_NO_OPTION) == 0) {
                        System.exit(0);
                    }
                }

                break;

            // Game Over
            case 4:
                // Verifica se clicou em algum botao
                int a2 = (Constantes.NUM_CELDA * Constantes.TAMANIO_CELDA) / 2;
                int x2 = e.getPoint().x;
                int y2 = e.getPoint().y;

                // Volta para a tela inicial
                if ((350 <= y2 && y2 <= 410) && (a2 - 210 <= x2 && x2 <= a2 - 10)) {
                    controlScene = 0;
                    newScene(controlScene);
                } else if ((350 <= y2 && y2 <= 410) && (a2 + 10 <= x2 && x2 <= a2 + 210)) {
                    // Sair
                    if (JOptionPane.showConfirmDialog(null,
                            "Deseja realmente sair ?", "Sair", JOptionPane.YES_NO_OPTION) == 0) {
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
