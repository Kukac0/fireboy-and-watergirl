package objects;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Door {

    private int x;
    private int y;
    private boolean isOpen;
    private Rectangle hitbox;

    public Door(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.isOpen = false;
        this.hitbox = new Rectangle(x, y, width, height);
    }

    public void update() {
        // Logic to update door state
    }

    public void draw(Graphics g) {
        // Logic to draw the door
    }

    public boolean isOpen() {
        return isOpen;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
