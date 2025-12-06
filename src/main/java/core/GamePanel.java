package core;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import static core.Game.GAME_HEIGHT;
import static core.Game.GAME_WIDTH;
import inputs.KeyBoardInputs;
import inputs.MouseInputs;


public class GamePanel extends JPanel{

    private MouseInputs mouseInputs;
    private Game game;
    
    public GamePanel(Game game){
        mouseInputs = new MouseInputs(this);
        this.game = game;

        setPanelSize();
        addKeyListener(new KeyBoardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        setFocusable(true);
    }

    private void setPanelSize(){
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
    }

    public void updateGame(){
    
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame(){
        return game;
    }
}
