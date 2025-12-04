package levels;

import java.awt.Graphics;
import levels.LevelHandler;
import utils.LoadSave;

public class Level {

    private int[][] lvlData;
    public final int p1StartX = 200;
    public final int p1StartY = 200;
    public final int p2StartX = 250;
    public final int p2StartY = 200;

    public Level(int[][] lvlData){
        this.lvlData = lvlData;
    }

    public int getSpriteIndex(int x, int y){
        return lvlData[y][x];
    }

    public int[][] getLvlData(){
        return lvlData;
    }

}
