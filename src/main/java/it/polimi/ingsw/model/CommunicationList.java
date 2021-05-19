package it.polimi.ingsw.model;

/**
 * @author Mattia Sironi
 */
public enum CommunicationList {
    FP("You received one faith point!"),
    FIRST("You are first!"),
    SECOND("You are second!"),
    THIRD("You are third!"),
    FOURTH("You are fourth!"),
    VATICAN("Pope favour reached!"),
    VATICAN_YES("You were in vatican section!"),
    VATICAN_NOPE("You were not in vatican section!");


    private final String string;

    CommunicationList(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
