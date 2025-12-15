package objects;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import core.Game;
import static core.Game.TILES_SIZE;
import entities.Player;
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
        loadObjects();
    }

    public void update() {
        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();

        checkObjectHit(player1);
        checkObjectHit(player2);

        for (Button b : buttons) {
            boolean player1Touch = player1.getHitbox().intersects(b.getHitbox());
            boolean player2Touch = player2.getHitbox().intersects(b.getHitbox());

            if (player1Touch || player2Touch) {
                b.setPressed(true);
                objectActivated(b.getId());
            } else {
                b.setPressed(false);
                objectDeactivated(b.getId());
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
            boolean player1Touch = player1.getHitbox().intersects(l.getHitbox());
            boolean player2Touch = player2.getHitbox().intersects(l.getHitbox());

            l.touch(player1Touch || player2Touch);
            if (l.isOn()) {
                objectActivated(l.getId());
            } else {
                objectDeactivated(l.getId());
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

    private void objectDeactivated(int id) {
        for (Door door : doors) {
            if (door.getId() == id) {
                door.setOpen(false);
            }
        }
        for (Lift lift : lifts) {
            if (lift.getId() == id) {
                lift.setActive(false);
            }
        }
    }

    public void checkObjectHit(Player player) {
        for (Lift lift : lifts) {
            if (lift.getHitbox().intersects(player.getHitbox())) {
                if (player.getAirSpeed() > 0 && player.getHitbox().y + player.getHitbox().height < lift.getHitbox().y + lift.getHitbox().height / 2) {
                    player.setInAir(false);
                    player.setAirSpeed(0);

                    player.setY(lift.getHitbox().y - player.getHitbox().height - 2);
                }
            }
        }
    }

    public void loadObjects() {
        Level level = game.getLevelHandler().getCurrentLevel();

        getButtons(level);
        getDoors(level);
        //getBoxes(level);
        getLifts(level);
        //getExits(level);
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

    private void getDoors(Level level) {
        for (int i = 0; i < level.getLvlData().length; i++) {
            for (int j = 0; j < level.getLvlData()[0].length; j++) {
                int id = level.getGreen()[i][j];

                if (id == 1) {
                    Door door = new Door(j * TILES_SIZE, i * TILES_SIZE, TILES_SIZE, -2 * TILES_SIZE, level.getBlue()[i][j]);
                    this.doors.add(door);
                }
            }
        }
    }

    private List<Box> getBoxes(Level level) {
        return null;
    }

    private void getLifts(Level level) {
        for (int i = 0; i < level.getLvlData().length; i++) {
            for (int j = 0; j < level.getLvlData()[0].length; j++) {
                int id = level.getGreen()[i][j];

                if (id == 3) {
                    int blue = level.getBlue()[i][j];
                    boolean right = true;
                    if (blue >= 100) {
                        right = false;
                        blue -= 100;
                    }
                    int switchId = blue / 10;
                    int length = blue % 10;

                    int xPos = (j + 1) * TILES_SIZE;
                    if (!right) {
                        xPos -= (length + 1) * TILES_SIZE;
                    }

                    Lift lift = new Lift(xPos, i * TILES_SIZE + TILES_SIZE / 2 - 1, length * TILES_SIZE, TILES_SIZE / 2,
                            i * TILES_SIZE + (int) (2.5 * TILES_SIZE), 0.5f, switchId);
                    this.lifts.add(lift);
                }
            }
        }
    }

    private List<Exit> getExits(Level level) {
        return null;
    }

}
