package it.polimi.ingsw.model;

// MODIFICHE DAL MODEL UML: HO USATO 3 INT[] E NON 6 ATTRIBUTI. METODO USEABILITY ADESSO RESTITUISCE
// LE QUANTITA' DELLE RISORSE E NON UN ARRAYLIST<RESOURCE>.

public class DevCard{
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

//    public int[] useAbility()  {
//        return getOutputRes();
//    } //production
}
