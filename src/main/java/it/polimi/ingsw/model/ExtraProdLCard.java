package it.polimi.ingsw.model;

public class ExtraProdLCard extends LeaderCard {

    private final CardColor color;
    private final ResourceType input;

    public ExtraProdLCard(int type, int victoryPoints, int requirementType, CardColor color, ResourceType input) {
        super(type, victoryPoints, requirementType);
        this.color = color;
        this.input = input;
    }

    public CardColor getColor() {
        return color;
    }

    public ResourceType getInput() {
        return input;
    }
    public void useAbility()  {} //TODO
}
