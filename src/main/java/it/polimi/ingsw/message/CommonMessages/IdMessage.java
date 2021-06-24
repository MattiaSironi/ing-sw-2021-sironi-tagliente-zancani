package it.polimi.ingsw.message.CommonMessages;

import it.polimi.ingsw.message.Message;

/**
 * IDMessage is sent from Server to Client to make sure Client knows what it his ID in game.
 */

public class IdMessage extends Message {
    private final int ID;

    public IdMessage(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }
}
