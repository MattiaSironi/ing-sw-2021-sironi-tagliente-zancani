package it.polimi.ingsw.model;

public enum ActionPhase {

    WAITING_FOR_ACTION ("is choosing an action!"),
    MARKET ("is buying resources!"),
    B_PAYMENT("is paying a Dev Card!"),
    CHOOSE_SLOT ("is choosing where to place the Dev Card!"),
    A_PAYMENT ("is paying a Leader Card!"),
    SELECT_RES("is choosing the resources to produce!"),
    BASIC ("is doing a basic production!"),
    PAYMENT("is paying!"),
    GAME_OVER("won!"),
    FIRST_ROUND ("is the first player");

    private final String others;

    ActionPhase(String others) {
        this.others = others;
    }

    public String getOthers() {
        return others;
    }
}
