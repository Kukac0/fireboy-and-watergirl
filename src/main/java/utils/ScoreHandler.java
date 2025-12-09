package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreHandler {

    private List<Score> scores;
    private static final String FILE_NAME = "scores.dat";

    public void addScore(Score score) {
        scores.add(score);
        Collections.sort(scores);
        if (scores.size() > 5) {
            scores.remove(scores.size() - 1);
        }
        LoadSave.SaveScores(FILE_NAME, scores);
    }

    public ScoreHandler() {
        scores = LoadSave.LoadScores(FILE_NAME);
        if (scores == null) {
            scores = new ArrayList<>();
        }
    }

    public List<Score> getScores() {
        return scores;
    }
}
