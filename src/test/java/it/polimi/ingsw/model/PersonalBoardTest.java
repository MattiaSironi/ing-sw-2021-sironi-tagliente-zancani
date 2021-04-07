package it.polimi.ingsw.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

/**
 * @author Mattia Sironi, Lea Zancani, Simone Tagliente
 * @see PersonalBoard
 */
public class PersonalBoardTest {
    private PersonalBoard p;

    @BeforeEach
    void init() {
        p = new PersonalBoard(new ArrayList<Slot>(), new ArrayList<DevDeck>(), new LeaderDeck(0, 1, new ArrayList<LeaderCard>()),
                0, false, false, false, new Strongbox(), new ShelfWarehouse());

    }

    @Test
    @DisplayName("Requirements meet for a DiscountLCard")
    void yesDiscount() {

        ArrayList<DevCard> dev1 = new ArrayList<DevCard>();
        dev1.add(0, new DevCard(0, 1, 1, CardColor.GREEN, new int[]{0, 0, 0, 2}, new int[]{1, 0, 0, 0}, new int[]{0, 0, 0, 0, 1}));
        p.getCardSlot().add(new DevDeck(dev1.size(), 0, dev1));
        ArrayList<DevCard> dev2 = new ArrayList<DevCard>();
        dev2.add(0, new DevCard(0, 2, 1, CardColor.PURPLE, new int[]{1, 0, 1, 1}, new int[]{1, 0, 0, 0}, new int[]{0, 0, 0, 1, 0}));
        p.getCardSlot().add(new DevDeck(dev2.size(), 0, dev2));
        ArrayList<LeaderCard> lc = new ArrayList<LeaderCard>();
        lc.add(new DiscountLCard(1, 2, CardColor.GREEN, CardColor.PURPLE, ResourceType.STONE));
        lc.add(new DiscountLCard(1, 2, CardColor.PURPLE, CardColor.GREEN, ResourceType.STONE));
        LeaderDeck ld = new LeaderDeck(lc.size(), 2, lc);
        p.setActiveLeader(ld);
        assertTrue(p.checkLCardRequirements(p.getActiveLeader().getCards().get(0)));
        assertTrue(p.checkLCardRequirements(p.getActiveLeader().getCards().get(1)));


    }

    @Test
    @DisplayName("same as above but the cards are in my PersonalBoardTest")
    void yesDiscountBut() {
        ArrayList<DevCard> dev1 = new ArrayList<DevCard>();
        dev1.add(0, new DevCard(0, 1, 1, CardColor.PURPLE, new int[]{0, 0, 0, 2}, new int[]{1, 0, 0, 0}, new int[]{0, 0, 0, 0, 1}));
        dev1.add(1, new DevCard(0, 6, 2, CardColor.PURPLE, new int[]{2, 0, 3, 0}, new int[]{1, 0, 1, 0}, new int[]{0, 0, 0, 3, 0}));
        dev1.add(2, new DevCard(0, 10, 3, CardColor.YELLOW, new int[]{0, 5, 2, 0}, new int[]{0, 1, 1, 0}, new int[]{2, 0, 0, 2, 1}));
        p.getCardSlot().add(new DevDeck(dev1.size(), 0, dev1));
        ArrayList<DevCard> dev2 = new ArrayList<DevCard>();
        dev2.add(0, new DevCard(0, 2, 1, CardColor.PURPLE, new int[]{1, 0, 1, 1}, new int[]{1, 0, 0, 0}, new int[]{0, 0, 0, 1, 0}));
        p.getCardSlot().add(new DevDeck(dev2.size(), 0, dev2));
        ArrayList<DevCard> dev3 = new ArrayList<DevCard>();
        dev3.add(0, new DevCard(0, 4, 1, CardColor.YELLOW, new int[]{2, 0, 2, 0}, new int[]{0, 1, 0, 1}, new int[]{0, 0, 2, 0, 1}));
        dev3.add(1, new DevCard(0, 6, 2, CardColor.BLUE, new int[]{3, 2, 0, 0}, new int[]{1, 1, 0, 0}, new int[]{0, 0, 3, 0, 0}));
        p.getCardSlot().add(new DevDeck(dev3.size(), 0, dev3));
        ArrayList<LeaderCard> lc = new ArrayList<LeaderCard>();
        lc.add(new DiscountLCard(1, 2, CardColor.GREEN, CardColor.BLUE, ResourceType.STONE));
        lc.add(new DiscountLCard(1, 2, CardColor.YELLOW, CardColor.PURPLE, ResourceType.COIN));
        LeaderDeck ld = new LeaderDeck(lc.size(), 2, lc);
        p.setActiveLeader(ld);
        assertFalse(p.checkLCardRequirements(p.getActiveLeader().getCards().get(0)));
        assertTrue(p.checkLCardRequirements(p.getActiveLeader().getCards().get(1)));
    }

