package it.polimi.ingsw.view;

public enum Actions {
    M ("Take Resources from the Market", true),
    B ("Buy one Development Card", true),
    A ("Activate your Productions", true),
    END ("End your turn", true),
    SM ("Show Market", true),
    SF ("Show Faith Track", true),
    SD ("Show Development Cards", true),
    SP ("Show Productions", true),
    SL ("Show Leader Cards", true),
    SR ("Show Resources", true),
    MR ("Manage your Resources", true);



    private final String string;
    private boolean enable;

    Actions(String string, boolean isEnable) {
        this.string = string;
        this.enable = isEnable;
    }


    public String getString() {
        return string;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }


}
