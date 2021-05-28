package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Class ExtraProdLCard represents the model of the Leader Card with ExtraProduction Ability
 *
 * @author Mattia Sironi
 */

public class ExtraProdLCard extends LeaderCard implements Printable, Serializable {
    //type=3?
    private final CardColor color;
    private final ResourceType input;

    public ExtraProdLCard(int type, int victoryPoints, CardColor color, ResourceType input) {
        super(type, victoryPoints);
        this.color = color;
        this.input = input;
    }


    public CardColor getColor() {
        return color;
    }

    public ResourceType getInput() {
        return input;
    }

    @Override
    public boolean equals(LeaderCard lc) {
        if(!(lc instanceof ExtraProdLCard))
            return false;
        else{
            ExtraProdLCard nc = (ExtraProdLCard)lc;
            return getInput() == nc.getInput() && getColor() == nc.getColor();

        }
    }


    @Override
    public void print() {
        System.out.println("Leader Card details" +
                "\n - Type : Extra production Card "+
                "\n - Victory points: " + this.getVictoryPoints() +
                "\n - Required color: " + this.color +
                "\n - Input resource for production : " + this.input);
    }
}
