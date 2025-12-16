package objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import core.Game;
import static core.Game.TILES_SIZE;
import entities.Player;
import gamestates.GameState;
import levels.Level;
import static utils.HelpMethods.CanMoveHere;

public class ObjectHandler {

    private Game game;
    private List<Button> buttons;
    private List<Door> doors;
    private List<Box> boxes;
    private List<Lift> lifts;
    private List<Exit> exits;
    private List<Lever> levers;
    private List<Fluid> fluids;
    private List<Gem> gems;

    public ObjectHandler(Game game) {
        this.game = game;
        this.buttons = new ArrayList<>();
        this.doors = new ArrayList<>();
        this.boxes = new ArrayList<>();
        this.lifts = new ArrayList<>();
        this.exits = new ArrayList<>();
        this.levers = new ArrayList<>();
        this.fluids = new ArrayList<>();
        this.gems = new ArrayList<>();
        loadObjects();
    }

    public void update() {
        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();

        checkLiftHit(player1);
        checkLiftHit(player2);

        checkBoxHit(player1);
        checkBoxHit(player2);

        for (Lift l : lifts) {
            l.setActive(false);
        }
        for (Door d : doors) {
            d.setOpen(false);
        }

        for (Button b : buttons) {
            boolean player1Touch = player1.getHitbox().intersects(b.getHitbox());
            boolean player2Touch = player2.getHitbox().intersects(b.getHitbox());

            if (player1Touch || player2Touch) {
                b.setPressed(true);
                objectActivated(b.getId());
            } else {
                b.setPressed(false);
            }
            b.update();
        }
        for (Box b : boxes) {
            b.update(game.getLevelHandler().getCurrentLevel().getLvlData());
        }
        boolean allExitsActive = true;
        for (Exit e : exits) {
            e.checkExit(player1, 0);
            e.checkExit(player2, 1);
            if (!e.isActive()) {
                allExitsActive = false;
            }
        }
        if (allExitsActive && !exits.isEmpty()) {
            GameState.state = GameState.WON;
            game.getWon().setCompletionTime();
        }

        for (Lever l : levers) {
            boolean player1Touch = player1.getHitbox().intersects(l.getHitbox());
            boolean player2Touch = player2.getHitbox().intersects(l.getHitbox());

            l.touch(player1Touch || player2Touch);

            if (l.isOn()) {
                objectActivated(l.getId());
            }
            l.update();
        }
        for (Lift l : lifts) {
            l.update();
        }
        for (Door d : doors) {
            d.update();
        }
        for (Fluid f : fluids) {
            if (f.touched(game.getPlayer1(), 0)) {
                player1.die();
            }
            if (f.touched(game.getPlayer2(), 1)) {
                player2.die();
            }
            f.update();
        }
        for (Gem g : gems) {
            if (g.isActive()) {
                g.update();
                g.checkCollect(game.getPlayer1(), 0);
                g.checkCollect(game.getPlayer2(), 1);
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
        for (Fluid f : fluids) {
            f.draw(g);
        }
        for (Gem gem : gems) {
            gem.draw(g);
        }
    }

    public void reset() {
        for (Button b : buttons) {
            b.reset();
        }
        for (Door d : doors) {
            d.reset();
        }
        for (Box b : boxes) {
            b.reset();
        }
        for (Lift l : lifts) {
            l.reset();
        }
        for (Lever l : levers) {
            l.reset();
        }
        for (Gem g : gems) {
            g.setActive(false);;
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

    public void checkLiftHit(Player player) {
        Rectangle playerHitbox = player.getHitbox();
        for (Lift lift : lifts) {
            Rectangle liftHitbox = lift.getHitbox();
            if (playerHitbox.intersects(liftHitbox)) {
                Rectangle intersection = playerHitbox.intersection(liftHitbox);

                if (intersection.width > intersection.height) {             //Vertical collision
                    if (playerHitbox.y < liftHitbox.y) {
                        if (player.getAirSpeed() >= 0) {
                            player.setInAir(false);
                            player.setAirSpeed(0);
                            player.setY(liftHitbox.y - playerHitbox.height - 2);
                        }
                    } else {
                        float pushedY = playerHitbox.y + intersection.height;
                        if (CanMoveHere(playerHitbox.x, pushedY, playerHitbox.width, playerHitbox.height, game.getLevelHandler().getCurrentLevel().getLvlData())) {
                            player.setAirSpeed(0);
                            player.setY(player.getY() + intersection.height);
                        } else {
                            lift.setY(playerHitbox.y - liftHitbox.height);
                        }
                    }

                } else if (intersection.width < intersection.height) {      //Horizontal collision
                    if (playerHitbox.x < liftHitbox.x) {
                        player.setX(player.getX() - intersection.width);
                    } else {
                        player.setX(player.getX() + intersection.width);
                    }
                }
            }
        }
    }

    public void checkBoxHit(Player player) {
        int[][] lvlData = game.getLevelHandler().getCurrentLevel().getLvlData();

        for (Box box : boxes) {
            if (player.getHitbox().intersects(box.getHitbox())) {
                Rectangle intersection = player.getHitbox().intersection(box.getHitbox());

                if (intersection.width > intersection.height) {     //Vertical collision
                    if (player.getHitbox().y < box.getHitbox().y) {
                        if (player.getAirSpeed() >= 0
                                && player.getHitbox().y + player.getHitbox().height <= box.getHitbox().y + box.getHitbox().height / 2 + 5) {

                            player.setInAir(false);
                            player.setAirSpeed(0);
                            player.setY(box.getHitbox().y - player.getHitbox().height - 1);
                        }
                    } else {
                        if (player.getAirSpeed() < 0) {
                            player.setAirSpeed(0);
                            player.setY(player.getY() + intersection.height);
                        }
                    }
                } else {    //Horizontal collision
                    if (player.getHitbox().x < box.getHitbox().x) {
                        box.push(1.0f);
                        player.setX(player.getX() - intersection.width);
                    } else {
                        box.push(-1.0f);
                        player.setX(player.getX() + intersection.width);
                    }
                }
            }
        }
    }

    public void loadObjects() {
        Level level = game.getLevelHandler().getCurrentLevel();

        getButtons(level);
        getDoors(level);
        getBoxes(level);
        getLifts(level);
        getExits(level);
        getLevers(level);
        getFluids(level);
        getGems(level);
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

    private void getBoxes(Level level) {
        for (int i = 0; i < level.getLvlData().length; i++) {
            for (int j = 0; j < level.getLvlData()[0].length; j++) {
                int id = level.getGreen()[i][j];

                if (id == 2) {
                    Box box = new Box(j * TILES_SIZE, i * TILES_SIZE, (int) (1.5 * TILES_SIZE), (int) (1.5 * TILES_SIZE));
                    boxes.add(box);
                }
            }
        }
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
                            i * TILES_SIZE + 3 * TILES_SIZE, 0.5f, switchId);
                    this.lifts.add(lift);
                }
            }
        }
    }

    private void getExits(Level level) {
        for (int i = 0; i < level.getLvlData().length; i++) {
            for (int j = 0; j < level.getLvlData()[0].length; j++) {
                int id = level.getGreen()[i][j];

                if (id == 4) {
                    Exit exit = new Exit(j * TILES_SIZE, i * TILES_SIZE, 2 * TILES_SIZE, 3 * TILES_SIZE, level.getBlue()[i][j]);
                    this.exits.add(exit);
                }
            }
        }
    }

    private void getLevers(Level level) {
        for (int i = 0; i < level.getLvlData().length; i++) {
            for (int j = 0; j < level.getLvlData()[0].length; j++) {
                int id = level.getGreen()[i][j];

                if (id == 5) {
                    Lever lever = new Lever(j * TILES_SIZE, i * TILES_SIZE, TILES_SIZE, TILES_SIZE, level.getBlue()[i][j]);
                    levers.add(lever);
                }
            }
        }
    }

    private void getFluids(Level level) {
        for (int i = 0; i < level.getLvlData().length; i++) {
            for (int j = 0; j < level.getLvlData()[0].length; j++) {
                int id = level.getGreen()[i][j];

                if (id == 200) {
                    int type = level.getBlue()[i][j];
                    Fluid fluid = new Fluid(j * TILES_SIZE, i * TILES_SIZE, TILES_SIZE, TILES_SIZE, type);
                    fluids.add(fluid);
                }
            }
        }
    }

    private void getGems(Level level) {
        for (int i = 0; i < level.getLvlData().length; i++) {
            for (int j = 0; j < level.getLvlData()[0].length; j++) {
                int id = level.getGreen()[i][j];

                if (id == 100) {
                    int type = level.getBlue()[i][j];
                    Gem gem = new Gem(j * TILES_SIZE, i * TILES_SIZE, TILES_SIZE, TILES_SIZE, type);
                    gems.add(gem);
                }
            }
        }
    }

}
