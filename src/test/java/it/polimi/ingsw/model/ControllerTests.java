package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.message.ActionMessages.*;
import it.polimi.ingsw.message.CommonMessages.*;
import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.SocketClientConnection;
import it.polimi.ingsw.view.ModelMultiplayerView;
import it.polimi.ingsw.view.RemoteView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import javax.lang.model.element.ModuleElement;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTests {
    Controller controller;
    Game game;
    PersonalBoard personalBoard;
    Strongbox strongbox;
    ShelfWarehouse warehouse;
    Player player;

    @BeforeEach
    void init() {
        game = new Game(false);
        controller = new Controller(game);
        personalBoard = new PersonalBoard();
        strongbox = new Strongbox();
        warehouse = new ShelfWarehouse();
        player = new Player(0, "gigi", true, new LeaderDeck(new ArrayList<LeaderCard>()), 10, ResourceType.EMPTY, ResourceType.EMPTY, ResourceType.EMPTY, ResourceType.EMPTY, ResourceType.EMPTY, ResourceType.EMPTY, false, personalBoard);
        game.getPlayers().add(player);
        game.getPlayers().get(0).setId(0);
        game.getPlayerById(0).setPersonalBoard(personalBoard);
        game.getPlayerById(0).getPersonalBoard().setStrongbox(strongbox);
        game.getPlayerById(0).getPersonalBoard().setWarehouse(warehouse);


//        -------RES INIT  PART------------
        ArrayList<Marble> hand = new ArrayList<>();
        hand.add(new Marble(ResourceType.COIN)); //JUST TO MAKE SURE THERE IS SOMETHING TO REMOVE. USELESS FOR THESE TESTS
        hand.add(new Marble(ResourceType.SHIELD));
        hand.add(new Marble(ResourceType.SHIELD));
        hand.add(new Marble(ResourceType.SERVANT));
        hand.add(new Marble(ResourceType.STONE));
        hand.add(new Marble(ResourceType.COIN));
        hand.add(new Marble(ResourceType.SHIELD));
        hand.add(new Marble(ResourceType.SHIELD));
        hand.add(new Marble(ResourceType.SERVANT));
        hand.add(new Marble(ResourceType.STONE));
        hand.add(new Marble(ResourceType.COIN));
        hand.add(new Marble(ResourceType.SHIELD));
        hand.add(new Marble(ResourceType.SHIELD));
        hand.add(new Marble(ResourceType.SERVANT));
        hand.add(new Marble(ResourceType.STONE));
        game.setMarketHand(hand);

    }

    @Test
    @DisplayName("new player added")
    public void newPlayerAdded() {
        controller.setNickname(new Nickname("gigi", 0, false)); //not added
        controller.setNickname(new Nickname("lea", 1, false));  //added
        controller.setNickname(new Nickname("lea", 2, false)); //not added
        controller.setNickname(new Nickname("simo", 2, false)); //added
        assertEquals(3, game.getPlayers().size());
    }
//---------------------------------------------------------------------------------------------------------------------------
    @Test
    @DisplayName("Pay one Resource")
    void payResource1() {
        ArrayList<ResourceType> resources = new ArrayList<>();
        resources.add(ResourceType.COIN);
        game.getBoard().getMatrix().setResToPay(resources);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.COIN, 0);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.STONE, 1);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.STONE, 1);
        controller.payRes(true, 0, true);

        assertEquals(0, game.getPlayerById(0).getPersonalBoard().getStrongbox().getResCount(ResourceType.COIN));
    }

    @Test
    @DisplayName("Pay one Resource")
    void payResource2() {
        ArrayList<ResourceType> resources = new ArrayList<>();
        resources.add(ResourceType.COIN);
        game.getBoard().getMatrix().setResToPay(resources);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.COIN, 0);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.STONE, 1);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.STONE, 1);
        controller.payRes(true, 0, false);

        assertEquals(0, game.getPlayerById(0).getPersonalBoard().getStrongbox().getResCount(ResourceType.COIN));
    }

