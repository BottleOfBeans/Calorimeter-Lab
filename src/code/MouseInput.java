import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

/**
 * Creates mouse input listener for panel.
 */
public class MouseInput implements MouseListener
{
    static double xPos;
    static double yPos;
    public static boolean pressed = false;
    static Image selected;

    @Override
    public void mouseClicked(MouseEvent e){   
        ;
    }
    
    public static void updateSelected() {
        if (selected != null){
            xPos = MouseInfo.getPointerInfo().getLocation().getX();
            yPos = MouseInfo.getPointerInfo().getLocation().getY();
            Vector2 mouseVector = new Vector2(xPos,yPos);
            if(selected != null){
                selected.currentPos = mouseVector;
            }
        }
        
    }

    @Override
    public void mousePressed(MouseEvent e) {

        pressed = true;
        xPos = e.getX();
        yPos = e.getY();
        Vector2 mouseVector = new Vector2(xPos, yPos);

        for(Image i : GameWindow.food){            
            Vector2 dist = mouseVector.returnSubtract(i.currentPos);
            System.out.print("Dist: "+dist.magnitude());
            if (dist.magnitude() < i.interactableRadius){
                selected = i;
                System.out.println("Clicked on food!");
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        selected = null;
        pressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        ;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        ;
    }
}