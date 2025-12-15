package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Button {

    private int x;
    private int y;
    private int width;
    private int height;
    private boolean isPressed;
    private Rectangle hitbox;
    private int id;

    public Button(int x, int y, int width, int height, int id) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isPressed = false;
        this.id = id;
        this.hitbox = new Rectangle(x, y, width, height);
    }

    public void update() {
        // Logic to update button state
    }

    public void draw(Graphics g) {
        if (isPressed) {
            g.setColor(new Color(100, 100, 100));
            g.fillRect(x, y + height / 2, width, height / 2);
        } else {
            g.setColor(Color.RED);
            g.fillRect(x, y, width, height);
        }
    }

    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public int getId() {
        return id;
    }
}
