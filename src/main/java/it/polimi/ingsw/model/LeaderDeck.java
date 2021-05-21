package it.polimi.ingsw.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class LeaderDeck represents the deck of Leader Cards
 *
 * @author Mattia Sironi
 */

public class LeaderDeck implements Printable, Serializable {
 // using int for type of card recognition
    private ArrayList<LeaderCard> cards;

    public LeaderDeck(ArrayList<LeaderCard> cards) {
        this.cards = cards;
    }





    public ArrayList<LeaderCard> getCards() {
        return cards;
    }

    public void setCards(ArrayList<LeaderCard> cards) {
        this.cards = cards;
    }

    @Override
//    public void print() {
//        int i=1;
//        for(LeaderCard c : this.getCards()){
//            System.out.println("Leader " + i + " : ");
//            switch (c.getType()){
//                case 1 -> { ((DiscountLCard) c).print();}
//                case 2-> { ((ExtraDepotLCard) c).print();}
//                case 3-> { ((ExtraProdLCard) c).print();}
//                case 4-> { ((WhiteTrayLCard) c).print();}
//            }
//            i++;
//            System.out.println("");
//        }
//    }



    public void print(){
        int i=1;
        for(LeaderCard c : this.getCards()){
            System.out.println("Leader " + i + " : ");
            switch (c.getType()){
                case 1 -> { ((DiscountLCard) c).print();}
                case 2-> { ((ExtraDepotLCard) c).print();}
                case 3-> { ((ExtraProdLCard) c).print();}
                case 4-> { ((WhiteTrayLCard) c).print();}
            }
            i++;
            System.out.println("");
        }
    }
}

