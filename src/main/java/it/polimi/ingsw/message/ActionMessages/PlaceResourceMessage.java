package it.polimi.ingsw.message.ActionMessages;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.model.ResourceType;

import java.util.ArrayList;

/**
 * @author Mattia Sironi
 */
public class PlaceResourceMessage extends Message {

    private final ResourceType res;
    private final int shelf;
    private final int ID;
    private final boolean initialPhase;
    private final boolean discard;

    public PlaceResourceMessage(ResourceType res, int shelf, int ID, boolean initialPhase, boolean discard) {
        this.res = res;
        this.shelf = shelf;
        this.ID = ID;
        this.initialPhase = initialPhase;
        this.discard = discard;
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

    public boolean isInitialPhase() {
        return initialPhase;
    }

    public boolean isDiscard() {
        return discard;
    }
}
