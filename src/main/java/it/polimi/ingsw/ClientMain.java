package it.polimi.ingsw;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.CLI;
import it.polimi.ingsw.view.ClientActionController;
import it.polimi.ingsw.view.ModelMultiplayerView;
import it.polimi.ingsw.view.SocketServerConnection;

import java.io.IOException;
import java.util.Scanner;

public class ClientMain {
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
            view.addObserver(cli);
            cms.setupMultiplayer();
        }


        public static void startSinglePlayer(){
            Game game = new Game(true, 0);
            game.setNumPlayer(1);
            Controller controller = new Controller(game);
            CLI cli = new CLI();
            ModelMultiplayerView modelMultiplayerView = new ModelMultiplayerView(new Game(true, 0));
            ClientActionController clientActionController = new ClientActionController(cli, modelMultiplayerView, null, true);
            modelMultiplayerView.addObserver(cli);
            clientActionController.addObserver(controller);
            game.addObserver(clientActionController);
            clientActionController.nicknameSetUp();

        }

        /*         ------------- INITIAL PHASE FOR TESTING ACTIONS -------------------------------*/
//        Game game = new Game();
//        Controller controller = new Controller(game);
//        game.setNumPlayer(1);
//        game.getPlayers().add(new Player(0, "GIGI"));
//        game.getPlayers().add(new Player(1, "gigino"));
//        game.getPlayerById(0).setPersonalBoard(new PersonalBoard());
//        game.getPlayerById(1).setPersonalBoard(new PersonalBoard());
//        ModelMultiplayerView mmv = new ModelMultiplayerView(game);
//        CLI cli = new CLI();
//        ClientActionController cac= new ClientActionController(cli, mmv, null);
//        mmv.addObserver(controller);
//        game.addObserver(mmv);
//        mmv.setCac(cac);
//        mmv.addObserver(cli);
//        cac.chooseAction();

        /*    ---------------- MANAGE RESOURCE ACTION ----------------------------*/

//        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.COIN, 0);
//        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.SHIELD, 1);
//        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.SHIELD, 1);
//

        /*    ---------------- MANAGE RESOURCE ACTION ----------------------------*/
}


