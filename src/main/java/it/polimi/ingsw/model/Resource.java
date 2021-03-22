package it.polimi.ingsw.model;

public class Resource {
    private ResourceType type;
    private Tray tray;

    public Resource(ResourceType type, Tray tray) {
        this.type = type;
        this.tray = tray;
    }

    public ResourceType getType() { //just Getters?
        return type;
    }

    public Tray getTray() {
        return tray;
    } //?
}
