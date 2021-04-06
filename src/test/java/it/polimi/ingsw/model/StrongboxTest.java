package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 * @author Mattia Sironi, Lea Zancani, Simone Tagliente
 * @see Strongbox
 */
public class StrongboxTest {


    @Test
    @DisplayName("questo è il primo nostro test")
    void firstTest()  {

        Strongbox s= new Strongbox();
        s.getInfinityShelf().get(0).setCount(5);
        s.getInfinityShelf().get(1).setCount(5);
        s.getInfinityShelf().get(2).setCount(5);
        s.getInfinityShelf().get(3).setCount(5);
        assertEquals(5, s.getResCount(ResourceType.COIN));
        assertEquals(5, s.getResCount(ResourceType.STONE));
        assertEquals(5, s.getResCount(ResourceType.SERVANT));
        assertEquals(5, s.getResCount(ResourceType.SHIELD));


    }

}
