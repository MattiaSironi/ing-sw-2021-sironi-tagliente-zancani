package it.polimi.ingsw.message;

public class ErrorMessage extends Message{
    private String string;
    private int ID;

    public ErrorMessage(String string, int ID) {
        this.string = string;
        this.ID = ID;
    }

    public String getString() {
        return string;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
