package it.polimi.ingsw.model;

/**
 * Class ExtraDepotLCard represents the model of the Leader Card with ExtraDepot Ability
 *
 * @author Mattia Sironi
 */

public class ExtraDepotLCard extends LeaderCard implements Printable{
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
    public void print() {
        System.out.println("Leader Card details" +
                "\n - Type : Extra Depot Card "+
                "\n - Victory points: " + this.getVictoryPoints() +
                "\n - Required resource: " + this.resType+
                "\n - Extra Depot resource : " + this.resDepot);
    }
}
