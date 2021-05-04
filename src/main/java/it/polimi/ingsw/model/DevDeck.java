package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

//Modifiche al Model UML: aggiunto Get() e Set() per l'attributo cards.

/**
 * Class Deck represents the deck of Development Cards
 *
 * @author Mattia Sironi
 * @see DevCard
 */

public class DevDeck implements Printable {
    private int size;
    private int type; // using int for type of card recognition
    private List<DevCard> cards;

    public DevDeck(int size, int type, List<DevCard> cards) {
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

    public List<DevCard> getCards() {
        return cards;
    }

    public void setCards(ArrayList<DevCard> cards) {
        this.cards = cards;
    }

    public void print(){
        this.cards.get(0).print();
    }
}

