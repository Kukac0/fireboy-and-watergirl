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

    private BufferedImage[][] animations;
    private float x, y;
    private int width, height;
    private int flipX = 0;      //for flipping the sprite
    private int flipW = 1;
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
    private boolean left, right, jump;
    private float playerSpeed = 2.0f;
    private int[][] lvlData;

    //Jump / Gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * SCALE;
    private float jumpSpeed = -2.5f * SCALE;
    private float fallSpeedAfterCollision = 0.5f * SCALE;
    private boolean inAir = false;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        this.width = TRUE_CHARACTER_WIDTH;
        this.height = TRUE_CHARACTER_HEIGHT;
        initHitbox();
        loadAnimations();
    }

    public void reset() {
        inAir = false;
        airSpeed = 0;
        reserDir();
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
        g.drawImage(animations[playerAction][aniIndex], 
                    (int) (x + flipX),
                    (int)  y,
                    (int) (TILES_SIZE * CHARACTER_SCALE) * flipW,
                    (int) (TILES_SIZE * CHARACTER_SCALE),
                    null);

    }

    private void setAnimation() {
        int startAni = playerAction;

        if (moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;

        if (inAir){
            if (airSpeed < 0)
                playerAction = JUMPING;
            else
                playerAction = FALLING;
        }

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

        if (jump)
            jump();
        
        if (!inAir){
            boolean leftFootInAir = utils.HelpMethods.IsSolid(x + xOffset, y + yOffset + TRUE_CHARACTER_HEIGHT + 1, lvlData);
            boolean rightFootInAir = utils.HelpMethods.IsSolid(x + xOffset + TRUE_CHARACTER_WIDTH, y + yOffset + TRUE_CHARACTER_HEIGHT + 1, lvlData);    
            
            if (!leftFootInAir && !rightFootInAir)
                inAir = true;
        }

        if (!isLeft() && !isRight() && !inAir){
            moving = false;
            return;
        }

        float xSpeed = 0;

        if (right && !left){
            xSpeed += playerSpeed;
            flipW = 1;
            flipX = 0;
            xOffset = 2 * (int)SCALE;
        }

        if (!right && left){
            xSpeed -= playerSpeed;
            flipW = -1;
            flipX = TRUE_CHARACTER_WIDTH;
            xOffset = -2 * (int)SCALE;
        }
            
        if (inAir){
            if (CanMoveHere(x + xOffset, y + yOffset + airSpeed, hitbox.width, hitbox.height, lvlData)){
                y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            }else {
                hitbox.y = (int)(y + yOffset);
                if (airSpeed > 0)
                    resetInAir();
                else 
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }
        } else 
            updateXPos(xSpeed);

        int tileX =(hitbox.x + hitbox.width / 2) / TILES_SIZE;
        int tileY =(hitbox.y + hitbox.height - 1) / TILES_SIZE;

        if (tileY < lvlData.length && tileX < lvlData[0].length && tileY >= 0 && tileX >= 0) {
            int tileId = lvlData[tileY][tileX];

        if (utils.HelpMethods.IsSlope(tileId)) {
            float slopeY = 0;
        
        float xDiff = 0;

        if (tileId == 21) {
            xDiff = hitbox.x - (float)(tileX * TILES_SIZE);
            slopeY = (tileY * TILES_SIZE) + xDiff;
            
        } 
        else if (tileId == 28) {
            xDiff = (hitbox.x + hitbox.width) - (float)(tileX * TILES_SIZE);
            slopeY = (tileY * TILES_SIZE) + TILES_SIZE - xDiff; 
        }
        
        if (hitbox.y + hitbox.height > slopeY) {
            y = slopeY - hitbox.height - yOffset + 1; 
            airSpeed = 0;
            inAir = false; 
        }
        }
    }
        moving = true;
    }

    private void jump() {
        if (inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(x + xOffset + xSpeed, y + yOffset, TRUE_CHARACTER_WIDTH, TRUE_CHARACTER_HEIGHT - 2, lvlData)) {
            x += xSpeed;
        } else {
            hitbox.x = (int)(x + xOffset);
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
        jump = false;
        left = false;
        right = false;
    }

    public void setLocation(int x, int y){
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