    @Test
    @DisplayName("Requirements meet for a DiscountLCard and two ExtraProdCards but not for an ExtraProdCard due to the levels of our DevCards")
    void yesDiscountButNopeAllExtraProds() {

        ArrayList<DevCard> dev1 = new ArrayList<DevCard>();
        dev1.add(0, new DevCard(0, 1, 1, CardColor.GREEN, new int[]{0, 0, 0, 2}, new int[]{1, 0, 0, 0}, new int[]{0, 0, 0, 0, 1}));
        dev1.add(1, new DevCard(0, 6, 2, CardColor.PURPLE, new int[]{2, 0, 3, 0}, new int[]{1, 0, 1, 0}, new int[]{0, 0, 0, 3, 0}));
        dev1.add(2, new DevCard(0, 10, 3, CardColor.YELLOW, new int[]{0, 5, 2, 0}, new int[]{0, 1, 1, 0}, new int[]{2, 0, 0, 2, 1}));
        p.getCardSlot().add(new DevDeck(dev1.size(), 0, dev1));
        ArrayList<DevCard> dev2 = new ArrayList<DevCard>();
        dev2.add(0, new DevCard(0, 2, 1, CardColor.PURPLE, new int[]{1, 0, 1, 1}, new int[]{1, 0, 0, 0}, new int[]{0, 0, 0, 1, 0}));
        p.getCardSlot().add(new DevDeck(dev2.size(), 0, dev2));
        ArrayList<LeaderCard> lc = new ArrayList<LeaderCard>();
        lc.add(new DiscountLCard(1, 2, CardColor.GREEN, CardColor.YELLOW, ResourceType.STONE));
        lc.add(new ExtraProdLCard(3, 4, CardColor.PURPLE, ResourceType.STONE));
        lc.add(new ExtraProdLCard(3, 4, CardColor.GREEN, ResourceType.COIN));
        lc.add(new ExtraProdLCard(3, 4, CardColor.YELLOW, ResourceType.SHIELD)); //size of our LeaderDeck is 4 just for testing
        LeaderDeck ld = new LeaderDeck(lc.size(), 2, lc);
        p.setActiveLeader(ld);
        assertTrue(p.checkLCardRequirements(p.getActiveLeader().getCards().get(0)));
        assertTrue(p.checkLCardRequirements(p.getActiveLeader().getCards().get(1)));
        assertFalse(p.checkLCardRequirements(p.getActiveLeader().getCards().get(2)));
        assertTrue(p.checkLCardRequirements(p.getActiveLeader().getCards().get(3)));


    }

    @Test
    @DisplayName("Tests for WhiteTrayLCard")
    void yesAndNopeWhiteTray() {
        ArrayList<DevCard> dev1 = new ArrayList<DevCard>();
        dev1.add(0, new DevCard(0, 1, 1, CardColor.PURPLE, new int[]{0, 0, 0, 2}, new int[]{1, 0, 0, 0}, new int[]{0, 0, 0, 0, 1}));
        dev1.add(1, new DevCard(0, 6, 2, CardColor.PURPLE, new int[]{2, 0, 3, 0}, new int[]{1, 0, 1, 0}, new int[]{0, 0, 0, 3, 0}));
        dev1.add(2, new DevCard(0, 10, 3, CardColor.YELLOW, new int[]{0, 5, 2, 0}, new int[]{0, 1, 1, 0}, new int[]{2, 0, 0, 2, 1}));
        p.getCardSlot().add(new DevDeck(dev1.size(), 0, dev1));
        ArrayList<DevCard> dev2 = new ArrayList<DevCard>();
        dev2.add(0, new DevCard(0, 2, 1, CardColor.PURPLE, new int[]{1, 0, 1, 1}, new int[]{1, 0, 0, 0}, new int[]{0, 0, 0, 1, 0}));
        p.getCardSlot().add(new DevDeck(dev2.size(), 0, dev2));
        ArrayList<DevCard> dev3 = new ArrayList<DevCard>();
        dev3.add(0, new DevCard(0, 4, 1, CardColor.YELLOW, new int[]{2, 0, 2, 0}, new int[]{0, 1, 0, 1}, new int[]{0, 0, 2, 0, 1}));
        dev3.add(1, new DevCard(0, 6, 2, CardColor.BLUE, new int[]{3, 2, 0, 0}, new int[]{1, 1, 0, 0}, new int[]{0, 0, 3, 0, 0}));
        p.getCardSlot().add(new DevDeck(dev3.size(), 0, dev3));
        ArrayList<LeaderCard> lc = new ArrayList<LeaderCard>();
        lc.add(new WhiteTrayLCard(4, 5, ResourceType.SHIELD, CardColor.PURPLE, CardColor.GREEN));
        lc.add(new WhiteTrayLCard(4, 5, ResourceType.SERVANT, CardColor.BLUE, CardColor.YELLOW));
        LeaderDeck ld = new LeaderDeck(lc.size(), 2, lc);
        p.setActiveLeader(ld);
        assertFalse(p.checkLCardRequirements(p.getActiveLeader().getCards().get(0)));
        assertTrue(p.checkLCardRequirements(p.getActiveLeader().getCards().get(1)));

    }

