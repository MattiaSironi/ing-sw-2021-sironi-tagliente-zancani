package it.polimi.ingsw.model;

public class ExtraDepotLCard extends LeaderCard {

    private final ResourceType resType;
    private final ResourceType resDepot;

    public ExtraDepotLCard(int type, int victoryPoints, int requirementType, ResourceType resType, ResourceType resDepot) {
        super(type, victoryPoints, requirementType);
        this.resType = resType;
        this.resDepot = resDepot;
    }

    public ResourceType getResType() {
        return resType;
    }

    public ResourceType getResDepot() {
        return resDepot;
    }

//    @Override
//    public void useAbility() { //TODO

    //}
}
