package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import core.GamePanel;
import entities.Player;
import gamestates.GameState;
import static gamestates.GameState.PLAYING;

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
        Player player1 = gamePanel.getGame().getPlayer1();
        Player player2 = gamePanel.getGame().getPlayer2();
        if (GameState.state == PLAYING){
            switch(e.getKeyCode()){
                case KeyEvent.VK_W -> player1.setJump(false);
                case KeyEvent.VK_A -> player1.setLeft(false);
                case KeyEvent.VK_D -> player1.setRight(false);
                
                case KeyEvent.VK_UP -> player2.setJump(false);
                case KeyEvent.VK_LEFT -> player2.setLeft(false);
                case KeyEvent.VK_RIGHT -> player2.setRight(false);
                default -> {
                }
            }
        }  
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Player player1 = gamePanel.getGame().getPlayer1();
        Player player2 = gamePanel.getGame().getPlayer2();
        switch (GameState.state) {
            case MENU -> {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    GameState.state = GameState.PLAYING;
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    GameState.state = GameState.QUIT;
                }
            }
            case PLAYING -> {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_W: player1.setJump(true); break;
                    case KeyEvent.VK_A: player1.setLeft(true); break;
                    case KeyEvent.VK_D: player1.setRight(true); break;

                    case KeyEvent.VK_UP: player2.setJump(true); break;
                    case KeyEvent.VK_LEFT: player2.setLeft(true); break;
                    case KeyEvent.VK_RIGHT: player2.setRight(true); break;
                    
                    case KeyEvent.VK_T: GameState.state = GameState.WON; break; //for testing
                    default: break;
                }
            }        
            
            case WON -> {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    gamePanel.getGame().getWon().saveScore();
                }
            }
        }
    }

}
