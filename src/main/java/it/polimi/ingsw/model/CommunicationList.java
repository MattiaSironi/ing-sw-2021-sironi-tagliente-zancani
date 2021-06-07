package it.polimi.ingsw.model;

/**
 * @author Mattia Sironi
 */
public enum CommunicationList {
    NICK_NOT_VALID("This nickname has already been chosen!"),
    NICK_VALID ("Nickname set correctly"),
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
    LORI_CAP ("Lorenzo Il Magnifico reached the end of the Faith track!"),
    PLAYER_CAP("You reached the end of the Faith Track!"),
    LORI_MOVE2("Token drawn. Lorenzo Il Magnifico moved by two positions on the Faith Track!"),
    LORI_MOVE("Token drawn. Lorenzo Il Magnifico moved by one position on the Faith Track and all Tokens have been shuffled!"),
    SEVENCARDS("You have just bought your seventh Development Card!"),
    LORI_YELLOW("Token drawn. Two YELLOW cards got discarded!"),
    LORI_BLUE("Token drawn. Two BLUE cards got discarded!"),
    LORI_PURPLE("Token drawn. Two PURPLE cards got discarded!"),
    LORI_GREEN("Token drawn. Two GREEN cards got discarded!"),
    EMPTYCOLUMN("One type of Development card is no longer avaiable in the grid"),
    INVALID_MOVE ("Invalid move"),
    NOT_ENOUGH_RES ("You do not have enough resources!"),
    NO_SLOTS ("No places available"),
    ZERO_CARDS("No Leaders to discard"),
    TWO_LEADERS("You already have 2 active Leaders"),
    NO_REQUIREMENTS("Can't activate this card");


    private final String string;

    CommunicationList(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
