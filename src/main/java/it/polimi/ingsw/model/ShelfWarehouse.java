package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * Class ShelfWarehouse groups all the shelves of the player
 *
 * @author Lea Zancani
 * @see Shelf
 */

public class ShelfWarehouse implements Printable {
    private ArrayList<Shelf> shelves;

    public ShelfWarehouse() {
        ArrayList<Shelf> s = new ArrayList<Shelf>();
        s.add(new Shelf(ResourceType.EMPTY, 0));
        s.add(new Shelf(ResourceType.EMPTY, 0));
        s.add(new Shelf(ResourceType.EMPTY, 0));
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
        return num;
    }

    /**
     * Method swapShelves allows the player to manage the resources by swapping the shelves
     *
     * @param s1 is the first shelf that the player wants to swap
     * @param s2 is the second shelf that the player wants to swap
     */
    public void swapShelves(int s1, int s2) {
        Shelf temp;
        if (this.shelves.get(s1).getCount() <= s2 + 1 && this.shelves.get(s2).getCount() <= s1 + 1) {
            temp = this.shelves.get(s1);
            this.shelves.set(s1, this.shelves.get(s2));
            this.shelves.set(s2, temp);
        } else System.out.println("Error. Not a valid operation. Check the game rules!\n ");
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
    public String addResource(ResourceType r, int shelfNum) {
        boolean error = false;
        int i;
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
                return "ok";
            } else
                return "Error. The shelf you keep your " + r.printResourceColouredName() + " resources is full or it is used by another ResourceType";
        } else return "Error. There is another shelf taken by " + r.printResourceColouredName() + " resources.";
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

    public ArrayList<Shelf> getShelves() {
        return shelves;
    }

    public void setShelves(ArrayList<Shelf> shelves) {
        this.shelves = shelves;
    }

    @Override
    public void print() {

        for (Shelf s : this.shelves)  {
            if (s.getResType().equals(ResourceType.EMPTY)) System.out.println("Shelf " + (this.shelves.indexOf(s)+1) + " is empty." );
            else System.out.println("Shelf " + (this.shelves.indexOf(s)+1) + " contains " + s.getCount() + " " + s.getResType().printResourceColouredName());



        }




//        System.out.println("Shelf 1: Resource =  " + this.getShelves().get(0).getResType().toString() + " Count = " +
//                this.getShelves().get(0).getCount());
//        System.out.println("Shelf 2: Resource =  " + this.getShelves().get(1).getResType().toString() + " Count = " +
//                this.getShelves().get(1).getCount());
//        System.out.println("Shelf 3: Resource =  " + this.getShelves().get(2).getResType().toString() + " Count = " +
//                this.getShelves().get(2).getCount());

    }
}
