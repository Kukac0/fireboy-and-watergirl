package objects;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import core.Game;
import static core.Game.TILES_SIZE;
import levels.Level;

public class ObjectHandler {

    private Game game;
    private List<Button> buttons;
    private List<Door> doors;
    private List<Box> boxes;
    private List<Lift> lifts;
    private List<Exit> exits;
    private List<Lever> levers;

    public ObjectHandler(Game game) {
        this.game = game;
        this.buttons = new ArrayList<>();
        this.doors = new ArrayList<>();
        this.boxes = new ArrayList<>();
        this.lifts = new ArrayList<>();
        this.exits = new ArrayList<>();
        this.levers = new ArrayList<>();
    }

    public void update() {
        loadObjects();
        for (Button b : buttons) {
            boolean player1Touch = game.getPlayer1().getHitbox().intersects(b.getHitbox());
            boolean player2Touch = game.getPlayer2().getHitbox().intersects(b.getHitbox());

            if (player1Touch || player2Touch) {
                b.setPressed(true);
                objectActivated(b.getId());
            } else {
                b.setPressed(false);
            }
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
        for (Exit e : exits) {
            e.update();
        }
        for (Lever l : levers) {
            boolean player1Touch = game.getPlayer1().getHitbox().intersects(l.getHitbox());
            boolean player2Touch = game.getPlayer2().getHitbox().intersects(l.getHitbox());

            l.touch(player1Touch || player2Touch);
            if (l.isOn()) {
                objectActivated(l.getId());
            }
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
        for (Exit e : exits) {
            e.draw(g);
        }
        for (Lever l : levers) {
            l.draw(g);
        }
    }

    private void objectActivated(int id) {
        for (Door door : doors) {
            if (door.getId() == id) {
                door.setOpen(true);
            }
        }
        for (Lift lift : lifts) {
            if (lift.getId() == id) {
                lift.setActive(true);
            }
        }
    }

    public void loadObjects() {
        Level level = game.getLevelHandler().getCurrentLevel();

        getButtons(level);
        this.doors = getDoors(level);
        this.boxes = getBoxes(level);
        this.lifts = getLifts(level);
        this.exits = getExits(level);
    }

    private void getButtons(Level level) {
        for (int i = 0; i < level.getLvlData().length; i++) {
            for (int j = 0; j < level.getLvlData()[0].length; j++) {
                int id = level.getGreen()[i][j];
                if (id == 0) {
                    Button button = new Button(j * TILES_SIZE, i * TILES_SIZE, TILES_SIZE, TILES_SIZE, level.getBlue()[i][j]);
                    this.buttons.add(button);
                }
            }
        }
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

    private List<Exit> getExits(Level level) {
        return null;
    }

}
