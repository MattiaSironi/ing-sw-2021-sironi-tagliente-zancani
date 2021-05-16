package it.polimi.ingsw.model;

public enum ErrorList {
    INVALID_MOVE ("Invalid move"),
    NOT_ENOUGH_RES ("You do not have enough resources!"),
    NO_SLOTS ("No places available");





    private final String string;

    ErrorList(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
