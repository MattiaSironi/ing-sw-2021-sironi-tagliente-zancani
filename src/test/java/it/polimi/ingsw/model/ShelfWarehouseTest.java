package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Mattia Sironi, Lea Zancani, Simone Tagliente
 * @see ShelfWarehouse
 */
public class ShelfWarehouseTest {

    private Game game;
    private ShelfWarehouse swh;

    @BeforeEach
    void init() {
        game = new Game(true, 0);
        game.getPlayers().add(new Player(0, "gigi"));
        game.setNumPlayer(1);
         swh = game.getPlayerById(0).getPersonalBoard().getWarehouse();


    }

    @Test
    @DisplayName("first shelf busy.")
    void firstShelfBusy() {
        game.addResourceToWarehouse(0,0,ResourceType.COIN);
        game.addResourceToWarehouse(0,0,ResourceType.COIN);
        assertEquals(1, swh.getResCount(ResourceType.COIN));
        swh.pay(1, ResourceType.COIN);
        assertEquals(0, swh.getResCount(ResourceType.COIN));
    }

    @Test
    @DisplayName("all shelves busy for a new ResourceType")
    void sorryWeClosed() {
        game.addResourceToWarehouse(0,0, ResourceType.SERVANT);
        game.addResourceToWarehouse(0,1, ResourceType.COIN);
        game.addResourceToWarehouse(0,2, ResourceType.STONE);
        game.addResourceToWarehouse(0,1, ResourceType.COIN);
        game.addResourceToWarehouse(0,2, ResourceType.STONE);
        game.addResourceToWarehouse(0,0, ResourceType.SERVANT);
        game.addResourceToWarehouse(0,2, ResourceType.SHIELD);


        assertEquals(1, swh.getResCount(ResourceType.SERVANT));
        assertEquals(2, swh.getResCount(ResourceType.COIN));
        assertEquals(2, swh.getResCount(ResourceType.STONE));
        assertEquals(0, swh.getResCount(ResourceType.SHIELD));

        swh.pay(1, ResourceType.SERVANT);
        swh.addResource(ResourceType.SHIELD, 0);

        assertEquals(0, swh.getResCount(ResourceType.SERVANT));
        assertEquals(2, swh.getResCount(ResourceType.COIN));
        assertEquals(2, swh.getResCount(ResourceType.STONE));
        assertEquals(1, swh.getResCount(ResourceType.SHIELD));


    }

    @Test
    @DisplayName("trying to pay with resources that I don't have")
    void sorryYouBroke() {

        swh.addResource(ResourceType.COIN, 0);
        swh.addResource(ResourceType.COIN, 1);
        swh.addResource(ResourceType.COIN, 2);

        assertEquals(1, swh.getResCount(ResourceType.COIN));
        assertEquals(0, swh.getResCount(ResourceType.SERVANT));
        assertEquals(0, swh.getResCount(ResourceType.STONE));
        assertEquals(0, swh.getResCount(ResourceType.SHIELD));

        swh.pay(2, ResourceType.SERVANT);
        swh.pay(1, ResourceType.STONE);
        swh.pay(3, ResourceType.STONE);
        swh.pay(1, ResourceType.COIN);
        swh.pay(1, ResourceType.COIN);

        assertEquals(0, swh.getResCount(ResourceType.COIN));
        assertEquals(0, swh.getResCount(ResourceType.SERVANT));
        assertEquals(0, swh.getResCount(ResourceType.STONE));
        assertEquals(0, swh.getResCount(ResourceType.SHIELD));

    }

