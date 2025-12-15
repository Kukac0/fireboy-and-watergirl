package entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import static core.Game.CHARACTER_SCALE;
import static core.Game.SCALE;
import static core.Game.TILES_SIZE;
import static utils.Constants.PlayerConstants.FALLING;
import static utils.Constants.PlayerConstants.IDLE;
import static utils.Constants.PlayerConstants.JUMPING;
import static utils.Constants.PlayerConstants.RUNNING;
import static utils.Constants.PlayerConstants.getSpriteAmount;
import static utils.HelpMethods.CanMoveHere;
import utils.LoadSave;

public class Player {

    protected BufferedImage[][] animations;
    protected float x, y;
    protected int width, height;
    private int flipX = 0;      //for flipping the sprite
    private int flipW = 1;
    private int xOffset = 12 * (int) SCALE;
    private static final int Y_OFFSET = 2 * (int) SCALE;
    private static final int CHARACTER_WIDTH = 10;
    private static final int CHARACTER_HEIGHT = 22;
    private static final int TRUE_CHARACTER_WIDTH = (int) (CHARACTER_WIDTH * CHARACTER_SCALE * SCALE);
    private static final int TRUE_CHARACTER_HEIGHT = (int) (CHARACTER_HEIGHT * CHARACTER_SCALE * SCALE);
    private Rectangle hitbox;
    private int aniTick, aniIndex, aniSpeed = 40;
    protected int playerAction = IDLE;
    protected boolean moving = false;
    protected boolean left, right, jump;
    protected float playerSpeed = 1.5f;
    protected int[][] lvlData;