//    @Test
//    @DisplayName("Pay one Resource")
//    void placeDevCard() {
//        game.getBoard().getMatrix().getChosenCard() = new DevCard(0,1,1,CardColor.YELLOW, new int[],  );
//
//    }






    //-----------------------LEADER PRODUCTION----------------------------------------------------------------------------------------------------

    @Test
    @DisplayName("Use Leader Production")
    void useLeaderProductionFromWarehouse0() {
        int oldFaithPos = game.getPlayerById(0).getPersonalBoard().getFaithTrack().getMarker();
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.COIN, 0);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.STONE, 1);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.STONE, 1);

        controller.useLeaderProduction(0, ResourceType.COIN, ResourceType.SHIELD, true);

        assertEquals(0, game.getPlayerById(0).getPersonalBoard().getWarehouse().getResCount(ResourceType.COIN));

        assertEquals(game.getPlayerById(0).getPersonalBoard().getFaithTrack().getMarker(), oldFaithPos + 1);
    }

    @Test
    @DisplayName("Use Leader Production")
    void useLeaderProductionFromWarehouse1() {
        int oldFaithPos = game.getPlayerById(0).getPersonalBoard().getFaithTrack().getMarker();
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.COIN, 0);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.STONE, 1);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.STONE, 1);

        controller.useLeaderProduction(0, ResourceType.STONE, ResourceType.SHIELD, true);

        assertEquals(1, game.getPlayerById(0).getPersonalBoard().getWarehouse().getResCount(ResourceType.STONE));
        assertEquals(game.getPlayerById(0).getPersonalBoard().getFaithTrack().getMarker(), oldFaithPos + 1);
    }

    @Test
    @DisplayName("Use Leader Production")
    void useLeaderProductionFromWarehouse2() {
        int oldFaithPos = game.getPlayerById(0).getPersonalBoard().getFaithTrack().getMarker();
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.SERVANT, 1);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.SERVANT, 1);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.STONE, 2);

        controller.useLeaderProduction(0, ResourceType.SERVANT, ResourceType.SHIELD, true);

        assertEquals(1, game.getPlayerById(0).getPersonalBoard().getWarehouse().getResCount(ResourceType.SERVANT));
        assertEquals(game.getPlayerById(0).getPersonalBoard().getFaithTrack().getMarker(), oldFaithPos + 1);
    }

    @Test
    @DisplayName("Use Leader Production")
    void useLeaderProductionFromWarehouse3() {
        int oldFaithPos = game.getPlayerById(0).getPersonalBoard().getFaithTrack().getMarker();
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.COIN, 2);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.STONE, 1);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.STONE, 1);

        controller.useLeaderProduction(0, ResourceType.COIN, ResourceType.SHIELD, true);

        assertEquals(0, game.getPlayerById(0).getPersonalBoard().getWarehouse().getResCount(ResourceType.COIN));
        assertEquals(game.getPlayerById(0).getPersonalBoard().getFaithTrack().getMarker(), oldFaithPos + 1);
    }

    @Test
    @DisplayName("Use Leader Production")
    void useLeaderProductionFromWarehouse4() {
        int oldFaithPos = game.getPlayerById(0).getPersonalBoard().getFaithTrack().getMarker();
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.COIN, 0);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.STONE, 2);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.STONE, 2);

        controller.useLeaderProduction(0, ResourceType.STONE, ResourceType.SHIELD, true);

        assertEquals(1, game.getPlayerById(0).getPersonalBoard().getWarehouse().getResCount(ResourceType.STONE));
        assertEquals(game.getPlayerById(0).getPersonalBoard().getFaithTrack().getMarker(), oldFaithPos + 1);
    }

    @Test
    @DisplayName("Use Leader Production")
    void useLeaderProductionFromWarehouse5() {
        int oldFaithPos = game.getPlayerById(0).getPersonalBoard().getFaithTrack().getMarker();
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.COIN, 2);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.COIN, 2);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.COIN, 2);

        controller.useLeaderProduction(0, ResourceType.COIN, ResourceType.SHIELD, true);

        assertEquals(2, game.getPlayerById(0).getPersonalBoard().getWarehouse().getResCount(ResourceType.COIN));
        assertEquals(game.getPlayerById(0).getPersonalBoard().getFaithTrack().getMarker(), oldFaithPos + 1);
    }

    //____________________________________________________________________________________________________________________________________________________________
    @Test
    @DisplayName("Use Leader Production")
    void useLeaderProductionFromStrongbox0() {
        int oldFaithPos = game.getPlayerById(0).getPersonalBoard().getFaithTrack().getMarker();
        game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.COIN, 3);
        game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.STONE, 1);
        game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.STONE, 1);

        controller.useLeaderProduction(0, ResourceType.COIN, ResourceType.SHIELD, false);

        assertEquals(2, game.getPlayerById(0).getPersonalBoard().getStrongbox().getResCount(ResourceType.COIN));

        assertEquals(game.getPlayerById(0).getPersonalBoard().getFaithTrack().getMarker(), oldFaithPos + 1);
    }

    @Test
    @DisplayName("Use Leader Production")
    void useLeaderProductionFromStrongbox1() {
        int oldFaithPos = game.getPlayerById(0).getPersonalBoard().getFaithTrack().getMarker();
        game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.COIN, 1);
        game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.STONE, 7);


        controller.useLeaderProduction(0, ResourceType.STONE, ResourceType.SHIELD, false);

        assertEquals(6, game.getPlayerById(0).getPersonalBoard().getStrongbox().getResCount(ResourceType.STONE));
        assertEquals(game.getPlayerById(0).getPersonalBoard().getFaithTrack().getMarker(), oldFaithPos + 1);
    }

    @Test
    @DisplayName("Use Leader Production")
    void useLeaderProductionFromStrongbox2() {
        int oldFaithPos = game.getPlayerById(0).getPersonalBoard().getFaithTrack().getMarker();
        game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.SERVANT, 5);
        game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.STONE, 1);

        controller.useLeaderProduction(0, ResourceType.SERVANT, ResourceType.SHIELD, false);

        assertEquals(4, game.getPlayerById(0).getPersonalBoard().getStrongbox().getResCount(ResourceType.SERVANT));
        assertEquals(game.getPlayerById(0).getPersonalBoard().getFaithTrack().getMarker(), oldFaithPos + 1);
    }

    @Test
    @DisplayName("Use Leader Production")
    void useLeaderProductionFromStrongbox3() {
        int oldFaithPos = game.getPlayerById(0).getPersonalBoard().getFaithTrack().getMarker();
        game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.COIN, 8);
        game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.STONE, 1);
        game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.STONE, 1);

        controller.useLeaderProduction(0, ResourceType.COIN, ResourceType.SHIELD, false);

        assertEquals(7, game.getPlayerById(0).getPersonalBoard().getStrongbox().getResCount(ResourceType.COIN));
        assertEquals(game.getPlayerById(0).getPersonalBoard().getFaithTrack().getMarker(), oldFaithPos + 1);
    }


    //-----------------------BUY DEVELOPMENT CARD----------------------------------------------------------------------------------------------------

    @Test
    @Disabled("incorrect")
    @DisplayName("Buy Development Card")
    void buyDevCardFromWarehouse() {
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.COIN, 0);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.STONE, 2);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.STONE, 2);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.SERVANT, 1);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.SERVANT, 1);

        ArrayList<ResourceType> resFromWare = new ArrayList<>();
        ArrayList<ResourceType> resFromStrong = new ArrayList<>();

        resFromWare.add(ResourceType.COIN);
        resFromWare.add(ResourceType.STONE);
        resFromWare.add(ResourceType.STONE);
        resFromWare.add(ResourceType.SERVANT);

