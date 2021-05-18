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

