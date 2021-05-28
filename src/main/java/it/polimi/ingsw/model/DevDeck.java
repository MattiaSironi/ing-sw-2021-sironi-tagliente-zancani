package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//Modifiche al Model UML: aggiunto Get() e Set() per l'attributo cards.

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

    public void print(){
        this.cards.get(0).print();
    }

    public DevDeck clone() {
        DevDeck clone = new DevDeck(new ArrayList<>());
        clone.cards.addAll(this.cards);
        return clone;
    }
}

