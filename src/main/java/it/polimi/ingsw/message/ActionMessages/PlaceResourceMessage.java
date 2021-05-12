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
    private String error;

    public PlaceResourceMessage(ResourceType res, int shelf, int ID, String error) {
        this.res = res;
        this.shelf = shelf;
        this.ID = ID;
        this.error = error;
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

    public String getError() {
        return error;
    }
}
