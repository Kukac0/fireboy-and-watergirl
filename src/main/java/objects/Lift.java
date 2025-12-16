package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Lift {

    private float x;
    private float y;
    private int width;
    private int height;
    private int id;
    private Rectangle hitbox;
    private int startY;
    private int targetY;
    private float speed;
    private boolean isActive;

    public Lift(int x, int y, int width, int height, int targetY, float speed, int id) {
        this.x = x;
        this.y = y;
        this.startY = y;
        this.targetY = targetY;
        this.width = width;
        this.height = height;
        this.hitbox = new Rectangle(x, y, width, height);
        this.id = id;
        this.speed = speed;
        this.isActive = false;
    }

    public void update() {
        float destY = isActive ? targetY : startY;

        if (y < destY) {
            y += speed;
            if (y > destY) {
                y = destY;
            }
        } else if (y > destY) {
            y -= speed;
            if (y < destY) {
                y = destY;
            }
        }

        hitbox.y = (int) y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect((int) x, (int) y, width, height);

        g.setColor(Color.BLACK);
        g.drawRect((int) x, (int) y, width, height);
    }

    public void reset() {
        y = startY;
        isActive = false;
    }

    public void setY(float y) {
        this.y = y;
        hitbox.y = (int) y;
    }

    public int getId() {
        return id;
    }

    public int getTarget() {
        return targetY;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
