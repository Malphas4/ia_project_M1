package awele.bot.competitor;

import awele.bot.demo.minmax.MinMaxNode;
import awele.core.Board;
import awele.core.InvalidBotException;

import java.util.ArrayList;

public abstract class Nodale {
    //Noeud Type qui sera courant à chaque noeud/algorithme

    /*TODO: reprise min max, pvs, svr, mcts, etc...
    TODO a revoir, simplifier et voir si correspond
     */

    /**
     * Numéro de joueur de l'IA
     */
    protected static int player;

    /**
     * Profondeur maximale
     */
    protected static int maxDepth = 6;
    private ArrayList<Nodale> childs = new ArrayList<Nodale>();

    /**
     * L'évaluation du noeud
     */
    private int evaluation;

    /**
     * Évaluation des coups selon MinMax
     */
    private int[] decision;

    /**
     * Constructeur...
     *
     * @param board L'état de la grille de jeu
     * @param depth La profondeur du noeud
     * @param alpha Le seuil pour la coupe alpha
     * @param beta  Le seuil pour la coupe beta
     */
    public Nodale(Board board, int depth, int alpha, int beta, int color) {
        /* On crée un tableau des évaluations des coups à jouer pour chaque situation possible */
        this.decision = new int[Board.NB_HOLES];
        /* Initialisation de l'évaluation courante */
        this.evaluation = this.worst();
        /* On parcourt toutes les coups possibles */
        for (int i = 0; i < Board.NB_HOLES; i++)
            /* Si le coup est jouable */
            if (board.getPlayerHoles()[i] != 0) {
                /* Sélection du coup à jouer */
                double[] decision = new double[Board.NB_HOLES];
                decision[i] = 1;
                /* On copie la grille de jeu et on joue le coup sur la copie */
                Board copy = (Board) board.clone();
                try {
                    int score = copy.playMoveSimulationScore(copy.getCurrentPlayer(), decision);
                    copy = copy.playMoveSimulationBoard(copy.getCurrentPlayer(), decision);
                    /* Si la nouvelle situation de jeu est un coup qui met fin à la partie,
                       on évalue la situation actuelle */
                    if ((score < 0) ||
                            (copy.getScore(Board.otherPlayer(copy.getCurrentPlayer())) >= 25) ||
                            (copy.getNbSeeds() <= 6))
                        this.decision[i] = this.diffScore(copy);
                        /* Sinon, on explore les coups suivants */
                    else {
                        /* Si la profondeur maximale n'est pas atteinte */
                        if (depth < Nodale.maxDepth) {
                            /* On construit le noeud suivant */
                            MinMaxNode child = this.getNextNode(copy, depth + 1, alpha, beta);
                            /* On récupère l'évaluation du noeud fils */
                            //this.decision [i] = this.childs.getEvaluation ();
                            this.decision[i] = this.get_childs(i).getEvaluation();
                        }
                        /* Sinon (si la profondeur maximale est atteinte), on évalue la situation actuelle */
                        else
                            this.decision[i] = this.diffScore(copy);
                    }
                    /* L'évaluation courante du noeud est mise à jour, selon le type de noeud (MinNode ou MaxNode) */
                    this.evaluation = this.minmax(this.decision[i], this.evaluation);
                    /* Coupe alpha-beta */
                    if (depth > 0) {
                        alpha = this.alpha(this.evaluation, alpha);
                        beta = this.beta(this.evaluation, beta);
                    }
                } catch (InvalidBotException e) {
                    this.decision[i] = 0;
                }
            }
    }

    private Nodale get_childs(int i) {
        return childs.get(i);
    }

    /**
     * Pire score pour un joueur
     */
    protected abstract int worst();

    /**
     * Initialisation
     */
    protected static void initialize(Board board, int maxDepth) {
        Nodale.maxDepth = maxDepth;
        Nodale.player = board.getCurrentPlayer();
    }

    private int diffScore(Board board) {
        return board.getScore(Nodale.player) - board.getScore(Board.otherPlayer(Nodale.player));
    }

    /**
     * Mise à jour de alpha
     *
     * @param evaluation L'évaluation courante du noeud
     * @param alpha      L'ancienne valeur d'alpha
     * @return
     */
    protected abstract int alpha(int evaluation, int alpha);

    /**
     * Mise à jour de beta
     *
     * @param evaluation L'évaluation courante du noeud
     * @param beta       L'ancienne valeur de beta
     * @return
     */
    protected abstract int beta(int evaluation, int beta);

    /**
     * Retourne le min ou la max entre deux valeurs, selon le type de noeud (MinNode ou MaxNode)
     *
     * @param eval1 Un double
     * @param eval2 Un autre double
     * @return Le min ou la max entre deux valeurs, selon le type de noeud
     */
    protected abstract int minmax(int eval1, int eval2);

    /**
     * Indique s'il faut faire une coupe alpha-beta, selon le type de noeud (MinNode ou MaxNode)
     *
     * @param eval  L'évaluation courante du noeud
     * @param alpha Le seuil pour la coupe alpha
     * @param beta  Le seuil pour la coupe beta
     * @return Un booléen qui indique s'il faut faire une coupe alpha-beta
     */
    protected abstract boolean alphabeta(int eval, int alpha, int beta);

    /**
     * Retourne un noeud (MinNode ou MaxNode) du niveau suivant
     *
     * @param board L'état de la grille de jeu
     * @param depth La profondeur du noeud
     * @param alpha Le seuil pour la coupe alpha
     * @param beta  Le seuil pour la coupe beta
     * @return Un noeud (MinNode ou MaxNode) du niveau suivant
     */
    protected abstract MinMaxNode getNextNode(Board board, int depth, int alpha, int beta);

    /**
     * L'évaluation du noeud
     *
     * @return L'évaluation du noeud
     */
    int getEvaluation() {
        return this.evaluation;
    }

    /**
     * L'évaluation de chaque coup possible pour le noeud
     *
     * @return
     */
    int[] getDecision() {
        return this.decision;
    }
}