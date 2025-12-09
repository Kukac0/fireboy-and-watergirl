package objects;

public class Box {

    private int x;
    private int y;
    private int width;
    private int height;

    public Box(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void update() {
        // Logic to update box state
    }

    public void draw(java.awt.Graphics g) {
        // Logic to draw the box
    }
}
