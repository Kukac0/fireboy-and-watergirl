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

import javax.imageio.ImageIO;

import core.Game;

public class LoadSave {

    public static final String PLAYER_ATLAS = "sprites.png";
    public static final String LEVEL_ATLAS = "level_sprites.png";
    public static final String LEVEL1_DATA = "level1.png";

    public static final String SCORES_FILE = "scores.dat";

    public static BufferedImage GetSpriteAtlas(String filename){
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

    public static int[][] GetLevelData(){
        int[][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
        BufferedImage img = GetSpriteAtlas(LEVEL1_DATA);

        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++){
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value >= 42)
                    value = 6;
                lvlData[j][i] = value;
            }
        return lvlData;
    }
  
    public static void SaveScores(String filename, ArrayList<Score> scores){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SCORES_FILE))) {
            oos.writeObject(scores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Score> LoadScores(String filename){
        File f = new File(SCORES_FILE);
        if (!f.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            return (ArrayList<Score>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
