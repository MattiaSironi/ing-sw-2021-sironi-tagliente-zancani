package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Class Turn is the part of the model players check to know whether is their turn or not.
 * @author Mattia Sironi
 */
public class Turn implements Serializable { //objectID = 10

    private int playerPlayingID = -1;
    private ActionPhase phase;


    public int getPlayerPlayingID() {
        return playerPlayingID;
    }

    public void setPlayerPlayingID(int playerPlayingID) {
        this.playerPlayingID = playerPlayingID;
    }

    public ActionPhase getPhase() {
        return phase;
    }

    public void setPhase(ActionPhase phase) {
        this.phase = phase;
    }


    /**
     * This method makes a deep copy of a Turn instance.
     * @return a deep copy of a Turn instance
     */
    public Turn clone()  {
        Turn clone = new Turn();
        clone.playerPlayingID = this.playerPlayingID;
        clone.phase = this.phase;
        return clone;
    }
}
