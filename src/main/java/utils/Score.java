package utils;

import java.io.Serializable;

public class Score implements Serializable, Comparable<Score> {
    private String name;
    private int time;

    public Score(String name, int time) {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        int minute = time / 60;
        int second = time % 60;
        return String.format("%s: %02d:%02d", name, minute, second);
    }

    @Override
    public int compareTo(Score other) {
        return Integer.compare(this.time, other.time);
    }
}
