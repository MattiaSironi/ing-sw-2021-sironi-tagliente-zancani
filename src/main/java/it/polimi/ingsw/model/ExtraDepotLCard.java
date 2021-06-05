package it.polimi.ingsw.model;

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
        System.out.println("Leader Card details" +
                "\n - Type : Extra Depot Card "+
                "\n - Victory points: " + this.getVictoryPoints() +
                "\n - Required resource: " + this.resType+
                "\n - Extra Depot resource : " + this.resDepot);
    }

    @Override
    public String toString() {
        return ("ED"+ "-" + this.getResDepot());
    }
}

