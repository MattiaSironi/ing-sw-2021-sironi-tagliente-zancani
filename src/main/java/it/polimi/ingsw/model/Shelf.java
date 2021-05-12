package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Class Shelf represents the single shelf of the ShelfWarehouse. The player has a maximum of three shelves, one made by one slot, one made by two slots and the last with three slots,
 * excluding the ones you can obtain with the ExtraDepot Leader Card
 *
 * @author Lea Zancani
 * @see ShelfWarehouse
 */

public class Shelf implements Serializable {
    private ResourceType resType;
    private int count;


    public Shelf(ResourceType resType, int count) {
        this.resType = resType;
        this.count = count;
    }

    public ResourceType getResType() {
        return resType;
    }

    public int getCount() {
        return count;
    }

    public void setResType(ResourceType resType) {
        this.resType = resType;
    }

    public void setCount(int count) {
        this.count = count;
    }
}


