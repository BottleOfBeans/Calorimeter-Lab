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

    
    public int fontsize = 12;

    public int labelWidth = fontsize*4;
    public int labelHeight = fontsize;


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

        g.setColor(Color.RED);
        g.fill(new Rectangle2D.Double(image.currentPos.x - 5, image.currentPos.y - (image.height - ((temp) * image.height)) - image.height/2, 10, (temp) * image.height));

        //Text and Text Label
        Vector2 labelPos = new Vector2(image.currentPos.x - image.width/2, image.currentPos.y-labelHeight-image.height/2);
        g.setColor(Color.WHITE);
        g.fill(new Rectangle2D.Double((int)labelPos.x, (int)labelPos.y, labelWidth, labelHeight));

        g.setColor(Color.RED);
        g.setFont(new Font("Roboto", Font.BOLD, fontsize));
        
        if (temp == -1){g.drawString("°C", (int)labelPos.x + labelWidth/2 - fontsize/2 - 5, (int)labelPos.y + labelHeight/2 + 5);}
        else{g.drawString(temp+"°C", (int)labelPos.x + labelWidth/2 - fontsize/2 - 5, (int)labelPos.y + labelHeight/2 + 5);}

    }
}