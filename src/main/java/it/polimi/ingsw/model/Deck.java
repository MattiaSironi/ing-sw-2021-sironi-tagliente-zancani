package it.polimi.ingsw.model;
import java.util.ArrayList;

//Modifiche al Model UML: aggiunto Get() e Set() per l'attributo cards.

public class Deck {
    private int size;
    private int type; // using int for type of card recognition
    private ArrayList<Card> cards;

    public Deck(int size, int type, ArrayList<Card> cards) {
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

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }
}
