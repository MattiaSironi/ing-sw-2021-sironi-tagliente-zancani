package it.polimi.ingsw.view;

public enum SingleActions {
    M ("Take Resources from the Market"), B ("Buy one Development Card"), A ("Activate the Production"), END ("End your turn");

    private final String string;

    SingleActions(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
