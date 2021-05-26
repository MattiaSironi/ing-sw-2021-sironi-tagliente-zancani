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
    private final int type; //0?
    private final int victoryPoints;
    private final int level;
    private final CardColor color;
    private final int[] costRes; //pos 0 -> Coin, pos 1 -> Stone, pos 2 -> Servant, pos 3 -> Shield
    private final int[] inputRes; //pos 0 -> Coin, pos 1 -> Stone, pos 2 -> Servant, pos 3 -> Shield
    private final int[] outputRes; //pos 0 -> Coin, pos 1 -> Stone, pos 2 -> Servant, pos 3 -> Shield, pos 4 -> faith point!!!

    public DevCard(int type, int victoryPoints, int level, CardColor color, int[] costRes, int[] inputRes, int[] outputRes) {
        this.type = type;
        this.victoryPoints = victoryPoints;
        this.level = level;
        this.color = color;
        this.costRes = costRes;
        this.inputRes = inputRes;
        this.outputRes = outputRes;
    }



    public int getType() {
        return type;
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


    public DevCard clone()  {
        DevCard clone = new DevCard(this.type, this.victoryPoints, this.level, this.color
        , new int[4], new int[4], new int[5]);
        clone.costRes[0]=costRes[0];
        clone.costRes[1]=costRes[1];
        clone.costRes[2]=costRes[2];
        clone.costRes[3]=costRes[3];
        clone.inputRes[0]=inputRes[0];
        clone.inputRes[1]=inputRes[1];
        clone.inputRes[2]=inputRes[2];
        clone.inputRes[3]=inputRes[3];
        clone.outputRes[0]=outputRes[0];
        clone.outputRes[1]=outputRes[1];
        clone.outputRes[2]=outputRes[2];
        clone.outputRes[3]=outputRes[3];
        clone.outputRes[4]=outputRes[4];
        return clone;

    }
}
