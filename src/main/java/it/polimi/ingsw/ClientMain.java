package it.polimi.ingsw;

import it.polimi.ingsw.MessageReceiver.ClientMessageReceiver;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.CLI;
import it.polimi.ingsw.view.ModelMultiplayerView;

import java.io.IOException;
import java.util.ArrayList;

public class ClientMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException {


        /*--------- INITIAL PHASE MULTIPLAYER MATCH ------------*/
        CLI cli = new CLI();
        ModelMultiplayerView view = new ModelMultiplayerView(new Game());
        ClientMessageReceiver cms = new ClientMessageReceiver(cli, view);
        view.addObserver(cli);
        cli.addObserver(cms);
        cms.setup();

        /*         ------------- INITIAL PHASE FOR TESTING ACTIONS -------------------------------*/
//        Game game = new Game();
//        Controller controller = new Controller(game);
//        game.setNumPlayer(1);
//        game.getPlayers().add(new Player("GIGI", 0));
//        game.getPlayerById(0).setPersonalBoard(new PersonalBoard());
//        ModelMultiplayerView mmv = new ModelMultiplayerView(game);
//        mmv.addObserver(controller);
//        game.addObserver(mmv);
//        CLI cli = new CLI();
//        mmv.addObserver(cli);
//        ClientMessageReceiver cmr = new ClientMessageReceiver(cli, mmv);
//        cmr.chooseAction();

        /*    ---------------- MANAGE RESOURCE ACTION ----------------------------*/

//        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.COIN, 0);
//        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.SHIELD, 1);
//        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.SHIELD, 1);
//


    }
}


