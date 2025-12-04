package core;

import java.awt.Graphics;

import entities.Player;
import gamestates.GameState;
import static gamestates.GameState.MENU;
import static gamestates.GameState.PLAYING;
import static gamestates.GameState.QUIT;
import gamestates.Menu;
import gamestates.Won;
import levels.Level;
import levels.LevelHandler;
import utils.ScoreHandler;

public class Game  implements Runnable{
    
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    private Player player1, player2;
    private LevelHandler levelHandler;
    private Won won;
    private Menu menu;
    private long startTime;
    private ScoreHandler scoreHandler;

    public final static int TILES_DEFAULT_SIZE = 24;
    public final static float SCALE = 1.0f;
    public final static float CHARACTER_SCALE = 2.0f;
    public final static int TILES_IN_WIDTH = 39;
    public final static int TILES_IN_HEIGHT = 29;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
    
    


    public Game(){
        initClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();

    }

    private void initClasses() {
        menu = new Menu(this);
        won = new Won(this);
        startTime = System.currentTimeMillis();
        scoreHandler = new ScoreHandler();
        player1 = new Player(100, 100);
        player2 = new Player(150, 100);
        levelHandler = new LevelHandler(this);
        player1.loadLvlData(levelHandler.getCurrentLevel().getLvlData());
        player2.loadLvlData(levelHandler.getCurrentLevel().getLvlData());
    }

    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void startNewGame(){
        player1.reset();
        player2.reset();
        startTime = System.currentTimeMillis();
        GameState.state = PLAYING;
    }

    public void update(){
        switch (GameState.state) {
            case MENU -> menu.update();
            case PLAYING -> {
                player1.update();
                player2.update();
                levelHandler.update();
            }
            case WON -> won.update();
            case QUIT -> System.exit(0);
        }
    }

    public void render(Graphics g){
        switch (GameState.state) {
            case MENU -> menu.draw(g);
            case PLAYING -> {
                levelHandler.draw(g);
                player1.render(g);
                player2.render(g);
            }
            case WON -> {
                won.draw(g);
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

        while(true){
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            
            previousTime = currentTime;

            if (deltaU >= 1){
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1){
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }

    }

    public void windowFocusLost(){
        player1.reserDir();
        player2.reserDir();
    }

    public ScoreHandler getScoreHandler(){
        return scoreHandler;
    }

    public long getStartTime(){
        return startTime;
    }

    public Won getWon(){
        return won;
    }

    private void resetPlayers() {
        Level level = levelHandler.getCurrentLevel();
        player1.setLocation(level.p1StartX, level.p1StartY);
        player2.setLocation(level.p2StartX, level.p2StartY);

        player1.reset();
        player2.reset();
    }

    public Player getPlayer1(){
        return player1;
    }

    public Player getPlayer2(){
        return player2;
    }
}
