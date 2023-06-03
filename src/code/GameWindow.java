import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

public class GameWindow extends JPanel implements Runnable {
    static int gameWidth = 1920; // gameColumnAmount*ActualTileSize;
    static int gameHeight = 1080; // gameRowAmount*ActualTileSize;

    static int FPS = 60;


    /*
        Colors for all of the componenets and stuff
    */ 
    Color backgroundColor = Color.white;
    Color tableColor = Color.gray;

    Vector2 bottomLeft = new Vector2(0,gameHeight);
    Vector2 bottomRight = new Vector2(gameWidth,gameHeight);
    Vector2 topLeft = new Vector2(0,0);
    Vector2 topRight = new Vector2(0,gameWidth);    
    
    double tableHeight = gameHeight/3;
    Rectangle2D tableTop = new Rectangle2D.Double(bottomLeft.x, bottomLeft.y - tableHeight, gameWidth, tableHeight);
    
    static Image background = new Image("src/sprites/background.png", 1920, 1080, 1920/2,1080/2);

    static Image tray = new Image("src/sprites/tray.png", 500, 200, (int) (gameWidth*0.8),(int)  (gameHeight*0.8));


    static Image almond = new Image("src/sprites/almond.png", 50, 50, gameWidth/2 + 60, gameHeight/2);
    static Image banana = new Image("src/sprites/banana.png", 50, 50, gameWidth/2 - 60, gameHeight/2);
    static Image cheeto = new Image("src/sprites/cheeto.png", 50, 50, gameWidth/2 + 120 , gameHeight/2 );
    static Image chip = new Image("src/sprites/chip.png", 50, 50, gameWidth/2 - 120, gameHeight/2);
    static Image marshmellow = new Image("src/sprites/marshmellow.png", 50, 50, gameWidth/2 + 180, gameHeight/2);

    static String urlsList[] = {"src/sprites/banana.png", "src/sprites/almond.png", "src/sprites/cheeto.png"};
    static Image test = new Image("src/sprites/banana.png", 50, 50, gameWidth/2, gameHeight/2, urlsList, FPS/5);
    
    static Image[] food = {almond, banana, cheeto, chip};
    static Image[] props = {test,tray};

    Thread gameThread;

    
    public GameWindow() {
        this.setPreferredSize(new Dimension(gameWidth, gameHeight));
        this.setBackground(backgroundColor);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addMouseListener(new MouseInput());
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
        if(MouseInput.pressed){
            MouseInput.updateSelected();
        }

        test.cycleAnimation();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;
        //Drawing Code goes here;
        
        //Background

        background.drawImage(g);

        graphics.setColor(backgroundColor);
        graphics.drawRect(0,0, gameWidth, gameHeight);

        //Props
        graphics.setColor(tableColor);
        graphics.fill(tableTop);
        for(Image i : props){
            i.drawImage(g);
        }

        //Food
        for(Image i : food){
            i.drawImage(g);
        }
        
        graphics.dispose();
    }

}
