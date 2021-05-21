package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Mattia Sironi
 */
public class FindWinnerTest {

    private Game game;

    @BeforeEach
    public void init()  {
        game = new Game();
        game.getPlayers().add(new Player(0, "gigi"));
        game.getPlayers().add(new Player(1, "lea"));
        game.getPlayers().add(new Player(2, "simo"));



    }

    @Test
    @DisplayName("first test with just resources in the shelfwarehouse")
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
    @DisplayName("pope favor")
    public void popeFavor()  {
        strongBoxTest();
        game.getPlayerById(0).getPersonalBoard().getFaithTrack().setFavorTile(0);
        game.getPlayerById(1).getPersonalBoard().getFaithTrack().setFavorTile(0);
        game.getPlayerById(1).getPersonalBoard().getFaithTrack().setFavorTile(2);
        game.getPlayerById(2).getPersonalBoard().getFaithTrack().setFavorTile(0);
        game.getPlayerById(2).getPersonalBoard().getFaithTrack().setFavorTile(2);
        game.getPlayerById(2).getPersonalBoard().getFaithTrack().setFavorTile(1);
        assertEquals(2, game.findWinner().getId());




    }
    @Test
    @DisplayName("faith marker")
    public void faithMarker()  {
        popeFavor();
        game.getPlayerById(0).getPersonalBoard().getFaithTrack().moveFaithMarkerPos(300);
        game.getPlayerById(1).getPersonalBoard().getFaithTrack().moveFaithMarkerPos(20);
        game.getPlayerById(2).getPersonalBoard().getFaithTrack().moveFaithMarkerPos(12);
        assertEquals(0, game.findWinner().getId());




    }

    @Test
    @DisplayName("Leader cards")
    public void leadCards()  {
        faithMarker();

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

        assertEquals(0, game.findWinner().getId());


    // PLAYER 0 : VP = 54   RES = 37
    // PLAYER 1 : VP = 51   RES = 34
    // PLAYER 2 : VP = 41  RES  = 34






    }

    @Test
    @DisplayName("Dev cards")
    public void devCards()  {
        leadCards();

        //PLAYER 0
        ArrayList<DevCard> deck00 = new ArrayList<>();
        ArrayList<DevCard> deck01 = new ArrayList<>();
        ArrayList<DevCard> deck02 = new ArrayList<>();


        deck00.add(new DevCard(0, 5, 1, CardColor.BLUE, new int[]{0, 0, 0, 0}, new int[] {0,0,0,0},new int[] {0,0,0,0,0}));
        deck00.add(new DevCard(0, 5, 2, CardColor.BLUE, new int[]{0, 0, 0, 0}, new int[] {0,0,0,0},new int[] {0,0,0,0,0}));
        deck00.add(new DevCard(0, 5, 3, CardColor.BLUE, new int[]{0, 0, 0, 0}, new int[] {0,0,0,0},new int[] {0,0,0,0,0}));


        deck01.add(new DevCard(0, 6, 1, CardColor.BLUE, new int[]{0, 0, 0, 0}, new int[] {0,0,0,0},new int[] {0,0,0,0,0}));
        deck01.add(new DevCard(0, 7, 2, CardColor.BLUE, new int[]{0, 0, 0, 0}, new int[] {0,0,0,0},new int[] {0,0,0,0,0}));

        ArrayList<DevDeck> deck0= new ArrayList<>();
        deck0.add(new DevDeck(3,0, deck00));
        deck0.add(new DevDeck(2, 0, deck01));
        deck0.add(new DevDeck(0, 0, deck02));

        game.getPlayerById(0).getPersonalBoard().setCardSlot(deck0);

        //PLAYER 1
        ArrayList<DevCard> deck10 = new ArrayList<>();
        ArrayList<DevCard> deck11 = new ArrayList<>();
        ArrayList<DevCard> deck12 = new ArrayList<>();


        deck10.add(new DevCard(0, 5, 1, CardColor.BLUE, new int[]{0, 0, 0, 0}, new int[] {0,0,0,0},new int[] {0,0,0,0,0}));



        deck11.add(new DevCard(0, 6, 1, CardColor.BLUE, new int[]{0, 0, 0, 0}, new int[] {0,0,0,0},new int[] {0,0,0,0,0}));
        deck11.add(new DevCard(0, 7, 2, CardColor.BLUE, new int[]{0, 0, 0, 0}, new int[] {0,0,0,0},new int[] {0,0,0,0,0}));

        deck12.add(new DevCard(0, 7, 2, CardColor.BLUE, new int[]{0, 0, 0, 0}, new int[] {0,0,0,0},new int[] {0,0,0,0,0}));

        ArrayList<DevDeck> deck1= new ArrayList<>();
        deck1.add(new DevDeck(3,0, deck10));
        deck1.add(new DevDeck(2, 0, deck11));
        deck1.add(new DevDeck(0, 0, deck12));

        game.getPlayerById(1).getPersonalBoard().setCardSlot(deck1);




        //PLAYER 2
        ArrayList<DevCard> deck20 = new ArrayList<>();
        ArrayList<DevCard> deck21 = new ArrayList<>();
        ArrayList<DevCard> deck22 = new ArrayList<>();






        deck21.add(new DevCard(0, 10, 1, CardColor.BLUE, new int[]{0, 0, 0, 0}, new int[] {0,0,0,0},new int[] {0,0,0,0,0}));
        deck21.add(new DevCard(0, 10, 2, CardColor.BLUE, new int[]{0, 0, 0, 0}, new int[] {0,0,0,0},new int[] {0,0,0,0,0}));



        ArrayList<DevDeck> deck2= new ArrayList<>();
        deck2.add(new DevDeck(3,0, deck20));
        deck2.add(new DevDeck(2, 0, deck21));
        deck2.add(new DevDeck(0, 0, deck22));

        game.getPlayerById(2).getPersonalBoard().setCardSlot(deck2);

        assertEquals(0, game.findWinner().getId());


        // PLAYER 0 : VP = 82   RES = 37
        // PLAYER 1 : VP = 76   RES = 34
        // PLAYER 2 : VP = 61  RES  = 34








    }


}


