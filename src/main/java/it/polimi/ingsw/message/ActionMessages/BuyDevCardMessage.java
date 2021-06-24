package it.polimi.ingsw.message.ActionMessages;

import it.polimi.ingsw.message.Message;

/**
 * BuyDevCardMessage is used to start "Buy a DevCard" action.
 */

public class BuyDevCardMessage extends Message {
    private final int chosenIndex;
    private final int ID;
    private final boolean payFrom; //0 from Ware, 1 from Strong
    private final int slot;

    public BuyDevCardMessage(int chosenIndex, int ID, boolean payFrom, int slot) {
        this.chosenIndex = chosenIndex;
        this.ID = ID;
        this.payFrom = payFrom;
        this.slot = slot;
    }

    public int getID() {
        return ID;
    }

    public int getSlot() {
        return slot;
    }

    public int getChosenIndex() {
        return chosenIndex;
    }

    public boolean isPayFrom() {
        return payFrom;
    }


}
