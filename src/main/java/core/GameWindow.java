package core;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class GameWindow extends JFrame {

    public GameWindow(GamePanel gamePanel) {

        setTitle("Fireboy & Watergirl");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createMenuBar(gamePanel);
        add(gamePanel);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        gamePanel.requestFocus();

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

    private void createMenuBar(GamePanel gamePanel) {
        JMenuBar menuBar = new JMenuBar();

        //File menu
        JMenu fileMenu = new JMenu("File");

        JMenuItem newItem = new JMenuItem("New Game");
        newItem.addActionListener(e -> {
            gamePanel.getGame().startNewGame();
            gamePanel.requestFocus();
        });

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(newItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        //Levels menu
        JMenu levelMenu = new JMenu("Levels");

        JMenuItem level1Item = new JMenuItem("Level 1");
        level1Item.addActionListener(e -> {
            gamePanel.getGame().loadLevel(1);
            gamePanel.requestFocus();
        });

        JMenuItem level2Item = new JMenuItem("Level 2");
        level2Item.addActionListener(e -> {
            gamePanel.getGame().loadLevel(2);
            gamePanel.requestFocus();
        });

        levelMenu.add(level1Item);
        levelMenu.add(level2Item);

        //Info menu
        JMenu infoMenu = new JMenu("Info");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Fireboy & Watergirl Clone\nMade by: Bedő Márton\nHomework Projekt",
                    "About",
                    JOptionPane.INFORMATION_MESSAGE);
            gamePanel.requestFocus();
        });
        infoMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(levelMenu);
        menuBar.add(infoMenu);

        this.setJMenuBar(menuBar);
    }
}
