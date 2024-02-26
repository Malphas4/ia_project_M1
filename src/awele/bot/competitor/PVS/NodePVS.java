package awele.bot.competitor.PVS;

import awele.bot.competitor.Nodale;
import awele.bot.demo.minmax.MinMaxNode;
import awele.core.Board;
import awele.core.InvalidBotException;

import java.util.ArrayList;

public class NodePVS extends Nodale {
    public NodePVS pred;
    public NodePVS next;
    public NodePVS[] childs;
    public NodePVS(Board b,int depth,int alpha,int beta,byte color,NodePVS pred, NodePVS next, NodePVS ... childs) {
        super(b,depth,alpha,beta,color);
        this.pred = pred;
        this.next = next;
        this.childs = childs;
    }
    /*public NodePVS(NodePVS pred) {
        super(pred);
    }*/
    public NodePVS get_pred() {
        return pred;
    }
    public NodePVS get_next() {
        return next;
    }
    public void set_pred(NodePVS pred) {
        this.pred = pred;
    }
    public void set_next(NodePVS next) {
        this.next = next;
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

    protected NodePVS getNextNode(Board board, int depth, int alpha, int beta) {
        return null;
    }

    public int pvs() {
        return 0;
    }

    //Noeud Type qui sera courant à chaque noeud/algorithme

    /*TODO: reprise min max, pvs, svr, mcts, etc...
    TODO a revoir, simplifier et voir si correspond
     */
}
