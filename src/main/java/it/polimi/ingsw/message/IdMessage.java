package it.polimi.ingsw.message;

public class IdMessage extends Message {
    private int ID;

    public IdMessage(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }
}
