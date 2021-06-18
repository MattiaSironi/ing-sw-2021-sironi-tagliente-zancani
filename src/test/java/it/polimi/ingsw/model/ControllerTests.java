package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.message.ActionMessages.*;
import it.polimi.ingsw.message.CommonMessages.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


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
        game = new Game(false, 0);
        controller = new Controller(game);
        personalBoard = new PersonalBoard();
        strongbox = new Strongbox();
        warehouse = new ShelfWarehouse();
        player = new Player(0, "gigi");
        game.getPlayers().add(player);
        game.getPlayers().add(new Player(1,"ollare"));
        game.getPlayers().get(0).setId(0);
        game.getPlayerById(0).setPersonalBoard(personalBoard);
        game.getPlayerById(0).getPersonalBoard().setStrongbox(strongbox);
        game.getPlayerById(0).getPersonalBoard().setWarehouse(warehouse);
        game.setTurn(0, ActionPhase.WAITING_FOR_ACTION);




    }

    void resPart()  {
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
        controller.setNickname(new Nickname("gigi", 0)); //not added
        controller.setNickname(new Nickname("lea", 1));  //added
        controller.setNickname(new Nickname("lea", 2)); //not added

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
        controller.payRes(true, 0, ActionPhase.PAYMENT);

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
        controller.payRes(true, 0, ActionPhase.PAYMENT);

        assertEquals(0, game.getPlayerById(0).getPersonalBoard().getStrongbox().getResCount(ResourceType.COIN));
    }

//    @Test
//    @DisplayName("Pay one Resource")
//    void placeDevCard() {
//        game.getBoard().getMatrix().getChosenCard() = new DevCard(0,1,1,CardColor.YELLOW, new int[],  );
//
//    }































