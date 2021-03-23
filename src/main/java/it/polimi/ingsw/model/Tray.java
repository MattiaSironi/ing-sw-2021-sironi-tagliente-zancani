package it.polimi.ingsw.model;

public class Tray {
    private ResourceType res;

    public Tray(ResourceType res) {
        this.res = res;
    }

    public ResourceType getRes() {
        return res;
    }

    public void setRes(ResourceType res) {
        this.res = res;
    }
}
