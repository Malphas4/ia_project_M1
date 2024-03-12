package awele.bot.competitor.MinMaxModif;


import awele.bot.DemoBot;
import awele.core.Board;
import awele.core.InvalidBotException;

public class MinMax extends DemoBot {
    /** Profondeur maximale */
    private static final int MAX_DEPTH = 6;

    /**
     * @throws InvalidBotException
     */
    public MinMax () throws InvalidBotException
    {
        this.setBotName ("BetterMinMax");
        this.setAuthors("jamal valizadeh - oleksendr stetsenko");
    }

    @Override
    public void initialize() {

    }

    @Override
    public void finish() {

    }

    @Override
    public double[] getDecision(Board board) {
        MinMaxNode.initialize (board, MinMax.MAX_DEPTH);
        return new MaxNode(board).getDecision ();
    }

    @Override
    public void learn() {

    }
}
