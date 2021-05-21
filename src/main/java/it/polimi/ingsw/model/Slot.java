package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Class Slot represents the single slot of the Faith Track of the Personal Board
 *
 * @author Simone Tagliente
 * @see PersonalBoard
 */

public class Slot implements Serializable {
    private int posIndex; //apparentemente inutile
    private int currentVictoryPoints;

    public Slot(int posIndex, int currentVictoryPoints) {
        this.posIndex = posIndex;
        this.currentVictoryPoints = currentVictoryPoints;
    }


    public int getCurrentVictoryPoints() {
        return currentVictoryPoints;
    }

    public void setCurrentVictoryPoints(int currentVictoryPoints) {
        this.currentVictoryPoints = currentVictoryPoints;
    }
}
