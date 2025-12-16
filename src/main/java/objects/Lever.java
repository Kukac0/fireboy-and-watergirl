package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Lever {

    private int x;
    private int y;
    private int width;
    private int height;
    private boolean isOn;
    private Rectangle hitbox;
    private int id;
    private boolean isTouchedByPlayer;

    public Lever(int x, int y, int width, int height, int id) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        this.isOn = false;
        this.hitbox = new Rectangle(x, y, width, height);
        this.isTouchedByPlayer = false;
    }

    public void update() {
        // for future animations
    }

    public void draw(Graphics g) {
        if (isOn) {
            g.setColor(Color.GREEN);
            g.fillRect(x, y + height / 2, width / 2, height / 2);
            g.setColor(Color.ORANGE);
            g.fillRect(x, y + height, width, height / -5);
        } else {
            g.setColor(Color.RED);
            g.fillRect(x + width / 2, y + height / 2, width / 2, height / 2);
            g.setColor(Color.ORANGE);
            g.fillRect(x, y + height, width, height / -5);
        }
    }

    public void touch(boolean isPlayerTouching) {
        if (isPlayerTouching && !this.isTouchedByPlayer) {
            isOn = !isOn;
        }

        this.isTouchedByPlayer = isPlayerTouching;
    }

    public void reset() {
        isOn = false;
        isTouchedByPlayer = false;
    }

    public boolean isOn() {
        return isOn;
    }

    public int getId() {
        return id;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
