
import java.text.SimpleDateFormat;
import java.util.Date;

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

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);        
        String date  = simpleDateFormat.format(new Date());
        
        System.out.println("""
            \n\n\n\n\n\n\n
            Instance 
            ============================
            At:    
                """ + date + "\n\n\n\n");
    }

}
