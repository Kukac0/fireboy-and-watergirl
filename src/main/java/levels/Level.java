package levels;

import java.awt.Button;
import java.util.ArrayList;
import java.util.List;

import objects.Door;

public class Level {

    private int[][] lvlData;
    private int[][] greenData;
    private int[][] blueData;
    private List<Button> buttons = new ArrayList<>();
    private List<Door> doors = new ArrayList<>();

    public final int p1StartX = 200;
    public final int p1StartY = 200;
    public final int p2StartX = 250;
    public final int p2StartY = 200;

    public Level(int[][] lvlData, int[][] greenData, int[][] blueData) {
        this.lvlData = lvlData;
        this.greenData = greenData;
        this.blueData = blueData;
    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    public int[][] getLvlData() {
        return lvlData;
    }

    public int[][] getGreen() {
        return greenData;
    }

    public int[][] getBlue() {
        return blueData;
    }

}
