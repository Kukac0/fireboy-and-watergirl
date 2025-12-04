package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import core.Game;
import utils.Score;
import utils.ScoreHandler;

public class Menu {

    private Game game;
    private ScoreHandler scoreHandler;

    public Menu(Game game) {
        this.game = game;
        this.scoreHandler = new ScoreHandler();

        if (scoreHandler.getScores().isEmpty()) {
            scoreHandler = new ScoreHandler();
        }
    }

    private void drawCenteredString(Graphics g, String text, int y) {
        int textWidth = g.getFontMetrics().stringWidth(text);
        int x = (Game.GAME_WIDTH - textWidth) / 2;
        g.drawString(text, x, y);
    }

    public void update() {
        //future animations may go here
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.setColor(Color.ORANGE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        drawCenteredString(g, "FIREBOY & WATERGIRL", 100);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        drawCenteredString(g, "Press ENTER to play", 200);
        drawCenteredString(g, "Press ESC to quit", 240);

        g.setColor(Color.CYAN);
        drawCenteredString(g, "--- Best Times ---", 320);

        int y = 360;
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        
        if (scoreHandler.getScores().isEmpty()) {
            drawCenteredString(g, "No Scores Yet", y);
        } else {
            for (Score s : scoreHandler.getScores()) {
                drawCenteredString(g, s.toString(), y);
                y += 30;
            }
        }
    }

}
