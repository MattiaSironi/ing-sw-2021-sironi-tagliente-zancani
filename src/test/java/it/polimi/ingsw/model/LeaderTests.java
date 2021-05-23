package it.polimi.ingsw.model;

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
        game = new Game(false);
        controller = new Controller(game);
        personalBoard = new PersonalBoard();
        strongbox = new Strongbox();
        strongbox.setInfinityShelf(new ArrayList<Shelf>());
        ArrayList<Shelf> shelves= new ArrayList<Shelf>();
        ArrayList<Shelf> war = new ArrayList<Shelf>();
        Shelf s1 = new Shelf(ResourceType.STONE,1);
        Shelf s2 = new Shelf(ResourceType.SERVANT,1);
        Shelf s3 = new Shelf(ResourceType.SHIELD,2);
        Shelf s = new Shelf(ResourceType.COIN,6);
        Shelf s4 =new Shelf(null,0);
        Shelf s5 =new Shelf(null,0);
        shelves.add(0,s);
        shelves.add(1,s2);
        shelves.add(2,s2);
        shelves.add(3,s3);
        war.add(s1);
        war.add(s2);
        war.add(s3);
        war.add(s4);
        war.add(s5);
        strongbox.setInfinityShelf(shelves);
        warehouse = new ShelfWarehouse();
        warehouse.setShelves(war);
        player = new Player(0, "lea",  true, new LeaderDeck(new ArrayList<LeaderCard>()), 10, ResourceType.EMPTY, ResourceType.EMPTY, ResourceType.EMPTY, ResourceType.EMPTY, ResourceType.EMPTY, ResourceType.EMPTY, false, personalBoard);
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
      DiscountLCard c1 = new DiscountLCard(1,3,CardColor.BLUE,CardColor.YELLOW,ResourceType.COIN);
        LeaderCard c2 = new LeaderCard(3,2);
        LeaderCard c3 = new LeaderCard(4,1);
        leaderCards.add(0,c1);
        leaderCards.add(1,c2);
      //  leaderCards.add(2,c3);
        LeaderDeck leaderDeck = new LeaderDeck(leaderCards);
        game.getPlayerById(0).setLeaderDeck(leaderDeck);
        game.getPlayerById(0).getPersonalBoard().getFaithTrack().setMarker(6);


        controller.DiscardLeaderCard(0, c1, false);
        ArrayList<LeaderCard> lctest = new ArrayList<LeaderCard>();
        lctest.add(0,c1);
        lctest.add(1,c3);
       assertEquals(7, game.getPlayerById(0).getPersonalBoard().getFaithTrack().getMarker());
       assertEquals(1,game.getPlayerById(0).getLeaderDeck().getCards().size());
       assertEquals(c2,game.getPlayerById(0).getLeaderDeck().getCards().get(0));

    }

    @Test
    @DisplayName("Play Discount Leader Card")
    void PlayDiscountLeader() {
        ArrayList<LeaderCard> leaderCards = new ArrayList<LeaderCard>();
        ArrayList<LeaderCard> hand = new ArrayList<LeaderCard>();
        ExtraDepotLCard inHand = new ExtraDepotLCard(2,1,ResourceType.COIN,ResourceType.SERVANT);
        hand.add(0,inHand);
        LeaderDeck unused = new LeaderDeck(hand);
        DiscountLCard c1 = new DiscountLCard(1,3,CardColor.BLUE,CardColor.YELLOW,ResourceType.COIN);
        leaderCards.add(0,c1);
        LeaderDeck leaderDeck = new LeaderDeck(leaderCards);
        game.getPlayerById(0).setLeaderDeck(unused);
        game.getPlayerById(0).getPersonalBoard().setActiveLeader(leaderDeck);
        game.getPlayerById(0).getPersonalBoard().getFaithTrack().setMarker(6);
        game.getPlayerById(0).setResDiscount1(ResourceType.SERVANT);
        game.getPlayerById(0).setResDiscount2(null);
        DevCard d1 = new DevCard(0,10,1,CardColor.BLUE,null,null,null);
        DevCard d2 =new DevCard(0,10,1,CardColor.PURPLE,null,null,null);
        ArrayList<DevCard> devc= new ArrayList<>();
        devc.add(d1);
        devc.add(d2);
        DevDeck dd = new DevDeck(2,0,devc);
        game.getPlayerById(0).getPersonalBoard().getCardSlot().set(0,dd);


        ArrayList<LeaderCard> myhand = new ArrayList<LeaderCard>();
        DiscountLCard dlc = new DiscountLCard(1,4, CardColor.BLUE,CardColor.PURPLE,ResourceType.COIN);
        myhand.add(dlc);
        LeaderDeck ddeck = new LeaderDeck(myhand);
        game.getPlayerById(0).setLeaderDeck(ddeck);


        controller.PlayLeaderCard(0, dlc);
     //   assertEquals(lctest, game.getPlayerById(0).getPersonalBoard().getActiveLeader().getCards());
        assertEquals(ResourceType.SERVANT,game.getPlayerById(0).getResDiscount1());
        assertEquals(ResourceType.COIN,game.getPlayerById(0).getResDiscount2());
        assertEquals(0,game.getPlayerById(0).getLeaderDeck().getCards().size());
        assertEquals(2,game.getPlayerById(0).getPersonalBoard().getActiveLeader().getCards().size());
        assertEquals(dlc,game.getPlayerById(0).getPersonalBoard().getActiveLeader().getCards().get(1));

    }


    @Test
    @DisplayName("Play Extra Depot Leader Card")
    void PlayExtraDepotLeader() {
        ArrayList<LeaderCard> leaderCards = new ArrayList<LeaderCard>();
        LeaderCard active = new ExtraProdLCard(3,2,CardColor.GREEN,ResourceType.COIN);

        leaderCards.add(0,active);
        LeaderDeck leaderDeck = new LeaderDeck(leaderCards);
        game.getPlayerById(0).getPersonalBoard().setActiveLeader(leaderDeck);
        game.getPlayerById(0).getPersonalBoard().getFaithTrack().setMarker(6);

       // game.getPlayerById(0).getPersonalBoard().getStrongbox().getInfinityShelf().set(0,new Shelf(ResourceType.COIN,5));



        ArrayList<LeaderCard> myhand = new ArrayList<LeaderCard>();
        ExtraDepotLCard ed = new ExtraDepotLCard (2,4,ResourceType.COIN, ResourceType.SHIELD);
        myhand.add(ed);
        LeaderDeck dd = new LeaderDeck(myhand);
        game.getPlayerById(0).setLeaderDeck(dd);


        controller.PlayLeaderCard(0, ed);
        assertEquals(ResourceType.SHIELD,game.getPlayerById(0).getPersonalBoard().getWarehouse().getShelves().get(3).getResType());
       assertEquals(null,game.getPlayerById(0).getPersonalBoard().getWarehouse().getShelves().get(4).getResType());
       assertEquals(active,game.getPlayerById(0).getPersonalBoard().getActiveLeader().getCards().get(0));
       assertEquals(ed,game.getPlayerById(0).getPersonalBoard().getActiveLeader().getCards().get(1));
       assertEquals(0,game.getPlayerById(0).getLeaderDeck().getCards().size());
       assertEquals(2,game.getPlayerById(0).getPersonalBoard().getActiveLeader().getCards().size());
       assertEquals(active,game.getPlayerById(0).getPersonalBoard().getActiveLeader().getCards().get(0));
       assertEquals(ed,game.getPlayerById(0).getPersonalBoard().getActiveLeader().getCards().get(1));


    }

    @Test
    @DisplayName("Play Extra Production Leader Card")
    void PlayExtraProdLeader() {
        ArrayList<LeaderCard> leaderCards = new ArrayList<LeaderCard>();
        LeaderCard c1 = new LeaderCard(3,3);
        leaderCards.add(0,c1);
        LeaderDeck leaderDeck = new LeaderDeck(leaderCards);
        game.getPlayerById(0).getPersonalBoard().setActiveLeader(leaderDeck);
        game.getPlayerById(0).getPersonalBoard().getFaithTrack().setMarker(6);
        game.getPlayerById(0).setInputExtraProduction1(ResourceType.STONE);
        game.getPlayerById(0).setInputExtraProduction2(null);
        DevCard d1 = new DevCard(0,10,2,CardColor.BLUE,null,null,null);
        ArrayList<DevCard> dev= new ArrayList<>();
        dev.add(d1);
        DevDeck dd = new DevDeck(1,0,dev);
        game.getPlayerById(0).getPersonalBoard().getCardSlot().set(0,dd);


        ArrayList<LeaderCard> hand = new ArrayList<LeaderCard>();
        ExtraProdLCard ep = new ExtraProdLCard (3,2,CardColor.BLUE, ResourceType.SHIELD);
        LeaderCard wc = new WhiteTrayLCard(4,1,ResourceType.COIN,CardColor.BLUE,CardColor.GREEN);
        hand.add(wc);
        hand.add(ep);
        LeaderDeck myHand = new LeaderDeck(hand);
        game.getPlayerById(0).setLeaderDeck(myHand);

        controller.PlayLeaderCard(0, ep);
        assertEquals(ResourceType.STONE,game.getPlayerById(0).getInputExtraProduction1());
        assertEquals(ResourceType.SHIELD,game.getPlayerById(0).getInputExtraProduction2());
        assertEquals(1,game.getPlayerById(0).getLeaderDeck().getCards().size());
        assertEquals(2,game.getPlayerById(0).getPersonalBoard().getActiveLeader().getCards().size());


    }


    @Test
    @DisplayName("Play White Tray Leader Card")
    void PlayWhiteTrayLeader() {
        ArrayList<LeaderCard> leaderCards = new ArrayList<LeaderCard>();
        ArrayList<LeaderCard> hand = new ArrayList<LeaderCard>();

        LeaderDeck leaderDeck = new LeaderDeck(leaderCards);
        game.getPlayerById(0).getPersonalBoard().setActiveLeader(leaderDeck);
        game.getPlayerById(0).getPersonalBoard().getFaithTrack().setMarker(6);
        game.getPlayerById(0).setWhiteConversion1(null);
        game.getPlayerById(0).setWhiteConversion2(null);
        DevCard d1 = new DevCard(0,10,1,CardColor.GREEN,null,null,null);
        DevCard d2 =new DevCard(0,10,1,CardColor.YELLOW,null,null,null);
        DevCard d3 =new DevCard(0,10,2,CardColor.YELLOW,null,null,null);
        ArrayList<DevCard> devgreen= new ArrayList<>();
        ArrayList<DevCard> devyellow=new ArrayList<>();
        devgreen.add(d1);
        devyellow.add(d2);
        devyellow.add(d3);
        DevDeck dd = new DevDeck(1,0,devgreen);
        DevDeck dd2= new DevDeck(2,0,devyellow);
        game.getPlayerById(0).getPersonalBoard().getCardSlot().set(0,dd);
        game.getPlayerById(0).getPersonalBoard().getCardSlot().set(1,dd2);



        WhiteTrayLCard wl = new WhiteTrayLCard (4,2, ResourceType.COIN, CardColor.GREEN, CardColor.YELLOW);
        hand.add(wl);
        LeaderDeck handDeck = new LeaderDeck(hand);
        game.getPlayerById(0).setLeaderDeck(handDeck);

        controller.PlayLeaderCard(0, wl);

        assertEquals(game.getPlayerById(0).getLeaderDeck().getCards().size(),0);
        assertEquals(game.getPlayerById(0).getPersonalBoard().getActiveLeader().getCards().size(),1);
        assertEquals(game.getPlayerById(0).getPersonalBoard().getActiveLeader().getCards().get(0),wl);
        assertEquals(ResourceType.COIN,game.getPlayerById(0).getWhiteConversion1());
        assertEquals(null,game.getPlayerById(0).getWhiteConversion2());

    }


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


