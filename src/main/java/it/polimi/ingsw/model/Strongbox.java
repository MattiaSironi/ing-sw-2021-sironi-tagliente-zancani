package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * Class Strongbox represents the Strongbox of the player where he can store any amount of any resource obtained using productions
 *
 * @author Simone Tagliente
 */

public class Strongbox {
//    private int coinNum;
//    private int stoneNum;
//    private int servantNum;
//    private int shieldNum;

    private ArrayList<Shelf> infinityShelf;
    private int earnedCoin;
    private int earnedStone;
    private int earnedServant;
    private int earnedShield;

    public Strongbox() {
//        this.coinNum = coinNum;
//        this.stoneNum = stoneNum;
//        this.servantNum = servantNum;
//        this.shieldNum = shieldNum;
        this.infinityShelf = new ArrayList<Shelf>();
        this.infinityShelf.add(0, new Shelf(ResourceType.COIN, 0));
        this.infinityShelf.add(1, new Shelf(ResourceType.STONE,0));
        this.infinityShelf.add(2, new Shelf(ResourceType.SERVANT,0));
        this.infinityShelf.add(3,new Shelf(ResourceType.SHIELD,0));

    }

    public ArrayList<Shelf> getInfinityShelf() {
        return infinityShelf;
    }

    public void setInfinityShelf(ArrayList<Shelf> infinityShelf) {
        this.infinityShelf = infinityShelf;
    }

    /*  public int getCoinNum() {
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
    }*/

  /*  public boolean CanIPay(DevCard card){ //-----------------< ho dovuto cambiare il parametro interno
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
    }*/

    /*public boolean canIPay (DevCard c){
        int i ;
        boolean ok = true;
        int[] costAmount = c.getCostRes();
        for (i = 0; i<4 ; i++){
           if(costAmount[i]>this.infinityShelf.get(i).getCount())
                    ok = false;
        }
        return ok;
    }*/

    /*public void Pay(ResourceType res, int quantity){

        if(res.equals(ResourceType.COIN))
            this.coinNum -= quantity;
        else if(res.equals(ResourceType.STONE))
            this.stoneNum -= quantity;
        else if(res.equals(ResourceType.SERVANT))
            this.servantNum -= quantity;
        else if(res.equals(ResourceType.SHIELD))
            this.shieldNum -= quantity;
    }*/

    /**
     * Method pay subtracts the quantity of a specific resource that the player has to pay
     * @param q is the quantity that the player has to pay
     * @param r is the ResourceType that the player has to pay
     */
    public void pay (int q, ResourceType r){
        int i = 0;
        while (!this.infinityShelf.get(i).getResType().equals(r))
            i++;
        this.infinityShelf.get(i).setCount(this.infinityShelf.get(i).getCount()-q);
    }
    /**
     * Method getResCount return the quantity of a specific ResourceType
     * @param r is the ResourceType that player wants to know the quantity of
     * @return the quantity of the ResourceType
     */
    public int getResCount(ResourceType r){
        int i = 0;
        while (!this.infinityShelf.get(i).getResType().equals(r))
            i++;
        return this.infinityShelf.get(i).getCount();
    }

    public void addResource(ResourceType r, int q){
        int index;
        int temp;
        if (r.equals(ResourceType.COIN)) index = 0;
        else if (r.equals(ResourceType.STONE)) index = 1;
        else if (r.equals(ResourceType.SERVANT)) index = 2;
        else index = 3;
        temp = this.infinityShelf.get(index).getCount();
        this.infinityShelf.get(index).setCount(temp + q);
    }

}
