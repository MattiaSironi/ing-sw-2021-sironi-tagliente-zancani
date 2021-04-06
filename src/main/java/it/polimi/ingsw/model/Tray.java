package it.polimi.ingsw.model;

/**
 * Class Tray represents the single tray placed in the market
 *
 * @author Simone Tagliente
 * @see Market
 */

public class Tray {
    private ResourceType res;

    public Tray(ResourceType res) {
        this.res = res;
    }

    public ResourceType getRes() {
        return res;
    }

    public void setRes(ResourceType res) {
        this.res = res;
    }
}
