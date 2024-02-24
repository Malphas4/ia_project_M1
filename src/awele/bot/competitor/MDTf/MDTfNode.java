package awele.bot.competitor.MDTf;

import awele.bot.competitor.Nodale;
import awele.bot.demo.minmax.MinMaxNode;
import awele.core.Board;

public class MDTfNode  extends Nodale
{
    byte color=1;
    public MDTfNode (Board board, int depth, double alpha, double beta,byte color)
    {
        super (board, 6, 0, 0, color);
    }

    @Override
    protected int worst() {
        return 0;
    }

    @Override
    protected int alpha(int evaluation, int alpha) {
        return 0;
    }

    @Override
    protected int beta(int evaluation, int beta) {
        return 0;
    }

    @Override
    protected int minmax(int eval1, int eval2) {
        return 0;
    }

    @Override
    protected boolean alphabeta(int eval, int alpha, int beta) {
        return false;
    }

    @Override
    protected Nodale getNextNode(Board board, int depth, int alpha, int beta) {
        return null;
    }


    protected MDTfNode getNextNode(Board board, int depth, double alpha, double beta) {
        return null;
    }



   // @Override
    protected double minmax (double eval1, double eval2)
    {
        return Math.max (eval1, eval2);
    }

    //@Override
    protected boolean alphabeta (double eval, double alpha, double beta)
    {
        return eval >= beta;
    }

    //@Override
    protected Nodale createNode (Board board, int depth, double alpha, double beta,byte color)
    {
        return new MDTfNode (board, depth, alpha, beta, color);
    }
}
