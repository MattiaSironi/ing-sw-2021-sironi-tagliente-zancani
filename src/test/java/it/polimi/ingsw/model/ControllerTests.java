package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;


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
        game = new Game();
        controller = new Controller(game);
        personalBoard = new PersonalBoard();
        strongbox = new Strongbox();
        warehouse = new ShelfWarehouse();
        player = new Player(0, "gigi", true, new LeaderDeck(0, 1, new ArrayList<LeaderCard>()), 10, ResourceType.EMPTY, ResourceType.EMPTY, ResourceType.EMPTY, ResourceType.EMPTY, ResourceType.EMPTY, ResourceType.EMPTY, false, personalBoard);
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
//-----------------------PRODUCTION----------------------------------------------------------------------------------------------------
//    @Test
//    @DisplayName("Use basic production")
//    @Disabled("incorrect")
//    void useBasicProductionFromWarehouse1() {
//
//        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.COIN, 0);
//        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.STONE, 1);
//        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.STONE, 1);
//
//        ArrayList<ResourceType> resFromWarehouse = new ArrayList<>();
//        ArrayList<ResourceType> resFroStrongbox = new ArrayList<>();
//
//        resFromWarehouse.add(ResourceType.COIN);
//        resFromWarehouse.add(ResourceType.STONE);
//
//        controller.payResources(0, resFromWarehouse, resFroStrongbox);
//
//        assertEquals(0, game.getPlayerById(0).getPersonalBoard().getWarehouse().getResCount(ResourceType.COIN));
//        assertEquals(1, game.getPlayerById(0).getPersonalBoard().getWarehouse().getResCount(ResourceType.STONE));
//    }

//    @Test
//    @DisplayName("Use basic production")
//    void useBasicProductionFromWarehouse2() {
//
//        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.COIN, 0);
//        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.STONE, 1);
//        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.STONE, 1);
//
//        ArrayList<ResourceType> resFromWarehouse = new ArrayList<>();
//        ArrayList<ResourceType> resFroStrongbox = new ArrayList<>();
//
//        resFromWarehouse.add(ResourceType.COIN);
//        resFromWarehouse.add(ResourceType.SERVANT);
//
//        controller.payResources(0, resFromWarehouse, resFroStrongbox);
//
//        assertEquals(1, game.getPlayerById(0).getPersonalBoard().getWarehouse().getResCount(ResourceType.COIN));
//        assertEquals(2, game.getPlayerById(0).getPersonalBoard().getWarehouse().getResCount(ResourceType.STONE));
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




}