    @Test
    @DisplayName("About ExtraDepotCards: enough resources in the StrongBox")
    void firstExtraDepot() {
//        p.getStrongbox().setInfinityShelf(new ArrayList<Shelf>());
//        p.getStrongbox().getInfinityShelf().add(0, new Shelf(ResourceType.COIN, 6));
//        p.getStrongbox().getInfinityShelf().add(1, new Shelf(ResourceType.STONE, 0));
//        p.getStrongbox().getInfinityShelf().add(2, new Shelf(ResourceType.SERVANT, 4));
//        p.getStrongbox().getInfinityShelf().add(3, new Shelf(ResourceType.SHIELD, 5));

        p.getStrongbox().pay(-6, ResourceType.COIN);
        p.getStrongbox().pay(-0, ResourceType.STONE);
        p.getStrongbox().pay(-4, ResourceType.SERVANT);
        p.getStrongbox().pay(-5, ResourceType.SHIELD);
        ArrayList<LeaderCard> lc = new ArrayList<LeaderCard>();
        lc.add(new ExtraDepotLCard(2, 3, ResourceType.COIN, ResourceType.STONE));
        lc.add(new ExtraDepotLCard(2, 3, ResourceType.STONE, ResourceType.SERVANT));
        lc.add(new ExtraDepotLCard(2, 3, ResourceType.SERVANT, ResourceType.SHIELD));
        lc.add(new ExtraDepotLCard(2, 3, ResourceType.SHIELD, ResourceType.COIN)); //size of our LeaderDeck is 4 just for testing
        LeaderDeck ld = new LeaderDeck(lc.size(), 2, lc);
        p.setActiveLeader(ld);
        assertTrue(p.checkLCardRequirements(p.getActiveLeader().getCards().get(0)));
        assertFalse(p.checkLCardRequirements(p.getActiveLeader().getCards().get(1)));
        assertFalse(p.checkLCardRequirements(p.getActiveLeader().getCards().get(2)));
        assertTrue(p.checkLCardRequirements(p.getActiveLeader().getCards().get(3)));


    }

    @Test
    @DisplayName("not enough resources in our strongbox. now we need our shelves")
    void secondExtraDepot() {

        p.getStrongbox().pay(-4, ResourceType.COIN);
        p.getStrongbox().pay(-2, ResourceType.STONE);
        p.getStrongbox().pay(-2, ResourceType.SERVANT);
        p.getStrongbox().pay(-0, ResourceType.SHIELD);
        //  MY SHELVES:   COIN
        //           SERVANT EMPTY
        //         STONE STONE  STONE
        //
        p.getWarehouse().addResource(ResourceType.COIN, 0);
        p.getWarehouse().addResource(ResourceType.SERVANT, 1);
        p.getWarehouse().addResource(ResourceType.STONE, 2);
        p.getWarehouse().addResource(ResourceType.STONE, 2);
        p.getWarehouse().addResource(ResourceType.STONE, 2);


        ArrayList<LeaderCard> lc = new ArrayList<LeaderCard>();
        lc.add(new ExtraDepotLCard(2, 3, ResourceType.COIN, ResourceType.STONE));
        lc.add(new ExtraDepotLCard(2, 3, ResourceType.STONE, ResourceType.SERVANT));
        lc.add(new ExtraDepotLCard(2, 3, ResourceType.SERVANT, ResourceType.SHIELD));
        lc.add(new ExtraDepotLCard(2, 3, ResourceType.SHIELD, ResourceType.COIN)); //size of our LeaderDeck is 4 just for testing
        LeaderDeck ld = new LeaderDeck(lc.size(), 2, lc);
        p.setActiveLeader(ld);
        assertTrue(p.checkLCardRequirements(p.getActiveLeader().getCards().get(0)));
        assertTrue(p.checkLCardRequirements(p.getActiveLeader().getCards().get(1)));
        assertFalse(p.checkLCardRequirements(p.getActiveLeader().getCards().get(2)));
        assertFalse(p.checkLCardRequirements(p.getActiveLeader().getCards().get(3)));


    }
}