package it.polimi.ingsw;
/**
 * Class MRMain is the entry point of our game.
 */

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.server.Server;

import java.io.IOException;
import java.util.Scanner;

public class MRMain {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Welcome to Master of Renaissance\n\n0 -> Run the server\n1 -> Run CLI (command line interface)\n2 -> Run GUI (graphics user interface)");
            Scanner in = new Scanner(System.in);
            switch (in.nextLine()) {
                case "0" -> Server.main(null);
                case "1" -> CLI.main(null);
                default -> GUI.main(null);
            }
        } else {
            switch (args[0]) {
                case "server" -> Server.main(null);
                case "CLI" -> CLI.main(null);
                case "GUI" -> GUI.main(null);
                default -> {
                    System.out.println("Invalid arg.");
                    System.exit(0);
                }
            }
        }
    }
}