//    ----------------RESOURCES------------------

    @Test
    @DisplayName("adding resources using Controller methods")
    public void addResStandardShelves() {
        resPart();

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
        resPart();
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
        resPart();
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

        controller.swapShelves(3,4,0);

         gigi = controller.getGame().getPlayerById(0).getPersonalBoard().getWarehouse();

        //         ----SIZES------
        assertEquals(1, gigi.getShelves().get(0).getCount());
        assertEquals(0, gigi.getShelves().get(1).getCount());
        assertEquals(2, gigi.getShelves().get(2).getCount());
        assertEquals(1, gigi.getShelves().get(3).getCount());
        assertEquals(0, gigi.getShelves().get(4).getCount());


//        ---RES------

        assertEquals(ResourceType.COIN, gigi.getShelves().get(0).getResType());
        assertEquals(ResourceType.EMPTY, gigi.getShelves().get(1).getResType());
        assertEquals(ResourceType.SERVANT, gigi.getShelves().get(2).getResType());
        assertEquals(ResourceType.COIN, gigi.getShelves().get(3).getResType());
        assertNull(gigi.getShelves().get(4).getResType());


    }


    @Test
    @DisplayName("special shelf 4 & 5")
    public void nowBoth() {
        resPart();
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

        controller.swapShelves(3,4,0);
        gigi = controller.getGame().getPlayerById(0).getPersonalBoard().getWarehouse();
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
        assertEquals(ResourceType.SERVANT, gigi.getShelves().get(3).getResType());
        assertEquals(ResourceType.COIN, gigi.getShelves().get(4).getResType());


    }

    @Test
    @DisplayName("Corner cases for swap")
    public void cornerSwap()  {
        controller.getGame().getPlayerById(0).getPersonalBoard().getWarehouse().getShelves().get(3).setResType(ResourceType.SHIELD);

        game.addResourceToWarehouse(0, 0, ResourceType.SHIELD);
        game.swapShelvesByID(0,3, 0);
        game.addResourceToWarehouse(0, 3, ResourceType.COIN );
        game.addResourceToWarehouse(0, 0, ResourceType.SHIELD );
        game.swapShelvesByID(1,3, 0);

        ShelfWarehouse gigi = controller.getGame().getPlayerById(0).getPersonalBoard().getWarehouse();
        //         ----SIZES------
        assertEquals(1, gigi.getShelves().get(0).getCount());
        assertEquals(0, gigi.getShelves().get(1).getCount());
        assertEquals(0, gigi.getShelves().get(2).getCount());
        assertEquals(1, gigi.getShelves().get(3).getCount());
        assertEquals(0, gigi.getShelves().get(4).getCount());


//        ---RES------

        assertEquals(ResourceType.SHIELD, gigi.getShelves().get(0).getResType());
        assertEquals(ResourceType.EMPTY, gigi.getShelves().get(1).getResType());
        assertEquals(ResourceType.EMPTY, gigi.getShelves().get(2).getResType());
        assertEquals(ResourceType.SHIELD, gigi.getShelves().get(3).getResType());
        assertNull(gigi.getShelves().get(4).getResType());



        game.getPlayerById(0).getPersonalBoard().getWarehouse().getShelves().get(0).setCount(0);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().getShelves().get(0).setResType(ResourceType.EMPTY);
        game.swapShelvesByID(1,3, 0);
        gigi = controller.getGame().getPlayerById(0).getPersonalBoard().getWarehouse();
        //         ----SIZES------
        assertEquals(0, gigi.getShelves().get(0).getCount());
        assertEquals(1, gigi.getShelves().get(1).getCount());
        assertEquals(0, gigi.getShelves().get(2).getCount());
        assertEquals(0, gigi.getShelves().get(3).getCount());
        assertEquals(0, gigi.getShelves().get(4).getCount());


//        ---RES------

        assertEquals(ResourceType.EMPTY, gigi.getShelves().get(0).getResType());
        assertEquals(ResourceType.SHIELD, gigi.getShelves().get(1).getResType());
        assertEquals(ResourceType.EMPTY, gigi.getShelves().get(2).getResType());
        assertEquals(ResourceType.SHIELD, gigi.getShelves().get(3).getResType());
        assertNull(gigi.getShelves().get(4).getResType());


        controller.swapShelves(3,4,0);
        game.addResourceToWarehouse(0, 4, ResourceType.COIN);
        game.addResourceToWarehouse(0, 4, ResourceType.SHIELD);
        controller.swapShelves(4,3,0);
        game.addResourceToWarehouse(0, 3, ResourceType.SHIELD);
        controller.swapShelves(3,4,0);

        controller.swapShelves(1,0,0);
        controller.swapShelves(0,4,0);
        controller.swapShelves(4,0,0);
        gigi = controller.getGame().getPlayerById(0).getPersonalBoard().getWarehouse();
        //         ----SIZES------
        assertEquals(1, gigi.getShelves().get(0).getCount());
        assertEquals(0, gigi.getShelves().get(1).getCount());
        assertEquals(0, gigi.getShelves().get(2).getCount());
        assertEquals(0, gigi.getShelves().get(3).getCount());
        assertEquals(2, gigi.getShelves().get(4).getCount());


//        ---RES------

        assertEquals(ResourceType.SHIELD, gigi.getShelves().get(0).getResType());
        assertEquals(ResourceType.EMPTY, gigi.getShelves().get(1).getResType());
        assertEquals(ResourceType.EMPTY, gigi.getShelves().get(2).getResType());
        assertNull(gigi.getShelves().get(3).getResType());
        assertEquals(ResourceType.SHIELD, gigi.getShelves().get(4).getResType());












    }


    @Test
    @DisplayName("DISCARD RES")
    public void discardRes()  {


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
        controller.setNickname(new Nickname("lea", 1));
        controller.getGame().setTurn(0,ActionPhase.WAITING_FOR_ACTION);
        controller.getGame().endTurn(0);
        assertEquals(1, controller.getGame().getTurn().getPlayerPlayingID());
    }

    @Test
    @DisplayName("End turn with update")
    public void endTurnUpdate() {


        controller.update(new Nickname("lea", 1));
        controller.getGame().setTurn(0, ActionPhase.WAITING_FOR_ACTION);
        controller.update(new EndTurnMessage(0));
        assertEquals(1, controller.getGame().getTurn().getPlayerPlayingID());

    }
    @Test
    @DisplayName("Initial phase")
    public void initialPhase()  {
        controller.setNickname(new Nickname("GIGI" ,2));
        controller.setNickname(new Nickname("GiGi", 3));

        controller.initialPhase();
        assertEquals(0, controller.getGame().getPlayers().get(0).getPersonalBoard().getFaithTrack().getMarker());
        assertEquals(0,controller.getGame().getPlayers().get(0).getStartResCount() );
        assertEquals(2,controller.getGame().getPlayers().get(0).getLeaderCardsToDiscard());
        assertEquals(0, controller.getGame().getPlayers().get(1).getPersonalBoard().getFaithTrack().getMarker());
        assertEquals(1,controller.getGame().getPlayers().get(1).getStartResCount() );
        assertEquals(2,controller.getGame().getPlayers().get(1).getLeaderCardsToDiscard());
        assertEquals(1, controller.getGame().getPlayers().get(2).getPersonalBoard().getFaithTrack().getMarker());
        assertEquals(1,controller.getGame().getPlayers().get(2).getStartResCount() );
        assertEquals(2,controller.getGame().getPlayers().get(2).getLeaderCardsToDiscard());
        assertEquals(1, controller.getGame().getPlayers().get(3).getPersonalBoard().getFaithTrack().getMarker());
        assertEquals(2,controller.getGame().getPlayers().get(3).getStartResCount() );
        assertEquals(2,controller.getGame().getPlayers().get(3).getLeaderCardsToDiscard());

    }

    @Test
    @DisplayName("distributing leader Cards")
    public void distributingLeaders() {

        controller.setNickname(new Nickname("GiGi", 2));
        controller.setNickname(new Nickname("gigI", 3));
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
    @DisplayName("Give Leader Test")
    public void giveLeader(){
        LeaderDeck leaderDeck = game.getBoard().getLeaderDeck().clone();
        game.getPlayers().add(new Player(2, "gogo"));
        game.getPlayers().add(new Player(3, "gugu"));


        game.giveLeaderCards();

        assertEquals(game.getPlayerById(0).getLeaderDeck().getCards().get(0), leaderDeck.getCards().get(0));
        assertEquals(game.getPlayerById(0).getLeaderDeck().getCards().get(1), leaderDeck.getCards().get(1));
        assertEquals(game.getPlayerById(0).getLeaderDeck().getCards().get(2), leaderDeck.getCards().get(2));
        assertEquals(game.getPlayerById(0).getLeaderDeck().getCards().get(3), leaderDeck.getCards().get(3));

        assertEquals(game.getPlayerById(1).getLeaderDeck().getCards().get(0), leaderDeck.getCards().get(4));
        assertEquals(game.getPlayerById(1).getLeaderDeck().getCards().get(1), leaderDeck.getCards().get(5));
        assertEquals(game.getPlayerById(1).getLeaderDeck().getCards().get(2), leaderDeck.getCards().get(6));
        assertEquals(game.getPlayerById(1).getLeaderDeck().getCards().get(3), leaderDeck.getCards().get(7));

        assertEquals(game.getPlayerById(2).getLeaderDeck().getCards().get(0), leaderDeck.getCards().get(8));
        assertEquals(game.getPlayerById(2).getLeaderDeck().getCards().get(1), leaderDeck.getCards().get(9));
        assertEquals(game.getPlayerById(2).getLeaderDeck().getCards().get(2), leaderDeck.getCards().get(10));
        assertEquals(game.getPlayerById(2).getLeaderDeck().getCards().get(3), leaderDeck.getCards().get(11));

        assertEquals(game.getPlayerById(3).getLeaderDeck().getCards().get(0), leaderDeck.getCards().get(12));
        assertEquals(game.getPlayerById(3).getLeaderDeck().getCards().get(1), leaderDeck.getCards().get(13));
        assertEquals(game.getPlayerById(3).getLeaderDeck().getCards().get(2), leaderDeck.getCards().get(14));
        assertEquals(game.getPlayerById(3).getLeaderDeck().getCards().get(3), leaderDeck.getCards().get(15));

    }

    @Test
    @DisplayName("Test check dev card number")
    public void handleChosenCard(){
        game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.COIN, 50);
        game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.STONE, 50);
        game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.SERVANT, 50);
        game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.SHIELD, 50);
        controller.handleChosenDevCard(1, 0);
        controller.payRes(true, 0, ActionPhase.PAYMENT);
        controller.payRes(false, 0, ActionPhase.PAYMENT);
        controller.placeDevCard(0, 0);
        controller.handleChosenDevCard(1, 0);
        controller.payRes(false, 0, ActionPhase.PAYMENT);
        controller.placeDevCard(0, 0);
        controller.placeDevCard(0, 1);
        controller.handleChosenDevCard(4, 0);
        controller.payRes(false, 0, ActionPhase.PAYMENT);
        controller.placeDevCard(0, 0);
        controller.handleChosenDevCard(4, 0);
        controller.payRes(false, 0, ActionPhase.PAYMENT);
        controller.placeDevCard(0, 2);
        controller.placeDevCard(0, 1);
        controller.handleChosenDevCard(8, 0);
        controller.placeDevCard(0, 2);
        controller.placeDevCard(0, 0);

        assertEquals(5, game.getPlayerById(0).getPersonalBoard().getCardSlot().get(0).getCards().size()
                + game.getPlayerById(0).getPersonalBoard().getCardSlot().get(1).getCards().size()
                + game.getPlayerById(0).getPersonalBoard().getCardSlot().get(2).getCards().size());

    }

    @Test
    @DisplayName("Basic Production Test")
    public void basicProdTest(){
        game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.COIN, 50);
        game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.STONE, 50);
        game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.SERVANT, 50);
        game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.SHIELD, 50);

        game.getTurn().setPhase(ActionPhase.WAITING_FOR_ACTION);
        controller.update(new BasicProductionMessage(ResourceType.COIN, ResourceType.SERVANT, null, 0, false));
        game.getTurn().setPhase(ActionPhase.BASIC);

        controller.update(new BasicProductionMessage(null, null, ResourceType.STONE, 0, false));
        game.getTurn().setPhase(ActionPhase.PAYMENT);
        controller.update(new BasicProductionMessage(null, null, null, 0, false));
        controller.update(new BasicProductionMessage(null, null, null, 0, false));
        controller.collectNewRes(0);

        assertEquals(49, game.getPlayerById(0).getPersonalBoard().getStrongbox().getResCount(ResourceType.COIN));
        assertEquals(49, game.getPlayerById(0).getPersonalBoard().getStrongbox().getResCount(ResourceType.SERVANT));
        assertEquals(51, game.getPlayerById(0).getPersonalBoard().getStrongbox().getResCount(ResourceType.STONE));

    }

    @Test
    @DisplayName("initial res")
    public void initialRes()  {

        game.getPlayerById(0).setStartResCount(1);
        game.getPlayerById(0).setLeaderCardsToDiscard(0);
        controller.placeRes(ResourceType.COIN, 0, 0, false, true );
        assertEquals(0, game.getPlayerById(0).getStartResCount());
        assertEquals(ResourceType.COIN, game.getPlayerById(0).getPersonalBoard().getWarehouse().getShelves().get(0).getResType());
        assertEquals(1, game.getPlayerById(0).getPersonalBoard().getWarehouse().getShelves().get(0).getCount());
    }












}
