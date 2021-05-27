package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Class Slot represents the single slot of the Faith Track of the Personal Board
 *
 * @author Simone Tagliente
 * @see PersonalBoard
 */

public class Slot implements Serializable {

    private final int currentVictoryPoints;

    public Slot(int currentVictoryPoints) {
        this.currentVictoryPoints = currentVictoryPoints;
    }


    public int getCurrentVictoryPoints() {
        return currentVictoryPoints;
    }


}

