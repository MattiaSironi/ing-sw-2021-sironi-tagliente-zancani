package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class LeaderDeck represents the deck of Leader Cards
 *
 * @author Mattia Sironi
 */

public class LeaderDeck implements Printable, Serializable, Cloneable {

    private ArrayList<LeaderCard> cards;

    public LeaderDeck(ArrayList<LeaderCard> cards) {
        this.cards = cards;
    }

    /**
     * Method clone creates a deep copy of a LeaderDeck instance
     * @return a deep copy of a LeaderDeck instance.
     */

    public LeaderDeck clone(){
        LeaderDeck clone = new LeaderDeck(new ArrayList<>());
        for(LeaderCard ld : this.cards) clone.getCards().add(ld);
        return clone;

    }



    public ArrayList<LeaderCard> getCards() {
        return cards;
    }

    public void setCards(ArrayList<LeaderCard> cards) {
        this.cards = cards;
    }

    @Override
    public void print(){
        int i=1;
        for(LeaderCard c : this.getCards()){
            System.out.println("Leader " + i + " : ");
            c.print();
            i++;
            System.out.println();
        }
    }


    /**
     * this method checks if a card is present in the deck.
     * @return true if the card is present.
     */
    public boolean isPresent(LeaderCard c){
        for(LeaderCard l: this.getCards()){
            if(l.equals(c)){
                return true;
            }
        }
        return false;
    }
}

