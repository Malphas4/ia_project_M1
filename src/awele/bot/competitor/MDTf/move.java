package awele.bot.competitor.MDTf;

import java.util.Comparator;

public class move {
    int id;
    int score;

    public move(int id, int score) {
        this.id = id;
        this.score = score;
    }

    public static Comparator<? super move> get_score() {
        return score;
    }

    public int getId() {
        return id;
    }



    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
