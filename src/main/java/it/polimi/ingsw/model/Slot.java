package it.polimi.ingsw.model;

/**
 * Class Slot represents the single slot of the Faith Track of the Personal Board
 *
 * @author Simone Tagliente
 * @see PersonalBoard
 */

public class Slot {
    private int posIndex; //apparentemente inutile
    private int currentVictoryPoints;

    public Slot(int posIndex, int currentVictoryPoints) {
        this.posIndex = posIndex;
        this.currentVictoryPoints = currentVictoryPoints;
    }

    public int getPosIndex() {
        return posIndex;
    }

    public void setPosIndex(int posIndex) {
        this.posIndex = posIndex;
    }

    public int getCurrentVictoryPoints() {
        return currentVictoryPoints;
    }

    public void setCurrentVictoryPoints(int currentVictoryPoints) {
        this.currentVictoryPoints = currentVictoryPoints;
    }
}
