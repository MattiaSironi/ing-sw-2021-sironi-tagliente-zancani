package it.polimi.ingsw.message.CommonMessages;

import it.polimi.ingsw.message.Message;

public class IdMessage extends Message {
    private final int ID;

    public IdMessage(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }
}
