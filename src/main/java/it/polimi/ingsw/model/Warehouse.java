package it.polimi.ingsw.model;


public class Warehouse {
    private ResourceType res1;
    private ResourceType res2;
    private ResourceType res3;
    private int res1Num;
    private int res2Num;
    private int res3Num;
    private int extra1Size;
    private ResourceType extra1Res;
    private int extra1ResNum;
    private int extra2Size;
    private ResourceType extra2Res;
    private int extra2ResNum;

    public Warehouse(ResourceType res1, ResourceType res2, ResourceType res3, int res1Num, int res2Num, int res3Num, int extra1Size, ResourceType extra1Res, int extra1ResNum, int extra2Size, ResourceType extra2Res, int extra2ResNum) {
        this.res1 = res1;
        this.res2 = res2;
        this.res3 = res3;
        this.res1Num = res1Num;
        this.res2Num = res2Num;
        this.res3Num = res3Num;
        this.extra1Size = extra1Size;
        this.extra1Res = extra1Res;
        this.extra1ResNum = extra1ResNum;
        this.extra2Size = extra2Size;
        this.extra2Res = extra2Res;
        this.extra2ResNum = extra2ResNum;
    }

    public ResourceType getRes1() {
        return res1;
    }

    public void setRes1(ResourceType res1) {
        this.res1 = res1;
    }

    public ResourceType getRes2() {
        return res2;
    }

    public void setRes2(ResourceType res2) {
        this.res2 = res2;
    }

    public ResourceType getRes3() {
        return res3;
    }

    public void setRes3(ResourceType res3) {
        this.res3 = res3;
    }

    public int getRes1Num() {
        return res1Num;
    }

    public void setRes1Num(int res1Num) {
        this.res1Num = res1Num;
    }

    public int getRes2Num() {
        return res2Num;
    }

    public void setRes2Num(int res2Num) {
        this.res2Num = res2Num;
    }

    public int getRes3Num() {
        return res3Num;
    }

    public void setRes3Num(int res3Num) {
        this.res3Num = res3Num;
    }

    public int getExtra1Size() {
        return extra1Size;
    }

    public void setExtra1Size(int extra1Size) {
        this.extra1Size = extra1Size;
    }

    public ResourceType getExtra1Res() {
        return extra1Res;
    }

    public void setExtra1Res(ResourceType extra1Res) {
        this.extra1Res = extra1Res;
    }

    public int getExtra1ResNum() {
        return extra1ResNum;
    }

    public void setExtra1ResNum(int extra1ResNum) {
        this.extra1ResNum = extra1ResNum;
    }

    public int getExtra2Size() {
        return extra2Size;
    }

    public void setExtra2Size(int extra2Size) {
        this.extra2Size = extra2Size;
    }

    public ResourceType getExtra2Res() {
        return extra2Res;
    }

    public void setExtra2Res(ResourceType extra2Res) {
        this.extra2Res = extra2Res;
    }

    public int getExtra2ResNum() {
        return extra2ResNum;
    }

    public void setExtra2ResNum(int extra2ResNum) {
        this.extra2ResNum = extra2ResNum;
    }

    private boolean canIPay(DevCard card){
        int coinReq = 0;
        int stoneReq = 0;
        int servantReq = 0;
        int shieldReq = 0;
        int coinTot = 0;
        int stoneTot = 0;
        int servantTot = 0;
        int shieldTot = 0;
        int[] costAmount = card.getCostRes();

        //can add 124 125 126 127 as attribute of the main class

        coinReq = costAmount[0];
        stoneReq = costAmount[1];
        servantReq = costAmount[2];
        shieldReq = costAmount[3];

        ///////////////////////////////////////     calculates number of res in the main warehouse
        if(res1 == ResourceType.COIN){
            coinTot = res1Num;
        }
        else if(res2 == ResourceType.COIN){
            coinTot = res2Num;
        }
        else if(res3 == ResourceType.COIN){
            coinTot = res3Num;
        }
        if(res1 == ResourceType.STONE){
            stoneTot = res1Num;
        }
        else if(res2 == ResourceType.STONE){
            stoneTot = res2Num;
        }
        else if(res3 == ResourceType.STONE){
            stoneTot = res3Num;
        }
        if(res1 == ResourceType.SERVANT){
            servantTot = res1Num;
        }
        else if(res2 == ResourceType.SERVANT){
            servantTot = res2Num;
        }
        else if(res3 == ResourceType.SERVANT){
            servantTot = res3Num;
        }
        if(res1 == ResourceType.SHIELD){
            shieldTot = res1Num;
        }
        else if(res2 == ResourceType.SHIELD){
            shieldTot = res2Num;
        }
        else if(res3 == ResourceType.SHIELD){
            shieldTot = res3Num;
        }
        ///////////////////////////////////////     calculates number of res in the extra shelfs
        if(extra1Res == ResourceType.COIN){
            coinTot = coinTot + extra1ResNum;
        }
        else if(extra2Res == ResourceType.COIN){
            coinTot = coinTot + extra2ResNum;
        }
        if(extra1Res == ResourceType.STONE){
            stoneTot = stoneTot + extra1ResNum;
        }
        else if(extra2Res == ResourceType.STONE){
            stoneTot = stoneTot + extra2ResNum;
        }
        if(extra1Res == ResourceType.SERVANT){
            servantTot = servantTot + extra1ResNum;
        }
        else if(extra2Res == ResourceType.SERVANT){
            servantTot = servantTot + extra2ResNum;
        }
        if(extra1Res == ResourceType.SHIELD){
            shieldTot = shieldTot + extra1ResNum;
        }
        else if(extra2Res == ResourceType.SHIELD){
            shieldTot = shieldTot + extra2ResNum;
        }

        ////////////////////////////////////        compares the cost with res in the warehouse
        if(coinTot < coinReq) return false;
        else if(stoneTot < stoneReq) return false;
        else if(servantTot < stoneReq) return false;
        else if(shieldTot < shieldReq) return false;
        else return true;
    }
}
