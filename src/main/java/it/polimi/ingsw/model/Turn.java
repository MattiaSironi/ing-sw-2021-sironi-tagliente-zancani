package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * @author Mattia Sironi
 */
public class Turn implements Serializable { //objectID = 10

    private int playerPlayingID;
    private String phase;

    public int getPlayerPlayingID() {
        return playerPlayingID;
    }

    public void setPlayerPlayingID(int playerPlayingID) {
        this.playerPlayingID = playerPlayingID;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }
}
