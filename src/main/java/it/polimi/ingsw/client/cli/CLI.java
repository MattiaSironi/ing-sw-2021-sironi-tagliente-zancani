package it.polimi.ingsw.client.cli;


import it.polimi.ingsw.client.ClientActionController;
import it.polimi.ingsw.client.ModelMultiplayerView;
import it.polimi.ingsw.client.SocketServerConnection;
import it.polimi.ingsw.constants.Color;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;

import java.io.IOException;
import java.util.Scanner;

public class CLI {
    private final Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws IOException { //TODO PRINT TITLE
        System.out.println("╔╦╗╔═╗╔═╗╔╦╗╔═╗╦═╗  ╔═╗╔═╗  ╦═╗╔═╗╔╗╔╔═╗╦╔═╗╔═╗╔═╗╔╗╔╔═╗╔═╗");
        System.out.println("║║║╠═╣╚═╗ ║ ║╣ ╠╦╝  ║ ║╠╣   ╠╦╝║╣ ║║║╠═╣║╚═╗╚═╗╠═╣║║║║  ║╣ ");
        System.out.println("╩ ╩╩ ╩╚═╝ ╩ ╚═╝╩╚═  ╚═╝╚    ╩╚═╚═╝╝╚╝╩ ╩╩╚═╝╚═╝╩ ╩╝╚╝╚═╝╚═╝");
        System.out.println("Authors : Mattia Sironi (gigi), Simone Tagliente (Simo), Lea Zancani (Lea)");
        System.out.println("Choose MODE:\n0 -> LOCAL\n1 -> ONLINE");
        System.out.print("> ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.equals("0")) {
            startSinglePlayer();
        } else {
            startMultiPlayer();
        }

    }

    public static void startMultiPlayer() throws IOException {
        CLI cli = new CLI();
        ModelMultiplayerView view = new ModelMultiplayerView(new Game(false, -1));
        SocketServerConnection ssc = new SocketServerConnection();
        ClientActionController cms = new ClientActionController(cli, view, ssc, false, false);
        ssc.setUI(cms);
        cms.setupMultiplayer();
    }


    public static void startSinglePlayer() {
        Game game = new Game(true, 0);
        game.setNumPlayer(1);
        Controller controller = new Controller(game);
        CLI cli = new CLI();
        ModelMultiplayerView modelMultiplayerView = new ModelMultiplayerView(new Game(true, 0));
        ClientActionController clientActionController = new ClientActionController(cli, modelMultiplayerView, null, true, true);
        clientActionController.addObserver(controller);
        game.addObserver(clientActionController);
        clientActionController.nicknameSetUp();

    }

    public synchronized String readFromInput() {
        System.out.print("> ");
        return in.nextLine();
    }

    public synchronized void printToConsole(String message) {
        System.out.println(message);
    }

    public synchronized void printErrorToConsole(String message) {
        System.out.println(Color.ANSI_RED + message + Color.ANSI_RESET);
    }

}