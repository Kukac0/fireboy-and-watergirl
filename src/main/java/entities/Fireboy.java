package entities;

public class Fireboy extends Player {

    public Fireboy(float x, float y) {
        super(x, y);
        loadAnimations("fireboy_sprites.png");
    }

    @Override
    protected void tileInteractions(int tileId, int tileX, int tileY) {
        super.tileInteractions(tileId, tileX, tileY);
        // future interactions
    }

}
