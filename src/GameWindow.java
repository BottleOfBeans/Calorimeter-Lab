import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;

public class GameWindow extends JPanel implements Runnable {

    static int gameWidth = 1920; // gameColumnAmount*ActualTileSize;
    static int gameHeight = 1080; // gameRowAmount*ActualTileSize;

    int FPS = 144;

    public GameWindow() {
        this.setPreferredSize(new Dimension(gameWidth, gameHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }

    public void startWindowThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;
            if (delta >= 1) {
                update(delta);
                repaint();
                delta--;
            }

        }
    }

    public void update(double deltaTime) {
        //Update code goes here:

        ;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;
        //Drawing Code goes here;

        graphics.dispose();
    }

}
