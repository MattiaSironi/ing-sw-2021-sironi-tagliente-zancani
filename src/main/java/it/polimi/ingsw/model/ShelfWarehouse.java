package it.polimi.ingsw.model;

import java.util.ArrayList;

public class ShelfWarehouse {
    private ArrayList<Shelf> shelves;

    public ShelfWarehouse(ArrayList<Shelf> shelves) {
        this.shelves = shelves;
    }

    public int getResCount (ResourceType r){
        int i = 0;
        int num = 0;
        while (!this.shelves.get(i).getResType().equals(r) && i<3)
            i++;
        if(i<3)
            num = this.shelves.get(i).getCount();
        return num;
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

  /*  public boolean checkRules(){
        if (this.shelves.get(0).getCount()>1 || this.shelves.get(1).getCount()>2 || this.shelves.get(2).getCount()>3)
            return false;
        if (this.shelves.get(0).getResType()==this.shelves.get(1).getResType() ||
                this.shelves.get(0).getResType()==this.shelves.get(2).getResType() ||
                this.shelves.get(1).getResType()==this.shelves.get(2).getResType())
            return false;
        else
            return true;
     }*/

     //il controller deve valutare se la c'è già una mensola con la risorsa da inserire o ci sono più possibilità e in quel caso chiedere all'utente
     public void addResource (ResourceType r, int shelfNum){
        boolean error = false;
        int i;
        for (i=0; i<3; i++)
        {
            if(i!=shelfNum)
            {
                if(this.shelves.get(i).getResType().equals(r))
                    error = true;
            }
        }
        if (!error)
        {
            if (this.shelves.get(shelfNum).getCount()<shelfNum+1)
            {
                if(this.shelves.get(shelfNum).getCount()==0)
                    this.shelves.get(shelfNum).setResType(r);
                this.shelves.get(shelfNum).setCount(this.shelves.get(shelfNum).getCount()+1);
            }
        }
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
