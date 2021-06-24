package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * FindWinnerTest contains tests on final part of the game. it checks if all victory points' counting methods work.
 * @author Mattia Sironi
 */
public class FindWinnerTest {

    private Game game;


    @BeforeEach
    public void init()  {

        game = new Game(false, 0);

        game.getPlayers().add(new Player(0, "gigi"));
        game.getPlayers().add(new Player(1, "lea"));
        game.getPlayers().add(new Player(2, "simo"));



    }

    @Test
    @DisplayName("first test with just resources in the shelfWarehouse")
    public void onlyShelvesTest()  {
        game.getPlayerById(2).getPersonalBoard().getWarehouse().addResource(ResourceType.COIN, 0);
        game.getPlayerById(2).getPersonalBoard().getWarehouse().addResource(ResourceType.SHIELD, 2);
        game.getPlayerById(2).getPersonalBoard().getWarehouse().addResource(ResourceType.SHIELD, 2);
        game.getPlayerById(2).getPersonalBoard().getWarehouse().addResource(ResourceType.SHIELD, 2);
        game.getPlayerById(1).getPersonalBoard().getWarehouse().addResource(ResourceType.COIN, 0);
        game.getPlayerById(1).getPersonalBoard().getWarehouse().addResource(ResourceType.SHIELD, 2);
        game.getPlayerById(1).getPersonalBoard().getWarehouse().addResource(ResourceType.SHIELD, 2);
        game.getPlayerById(1).getPersonalBoard().getWarehouse().addResource(ResourceType.SHIELD, 2);
        game.getPlayerById(1).getPersonalBoard().getWarehouse().addResource(ResourceType.SERVANT, 1);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.COIN, 0);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.STONE, 2);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.STONE, 2);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.STONE, 2);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.SERVANT, 1);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.SERVANT, 1);
        assertEquals(0, game.findWinner().getId());

    }
    @Test
    @DisplayName("adding resources in strongbox")
    public void strongBoxTest()  {
        game.getPlayerById(2).getPersonalBoard().getWarehouse().addResource(ResourceType.COIN, 0);
        game.getPlayerById(2).getPersonalBoard().getWarehouse().addResource(ResourceType.SHIELD, 2);
        game.getPlayerById(2).getPersonalBoard().getWarehouse().addResource(ResourceType.SHIELD, 2);
        game.getPlayerById(2).getPersonalBoard().getWarehouse().addResource(ResourceType.SHIELD, 2);
        game.getPlayerById(1).getPersonalBoard().getWarehouse().addResource(ResourceType.COIN, 0);
        game.getPlayerById(1).getPersonalBoard().getWarehouse().addResource(ResourceType.SHIELD, 2);
        game.getPlayerById(1).getPersonalBoard().getWarehouse().addResource(ResourceType.SHIELD, 2);
        game.getPlayerById(1).getPersonalBoard().getWarehouse().addResource(ResourceType.SHIELD, 2);
        game.getPlayerById(1).getPersonalBoard().getWarehouse().addResource(ResourceType.SERVANT, 1);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.COIN, 0);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.STONE, 2);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.STONE, 2);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.STONE, 2);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.SERVANT, 1);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.SERVANT, 1);
        game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.COIN, 12);
        game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.SERVANT, 8);
        game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.STONE, 4);
        game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.SHIELD, 7);
        game.getPlayerById(1).getPersonalBoard().getStrongbox().addResource(ResourceType.COIN,3);
        game.getPlayerById(1).getPersonalBoard().getStrongbox().addResource(ResourceType.SERVANT, 12);
        game.getPlayerById(1).getPersonalBoard().getStrongbox().addResource(ResourceType.SHIELD, 5);
        game.getPlayerById(1).getPersonalBoard().getStrongbox().addResource(ResourceType.STONE, 9);
        game.getPlayerById(2).getPersonalBoard().getStrongbox().addResource(ResourceType.COIN, 21);
        game.getPlayerById(2).getPersonalBoard().getStrongbox().addResource(ResourceType.STONE, 0);
        game.getPlayerById(2).getPersonalBoard().getStrongbox().addResource(ResourceType.SERVANT, 0);
        game.getPlayerById(2).getPersonalBoard().getStrongbox().addResource(ResourceType.SHIELD, 9);
        assertEquals(0, game.findWinner().getId());





    }


    @Test
    @DisplayName("Leader cards")
    public void leadCards()  {
        strongBoxTest();

        // --- PLAYER 1 ----
        ArrayList<LeaderCard> ldeck0 = new ArrayList<>();
        ldeck0.add(new WhiteTrayLCard(0, 10, ResourceType.COIN, CardColor.BLUE, CardColor.YELLOW));
        ldeck0.add(new ExtraDepotLCard(0, 15, ResourceType.COIN, ResourceType.SHIELD));
        LeaderDeck deck0 = new LeaderDeck (ldeck0);
        game.getPlayerById(0).getPersonalBoard().setActiveLeader(deck0);


        // --- PLAYER 2 ----
        ArrayList<LeaderCard> ldeck1 = new ArrayList<>();
        ldeck1.add(new ExtraProdLCard(0, 12, CardColor.GREEN, ResourceType.SERVANT));
        ldeck1.add(new DiscountLCard(0, 15, CardColor.BLUE, CardColor.PURPLE, ResourceType.STONE));
        LeaderDeck deck1 = new LeaderDeck(ldeck1);
        game.getPlayerById(1).getPersonalBoard().setActiveLeader(deck1);

        // --- PLAYER 3 ----
        ArrayList<LeaderCard> ldeck2 = new ArrayList<>();
        ldeck2.add(new ExtraDepotLCard(0, 8, ResourceType.SERVANT, ResourceType.STONE ));
        ldeck2.add(new ExtraProdLCard(0, 12, CardColor.GREEN, ResourceType.SERVANT));
        LeaderDeck deck2 = new LeaderDeck (ldeck2);
        game.getPlayerById(2).getPersonalBoard().setActiveLeader(deck2);

        assertEquals(1, game.findWinner().getId());

//        GIGI VP 32 R 37
//        LEA VP 33 R 34
//        SIMO VP 26 R 34











    }

    @Test
    @DisplayName("Dev cards")
    public void devCards()  {
        leadCards();

        //PLAYER 0
        ArrayList<DevCard> deck00 = new ArrayList<>();
        ArrayList<DevCard> deck01 = new ArrayList<>();
        ArrayList<DevCard> deck02 = new ArrayList<>();


        deck00.add(new DevCard(5, 1, CardColor.BLUE, new int[]{0, 0, 0, 0}, new int[] {0,0,0,0},new int[] {0,0,0,0,0}));
        deck00.add(new DevCard(5, 2, CardColor.BLUE, new int[]{0, 0, 0, 0}, new int[] {0,0,0,0},new int[] {0,0,0,0,0}));
        deck00.add(new DevCard( 5, 3, CardColor.BLUE, new int[]{0, 0, 0, 0}, new int[] {0,0,0,0},new int[] {0,0,0,0,0}));


        deck01.add(new DevCard(6, 1, CardColor.BLUE, new int[]{0, 0, 0, 0}, new int[] {0,0,0,0},new int[] {0,0,0,0,0}));
        deck01.add(new DevCard(7, 2, CardColor.BLUE, new int[]{0, 0, 0, 0}, new int[] {0,0,0,0},new int[] {0,0,0,0,0}));

        ArrayList<DevDeck> deck0= new ArrayList<>();
        deck0.add(new DevDeck(deck00));
        deck0.add(new DevDeck(deck01));
        deck0.add(new DevDeck(deck02));

        game.getPlayerById(0).getPersonalBoard().setCardSlot(deck0);

        //PLAYER 1
        ArrayList<DevCard> deck10 = new ArrayList<>();
        ArrayList<DevCard> deck11 = new ArrayList<>();
        ArrayList<DevCard> deck12 = new ArrayList<>();


        deck10.add(new DevCard(5, 1, CardColor.BLUE, new int[]{0, 0, 0, 0}, new int[] {0,0,0,0},new int[] {0,0,0,0,0}));



        deck11.add(new DevCard(6, 1, CardColor.BLUE, new int[]{0, 0, 0, 0}, new int[] {0,0,0,0},new int[] {0,0,0,0,0}));
        deck11.add(new DevCard(7, 2, CardColor.BLUE, new int[]{0, 0, 0, 0}, new int[] {0,0,0,0},new int[] {0,0,0,0,0}));

        deck12.add(new DevCard(7, 2, CardColor.BLUE, new int[]{0, 0, 0, 0}, new int[] {0,0,0,0},new int[] {0,0,0,0,0}));

        ArrayList<DevDeck> deck1= new ArrayList<>();
        deck1.add(new DevDeck(deck10));
        deck1.add(new DevDeck(deck11));
        deck1.add(new DevDeck(deck12));

        game.getPlayerById(1).getPersonalBoard().setCardSlot(deck1);




        //PLAYER 2
        ArrayList<DevCard> deck20 = new ArrayList<>();
        ArrayList<DevCard> deck21 = new ArrayList<>();
        ArrayList<DevCard> deck22 = new ArrayList<>();






        deck21.add(new DevCard(10, 1, CardColor.BLUE, new int[]{0, 0, 0, 0}, new int[] {0,0,0,0},new int[] {0,0,0,0,0}));
        deck21.add(new DevCard(10, 2, CardColor.BLUE, new int[]{0, 0, 0, 0}, new int[] {0,0,0,0},new int[] {0,0,0,0,0}));



        ArrayList<DevDeck> deck2= new ArrayList<>();
        deck2.add(new DevDeck(deck20));
        deck2.add(new DevDeck(deck21));
        deck2.add(new DevDeck(deck22));

        game.getPlayerById(2).getPersonalBoard().setCardSlot(deck2);

        assertEquals(0, game.findWinner().getId());

//        GIGI VP 60 R 37
//        LEA VP 58 R 34
//        SIMO VP 46 R 34












    }

    @Test
    @DisplayName("find real winner")
    public void realWinner()  {
        devCards();

//        ----- FIRST POPE  5-8-----
        game.moveFaithPosByID(0, 1); //GIGI 1
        game.moveFaithPosByID(1, 1); // LEA 1
        game.moveFaithPosByID(2, 1); // SIMO 1
        game.moveFaithPosByID(0, 3); // GIGI 4
        game.moveFaithPosByID(1, 1); //LEA 2
        game.moveFaithPosByID(0, 2); //GIGI 6
        game.moveFaithPosByID(2, 3); //SIMO 4
        game.moveFaithPosByID(2, 1); //SIMO 5
        game.moveFaithPosByID(1, 2); //LEA 4


        assertEquals(null, game.getPlayerById(0).getPersonalBoard().getFaithTrack().getFavorTile1());
        assertEquals(null, game.getPlayerById(1).getPersonalBoard().getFaithTrack().getFavorTile1());
        assertEquals(null, game.getPlayerById(2).getPersonalBoard().getFaithTrack().getFavorTile1());

        game.moveFaithPosByID(0, 2); //GIGI 8


//        ------- POPE FAVOR 1 REACHED ----------
//            GIGI 8 YES
//            LEA 4 NOPE
//            SIMO 5 YEP

        assertEquals(1, game.getPlayerById(0).getPersonalBoard().getFaithTrack().getFavorTile1());
        assertEquals(0, game.getPlayerById(1).getPersonalBoard().getFaithTrack().getFavorTile1());
        assertEquals(1, game.getPlayerById(2).getPersonalBoard().getFaithTrack().getFavorTile1());



//        ---SECOND POPE 12-16


        game.moveFaithPosByID(1, 5); //LEA 9
        assertEquals(0, game.getPlayerById(1).getPersonalBoard().getFaithTrack().getFavorTile1()); // 9 points but not on time
        game.moveFaithPosByID(2, 5); //SIMO 10
        game.moveFaithPosByID(1, 6); //LEA 15
        game.moveFaithPosByID(2, 2); //SIMO 12
        game.moveFaithPosByID(0, 3); //GIGI 11
        assertEquals(null, game.getPlayerById(0).getPersonalBoard().getFaithTrack().getFavorTile2());
        assertEquals(null, game.getPlayerById(1).getPersonalBoard().getFaithTrack().getFavorTile2());
        assertEquals(null, game.getPlayerById(2).getPersonalBoard().getFaithTrack().getFavorTile2());

        game.moveFaithPosByID(1, 2); //LEA 17

//        ------- POPE FAVOR 2 REACHED----------
//            GIGI 11 NOPE
//            LEA 17 YES
//            SIMO 12 YEP

        assertEquals(0, game.getPlayerById(0).getPersonalBoard().getFaithTrack().getFavorTile2());
        assertEquals(1, game.getPlayerById(1).getPersonalBoard().getFaithTrack().getFavorTile2());
        assertEquals(1, game.getPlayerById(2).getPersonalBoard().getFaithTrack().getFavorTile2());


//        ----- THIRD POPE  19-24-----
        game.moveFaithPosByID(2, 5); //SIMO 17
        game.moveFaithPosByID(1, 1); //LEA 18
        game.moveFaithPosByID(0, 10); //GIGI 21
        game.moveFaithPosByID(0, 2); //GIGI 23
        assertEquals(null, game.getPlayerById(0).getPersonalBoard().getFaithTrack().getFavorTile3());
        assertEquals(null, game.getPlayerById(1).getPersonalBoard().getFaithTrack().getFavorTile3());
        assertEquals(null, game.getPlayerById(2).getPersonalBoard().getFaithTrack().getFavorTile3());

        game.moveFaithPosByID(0, 5); //GIGI 24 CAPPED
        assertEquals(24, game.getPlayerById(0).getPersonalBoard().getFaithTrack().getMarker());

        //        ------- POPE FAVOR 2 REACHED----------
//            GIGI 24 YES    20 pts + 2 + 4
//            LEA 18 NOPE    12 + 3
//            SIMO 17 NOPE 9 + 2 + 3


        assertEquals(1, game.getPlayerById(0).getPersonalBoard().getFaithTrack().getFavorTile3());
        assertEquals(0, game.getPlayerById(1).getPersonalBoard().getFaithTrack().getFavorTile3());
        assertEquals(0, game.getPlayerById(2).getPersonalBoard().getFaithTrack().getFavorTile3());




//        GIGI VP 86 R 37
//        LEA VP 73 R 34
//        SIMO VP 60 R 34

        assertEquals(0, game.findWinner().getId());























    }


}


