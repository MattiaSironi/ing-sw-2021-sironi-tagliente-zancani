package it.polimi.ingsw.model;
// modifiche al model uml: attributi in comune tra DevCard e LeaderCard vanno in Card
public class Card {
    private final int type;
    private final int victoryPoints;

    public Card(int type, int victoryPoints) {
        this.type = type;
        this.victoryPoints = victoryPoints;
    }

    public int getType() {
        return type;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    private void useAbility()  {} //not implemented here
}
