package it.polimi.ingsw.client.cli;


import it.polimi.ingsw.client.ClientActionController;
import it.polimi.ingsw.client.ModelMultiplayerView;
import it.polimi.ingsw.client.SocketServerConnection;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.message.ActionMessages.*;
import it.polimi.ingsw.message.CommonMessages.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

import java.io.IOException;
import java.util.Scanner;

public class CLI  {
    private Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Choose MODE:\n0->SINGLEPLAYER\n1->MULTIPLAYER");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if(input.equals("0")){
            startSinglePlayer();
        }
        else{
            startMultiPlayer();
        }

    }
    /*--------- INITIAL PHASE MULTIPLAYER MATCH ------------*/
    public static void startMultiPlayer() throws IOException, ClassNotFoundException {
        CLI cli = new CLI();
        ModelMultiplayerView view = new ModelMultiplayerView(new Game(false, -1));
        SocketServerConnection ssc = new SocketServerConnection();
        ClientActionController cms = new ClientActionController(cli, view, ssc, false);
        ssc.setCac(cms);
        cms.setupMultiplayer();
    }


    public static void startSinglePlayer(){
        Game game = new Game(true, 0);
        game.setNumPlayer(1);
        Controller controller = new Controller(game);
        CLI cli = new CLI();
        ModelMultiplayerView modelMultiplayerView = new ModelMultiplayerView(new Game(true, 0));
        ClientActionController clientActionController = new ClientActionController(cli, modelMultiplayerView, null, true);
        clientActionController.addObserver(controller);
        game.addObserver(clientActionController);
        clientActionController.nicknameSetUp();

    }

    public synchronized String readFromInput (){
        System.out.print("> ");
        String input = in.nextLine();
        return input;
    }

    public synchronized void printToConsole(String message){
        System.out.println(message);
    }

}
