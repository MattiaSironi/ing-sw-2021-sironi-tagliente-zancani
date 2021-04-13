package it.polimi.ingsw.message;

public class Nickname extends Message {
    private String string;
    private int ID;

    public Nickname(String nickname, int ID) {
        this.string = nickname;
        this.ID = ID;
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
