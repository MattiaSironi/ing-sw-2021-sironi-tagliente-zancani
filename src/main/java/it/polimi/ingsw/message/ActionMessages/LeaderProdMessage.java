package it.polimi.ingsw.message.ActionMessages;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.ResourceType;

public class LeaderProdMessage extends Message { //TODO DELETE ?
    int id;
    LeaderCard l;
    ResourceType chosenRes;

    public LeaderProdMessage(int id, LeaderCard l, ResourceType res) {
        this.id = id;
        this.l = l;
        this.chosenRes = res;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LeaderCard getL() {
        return l;
    }

    public void setL(LeaderCard l) {
        this.l = l;
    }

    public ResourceType getChosenRes() {
        return chosenRes;
    }

    public void setChosenRes(ResourceType chosenRes) {
        this.chosenRes = chosenRes;
    }
}
