package it.polimi.ingsw.model;

/**
 * Class Tray represents the single tray placed in the market
 *
 * @author Simone Tagliente
 * @see Market
 */

public class Marble {
    private ResourceType res;

    public Marble(ResourceType res) {
        this.res = res;
    }

    public Marble() {

    }

    public ResourceType getRes() {
        return res;
    }

    public void setRes(ResourceType res) {
        this.res = res;
    }
}
