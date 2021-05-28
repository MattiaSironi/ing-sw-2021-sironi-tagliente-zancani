package it.polimi.ingsw.model;

import it.polimi.ingsw.constants.Color;

import java.io.Serializable;

/**
 * Enum ResourceType represents the type of a resource used in the game in order to buy cards
 *
 * @author Mattia Sironi
 */

public enum ResourceType implements Serializable {
    COIN,
    STONE,
    SERVANT,
    SHIELD,
    FAITH_POINT,
    EMPTY;



    public String getColor() {
        return switch (this) {
            case COIN -> Color.ANSI_YELLOW;
            case SERVANT -> Color.ANSI_PURPLE;
            case SHIELD -> Color.ANSI_BLUE;
            case FAITH_POINT -> Color.ANSI_RED;
            case STONE -> Color.ANSI_GREY;
            default -> Color.ANSI_RESET;
        };
    }



    public String printResourceColouredName()  { return (getColor() + this.toString() + Color.ANSI_RESET); }

//    public String printResourceColouredBall() {
//        return (getColor() + "▒░░ ░▓▓▓▓▓▒  ░░ \n ▒  ▓█▓▓▓▓▓██▒ \n ░█▓░   ░▒▒▓██ \n ░█▓      ░▒▒▓█▓ \n ▒█▒      ░▒▒▒██ \n ▒█▒░░░ ░░▒▒▒▒██ \n ▓█▒▒▒▒▒▒▒▒▒██▒ \n ░ ███▓▓▓▓▓███▓ \n ▒  ░▓▓████▓▒ \n  "
//        + Color.ANSI_RESET);
//    }



}
