package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import core.Game;
import entities.Player;

public class Gem {

    private int x;
    private int y;
    private int width;
    private int height;
    private Rectangle hitbox;
    private int type; // 0 = Blue (Watergirl), 1 = Red (Fireboy)
    private boolean active = true;

    // Animation variables
    private float hoverOffset = 0;
    private float hoverDir = 0.05f * Game.SCALE;
    private int maxHover = 5;

    public Gem(int x, int y, int width, int height, int type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        initHitbox();
    }

    private void initHitbox() {
        int w = (int) (width * 0.7);
        int h = (int) (height * 0.7);
        hitbox = new Rectangle(x + (width - w) / 2, y + (height - h) / 2, w, h);
    }

    public void update() {
        updateHover();
    }

    private void updateHover() {
        hoverOffset += hoverDir;
        if (hoverOffset >= maxHover || hoverOffset <= 0) {
            hoverDir *= -1;
        }
    }

    public boolean checkCollect(Player p, int playerType) {
        if (!active) {
            return false;
        }

        if (playerType == type && p.getHitbox().intersects(hitbox)) {
            active = false;
            return true;
        }
        return false;
    }

    public void draw(Graphics g) {
        if (!active) {
            return;
        }

        if (type == 0) {
            g.setColor(new Color(30, 150, 255));
        } else {
            g.setColor(new Color(200, 50, 0));
        }

        int drawY = (int) (y + hoverOffset);
        int centerX = x + width / 2;
        int centerY = drawY + height / 2;
        int halfW = width / 2 - 2;
        int halfH = height / 2 - 2;

        int[] xPoints = {centerX, centerX + halfW, centerX, centerX - halfW};
        int[] yPoints = {centerY - halfH, centerY, centerY + halfH, centerY};

        g.fillPolygon(xPoints, yPoints, 4);

        g.setColor(Color.WHITE);
        g.drawPolygon(xPoints, yPoints, 4);
    }

    public void setActive(boolean isActive) {
        active = isActive;
    }

    public boolean isActive() {
        return active;
    }
}
