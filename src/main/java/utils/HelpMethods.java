package utils;

import core.Game;

public class HelpMethods {

    public static boolean CanMoveHere(float x, float y, int width, int height, int[][] lvlData){

        return (!IsSolid(x, y, lvlData) 
                && !IsSolid(x + width, y + height, lvlData)
                    && !IsSolid(x + width, y, lvlData)
                        && !IsSolid(x, y + height, lvlData)
                            && !IsSolid(x + width, y + height/2, lvlData)   //because the cahracter model is taller than a block
                                && !IsSolid(x, y + height/2, lvlData));

    }

    public static boolean IsSolid(float x, float y, int[][] lvlData){
        if (x < 0 || x >= Game.GAME_WIDTH)
            return true;
        if (y < 0 || y >= Game.GAME_HEIGHT)
            return true;
        
        float xIndex = x/Game.TILES_SIZE;
        float yIndex = y/Game.TILES_SIZE;

        int value = lvlData[(int)yIndex][(int)xIndex];

        return (value >= 42 || value < 0 || value != 6);
        }
        
}
