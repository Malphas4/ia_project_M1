package awele.bot.competitor.PVS;

import awele.bot.CompetitorBot;
import awele.core.Board;

import static java.lang.Math.max;

public class PVS /* extends CompetitorBot*/ {/*
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
    public int pvs(NodePVS node, int depth,int α, int β,int color) {
        int score;
        if( depth == 0 || node.next ==null){
            return color * node.pvs();
        }
        for (NodePVS child:
            node.childs) {
            if (child.pred ==null )
                score = -pvs(child, depth - 1, -β, -α, -color);
            else {
                score = -pvs(child, depth - 1, -α - 1, -α, -color);//(* search with a null window *)
                if (α < score && score < β)
                    score = -pvs(child, depth - 1, -β, -α, -color);// (* if it failed high, do a full re-search *)
            }
            α = max(α, score);
            if( α >= β)
                child=null;//break;// (* beta cut-off *)
            //  return α;
        }
        return α;
    }


    /*
    function pvs(node, depth, α, β, color) is
    if depth = 0 or node is a terminal node then
        return color × the heuristic value of node
    for each child of node do
        if child is first child then
            score := −pvs(child, depth − 1, −β, −α, −color)
        else
            score := −pvs(child, depth − 1, −α − 1, −α, −color) (* search with a null window *)
            if α < score < β then
                score := −pvs(child, depth − 1, −β, −α, −color) (* if it failed high, do a full re-search *)
        α := max(α, score)
        if α ≥ β then
            break (* beta cut-off *)
    return α

    * */
}
