package it.polimi.ingsw.model;

/**
 * This Enum contains all possible actions player can do in this game, besides managing resources and end the turn
 *
 */
public enum ActionPhase {

    WAITING_FOR_ACTION ("is choosing an action!"),
    MARKET ("is buying resources!"),
    B_PAYMENT("is paying a Dev Card!"),
    CHOOSE_SLOT ("is choosing where to place the Dev Card!"),
    A_PAYMENT ("is producing resources!"),
    SELECT_RES("is choosing the resources to produce!"),
    BASIC ("is doing a basic production!"),
    PAYMENT("is paying!"),
    GAME_OVER("won!"),
    FIRST_ROUND ("is the first player"),
    D_PAYMENT("is using a development card's production");

    private final String others;

    ActionPhase(String others) {
        this.others = others;
    }

    /**
     * this method gets a string that players not in turn will see.
     * @return String others.
     */
    public String getOthers() {
        return others;
    }
}
