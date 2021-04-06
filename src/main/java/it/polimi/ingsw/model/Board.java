package it.polimi.ingsw.model;
import java.util.ArrayList;

/**
 * Class Board represents the main game board of "Master of Renaissance. It's composed by the Market, the Leader Deck, the matrix of Development Cards and the pile of Solo
 * Action Token if the game is single player.
 *
 * @author Simone Tagliente
 */

public class Board {
    private Market market;
    private LeaderDeck leaderDeck;
    private ArrayList<DevDeck> devDecks;
    private ArrayList<SoloActionToken> tokenArray;

    public Board(Market market, LeaderDeck leaderDeck, ArrayList<DevDeck> devDecks, ArrayList<SoloActionToken> tokenArray) {
        this.market = market;
        this.leaderDeck = leaderDeck;
        this.devDecks = devDecks;
        this.tokenArray = tokenArray;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public LeaderDeck getLeaderDeck() {
        return leaderDeck;
    }

    public void setLeaderDeck(LeaderDeck leaderDeck) {
        this.leaderDeck = leaderDeck;
    }

    public ArrayList<DevDeck> getDevDecks() {
        return devDecks;
    }

    public void setDevDecks(ArrayList<DevDeck> devDecks) {
        this.devDecks = devDecks;
    }

    public ArrayList<SoloActionToken> getTokenArray() {
        return tokenArray;
    }

    public void setTokenArray(ArrayList<SoloActionToken> tokenArray) {
        this.tokenArray = tokenArray;
    }
}

