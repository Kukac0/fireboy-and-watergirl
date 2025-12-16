package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import static core.Game.SCALE;
import entities.Player;

public class Fluid {

    private int x;
    private int y;
    private int width;
    private int height;
    private Rectangle hitbox;
    private int type; // 0 = Water, 1 = Lava, 2 = Poison/Goo

    public Fluid(int x, int y, int width, int height, int type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        this.hitbox = new Rectangle(x, y + 4 * (int) SCALE, width, height - 4 * (int) SCALE);
    }

    public void update() {
        // Animation logic (bubbling) could go here
    }

    public void draw(Graphics g) {
        // Set Color based on type
        if (type == 0) {
            g.setColor(new Color(30, 150, 255)); // Water 
        } else if (type == 1) {
            g.setColor(new Color(200, 50, 0)); // Lava 
        } else {
            g.setColor(new Color(50, 230, 0)); // Poison 
        }

        g.fillRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    public boolean touched(Player p, int playerType) {
        if (p.getHitbox().intersects(hitbox)) {
            switch (type) {
                case 0 -> {
                    return playerType == 1;
                }
                case 1 -> {
                    return playerType == 0;
                }
                case 2 -> {
                    return true;
                }
                default -> {
                }
            }
        }
        return false;
    }

}
