package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Class ExtraDepotLCard represents the model of the Leader Card with WhiteTray Ability
 *
 * @author Mattia Sironi
 */

public class WhiteTrayLCard extends LeaderCard implements Printable, Serializable {
    //type=4?
    private final ResourceType resType;
    private final CardColor x1Color;
    private final CardColor x2Color;

    public WhiteTrayLCard(int type, int victoryPoints, ResourceType resType, CardColor x1Color, CardColor x2Color) {
        super(type, victoryPoints);
        this.resType = resType;
        this.x2Color = x2Color;
        this.x1Color = x1Color;

    }

    public ResourceType getResType() {
        return resType;
    }

    public CardColor getX1Color() {
        return x1Color;
    }

    public CardColor getX2Color() {
        return x2Color;
    }


    @Override
    public boolean equals(LeaderCard lc) {
        if(!(lc instanceof WhiteTrayLCard))
            return false;
        else{
            WhiteTrayLCard c = (WhiteTrayLCard)lc;
            if(!(getResType()==c.getResType()&&
                    getX1Color()==c.getX1Color()&&
                    getX2Color()==c.getX2Color()
            ))
                return false;
            else
                return true;
        }
    }



    @Override
    public void print() {
        System.out.println("Leader Card details" +
                "\n - Type : White Marble Card "+
                "\n - Victory points: " + this.getVictoryPoints() +
                "\n - Required colors: " + this.x1Color + " " + this.x2Color + " " + this.x2Color +
                "\n - White marble conversion resource : " + this.resType);
    }
}
