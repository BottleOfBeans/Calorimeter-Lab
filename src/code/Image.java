import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.Toolkit;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;

public class Image extends JPanel{

    public Vector2 currentPos;
    public String url;
    public int width;
    public int height;
    public double interactableRadius;
    public BufferedImage selfImage;
    
    public Image(String url, int width, int height, int xPos, int yPos){
        
        this.url = url;
        this.width = width;
        this.height = height;
        this.currentPos = new Vector2(xPos,yPos);
        this.interactableRadius = Math.sqrt(width*width + height* height);
        this.setType();
        this.setSize();
    }
   
    public void setType(){
        try{
            selfImage = ImageIO.read(getClass().getResourceAsStream(url));
        }
        catch(IOException e ){
            e.printStackTrace();
        }
    }

    public void setSize(){
        //Set the size of the bufferedImage (name : selfImage)        BufferedImage resizedImage = new BufferedImage(this.width, this.height);  
    }


    public void drawImage(Graphics g){
        Graphics2D graphics = (Graphics2D) g;
        graphics.drawImage(selfImage, null, (int) currentPos.x, (int) currentPos.y);
    }

}