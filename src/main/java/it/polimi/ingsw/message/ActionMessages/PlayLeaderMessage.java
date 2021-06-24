package it.polimi.ingsw.message.ActionMessages;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.model.LeaderCard;

/**
 * PlayLeaderMessage is used to activate or discard a LeaderCard.
 */

public class PlayLeaderMessage extends Message {
    private final int ID;
    private final int idx;
    private final boolean action; //true = play, false = discard
    private final LeaderCard lc;
    private final boolean initialPhase;

    public PlayLeaderMessage(int ID, int idx, boolean b, LeaderCard lc, boolean initialPhase) {
        this.ID = ID;
        this.idx = idx;
        this.action = b;
        this.lc = lc;
        this.initialPhase = initialPhase;
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

    public boolean isInitialPhase() {
        return initialPhase;
    }

}
