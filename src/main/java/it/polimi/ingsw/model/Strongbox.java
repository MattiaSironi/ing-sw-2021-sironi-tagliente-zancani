package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class Strongbox represents the Strongbox of the player where he can store any amount of any resource obtained using productions
 *
 * @author Simone Tagliente
 */

public class Strongbox implements Printable, Serializable {

    private ArrayList<Shelf> infinityShelf;
    private int earnedCoin;
    private int earnedStone;
    private int earnedServant;
    private int earnedShield;

    public Strongbox() {
        this.infinityShelf = new ArrayList<>();
        this.infinityShelf.add(0, new Shelf(ResourceType.COIN, 0));
        this.infinityShelf.add(1, new Shelf(ResourceType.STONE, 0));
        this.infinityShelf.add(2, new Shelf(ResourceType.SERVANT, 0));
        this.infinityShelf.add(3, new Shelf(ResourceType.SHIELD, 0));
        this.earnedCoin = 0;
        this.earnedServant = 0;
        this.earnedShield = 0;
        this.earnedStone = 0;
    }

    public ArrayList<Shelf> getInfinityShelf() {
        return infinityShelf;
    }

    public void setInfinityShelf(ArrayList<Shelf> infinityShelf) {
        this.infinityShelf = infinityShelf;
    }

    public int numberOfResources()  {
        return  this.infinityShelf.stream().mapToInt(Shelf::getCount).sum();
    }

    public int getEarnedCoin() {
        return earnedCoin;
    }

    public void setEarnedCoin(int earnedCoin) {
        this.earnedCoin = earnedCoin;
    }

    public int getEarnedStone() {
        return earnedStone;
    }

    public void setEarnedStone(int earnedStone) {
        this.earnedStone = earnedStone;
    }

    public int getEarnedServant() {
        return earnedServant;
    }

    public void setEarnedServant(int earnedServant) {
        this.earnedServant = earnedServant;
    }

    public int getEarnedShield() {
        return earnedShield;
    }

    public void setEarnedShield(int earnedShield) {
        this.earnedShield = earnedShield;
    }

    /**
     * Method pay subtracts the quantity of a specific resource that the player has to pay
     *
     * @param q is the quantity that the player has to pay
     * @param r is the ResourceType that the player has to pay
     */
    public void pay(int q, ResourceType r) {
        int i = 0;
        while (!this.infinityShelf.get(i).getResType().equals(r))
            i++;
        this.infinityShelf.get(i).setCount(this.infinityShelf.get(i).getCount() - q);
    }

    /**
     * Method getResCount return the quantity of a specific ResourceType
     *
     * @param r is the ResourceType that player wants to know the quantity of
     * @return the quantity of the ResourceType
     */
    public int getResCount(ResourceType r) {
        int i = 0;
        while (!this.infinityShelf.get(i).getResType().equals(r))
            i++;
        return this.infinityShelf.get(i).getCount();
    }

    public void addResource(ResourceType r, int q) {
        int index;
        int temp;
        if (r.equals(ResourceType.COIN)) index = 0;
        else if (r.equals(ResourceType.STONE)) index = 1;
        else if (r.equals(ResourceType.SERVANT)) index = 2;
        else index = 3;
        temp = this.infinityShelf.get(index).getCount();
        this.infinityShelf.get(index).setCount(temp + q);
    }

    public boolean canIPay(int i, ResourceType r) {
        return getResCount(r) >= i;
    }

    @Override
    public void print() {
        System.out.println("======================");
        System.out.println("STRONGBOX: ");
        for (Shelf s : this.getInfinityShelf()) {
                System.out.println(s.getCount() + " " + s.getResType().printResourceColouredName() + "(s)");
        }
        System.out.println("======================");
    }

    public Strongbox clone() {
        Strongbox temp = new Strongbox();
        temp.earnedCoin = this.earnedCoin;
        temp.earnedServant = this.earnedServant;
        temp.earnedStone = this.earnedStone;
        temp.earnedShield = this.earnedShield;
        temp.infinityShelf.set(0, new Shelf(this.infinityShelf.get(0).getResType(), this.infinityShelf.get(0).getCount()));
        temp.infinityShelf.set(1, new Shelf(this.infinityShelf.get(1).getResType(), this.infinityShelf.get(1).getCount()));
        temp.infinityShelf.set(2, new Shelf(this.infinityShelf.get(2).getResType(), this.infinityShelf.get(2).getCount()));
        temp.infinityShelf.set(3, new Shelf(this.infinityShelf.get(3).getResType(), this.infinityShelf.get(3).getCount()));
        return temp;
    }
}
