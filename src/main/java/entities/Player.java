package entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import static core.Game.CHARACTER_SCALE;
import static core.Game.SCALE;
import static core.Game.TILES_SIZE;
import static utils.Constants.PlayerConstants.IDLE;
import static utils.Constants.PlayerConstants.RUNNING;
import static utils.Constants.PlayerConstants.getSpriteAmount;
import static utils.HelpMethods.CanMoveHere;
import utils.LoadSave;

public class Player {

    private BufferedImage[][] animations;
    private float x, y;
    private int width, height;
    private int xOffset = 2 * (int)SCALE;
    private int yOffset = 2 * (int)SCALE;
    private final int CHARACTER_WIDTH = 10;
    private final int CHARACTER_HEIGHT = 22;
    private final int TRUE_CHARACTER_WIDTH = (int)(CHARACTER_WIDTH * CHARACTER_SCALE * SCALE);
    private final int TRUE_CHARACTER_HEIGHT = (int)(CHARACTER_HEIGHT * CHARACTER_SCALE* SCALE);
    private Rectangle hitbox; 
    private int aniTick, aniIndex, aniSpeed = 40;
    private int playerAction = IDLE;
    private boolean moving = false;
    private boolean up, left, down, right;
    private float playerSpeed = 2.0f;
    private int[][] lvlData;

    public Player(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        initHitbox();
        loadAnimations();
    }

    private void initHitbox() {
        hitbox = new Rectangle((int) x + xOffset, (int) y + yOffset, TRUE_CHARACTER_WIDTH, TRUE_CHARACTER_HEIGHT);
    }

    private void updateHitbox(){
        hitbox.x = (int) x + xOffset;
        hitbox.y = (int) y + yOffset;
    }

    public Rectangle getHitbox(){
        return hitbox;
    }


    public void update() {

        updatePos();
        updateHitbox();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int) x, (int) y, (int) (TILES_SIZE * CHARACTER_SCALE), (int) (TILES_SIZE * CHARACTER_SCALE), null);

    }

    private void setAnimation() {
        int startAni = playerAction;

        if (moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;

        if (startAni != playerAction)
            resetAniTick();
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= getSpriteAmount(playerAction))
                aniIndex = 0;
        }
    }

    public void loadLvlData(int[][] lvlData){
        this.lvlData = lvlData;
    }

    private void updatePos() {
        moving = false;

        if (!left && !right && !up && !down)
            return;

        float xSpeed = 0;
        float ySpeed = 0;

        if (left && !right) 
            xSpeed = -playerSpeed;
        else if (!left && right) 
            xSpeed = playerSpeed;
    
        if (up && !down) 
            ySpeed = -playerSpeed;
        else if (!up && down) 
            ySpeed = playerSpeed;

        if (CanMoveHere(x + xOffset + xSpeed, y + yOffset + ySpeed , TRUE_CHARACTER_WIDTH, TRUE_CHARACTER_HEIGHT, lvlData)) {
            x += xSpeed;
            y += ySpeed;
            moving = true;
        }
    }

    private void loadAnimations() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
        animations = new BufferedImage[4][4];

        for (int j = 0; j < animations.length; j++)
            for (int i = 0; i < animations.length; i++)
                animations[j][i] = img.getSubimage(i * 24, j * 24, 24, 24);

    }

    public void reserDir() {
        up = false;
        left = false;
        down = false;
        right = false;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

}
