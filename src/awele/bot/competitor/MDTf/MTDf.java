package awele.bot.competitor.MDTf;

import awele.bot.ChampionBot;
import awele.core.Board;

public class MTDf extends ChampionBot {
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
    /*
    Original Pascal pseudo code by Aske Plaat:

function MTDF(root : node_type; f : integer; d : integer) : integer;
      g := f;
      upperbound := +INFINITY;
      lowerbound := -INFINITY;
      repeat
            if g == lowerbound then beta := g + 1 else beta := g;
            g := AlphaBetaWithMemory(root, beta - 1, beta, d);
            if g < beta then upperbound := g else lowerbound := g;
      until lowerbound >= upperbound;
      return g;

Typically, one would call MTD(f) in an iterative deepening framework, the first guess the value of the previous iteration:

function iterative_deepening(root : node_type) : integer;

      firstguess := 0;
      for d = 1 to MAX_SEARCH_DEPTH do
            firstguess := MTDF(root, firstguess, d);
            if times_up() then break;
      return firstguess;

    * */
}
