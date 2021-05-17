package it.polimi.ingsw.model;

public enum ErrorList {
    INVALID_MOVE ("Invalid move"),
    NOT_ENOUGH_RES ("You do not have enough resources!"),
    NO_SLOTS ("No places available"),
    ZERO_CARDS("No Leaders to discard"),
    TWO_LEADERS("You already have 2 active Leaders");





    private final String string;

    ErrorList(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