//        controller.buyDevCard(0, true, false, resFromWare, resFromStrong);
        assertEquals(0, game.getPlayerById(0).getPersonalBoard().getWarehouse().getResCount(ResourceType.COIN));
        assertEquals(0, game.getPlayerById(0).getPersonalBoard().getWarehouse().getResCount(ResourceType.STONE));
        assertEquals(1, game.getPlayerById(0).getPersonalBoard().getWarehouse().getResCount(ResourceType.SERVANT));
    }


//    ----------------RESOURCES------------------

    @Test
    @DisplayName("adding resources using Controller methods")
    public void addResStandardShelves() {

        controller.placeRes(ResourceType.COIN, 0, 0, false, false); //YES
        controller.placeRes(ResourceType.COIN, 0, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 0, 0, false, false); //NO
        controller.placeRes(ResourceType.SHIELD, 0, 0, false, false); //NO
        controller.placeRes(ResourceType.STONE, 0, 0, false, false); //NO

        controller.placeRes(ResourceType.COIN, 1, 0, false, false); //NO
        controller.placeRes(ResourceType.COIN, 1, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 1, 0, false, false); //YES
        controller.placeRes(ResourceType.SHIELD, 1, 0, false, false); //NO
        controller.placeRes(ResourceType.STONE, 1, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 1, 0, false, false); //YES

        controller.placeRes(ResourceType.COIN, 2, 0, false, false); //NO
        controller.placeRes(ResourceType.COIN, 2, 0, false, false); //NO
        controller.placeRes(ResourceType.COIN, 2, 0, false, false); //NO
        controller.placeRes(ResourceType.COIN, 2, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 2, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 2, 0, false, false); //NO
        controller.placeRes(ResourceType.SHIELD, 2, 0, false, false); //YES
        controller.placeRes(ResourceType.STONE, 2, 0, false, false); //NO


        controller.placeRes(ResourceType.COIN, 3, 0, false, false); //NO
        controller.placeRes(ResourceType.COIN, 3, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 3, 0, false, false); //NO
        controller.placeRes(ResourceType.SHIELD, 3, 0, false, false); //NO
        controller.placeRes(ResourceType.STONE, 3, 0, false, false); //NO

        controller.placeRes(ResourceType.COIN, 4, 0, false, false); //NO
        controller.placeRes(ResourceType.COIN, 4, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 4, 0, false, false); //NO
        controller.placeRes(ResourceType.SHIELD, 4, 0, false, false); //NO
        controller.placeRes(ResourceType.STONE, 4, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 4, 0, false, false); //NO

        controller.placeRes(ResourceType.COIN, 3, 0, false, false); //NO
        controller.placeRes(ResourceType.COIN, 3, 0, false, false); //NO
        controller.placeRes(ResourceType.COIN, 3, 0, false, false); //NO
        controller.placeRes(ResourceType.COIN, 3, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 4, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 4, 0, false, false); //NO
        controller.placeRes(ResourceType.SHIELD, 4, 0, false, false); //NO
        controller.placeRes(ResourceType.STONE, 4, 0, false, false); //NO


        ShelfWarehouse gigi = controller.getGame().getPlayerById(0).getPersonalBoard().getWarehouse();

//         ----SIZES------
        assertEquals(1, gigi.getShelves().get(0).getCount());
        assertEquals(2, gigi.getShelves().get(1).getCount());
        assertEquals(1, gigi.getShelves().get(2).getCount());
        assertEquals(0, gigi.getShelves().get(3).getCount());
        assertEquals(0, gigi.getShelves().get(4).getCount());


//        ---RES------

        assertEquals(ResourceType.COIN, gigi.getShelves().get(0).getResType());
        assertEquals(ResourceType.SERVANT, gigi.getShelves().get(1).getResType());
        assertEquals(ResourceType.SHIELD, gigi.getShelves().get(2).getResType());
        assertNull(gigi.getShelves().get(3).getResType());
        assertNull(gigi.getShelves().get(4).getResType());


    }
    @Test
    @DisplayName("swap the AddTestStandardShelves ")
    public void swap1()  {
        addResStandardShelves();
        controller.swapShelves(-1, 3, 0);
        controller.swapShelves(5,5,0);
        controller.update(new ManageResourceMessage(5,5,0));
        controller.swapShelves(1,1,0);
        controller.swapShelves(3,4,0);
        controller.swapShelves(4,3,0);
        controller.swapShelves(1,3,0);
        controller.swapShelves(0,1,0);
        controller.swapShelves(0,2,0); //yes
        controller.swapShelves(1,2,0); //yes



        ShelfWarehouse gigi = controller.getGame().getPlayerById(0).getPersonalBoard().getWarehouse();

//         ----SIZES------
        assertEquals(1, gigi.getShelves().get(0).getCount());
        assertEquals(1, gigi.getShelves().get(1).getCount());
        assertEquals(2, gigi.getShelves().get(2).getCount());
        assertEquals(0, gigi.getShelves().get(3).getCount());
        assertEquals(0, gigi.getShelves().get(4).getCount());


//        ---RES------

        assertEquals(ResourceType.SHIELD, gigi.getShelves().get(0).getResType());
        assertEquals(ResourceType.COIN, gigi.getShelves().get(1).getResType());
        assertEquals(ResourceType.SERVANT, gigi.getShelves().get(2).getResType());
        assertNull(gigi.getShelves().get(3).getResType());
        assertNull(gigi.getShelves().get(4).getResType());




    }


    @Test
    @DisplayName("special shelf 4")
    public void specialShelf4() {
        controller.getGame().getPlayerById(0).getPersonalBoard().getWarehouse().getShelves().get(3).setResType(ResourceType.SHIELD);

        controller.placeRes(ResourceType.COIN, 0, 0, false, false); //YES
        controller.placeRes(ResourceType.STONE, 0, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 0, 0, false, false); //NO
        controller.placeRes(ResourceType.SHIELD, 0, 0, false, false); //NO
        controller.placeRes(ResourceType.STONE, 0, 0, false, false); //NO

        controller.placeRes(ResourceType.COIN, 1, 0, false, false); //NO
        controller.placeRes(ResourceType.COIN, 1, 0, false, false); //NO
        controller.placeRes(ResourceType.SHIELD, 1, 0, false, false); //YES
        controller.placeRes(ResourceType.SHIELD, 1, 0, false, false); //YES
        controller.placeRes(ResourceType.STONE, 1, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 1, 0, false, false); //NO

        controller.placeRes(ResourceType.COIN, 2, 0, false, false); //NO
        controller.placeRes(ResourceType.COIN, 2, 0, false, false); //NO
        controller.placeRes(ResourceType.COIN, 2, 0, false, false); //NO
        controller.placeRes(ResourceType.COIN, 2, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 2, 0, false, false); //YES
        controller.placeRes(ResourceType.SERVANT, 2, 0, false, false); //YES
        controller.placeRes(ResourceType.SHIELD, 2, 0, false, false); //NO
        controller.placeRes(ResourceType.STONE, 2, 0, false, false); //NO


        controller.placeRes(ResourceType.COIN, 3, 0, false, false); //NO
        controller.placeRes(ResourceType.COIN, 3, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 3, 0, false, false); //NO
        controller.placeRes(ResourceType.SHIELD, 3, 0, false, false); //YES
        controller.placeRes(ResourceType.STONE, 3, 0, false, false); //NO
        controller.placeRes(ResourceType.COIN, 3, 0, false, false); //NO
        controller.placeRes(ResourceType.COIN, 3, 0, false, false); //NO
        controller.placeRes(ResourceType.SHIELD, 3, 0, false, false); //YES
        controller.placeRes(ResourceType.SHIELD, 3, 0, false, false); //NO

        controller.placeRes(ResourceType.COIN, 4, 0, false, false); //NO
        controller.placeRes(ResourceType.COIN, 4, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 4, 0, false, false); //NO
        controller.placeRes(ResourceType.SHIELD, 4, 0, false, false); //NO
        controller.placeRes(ResourceType.STONE, 4, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 4, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 4, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 4, 0, false, false); //NO
        controller.placeRes(ResourceType.SHIELD, 4, 0, false, false); //NO
        controller.placeRes(ResourceType.STONE, 4, 0, false, false); //NO

        ShelfWarehouse gigi = controller.getGame().getPlayerById(0).getPersonalBoard().getWarehouse();

//         ----SIZES------
        assertEquals(1, gigi.getShelves().get(0).getCount());
        assertEquals(2, gigi.getShelves().get(1).getCount());
        assertEquals(2, gigi.getShelves().get(2).getCount());
        assertEquals(2, gigi.getShelves().get(3).getCount());
        assertEquals(0, gigi.getShelves().get(4).getCount());


//        ---RES------

        assertEquals(ResourceType.COIN, gigi.getShelves().get(0).getResType());
        assertEquals(ResourceType.SHIELD, gigi.getShelves().get(1).getResType());
        assertEquals(ResourceType.SERVANT, gigi.getShelves().get(2).getResType());
        assertEquals(ResourceType.SHIELD, gigi.getShelves().get(3).getResType());
        assertNull(gigi.getShelves().get(4).getResType());


    }


    @Test
    @DisplayName("special shelf 4")
    public void specialShelf5() {
        controller.getGame().getPlayerById(0).getPersonalBoard().getWarehouse().getShelves().get(4).setResType(ResourceType.COIN);

        controller.placeRes(ResourceType.COIN, 0, 0, false, false); //YES
        controller.placeRes(ResourceType.STONE, 0, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 0, 0, false, false); //NO
        controller.placeRes(ResourceType.SHIELD, 0, 0, false, false); //NO
        controller.placeRes(ResourceType.STONE, 0, 0, false, false); //NO


        controller.placeRes(ResourceType.COIN, 2, 0, false, false); //NO
        controller.placeRes(ResourceType.COIN, 2, 0, false, false); //NO
        controller.placeRes(ResourceType.COIN, 2, 0, false, false); //NO
        controller.placeRes(ResourceType.COIN, 2, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 2, 0, false, false); //YES
        controller.placeRes(ResourceType.SERVANT, 2, 0, false, false); //YES
        controller.placeRes(ResourceType.SHIELD, 2, 0, false, false); //NO
        controller.placeRes(ResourceType.STONE, 2, 0, false, false); //NO

        controller.placeRes(ResourceType.COIN, 1, 0, false, false); //NO
        controller.placeRes(ResourceType.COIN, 1, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 1, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 1, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 1, 0, false, false); //NO


        controller.placeRes(ResourceType.COIN, 3, 0, false, false); //NO
        controller.placeRes(ResourceType.COIN, 3, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 3, 0, false, false); //NO
        controller.placeRes(ResourceType.SHIELD, 3, 0, false, false); //NO
        controller.placeRes(ResourceType.STONE, 3, 0, false, false); //NO
        controller.placeRes(ResourceType.COIN, 3, 0, false, false); //NO
        controller.placeRes(ResourceType.COIN, 3, 0, false, false); //NO
        controller.placeRes(ResourceType.SHIELD, 3, 0, false, false); //NO
        controller.placeRes(ResourceType.SHIELD, 3, 0, false, false); //NO

        controller.placeRes(ResourceType.COIN, 4, 0, false, false); //YES
        controller.placeRes(ResourceType.SERVANT, 4, 0, false, false); //NO
        controller.placeRes(ResourceType.SHIELD, 4, 0, false, false); //NO
        controller.placeRes(ResourceType.STONE, 4, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 4, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 4, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 4, 0, false, false); //NO
        controller.placeRes(ResourceType.SHIELD, 4, 0, false, false); //NO
        controller.placeRes(ResourceType.STONE, 4, 0, false, false); //NO

        ShelfWarehouse gigi = controller.getGame().getPlayerById(0).getPersonalBoard().getWarehouse();

//         ----SIZES------
        assertEquals(1, gigi.getShelves().get(0).getCount());
        assertEquals(0, gigi.getShelves().get(1).getCount());
        assertEquals(2, gigi.getShelves().get(2).getCount());
        assertEquals(0, gigi.getShelves().get(3).getCount());
        assertEquals(1, gigi.getShelves().get(4).getCount());


//        ---RES------

        assertEquals(ResourceType.COIN, gigi.getShelves().get(0).getResType());
        assertEquals(ResourceType.EMPTY, gigi.getShelves().get(1).getResType());
        assertEquals(ResourceType.SERVANT, gigi.getShelves().get(2).getResType());
        assertNull(gigi.getShelves().get(3).getResType());
        assertEquals(ResourceType.COIN, gigi.getShelves().get(4).getResType());


    }


    @Test
    @DisplayName("special shelf 4 & 5")
    public void nowBoth() {
        controller.getGame().getPlayerById(0).getPersonalBoard().getWarehouse().getShelves().get(3).setResType(ResourceType.COIN);
        controller.getGame().getPlayerById(0).getPersonalBoard().getWarehouse().getShelves().get(4).setResType(ResourceType.SERVANT);

        controller.placeRes(ResourceType.COIN, 0, 0, false, false); //YES
        controller.placeRes(ResourceType.STONE, 0, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 0, 0, false, false); //NO
        controller.placeRes(ResourceType.SHIELD, 0, 0, false, false); //NO
        controller.placeRes(ResourceType.STONE, 0, 0, false, false); //NO


        controller.placeRes(ResourceType.COIN, 2, 0, false, false); //NO
        controller.placeRes(ResourceType.COIN, 2, 0, false, false); //NO
        controller.placeRes(ResourceType.COIN, 2, 0, false, false); //NO
        controller.placeRes(ResourceType.COIN, 2, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 2, 0, false, false); //YES
        controller.placeRes(ResourceType.SERVANT, 2, 0, false, false); //YES
        controller.placeRes(ResourceType.SHIELD, 2, 0, false, false); //NO
        controller.placeRes(ResourceType.STONE, 2, 0, false, false); //NO

        controller.placeRes(ResourceType.COIN, 1, 0, false, false); //NO
        controller.placeRes(ResourceType.COIN, 1, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 1, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 1, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 1, 0, false, false); //NO


        controller.placeRes(ResourceType.COIN, 3, 0, false, false); //YES
        controller.placeRes(ResourceType.COIN, 3, 0, false, false); //YES
        controller.placeRes(ResourceType.SERVANT, 3, 0, false, false); //NO
        controller.placeRes(ResourceType.SHIELD, 3, 0, false, false); //NO
        controller.placeRes(ResourceType.STONE, 3, 0, false, false); //NO
        controller.placeRes(ResourceType.COIN, 3, 0, false, false); //NO
        controller.placeRes(ResourceType.COIN, 3, 0, false, false); //NO
        controller.placeRes(ResourceType.SHIELD, 3, 0, false, false); //NO
        controller.placeRes(ResourceType.SHIELD, 3, 0, false, false); //NO

        controller.placeRes(ResourceType.COIN, 4, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 4, 0, false, false); //YES
        controller.placeRes(ResourceType.SHIELD, 4, 0, false, false); //NO
        controller.placeRes(ResourceType.STONE, 4, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 4, 0, false, false); //YES
        controller.placeRes(ResourceType.SERVANT, 4, 0, false, false); //NO
        controller.placeRes(ResourceType.SERVANT, 4, 0, false, false); //NO
        controller.placeRes(ResourceType.SHIELD, 4, 0, false, false); //NO
        controller.placeRes(ResourceType.STONE, 4, 0, false, false); //NO

        ShelfWarehouse gigi = controller.getGame().getPlayerById(0).getPersonalBoard().getWarehouse();

//         ----SIZES------
        assertEquals(1, gigi.getShelves().get(0).getCount());
        assertEquals(0, gigi.getShelves().get(1).getCount());
        assertEquals(2, gigi.getShelves().get(2).getCount());
        assertEquals(2, gigi.getShelves().get(3).getCount());
        assertEquals(2, gigi.getShelves().get(4).getCount());


//        ---RES------

        assertEquals(ResourceType.COIN, gigi.getShelves().get(0).getResType());
        assertEquals(ResourceType.EMPTY, gigi.getShelves().get(1).getResType());
        assertEquals(ResourceType.SERVANT, gigi.getShelves().get(2).getResType());
        assertEquals(ResourceType.COIN, gigi.getShelves().get(3).getResType());
        assertEquals(ResourceType.SERVANT, gigi.getShelves().get(4).getResType());


    }


    @Test
    @DisplayName("DISCARD RES")
    public void discardRes()  {
        controller.setNickname(new Nickname("lea", 1, false));
        controller.update(new MarketMessage(true, 0,0));
        assertEquals(4,controller.getGame().getBoard().getMarket().getHand().size());
        controller.goToMarket(false, 0, 0);
        assertEquals(3,controller.getGame().getBoard().getMarket().getHand().size());
        assertEquals(0, game.getPlayerById(0).getPersonalBoard().getFaithTrack().getMarker());
        assertEquals(0, game.getPlayerById(1).getPersonalBoard().getFaithTrack().getMarker());
        controller.update(new PlaceResourceMessage(ResourceType.COIN, -1, 0, false, true));
        assertEquals(0, game.getPlayerById(0).getPersonalBoard().getFaithTrack().getMarker());
        assertEquals(1, game.getPlayerById(1).getPersonalBoard().getFaithTrack().getMarker());
        controller.placeRes(ResourceType.FAITH_POINT, -1, 0,  false, false );
        assertEquals(1, game.getPlayerById(0).getPersonalBoard().getFaithTrack().getMarker());
        assertEquals(1, game.getPlayerById(1).getPersonalBoard().getFaithTrack().getMarker());

    }


    @Test
    @DisplayName("End turn")
    public void endTurn()  {
        controller.setNickname(new Nickname("lea", 1 ,false));
        controller.getGame().setTurn(0,ActionPhase.WAITING_FOR_ACTION, false, null);
        controller.getGame().endTurn(0);
        assertEquals(1, controller.getGame().getTurn().getPlayerPlayingID());
    }

    @Test
    @DisplayName("End turn with update")
    public void endTurnUpdate() {


        controller.update(new Nickname("lea", 1, false));
        controller.getGame().setTurn(0, ActionPhase.WAITING_FOR_ACTION, false, null);
        controller.update(new EndTurnMessage(0));
        assertEquals(1, controller.getGame().getTurn().getPlayerPlayingID());

    }
    @Test
    @DisplayName("Initial phase")
    public void initialPhase()  {
        controller.setNickname(new Nickname("GIGI" ,1 ,false));
        controller.setNickname(new Nickname("GiGi", 2, false));
        controller.setNickname(new Nickname("gigI", 3, false));
        controller.initialPhase();
        assertEquals(0, controller.getGame().getPlayers().get(0).getPersonalBoard().getFaithTrack().getMarker());
        assertEquals(0,controller.getGame().getPlayers().get(0).getStartResCount() );
        assertEquals(2,controller.getGame().getPlayers().get(0).getLeaderCardsToDiscard());
        assertEquals(0, controller.getGame().getPlayers().get(1).getPersonalBoard().getFaithTrack().getMarker());
        assertEquals(1,controller.getGame().getPlayers().get(1).getStartResCount() );
        assertEquals(0,controller.getGame().getPlayers().get(1).getLeaderCardsToDiscard());
        assertEquals(1, controller.getGame().getPlayers().get(2).getPersonalBoard().getFaithTrack().getMarker());
        assertEquals(1,controller.getGame().getPlayers().get(2).getStartResCount() );
        assertEquals(0,controller.getGame().getPlayers().get(2).getLeaderCardsToDiscard());
        assertEquals(1, controller.getGame().getPlayers().get(3).getPersonalBoard().getFaithTrack().getMarker());
        assertEquals(2,controller.getGame().getPlayers().get(3).getStartResCount() );
        assertEquals(0,controller.getGame().getPlayers().get(3).getLeaderCardsToDiscard());

    }

    @Test
    @DisplayName("distributing leader Cards")
    public void distributingLeaders() {
        controller.setNickname(new Nickname("GIGI" ,1 ,false));
        controller.setNickname(new Nickname("GiGi", 2, false));
        controller.setNickname(new Nickname("gigI", 3, false));
        game.giveLeaderCards();
        assertEquals(0, game.getBoard().getLeaderDeck().getCards().size());
        assertEquals(4, game.getPlayers().get(0).getLeaderDeck().getCards().size());
        assertEquals(4, game.getPlayers().get(1).getLeaderDeck().getCards().size());
        assertEquals(4, game.getPlayers().get(2).getLeaderDeck().getCards().size());
        assertEquals(4, game.getPlayers().get(3).getLeaderDeck().getCards().size());

        ArrayList<LeaderCard> lea = new ArrayList<>();

        for (Player p : game.getPlayers())  {
            for (LeaderCard l : p.getLeaderDeck().getCards()) lea.add(l);

        }
        assertEquals(16, lea.size());

        for (LeaderCard l : lea)  {
            for (LeaderCard ll: lea.subList(lea.indexOf(l)+1, lea.size()))  {
                assertEquals(false, l.equals(ll));
            }
        }


    }

    @Test
    @DisplayName("just for coverage :P")
    public void randomUpdates()  {
        controller.update(new ErrorMessage("hello", 0));
        controller.update(new IdMessage(0));
        controller.update(new ObjectMessage(game, -1, -1));
        controller.update(new PrintableMessage(game.getPlayerById(0).getPersonalBoard().getFaithTrack()));
        controller.update(new ChooseNumberOfPlayer(2));
        assertEquals(true, true);
    }








}
