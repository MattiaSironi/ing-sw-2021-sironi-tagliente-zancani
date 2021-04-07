package it.polimi.ingsw.model;

import java.util.ArrayList;

//Modifiche al Model UML: aggiunto Get() e Set() per l'attributo cards.

/**
 * Class Deck represents the deck of Development Cards
 *
 * @author Mattia Sironi
 * @see DevCard
 */

public class DevDeck {
    private int size;
    private int type; // using int for type of card recognition
    private ArrayList<DevCard> cards;

    public DevDeck(int size, int type, ArrayList<DevCard> cards) {
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

    public ArrayList<DevCard> getCards() {
        return cards;
    }

    public void setCards(ArrayList<DevCard> cards) {
        this.cards = cards;
    }
}
