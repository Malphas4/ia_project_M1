package awele.bot.competitor.Negamax;

import awele.bot.CompetitorBot;
import awele.core.Board;

public class Negamax extends CompetitorBot {
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

    int negamax(NegaNode node,int depth,int a, int b, int color){
        Double positiveInfinity = Double.POSITIVE_INFINITY;
        Double negativeInfinity = Double.NEGATIVE_INFINITY;



        return 0;
    }


    /*Algo negamax
    *
    *
    function negamax(node, depth, α, β, color) is
    if depth = 0 or node is a terminal node then
        return color × the heuristic value of node

    childNodes := generateMoves(node)
    childNodes := orderMoves(childNodes)
    value := −∞
    foreach child in childNodes do
        value := max(value, −negamax(child, depth − 1, −β, −α, −color))
        α := max(α, value)
        if α ≥ β then
            break (* cut-off *)
    return value

    (* Initial call for Player A's root node *)
    negamax(rootNode, depth, −∞, +∞, 1)

    appel pour joueur B ( rootNode,depth, −∞, +∞, -1)
    * */
}
