package it.polimi.ingsw.message;

public class OutputMessage extends Message {

    private String string;

    public OutputMessage(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
