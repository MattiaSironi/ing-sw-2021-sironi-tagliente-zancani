package it.polimi.ingsw.message.ActionMessages;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.model.ResourceType;

import java.util.ArrayList;

public class ProductionMessage extends Message {

    ArrayList<ResourceType> resFromWarehouse, resFromStrongbox, resToBuy;
    int ID;
    boolean endAction;

    public ProductionMessage(ArrayList<ResourceType> resFromWarehouse, ArrayList<ResourceType> resFromStrongbox,ArrayList<ResourceType> tobuy, int ID, boolean endAction) {
        this.resFromWarehouse = resFromWarehouse;
        this.resFromStrongbox = resFromStrongbox;
        this.resToBuy = tobuy;
        this.ID = ID;
        this.endAction = endAction;
    }

    public ArrayList<ResourceType> getResToBuy() {
        return resToBuy;
    }

    public void setResToBuy(ArrayList<ResourceType> resToBuy) {
        this.resToBuy = resToBuy;
    }

    public ArrayList<ResourceType> getResFromWarehouse() {
        return resFromWarehouse;
    }

    public void setResFromWarehouse(ArrayList<ResourceType> resFromWarehouse) {
        this.resFromWarehouse = resFromWarehouse;
    }

    public ArrayList<ResourceType> getResFromStrongbox() {
        return resFromStrongbox;
    }

    public void setResFromStrongbox(ArrayList<ResourceType> resFromStrongbox) {
        this.resFromStrongbox = resFromStrongbox;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isEndAction() {
        return endAction;
    }

    public void setEndAction(boolean endAction) {
        this.endAction = endAction;
    }
}
