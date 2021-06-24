package it.polimi.ingsw.model;

import it.polimi.ingsw.constants.Color;

import java.io.Serializable;

/**
 * Class WhiteTrayLCard represents the model of the Leader Card with WhiteTray Ability
 *
 * @author Mattia Sironi
 */

public class WhiteTrayLCard extends LeaderCard implements Printable, Serializable {
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
            return getResType() == c.getResType() &&
                    getX1Color() == c.getX1Color() &&
                    getX2Color() == c.getX2Color() &&
                    getVictoryPoints() == c.getVictoryPoints();
        }
    }

    /**
     * Method print prints to console a WhiteTrayLCard instance.
     */

    @Override
    public void print() {
        String s = "\u2726";
        System.out.println( s+" "+s+" "+s+" "+s+" "+s+" "+s+" "+s+" "+s+" "+s+ Color.ANSI_RED +
                " White Marble Card "+Color.ANSI_RESET+ s+" "+s+" "+s+" "+s+" "+s+" "+s+" "+s+" "+s+" "+s+" "+
                "\nVictory points: " + this.getVictoryPoints() +
                "\nRequires: a "+ this.x2Color+ " development card (level 2 or higher)" +
                "\nRequires: a "+ this.x1Color+ " development card (level 1 or higher)" +
                "\nWhite marble conversion resource : " + this.resType.printResourceColouredName());
    }

    @Override
    public String toString() {
        return ("W" +  "-" + this.getResType().toString());
    }
}
