package it.polimi.ingsw.message.ActionMessages;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.model.Marble;

import java.util.ArrayList;

/**
 * @author Mattia Sironi
 */
public class ResourceListMessage extends Message {
    private ArrayList<Marble> marbles;
    private int ID;

    public ResourceListMessage(ArrayList<Marble> marbles, int ID) {
        this.marbles = marbles;
        this.ID = ID;
    }

    public ArrayList<Marble> getMarbles() {
        return marbles;
    }

    public int getID() {
        return ID;
    }
}
