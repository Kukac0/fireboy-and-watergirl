package gamestates;

public enum GameState {
    MENU,
    PLAYING,
    WON,
    LOST,
    QUIT;

    public static GameState state = MENU;
}
