package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Class Tray represents the single tray placed in the market
 *
 * @author Simone Tagliente
 * @see Market
 */

public class Marble implements Serializable {
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
