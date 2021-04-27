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
//        CLI cli = new CLI();
//        ModelMultiplayerView view = new ModelMultiplayerView(new Game(0, 0, 0, new ArrayList<>(), new LorenzoIlMagnifico(), new Board()));
//        ClientMessageReceiver cms = new ClientMessageReceiver(cli, view);
//        view.addObserver(cli);
//        cli.addObserver(cms);
//
//
//        cms.chooseAction();
//        //cms.setup();
//    }


        Game game = new Game();
        Controller controller = new Controller(game);
        game.setNumPlayer(1);
        game.getPlayers().add(new Player("GIGI", 0));
        game.getPlayerById(0).setPersonalBoard(new PersonalBoard());
        ModelMultiplayerView mmv = new ModelMultiplayerView(game);
        mmv.addObserver(controller);
        game.addObserver(mmv);
        CLI cli = new CLI();

        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.COIN, 0);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.SHIELD, 1);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.SHIELD, 1);

        mmv.addObserver(cli);
        ClientMessageReceiver cmr = new ClientMessageReceiver(
                cli, mmv);
        cmr.chooseAction();


    }
}


//    @BeforeEach
//    public void init() throws CloneNotSupportedException {
//        game= new Game();
//        controller= new Controller(game);
//        game.setNumPlayer(1);
//        game.getPlayers().add(new Player("GIGI", 0));
//        game.getPlayerById(0).setPersonalBoard(new PersonalBoard());
//        mmv= new ModelMultiplayerView(game.clone());
//        mmv.addObserver(controller);
//        game.addObserver(mmv);
//        cli= new CLI();
//
//
//    }

//    @Test
//    @Disabled("just for debugging")
//    public void isInitCorrect()  {
//
//        assertTrue(true);
//    }
//
//
//    @Test
//    @Disabled("Junit sucks")
//    public void firstTest()  {
//        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.COIN,0);
//        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.SHIELD,1);
//
//        mmv.addObserver(cli);
//        ClientMessageReceiver cmr= new ClientMessageReceiver(
//                cli, mmv);
//        cmr.chooseAction();
//    }
