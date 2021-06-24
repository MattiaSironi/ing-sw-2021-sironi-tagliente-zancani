package it.polimi.ingsw.constants;

/** Enum Ball contains rows used to print Market's Marbles.
 * @see it.polimi.ingsw.model.Marble
 * @see it.polimi.ingsw.model.Market
 * @see it.polimi.ingsw.model.ResourceType
 * @author Mattia Sironi
 */
public enum Ball {

     Ball1("         ░▒▓▓▓▓▓▓▒         "),
     Ball2("      ▒▓███▓▓▓▓▓█▓▓▓▓░     "),
     Ball3("   ▒██████▓▓▓▓▓███▓████▓   "),
     Ball4("  ▒████▓▓▓▓▓▓▓▓░░▓▓▓▓▓███  "),
     Ball5(" ▓████▓▓▓▓▓▓▓▓▓ ░▓▓▓▓▓▓██▓ "),
     Ball6(" ████▓▓▓▓▓▓▓▓▓▓▓█▓▓▓▓▓▓▓██ "),
     Ball7(" ▒█████▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓██▓ "),
     Ball8("  ░██████▓█▓▓▓▓▓▓▓▓▓█████  "),
     Ball9("   ░▓███████████▓██████▓   "),
    Ball10("      ░▒▓▓████████▓▓▒      ");




    private final String ballLine;

    public String getBallLine() {
        return ballLine;
    }

    Ball(String ballLine) {
        this.ballLine = ballLine;
    }
}





