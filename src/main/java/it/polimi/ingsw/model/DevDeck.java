package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



/**
 * Class Deck represents the deck of Development Cards
 *
 * @author Mattia Sironi
 * @see DevCard
 */

public class DevDeck implements Printable, Serializable {


    private final List<DevCard> cards;

    public DevDeck(List<DevCard> cards) {

        this.cards = cards;
    }

    public void removeCardFromCards(){
        cards.remove(0);
    }


    public List<DevCard> getCards() {
        return cards;
    }

    /**
     * This method prints to console first DevCard in a DevDeck
     */

    public void print(){
        this.cards.get(0).print();
    }

    /**
     * This method makes a deep copy of the DevDeck.
     * @return a deep clone of the DevDeck.
     */

    public DevDeck clone() {
        DevDeck clone = new DevDeck(new ArrayList<>());
        clone.cards.addAll(this.cards);
        return clone;
    }
}