    @Test
    @DisplayName("swapping shelves")
    void swappie() {


        game.addResourceToWarehouse(0,0,ResourceType.COIN);
        game.addResourceToWarehouse(0,1,ResourceType.SERVANT);
        game.addResourceToWarehouse(0,1,ResourceType.SERVANT);
        game.addResourceToWarehouse(0,2,ResourceType.SHIELD);
        game.addResourceToWarehouse(0,2,ResourceType.SHIELD);


        game.swapShelvesByID(2, 1, 0);            //        COIN
        game.swapShelvesByID(1,2,0);           //    SHIELD SHIELD
        game.swapShelvesByID(2,1,0);             // SERVANT SERVANT EMPTY

        game.getPlayerById(0).getPersonalBoard().getWarehouse().pay(1, ResourceType.SERVANT);



        game.swapShelvesByID(2, 0, 0);        //       SERVANT
        game.swapShelvesByID(2, 0, 0);        //   SHIELD SHIELD
        game.swapShelvesByID(2, 0, 0);        // COIN COIN EMPTY

        game.swapShelvesByID(1, 0, 0);

        game.addResourceToWarehouse(0,2,ResourceType.COIN);


        swh = game.getPlayerById(0).getPersonalBoard().getWarehouse();
        assertEquals(2, swh.getResCount(ResourceType.COIN));
        assertEquals(1, swh.getResCount(ResourceType.SERVANT));
        assertEquals(0, swh.getResCount(ResourceType.STONE));
        assertEquals(2, swh.getResCount(ResourceType.SHIELD));

        game.getPlayerById(0).getPersonalBoard().getWarehouse().pay(1, ResourceType.SERVANT);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().pay(1, ResourceType.COIN);
        game.swapShelvesByID(0, 2, 0);                                     // SHIELD SHIELD EMPTY
        game.swapShelvesByID(1, 2, 0);

        game.addResourceToWarehouse(0,1,ResourceType.STONE);
        game.addResourceToWarehouse(0,1,ResourceType.STONE);

        swh = game.getPlayerById(0).getPersonalBoard().getWarehouse();

        assertEquals(1, swh.getResCount(ResourceType.COIN));
        assertEquals(0, swh.getResCount(ResourceType.SERVANT));
        assertEquals(2, swh.getResCount(ResourceType.STONE));
        assertEquals(2, swh.getResCount(ResourceType.SHIELD));


    }
    @Test
    @DisplayName("swapping third with one")
    void swappietwo() {
        game.addResourceToWarehouse(0,2,ResourceType.SHIELD);
        game.addResourceToWarehouse(0,2,ResourceType.SHIELD);
        game.addResourceToWarehouse(0,2,ResourceType.SHIELD);
        game.addResourceToWarehouse(0,2,ResourceType.SHIELD);
        game.swapShelvesByID(1, 2, 0);
        game.swapShelvesByID(0, 2, 0);
        game.swapShelvesByID(2, 0, 0);

        swh = game.getPlayerById(0).getPersonalBoard().getWarehouse();
        assertEquals(0, swh.getResCount(ResourceType.COIN));
        assertEquals(0, swh.getResCount(ResourceType.SERVANT));
        assertEquals(0, swh.getResCount(ResourceType.STONE));
        assertEquals(3, swh.getResCount(ResourceType.SHIELD));
    }

    @Test
    @DisplayName("#4 Test of addResource")
    void testAddResource_4() {

        swh.addResource(ResourceType.COIN, 2);
        swh.addResource(ResourceType.STONE, 2);
        assertEquals(1, swh.getResCount(ResourceType.COIN));
        assertEquals(0, swh.getResCount(ResourceType.STONE));
    }


    }








