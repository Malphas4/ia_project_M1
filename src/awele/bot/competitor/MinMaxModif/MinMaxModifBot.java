package awele.bot.competitor.MinMaxModif;

import awele.bot.CompetitorBot;
import awele.core.Board;
import awele.core.InvalidBotException;

public class MinMaxModifBot extends CompetitorBot {
    @Override
    public void initialize() {
        setBotName("MnModif");
        try {
            setAuthors("Yolo","TKT");
        } catch (InvalidBotException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void finish() {

    }

    @Override
    public double[] getDecision(Board board) {
        return new double[0];
    }

    @Override
    public void learn() {

    }
}
