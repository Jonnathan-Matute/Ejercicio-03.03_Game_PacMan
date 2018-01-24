package Vista;

/**
 *
 * @author Alex Reinoso
 */
public class Principal {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                PantallaJuego screen = new PantallaJuego();
                screen.setVisible(true);
                screen.createBufferStrategy(2);
                screen.go();
            }
        });
    }
}
