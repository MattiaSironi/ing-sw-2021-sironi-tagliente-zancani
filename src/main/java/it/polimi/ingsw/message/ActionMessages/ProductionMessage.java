package it.polimi.ingsw.message.ActionMessages;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.model.DevCard;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.ResourceType;

import java.util.ArrayList;

public class ProductionMessage extends Message {

    private final ArrayList<ResourceType> resFromWarehouse, resFromStrongbox, resToBuy;
    private final int ID;
    private final boolean endAction;
    private final LeaderCard d;
    private final DevCard dc;

    public ProductionMessage(ArrayList<ResourceType> resFromWarehouse, ArrayList<ResourceType> resFromStrongbox, ArrayList<ResourceType> tobuy, LeaderCard d, DevCard dc, int ID, boolean endAction) {
        this.resFromWarehouse = resFromWarehouse;
        this.resFromStrongbox = resFromStrongbox;
        this.resToBuy = tobuy;
        this.ID = ID;
        this.endAction = endAction;
        this.d = d;
        this.dc=dc;
    }

    public ArrayList<ResourceType> getResToBuy() {
        return resToBuy;
    }



    public ArrayList<ResourceType> getResFromWarehouse() {
        return resFromWarehouse;
    }



    public ArrayList<ResourceType> getResFromStrongbox() {
        return resFromStrongbox;
    }

    public int getID() {
        return ID;
    }

    public boolean isEndAction() {
        return endAction;
    }

    public LeaderCard getD() {
        return d;
    }

    public DevCard getDc() {
        return dc;
    }

}
