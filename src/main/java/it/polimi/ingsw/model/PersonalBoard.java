package it.polimi.ingsw.model;

import java.util.ArrayList;

public class PersonalBoard {
    private final ArrayList<Slot> faithTrack;
    private ArrayList<Deck> cardSlot;
    private ArrayList<Card> activeLeader;
    private int numDevCards;
    private boolean favorTile1;
    private boolean favorTile2;
    private boolean favorTile3;
    private final Strongbox strongbox;
    private final Warehouse warehouse;

    public PersonalBoard(ArrayList<Slot> faithTrack, ArrayList<Deck> cardSlot, ArrayList<Card> activeLeader, int numDevCards, boolean favorTile1, boolean favorTile2, boolean favorTile3, Strongbox strongbox, Warehouse warehouse) {
        this.faithTrack = faithTrack;
        this.cardSlot = cardSlot;
        this.activeLeader = activeLeader;
        this.numDevCards = numDevCards;
        this.favorTile1 = favorTile1;
        this.favorTile2 = favorTile2;
        this.favorTile3 = favorTile3;
        this.strongbox = strongbox;
        this.warehouse = warehouse;
    }

    public void setCardSlot(ArrayList<Deck> cardSlot) {
        this.cardSlot = cardSlot;
    }

    public void setActiveLeader(ArrayList<Card> activeLeader) {
        this.activeLeader = activeLeader;
    }

    public void setNumDevCards(int numDevCards) {
        this.numDevCards = numDevCards;
    }

    public void setFavorTile1(boolean favorTile1) {
        this.favorTile1 = favorTile1;
    }

    public void setFavorTile2(boolean favorTile2) {
        this.favorTile2 = favorTile2;
    }

    public void setFavorTile3(boolean favorTile3) {
        this.favorTile3 = favorTile3;
    }

    public ArrayList<Deck> getCardSlot() {
        return cardSlot;
    }

    public ArrayList<Card> getActiveLeader() {
        return activeLeader;
    }

    public int getNumDevCards() {
        return numDevCards;
    }

    public boolean isFavorTile1() {
        return favorTile1;
    }

    public boolean isFavorTile2() {
        return favorTile2;
    }

    public boolean isFavorTile3() {
        return favorTile3;
    }

    public void regularProduction (Resource r1, Resource r2, Resource r3){
            //controllo se posso pagare con le mie risorse e aggiorno le risorse?
    }

}
