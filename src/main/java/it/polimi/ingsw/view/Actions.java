package it.polimi.ingsw.view;

public enum Actions {
    M ("Take Resources from the Market", false),
    B ("Buy one Development Card", false),
    A ("Activate the Production", false),
    END ("End your turn", false),
    SM ("Show Market", true),
    SF ("Show Faith Track", true),
    SD ("Show Development Cards", true),
    SP ("Show Productions", true),
    SL ("Show Leader Cards", true),
    SR ("Show Resources", true),
    MR ("Manage your Resources", true);


    private final String string;
    private boolean isMulti;

    Actions(String string, boolean isMulti) {
        this.string = string;
        this.isMulti = isMulti;
    }

    public String getString() {
        return string;
    }

    public boolean isMulti() {
        return isMulti;
    }
}
