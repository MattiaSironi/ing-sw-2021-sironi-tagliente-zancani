package it.polimi.ingsw.model;

import java.util.ArrayList;

public class ShelfWarehouse {
    private ArrayList<Shelf> shelves;

    public ShelfWarehouse(ArrayList<Shelf> shelves) {
        this.shelves = shelves;
    }

    public int getResCount (ResourceType r){
        int i = 0;
        while (!this.shelves.get(i).getResType().equals(r) && i<3)
            i++;
        if(i<3)
            i = this.shelves.get(i).getCount();
        return i;
    }

    public void swapShelves(int s1, int s2){
        Shelf temp;

        if(this.shelves.get(s1).getCount()<=s2+1 && this.shelves.get(s2).getCount()<=s1+1 )
        {
            temp = this.shelves.get(s1);
            this.shelves.set(s1, this.shelves.get(s2));
            this.shelves.set(s2, temp);
        }
    }

    public boolean checkRules(){
        if (this.shelves.get(0).getCount()>1 || this.shelves.get(1).getCount()>2 || this.shelves.get(2).getCount()>3)
            return false;
        if (this.shelves.get(0).getResType()==this.shelves.get(1).getResType() ||
                this.shelves.get(0).getResType()==this.shelves.get(2).getResType() ||
                this.shelves.get(1).getResType()==this.shelves.get(2).getResType())
            return false;
        else
            return true;
     }

     public void pay (int quantity,ResourceType r){
        int i=0;
        while (!this.shelves.get(i).getResType().equals(r) && i<3)
             i++;
         if(i<3)
         {
             this.shelves.get(i).setCount(this.shelves.get(i).getCount()-quantity);
         }

     }
}
