package it.polimi.ingsw.model;

/**
 * Class LeaderCard groups the attributes common to all the Leader Cards
 *
 * @author Mattia Sironi
 */
//Modifiche al Model UML: attributo requirements type non è più un boolean ma è un int, perché ho 4 sottoclassi
public class LeaderCard {
    private final int type;
    private final int victoryPoints;


    public LeaderCard(int type, int victoryPoints) {
        this.type = type;
        this.victoryPoints = victoryPoints;

    }


    public int getType() {
        return type;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

}
