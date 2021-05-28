package it.polimi.ingsw.message.ActionMessages;

import it.polimi.ingsw.message.Message;

/**
 * @author Mattia Sironi
 */
public class MarketMessage extends Message {
    private final boolean row;
    private final int index;
    private final int ID;

    public MarketMessage(boolean row, int index, int ID) {
        this.row = row;
        this.index = index;
        this.ID = ID;
    }

    public boolean isRow() {
        return row;
    }

    public int getIndex() {
        return index;
    }

    public int getID() {
        return ID;
    }
}
