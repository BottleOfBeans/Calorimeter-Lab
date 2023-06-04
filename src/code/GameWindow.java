import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class GameWindow extends JPanel implements Runnable {
    static int gameWidth = 1920; // gameColumnAmount*ActualTileSize;
    static int gameHeight = 1080; // gameRowAmount*ActualTileSize;

    static int FPS = 60;
    static int weight = 0;

    /*
        Colors for all of the componenets and stuff
    */ 
    Color backgroundColor = Color.white;
    Color tableColor = Color.gray;

    Vector2 bottomLeft = new Vector2(0,gameHeight);
    Vector2 bottomRight = new Vector2(gameWidth,gameHeight);
    Vector2 topLeft = new Vector2(0,0);
    Vector2 topRight = new Vector2(0,gameWidth);    

    static int room_temperature = 23;
    static int temperature = room_temperature;
    static int finalTemperature = room_temperature;

    static int massOfWater = 100; //100g
    
    double tableHeight = gameHeight/3;
    Rectangle2D tableTop = new Rectangle2D.Double(bottomLeft.x, bottomLeft.y - tableHeight, gameWidth, tableHeight);
    
    static Image background = new Image("src/sprites/backgroundDark1.png", 2000, 1100, 1920/2 ,1080/2);
    static Image instructions = new Image("src/sprites/instructions.png", 500, 900, 1920/2 ,1080/2);
    
    static Image scale = new Image("src/sprites/scale.png", 500, 200, (int) (gameWidth/2),(int)  (gameHeight*0.8));
    static Image bowl = new Image("src/sprites/bowl.png", 500, 200, (int) (gameWidth/2),(int)  (gameHeight*0.8)-100);
    Rectangle2D bowlHitbox = new Rectangle2D.Double((int) (gameWidth/2) - 250,(int)  (gameHeight*0.8)-150,500, 100);

    static Image tray = new Image("src/sprites/tray.png", 500, 200, (int) (gameWidth*0.8),(int)  (gameHeight*0.8));
    static Image ringStand = new Image("src/sprites/ringStand.png", 500, 800,  (int) (gameWidth*0.2),(int)  (gameHeight*0.5));
    static Image cokeCan = new Image("src/sprites/cokecan.png", 400, 400,  (int) (gameWidth*0.2)+60,(int)  (gameHeight*0.4));
    Rectangle2D canHitbox = new Rectangle2D.Double((int) (gameWidth*0.2)+60 - 50,(int)  (gameHeight*0.4) - 200, 100, 400);

    static Image matchStick = new Image("src/sprites/matchStick.png", 200, 100,  (int) (gameWidth*0.3)+60,(int)  (gameHeight*0.8));

    static int[] foodMasses = {9, 45, 2, 3, 8}; // almond, banana, cheeto, chip, marshmallow
    static double[] finalMasses = {4, 20, 0.5, 1, 3}; // same order

    static double[] heatContents = {6.0, 0.01, 5.7, 5.7, 3.1}; // same order

    static double[] changeTemp = {
        ((foodMasses[0] - finalMasses[0]) * heatContents[0] * 1000 * 4.184) / (4.184 * massOfWater),
        ((foodMasses[1] - finalMasses[1]) * heatContents[1] * 1000 * 4.184) / (4.184 * massOfWater),
        ((foodMasses[2] - finalMasses[2]) * heatContents[2] * 1000 * 4.184) / (4.184 * massOfWater),
        ((foodMasses[3] - finalMasses[3]) * heatContents[3] * 1000 * 4.184) / (4.184 * massOfWater),
        ((foodMasses[4] - finalMasses[4]) * heatContents[4] * 1000 * 4.184) / (4.184 * massOfWater)};
    
    static Image almond = new Image("src/sprites/almond.png", 50, 50, (int) (gameWidth*0.8 - 0),(int)  (gameHeight*0.8), foodMasses[0], changeTemp[0], finalMasses[0]);
    static Image banana = new Image("src/sprites/banana.png", 50, 50, (int) (gameWidth*0.8 + 90),(int)  (gameHeight*0.8), foodMasses[1], changeTemp[1], finalMasses[1]);
    static Image cheeto = new Image("src/sprites/cheeto.png", 50, 50, (int) (gameWidth*0.8 - 90),(int)  (gameHeight*0.8), foodMasses[2], changeTemp[2], finalMasses[2]);
    static Image chip = new Image("src/sprites/chip.png", 50, 50, (int) (gameWidth*0.8 + 170),(int)  (gameHeight*0.8), foodMasses[3], changeTemp[3], finalMasses[3]);
    static Image marshmellow = new Image("src/sprites/marshmellow.png", 50, 50, (int) (gameWidth*0.8 - 170),(int)  (gameHeight*0.8), foodMasses[4], changeTemp[4], finalMasses[4]);
    
    static Image thermometer_image = new Image("src/sprites/thermometer.png", 50, 150, 300,300);
    static Thermometer thermometer = new Thermometer(thermometer_image, -1);

    static String testUrlsList[] = {"src/sprites/banana.png", "src/sprites/almond.png", "src/sprites/cheeto.png"};
    static Image test = new Image("src/sprites/banana.png", 50, 50, gameWidth/2, gameHeight/2, testUrlsList, FPS/5);
    
    static Image[] food = {almond, banana, cheeto, chip, marshmellow};
    static Image[] dragabbles = {instructions,matchStick};
    static Image[] props = {test, tray, scale, bowl, ringStand, cokeCan};

    ArrayList<Image> scaleContainer = new ArrayList<Image>();
    ArrayList<Image> burnContainer = new ArrayList<Image>();


    Thread gameThread;

    static Ellipse2D burnHitbox = new Ellipse2D.Double(419 - 50,661 - 50, 100,100);
    
    // 419 661 --> Burning Hitbox Center
    
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
        //==============================

        //===============================
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

    public static void resetFoods(){
        //if you want to move all the foods back to the tray
        food[0].currentPos = new Vector2(gameWidth*0.8 - 0, gameHeight*0.8);
        food[1].currentPos = new Vector2(gameWidth*0.8 + 90, gameHeight*0.8);
        food[2].currentPos = new Vector2(gameWidth*0.8 - 90, gameHeight*0.8);
        food[3].currentPos = new Vector2(gameWidth*0.8 + 170, gameHeight*0.8);
        food[4].currentPos = new Vector2(gameWidth*0.8 - 170, gameHeight*0.8);
    
        for(Image i : food){
            if(i.burned){
                i.lighten();
            }
            i.burned = false;
            i.mass = i.prevMass;
        }
        temperature = room_temperature;
        finalTemperature = room_temperature;
        
        /*
        //If you want to reset the foods and create new foods
        food[0] = new Image("src/sprites/almond.png", 50, 50, (int) (gameWidth*0.8 - 0),(int)  (gameHeight*0.8), 9);
        food[1] = new Image("src/sprites/banana.png", 50, 50, (int) (gameWidth*0.8 + 90),(int)  (gameHeight*0.8), 45);
        food[2] = new Image("src/sprites/cheeto.png", 50, 50, (int) (gameWidth*0.8 - 90),(int)  (gameHeight*0.8), 2);
        food[3] = new Image("src/sprites/chip.png", 50, 50, (int) (gameWidth*0.8 + 170),(int)  (gameHeight*0.8), 3);
        food[4] = new Image("src/sprites/marshmellow.png", 50, 50, (int) (gameWidth*0.8 - 170),(int)  (gameHeight*0.8), 8);
    */
   /*
    static Image almond = new Image("src/sprites/almond.png", 50, 50, (int) (gameWidth*0.8 - 0),(int)  (gameHeight*0.8), 9);
    static Image banana = new Image("src/sprites/banana.png", 50, 50, (int) (gameWidth*0.8 + 90),(int)  (gameHeight*0.8), 45);
    static Image cheeto = new Image("src/sprites/cheeto.png", 50, 50, (int) (gameWidth*0.8 - 90),(int)  (gameHeight*0.8), 2);
    static Image chip = new Image("src/sprites/chip.png", 50, 50, (int) (gameWidth*0.8 + 170),(int)  (gameHeight*0.8), 3);
    static Image marshmellow = new Image("src/sprites/marshmellow.png", 50, 50, (int) (gameWidth*0.8 - 170),(int)  (gameHeight*0.8), 8);
    */
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
       
        updateScale(g);
        updateThermometer();
        updateBurn();
        stabalizeTemps();
        
        for(Image i : dragabbles){
            i.drawImage(g);
        }

        //Food
        for(Image i : food){
            i.drawImage(g);
        }

        thermometer.drawImage(g);

        //graphics.fill(bowlHitbox);
        //graphics.fill(burnHitbox);
        //graphics.fill(canHitbox);

        graphics.dispose();
    }

    public void updateScale(Graphics g){
        // 822, 844
        int labelWidth = 100;
        int labelHeight = 50;    
        int fontsize = 30;
        g.setColor(Color.orange);

        scaleContainer.clear();
        scale.mass = 0;

        for(Image i: food){
            Point2D p = new Point2D.Double(i.currentPos.x, i.currentPos.y);
            if(bowlHitbox.contains(p)){
                scaleContainer.add(i);
            }
        }
        for(Image i: scaleContainer){
            scale.mass += i.mass;
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Serif", Font.BOLD, fontsize));
        g.drawString(scale.mass+"g", (int)822 + labelWidth/2 - fontsize/2, (int)855 + labelHeight/2);
    }

    public void updateThermometer(){
        //canHitbox
        Point2D p = new Point2D.Double(thermometer.image.currentPos.x, thermometer.image.currentPos.y);
        if(canHitbox.contains(p)){
            thermometer.temp = temperature;
        }
        else{
            thermometer.temp = room_temperature;
        }
    }

    public void updateBurn(){

        burnContainer.clear();

        for(Image i : food){
            Point2D p = new Point2D.Double(i.currentPos.x, i.currentPos.y);
            if(burnHitbox.contains(p) && !i.burned){
                i.currentPos = new Vector2(burnHitbox.getCenterX(), burnHitbox.getCenterY());
                burnContainer.add(i);
            }
        }   
        Point2D p = new Point2D.Double(matchStick.currentPos.x, matchStick.currentPos.y);
        if(burnHitbox.contains(p) && !burnContainer.isEmpty()){
            flame();
        }
    }

    public void flame(){
        for(Image i : burnContainer){
            i.burned = true;
            i.mass = i.finalMass;
            finalTemperature += i.tempreture;
            i.darken(0.25f);
            //f is after demical to show that it is a flaot type
            //should be from 0 - 1
        }
        System.out.println("The food has been burned (lit 100%)");
    }

    public void stabalizeTemps(){
        temperature = finalTemperature;
    }

}
