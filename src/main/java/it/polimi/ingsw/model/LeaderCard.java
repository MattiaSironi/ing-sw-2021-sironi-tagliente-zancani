package it.polimi.ingsw.model;
//Moficiche al Model UML: attributo requirements tpe non è più un boolean ma è un int, perché ho 4 sottoclassi
public class LeaderCard extends Card {

    private final int requirementType;

    public LeaderCard(int type, int victoryPoints, int requirementType) {
        super(type, victoryPoints);
        this.requirementType = requirementType;
    }

    public int getRequirementType() {
        return requirementType;
    }
    public void useAbility()  {} //TODO
}
