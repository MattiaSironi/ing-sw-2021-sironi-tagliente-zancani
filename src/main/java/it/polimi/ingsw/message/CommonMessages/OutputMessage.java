package it.polimi.ingsw.message.CommonMessages;

import it.polimi.ingsw.message.Message;

public class OutputMessage extends Message {

    private String string;

    public OutputMessage(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
