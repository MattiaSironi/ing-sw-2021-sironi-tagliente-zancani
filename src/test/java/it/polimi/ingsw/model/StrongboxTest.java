package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Mattia Sironi, Lea Zancani, Simone Tagliente
 * @see Strongbox
 */
public class StrongboxTest {
    private Strongbox s;


    @BeforeEach
    void init() {
        s = new Strongbox();
    }


    @Test
    @DisplayName("questo è il nostro primo test")
    void firstTest() {

        Strongbox s = new Strongbox();
        s.getInfinityShelf().get(0).setCount(5);
        s.getInfinityShelf().get(1).setCount(5);
        s.getInfinityShelf().get(2).setCount(5);
        s.getInfinityShelf().get(3).setCount(5);
        assertEquals(5, s.getResCount(ResourceType.COIN));
        assertEquals(5, s.getResCount(ResourceType.STONE));
        assertEquals(5, s.getResCount(ResourceType.SERVANT));
        assertEquals(5, s.getResCount(ResourceType.SHIELD));


    }

    @Test
    @DisplayName("questo è il nostro secondo test")
    void secondTest() {

        s.getInfinityShelf().get(0).setCount(5);
        s.getInfinityShelf().get(1).setCount(5);
        s.getInfinityShelf().get(2).setCount(5);
        s.getInfinityShelf().get(3).setCount(5);
        s.pay(3, ResourceType.STONE);
        s.pay(2, ResourceType.COIN);
        assertEquals(3, s.getResCount(ResourceType.COIN));
        assertEquals(2, s.getResCount(ResourceType.STONE));
        assertEquals(5, s.getResCount(ResourceType.SERVANT));
        assertEquals(5, s.getResCount(ResourceType.SHIELD));
    }




}
