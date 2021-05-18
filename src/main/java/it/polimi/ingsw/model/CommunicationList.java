package it.polimi.ingsw.model;

/**
 * @author Mattia Sironi
 */
public enum CommunicationList {
    FP("You received one faith point!"),
    FIRST("You are first!"),
    SECOND("You are second!"),
    THIRD("You are third!"),
    FOURTH("You are fourth!");


    private final String string;

    CommunicationList(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
