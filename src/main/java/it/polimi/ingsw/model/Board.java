package it.polimi.ingsw.model;
import java.util.ArrayList;

public class Board {
    private Market market;
    private Deck leaderDeck;
    private ArrayList<Deck> devDecks;
    private ArrayList<SoloActionToken> tokenArray;

    public Board(Market market, Deck leaderDeck, ArrayList<Deck> devDecks, ArrayList<SoloActionToken> tokenArray) {
        this.market = market;
        this.leaderDeck = leaderDeck;
        this.devDecks = devDecks;
        this.tokenArray = tokenArray;
    }

    public Market getMarket() {
        return market;
    }

    public Deck getLeaderDeck() {
        return leaderDeck;
    }

    public ArrayList<Deck> getDevDecks() {
        return devDecks;
    }

    public ArrayList<SoloActionToken> getTokenArray() {
        return tokenArray;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public void setLeaderDeck(Deck leaderDeck) {
        this.leaderDeck = leaderDeck;
    }

    public void setDevDecks(ArrayList<Deck> devDecks) {
        this.devDecks = devDecks;
    }

    public void setTokenArray(ArrayList<SoloActionToken> tokenArray) {
        this.tokenArray = tokenArray;
    }
}
