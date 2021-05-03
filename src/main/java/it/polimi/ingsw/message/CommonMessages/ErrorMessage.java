package it.polimi.ingsw.message.CommonMessages;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.model.ResourceType;

import java.util.Optional;

public class ErrorMessage extends Message {
    private String string;
    private int ID;
    private ResourceType r= null;

    public ErrorMessage(String string, int ID) {
        this.string = string;
        this.ID = ID;
    }

    public ErrorMessage(String string, int ID, ResourceType r) {
        this.string = string;
        this.ID = ID;
        this.r = r;
    }

    public String getString() {
        return string;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public ResourceType getR() {
        return r;
    }
}
