package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class ShelfWarehouse groups all the shelves of the player
 *
 * @author Lea Zancani
 * @see Shelf
 */

public class ShelfWarehouse implements Printable, Serializable {
    private ArrayList<Shelf> shelves;
    private ResourceType bought;

    public ShelfWarehouse() {
        ArrayList<Shelf> s = new ArrayList<Shelf>();
        s.add(new Shelf(ResourceType.EMPTY, 0));
        s.add(new Shelf(ResourceType.EMPTY, 0));
        s.add(new Shelf(ResourceType.EMPTY, 0));
        s.add(new Shelf(null, 0));
        s.add(new Shelf(null, 0));
        this.shelves = s;
    }

    /**
     * Method getResCount return the quantity of a specific ResourceType
     *
     * @param r is the ResourceType that player wants to know the quantity of
     * @return the quantity of the ResourceType
     */
    public int getResCount(ResourceType r) {
        int i = 0;
        int num = 0;
        while (i < 3 && !this.shelves.get(i).getResType().equals(r))
            i++;
        if (i < 3)
            num = this.shelves.get(i).getCount();
        else if (this.shelves.get(3).getCount()>0 && this.getShelves().get(3).getResType()==r)
            num = this.shelves.get(3).getCount();
        else if (this.shelves.get(4).getCount()>0 && this.getShelves().get(4).getResType()==r)
            num = this.shelves.get(4).getCount();
        return num;
    }

    /**
     * Method swapShelves allows the player to manage the resources by swapping the shelves
     *
     * @param s1 is the first shelf that the player wants to swap
     * @param s2 is the second shelf that the player wants to swap
     */
    public boolean swapShelves(int s1, int s2) {
        Shelf temp;
        if (s1 == 3 || s1==4 || s2 == 3 || s2 == 4) return swapLeaderCase(s1, s2);
        if (this.shelves.get(s1).getCount() <= s2 + 1 && this.shelves.get(s2).getCount() <= s1 + 1) {
            temp = this.shelves.get(s1);
            this.shelves.set(s1, this.shelves.get(s2));
            this.shelves.set(s2, temp);
            return true;
        } else return false;



    }
    public ShelfWarehouse clone()  {
       ShelfWarehouse temp = new ShelfWarehouse();

       temp.shelves.set(0, new Shelf(this.shelves.get(0).getResType(), this.shelves.get(0).getCount()));
       temp.shelves.set(1, new Shelf(this.shelves.get(1).getResType(), this.shelves.get(1).getCount()));
       temp.shelves.set(2, new Shelf(this.shelves.get(2).getResType(), this.shelves.get(2).getCount()));
       temp.shelves.set(3, new Shelf(this.shelves.get(3).getResType(), this.shelves.get(3).getCount()));
       temp.shelves.set(4, new Shelf(this.shelves.get(4).getResType(), this.shelves.get(4).getCount()));
       return  temp;

    }

    public boolean swap(int s1, int s2) {


        if (s1 == 3 || s1 == 4) {

            if (this.shelves.get(s1).getResType() == this.shelves.get(s2).getResType() || this.shelves.get(s2).getResType() == ResourceType.EMPTY) {
                Shelf temp = this.shelves.get(s1);
                this.shelves.set(s1, this.shelves.get(s2));

                this.shelves.get(s1).setResType(temp.getResType());
                this.shelves.set(s2, temp);
                if (this.shelves.get(s2).getCount() == 0) this.shelves.get(s2).setResType(ResourceType.EMPTY);
            } else return false;
        } else if (s2 == 3 || s2 == 4) {

            if (this.shelves.get(s1).getResType() == this.shelves.get(s2).getResType() || this.shelves.get(s1).getResType() == ResourceType.EMPTY) {
                Shelf temp = this.shelves.get(s1);
                this.shelves.set(s1, this.shelves.get(s2));


                this.shelves.set(s2, temp);
                this.shelves.get(s2).setResType(this.shelves.get(s1).getResType());
                if (this.shelves.get(s1).getCount() == 0) {
                    this.shelves.get(s1).setResType(ResourceType.EMPTY);

                }
            } else return false;

        } else {
            Shelf temp = this.shelves.get(s1);
            this.shelves.set(s1, this.shelves.get(s2));

            this.shelves.set(s2, temp);
        }
        return checkRules();

    }

    public boolean checkRules(){

        return this.shelves.get(0).getCount() <= 1 &&
                this.shelves.get(1).getCount() <= 2 &&
                this.shelves.get(2).getCount() <= 3 &&
                this.shelves.get(3).getCount() <= 2 &&
                this.shelves.get(4).getCount() <= 2 &&
                this.shelves.stream().filter(x -> x.getResType() == (this.shelves.get(3).getResType())).count() <= 2 &&
                this.shelves.stream().filter(x -> x.getResType() == (this.shelves.get(4).getResType())).count() <= 2 &&
                ((this.shelves.get(3).getResType() != this.shelves.get(4).getResType() ||
                        (this.shelves.get(3).getResType()==  null && this.shelves.get(4).getResType()==  null)));




    }


