package it.polimi.ingsw.model;

import it.polimi.ingsw.constants.Color;
import it.polimi.ingsw.controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class LeaderTests {
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
        player = new Player(0, "lea", 3, true, new LeaderDeck(0, 1, new ArrayList<LeaderCard>()), 10, ResourceType.EMPTY, ResourceType.EMPTY, ResourceType.EMPTY, ResourceType.EMPTY, ResourceType.EMPTY, ResourceType.EMPTY, false, personalBoard);
        game.getPlayers().add(player);
        game.getPlayers().get(0).setId(0);
        game.getPlayerById(0).setPersonalBoard(personalBoard);
        game.getPlayerById(0).getPersonalBoard().setStrongbox(strongbox);
        game.getPlayerById(0).getPersonalBoard().setWarehouse(warehouse);
    }

    @Test
    @DisplayName("Discard Leader Card")
    void DiscardLeader() {
       ArrayList<LeaderCard> leaderCards = new ArrayList<LeaderCard>();
       LeaderCard c1 = new LeaderCard(1,3);
        LeaderCard c2 = new LeaderCard(3,2);
        LeaderCard c3 = new LeaderCard(4,1);
        leaderCards.add(0,c1);
        leaderCards.add(1,c2);
        leaderCards.add(2,c3);
        LeaderDeck leaderDeck = new LeaderDeck(3,1,leaderCards);
        game.getPlayerById(0).setLeaderDeck(leaderDeck);
        game.getPlayerById(0).setFaithMarkerPos(6);

        controller.DiscardLeaderCard(0, new LeaderCard(3,2));
        ArrayList<LeaderCard> lctest = new ArrayList<LeaderCard>();
        lctest.add(0,c1);
        lctest.add(1,c3);
        assertEquals(7, game.getPlayerById(0).getFaithMarkerPos());
        assertEquals(lctest,game.getPlayerById(0).getLeaderDeck().getCards());

    }

    @Test
    @DisplayName("Play Discount Leader Card")
    void PlayDiscountLeader() {
        ArrayList<LeaderCard> leaderCards = new ArrayList<LeaderCard>();
        ArrayList<LeaderCard> hand = new ArrayList<LeaderCard>();
        LeaderCard inHand = new LeaderCard (2,1);
        hand.add(0,inHand);
        LeaderDeck unused = new LeaderDeck(1,2,hand);
        LeaderCard c1 = new LeaderCard(1,3);
        leaderCards.add(0,c1);
        LeaderDeck leaderDeck = new LeaderDeck(1,1,leaderCards);
        game.getPlayerById(0).setLeaderDeck(unused);
        game.getPlayerById(0).getPersonalBoard().setActiveLeader(leaderDeck);
        game.getPlayerById(0).setFaithMarkerPos(6);
        game.getPlayerById(0).setResDiscount1(ResourceType.SERVANT);
        game.getPlayerById(0).setResDiscount2(ResourceType.EMPTY);


        ArrayList<LeaderCard> lctest = new ArrayList<LeaderCard>();
        lctest.addAll(leaderCards);
        DiscountLCard dlc = new DiscountLCard(1,4, CardColor.BLUE,CardColor.PURPLE,ResourceType.COIN);
        lctest.add(dlc);

        controller.PlayLeaderCard(0, dlc);
        assertEquals(lctest, game.getPlayerById(0).getPersonalBoard().getActiveLeader().getCards());
        assertEquals(ResourceType.SERVANT,game.getPlayerById(0).getResDiscount1());
        assertEquals(ResourceType.COIN,game.getPlayerById(0).getResDiscount2());
        assertEquals(0,game.getPlayerById(0).getLeaderDeck().getSize());

    }


    @Test
    @DisplayName("Play Extra Depot Leader Card")
    void PlayExtraDepotLeader() {
        ArrayList<LeaderCard> leaderCards = new ArrayList<LeaderCard>();
        ArrayList<LeaderCard> hand = new ArrayList<LeaderCard>();
        LeaderCard inHand = new LeaderCard (4,1);
        LeaderCard inHand2 = new LeaderCard(1,2);
        hand.add(0,inHand);
        hand.add(1,inHand2);
        LeaderDeck unused = new LeaderDeck(1,2,hand);
        LeaderDeck leaderDeck = new LeaderDeck(0,1,leaderCards);
        game.getPlayerById(0).getPersonalBoard().setActiveLeader(leaderDeck);
        game.getPlayerById(0).setFaithMarkerPos(6);
        game.getPlayerById(0).getPersonalBoard().setExtraShelfRes1(ResourceType.EMPTY);
        game.getPlayerById(0).getPersonalBoard().setExtraShelfRes2(ResourceType.EMPTY);



        ArrayList<LeaderCard> lctest = new ArrayList<LeaderCard>();
        lctest.addAll(leaderCards);
        ExtraDepotLCard ed = new ExtraDepotLCard (2,4,ResourceType.COIN, ResourceType.SHIELD);
        lctest.add(ed);

        controller.PlayLeaderCard(0, ed);
        assertEquals(lctest, game.getPlayerById(0).getPersonalBoard().getActiveLeader().getCards());
        assertEquals(ResourceType.SHIELD,game.getPlayerById(0).getPersonalBoard().getExtraShelfRes1());
        assertEquals(ResourceType.EMPTY,game.getPlayerById(0).getPersonalBoard().getExtraShelfRes2());

    }

    @Test
    @DisplayName("Play Extra Production Leader Card")
    void PlayExtraProdLeader() {
        ArrayList<LeaderCard> leaderCards = new ArrayList<LeaderCard>();
        LeaderCard c1 = new LeaderCard(3,3);
        leaderCards.add(0,c1);
        LeaderDeck leaderDeck = new LeaderDeck(1,1,leaderCards);
        game.getPlayerById(0).getPersonalBoard().setActiveLeader(leaderDeck);
        game.getPlayerById(0).setFaithMarkerPos(6);
        game.getPlayerById(0).setInputExtraProduction1(ResourceType.STONE);
        game.getPlayerById(0).setInputExtraProduction2(ResourceType.EMPTY);



        ArrayList<LeaderCard> lctest = new ArrayList<LeaderCard>();
        lctest.addAll(leaderCards);
        ExtraProdLCard ep = new ExtraProdLCard (3,2,CardColor.BLUE, ResourceType.SHIELD);
        lctest.add(ep);

        controller.PlayLeaderCard(0, ep);
        assertEquals(lctest, game.getPlayerById(0).getPersonalBoard().getActiveLeader().getCards());
        assertEquals(ResourceType.STONE,game.getPlayerById(0).getInputExtraProduction1());
        assertEquals(ResourceType.SHIELD,game.getPlayerById(0).getInputExtraProduction2());

    }


    @Test
    @DisplayName("Play White Tray Leader Card")
    void PlayWhiteTrayLeader() {
        ArrayList<LeaderCard> leaderCards = new ArrayList<LeaderCard>();

        LeaderDeck leaderDeck = new LeaderDeck(0,1,leaderCards);
        game.getPlayerById(0).getPersonalBoard().setActiveLeader(leaderDeck);
        game.getPlayerById(0).setFaithMarkerPos(6);
        game.getPlayerById(0).setWhiteConversion1(ResourceType.EMPTY);
        game.getPlayerById(0).setWhiteConversion2(ResourceType.EMPTY);



        ArrayList<LeaderCard> lctest = new ArrayList<LeaderCard>();
        lctest.addAll(leaderCards);
        WhiteTrayLCard wl = new WhiteTrayLCard (4,2, ResourceType.COIN, CardColor.GREEN, CardColor.YELLOW);
        lctest.add(wl);

        controller.PlayLeaderCard(0, wl);
        assertEquals(lctest, game.getPlayerById(0).getPersonalBoard().getActiveLeader().getCards());
        assertEquals(ResourceType.COIN,game.getPlayerById(0).getWhiteConversion1());
        assertEquals(ResourceType.EMPTY,game.getPlayerById(0).getWhiteConversion2());

    }









}


