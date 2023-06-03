import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

/**
 * Creates mouse input listener for panel.
 */
public class MouseInput implements MouseListener
{
    
    @Override
    public void mouseClicked(MouseEvent e)
    {   
        double xPos = e.getX() / GameWindow.zoomX;
        double yPos = e.getY() / GameWindow.zoomY;
        
        Particle part = new Particle(new Vector2(xPos, yPos), generateRandomDirectionVector(5), 4);
        
        if(e.getButton() == 3){
            part = new Particle(new Vector2(xPos, yPos),new Vector2(3,0), 0);
        }
        
        GameWindow.particles.add(part);
    }

    public Vector2 generateRandomDirectionVector(double gSpeed){
        Random rand = new Random();
        double randomXVelo = rand.nextInt(-10,10);
        double randomYVelo = rand.nextInt(-10,10);
        Vector2 gVelo = new Vector2(randomXVelo, randomYVelo);
        gVelo = gVelo.normalize().returnMultiply(gSpeed);
        return gVelo; 
    }

    @Override
    public void mousePressed(MouseEvent e) {
        ;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        ;
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