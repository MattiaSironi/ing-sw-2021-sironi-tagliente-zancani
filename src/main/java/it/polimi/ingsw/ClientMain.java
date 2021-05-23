package it.polimi.ingsw;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.CLI;
import it.polimi.ingsw.view.ClientActionController;
import it.polimi.ingsw.view.ModelMultiplayerView;
import it.polimi.ingsw.view.SocketServerConnection;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException {


        /*--------- INITIAL PHASE MULTIPLAYER MATCH ------------*/
        CLI cli = new CLI();
        ModelMultiplayerView view = new ModelMultiplayerView(new Game(false));
        SocketServerConnection ssc = new SocketServerConnection();
        ClientActionController cms = new ClientActionController(cli, view, ssc);
        ssc.setCac(cms);
        view.addObserver(cli);
        cms.setup();

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
}


