package it.polimi.ingsw.model;

/**
 * Class DiscountLCard represents the model of the Leader Card with Discount Ability
 *
 * @author Mattia Sironi
 */

public class DiscountLCard extends LeaderCard implements Printable {
    //type=1?
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
    public void print() {
        System.out.println("Leader Card details" +
                "\n - Type : Discount Card "+
                "\n - Victory points: " + this.getVictoryPoints() +
                "\n - Required colors: " + this.color1 + " " + this.color2 +
                "\n - Discount resource : " + this.resType);
    }
}
