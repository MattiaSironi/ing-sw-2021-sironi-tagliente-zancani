package it.polimi.ingsw.model;

public class ExtraDepotLCard extends LeaderCard {
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


}
