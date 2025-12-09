package core;

import java.awt.Graphics;

import entities.Fireboy;
import entities.Player;
import entities.Watergirl;
import gamestates.GameState;
import static gamestates.GameState.MENU;
import static gamestates.GameState.PLAYING;
import static gamestates.GameState.QUIT;
import gamestates.Menu;
import gamestates.Won;
import levels.Level;
import levels.LevelHandler;
import objects.ObjectHandler;
import utils.ScoreHandler;

public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private static final int FPS_SET = 120;
    private static final int UPS_SET = 200;

    private Player player1, player2;
    private LevelHandler levelHandler;
    private Won won;
    private Menu menu;
    private long startTime;
    private ScoreHandler scoreHandler;
    private ObjectHandler objectHandler;

    public static final int TILES_DEFAULT_SIZE = 24;
    public static final float SCALE = 1.0f;
    public static final float CHARACTER_SCALE = 2.0f;
    public static final int TILES_IN_WIDTH = 39;
    public static final int TILES_IN_HEIGHT = 29;
    public static final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public static final int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public static final int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    public Game() {
        initClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();

    }

    private void initClasses() {
        scoreHandler = new ScoreHandler();
        menu = new Menu(this);
        won = new Won(this);
        player1 = new Watergirl(0, 0);
        player2 = new Fireboy(0, 0);
        levelHandler = new LevelHandler(this);
        objectHandler = new ObjectHandler(this);
        player1.loadLvlData(levelHandler.getCurrentLevel().getLvlData());
        player2.loadLvlData(levelHandler.getCurrentLevel().getLvlData());
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void startNewGame() {
        resetPlayers();
        startTime = System.currentTimeMillis();
        GameState.state = PLAYING;
    }

    public void update() {
        switch (GameState.state) {
            case MENU ->
                menu.update();
            case PLAYING -> {
                player1.update();
                player2.update();
                levelHandler.update();
                objectHandler.update();
            }
            case WON ->
                won.update();
            case QUIT ->
                System.exit(0);
        }
    }

    public void render(Graphics g) {
        switch (GameState.state) {
            case MENU ->
                menu.draw(g);
            case PLAYING -> {
                levelHandler.draw(g);
                player1.render(g);
                player2.render(g);
                objectHandler.draw(g);
            }
            case WON -> {
                won.draw(g);
            }
            case QUIT -> {
            }
        }
    }

    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;

            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }

    }

    public void windowFocusLost() {
        player1.reserDir();
        player2.reserDir();
    }

    public LevelHandler getLevelHandler() {
        return levelHandler;
    }

    public ScoreHandler getScoreHandler() {
        return scoreHandler;
    }

    public long getStartTime() {
        return startTime;
    }

    public Won getWon() {
        return won;
    }

    private void resetPlayers() {
        Level level = levelHandler.getCurrentLevel();
        player1.setLocation(level.p1StartX, level.p1StartY);
        player2.setLocation(level.p2StartX, level.p2StartY);

        player1.reset();
        player2.reset();
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}