    private boolean swapLeaderCase(int s1, int s2) {  //SEMPLIFICARE CONTROLLI E SISTEMARE PROBLEMA PIU' DI DUE SHELF CON STESSA RISORSA.
        if ((this.shelves.get(s1).getResType() == this.shelves.get(s2).getResType() ||
        this.shelves.get(s1).getResType() == ResourceType.EMPTY ||
            this.shelves.get(s2).getResType() == ResourceType.EMPTY) &&
        this.shelves.get(s1).getResType() != null && this.shelves.get(s2).getResType() != null ) {

            if ((s1==0  && this.shelves.get(s2).getCount()>=2) || (s2==0 && this.shelves.get(s1).getCount()>=2)) return false;
            else if ((s1 == 2 && this.shelves.get(s1).getCount() > 2) || (s2==2 && this.shelves.get(s2).getCount() > 2 )) return false;
            else { Shelf temp;
                if (s1 == 3 || s1== 4)  {

                    temp = this.shelves.get(s1);
                    this.shelves.set(s1, this.shelves.get(s2));
                    this.shelves.get(s1).setResType(temp.getResType());
                    this.shelves.set(s2, temp);
                    if (this.shelves.get(s2).getCount()==0) this.shelves.get(s2).setResType(ResourceType.EMPTY);
                }


                else {

                    temp = this.shelves.get(s1);
                    this.shelves.set(s1, this.shelves.get(s2));
                    this.shelves.set(s2, temp);
                this.shelves.get(s2).setResType(this.shelves.get(s1).getResType());
                    if (this.shelves.get(s1).getCount() == 0) {
                        this.shelves.get(s1).setResType(ResourceType.EMPTY);

                    }
                }


            }
            return true;
        }
        else return false;

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

    /**
     * Method addResource adds one resource in the selected shelf
     *
     * @param r        is the resource that the player wants to add
     * @param shelfNum is the shelf selected by the player
     */
    public boolean addResource(ResourceType r, int shelfNum) {
        boolean error = false;
        int i;
        if (shelfNum == 3 || shelfNum == 4)  return AddLeaderCase(r, shelfNum);
        else {
            for (i = 0; i < 3; i++) {
                if (i != shelfNum) {
                    if (this.shelves.get(i).getResType().equals(r))
                        error = true;
                }
            }
            if (!error) {
                if (this.shelves.get(shelfNum).getCount() < shelfNum + 1 && (this.shelves.get(shelfNum).getResType().equals(r) ||
                        this.shelves.get(shelfNum).getResType().equals(ResourceType.EMPTY))) {
                    if (this.shelves.get(shelfNum).getCount() == 0)
                        this.shelves.get(shelfNum).setResType(r);
                    this.shelves.get(shelfNum).setCount(this.shelves.get(shelfNum).getCount() + 1);
                    return true;
                } else
                    return false;
            } else return false;
        }
    }

    private boolean AddLeaderCase(ResourceType r, int shelfNum) {

        if (this.shelves.get(shelfNum).getResType() == r && this.shelves.get(shelfNum).getCount() < 2) {
            this.shelves.get(shelfNum).setCount(this.shelves.get(shelfNum).getCount() + 1);
            return true;
        } else
            return false;
        }


    /**
     * Method pay subtracts the quantity of a specific resource that the player has to pay
     *
     * @param q is the quantity that the player has to pay
     * @param r is the ResourceType that the player has to pay
     */
    public void pay(int q, ResourceType r) {
        int i = 0;
        while (i < 3 && !this.shelves.get(i).getResType().equals(r))
            i++;
        if (i < 3) {
            this.shelves.get(i).setCount(this.shelves.get(i).getCount() - q);
            if (this.shelves.get(i).getCount() == 0) {
                this.shelves.set(i, new Shelf(ResourceType.EMPTY, 0));
            }
        } else System.out.println("Error. You don't have this ResourceType\n");

    }

    public void payFromFirstExtraShelf(int q){
        this.shelves.get(4).setCount(this.shelves.get(4).getCount() - 1);
    }

    public void payFromSecondExtraShelf(int q){
        this.shelves.get(5).setCount(this.shelves.get(5).getCount() - 1);
    }

    public ArrayList<Shelf> getShelves() {
        return shelves;
    }

    public void setShelves(ArrayList<Shelf> shelves) {
        this.shelves = shelves;
    }

    @Override
    public void print() {

        for (Shelf s : this.shelves)  {
             if (shelves.indexOf(s) <=2) {
                 if (s.getResType().equals(ResourceType.EMPTY))
                     System.out.println("Shelf " + (this.shelves.indexOf(s) + 1) + " is empty.");
                 else
                     System.out.println("Shelf " + (this.shelves.indexOf(s) + 1) + " contains " + s.getCount() + " " + s.getResType().printResourceColouredName() + "(s)");
             }
             else if(s.getResType()==null) System.out.println("Special shelf "  + (this.shelves.indexOf(s) + 1) + " is not available.");
             else System.out.println("Special Shelf " + (this.shelves.indexOf(s) + 1) + " contains " + s.getCount() + " " + s.getResType().printResourceColouredName() + "(s)");
        }
    }


    public boolean canIPay(int i, ResourceType r){
        if(getResCount(r) < i) return false;
        else return true;
    }

    public int numberOfResources()  {
        return  this.shelves.stream().mapToInt(x -> x.getCount()).sum();
    }

    public ResourceType getBought() {
        return bought;
    }

    public void setBought(ResourceType bought) {
        this.bought = bought;
    }
}
