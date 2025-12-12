package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import core.Game;
import utils.LoadSave;

public class LevelHandler {

    private Game game;
    private BufferedImage[] levelSprite;
    private Level level1;

    public LevelHandler(Game game) {
        this.game = game;
        importLevelSprites();
        level1 = LoadSave.GetLevelData();
    }

    private void importLevelSprites() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[42];
        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 7; i++) {
                int index = j * 7 + i;
                levelSprite[index] = img.getSubimage(i * 24, j * 24, 24, 24);
            }
        }
    }

    public void draw(Graphics g) {

        for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
            for (int i = 0; i < Game.TILES_IN_WIDTH; i++) {
                int index = level1.getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], i * Game.TILES_SIZE, j * Game.TILES_SIZE, Game.TILES_SIZE, Game.TILES_SIZE, null);
            }
        }
    }

    public void update() {

    }

    public Level getCurrentLevel() {
        return level1;
    }

}
