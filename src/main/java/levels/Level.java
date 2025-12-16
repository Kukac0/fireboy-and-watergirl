package levels;

import static core.Game.TILES_SIZE;

public class Level {

    private int[][] lvlData;
    private int[][] greenData;
    private int[][] blueData;

    public int p1StartX;
    public int p1StartY;
    public int p2StartX;
    public int p2StartY;

    public Level(int[][] lvlData, int[][] greenData, int[][] blueData) {
        this.lvlData = lvlData;
        this.greenData = greenData;
        this.blueData = blueData;
    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    public void setStartPos() {
        for (int i = 0; i < getLvlData().length; i++) {
            for (int j = 0; j < getLvlData()[0].length; j++) {
                int id = getGreen()[i][j];

                if (id == 100) {
                    p1StartX = j * TILES_SIZE;
                    p1StartY = i * TILES_SIZE;
                }
                if (id == 101) {
                    p2StartX = j * TILES_SIZE;
                    p2StartY = i * TILES_SIZE;
                }
            }
        }
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
