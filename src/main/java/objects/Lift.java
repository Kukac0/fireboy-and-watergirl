package objects;

import java.awt.Graphics;

public class Lift {

    private int x;
    private int y;
    private int width;
    private int height;
    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    private int speedX;
    private int speedY;

    public Lift(int x, int y, int width, int height, int tragetX, int targetY, int speedX, int speedY) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.minX = Math.min(x, tragetX);
        this.maxX = Math.max(x, tragetX);
        this.minY = Math.min(y, targetY);
        this.maxY = Math.max(y, targetY);
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void update() {
        x += speedX;
        y += speedY;

        if (x < minX || x > maxX) {
            speedX = -speedX;
        }
        if (y < minY || y > maxY) {
            speedY = -speedY;
        }
    }

    public void draw(Graphics g) {
        // Logic to draw the lift
    }

}
