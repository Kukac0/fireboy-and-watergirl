package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import static core.Game.SCALE;
import entities.Player;

public class Exit {

    private int x;
    private int y;
    private int width;
    private int height;
    private Rectangle hitbox;
    private int owner;  //0 = Watergirl, 1 = Fireboy

    private boolean active = false;
    private long timeStart = 0;
    private boolean playerInExit = false;

    public Exit(int x, int y, int width, int height, int owner) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.owner = owner;
        this.hitbox = new Rectangle(x, y, width, height);
    }

    public void update() {
        //for future animations
    }

    public void draw(Graphics g) {
        Color baseColor;
        if (owner == 0) {
            baseColor = new Color(30, 150, 255);;
        } else {
            baseColor = new Color(200, 50, 0);
        }

        g.setColor(baseColor.darker());
        g.fillRect(x, y, width, height);

        if (active) {
            g.setColor(baseColor.darker().darker());
        } else {
            g.setColor(baseColor);
        }

        g.fillRect(x + 4 * (int) SCALE, y + 4 * (int) SCALE, width - 8 * (int) SCALE, height - 4 * (int) SCALE);
    }

    public void checkExit(Player player, int playerType) {
        if (playerType == owner) {
            if (player.getHitbox().intersects(hitbox)) {
                if (!playerInExit) {
                    playerInExit = true;
                    timeStart = System.currentTimeMillis();
                } else {
                    if (System.currentTimeMillis() - timeStart >= 1000) {
                        active = true;
                    }
                }
            } else {
                playerInExit = false;
                active = false;
            }
        }
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public boolean isActive() {
        return active;
    }
}
