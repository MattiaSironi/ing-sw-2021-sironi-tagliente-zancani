package it.polimi.ingsw.message.ActionMessages;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.model.ResourceType;

public class BasicProductionMessage extends Message {
    private ResourceType paidRes1;
    private ResourceType paidRes2;
    private ResourceType boughtRes;
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

    public void setPaidRes1(ResourceType paidRes1) {
        this.paidRes1 = paidRes1;
    }

    public ResourceType getPaidRes2() {
        return paidRes2;
    }

    public void setPaidRes2(ResourceType paidRes2) {
        this.paidRes2 = paidRes2;
    }

    public ResourceType getBoughtRes() {
        return boughtRes;
    }

    public void setBoughtRes(ResourceType boughtRes) {
        this.boughtRes = boughtRes;
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

    public void setPayFrom(boolean payFrom) {
        this.payFrom = payFrom;
    }
}
