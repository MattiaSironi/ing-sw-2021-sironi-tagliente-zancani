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
        switch (this)  {
            case COIN: return Color.ANSI_YELLOW;
            case SERVANT: return  Color.ANSI_PURPLE;
            case SHIELD: return Color.ANSI_BLUE;
            case FAITH_POINT: return Color.ANSI_RED;
            case STONE: return Color.ANSI_GREY;
            default: return Color.ANSI_RESET;
        }
    }



    public String printResourceColouredName()  { return (getColor() + this.toString() + Color.ANSI_RESET); }

//    public String printResourceColouredBall() {
//        return (getColor() + "▒░░ ░▓▓▓▓▓▒  ░░ \n ▒  ▓█▓▓▓▓▓██▒ \n ░█▓░   ░▒▒▓██ \n ░█▓      ░▒▒▓█▓ \n ▒█▒      ░▒▒▒██ \n ▒█▒░░░ ░░▒▒▒▒██ \n ▓█▒▒▒▒▒▒▒▒▒██▒ \n ░ ███▓▓▓▓▓███▓ \n ▒  ░▓▓████▓▒ \n  "
//        + Color.ANSI_RESET);
//    }

//    public String printResourceColouredBall() {
//        return (getColor() + "i    rBQBMi    \n" +
//                             "i  uQgrvXBQBi  \n" +
//                             "  BQ      iQBQ \n" +
//                             " BZ        ivBD\n" +
//                             "iQi       iiiQB\n" +
//                             " Bi      iiiiBQ\n" +
//                             " QBii i i iiBQ \n" +
//                             "  QBQMirvQQBQr \n" +
//                             "i   XQBQBQBi   "
//        + Color.ANSI_RESET);
//    }


}
