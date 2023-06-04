import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.Font;

import javax.swing.*;

public class Thermometer extends JPanel{

    public Image image;

    public int temp;
    public int max_temp = 100;

    public int labelWidth = 100;
    public int labelHeight = 50;

    public int fontsize = 12;


    public Thermometer(Image image, int temp){
        this.image = image;
        this.temp = temp;
    }

    public int getHeight(){
        int height = (temp/max_temp) * image.height;
        return height;
    }


    public void drawImage(Graphics a){
        
        Graphics2D g= (Graphics2D) a;

        image.drawImage(g);

        g.setColor(Color.GRAY);
        g.fill(new Rectangle2D.Double((int)image.currentPos.x-image.width/2, (int)image.currentPos.y-image.height/2, image.width, image.height));

        g.setColor(Color.RED);
        g.fill(new Rectangle2D.Double((int)image.currentPos.x, (int)image.currentPos.y - (image.height - getHeight()) - image.height/2, 10, this.getHeight()));

        //Text and Text Label
        Vector2 labelPos = new Vector2(image.currentPos.x - image.width/2, image.currentPos.y-labelHeight-image.height/2);
        g.setColor(Color.WHITE);
        g.fill(new Rectangle2D.Double((int)labelPos.x, (int)labelPos.y, labelWidth, labelHeight));

        g.setColor(Color.RED);
        g.setFont(new Font("Serif", Font.BOLD, fontsize));
        g.drawString(temp+"^C", (int)labelPos.x + labelWidth/2 - fontsize/2, (int)labelPos.y + labelHeight/2);

    }
}