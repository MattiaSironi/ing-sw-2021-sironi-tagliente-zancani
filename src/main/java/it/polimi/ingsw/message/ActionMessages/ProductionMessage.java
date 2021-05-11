package it.polimi.ingsw.message.ActionMessages;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.model.ResourceType;

import java.util.ArrayList;

public class ProductionMessage extends Message {

    ArrayList<ResourceType> resFromWarehouse, resFromStrongbox;
    int ID;

    public ProductionMessage(ArrayList<ResourceType> resFromWarehouse, ArrayList<ResourceType> resFromStrongbox, int ID) {
        this.resFromWarehouse = resFromWarehouse;
        this.resFromStrongbox = resFromStrongbox;
        this.ID = ID;
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
}
