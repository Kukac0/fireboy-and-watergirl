package entities;

public class Watergirl extends Player {

    public Watergirl(float x, float y) {
        super(x, y);
        loadAnimations("watergirl_sprites.png");
    }

    @Override
    protected void tileInteractions(int tileId, int tileX, int tileY) {
        super.tileInteractions(tileId, tileX, tileY);
        // future interactions
    }
}
