package it.polimi.ingsw.message.ActionMessages;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.model.DevCard;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.ResourceType;

import java.util.ArrayList;

/**
 * ProductionMessage is used to start and finish "Production" action in game.
 */

public class ProductionMessage extends Message {

    private final int ID;
    private final boolean endAction;
    private int index;
    private boolean payFrom;

    public ProductionMessage(int ID, boolean endAction, int index, boolean payFrom) {
        this.ID = ID;
        this.endAction = endAction;
        this.index = index;
        this.payFrom = payFrom;

    }

    public int getID() {
        return ID;
    }

    public boolean isEndAction() {
        return endAction;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isPayFrom() {
        return payFrom;
    }

    public void setPayFrom(boolean payFrom) {
        this.payFrom = payFrom;
    }
}
