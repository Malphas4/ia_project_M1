package awele.bot.competitor.NegaMax;
import awele.bot.CompetitorBot;
import awele.bot.competitor.NegaMax.NegaMaxNode;
import awele.core.Board;
import awele.core.InvalidBotException;

/**
 * @author Alexandre Blansché
 * Bot qui prend ses décisions selon le MinMax
 */
public class NegaMaxBot extends CompetitorBot {
    /**
     * Profondeur maximale
     */
    private static final int MAX_DEPTH = 9; // MAX = 9

    /**
     * @throws InvalidBotException
     */
    public NegaMaxBot() throws InvalidBotException {
        this.setBotName("NegaMax3 Profondeur = " + MAX_DEPTH);
        this.setAuthors("Weber","Ly");
    }

    /**
     * Fonction d'initalisation du bot
     * Cette fonction est appelée avant chaque affrontement
     */
    @Override
    public void initialize() {
        NegaMaxNode.initialize(MAX_DEPTH);
    }

    /**
     * Pas d'apprentissage
     */
    @Override
    public void learn() { }

    /**
     * Sélection du coup selon l'algorithme MinMax
     */
    @Override
    public double[] getDecision(Board board) {
        return new NegaMaxNode(
                    board, 0,
                    board.getCurrentPlayer(),
                    Board.otherPlayer(board.getCurrentPlayer()),
                    Double.MIN_VALUE,
                    Double.MAX_VALUE)
                .getDecision();
    }

    /**
     * Rien à faire
     */
    @Override
    public void finish() { }
}