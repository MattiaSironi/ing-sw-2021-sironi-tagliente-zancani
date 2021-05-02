package it.polimi.ingsw.model;

import it.polimi.ingsw.constants.Color;

/**
 * Enum ResourceType represents the type of a resource used in the game in order to buy cards
 *
 * @author Mattia Sironi
 */

public enum ResourceType {
    COIN,
    STONE,
    SERVANT,
    SHIELD,
    FAITH_POINT,
    EMPTY;



    public String getColor() {
        switch (this)  {
            case COIN: return Color.ANSI_YELLOW;
            case SERVANT: return  Color.ANSI_PURPLE;
            case SHIELD: return Color.ANSI_BLUE;
            case FAITH_POINT: return Color.ANSI_RED;
            case STONE: return Color.ANSI_GREY;
            default: return Color.ANSI_RESET;
        }
    }



    public String printResourceColouredName()  {
//        return (getColor() + "  .,aa,.\n" +
//                " 88P\"\"Y8P'\n" +
//                "88\"P'Y8P8B'\n" +
//                " 88P\"\"Y8P'\n" +
//                "  .,aa,." + Color.ANSI_RESET);

        return (getColor() + this.toString() + Color.ANSI_RESET);
    }
}
