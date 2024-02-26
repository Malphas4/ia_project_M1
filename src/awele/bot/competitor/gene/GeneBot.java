package awele.bot.competitor.gene;

import awele.bot.CompetitorBot;
import awele.core.Board;
import awele.core.InvalidBotException;

public class GeneBot extends CompetitorBot {

    public GeneBot() throws InvalidBotException {
        setBotName("GeneBot");
        setAuthors("Rozen & Malphas");
    }

    @Override
    public void initialize() {

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
