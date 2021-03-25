package it.polimi.ingsw.model;
import java.util.ArrayList;

public class Warehouse{
    private ArrayList<ResourceType> resArray;
    private boolean extraSchelf1;
    private ResourceType extraSchelf1ResType;
    private boolean extraSchelf2;
    private ResourceType extraSchelf2ResType;

    public Warehouse(ArrayList<ResourceType> resArray, boolean extraSchelf1, ResourceType extraSchelf1ResType, boolean extraSchelf2, ResourceType extraSchelf2ResType) {
        this.resArray = resArray;
        this.extraSchelf1 = extraSchelf1;
        this.extraSchelf1ResType = extraSchelf1ResType;
        this.extraSchelf2 = extraSchelf2;
        this.extraSchelf2ResType = extraSchelf2ResType;
    }

    public ArrayList<ResourceType> getResArray() {
        return resArray;
    }

    public void setResArray(ArrayList<ResourceType> resArray) {
        this.resArray = resArray;
    }

    public boolean isExtraSchelf1() {
        return extraSchelf1;
    }

    public void setExtraSchelf1(boolean extraSchelf1) {
        this.extraSchelf1 = extraSchelf1;
    }

    public ResourceType getExtraSchelf1ResType() {
        return extraSchelf1ResType;
    }

    public void setExtraSchelf1ResType(ResourceType extraSchelf1ResType) {
        this.extraSchelf1ResType = extraSchelf1ResType;
    }

    public boolean isExtraSchelf2() {
        return extraSchelf2;
    }

    public void setExtraSchelf2(boolean extraSchelf2) {
        this.extraSchelf2 = extraSchelf2;
    }

    public ResourceType getExtraSchelf2ResType() {
        return extraSchelf2ResType;
    }

    public void setExtraSchelf2ResType(ResourceType extraSchelf2ResType) {
        this.extraSchelf2ResType = extraSchelf2ResType;
    }


    public void manageRes(int pos1, int pos2){
        ResourceType tmp;
        if(pos1 >= 0 && pos1 <= 5 && pos2 >= 0 && pos2 <= 5 ) {
            ArrayList<ResourceType> resArrayClone = (ArrayList<ResourceType>) resArray.clone();
            tmp = resArrayClone.get(pos2);
            resArrayClone.set(pos2, resArrayClone.get(pos1));
            resArrayClone.set(pos1, tmp);
            if (checkRules(resArrayClone))
                this.setResArray(resArrayClone);
            else {
                //restituisce l'array non modificato o stampa errore o inserire eccezione
            }
        }
        else{
            //restituisce l'array non modificato o stampa errore o inserire eccezione
        }

    }
    private boolean checkRules(ArrayList<ResourceType> resArrayToCheck){

        int count;

        if(!resArrayToCheck.get(0).equals(ResourceType.EMPTY)) {
            for (count = 1; count < 6; count++) {
                if (resArrayToCheck.get(count).equals(resArrayToCheck.get(0)))
                    return false;
            }
        }
        if(!resArrayToCheck.get(1).equals(ResourceType.EMPTY)) {
            if (!(resArrayToCheck.get(2).equals(resArrayToCheck.get(1)) || resArrayToCheck.get(2).equals(ResourceType.EMPTY)))
                return false;
            for (count = 3; count < 6; count++) {
                if (resArrayToCheck.get(count).equals(resArrayToCheck.get(1)))
                    return false;
            }
        }
        if(!resArrayToCheck.get(3).equals(ResourceType.EMPTY)) {
            if(!((resArrayToCheck.get(4).equals(resArrayToCheck.get(3)) || resArrayToCheck.get(4).equals(ResourceType.EMPTY)) &&
                    ((resArrayToCheck.get(5).equals(resArrayToCheck.get(4)) || resArrayToCheck.equals(ResourceType.EMPTY))))){
                return false;
            }
        }

        return true;


        //check regole warehouse

    }

    public int size(ResourceType res){
        int tot = 0;
        for(ResourceType r : this.resArray){
            if(r.equals(res)){
                tot++;
            }
        }
        return tot;
    }



    public void Pay(ResourceType res, int quantity){
        if(this.resArray.get(0).equals(res)) {
            this.resArray.set(0, ResourceType.EMPTY);
        }
        else if(this.resArray.get(1).equals(res)) {
            for (int i = 1; i < 1 + quantity; i++) {
                this.resArray.set(i, ResourceType.EMPTY);
            }
        }
        else{
            for(int i = 3; i < 3 + quantity; i++){
                this.resArray.set(i, ResourceType.EMPTY);
            }
        }
    }
}
