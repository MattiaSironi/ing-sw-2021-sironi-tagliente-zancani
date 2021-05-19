package it.polimi.ingsw.message.ActionMessages;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.model.DevCard;
import it.polimi.ingsw.model.ResourceType;

import java.util.ArrayList;

public class BuyDevCardMessage extends Message {
    int choosenIndex;
    int ID;
    boolean payFrom; //0 from Ware, 1 from Strong
//    DevCard d;
//    ArrayList<ResourceType> resFromWarehouse, resFromStrongbox;
    int slot;

    public BuyDevCardMessage(int choosenIndex, int ID, boolean payFrom, int slot) {
        this.choosenIndex = choosenIndex;
        this.ID = ID;
        this.payFrom = payFrom;
//        this.d = d;
//        this.resFromWarehouse = resFromWarehouse;
//        this.resFromStrongbox = resFromStrongbox;
        this.slot = slot;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

//    public DevCard getD() {
//        return d;
//    }
//
//    public void setD(DevCard d) {
//        this.d = d;
//    }

//    public ArrayList<ResourceType> getResFromWarehouse() {
//        return resFromWarehouse;
//    }

//    public void setResFromWarehouse(ArrayList<ResourceType> resFromWarehouse) {
//        this.resFromWarehouse = resFromWarehouse;
//    }
//
//    public ArrayList<ResourceType> getResFromStrongbox() {
//        return resFromStrongbox;
//    }
//
//    public void setResFromStrongbox(ArrayList<ResourceType> resFromStrongbox) {
//        this.resFromStrongbox = resFromStrongbox;
//    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getChoosenIndex() {
        return choosenIndex;
    }

    public void setChoosenIndex(int choosenIndex) {
        this.choosenIndex = choosenIndex;
    }

    public boolean isPayFrom() {
        return payFrom;
    }

    public void setPayFrom(boolean payFrom) {
        this.payFrom = payFrom;
    }
}
