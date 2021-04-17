package it.polimi.ingsw.message;

public class ErrorMessage extends Message{
    private String string;

    public ErrorMessage(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
