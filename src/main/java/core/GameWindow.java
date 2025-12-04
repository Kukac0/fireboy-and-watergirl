package core;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

public class GameWindow extends JFrame{

    public GameWindow(GamePanel gamePanel){
        
        setTitle("Fireboy & Watergirl");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(gamePanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        addWindowFocusListener(new WindowFocusListener() {

            @Override
            public void windowGainedFocus(WindowEvent e) {

            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame().windowFocusLost();
            }
            
        });
    }
}
