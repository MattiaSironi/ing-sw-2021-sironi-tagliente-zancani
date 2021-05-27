package it.polimi.ingsw;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.server.Server;

import java.io.IOException;
import java.util.Scanner;

public class MRMain {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Welcome to Master of Renaissance\n\n0 -> Run the server\n1 -> Run CLI (command line interface)\n2 -> Run GUI (graphics user interface)");
        Scanner in = new Scanner(System.in);
        switch(in.nextLine()){
            case "0" -> Server.main(null);
            case "1" -> CLI.main(null);
            default -> GUI.main(null);
        }
    }
}
