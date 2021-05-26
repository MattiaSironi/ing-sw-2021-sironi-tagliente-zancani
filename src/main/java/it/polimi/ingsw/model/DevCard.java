package it.polimi.ingsw.model;

// MODIFICHE DAL MODEL UML: HO USATO 3 INT[] E NON 6 ATTRIBUTI. METODO USEABILITY ADESSO RESTITUISCE
// LE QUANTITA' DELLE RISORSE E NON UN ARRAYLIST<RESOURCE>.

import java.io.Serializable;

/**
 * Class DevCard represents the model of a single Development Card and all its attributes
 *
 * @author Mattia Sironi
 */

public class DevCard implements Printable, Serializable {
    private final int victoryPoints;
    private final int level;
    private final CardColor color;
    private final int[] costRes; //pos 0 -> Coin, pos 1 -> Stone, pos 2 -> Servant, pos 3 -> Shield
    private final int[] inputRes; //pos 0 -> Coin, pos 1 -> Stone, pos 2 -> Servant, pos 3 -> Shield
    private final int[] outputRes; //pos 0 -> Coin, pos 1 -> Stone, pos 2 -> Servant, pos 3 -> Shield, pos 4 -> faith point!!!

    public DevCard(int victoryPoints, int level, CardColor color, int[] costRes, int[] inputRes, int[] outputRes) {
        this.victoryPoints = victoryPoints;
        this.level = level;
        this.color = color;
        this.costRes = costRes;
        this.inputRes = inputRes;
        this.outputRes = outputRes;
    }


    public int getVictoryPoints() {
        return victoryPoints;
    }

    public CardColor getColor() {
        return color;
    }

    public int getLevel() {
        return level;
    }

    public int[] getCostRes() {
        return costRes;
    }

    public int[] getInputRes() {
        return inputRes;
    }

    public int[] getOutputRes() {
        return outputRes;
    }

    @Override
    public void print(){
        System.out.println("Card details" +
                        "\n - Level: " + this.level +
                        "\n - Color: " + this.color +
                        "\n - Cost: " + this.costRes[0] + " " + ResourceType.COIN.printResourceColouredName() + " " +  this.costRes[1] + " " + ResourceType.STONE.printResourceColouredName()  + " " +  this.costRes[2] + " " + ResourceType.SERVANT.printResourceColouredName()  + " " + this.costRes[3] + " " + ResourceType.SHIELD.printResourceColouredName() +
                        "\n - Input Resources: " + this.inputRes[0] + " " + ResourceType.COIN.printResourceColouredName()  + " " +  this.inputRes[1] + " " + ResourceType.STONE.printResourceColouredName()  + " " +  this.inputRes[2] + " " + ResourceType.SERVANT.printResourceColouredName()  + " " + this.inputRes[3] + " " + ResourceType.SHIELD.printResourceColouredName() +
                        "\n - Output Resources: " + this.outputRes[0] + " " + ResourceType.COIN.printResourceColouredName()  + " " +  this.outputRes[1] + " " + ResourceType.STONE.printResourceColouredName()  + " " + this.outputRes[2] + " " + ResourceType.SERVANT.printResourceColouredName()  + " " + this.outputRes[3] + " " + ResourceType.SHIELD.printResourceColouredName() + " " + this.outputRes[4] + " " + ResourceType.FAITH_POINT.printResourceColouredName());
    }

//    public int[] useAbility()  {
//        return getOutputRes();
//    } //production

}
