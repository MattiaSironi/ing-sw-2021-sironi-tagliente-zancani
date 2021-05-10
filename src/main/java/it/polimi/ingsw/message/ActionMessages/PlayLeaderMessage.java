package it.polimi.ingsw.message.ActionMessages;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.model.LeaderCard;

public class PlayLeaderMessage extends Message {
    int ID;
    int idx;
    boolean action; //true = play, false = discard
    LeaderCard lc;

    public PlayLeaderMessage(int ID, int idx, boolean b, LeaderCard lc) {
        this.ID = ID;
        this.idx = idx;
        this.action = b;
        this.lc = lc;
    }

    public int getID() {
        return ID;
    }

    public int getIdx() {
        return idx;
    }

    public boolean getAction(){
        return action;
    }

    public LeaderCard getLc() {
        return lc;
    }
}
