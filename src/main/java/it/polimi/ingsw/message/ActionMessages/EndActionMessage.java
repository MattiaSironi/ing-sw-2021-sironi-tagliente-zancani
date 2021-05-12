package it.polimi.ingsw.message.ActionMessages;

import it.polimi.ingsw.message.Message;

/**
 * @author Mattia Sironi
 */
public class EndActionMessage extends Message {
    private int ID;

    public EndActionMessage(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }
}
