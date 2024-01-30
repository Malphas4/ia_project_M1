package awele.bot.competitor.DeepVoid;

import awele.bot.CompetitorBot;
import awele.core.Board;

public class DeepPurple extends CompetitorBot {
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
    /*Classe  utilisant le concept de noeuds stables/scabreux quiescence utilisé par DeppBlue pour le go
     * en partant des feuilles
     *
     https://www.chessprogramming.org/Quiescence_Search
     * **/
}
