package awele.bot.competitor.NegaMax;

import awele.bot.competitor.MinMaxModif.MinMaxNode;
import awele.core.Board;
import awele.core.InvalidBotException;

import java.util.HashMap;

/**
 * @author Alexandre Blansché Noeud d'un arbre MinMax
 */
public class NegaMaxNode {
    /**
     * Profondeur maximale
     */
    private static int maxDepth;


    private final double depth;

    /**
     * L'évaluation du noeud
     */
    private double evaluation;


    /**
     * Évaluation des coups selon MinMax
     */
    private final double[] decision;
    private static HashMap<String, double[]> transpositionTable;



    /**
     * Constructeur...
     *
     * @param board L'état de la grille de jeu
     * @param depth La profondeur du noeud
     */

    public NegaMaxNode(Board board, double depth, int myTour, int opponentTour, double a, double b) {
        this.depth = depth;
        /* On crée index de notre situation */


        // Ctable de transposition
        String stateKey = board.toString();
        //System.out.println(transpositionTable.size());
        if (transpositionTable.containsKey(stateKey)) {
            decision = transpositionTable.get(stateKey);
            //System.out.println("Déjà vu : " + stateKey + " : decision : " + Arrays.toString(decision));
            return;
        }

        /* On crée un tableau des évaluations des coups à jouer pour chaque situation possible */
        this.decision = new double[Board.NB_HOLES];
        /* Initialisation de l'évaluation courante */
        this.evaluation = -Double.MAX_VALUE;
        Board copy;
        double[] decisionTemp = new double[Board.NB_HOLES];

        for (int i = 0; i < Board.NB_HOLES; i++) {
            /* Si le coup est jouable */
            if (board.getPlayerHoles()[i] != 0) {
                /* Sélection du coup à jouer */
                decisionTemp[i] = (i + 1);
                /* On copie la grille de jeu et on joue le coup sur la copie */
                // Board copy = (Board) board.clone();
                try {
                    copy = board.playMoveSimulationBoard(myTour, decisionTemp);
                    if ((copy.getScore(myTour) < 0) || (copy.getScore(opponentTour) >= 25) || (copy.getNbSeeds() <= 6) || !(depth < NegaMaxNode.maxDepth)) {
                        this.decision[i] = scoreEntireBoardById(copy, myTour, opponentTour);

                        //mise à jour de la table
                        transpositionTable.put(stateKey, decision);
                    }
                    else {

                        /* Si le noeud n'a pas encore été calculé, on le construit */
                        /* On construit le noeud suivant */
                        NegaMaxNode child = new NegaMaxNode(copy, depth + 1, opponentTour, myTour, -b, -a);

                        /* On récupère l'évaluation du noeud fils */
                        this.decision[i] = -child.getEvaluation();

                        /*
                         * Sinon (si la profondeur maximale est atteinte), on évalue la situation
                         * actuelle
                         */
                        //ajout dans la table de transposition
                        transpositionTable.put(stateKey, decision);


                    }
                    /*
                     * L'évaluation courante du noeud est mise à jour, selon le type de noeud
                     * (MinNode ou MaxNode)
                     */
                    if (this.decision[i] > this.evaluation) {
                        this.evaluation = this.decision[i];
                    }

                    if (depth > 0) {
                        a = Double.max(a, this.decision[i]);
                        if (a >= b) {
                            break;
                        }
                    }

                } catch (InvalidBotException e) {
                    this.decision[i] = 0;
                }
            }
        }
    }
    /**
     * Initialisation
     */
    public static void initialize(int maxDepth) {
        NegaMaxNode.maxDepth = maxDepth;
        transpositionTable = new HashMap<String, double[]>();
    }




    private int scoreEntireBoardById(Board board, int myTour, int opponentTour) {
        int total = 0;
        int[] seedsPlayer = board.getPlayerHoles(), seedsOpponent = board.getOpponentHoles();

        for (int i = 0; i < 6; i++) {
            int seedP = seedsPlayer[i];
            int seedO = seedsOpponent[i];
            if (seedP > 12)
                total += 28;
            else if (seedP == 0)
                total -= 54;
            else if (seedP < 3)
                total -= 36;

            if (seedO > 12)
                total -= 28;
            else if (seedO == 0)
                total += 54;
            else if (seedO < 3)
                total += 36;
        }
        return (25 * (board.getScore(myTour) - board.getScore(opponentTour))) - total;
    }


    /**
     * L'évaluation du noeud
     *
     * @return L'évaluation du noeud
     */
    double getEvaluation() {
        return this.evaluation;
    }



    /**
     * L'évaluation de chaque coup possible pour le noeud
     *
     * @return
     */
    public double[] getDecision() {
        return this.decision;
    }
}