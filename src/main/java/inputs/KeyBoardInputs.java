package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import core.GamePanel;
import entities.Player;

public class KeyBoardInputs implements KeyListener{

    private GamePanel gamePanel;

    public KeyBoardInputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        Player player = gamePanel.getGame().getPlayer();
        switch(e.getKeyCode()){
            case KeyEvent.VK_W -> player.setUp(false);
            case KeyEvent.VK_A -> player.setLeft(false);
            case KeyEvent.VK_S -> player.setDown(false);
            case KeyEvent.VK_D -> player.setRight(false);
            case KeyEvent.VK_UP -> player.setUp(false);
            case KeyEvent.VK_LEFT -> player.setLeft(false);
            case KeyEvent.VK_DOWN -> player.setDown(false);
            case KeyEvent.VK_RIGHT -> player.setRight(false);
            default -> {
            }
        }  
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Player player = gamePanel.getGame().getPlayer();
        switch(e.getKeyCode()){
            case KeyEvent.VK_W -> player.setUp(true);
            case KeyEvent.VK_A -> player.setLeft(true);
            case KeyEvent.VK_S -> player.setDown(true);
            case KeyEvent.VK_D -> player.setRight(true);
            case KeyEvent.VK_UP -> player.setUp(true);
            case KeyEvent.VK_LEFT -> player.setLeft(true);
            case KeyEvent.VK_DOWN -> player.setDown(true);
            case KeyEvent.VK_RIGHT -> player.setRight(true);
            default -> {
            }
        }
    }

}
