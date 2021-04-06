package it.polimi.ingsw.model;

/**
 * Class LorenzoIlMagnifico keeps track of the position of Lorenzo Il Magnifico if the game is single player
 *
 * @author Simone Tagliente
 */

public class LorenzoIlMagnifico {
    int blackCrossPos;

    public LorenzoIlMagnifico(int blackCrossPos) {
        this.blackCrossPos = blackCrossPos;
    }

    public int getBlackCrossPos() {
        return blackCrossPos;
    }

    public void setBlackCrossPos(int blackCrossPos) {
        this.blackCrossPos = blackCrossPos;
    }
}
