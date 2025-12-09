package objects;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Button {

    private int x;
    private int y;
    private boolean isPressed;
    private Rectangle hitbox;

    public Button(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.isPressed = false;
        this.hitbox = new Rectangle(x, y, width, height);
    }

    public void update() {
        // Logic to update button state
    }

    public void draw(Graphics g) {
        // Logic to draw the button
    }

    public boolean isPressed() {
        return isPressed;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