//
//    @Test
//    @DisplayName("#1 Test of getResCount")
//    void testGetResCount_1() {
//        ShelfWarehouse s = new ShelfWarehouse();
//        s.getShelves().get(0).setCount(1);
//        s.getShelves().get(0).setResType(ResourceType.COIN);
//        assertEquals(1, s.getResCount(ResourceType.COIN));
//        assertEquals(0, s.getResCount(ResourceType.STONE));
//        assertEquals(0, s.getResCount(ResourceType.SERVANT));
//        assertEquals(0, s.getResCount(ResourceType.SHIELD));
//
//    }
//
//    @Test
//    @DisplayName("#2 Test of getResCount")
//    void testGetResCount_2() {
//        ShelfWarehouse s = new ShelfWarehouse();
//        s.getShelves().get(1).setCount(2);
//        s.getShelves().get(1).setResType(ResourceType.COIN);
//        assertEquals(2, s.getResCount(ResourceType.COIN));
//        assertEquals(0, s.getResCount(ResourceType.STONE));
//        assertEquals(0, s.getResCount(ResourceType.SERVANT));
//        assertEquals(0, s.getResCount(ResourceType.SHIELD));
//
//    }
//
//    @Test
//    @DisplayName("#3 Test of getResCount")
//    void testGetResCount_3() {
//        ShelfWarehouse s = new ShelfWarehouse();
//        s.getShelves().get(2).setCount(3);
//        s.getShelves().get(2).setResType(ResourceType.STONE);
//        s.getShelves().get(0).setCount(1);
//        s.getShelves().get(0).setResType(ResourceType.COIN);
//        assertEquals(1, s.getResCount(ResourceType.COIN));
//        assertEquals(3, s.getResCount(ResourceType.STONE));
//        assertEquals(0, s.getResCount(ResourceType.SERVANT));
//        assertEquals(0, s.getResCount(ResourceType.SHIELD));
//
//    }
//
//    @Test
//    @DisplayName("#1 Test of swapShelves")
//    void testSwapShelves_1() {
//        ShelfWarehouse s = new ShelfWarehouse();
//        s.getShelves().get(2).setCount(1);
//        s.getShelves().get(2).setResType(ResourceType.STONE);
//        s.getShelves().get(0).setCount(1);
//        s.getShelves().get(0).setResType(ResourceType.COIN);
//        s.swapShelves(0, 2);
//        assertEquals(1, s.getShelves().get(0).getCount());
//        assertEquals(1, s.getShelves().get(2).getCount());
//        assertEquals(ResourceType.STONE, s.getShelves().get(0).getResType());
//        assertEquals(ResourceType.COIN, s.getShelves().get(2).getResType());
//    }
//
//    @Test
//    @DisplayName("#2 Test of swapShelves")
//    void testSwapShelves_2() {
//        ShelfWarehouse s = new ShelfWarehouse();
//        s.getShelves().get(2).setCount(2);
//        s.getShelves().get(2).setResType(ResourceType.STONE);
//        s.getShelves().get(1).setCount(2);
//        s.getShelves().get(1).setResType(ResourceType.COIN);
//        s.swapShelves(1, 2);
//        assertEquals(2, s.getShelves().get(1).getCount());
//        assertEquals(2, s.getShelves().get(2).getCount());
//        assertEquals(ResourceType.STONE, s.getShelves().get(1).getResType());
//        assertEquals(ResourceType.COIN, s.getShelves().get(2).getResType());
//    }
//
//    @Test
//    @DisplayName("#3 Test of swapShelves")
//    void testSwapShelves_3() {
//        ShelfWarehouse s = new ShelfWarehouse();
//        s.getShelves().get(0).setCount(1);
//        s.getShelves().get(0).setResType(ResourceType.COIN);
//        s.getShelves().get(1).setCount(1);
//        s.getShelves().get(1).setResType(ResourceType.STONE);
//        s.getShelves().get(2).setCount(2);
//        s.getShelves().get(2).setResType(ResourceType.SERVANT);
//        s.swapShelves(0, 1);
//        s.swapShelves(1, 2);
//
//        assertEquals(1, s.getShelves().get(0).getCount());
//        assertEquals(ResourceType.STONE, s.getShelves().get(0).getResType());
//
//        assertEquals(2, s.getShelves().get(1).getCount());
//        assertEquals(ResourceType.SERVANT, s.getShelves().get(1).getResType());
//
//        assertEquals(1, s.getShelves().get(2).getCount());
//        assertEquals(ResourceType.COIN, s.getShelves().get(2).getResType());
//
//
//    }
//
//    @Test
//    @DisplayName("#4 Test of swapShelves")
//    void testSwapShelves_4() {
//        ShelfWarehouse s = new ShelfWarehouse();
//        s.getShelves().get(0).setCount(1);
//        s.getShelves().get(0).setResType(ResourceType.COIN);
//        s.swapShelves(0, 2);
//        s.swapShelves(2, 1);
//        s.swapShelves(1, 2);
//        s.swapShelves(2, 0);
//        s.swapShelves(0, 2);
//        assertEquals(0, s.getShelves().get(0).getCount());
//        assertEquals(ResourceType.EMPTY, s.getShelves().get(0).getResType());
//        assertEquals(0, s.getShelves().get(1).getCount());
//        assertEquals(ResourceType.EMPTY, s.getShelves().get(1).getResType());
//        assertEquals(1, s.getShelves().get(2).getCount());
//        assertEquals(ResourceType.COIN, s.getShelves().get(2).getResType());
//    }
//
//    @Test
//    @DisplayName("#1 Test of addResource")
//    void testAddResource_1() {
//        ShelfWarehouse s = new ShelfWarehouse();
//        assertTrue(s.addResource(ResourceType.COIN, 0));
//        assertEquals(1, s.getShelves().get(0).getCount());
//        assertEquals(ResourceType.COIN, s.getShelves().get(0).getResType());
//    }
//
//    @Test
//    @DisplayName("#2 Test of addResource")
//    void testAddResource_2() {
//        ShelfWarehouse s = new ShelfWarehouse();
//        assertTrue(s.addResource(ResourceType.COIN, 0));
//        assertEquals(1, s.getShelves().get(0).getCount());
//        assertEquals(ResourceType.COIN, s.getShelves().get(0).getResType());
//        assertFalse(s.addResource(ResourceType.COIN, 0));
//    }
//
//    @Test
//    @DisplayName("#3 Test of addResource")
//    void testAddResource_3() {
//        ShelfWarehouse s = new ShelfWarehouse();
//        assertTrue(s.addResource(ResourceType.COIN, 0));
//        assertEquals(1, s.getShelves().get(0).getCount());
//        assertEquals(ResourceType.COIN, s.getShelves().get(0).getResType());
//        assertFalse(s.addResource(ResourceType.COIN, 2));
//    }
//
//    @Test
//    @DisplayName("#4 Test of addResource")
//    void testAddResource_4() {
//        ShelfWarehouse s = new ShelfWarehouse();
//        assertTrue(s.addResource(ResourceType.COIN, 2));
//        assertFalse(s.addResource(ResourceType.STONE, 2));
//    }
//
//}