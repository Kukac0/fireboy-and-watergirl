package objects;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import core.Game;
import levels.Level;

public class ObjectHandler {

    private Game game;
    private List<Button> buttons;
    private List<Door> doors;
    private List<Box> boxes;
    private List<Lift> lifts;

    public ObjectHandler(Game game) {
        this.game = game;
        this.buttons = new ArrayList<>();
        this.doors = new ArrayList<>();
        this.boxes = new ArrayList<>();
        this.lifts = new ArrayList<>();
    }

    public void update() {
        for (Button b : buttons) {
            b.update();
        }
        for (Door d : doors) {
            d.update();
        }
        for (Box b : boxes) {
            b.update();
        }
        for (Lift l : lifts) {
            l.update();
        }
    }

    public void draw(Graphics g) {
        for (Button b : buttons) {
            b.draw(g);
        }
        for (Door d : doors) {
            d.draw(g);
        }
        for (Box b : boxes) {
            b.draw(g);
        }
        for (Lift l : lifts) {
            l.draw(g);
        }
    }

    public void loadObjects() {
        Level level = game.getLevelHandler().getCurrentLevel();

        this.buttons = getButtons(level);
        this.doors = getDoors(level);
        this.boxes = getBoxes(level);
        this.lifts = getLifts(level);
    }

    private List<Button> getButtons(Level level) {
        return null;
    }

    private List<Door> getDoors(Level level) {
        return null;
    }

    private List<Box> getBoxes(Level level) {
        return null;
    }

    private List<Lift> getLifts(Level level) {
        return null;
    }

}
