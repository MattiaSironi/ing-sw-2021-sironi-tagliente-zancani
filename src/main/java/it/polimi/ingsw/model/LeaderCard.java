package it.polimi.ingsw.model;
//Moficiche al Model UML: attributo requirements type non è più un boolean ma è un int, perché ho 4 sottoclassi
public class LeaderCard{
    private final int type;
    private final int victoryPoints;
    private final int requirementType;

    public LeaderCard(int type, int victoryPoints, int requirementType) {
        this.type = type;
        this.victoryPoints = victoryPoints;
        this.requirementType = requirementType;
    }

    public int getType() {
        return type;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public int getRequirementType() {
        return requirementType;
    }

}
