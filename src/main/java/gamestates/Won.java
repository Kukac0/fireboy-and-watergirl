package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JOptionPane;

import core.Game;
import utils.Score;

public class Won {

    private Game game;
    private int completionTime;

    public Won(Game game) {
        this.game = game;
    }

    public void setCompletionTime() {
        this.completionTime = (int) ((System.currentTimeMillis() - game.getStartTime()) / 1000);
    }

    public void update() {
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

        int currentRed = game.getLevelHandler().getCurrentRedGems();
        int maxRed = game.getLevelHandler().getCurrentLevel().getMaxRedGems();
        int currentBlue = game.getLevelHandler().getCurrentBlueGems();
        int maxBlue = game.getLevelHandler().getCurrentLevel().getMaxBlueGems();

        g.setFont(new Font("Arial", Font.BOLD, 25));

        // Red Gem Stats
        g.setColor(Color.RED);
        String redText = "Red Gems: " + currentRed + " / " + maxRed;
        int redWidth = g.getFontMetrics().stringWidth(redText);
        g.drawString(redText, (Game.GAME_WIDTH - redWidth) / 2, 300);

        // Blue Gem Stats
        g.setColor(Color.CYAN);
        String blueText = "Blue Gems: " + currentBlue + " / " + maxBlue;
        int blueWidth = g.getFontMetrics().stringWidth(blueText);
        g.drawString(blueText, (Game.GAME_WIDTH - blueWidth) / 2, 350);

        // 5. Perfect Score Bonus Text
        if (currentRed == maxRed && currentBlue == maxBlue) {
            g.setColor(Color.GREEN);
            String perfect = "PERFECT SCORE!";
            int perfectWidth = g.getFontMetrics().stringWidth(perfect);
            g.drawString(perfect, (Game.GAME_WIDTH - perfectWidth) / 2, 400);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        String saveInfo = "Press ENTER To Save Your Score";
        int saveWidth = g.getFontMetrics().stringWidth(saveInfo);
        g.drawString(saveInfo, (Game.GAME_WIDTH - saveWidth) / 2, 500);

        g.setFont(new Font("Arial", Font.PLAIN, 20));
        String closeInfo = "Press ESC To Return To The Menu";
        int closeWidth = g.getFontMetrics().stringWidth(closeInfo);
        g.drawString(closeInfo, (Game.GAME_WIDTH - closeWidth) / 2, 550);

        // Completion Time
        int minute = this.completionTime / 60;
        int second = this.completionTime % 60;
        String timeStr = "Your Time: " + String.format("%02d:%02d", minute, second);
        int timeWidth = g.getFontMetrics().stringWidth(timeStr);
        g.setColor(Color.YELLOW);
        g.drawString(timeStr, (Game.GAME_WIDTH - timeWidth) / 2, 250);
    }

    public void saveScore() {
        String name = JOptionPane.showInputDialog(null,
                "Good Job!\nWhat's your name?",
                "Saving Score",
                JOptionPane.QUESTION_MESSAGE);

        if (name != null && !name.trim().isEmpty()) {
            Score score = new Score(name, this.completionTime);
            game.getScoreHandler().addScore(score);
        }
        GameState.state = GameState.MENU;
    }
}
