import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

public class GameWindow extends JPanel implements Runnable {
    /*
        Colors for all of the componenets and stuff
    */ 
    Color backgroundColor = Color.white;
    Color tableColor = Color.gray;

    Vector2 bottomLeft = new Vector2(0,gameHeight);
    Vector2 bottomRight = new Vector2(gameWidth,gameHeight);
    Vector2 topLeft = new Vector2(0,0);
    Vector2 topRight = new Vector2(0,gameWidth);    
    
    double tableHeight = 400;
    Rectangle2D tableTop = new Rectangle2D.Double(bottomLeft.x, bottomLeft.y - tableHeight, gameWidth, tableHeight);
    
    Image bananaObj = new Image("/src/sprites/banana.png", 200, 200, gameWidth/2, gameHeight/2);
    Image robloxObj = new Image("/src/sprites/roblox.png", 32, 32, gameWidth/3, gameHeight/2);
    
    Image[] food = {bananaObj,robloxObj};

    Image foodInHand = null;
    
    static int gameWidth = 1920; // gameColumnAmount*ActualTileSize;
    static int gameHeight = 1080; // gameRowAmount*ActualTileSize;
    int FPS = 144;
    Thread gameThread;

    
    public GameWindow() {
        this.setPreferredSize(new Dimension(gameWidth, gameHeight));
        this.setBackground(backgroundColor);
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


    //Ignore Everything Above
    /*
     * =========================================================================================================================================================================================
     * =========================================================================================================================================================================================
     * =========================================================================================================================================================================================
     */


    public void update(double deltaTime) {
        //Update code goes here:
        /*think about the heat loss
            heat is loss while the calorimeter and water is being heated up
            heat is lost  from fire to air
            not
ll of the food is convered to energy - some of the heat is left on the food and it cools down
             a       */
        // tempW = m * 4.184 * q
        ;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;
        //Drawing Code goes here;
        
        //Background
        graphics.setColor(backgroundColor);
        graphics.drawRect(0,0, gameWidth, gameHeight);

        //Table
        graphics.setColor(tableColor);
        graphics.fill(tableTop);

        //Food
        for(Image i : food){
            i.drawImage(g);
        }

        graphics.dispose();
    }

}
