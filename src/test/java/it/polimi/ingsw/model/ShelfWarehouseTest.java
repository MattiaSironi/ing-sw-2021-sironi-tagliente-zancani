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
    private ShelfWarehouse swh;

    @BeforeEach
    void init() {
        swh = new ShelfWarehouse();
    }

    @Test
    @DisplayName("first shelf busy.")
    void firstShelfBusy() {
        swh.addResource(ResourceType.COIN, 0);
        swh.addResource(ResourceType.COIN, 0);
        assertEquals(1, swh.getResCount(ResourceType.COIN));
        swh.pay(1, ResourceType.COIN);
        assertEquals(0, swh.getResCount(ResourceType.COIN));
    }

    @Test
    @DisplayName("all shelves busy for a new ResourceType")
    void sorryWeClosed() {
        swh.addResource(ResourceType.SERVANT, 0);
        swh.addResource(ResourceType.COIN, 1);
        swh.addResource(ResourceType.STONE, 2);
        swh.addResource(ResourceType.COIN, 1);
        swh.addResource(ResourceType.STONE, 2);
        swh.addResource(ResourceType.SERVANT, 0);
        swh.addResource(ResourceType.SHIELD, 2);

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
        swh.addResource(ResourceType.COIN, 0);           //         COIN
        swh.addResource(ResourceType.SERVANT, 1);        //     SERVANT SERVANT
        swh.addResource(ResourceType.SERVANT, 1);        //    SHIELD SHIELD EMPTY
        swh.addResource(ResourceType.SHIELD, 2);
        swh.addResource(ResourceType.SHIELD, 2);

        swh.swapShelves(2, 1);                                     //        COIN
        swh.swapShelves(1, 2);                                     //    SHIELD SHIELD
        swh.swapShelves(2, 1);                                     // SERVANT SERVANT EMPTY

        swh.pay(1, ResourceType.SERVANT);

        swh.swapShelves(2, 0);                                     //       SERVANT
        swh.swapShelves(2, 0);                                     //   SHIELD SHIELD
        swh.swapShelves(2, 0);                                     // COIN COIN EMPTY

        swh.swapShelves(1, 0);
        swh.addResource(ResourceType.COIN, 2);


        assertEquals(2, swh.getResCount(ResourceType.COIN));
        assertEquals(1, swh.getResCount(ResourceType.SERVANT));
        assertEquals(0, swh.getResCount(ResourceType.STONE));
        assertEquals(2, swh.getResCount(ResourceType.SHIELD));

        swh.pay(1, ResourceType.SERVANT);                     //       COIN
        swh.pay(1, ResourceType.COIN);                        //    STONE STONE
        swh.swapShelves(0, 2);                                    // SHIELD SHIELD EMPTY
        swh.swapShelves(1, 2);
        swh.addResource(ResourceType.STONE, 1);
        swh.addResource(ResourceType.STONE, 1);

        assertEquals(1, swh.getResCount(ResourceType.COIN));
        assertEquals(0, swh.getResCount(ResourceType.SERVANT));
        assertEquals(2, swh.getResCount(ResourceType.STONE));
        assertEquals(2, swh.getResCount(ResourceType.SHIELD));


    }
    @Test
    @DisplayName("swapping third with one")
    void swappietwo() {
        swh.addResource(ResourceType.SHIELD, 2);
        swh.addResource(ResourceType.SHIELD, 2);
        swh.addResource(ResourceType.SHIELD, 2);
        swh.addResource(ResourceType.SHIELD, 2);

        swh.swapShelves(0, 2);
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