package awele.bot.competitor.PVS;

import awele.bot.DemoBot;
import awele.core.Board;
import awele.core.InvalidBotException;

import java.util.*;

public class Negascout extends DemoBot {

    private final static int MAXDEPTH = 7;

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
        double alpha = Double.MIN_VALUE;
        double beta = Double.MAX_VALUE;
        double[] decision = new double[Board.NB_HOLES];

        boolean[] validMoves = board.validMoves(board.getCurrentPlayer());
        List<Integer> indexOrder = orderArrayIndex(board).reversed();

        for (int i = 0; i < Board.NB_HOLES; i++) {
            if (validMoves[indexOrder.get(i)]) {
                try {
                    double [] sim = new double[Board.NB_HOLES];
                    sim[indexOrder.get(i)] = 1;
                    decision[indexOrder.get(i)] = -negascout(board.playMoveSimulationBoard(board.getCurrentPlayer(), sim), -beta, -alpha, 1);
                } catch (InvalidBotException ignored) {
                }
            }
        }
        return decision;
    }

    @Override
    public void learn() {

    }

    public List<Integer> orderArrayIndex(Board board) {
        List<Integer> result = new ArrayList<>();

        Map<Integer, Double> risks = new LinkedHashMap<>();
        for (int i = 0; i < Board.NB_HOLES; i++) {
            double[] decision = new double[Board.NB_HOLES];
            decision[i] = 1;
            try {
                risks.put(i, evaluateRiskReward(board.playMoveSimulationBoard(board.getCurrentPlayer(), decision)));
            } catch (InvalidBotException ignored) {
            }
        }
        List<Map.Entry<Integer, Double>> list = new ArrayList<>(risks.entrySet());
        list.sort(Map.Entry.comparingByValue());

        for (Map.Entry entry : list) {
            result.add((Integer) entry.getKey());
        }

        return result;
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
    public double negascout(Board board, double alpha, double beta, int depth) {
        double a, b, t;
        a = alpha;
        b = beta;

        boolean[] validMoves = board.validMoves(board.getCurrentPlayer());
        int n = 0;
        for (boolean valid : validMoves)
            if (valid) n++;

        if (depth == MAXDEPTH || n == 0)
            return evaluateScoreRisk(board);

        List<Integer> indexOrder = orderArrayIndex(board).reversed();

        for (int i = 0 ; i < Board.NB_HOLES ; i++) {
            if (validMoves[indexOrder.get(i)]) {
                double[] decision = new double[Board.NB_HOLES];
                decision[indexOrder.get(i)] = 1;
                Board copy;
                try {
                    copy = board.playMoveSimulationBoard(board.getCurrentPlayer(), decision);
                    t = -negascout(copy,  -b, -a, depth + 1);
                    if ((t > a) && (t < beta) && (indexOrder.get(i) < Board.NB_HOLES) && (depth < MAXDEPTH - 1))
                        a = -negascout(copy, -beta, -t, depth + 1);
                    a = Math.max(a, t);
                    if (a >= beta)
                        return a;
                    b = a + 1;
                } catch(InvalidBotException ignored) {}
            }
        }
        return a;
    }

    /**
     * Evaluation du risque => Pas bon
     * Formule : R = P x G
     * où P est la probabilité de l'évènement = probabilité d'un coup permettant de récupérer des graines
     * où G est la gravité : nombre de graines que le joueur peut récupérer
     * @param board Le plateau à évaluer
     * @return Le risque
     */
    public double evaluateRiskReward(Board board) {
        int[] opponentHoles = board.getOpponentHoles();
        int[] currentPlayerHoles = board.getPlayerHoles();
        int nbRisk = 0;
        int gravity = 0;
        for (int i = 0; i < Board.NB_HOLES; i++) {
            int opponentSeed = opponentHoles[i];
            int iRemaining = (i + opponentSeed) % Board.NB_HOLES;
            if (currentPlayerHoles[iRemaining] == 2 || currentPlayerHoles[iRemaining] == 3) {
                for (int j = 0; j < iRemaining; j++) {
                    int seed = currentPlayerHoles[j];
                    gravity += seed;
                }
                nbRisk++;
            }
        }
        double probability = (double) nbRisk / opponentHoles.length;
        return probability * gravity;
    }

    public double evaluateScore(Board board) {
        return board.getScore(board.getCurrentPlayer());
    }

    public double evaluateScoreMinus(Board board) {
        return board.getScore(board.getCurrentPlayer()) - board.getScore(1 - board.getCurrentPlayer());
    }

    public double evaluateScoreRisk(Board board) {
        double v1 = 0;
        for (int g1 : board.getPlayerHoles())
            if (g1 == 1 || g1 == 2) v1++;

        double v2 = 0;
        for (int g2 : board.getOpponentHoles())
            if (g2 == 1 || g2 == 2) v2++;

        double m1 = 2 * board.getScore(board.getCurrentPlayer()) + v2;
        double m2 = 2 * board.getScore(1 - board.getCurrentPlayer()) + v1;
        return m1 - m2;
    }

}
