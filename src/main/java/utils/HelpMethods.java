package utils;

import static core.Game.GAME_HEIGHT;
import static core.Game.GAME_WIDTH;
import static core.Game.TILES_SIZE;

public class HelpMethods {

    public static boolean CanMoveHere(float x, float y, int width, int height, int[][] lvlData) {

        return (!IsSolid(x, y, lvlData)
                && !IsSolid(x + width, y + height, lvlData)
                && !IsSolid(x + width, y, lvlData)
                && !IsSolid(x, y + height, lvlData)
                && !IsSolid(x + width, y + height / 2, lvlData) //because the cahracter model is taller than a block
                && !IsSolid(x, y + height / 2, lvlData));

    }

    public static boolean IsSolid(float x, float y, int[][] lvlData) {
        if (x < 0 || x >= GAME_WIDTH) {
            return true;
        }
        if (y < 0 || y >= GAME_HEIGHT) {
            return true;
        }

        float xIndex = x / TILES_SIZE;
        float yIndex = y / TILES_SIZE;

        int value = lvlData[(int) yIndex][(int) xIndex];

        if (IsSlope(value)) {
            return false;
        }

        if (value == 36) {
            return (y % TILES_SIZE) >= (TILES_SIZE / 2);
        }

        return (value >= 42 || value < 0 || value != 6);
    }

    public static boolean IsSlope(int id) {
        return (id == 21 || id == 22 || id == 28 || id == 29 || id == 35 || id == 37 || id == 38 || id == 39);
    }

}
