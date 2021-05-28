package it.polimi.ingsw.message.CommonMessages;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.model.ResourceType;

import java.util.Optional;

public class ErrorMessage extends Message {
    private final String string;
    private final int ID;


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

}
