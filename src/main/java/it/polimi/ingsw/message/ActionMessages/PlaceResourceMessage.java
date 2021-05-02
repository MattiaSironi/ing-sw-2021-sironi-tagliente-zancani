package it.polimi.ingsw.message.ActionMessages;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.model.ResourceType;

/**
 * @author Mattia Sironi
 */
public class PlaceResourceMessage extends Message {

    private ResourceType res;
    private int shelf;
    private int ID;

    public PlaceResourceMessage(ResourceType res, int shelf, int ID) {
        this.res = res;
        this.shelf = shelf;
        this.ID = ID;
    }

    public ResourceType getRes() {
        return res;
    }

    public int getShelf() {
        return shelf;
    }

    public int getID() {
        return ID;
    }
}
