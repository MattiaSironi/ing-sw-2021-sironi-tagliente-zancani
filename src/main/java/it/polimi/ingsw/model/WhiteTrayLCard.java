package it.polimi.ingsw.model;

public class WhiteTrayLCard extends LeaderCard {
    private final ResourceType resType;
    private final CardColor x1Color;
    private final CardColor x2Color;

    public WhiteTrayLCard(int type, int victoryPoints, int requirementType, ResourceType resType, CardColor x1Color, CardColor x2Color) {
        super(type, victoryPoints, requirementType);
        this.resType = resType;
        this.x2Color = x2Color;
        this.x1Color = x1Color;

    }

    public ResourceType getResType() {
        return resType;
    }

    public CardColor getX1Color() {
        return x1Color;
    }

    public CardColor getX2Color() {
        return x2Color;
    }


}
