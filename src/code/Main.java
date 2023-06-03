
import javax.swing.*;

public class Main {
    public static void main(String args[]) {
      
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("JPL Hackathon Calorimeter Lab");
        GameWindow gameWindow = new GameWindow();
        window.add(gameWindow);
        window.setUndecorated(true);
        window.pack();
        window.setVisible(true);
        gameWindow.startWindowThread();

    }

}
