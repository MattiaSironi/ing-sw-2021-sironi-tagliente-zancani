package it.polimi.ingsw.view;

public enum MultiActions {

    SM ("Show Market"), SF ("Show Faith Track"), SD ("Show Development Cards"), SP ("Show Productions"), SL ("Show Leader Cards"), SR ("Show Resources"), MR ("Manage your Resources");

    private final String string;

    MultiActions(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
