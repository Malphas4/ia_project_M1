package awele.bot.competitor.MDTf;

import awele.bot.competitor.Nodale;
import awele.bot.demo.minmax.MinMaxNode;
import awele.bot.demo.minmax.MinNode;
import awele.core.Board;
import awele.core.InvalidBotException;

public class MDTfNode  extends Nodale
{
    byte color=1;
   public int lowerbound,upperbound;
    Board stateBoard;
    private int depth;

    public MDTfNode (Board board, int depth, double alpha, double beta,byte color)
    {
        super (board, 6, 0, 0, color);
    }

    public MDTfNode(Board board) {
//        super(board, 6, 24.0, 0.0, 1);
        super (board, 24, 0, 0);
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

    public int alpha() {
        return upperbound;
    }

    public int beta() {
        return lowerbound;

    }
    //@Override
    protected MDTfNode getNextNode (Board board, int depth, double alpha, double beta)
    {
        return new MDTfNode(board, depth, alpha, beta, (byte) -color);//TODO changer en int peut etre, byte negatif? 0
    }

    public MDTfNode firstchild() throws InvalidBotException {
        Board b = (Board) this.stateBoard.clone();
        double[]score ={1.0,0.0,0.0,0.0};

        b.playMoveSimulationBoard(b.getCurrentPlayer()+1,score);
        return new MDTfNode(b, this.depth+1, this.alpha, this.beta, (byte) -color);

    }

    public MDTfNode getBrother() {
        return null;
    }

    public boolean victory() {
        //if (this.board.playMoveSimulationBoard(1,this.getDecision()).
        return false; // savoir si partie fine TODO
    }
}
