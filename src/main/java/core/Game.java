package core;

import java.awt.Graphics;

import entities.Fireboy;
import entities.Player;
import entities.Watergirl;
import gamestates.GameState;
import static gamestates.GameState.MENU;
import static gamestates.GameState.PLAYING;
import static gamestates.GameState.QUIT;
import gamestates.Lost;
import gamestates.Menu;
import gamestates.Won;
import levels.Level;
import levels.LevelHandler;
import objects.ObjectHandler;
import utils.ScoreHandler;

/*
    * Main game class that manages the game loop, updates, and rendering.
    * This class implements the Runnable interface to run the game on a separate thread.
 */
public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private static final int FPS_SET = 120;
    private static final int UPS_SET = 200;

    private Player player1, player2;
    private LevelHandler levelHandler;
    private Won won;
    private Lost lost;
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

    /*
    * Constructs a new Game instance and initializes all game components.
    * Creates the game window, panel, and starts the game loop thread.
     */
    public Game() {
        initClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();

    }

    /**
     * Initializes all game classes including players, handlers, and game
     * states. Sets up the initial level data for both players.
     */
    private void initClasses() {
        scoreHandler = new ScoreHandler();
        menu = new Menu(this);
        won = new Won(this);
        lost = new Lost(this);
        player1 = new Watergirl(0, 0);
        player2 = new Fireboy(0, 0);
        levelHandler = new LevelHandler(this);
        objectHandler = new ObjectHandler(this);
        player1.loadLvlData(levelHandler.getCurrentLevel().getLvlData());
        player2.loadLvlData(levelHandler.getCurrentLevel().getLvlData());
    }

    /**
     * Starts the game loop on a new thread.
     */
    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Starts a new game by resetting players, objects, and level. Records the
     * start time and sets the game state to PLAYING.
     */
    public void startNewGame() {
        resetPlayers();
        objectHandler.reset();
        levelHandler.reset();
        startTime = System.currentTimeMillis();
        GameState.state = PLAYING;
    }

    /**
     * Updates the game state based on the current GameState. Delegates update
     * logic to the appropriate handler (menu, playing, won, lost).
     */
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
            case LOST ->
                lost.update();
            case QUIT ->
                System.exit(0);
        }
    }

    /**
     * Renders the game based on the current GameState. Draws the appropriate
     * screen (menu, gameplay, won, or lost).
     *
     * @param g the Graphics context to draw on
     */
    public void render(Graphics g) {
        switch (GameState.state) {
            case MENU ->
                menu.draw(g);
            case PLAYING -> {
                levelHandler.drawBackground(g);
                objectHandler.draw(g);
                levelHandler.draw(g);
                player1.render(g);
                player2.render(g);
            }
            case WON -> {
                won.draw(g);
            }
            case LOST -> {
                lost.draw(g);
            }
            case QUIT -> {
            }
        }
    }

    /**
     * Main game loop implementation. Uses a fixed timestep pattern with
     * separate update (200 UPS) and render (120 FPS) rates. Continuously
     * updates game logic and renders frames while the game is running.
     */
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

    /**
     * Loads a specific level by index. Updates level data for both players and
     * resets the game state.
     *
     * @param levelIndex the index of the level to load
     */
    public void loadLevel(int levelIndex) {
        levelHandler.setLevel(levelIndex);
        objectHandler.loadNewLevel(levelHandler.getCurrentLevel());
        player1.loadLvlData(levelHandler.getCurrentLevel().getLvlData());
        player2.loadLvlData(levelHandler.getCurrentLevel().getLvlData());
        startNewGame();
    }

    /**
     * Called when the game window loses focus. Resets player movement
     * directions to prevent stuck keys.
     */
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

    public Lost getLost() {
        return lost;
    }

    /**
     * Resets both players to their starting positions for the current level.
     * Also resets their internal state (air speed, movement, etc.).
     */
    private void resetPlayers() {
        Level level = levelHandler.getCurrentLevel();
        level.setStartPos();

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
