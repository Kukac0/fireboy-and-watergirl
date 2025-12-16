package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Door {

    private int x;
    private int y;
    private int width;
    private int height;
    private boolean isOpen;
    private int id;
    private Rectangle hitbox;

    public Door(int x, int y, int width, int height, int id) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        this.isOpen = false;
        this.hitbox = new Rectangle(x, y, width, height);
    }

    public void update() {
        // Logic to update door state
    }

    public void draw(Graphics g) {
        if (!isOpen) {
            g.setColor(new Color(84, 46, 14));
            g.fillRect(x, y, width, height);
        } else {
            g.setColor(new Color(60, 30, 10));
            g.drawRect(x, y, width, height);
        }
    }

    public void reset() {
        isOpen = false;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public int getId() {
        return id;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
