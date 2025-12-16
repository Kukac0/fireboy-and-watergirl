package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import static core.Game.SCALE;
import static utils.HelpMethods.CanMoveHere;

public class Box {

    private float x;
    private float y;
    private int startX;
    private int startY;
    private int width;
    private int height;
    private Rectangle hitbox;

    private float friction = 0.9f;
    private float xSpeed = 0;
    private float gravity = 1.0f * SCALE;

    public Box(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this.width = width;
        this.height = height;
        this.hitbox = new Rectangle(x, y, width, height);
    }

    public void update(int[][] lvlData) {
        updateX(lvlData);
        updateY(lvlData);
        updateHitbox();
    }

    public void draw(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

        g.setColor(Color.BLACK);
        g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

    }

    private void updateX(int[][] lvlData) {
        if (xSpeed != 0) {
            if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
                x += xSpeed;
            } else {
                xSpeed = 0;
            }

            xSpeed *= friction;

            if (Math.abs(xSpeed) < 0.1f) {
                xSpeed = 0;
            }
        }
    }

    private void updateY(int[][] lvlData) {
        if (CanMoveHere(x, y + gravity, width, height, lvlData)) {
            y += gravity;
        }
    }

    public void push(float pushSpeed) {
        this.xSpeed = pushSpeed;
    }

    private void updateHitbox() {
        hitbox.x = (int) x;
        hitbox.y = (int) y;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void reset() {
        y = startY;
        x = startX;
    }
}
