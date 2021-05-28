package it.polimi.ingsw.message.ActionMessages;

import it.polimi.ingsw.message.Message;

public class ManageResourceMessage extends Message {
    private final int shelf1;
    private final int shelf2;
    private final int ID;

    public ManageResourceMessage(int shelf1, int shelf2, int ID) {
        this.shelf1 = shelf1;
        this.shelf2 = shelf2;
        this.ID = ID;
    }

    public int getShelf1() {
        return shelf1;
    }

    public int getShelf2() {
        return shelf2;
    }

    public int getID() {
        return ID;
    }

}
