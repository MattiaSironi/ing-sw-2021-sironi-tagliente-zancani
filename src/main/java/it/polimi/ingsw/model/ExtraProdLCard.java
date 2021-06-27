package it.polimi.ingsw.model;

import it.polimi.ingsw.constants.Color;

import java.io.Serializable;

/**
 * Class ExtraProdLCard represents the model of the Leader Card with ExtraProduction Ability
 *
 * @author Mattia Sironi
 */

public class ExtraProdLCard extends LeaderCard implements Printable, Serializable {

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
            return getInput() == nc.getInput() && getColor() == nc.getColor() &&
                    getVictoryPoints() == nc.getVictoryPoints();

        }
    }

    /**
     * Method print prints to console a ExtraProdLCard instance.
     */

    @Override
    public void print() {
        String s = "\u2726";
        System.out.println( s+" "+s+" "+s+" "+s+" "+s+" "+s+" "+s+" "+s+" "+s+ Color.ANSI_PURPLE +
                " Extra Production Card "+Color.ANSI_RESET+ s+" "+s+" "+s+" "+s+" "+s+" "+s+" "+s+" "+s+" "+s+" "+
                "\nVictory points: " + this.getVictoryPoints() +
                "\nRequires: a "+ this.color+ " development card (level 1 or higher)" +
                "\nInput resource for production : " + this.input.printResourceColouredName()+
                "\nOutput : a resource of your choice + a faith point");
    }

    @Override
    public String toString() {
        return ("EP"+"-"+this.getInput().toString());
    }
}
