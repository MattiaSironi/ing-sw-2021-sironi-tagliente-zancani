package it.polimi.ingsw.message.ActionMessages;

import it.polimi.ingsw.message.Message;

/**
 * EndTurnMessage is used to end the turn.
 */

public class EndTurnMessage extends Message {
    private final int ID;

    public EndTurnMessage(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }
}
