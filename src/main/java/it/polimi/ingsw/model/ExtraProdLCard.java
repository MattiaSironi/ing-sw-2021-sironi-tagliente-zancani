package it.polimi.ingsw.model;

public class ExtraProdLCard extends LeaderCard {
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

}
