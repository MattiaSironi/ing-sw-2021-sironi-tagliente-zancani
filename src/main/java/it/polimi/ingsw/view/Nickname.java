package it.polimi.ingsw.view;

public class Nickname {
    private String nickname;
    private int ID;

    public Nickname(String nickname, int ID) {
        this.nickname = nickname;
        this.ID = ID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
