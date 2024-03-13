package awele.bot.competitor.PVS;

import awele.bot.CompetitorBot;
import awele.bot.DemoBot;
import awele.bot.competitor.MinMaxModif.MinMaxNode;
import awele.core.Board;
import awele.core.InvalidBotException;

import java.util.*;

public class Negascout extends CompetitorBot {

    private final static int MAXDEPTH = 9;

    ////////////////////////////////////////
    // Déclaration des constructeurs
    ////////////////////////////////////////

    /**
     * Constructeur par défaut
     * @throws InvalidBotException
     */
    public Negascout() throws InvalidBotException {
        setBotName("Negascout");
        setAuthors("Rozen & Malphas");
    }

    ////////////////////////////////////////
    // Définition des méthodes surchargés
    ////////////////////////////////////////

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
        /*List<Integer> indexOrder = orderArrayIndex(board);*/

        for (int i = 0; i < Board.NB_HOLES; i++) {
            if (validMoves[i]) {
                try {
                    double [] sim = new double[Board.NB_HOLES];
                    sim[i] = 1;
                    decision[i] = -negascout(board.playMoveSimulationBoard(board.getCurrentPlayer(), sim), -beta, -alpha, 1);
                } catch (InvalidBotException ignored) {
                }
            }
        }
        return decision;
    }

    @Override
    public void learn() {

    }

    ////////////////////////////////////////
    // Définition des méthodes de classes
    ////////////////////////////////////////

    /**
     * Méthode qui s'arrange pour retourner la liste ordonnée des indexs selon une heuristique
     * @param board Le plateau de jeu
     * @return Liste d'index ordonnée selon h(x)
     */
    public List<Integer> orderArrayIndex(Board board) {
        // Création de la liste d'index
        List<Integer> result = new ArrayList<>();

        // Création de la Map <index, heuristique_valeur>
        Map<Integer, Double> risks = new LinkedHashMap<>();

        for (int i = 0; i < Board.NB_HOLES; i++) {
            double[] decision = new double[Board.NB_HOLES];
            decision[i] = 1;
            try {
                risks.put(i, evaluateScoreRisk(board.playMoveSimulationBoard(board.getCurrentPlayer(), decision)));
            } catch (InvalidBotException ignored) {
            }
        }
        // Triage de la map par rapport à ces valeurs
        List<Map.Entry<Integer, Double>> list = new ArrayList<>(risks.entrySet());
        list.sort(Map.Entry.comparingByValue());

        // Ajout dans la liste de résultat dans l'ordre les indexs de la map triées
        for (Map.Entry entry : list) {
            result.add((Integer) entry.getKey());
        }

//        return result.reversed();
      return result;

    }

    /**
     * Méthode récursive permettant de lancer l'algorithme NegaScout
     * @param board Plateau de jeu
     * @param alpha Alpha
     * @param beta Beta
     * @param depth Profondeur actuel
     * @return Une valeur de décision
     */
    public double negascout(Board board, double alpha, double beta, int depth) {
        double a, b, t;
        a = alpha;
        b = beta;

        boolean[] validMoves = board.validMoves(board.getCurrentPlayer());
        int n = 0;
        for (boolean valid : validMoves)
            if (valid) n++;

        if (depth == MAXDEPTH || n == 0)
            return customizedScore(board);

        /*List<Integer> indexOrder = orderArrayIndex(board);*/

        for (int i = Board.NB_HOLES - 1 ; 0 <= i ; i--) {
            if (validMoves[i]) {
                double[] decision = new double[Board.NB_HOLES];
                decision[i] = 1;
                Board copy;
                try {
                    copy = board.playMoveSimulationBoard(board.getCurrentPlayer(), decision);
                    t = -negascout(copy,  -b, -a, depth + 1);
                    if ((t > a) && (t < beta) && (i < Board.NB_HOLES - 1) && (depth < MAXDEPTH - 1))
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

    private int customizedScore(Board board){
        int myScore = board.getScore(board.getCurrentPlayer());
        int oppScore = board.getScore(1 - board.getCurrentPlayer());

        // Start of game strategy
        int emptyCount = 0;
        int nonEmptyCount = 0;
        int myMobility = 0;
        int oppMobility = 0;
        int myGranaryScore = 0;
        int oppVulnerableCount = 0;

        int mySeedsInHand = board.getPlayerSeeds();
        int oppSeedsInHand = Board.otherPlayer(board.getCurrentPlayer());
        for (int i = 0; i < 6; i++) {
            if (board.getPlayerHoles()[i] == 0)
                emptyCount++;
            else if (board.getPlayerHoles()[i] > 2)
                nonEmptyCount++;

            if (board.validMoves(board.getCurrentPlayer())[i])
                myMobility++;
            if (board.validMoves(Board.otherPlayer(board.getCurrentPlayer()))[i])
                oppMobility++;
            if (board.getPlayerHoles()[i] >= 2 * (6 - i))
                myGranaryScore += board.getPlayerHoles()[i];
            if (board.getOpponentHoles()[i] < 2)
                oppVulnerableCount++;

        }
        int startScore = 10 * (emptyCount - nonEmptyCount) + 2 * (myMobility - oppMobility);

        // Middle of game strategy
        int middleScore = myGranaryScore + 5 * oppVulnerableCount;

        // Bonus for having more seeds in hand
        int seedBonus = 5 * (mySeedsInHand - oppSeedsInHand);

        return 20 * (myScore - oppScore) + startScore + middleScore;
    }

}
