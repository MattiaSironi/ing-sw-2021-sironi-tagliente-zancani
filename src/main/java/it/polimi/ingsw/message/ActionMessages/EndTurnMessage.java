package it.polimi.ingsw.message.ActionMessages;

import it.polimi.ingsw.message.Message;

public class EndTurnMessage extends Message {
    private final int ID;

    public EndTurnMessage(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }
}
