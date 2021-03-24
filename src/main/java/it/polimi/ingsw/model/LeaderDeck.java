package it.polimi.ingsw.model;

import java.util.ArrayList;

public class LeaderDeck {
    private int size;
    private int type; // using int for type of card recognition
    private ArrayList<LeaderCard> cards;

    public LeaderDeck(int size, int type, ArrayList<LeaderCard> cards) {
        this.size = size;
        this.type = type;
        this.cards = cards;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<LeaderCard> getCards() {
        return cards;
    }

    public void setCards(ArrayList<LeaderCard> cards) {
        this.cards = cards;
    }
}

