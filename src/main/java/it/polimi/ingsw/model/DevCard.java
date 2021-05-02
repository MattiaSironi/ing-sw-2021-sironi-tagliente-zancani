package it.polimi.ingsw.model;

// MODIFICHE DAL MODEL UML: HO USATO 3 INT[] E NON 6 ATTRIBUTI. METODO USEABILITY ADESSO RESTITUISCE
// LE QUANTITA' DELLE RISORSE E NON UN ARRAYLIST<RESOURCE>.

/**
 * Class DevCard represents the model of a single Development Card and all its attributes
 *
 * @author Mattia Sironi
 */

public class DevCard implements Printable {
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
                        "\n - Cost: " + this.costRes[0] + "Coins, " + this.costRes[1] + "Stones, " + this.costRes[2] + "Servants, " + this.costRes[3] + "Shields" +
                        "\n - Input Resources: " + this.inputRes[0] + "Coins, " + this.inputRes[1] + "Stones, " + this.inputRes[2] + "Servants, " + this.inputRes[3] + "Shields" +
                        "\n - Output Resources: " + this.outputRes[0] + "Coins, " + this.outputRes[1] + "Stones, " + this.outputRes[2] + "Servants, " + this.outputRes[3] + "Shields" + this.outputRes[4] + "Faith Points");
    }

//    public int[] useAbility()  {
//        return getOutputRes();
//    } //production
}
