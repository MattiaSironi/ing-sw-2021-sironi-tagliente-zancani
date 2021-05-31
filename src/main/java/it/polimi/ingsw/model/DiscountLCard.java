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
                    getResType() == other.getResType();
        }
    }

    @Override
    public void print() {
        System.out.println("Leader Card details" +
                "\n - Type : Discount Card "+
                "\n - Victory points: " + this.getVictoryPoints() +
                "\n - Required colors: " + this.color1 + " " + this.color2 +
                "\n - Discount resource : " + this.resType);
    }

    @Override
    public String toString() {
        return ("D"+"-" + this.getResType().toString());
    }
}

