package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import core.Game;

public class Lost {

    private Game game;

    public Lost(Game game) {
        this.game = game;
    }

    public void update() {
        //For future animations
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        String text = "GAME OVER";
        int textWidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, (Game.GAME_WIDTH - textWidth) / 2, 150);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));

        String restartInfo = "Press R To Restart Level";
        int restartWidth = g.getFontMetrics().stringWidth(restartInfo);
        g.drawString(restartInfo, (Game.GAME_WIDTH - restartWidth) / 2, 250);

        String menuInfo = "Press ESC To Return To Menu";
        int menuWidth = g.getFontMetrics().stringWidth(menuInfo);
        g.drawString(menuInfo, (Game.GAME_WIDTH - menuWidth) / 2, 300);
    }
}
