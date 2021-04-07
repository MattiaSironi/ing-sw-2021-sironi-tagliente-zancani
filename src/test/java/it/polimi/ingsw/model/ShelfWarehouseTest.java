package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Mattia Sironi, Lea Zancani, Simone Tagliente
 * @see ShelfWarehouse
 */
public class ShelfWarehouseTest {


    @Test
    @DisplayName("#1 Test of getResCount")
    void testGetResCount_1() {
        ShelfWarehouse s = new ShelfWarehouse();
        s.getShelves().get(0).setCount(1);
        s.getShelves().get(0).setResType(ResourceType.COIN);
        assertEquals(1, s.getResCount(ResourceType.COIN));
        assertEquals(0, s.getResCount(ResourceType.STONE));
        assertEquals(0, s.getResCount(ResourceType.SERVANT));
        assertEquals(0, s.getResCount(ResourceType.SHIELD));

    }

    @Test
    @DisplayName("#2 Test of getResCount")
    void testGetResCount_2() {
        ShelfWarehouse s = new ShelfWarehouse();
        s.getShelves().get(1).setCount(2);
        s.getShelves().get(1).setResType(ResourceType.COIN);
        assertEquals(2, s.getResCount(ResourceType.COIN));
        assertEquals(0, s.getResCount(ResourceType.STONE));
        assertEquals(0, s.getResCount(ResourceType.SERVANT));
        assertEquals(0, s.getResCount(ResourceType.SHIELD));

    }

    @Test
    @DisplayName("#3 Test of getResCount")
    void testGetResCount_3() {
        ShelfWarehouse s = new ShelfWarehouse();
        s.getShelves().get(2).setCount(3);
        s.getShelves().get(2).setResType(ResourceType.STONE);
        s.getShelves().get(0).setCount(1);
        s.getShelves().get(0).setResType(ResourceType.COIN);
        assertEquals(1, s.getResCount(ResourceType.COIN));
        assertEquals(3, s.getResCount(ResourceType.STONE));
        assertEquals(0, s.getResCount(ResourceType.SERVANT));
        assertEquals(0, s.getResCount(ResourceType.SHIELD));

    }

    @Test
    @DisplayName("#1 Test of swapShelves")
    void testSwapShelves_1() {
        ShelfWarehouse s = new ShelfWarehouse();
        s.getShelves().get(2).setCount(1);
        s.getShelves().get(2).setResType(ResourceType.STONE);
        s.getShelves().get(0).setCount(1);
        s.getShelves().get(0).setResType(ResourceType.COIN);
        s.swapShelves(0, 2);
        assertEquals(1, s.getShelves().get(0).getCount());
        assertEquals(1, s.getShelves().get(2).getCount());
        assertEquals(ResourceType.STONE, s.getShelves().get(0).getResType());
        assertEquals(ResourceType.COIN, s.getShelves().get(2).getResType());
    }

    @Test
    @DisplayName("#2 Test of swapShelves")
    void testSwapShelves_2() {
        ShelfWarehouse s = new ShelfWarehouse();
        s.getShelves().get(2).setCount(2);
        s.getShelves().get(2).setResType(ResourceType.STONE);
        s.getShelves().get(1).setCount(2);
        s.getShelves().get(1).setResType(ResourceType.COIN);
        s.swapShelves(1, 2);
        assertEquals(2, s.getShelves().get(1).getCount());
        assertEquals(2, s.getShelves().get(2).getCount());
        assertEquals(ResourceType.STONE, s.getShelves().get(1).getResType());
        assertEquals(ResourceType.COIN, s.getShelves().get(2).getResType());
    }

    @Test
    @DisplayName("#3 Test of swapShelves")
    void testSwapShelves_3() {
        ShelfWarehouse s = new ShelfWarehouse();
        s.getShelves().get(0).setCount(1);
        s.getShelves().get(0).setResType(ResourceType.COIN);
        s.getShelves().get(1).setCount(1);
        s.getShelves().get(1).setResType(ResourceType.STONE);
        s.getShelves().get(2).setCount(2);
        s.getShelves().get(2).setResType(ResourceType.SERVANT);
        s.swapShelves(0, 1);
        s.swapShelves(1, 2);

        assertEquals(1, s.getShelves().get(0).getCount());
        assertEquals(ResourceType.STONE, s.getShelves().get(0).getResType());

        assertEquals(2, s.getShelves().get(1).getCount());
        assertEquals(ResourceType.SERVANT, s.getShelves().get(1).getResType());

        assertEquals(1, s.getShelves().get(2).getCount());
        assertEquals(ResourceType.COIN, s.getShelves().get(2).getResType());


    }

    @Test
    @DisplayName("#4 Test of swapShelves")
    void testSwapShelves_4() {
        ShelfWarehouse s = new ShelfWarehouse();
        s.getShelves().get(0).setCount(1);
        s.getShelves().get(0).setResType(ResourceType.COIN);
        s.swapShelves(0, 2);
        s.swapShelves(2, 1);
        s.swapShelves(1, 2);
        s.swapShelves(2, 0);
        s.swapShelves(0, 2);
        assertEquals(0, s.getShelves().get(0).getCount());
        assertEquals(ResourceType.EMPTY, s.getShelves().get(0).getResType());
        assertEquals(0, s.getShelves().get(1).getCount());
        assertEquals(ResourceType.EMPTY, s.getShelves().get(1).getResType());
        assertEquals(1, s.getShelves().get(2).getCount());
        assertEquals(ResourceType.COIN, s.getShelves().get(2).getResType());
    }

    @Test
    @DisplayName("#1 Test of addResource")
    void testAddResource_1() {
        ShelfWarehouse s = new ShelfWarehouse();
        assertTrue(s.addResource(ResourceType.COIN, 0));
        assertEquals(1, s.getShelves().get(0).getCount());
        assertEquals(ResourceType.COIN, s.getShelves().get(0).getResType());
    }

    @Test
    @DisplayName("#2 Test of addResource")
    void testAddResource_2() {
        ShelfWarehouse s = new ShelfWarehouse();
        assertTrue(s.addResource(ResourceType.COIN, 0));
        assertEquals(1, s.getShelves().get(0).getCount());
        assertEquals(ResourceType.COIN, s.getShelves().get(0).getResType());
        assertFalse(s.addResource(ResourceType.COIN, 0));
    }

    @Test
    @DisplayName("#3 Test of addResource")
    void testAddResource_3() {
        ShelfWarehouse s = new ShelfWarehouse();
        assertTrue(s.addResource(ResourceType.COIN, 0));
        assertEquals(1, s.getShelves().get(0).getCount());
        assertEquals(ResourceType.COIN, s.getShelves().get(0).getResType());
        assertFalse(s.addResource(ResourceType.COIN, 2));
    }

    @Test
    @DisplayName("#4 Test of addResource")
    void testAddResource_4() {
        ShelfWarehouse s = new ShelfWarehouse();
        assertTrue(s.addResource(ResourceType.COIN, 2));
        assertFalse(s.addResource(ResourceType.STONE, 2));
    }

}