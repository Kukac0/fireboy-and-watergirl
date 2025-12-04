package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JOptionPane;

import core.Game;
import static gamestates.GameState.MENU;
import utils.Score;

public class Won {

    private Game game;
    private int completionTime;

    public Won(Game game) {
        this.game = game;
        this.completionTime = (int)(System.currentTimeMillis()/1000) - (int)(game.getStartTime()/1000);
    }

    public void update(){
        // For future winning animations or effects
    }

    public void draw(Graphics g) {
        // Background overlay
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        // Text
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        String text = "LEVEL COMPLETED!";
        int textWidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, (Game.GAME_WIDTH - textWidth) / 2, 150);

        g.setFont(new Font("Arial", Font.PLAIN, 20));
        String info = "Press ENTER To Save Your Score";
        int infoWidth = g.getFontMetrics().stringWidth(info);
        g.drawString(info, (Game.GAME_WIDTH - infoWidth) / 2, 250);
        
        // Completion Time
        int minute = completionTime / 60;
        int second = completionTime % 60;
        String timeStr = "Your Time: " + String.format("%02d:%02d", minute, second);
        int timeWidth = g.getFontMetrics().stringWidth(timeStr);
        g.setColor(Color.YELLOW);
        g.drawString(timeStr, (Game.GAME_WIDTH - timeWidth) / 2, 300);
    }
    
    public void saveScore() {
        long currentTime = System.currentTimeMillis();
        int timeInSeconds = (int) ((currentTime - game.getStartTime()) / 1000);

        String name = JOptionPane.showInputDialog(null, 
            "Good Job!\nWhat's your name?", 
            "Enter Name",
            JOptionPane.QUESTION_MESSAGE);

        if (name != null && !name.trim().isEmpty()) {
            Score score = new Score(name, timeInSeconds);
            game.getScoreHandler().addScore(score);
        }

        GameState.state = MENU;
    }
}
