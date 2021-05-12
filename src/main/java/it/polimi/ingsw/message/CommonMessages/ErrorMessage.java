package it.polimi.ingsw.message.CommonMessages;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.model.ResourceType;

import java.util.Optional;

public class ErrorMessage extends Message {
    private String string;
    private int ID;


    public ErrorMessage(String string, int ID) {
        this.string = string;
        this.ID = ID;
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


}
