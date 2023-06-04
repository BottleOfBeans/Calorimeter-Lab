import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
        if(e.getButton() == MouseEvent.BUTTON3){
            GameWindow.resetFoods();
            
        }
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
            if (dist.magnitude() < i.interactableRadius){
                selected = i;
            }
        }
        for(Image i : GameWindow.dragabbles){            
            Vector2 dist = mouseVector.returnSubtract(i.currentPos);
            if (dist.magnitude() < i.interactableRadius){
                selected = i;
            }
        }

        // thermomter
        Vector2 dist = mouseVector.returnSubtract(GameWindow.thermometer.image.currentPos);
        if (dist.magnitude() < GameWindow.thermometer.image.interactableRadius){
            selected = GameWindow.thermometer.image;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
        if (selected == GameWindow.matchStick){
            GameWindow.matchStick.currentPos = new Vector2 (GameWindow.gameWidth*0.3+60 ,  GameWindow.gameHeight*0.8);
        }
        else if (selected == GameWindow.thermometer.image){
            GameWindow.thermometer.image.currentPos = new Vector2 (300 ,  300);
        }
        
        
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