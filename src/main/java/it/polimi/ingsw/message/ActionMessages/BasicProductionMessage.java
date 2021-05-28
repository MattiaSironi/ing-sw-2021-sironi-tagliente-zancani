package it.polimi.ingsw.message.ActionMessages;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.model.ResourceType;

public class BasicProductionMessage extends Message {
    private final ResourceType paidRes1;
    private final ResourceType paidRes2;
    private final ResourceType boughtRes;
    boolean payFrom;
    int ID;

    public BasicProductionMessage(ResourceType paidRes1, ResourceType paidRes2, ResourceType boughtRes, int ID, boolean payFrom) {
        this.paidRes1 = paidRes1;
        this.paidRes2 = paidRes2;
        this.boughtRes = boughtRes;
        this.ID = ID;
        this.payFrom = payFrom;
    }

    public ResourceType getPaidRes1() {
        return paidRes1;
    }

    public ResourceType getPaidRes2() {
        return paidRes2;
    }

    public ResourceType getBoughtRes() {
        return boughtRes;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isPayFrom() {
        return payFrom;
    }

}
