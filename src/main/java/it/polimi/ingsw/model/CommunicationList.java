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
    VATICAN_NOPE("You were not in vatican section!"),
    COINDISCOUNT("You got a discount of 1 " + ResourceType.COIN.printResourceColouredName() + "!"),
    STONEDISCOUNT("You got a discount of 1 " + ResourceType.STONE.printResourceColouredName() + "!"),
    SERVANTDISCOUNT("You got a discount of 1 " + ResourceType.SERVANT.printResourceColouredName() + "!"),
    SHIELDDISCOUNT("You got a discount of 1 " + ResourceType.SHIELD.printResourceColouredName() + "!"),
    NO_PAYMENT_NEEDED("All the resources were discounted!"),
    SEVENCARDS("You have just bought your seventh Develpment Card!");


    private final String string;

    CommunicationList(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
