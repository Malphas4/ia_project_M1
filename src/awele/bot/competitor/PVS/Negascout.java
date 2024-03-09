package awele.bot.competitor.PVS;

import awele.bot.CompetitorBot;
import awele.bot.DemoBot;
import awele.core.Board;
import awele.core.InvalidBotException;

public class Negascout extends DemoBot {

    private final static int MAXDEPTH = 10;

    public Negascout() throws InvalidBotException {
        setBotName("Negascout");
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
        int alpha = - (board.getNbSeeds() + board.getScore(board.getCurrentPlayer()) + board.getOpponentSeeds() + 1);
        int beta = - alpha;
        double[] decision = new double[Board.NB_HOLES];
        for (int i = 0; i < Board.NB_HOLES; i++) {
            decision[i] = negascout(board, alpha, beta, 0);
        }
        return decision;
    }

    @Override
    public void learn() {

    }


    /*int a, b, t, i;
    if ( d == maxdepth )
        return Evaluate(p);                        leaf node
    determine successors p_1,...,p_w of p;
    a = alpha;
    b = beta;
    for ( i = 1; i <= w; i++ ) {
        t = -NegaScout ( p_i, -b, -a );
        if ( (t > a) && (t < beta) && (i > 1) && (d < maxdepth-1) )
            a = -NegaScout ( p_i, -beta, -t );      re-search
        a = max( a, t );
        if ( a >= beta )
            return a;                                 cut-off
        b = a + 1;                       set new null window
    }
    return a;*/
    public int negascout(Board board, int alpha, int beta, int depth) {
        int a, b, t;
        if (depth == MAXDEPTH)
            return board.getScore(board.getCurrentPlayer());
        a = alpha;
        b = beta;
        for (int i = 0 ; i < Board.NB_HOLES ; i++) {
            double[] decision = new double[Board.NB_HOLES];
            decision[i] = 1;
            Board copy;
            try {
                copy = board.playMoveSimulationBoard(board.getCurrentPlayer(), decision);
                t = - negascout(copy, -b, -a, depth + 1);
                if ( (t > a) && (t < beta) && (i > 0) && (depth < MAXDEPTH) )
                    a = - negascout(copy, -beta, -t, depth + 1);
                a = Math.max(a, t);
                if (a >= beta)
                    return a;
                b = a + 1;
            } catch (InvalidBotException ignored) {}
        }
        return a;
    }
}
