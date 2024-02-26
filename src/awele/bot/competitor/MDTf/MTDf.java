package awele.bot.competitor.MDTf;

import awele.bot.ChampionBot;
import awele.core.Board;
import awele.core.InvalidBotException;

public class MTDf extends ChampionBot {


    MTDf() throws InvalidBotException {
        this.setBotName("MTDf");
        this.setAuthors("Weber", "Ly");

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

    int MTDfix(Board root, MoveList moves, int f, int d) {
    int  g = f;
    int low = 0; // nombre minimum de graines
    int upp = 48;// nombre max de graines
    do {
        bestMove = moves.getFirst();//*
        bestValue = g;//*
        int gamma = (g == low ? g + 1 : g);
        g = rootMT(root, moves, gamma, d); // post: moves have bound values
        if (g < gamma) upp = g;
        else low = g;
        moves.sort(); // stable sort descending
    } while (low < upp);
    if (g < gamma && bestMove != moves.getFirst()) {//*
        moves.putFirst(bestMove);//*
        g = bestValue;//*
    }//*
                return g;
    }
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
/*
* int MT(Board board, int gamma, int depth) {
    if (depth == 0 || terminal(board)) return negaEval(board);
    *          // negaEval returns the evaluation relative to the color to move
    ttMove = NO_MOVE;
    entry = tt.lookup(board);
    if (entry != null) {
        if (entry.depth >= depth) {
            if (entry.bound == LOWER && entry.value >= gamma) return entry.value;
            if (entry.bound == UPPER  && entry.value  <  gamma) return entry.value;
        }
        ttMove = entry.move;
    }
    generateMoves(board, moves);            // at least one move (else terminal)
    orderMoves(moves, ttMove);                // apply move ordering, ttMove (if any) first
    g = −INFINITY;
    for (i = 0; i < moves.size; i++) {
        value = −MT(board.makeMove(moves[i]), −gamma + 1, depth − 1);
        if (value > g) {
            g = value;
            move = moves[i];
            if (g >= gamma) break;
        }
    }
    tt.store(board, g, g < gamma ? UPPER : LOWER, depth, <NEW_TT_MOVE>);
    return g;
}
*
*
*
*
* MTDF fixed
*
*
* // post: best move is first in 'moves'
int MTDfix(Board root, MoveList moves, int f, int d) {
g = f;
low = −INF; upp = +INF;
do {
* bestMove = moves.getFirst();
* bestValue = g;
gamma = g == low ? g + 1 : g;
g = rootMT(root, moves, gamma, d); // post: moves have bound values
if (g < gamma) upp = g; else low = g;
moves.sort(); // stable sort descending
} while (low < upp);
* if (g < gamma && bestMove != moves.getFirst()) {
* moves.putFirst(bestMove);
* g = bestValue;
* }
return g;
}
*
*
* */