    //Jump / Gravity
    protected float airSpeed = 0f;
    protected float gravity = 0.04f * SCALE;
    protected float jumpSpeed = -2.5f * SCALE;
    protected float fallSpeedAfterCollision = 0.5f * SCALE;
    protected boolean inAir = false;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        this.width = TRUE_CHARACTER_WIDTH;
        this.height = TRUE_CHARACTER_HEIGHT;
        initHitbox();
    }

    public void reset() {
        inAir = false;
        airSpeed = 0;
        reserDir();
    }

    private void initHitbox() {
        hitbox = new Rectangle((int) x + xOffset, (int) y + Y_OFFSET, TRUE_CHARACTER_WIDTH, TRUE_CHARACTER_HEIGHT);
    }

    private void updateHitbox() {
        hitbox.x = (int) x + xOffset;
        hitbox.y = (int) y + Y_OFFSET;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void update() {
        updatePos();
        updateHitbox();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex],
                (int) (x + flipX),
                (int) y,
                (int) (TILES_SIZE * CHARACTER_SCALE) * flipW,
                (int) (TILES_SIZE * CHARACTER_SCALE),
                null);

    }

    private void setAnimation() {
        int startAni = playerAction;

        if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }

        if (inAir) {
            if (airSpeed < 0) {
                playerAction = JUMPING;
            } else {
                playerAction = FALLING;
            }
        }

        if (startAni != playerAction) {
            resetAniTick();
        }
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
            if (aniIndex >= getSpriteAmount(playerAction)) {
                aniIndex = 0;
            }
        }
    }

    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
    }

    private void updatePos() {
        moving = false;

        if (jump) {
            jump();
        }

        if (!inAir && !isPlayerOnFloor()) {
            inAir = true;
        }

        if (!isLeft() && !isRight() && !inAir) {
            moving = false;
            return;
        }

        float xSpeed = 0;

        if (right && !left) {
            xSpeed += playerSpeed;
            flipW = 1;
            flipX = 0;
        }

        if (!right && left) {
            xSpeed -= playerSpeed;
            flipW = -1;
            flipX = (int) (TILES_SIZE * CHARACTER_SCALE);
        }

        if (inAir) {
            movingInAir(xSpeed);
        } else {
            updateXPos(xSpeed);
        }

        checkSlopeCollision();

        int tileY = (hitbox.y + hitbox.height) / TILES_SIZE;
        int tileX = (hitbox.x) / TILES_SIZE;

        if (tileY < lvlData.length && tileX < lvlData[0].length && tileY >= 0 && tileX >= 0) {
            int tileId = lvlData[tileY][tileX];
            tileInteractions(tileId, tileX, tileY);
        }
        if (left || right) {
            moving = true;
        }
    }

    private void movingInAir(float xSpeed) {
        if (CanMoveHere(x + xOffset, y + Y_OFFSET + airSpeed, hitbox.width, hitbox.height, lvlData)) {
            y += airSpeed;
            airSpeed += gravity;
            updateXPos(xSpeed);
        } else {
            hitbox.y = (int) (y + Y_OFFSET);
            if (airSpeed > 0) {
                resetInAir();
            } else {
                airSpeed = fallSpeedAfterCollision;
            }
            updateXPos(xSpeed);
        }
    }

    protected void tileInteractions(int tileId, int tileX, int tileY) {
        //empty for now
    }

    private void slopeInteractions(int tileId, int tileX, int tileY) {
        float slopeY = 0;
        float xDiff = 0;

        if (!left && !right) {
            moving = false;
        }
        if (tileId == 21) {
            xDiff = hitbox.x - (float) (tileX * TILES_SIZE);
            slopeY = (tileY * TILES_SIZE) + xDiff;
        } else if (tileId == 28) {
            xDiff = (hitbox.x + hitbox.width) - (float) (tileX * TILES_SIZE);
            slopeY = (tileY * TILES_SIZE) + TILES_SIZE - xDiff;
        }
        if (airSpeed >= 0 && hitbox.y + hitbox.height > slopeY) {
            y = slopeY - hitbox.height - Y_OFFSET + 1;
            airSpeed = 0;
            inAir = false;
        }
    }

    private void checkSlopeCollision() {
        int tileY = (hitbox.y + hitbox.height - 1) / TILES_SIZE;
        int centerX = (hitbox.x + hitbox.width / 2) / TILES_SIZE;
        int leftX = hitbox.x / TILES_SIZE;
        int rightX = (hitbox.x + hitbox.width) / TILES_SIZE;

        int leftId = getTileId(leftX, tileY);
        int rightId = getTileId(rightX, tileY);
        int centerId = getTileId(centerX, tileY);

        if (utils.HelpMethods.IsSlope(rightId)) {
            slopeInteractions(rightId, rightX, tileY);
        } else if (utils.HelpMethods.IsSlope(leftId)) {
            slopeInteractions(leftId, leftX, tileY);
        } else if (utils.HelpMethods.IsSlope(centerId)) {
            slopeInteractions(centerId, centerX, tileY);
        }
    }

    private int getTileId(int tileX, int tileY) {
        if (tileX >= 0 && tileX < lvlData[0].length && tileY >= 0 && tileY < lvlData.length) {
            return lvlData[tileY][tileX];
        }
        return -1;
    }

    private void jump() {
        if (inAir) {
            return;
        }
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private boolean isPlayerOnFloor() {
        boolean leftFootOnGround = utils.HelpMethods.IsSolid(x + xOffset, y + Y_OFFSET + TRUE_CHARACTER_HEIGHT + 1, lvlData);
        boolean rightFootOnGround = utils.HelpMethods.IsSolid(x + xOffset + TRUE_CHARACTER_WIDTH, y + Y_OFFSET + TRUE_CHARACTER_HEIGHT + 1, lvlData);

        return leftFootOnGround || rightFootOnGround;
    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(x + xOffset + xSpeed, y + Y_OFFSET, TRUE_CHARACTER_WIDTH, TRUE_CHARACTER_HEIGHT - 2, lvlData)) {
            x += xSpeed;
        } else {
            float stepUp = 2.0f;
            if (CanMoveHere(x + xOffset + xSpeed, y + Y_OFFSET - stepUp, TRUE_CHARACTER_WIDTH, TRUE_CHARACTER_HEIGHT - 2, lvlData)) {
                y -= stepUp;
                x += xSpeed;
            } else {
                hitbox.x = (int) (x + xOffset);
            }
        }
    }

    protected void loadAnimations(String filename) {
        BufferedImage img = LoadSave.GetSpriteAtlas(filename);
        animations = new BufferedImage[4][4];

        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations.length; i++) {
                animations[j][i] = img.getSubimage(i * 24, j * 24, 24, 24);
            }
        }
    }

    public void reserDir() {
        jump = false;
        left = false;
        right = false;
    }

    public void setInAir(boolean inAir) {
        this.inAir = inAir;
    }

    public void setAirSpeed(float airSpeed) {
        this.airSpeed = airSpeed;
    }

    public float getAirSpeed() {
        return airSpeed;
    }

    public void setY(float y) {
        this.y = y;
        updateHitbox();
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isJump() {
        return jump;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

}
