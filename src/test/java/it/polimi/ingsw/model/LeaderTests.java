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
        player = new Player(0, "lea",  true, new LeaderDeck(0, 1, new ArrayList<LeaderCard>()), 10, ResourceType.EMPTY, ResourceType.EMPTY, ResourceType.EMPTY, ResourceType.EMPTY, ResourceType.EMPTY, ResourceType.EMPTY, false, personalBoard);
        game.getPlayers().add(player);
        game.getPlayers().get(0).setId(0);
        game.getPlayerById(0).setPersonalBoard(personalBoard);
        game.getPlayerById(0).getPersonalBoard().setStrongbox(strongbox);
        game.getPlayerById(0).getPersonalBoard().setWarehouse(warehouse);
    }
//
//    @Test
//    @DisplayName("Discard Leader Card")
//    void DiscardLeader() {
//       ArrayList<LeaderCard> leaderCards = new ArrayList<LeaderCard>();
//       LeaderCard c1 = new LeaderCard(1,3);
//        LeaderCard c2 = new LeaderCard(3,2);
//        LeaderCard c3 = new LeaderCard(4,1);
//        leaderCards.add(0,c1);
//        leaderCards.add(1,c2);
//        leaderCards.add(2,c3);
//        LeaderDeck leaderDeck = new LeaderDeck(3,1,leaderCards);
//        game.getPlayerById(0).setLeaderDeck(leaderDeck);
//        game.getPlayerById(0).getPersonalBoard().getFaithTrack().setMarker(6);
//
//        controller.DiscardLeaderCard(0, new LeaderCard(3,2));
//        ArrayList<LeaderCard> lctest = new ArrayList<LeaderCard>();
//        lctest.add(0,c1);
//        lctest.add(1,c3);
//      //  assertEquals(7, game.getPlayerById(0).getPersonalBoard().getFaithTrack().getMarker());
//     //   assertEquals(lctest,game.getPlayerById(0).getLeaderDeck().getCards());
//
//    }
//
//    @Test
//    @DisplayName("Play Discount Leader Card")
//    void PlayDiscountLeader() {
//        ArrayList<LeaderCard> leaderCards = new ArrayList<LeaderCard>();
//        ArrayList<LeaderCard> hand = new ArrayList<LeaderCard>();
//        ExtraDepotLCard inHand = new ExtraDepotLCard(2,1,ResourceType.COIN,ResourceType.SERVANT);
//        hand.add(0,inHand);
//        LeaderDeck unused = new LeaderDeck(1,2,hand);
//        DiscountLCard c1 = new DiscountLCard(1,3,CardColor.BLUE,CardColor.YELLOW,ResourceType.COIN);
//        leaderCards.add(0,c1);
//        LeaderDeck leaderDeck = new LeaderDeck(1,1,leaderCards);
//        game.getPlayerById(0).setLeaderDeck(unused);
//        game.getPlayerById(0).getPersonalBoard().setActiveLeader(leaderDeck);
//        game.getPlayerById(0).getPersonalBoard().getFaithTrack().setMarker(6);
//        game.getPlayerById(0).setResDiscount1(ResourceType.SERVANT);
//        game.getPlayerById(0).setResDiscount2(null);
//        DevCard d1 = new DevCard(0,10,1,CardColor.BLUE,null,null,null);
//        DevCard d2 =new DevCard(0,10,1,CardColor.PURPLE,null,null,null);
//        ArrayList<DevCard> devc= new ArrayList<>();
//        devc.add(d1);
//        devc.add(d2);
//        DevDeck dd = new DevDeck(2,0,devc);
//        game.getPlayerById(0).getPersonalBoard().getCardSlot().set(0,dd);
//
//
//        ArrayList<LeaderCard> lctest = new ArrayList<LeaderCard>();
//        lctest.addAll(leaderCards);
//        DiscountLCard dlc = new DiscountLCard(1,4, CardColor.BLUE,CardColor.PURPLE,ResourceType.COIN);
//        lctest.add(dlc);
//
//        controller.PlayLeaderCard(0, dlc);
//     //   assertEquals(lctest, game.getPlayerById(0).getPersonalBoard().getActiveLeader().getCards());
//        assertEquals(ResourceType.SERVANT,game.getPlayerById(0).getResDiscount1());
//        assertEquals(ResourceType.COIN,game.getPlayerById(0).getResDiscount2());
//        assertEquals(0,game.getPlayerById(0).getLeaderDeck().getCards().size());
//        assertEquals(2,game.getPlayerById(0).getPersonalBoard().getActiveLeader().getSize());
//        assertEquals(dlc,game.getPlayerById(0).getPersonalBoard().getActiveLeader().getCards().get(1));
//
//    }
//
//
//    @Test
//    @DisplayName("Play Extra Depot Leader Card")
//    void PlayExtraDepotLeader() {
//        ArrayList<LeaderCard> leaderCards = new ArrayList<LeaderCard>();
//        ArrayList<LeaderCard> hand = new ArrayList<LeaderCard>();
//        LeaderCard inHand = new LeaderCard (4,1);
//        LeaderCard inHand2 = new LeaderCard(1,2);
//        hand.add(0,inHand);
//        hand.add(1,inHand2);
//        LeaderDeck unused = new LeaderDeck(1,2,hand);
//        LeaderDeck leaderDeck = new LeaderDeck(0,1,leaderCards);
//        game.getPlayerById(0).getPersonalBoard().setActiveLeader(leaderDeck);
//        game.getPlayerById(0).getPersonalBoard().getFaithTrack().setMarker(6);
//        game.getPlayerById(0).getPersonalBoard().setExtraShelfRes1(ResourceType.EMPTY);
//        game.getPlayerById(0).getPersonalBoard().setExtraShelfRes2(ResourceType.EMPTY);
//
//
//
//        ArrayList<LeaderCard> lctest = new ArrayList<LeaderCard>();
//        lctest.addAll(leaderCards);
//        ExtraDepotLCard ed = new ExtraDepotLCard (2,4,ResourceType.COIN, ResourceType.SHIELD);
//        lctest.add(ed);
//
//        controller.PlayLeaderCard(0, ed);
//        assertEquals(lctest, game.getPlayerById(0).getPersonalBoard().getActiveLeader().getCards());
//        assertEquals(ResourceType.SHIELD,game.getPlayerById(0).getPersonalBoard().getExtraShelfRes1());
//        assertEquals(ResourceType.EMPTY,game.getPlayerById(0).getPersonalBoard().getExtraShelfRes2());
//
//    }
//
//    @Test
//    @DisplayName("Play Extra Production Leader Card")
//    void PlayExtraProdLeader() {
//        ArrayList<LeaderCard> leaderCards = new ArrayList<LeaderCard>();
//        LeaderCard c1 = new LeaderCard(3,3);
//        leaderCards.add(0,c1);
//        LeaderDeck leaderDeck = new LeaderDeck(1,1,leaderCards);
//        game.getPlayerById(0).getPersonalBoard().setActiveLeader(leaderDeck);
//        game.getPlayerById(0).getPersonalBoard().getFaithTrack().setMarker(6);
//        game.getPlayerById(0).setInputExtraProduction1(ResourceType.STONE);
//        game.getPlayerById(0).setInputExtraProduction2(ResourceType.EMPTY);
//
//
//
//        ArrayList<LeaderCard> lctest = new ArrayList<LeaderCard>();
//        lctest.addAll(leaderCards);
//        ExtraProdLCard ep = new ExtraProdLCard (3,2,CardColor.BLUE, ResourceType.SHIELD);
//        lctest.add(ep);
//
//        controller.PlayLeaderCard(0, ep);
//        assertEquals(lctest, game.getPlayerById(0).getPersonalBoard().getActiveLeader().getCards());
//        assertEquals(ResourceType.STONE,game.getPlayerById(0).getInputExtraProduction1());
//        assertEquals(ResourceType.SHIELD,game.getPlayerById(0).getInputExtraProduction2());
//
//    }
//
//
//    @Test
//    @DisplayName("Play White Tray Leader Card")
//    void PlayWhiteTrayLeader() {
//        ArrayList<LeaderCard> leaderCards = new ArrayList<LeaderCard>();
//
//        LeaderDeck leaderDeck = new LeaderDeck(0,1,leaderCards);
//        game.getPlayerById(0).getPersonalBoard().setActiveLeader(leaderDeck);
//        game.getPlayerById(0).getPersonalBoard().getFaithTrack().setMarker(6);
//        game.getPlayerById(0).setWhiteConversion1(null);
//        game.getPlayerById(0).setWhiteConversion2(null);
//        DevCard d1 = new DevCard(0,10,1,CardColor.GREEN,null,null,null);
//        DevCard d2 =new DevCard(0,10,1,CardColor.YELLOW,null,null,null);
//        DevCard d3 =new DevCard(0,10,2,CardColor.YELLOW,null,null,null);
//        ArrayList<DevCard> devc= new ArrayList<>();
//        devc.add(d1);
//        devc.add(d2);
//        devc.add(d3);
//        DevDeck dd = new DevDeck(2,0,devc);
//        game.getPlayerById(0).getPersonalBoard().getCardSlot().set(0,dd);
//
//
//        ArrayList<LeaderCard> lctest = new ArrayList<LeaderCard>();
//        lctest.addAll(leaderCards);
//        WhiteTrayLCard wl = new WhiteTrayLCard (4,2, ResourceType.COIN, CardColor.GREEN, CardColor.YELLOW);
//        lctest.add(wl);
//
//        controller.PlayLeaderCard(0, wl);
//      //  assertEquals(lctest, game.getPlayerById(0).getPersonalBoard().getActiveLeader().getCards());
//        assertEquals(ResourceType.COIN,game.getPlayerById(0).getWhiteConversion1());
//        assertEquals(null,game.getPlayerById(0).getWhiteConversion2());
//
//    }
//

    @Test
    @DisplayName("Override equals")
    void equalsTest(){
        LeaderCard l1 = new WhiteTrayLCard (4,2, ResourceType.COIN, CardColor.GREEN, CardColor.YELLOW);
        LeaderCard l2 = new  WhiteTrayLCard (4,2, ResourceType.COIN, CardColor.GREEN, CardColor.YELLOW);
        LeaderCard l3 =  new ExtraDepotLCard (2,4,ResourceType.COIN, ResourceType.SHIELD);
        LeaderCard l4 = new ExtraDepotLCard (2,4,ResourceType.STONE, ResourceType.SHIELD);

        assertEquals(true, l1.equals(l2));
        assertEquals(false,l1.equals(l3));
        assertEquals(false,l3.equals(l4));
    }







}


