package awele.bot.competitor.SSS;

import awele.bot.ChampionBot;
import awele.core.Board;

public class SSS extends ChampionBot {
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
    while true do   // repeat until stopped
    pop an element p=(J, s, h) from the head of the OPEN queue
    if J = e and s = S then
        STOP the algorithm and return h as a result
    else
        apply Gamma operator for p

Γ {\displaystyle \Gamma } operator for p = ( J , s , h ) {\displaystyle p=(J,s,h)} is defined in the following way:

if s = L then
    if J is a terminal node then
        (1.) add (J, S, min(h, value(J))) to OPEN
    else if J is a MIN node then
        (2.) add (J.1, L, h) to OPEN
    else
        (3.) for j=1..number_of_children(J) add (J.j, L, h) to OPEN
else
    if J is a MIN node then
        (4.) add (parent(J), S, h) to OPEN
             remove from OPEN all the states that are associated with the children of parent(J)
    else if is_last_child(J) then   // if J is the last child of parent(J)
        (5.) add (parent(J), S, h) to OPEN
    else
        (6.) add (parent(J).(k+1), L, h) to OPEN   // add state associated with the next child of parent(J) to OPEN

    */
}

