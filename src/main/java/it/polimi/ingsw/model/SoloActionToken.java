package it.polimi.ingsw.model;

public class SoloActionToken {
    private CardColor discard2Card;
    private boolean moveBlack2;
    private boolean moveBlackAndShuffle;

    public SoloActionToken(CardColor discard2Card, boolean moveBlack2, boolean moveBlackAndShuffle) {
        this.discard2Card = discard2Card;
        this.moveBlack2 = moveBlack2;
        this.moveBlackAndShuffle = moveBlackAndShuffle;
    }

    public CardColor getDiscard2Card() {
        return discard2Card;
    }

    public boolean isMoveBlack2() {
        return moveBlack2;
    }

    public boolean isMoveBlackAndShuffle() {
        return moveBlackAndShuffle;
    }

    public void setDiscard2Card(CardColor discard2Card) {
        this.discard2Card = discard2Card;
    }

    public void setMoveBlack2(boolean moveBlack2) {
        this.moveBlack2 = moveBlack2;
    }

    public void setMoveBlackAndShuffle(boolean moveBlackAndShuffle) {
        this.moveBlackAndShuffle = moveBlackAndShuffle;
    }

}
