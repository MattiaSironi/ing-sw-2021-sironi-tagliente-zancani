package it.polimi.ingsw.message.CommonMessages;

import it.polimi.ingsw.message.Message;

import java.io.Externalizable;
import java.io.Serializable;

/**
 * Nickname Message is sent to set a player's nickname.
 */
public class Nickname extends Message implements Serializable {
    private final String string;
    private final int ID;


    public Nickname(String nickname, int ID) {
        this.string = nickname;
        this.ID = ID;

    }

    public String getString() {
        return string;
    }

    public int getID() {
        return ID;
    }


}
