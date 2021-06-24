package it.polimi.ingsw.message.ActionMessages;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.model.ResourceType;

/**
 * LeaderProductionMessage is used to activate a ExtraProdLCard production.
 */

public class LeaderProductionMessage extends Message {
    int index, ID;
    ResourceType wantedRes;

    public LeaderProductionMessage(int index, int ID, ResourceType wantedRes) {
        this.index = index;
        this.ID = ID;
        this.wantedRes = wantedRes;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ResourceType getWantedRes() {
        return wantedRes;
    }

    public void setWantedRes(ResourceType wantedRes) {
        this.wantedRes = wantedRes;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
