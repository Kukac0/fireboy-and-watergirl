package objects;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Exit {

    private int x;
    private int y;
    private int width;
    private int height;
    private Rectangle hitbox;
    private int owner;  //0 = Watergirl, 1 = Fireboy

    public Exit(int x, int y, int width, int height, int owner) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.owner = owner;
        this.hitbox = new Rectangle(x, y, width, height);
    }

    public void update() {
        // Logic to update exit state
    }

    public void draw(Graphics g) {
        // Logic to draw the exit
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

}
