package it.polimi.ingsw.message;

import java.io.Externalizable;
import java.io.Serializable;

public class Nickname extends Message implements Serializable {
    private String string;
    private int ID;
    private Boolean valid;

    public Nickname(String nickname, int ID, boolean valid) {
        this.string = nickname;
        this.ID = ID;
        this.valid = valid;
    }

    public Boolean getValid() {
        return valid;
    }

    public String getString() {
        return string;
    }

    public void setString(String nickname) {
        this.string = nickname;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
