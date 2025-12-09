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
    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    private float speedX;
    private float speedY;
    private boolean isActive;
    private boolean vertical;

    public Lift(int x, int y, int width, int height, int targetX, int targetY, float speed, int id) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hitbox = new Rectangle(x, y, width, height);
        this.id = id;
        this.isActive = false;

        this.minX = Math.min(x, targetX);
        this.maxX = Math.max(x, targetX);
        this.minY = Math.min(y, targetY);
        this.maxY = Math.max(y, targetY);

        if (x != targetX) {
            this.speedX = speed;
        }
        if (y != targetY) {
            this.speedY = speed;
        }

        if (x != targetX) {
            vertical = false;
        } else {
            vertical = true;
        }
    }

    public void update() {
        float tempSpeed = Math.max(speedX, speedY);

        if (x < minX || x > maxX) {
            tempSpeed = 0;
        }
        if (y < minY || y > maxY) {
            tempSpeed = 0;
            y = (int) y;
        }

        if (isActive && vertical) {
            x += 0;
            y += tempSpeed;
        } else if (isActive && !vertical) {
            x += tempSpeed;
            y += 0;
        } else if (!isActive && vertical) {
            x += 0;
            y -= tempSpeed;
        } else if (!isActive && !vertical) {
            x -= tempSpeed;
            y += 0;
        }

        hitbox.x = (int) x;
        hitbox.y = (int) y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect((int) x, (int) y, width, height);
    }

    public int getId() {
        return id;
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
