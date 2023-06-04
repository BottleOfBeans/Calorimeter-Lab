import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Image extends JPanel{

    public Vector2 currentPos;
    public String url;
    public int width;
    public int height;
    public double interactableRadius;
    public BufferedImage selfImage;
    public String[] animURLS;
    public int switchTicks = 0;
    public int FPS;
    public int animationIndex = 0;

    public Graphics2D graphics;
    
    public Image(String url, int width, int height, int xPos, int yPos){
        
        this.url = url;
        this.width = width;
        this.height = height;
        this.currentPos = new Vector2(xPos,yPos);
        this.interactableRadius = Math.sqrt(width*width + height* height);
        interactableRadius = 50;
        this.setType();
        this.setSize();
    }


    public Image(String url, int width, int height, int xPos, int yPos, String[] animationURLs, int FPS){
        
        this.url = url;
        this.width = width;
        this.height = height;

        this.animURLS = animationURLs;
        this.FPS = FPS;
        
        this.currentPos = new Vector2(xPos,yPos);
        this.interactableRadius = Math.sqrt(width*width + height* height);
        //interactableRadius = 50;
        this.setType();
        this.setSize();
    }

    public void cycleAnimation(){
        switchTicks += 1;
        if (switchTicks == FPS){
            switchTicks = 0;
            animationIndex += 1;

            if (animationIndex == animURLS.length){
                animationIndex = 0;
            }
            
            this.url = this.animURLS[animationIndex];
            this.setType();
            this.setSize();
        }
    }
   
    public void setType(){
        try{
            selfImage = ImageIO.read(new FileInputStream(url));
        }
          catch(IOException e){
            e.printStackTrace();
        }
    }    

    public static BufferedImage toBufferedImage(java.awt.Image img){
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return bimage;
    }


    public void setSize(){
        java.awt.Image img = selfImage.getScaledInstance(this.width, this.height, BufferedImage.SCALE_DEFAULT);
        selfImage = toBufferedImage(img);
    }

    public void drawImage(Graphics g){
       Graphics2D graphics = (Graphics2D) g;
       graphics.setColor(Color.red);
       //graphics.fill(new Ellipse2D.Double(currentPos.x - interactableRadius, currentPos.y - interactableRadius, interactableRadius*2, interactableRadius*2));
       graphics.drawImage(selfImage, null, (int) currentPos.x - this.width/2, (int) currentPos.y - this.height/2);
    }
}