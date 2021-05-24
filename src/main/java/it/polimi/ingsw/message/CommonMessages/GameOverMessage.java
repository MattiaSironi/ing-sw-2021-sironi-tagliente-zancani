package it.polimi.ingsw.message.CommonMessages;

/**
 * @author Mattia Sironi
 */
public class GameOverMessage {
    private int winnerID;

    public GameOverMessage(int winnerID) {
        this.winnerID = winnerID;
    }

    public int getWinnerID() {
        return winnerID;
    }
}
