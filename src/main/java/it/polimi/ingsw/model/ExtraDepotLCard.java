package it.polimi.ingsw.model;

import it.polimi.ingsw.constants.Color;

import java.io.Serializable;

/**
 * Class ExtraDepotLCard represents the model of the Leader Card with ExtraDepot Ability
 *
 * @author Mattia Sironi
 */

public class ExtraDepotLCard extends LeaderCard implements Printable, Serializable {
    //type=2?
    private final ResourceType resType;
    private final ResourceType resDepot;

    public ExtraDepotLCard(int type, int victoryPoints, ResourceType resType, ResourceType resDepot) {
        super(type, victoryPoints);
        this.resType = resType;
        this.resDepot = resDepot;
    }

    public ResourceType getResType() {
        return resType;
    }

    public ResourceType getResDepot() {
        return resDepot;
    }

    @Override
    public boolean equals(LeaderCard lc) {
        if(!(lc instanceof ExtraDepotLCard))
            return false;
        else{
            ExtraDepotLCard c = (ExtraDepotLCard)lc;
            return this.getResDepot() == c.getResDepot() &&
                    this.getResType() == c.getResType() &&
                    this.getVictoryPoints() == c.getVictoryPoints();
        }
    }



    @Override
    public void print() {
        String s = "\u2726";
        System.out.println(
              s+" "+s+" "+s+" "+s+" "+s+" "+s+" "+s+" "+s+" "+s+ Color.ANSI_BLUE +
                        " Extra Depot Card "+Color.ANSI_RESET+ s+" "+s+" "+s+" "+s+" "+s+" "+s+" "+s+" "+s+" "+s+" "+
                "\nVictory points: " + this.getVictoryPoints() +
                "\nRequires : 5 " + this.resType.printResourceColouredName()+
                "\nExtra Depot resource : " + this.resDepot.printResourceColouredName());
    }

    @Override
    public String toString() {
        return ("ED"+ "-" + this.getResDepot());
    }
}

