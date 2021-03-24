package it.polimi.ingsw.model;

public class Strongbox {
    private int coinNum;
    private int stoneNum;
    private int servantNum;
    private int shieldNum;

    public Strongbox(int coinNum, int stoneNum, int servantNum, int shieldNum) {
        this.coinNum = coinNum;
        this.stoneNum = stoneNum;
        this.servantNum = servantNum;
        this.shieldNum = shieldNum;
    }

    public int getCoinNum() {
        return coinNum;
    }

    public void setCoinNum(int coinNum) {
        this.coinNum = coinNum;
    }

    public int getStoneNum() {
        return stoneNum;
    }

    public void setStoneNum(int stoneNum) {
        this.stoneNum = stoneNum;
    }

    public int getServantNum() {
        return servantNum;
    }

    public void setServantNum(int servantNum) {
        this.servantNum = servantNum;
    }

    public int getShieldNum() {
        return shieldNum;
    }

    public void setShieldNum(int shieldNum) {
        this.shieldNum = shieldNum;
    }

    public boolean CanIPay(DevCard card){ //-----------------< ho dovuto cambiare il parametro interno
        int coinReq = 0;
        int stoneReq = 0;
        int servantReq = 0;
        int shieldReq = 0;
        int[] costAmount = card.getCostRes();

        coinReq = costAmount[0];
        stoneReq = costAmount[1];
        servantReq = costAmount[2];
        shieldReq = costAmount[3];

        if(coinNum < coinReq) return false;
        else if(stoneNum < stoneReq) return false;
        else if(servantNum < servantReq) return false;
        else if(shieldNum < shieldReq) return false;
        else return true;
    }

    public void Pay(ResourceType res, int quantity){

        if(res.equals(ResourceType.COIN))
            this.coinNum -= quantity;
        else if(res.equals(ResourceType.STONE))
            this.stoneNum -= quantity;
        else if(res.equals(ResourceType.SERVANT))
            this.servantNum -= quantity;
        else if(res.equals(ResourceType.SHIELD))
            this.shieldNum -= quantity;
    }
}
