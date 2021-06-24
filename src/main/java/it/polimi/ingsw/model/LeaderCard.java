package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Class LeaderCard groups the attributes common to all the Leader Cards
 *
 * @author Mattia Sironi
 */

public class LeaderCard implements Serializable, Printable {
    private final int type;
    private final int victoryPoints;


    public LeaderCard(int type, int victoryPoints) {
        this.type = type;
        this.victoryPoints = victoryPoints;

    }


    public int getType() {
        return type;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public boolean equals(LeaderCard lc){
        return false;
    }

    public String toString(){
        return ("");
    }

    @Override
    public void print() {
    }
}
