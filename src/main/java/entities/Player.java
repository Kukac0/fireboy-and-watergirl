package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static utils.Constants.PlayerConstants.IDLE;
import static utils.Constants.PlayerConstants.RUNNING;
import static utils.Constants.PlayerConstants.getSpriteAmount;
import utils.LoadSave;

public class Player {

    private BufferedImage[][] animations;
    private float x, y;
    private int aniTick, aniIndex, aniSpeed = 40;
    private int playerAction = IDLE;
    private boolean moving = false;
    private boolean up, left, down, right;
    private float playerSpeed = 2.0f;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        loadAnimations();
    }

    public void update() {

        updatePos();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int) x, (int) y, 48, 48, null);

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

    private void updatePos() {

        moving = false;

        if (left && !right) {
            x -= playerSpeed;
            moving = true;
        } else if (!left && right) {
            x += playerSpeed;
            moving = true;
        }

        if (up && !down) {
            y -= playerSpeed;
            moving = true;
        } else if (!up && down) {
            y += playerSpeed;
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
