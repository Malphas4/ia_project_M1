package awele.bot.competitor.se;

import awele.bot.ChampionBot;
import awele.bot.CompetitorBot;
import awele.bot.DemoBot;
import awele.core.Board;
import awele.core.InvalidBotException;

public class ExpertSystem extends ChampionBot {

    public ExpertSystem() throws InvalidBotException {
        setBotName("SE Bot");
        setAuthors("Rozen");
    }

    /**
     * Evaluation du risque
     * Formule : R = P x G
     * où P est la probabilité de l'évènement = probabilité d'un coup permettant de récupérer des graines
     * où G est la gravité : nombre de graines que le joueur peut récupérer
     * @param board Le plateau à évaluer
     * @return Le risque
     */
    public double evaluateRisk(Board board) {
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

    /**
     * Fonction d'initalisation du bot
     * Cette fonction est appelée avant chaque affrontement
     */
    @Override
    public void initialize() {

    }

    /**
     * Fonction de finalisation du bot
     * Cette fonction est appelée après chaque affrontement
     */
    @Override
    public void finish() {
        // Pas de finish
    }

    /**
     * Fonction de prise de décision du bot
     *
     * @param board État du plateau de jeu
     * @return Un tableau de six réels indiquant l'efficacité supposée de chacun des six coups possibles
     */
    @Override
    public double[] getDecision(Board board) {
        double[] decision = new double[Board.NB_HOLES];
        for (int i = 0; i < Board.NB_HOLES; i++) {
            try {
                // Initialisation du tableau de décision pour un coup
                double[] decisionSimulation = new double[Board.NB_HOLES];
                decisionSimulation[i] = 1;

                // Simulation du plateau pour le coup
                Board copy = (Board) board.clone();
                // Enregistrement des scores pour le calcul du risque / récompense
                int previousScore = board.getScore(board.getCurrentPlayer());
                int newScore = board.playMoveSimulationScore(copy.getCurrentPlayer(), decisionSimulation);
                copy.playMoveSimulationBoard(copy.getCurrentPlayer(), decisionSimulation);

                double reward = newScore - previousScore;
                double risk = evaluateRisk(copy);
                if (reward != 0 && risk != 0) decision[i] = reward / risk;
            } catch (InvalidBotException e) {
                throw new RuntimeException(e);
            }
        }
        return decision;
    }

    /**
     * Apprentissage du bot
     * Cette fonction est appelée une fois (au chargement du bot)
     */
    @Override
    public void learn() {
        // Pas de learn
    }

}
