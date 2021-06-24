package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Class DiscountLCard represents the model of the Leader Card with Discount Ability
 *
 * @author Mattia Sironi
 */

public class DiscountLCard extends LeaderCard implements Printable, Serializable {

    private final CardColor color1;
    private final CardColor color2;
    private final ResourceType resType;

    public DiscountLCard(int type, int victoryPoints, CardColor color1, CardColor color2, ResourceType resType) {
        super(type, victoryPoints);
        this.color1 = color1;
        this.color2 = color2;
        this.resType = resType;
    }

    public CardColor getColor1() {
        return color1;
    }

    public CardColor getColor2() {
        return color2;
    }

    public ResourceType getResType() {
        return resType;
    }

    @Override
    public boolean equals(LeaderCard lc) {
        if (!(lc instanceof DiscountLCard))
            return false;
        else {
            DiscountLCard other = (DiscountLCard) lc;
            return getColor1() == other.getColor1() &&
                    getColor2() == other.getColor2() &&
                    getResType() == other.getResType() &&
                    getVictoryPoints() == other.getVictoryPoints();
        }
    }

    /**
     * Method print prints to console a DiscountLCard instance.
     */

    @Override
    public void print() {
        String s = "\u2726";
        String ANSI_RESET = "\u001B[0m";
        String ANSI_YELLOW = "\u001B[33m";
        System.out.println(s+" "+s+" "+s+" "+s+" "+s+" "+s+" "+s+" "+s+" "+s+" "+s+ ANSI_YELLOW +
                " Discount Card "+ANSI_RESET+ s+" "+s+" "+s+" "+s+" "+s+" "+s+" "+s+" "+s+" "+s+" "+s+" "+
                "\nVictory points: " + this.getVictoryPoints() +
                "\nRequires: a "+ this.color1 + " development card (level 1 or higher)" +
                "\nRequires: a "+this.color2 +"  development card (level 1 or higher)"+
                "\nDiscount resource : " + this.resType.printResourceColouredName());
    }

    @Override
    public String toString() {
        return ("D"+"-" + this.getResType().toString());
    }
}

