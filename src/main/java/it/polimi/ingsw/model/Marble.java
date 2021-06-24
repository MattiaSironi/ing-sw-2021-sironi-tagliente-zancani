package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Class Marble represents the single tray placed in the market
 *
 * @author Simone Tagliente
 * @see Market
 */

public class Marble implements Serializable {
    private final ResourceType res;

    public Marble(ResourceType res) {
        this.res = res;
    }

    public ResourceType getRes() {
        return res;
    }

}
