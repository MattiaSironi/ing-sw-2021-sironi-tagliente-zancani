package it.polimi.ingsw.model;

// MODIFICHE DAL MODEL UML: HO USATO 3 INT[] E NON 6 ATTRIBUTI. METODO USEABILITY ADESSO RESTITUISCE
// LE QUANTITA' DELLE RISORSE E NON UN ARRAYLIST<RESOURCE>.

public class DevCard extends Card{
    private final int level;
    private final CardColor color;
    private final int[] costRes; //pos 0 -> Coin, pos 1 -> Stone, pos 2 -> Servant, pos 3 -> Shield
    private final int[] inputRes; //pos 0 -> Coin, pos 1 -> Stone, pos 2 -> Servant, pos 3 -> Shield
    private final int[] outputRes; //pos 0 -> Coin, pos 1 -> Stone, pos 2 -> Servant, pos 3 -> Shield

    public DevCard(int victoryPoints, CardColor color, int level, int type, int[] costRes, int[] inputRes, int[] outputRes) {
        super (type, victoryPoints);
        this.color = color;
        this.level = level;
        this.costRes = costRes;
        this.inputRes = inputRes;
        this.outputRes = outputRes;
    }

    public  void checkFlag(Card c)  { //TODO

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

    public int[] useAbility()  {
        return getOutputRes();
    }
}
