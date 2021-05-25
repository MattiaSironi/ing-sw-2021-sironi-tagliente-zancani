package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Class SoloActionToken represents the model of the Solo Action Token used in single player mode
 *
 * @author Simone Tagliente
 */

public class SoloActionToken implements Serializable {
    private CardColor discard2Cards;
    private boolean moveBlack2;
    private boolean moveBlackAndShuffle;

    public SoloActionToken(CardColor discard2Cards, boolean moveBlack2, boolean moveBlackAndShuffle) {
        this.discard2Cards = discard2Cards;
        this.moveBlack2 = moveBlack2;
        this.moveBlackAndShuffle = moveBlackAndShuffle;
    }

    public CardColor getDiscard2Card() {
        return discard2Cards;
    }

    public boolean isMoveBlack2() {
        return moveBlack2;
    }

    public boolean isMoveBlackAndShuffle() {
        return moveBlackAndShuffle;
    }

    public void setDiscard2Card(CardColor discard2Card) {
        this.discard2Cards = discard2Card;
    }

    public void setMoveBlack2(boolean moveBlack2) {
        this.moveBlack2 = moveBlack2;
    }

    public void setMoveBlackAndShuffle(boolean moveBlackAndShuffle) {
        this.moveBlackAndShuffle = moveBlackAndShuffle;
    }

    public SoloActionToken clone()  {
        return new SoloActionToken(this.discard2Cards, this.moveBlack2, this.moveBlackAndShuffle);
    }

}
