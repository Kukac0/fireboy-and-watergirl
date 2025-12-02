package utils;

public class Constants {

    public static class Directions{
        public static final int UP = 0;
        public static final int LEFT = 1;
        public static final int DOWN = 2;
        public static final int RIGHT = 3;
    }

    public static class PlayerConstants{
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMPING = 2;
        public static final int FALLING = 3;
        public static final int DEATH = 4;
        public static final int WIN = 5;

        public static int getSpriteAmount(int playerAction ){

            return switch (playerAction) {
                case IDLE -> 4;
                case RUNNING -> 2;
                case JUMPING -> 1;
                case FALLING -> 1;
                default -> 1;
            };
        }

    }
}
