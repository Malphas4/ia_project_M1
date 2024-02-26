package awele.bot.competitor.MDTf;

import awele.bot.ChampionBot;
import awele.bot.demo.minmax.MaxNode;
import awele.bot.demo.minmax.MinMaxBot;
import awele.bot.demo.minmax.MinMaxNode;
import awele.core.Board;
import awele.core.InvalidBotException;

import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class MTDf extends ChampionBot {


    private static final int MAX_DEPTH = 6;
    private static final int MAXNODE = 50;
    public ArrayList<move> _moves=new ArrayList<>();

    MTDf() throws InvalidBotException {
        this.setBotName("MTD-f");
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
        MDTfNode root= new MDTfNode(board);
        this.initialize();
        return  MTDfix(root, _moves,0,MTDf.MAX_DEPTH).getDecision ();
    }

    @Override
    public void learn() {

    }

    int MTDfix(MDTfNode root, ArrayList<move> moves, int f, int d) {
    int  g = f;
    int low = 0; // nombre minimum de graines
    int upp = 48;// nombre max de graines
    int bestValue;
    int gamma;
    move bestMove;
        do {
        bestMove = moves.get(moves.size()-1);//*
        bestValue = g;//*
        gamma = (g == low ? g + 1 : g);
       // g = rootMT(root, moves, gamma, d); // post: moves have bound values
       g = AlphaBetaWithMemory(root, root.alpha(), root.beta(), d); // JU'ai aps trouvé la fonction rootMT en ligne le papier de 2018  est a lire/
        // site du chercheur https://jhorssen.home.xs4all.nl/Maximus/research/mtdf/index.htm
        if (g < gamma) upp = g;
        else low = g;
        moves.sort(move.get_score()); // stable sort descending
    } while (low < upp);
    if (g < gamma && bestMove != moves.get(moves.size()-1)) {//*
        moves.add(bestMove);//*
        g = bestValue;//*
    }//*
                return g;
    }


    int AlphaBetaWithMemory(MDTfNode n , int alpha ,int  beta ,  int d) throws InvalidBotException {
    int g;
    int b,a;
    MDTfNode c;
    if retrieve(n) == OK /* Transposition table lookup */ {
        if (n.lowerbound >= beta)  return n.lowerbound;
        if (n.upperbound <= alpha ) return n.upperbound;
        alpha=max(alpha, n.lowerbound);
        beta=min(beta, n.upperbound);
        //if (d == 0)  g =evaluate(n); /* leaf node */
        if (d == 0)  g =n.getEvaluation(); /* leaf node */
    }
    else if (n.nb == this.MAXNODE) {
            g = -99999;
         a = alpha; /* save original alpha value */
        //MDTfNode c=firstchild(n);
         c=n.firstchild();
        while ((g < beta) && (c != NOCHILD)){
                g=max(g, AlphaBetaWithMemory(c, a, beta, d - 1));
                a=max(a, g);
//                c=nextbrother(c);
                c=c.getBrother();
            }
    } else {/* n is a MINNODE */
            g = 9999;
            b = beta; /* save original beta value */
            c = n.firstchild();
            while ((g > alpha) && (c != NOCHILD)) {
                g = min(g, AlphaBetaWithMemory(c, alpha, b, d - 1));
                b = min(b, g);
//                c = nextbrother(c);
                c = c.nextbrother();
            }
            /* Traditional transposition table storing of bounds */
            /* Fail low result implies an upper bound */
            if (g <= alpha) n.upperbound=g;
            store n.upperbound;
            /* Found an accurate minimax value - will not occur if called with zero window */
            if (g > alpha && g < beta){
                n.lowerbound = g;
                n.upperbound = g;
                store n.lowerbound, n.upperbound;
            }
            /* Fail high result implies a lower bound */
            if (g >= beta) {
                n.lowerbound = g;
                store n.lowerbound;
            }
        }
    return g;


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

/*
*
*
* function AlphaBetaWithMemory(n : node_type; alpha , beta , d : integer) : integer;

    if retrieve(n) == OK then /* Transposition table lookup
        if n.lowerbound >= beta then return n.lowerbound;
                if n.upperbound <= alpha then return n.upperbound;
                alpha := max(alpha, n.lowerbound);
                beta := min(beta, n.upperbound);
                if d == 0 then g := evaluate(n); /* leaf node
                else if n == MAXNODE then
                g := -INFINITY; a := alpha; /* save original alpha value
                c := firstchild(n);
                while (g < beta) and (c != NOCHILD) do
        g := max(g, AlphaBetaWithMemory(c, a, beta, d - 1));
        a := max(a, g);
        c := nextbrother(c);
        else /* n is a MINNODE
        g := +INFINITY; b := beta; /* save original beta value
        c := firstchild(n);
        while (g > alpha) and (c != NOCHILD) do
        g := min(g, AlphaBetaWithMemory(c, alpha, b, d - 1));
        b := min(b, g);
        c := nextbrother(c);
        /* Traditional transposition table storing of bounds
        /* Fail low result implies an upper bound
        if g <= alpha then n.upperbound := g; store n.upperbound;
        /* Found an accurate minimax value - will not occur if called with zero window
        if g >  alpha and g < beta then
        n.lowerbound := g; n.upperbound := g; store n.lowerbound, n.upperbound;
        /* Fail high result implies a lower bound
        if g >= beta then n.lowerbound := g; store n.lowerbound;
        return g;
        * */

