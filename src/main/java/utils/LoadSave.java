package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import core.Game;
import levels.Level;

public class LoadSave {

    public static final String PLAYER_ATLAS = "sprites.png";
    public static final String LEVEL_ATLAS = "level_sprites.png";
    public static final String LEVEL1_DATA = "level1.png";

    public static final String SCORES_FILE = "scores.dat";

    public static BufferedImage GetSpriteAtlas(String filename) {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + filename);
        try {
            img = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    public static Level GetLevelData() {
        BufferedImage img = GetSpriteAtlas(LEVEL1_DATA);
        int h = Game.TILES_IN_HEIGHT;
        int w = Game.TILES_IN_WIDTH;

        int[][] lvlData = new int[h][w];
        int[][] greenData = new int[h][w];
        int[][] blueData = new int[h][w];

        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();

                if (r >= 42) {
                    r = 6;
                }
                lvlData[j][i] = r;
                greenData[j][i] = g;
                blueData[j][i] = b;
            }
        }
        return new Level(lvlData, greenData, blueData);
    }

    public static void SaveScores(String filename, List<Score> scores) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SCORES_FILE))) {
            oos.writeObject(scores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Score> LoadScores(String filename) {
        File f = new File(filename);
        if (!f.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            return (List<Score>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
