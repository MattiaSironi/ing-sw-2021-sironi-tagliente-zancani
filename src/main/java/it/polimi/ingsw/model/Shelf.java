package it.polimi.ingsw.model;

public class Shelf {
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


