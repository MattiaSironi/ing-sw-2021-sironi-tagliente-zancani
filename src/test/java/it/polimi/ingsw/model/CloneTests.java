package it.polimi.ingsw.model;

import org.apache.commons.io.input.ClassLoaderObjectInputStream;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Mattia Sironi
 */
public class CloneTests {

    @Test
    @DisplayName("Turn clone")
    public void turnClone()  {
        Turn original = new Turn();
        original.setPlayerPlayingID(0);
        original.setPhase(ActionPhase.MARKET);
        original.setError(false);
        original.setErrorType(null);
        Turn clone = original.clone();
        assertEquals(original.getPlayerPlayingID(), clone.getPlayerPlayingID());
        assertEquals(original.getPhase(), clone.getPhase());
        assertEquals(original.isError(), clone.isError());
        assertEquals(original.getErrorType(), clone.getErrorType());

        clone.setError(true);
        clone.setErrorType(ErrorList.INVALID_MOVE);
        clone.setPlayerPlayingID(10);
        clone.setPhase(ActionPhase.GAME_OVER);

        assertEquals(original.getPlayerPlayingID(), 0);
        assertEquals(original.getPhase(), ActionPhase.MARKET);
        assertEquals(original.isError(), false);
        assertEquals(original.getErrorType(), null);

        assertEquals(clone.getPlayerPlayingID(), 10);
        assertEquals(clone.getPhase(), ActionPhase.GAME_OVER);
        assertEquals(clone.isError(), true);
        assertEquals(clone.getErrorType(), ErrorList.INVALID_MOVE);




    }

    @Test
    @DisplayName("Communication clone")
    public void communicationClone()  {
        Communication original = new Communication();
        original.setAddresseeID(0);
        original.setCommunication(CommunicationList.FIRST);

        Communication clone = original.clone();

        assertEquals(original.getAddresseeID(), clone.getAddresseeID());
        assertEquals(original.getCommunication(), clone.getCommunication());

        original.setAddresseeID(10);
        original.setCommunication(CommunicationList.SECOND);

        assertEquals(original.getAddresseeID(), 10);
        assertEquals(original.getCommunication(), CommunicationList.SECOND);
        assertEquals(clone.getAddresseeID(), 0);
        assertEquals(clone.getCommunication(), CommunicationList.FIRST);



    }



    @Test
    @DisplayName("Market clone")
    public void marketClone()  {

        Game game = new Game(true, 0);
        Market clone = game.getBoard().getMarket().clone();

        ArrayList<Marble> handclone = new ArrayList<>();
        handclone.add(new Marble(ResourceType.COIN));
        handclone.add(new Marble(ResourceType.SHIELD));
        clone.setHand(handclone);

        assertEquals(0, game.getBoard().getMarket().getHand().size());
        assertEquals(2, clone.getHand().size());

    }


    @Test
    @DisplayName("DevDeck clone")
    public void devDeckClone() {
        Game game = new Game(true, 0);
        DevDeck clone = game.getBoard().getDevDecks().get(0).clone();
        assertEquals(clone.getCards().size(), 4);
        int i=0;
        for (DevCard original : game.getBoard().getDevDecks().get(0).getCards()) {
            assertEquals(original.getColor(), clone.getCards().get(i).getColor());
            assertEquals(original.getType(), clone.getCards().get(i).getType());
            assertEquals(original.getLevel(), clone.getCards().get(i).getLevel());
            assertEquals(original.getCostRes()[0], clone.getCards().get(i).getCostRes()[0]);
            assertEquals(original.getCostRes()[1], clone.getCards().get(i).getCostRes()[1]);
            assertEquals(original.getCostRes()[2], clone.getCards().get(i).getCostRes()[2]);
            assertEquals(original.getCostRes()[3], clone.getCards().get(i).getCostRes()[3]);
            assertEquals(original.getInputRes()[0], clone.getCards().get(i).getInputRes()[0]);
            assertEquals(original.getInputRes()[1], clone.getCards().get(i).getInputRes()[1]);
            assertEquals(original.getInputRes()[2], clone.getCards().get(i).getInputRes()[2]);
            assertEquals(original.getInputRes()[3], clone.getCards().get(i).getInputRes()[3]);
            assertEquals(original.getOutputRes()[0], clone.getCards().get(i).getOutputRes()[0]);
            assertEquals(original.getOutputRes()[1], clone.getCards().get(i).getOutputRes()[1]);
            assertEquals(original.getOutputRes()[2], clone.getCards().get(i).getOutputRes()[2]);
            assertEquals(original.getOutputRes()[3], clone.getCards().get(i).getOutputRes()[3]);
            assertEquals(original.getOutputRes()[4], clone.getCards().get(i).getOutputRes()[4]);
            i++;
        }

        clone.getCards().remove(0);
        assertEquals(clone.getCards().size(), 3);
        assertEquals(game.getBoard().getDevDecks().get(0).getCards().size(), 4);
    }


    @Test
    @DisplayName("Game clone")
    public void gameClone(){
        Game game = new Game(0,1,0,0,true,false,false,false);

        Player p1 = new Player(0,"gigi");
        Strongbox strongbox = new Strongbox();
        ShelfWarehouse warehouse = new ShelfWarehouse();
        Shelf s1 = new Shelf(ResourceType.COIN,0);
        Shelf s2 = new Shelf(ResourceType.STONE,2);
        Shelf s3 = new Shelf(ResourceType.SERVANT,1);
        Shelf s4 = new Shelf(ResourceType.SHIELD,3);
        Shelf s5 = new Shelf(null,0);
        ArrayList strArray = new ArrayList();
        ArrayList warArray = new ArrayList();
        strArray.add(0,s1);
        strArray.add(1,s2);
        strArray.add(2,s3);
        strArray.add(3,s4);
        warArray.add(0,s1);
        warArray.add(1,s2);
        warArray.add(2,s3);
        warArray.add(3,s5);
        warArray.add(4,s5);
        strongbox.setInfinityShelf(strArray);
        warehouse.setShelves(warArray);
        p1.setPersonalBoard(new PersonalBoard());
        p1.getPersonalBoard().setStrongbox(strongbox);
        p1.getPersonalBoard().setWarehouse(warehouse);
        int[] cost0 = {1,2,3,4};
        int[] input0 = {1,1,0,0};
        int[] output0 = {0,0,2,0,0};
        DevCard d0 = new DevCard(1,2,3,CardColor.BLUE,cost0,input0,output0);
        ArrayList devDeckArray = new ArrayList();
        ArrayList<DevCard> vett = new ArrayList();
        vett.add(d0);
        DevDeck dd1 = new DevDeck(vett);
        devDeckArray.add(dd1);
        p1.getPersonalBoard().setCardSlot(devDeckArray);
        LeaderCard active = new ExtraDepotLCard(2,1,ResourceType.COIN,ResourceType.SERVANT);
        LeaderCard inactive = new WhiteTrayLCard(4,3,ResourceType.COIN,CardColor.BLUE,CardColor.PURPLE);
        ArrayList actLeader = new ArrayList();
        ArrayList inactLeader = new ArrayList();
        actLeader.add(active);
        inactLeader.add(inactive);
        LeaderDeck attivi = new LeaderDeck(actLeader);
        LeaderDeck disattivi = new LeaderDeck(inactLeader);
        p1.getPersonalBoard().setFaithTrack(new FaithTrack(2));
        p1.getPersonalBoard().setLeaderChosen(new ExtraProdLCard(3,3,CardColor.GREEN,ResourceType.COIN));
        p1.getPersonalBoard().setActiveLeader(attivi);
        p1.setLeaderDeck(disattivi);

        game.getPlayers().add(p1);

        int[] cost = {1,2,3,4};
        int[] input = {1,1,0,0};
        int[] output = {0,0,2,0,0};
        DevCard d1 = new DevCard(1,2,3,CardColor.BLUE,cost,input,output);
        ArrayList devVett = new ArrayList();
        devVett.add(d1);
        Market market = new Market();
        DevelopmentCardMatrix matrix = new DevelopmentCardMatrix(devDeckArray);
        matrix.setChosenCard(d0);
        matrix.setChosenIndex(1);
        ArrayList resVett = new ArrayList();
        resVett.add(ResourceType.COIN);
        resVett.add(ResourceType.STONE);
        matrix.setResToPay(resVett);
        ArrayList<SoloActionToken> tokenArray = new ArrayList<>();
        tokenArray.add(new SoloActionToken(CardColor.BLUE,false,true));

        game.getBoard().setDevDecks(devDeckArray);
        game.getBoard().setLeaderDeck(disattivi);
        game.getBoard().setMarket(market);
        game.getBoard().setMatrix(matrix);
        game.getBoard().setTokenArray(tokenArray);
        Game clone = game.clone();


        assertEquals(clone.getGameID(),game.getGameID());
        assertEquals(clone.getNumPlayer(),game.getNumPlayer());
        assertEquals(clone.getCurrPlayer(),game.getCurrPlayer());
        assertEquals(clone.isFirstvatican(),game.isFirstvatican());
        assertEquals(clone.isSecondvatican(),game.isSecondvatican());
        assertEquals(clone.isThirdvatican(),game.isThirdvatican());
        assertEquals(clone.isGameOver(),game.isGameOver());
        assertEquals(clone.getPlayers().get(0).getNickname(),game.getPlayers().get(0).getNickname());
        assertEquals(clone.getPlayerById(0).getLeaderDeck().getCards().get(0),game.getPlayerById(0).getLeaderDeck().getCards().get(0));
        assertEquals(clone.getPlayerById(0).getPersonalBoard().getActiveLeader().getCards().get(0),game.getPlayerById(0).getPersonalBoard().getActiveLeader().getCards().get(0));
        assertEquals(clone.getPlayerById(0).getPersonalBoard().getStrongbox().getResCount(ResourceType.SHIELD),game.getPlayerById(0).getPersonalBoard().getStrongbox().getResCount(ResourceType.SHIELD));
        assertEquals(clone.getPlayerById(0).getPersonalBoard().getWarehouse().getResCount(ResourceType.COIN),game.getPlayerById(0).getPersonalBoard().getWarehouse().getResCount(ResourceType.COIN));
        assertEquals(clone.getBoard().getMarket().getMarbleOut(),game.getBoard().getMarket().getMarbleOut());
        assertEquals(clone.getBoard().getDevDecks().get(0).getCards().get(0),game.getBoard().getDevDecks().get(0).getCards().get(0));
        assertEquals(clone.getBoard().getMatrix().getChosenCard(),game.getBoard().getMatrix().getChosenCard());

    }

    @Test
    @DisplayName("Test clone() of Player class")
    public void playerCloneTest(){
        Player p1 = new Player(0,"gigi");
        Strongbox strongbox = new Strongbox();
        ShelfWarehouse warehouse = new ShelfWarehouse();
        Shelf s1 = new Shelf(ResourceType.COIN,0);
        Shelf s2 = new Shelf(ResourceType.STONE,2);
        Shelf s3 = new Shelf(ResourceType.SERVANT,1);
        Shelf s4 = new Shelf(ResourceType.SHIELD,3);
        Shelf s5 = new Shelf(null,0);
        ArrayList strArray = new ArrayList();
        ArrayList warArray = new ArrayList();
        strArray.add(0,s1);
        strArray.add(1,s2);
        strArray.add(2,s3);
        strArray.add(3,s4);
        warArray.add(0,s1);
        warArray.add(1,s2);
        warArray.add(2,s3);
        warArray.add(3,s5);
        warArray.add(4,s5);
        strongbox.setInfinityShelf(strArray);
        warehouse.setShelves(warArray);
        p1.setPersonalBoard(new PersonalBoard());
        p1.getPersonalBoard().setStrongbox(strongbox);
        p1.getPersonalBoard().setWarehouse(warehouse);
        int[] cost0 = {1,2,3,4};
        int[] input0 = {1,1,0,0};
        int[] output0 = {0,0,2,0,0};
        DevCard d0 = new DevCard(1,2,3,CardColor.BLUE,cost0,input0,output0);
        ArrayList devDeckArray = new ArrayList();
        ArrayList<DevCard> vett = new ArrayList();
        vett.add(d0);
        DevDeck dd1 = new DevDeck(vett);
        devDeckArray.add(dd1);
        p1.getPersonalBoard().setCardSlot(devDeckArray);
        LeaderCard active = new ExtraDepotLCard(2,1,ResourceType.COIN,ResourceType.SERVANT);
        LeaderCard inactive = new WhiteTrayLCard(4,3,ResourceType.COIN,CardColor.BLUE,CardColor.PURPLE);
        ArrayList actLeader = new ArrayList();
        ArrayList inactLeader = new ArrayList();
        actLeader.add(active);
        inactLeader.add(inactive);
        LeaderDeck attivi = new LeaderDeck(actLeader);
        LeaderDeck disattivi = new LeaderDeck(inactLeader);
        p1.getPersonalBoard().setFaithTrack(new FaithTrack(2));
        p1.getPersonalBoard().setLeaderChosen(new ExtraProdLCard(3,3,CardColor.GREEN,ResourceType.COIN));
        p1.getPersonalBoard().setActiveLeader(attivi);
        p1.setLeaderDeck(disattivi);
        Player clone = p1.clone();

        assertEquals(clone.getNickname(),p1.getNickname());
        assertEquals(clone.getLeaderDeck().getCards().get(0),p1.getLeaderDeck().getCards().get(0));
        assertEquals(clone.getPersonalBoard().getActiveLeader().getCards().get(0),p1.getPersonalBoard().getActiveLeader().getCards().get(0));
        assertEquals(clone.getPersonalBoard().getStrongbox().getResCount(ResourceType.SHIELD),p1.getPersonalBoard().getStrongbox().getResCount(ResourceType.SHIELD));
        assertEquals(clone.getPersonalBoard().getWarehouse().getResCount(ResourceType.COIN),p1.getPersonalBoard().getWarehouse().getResCount(ResourceType.COIN));
        assertEquals(clone.getId(), p1.getId());
        assertEquals(clone.getInputExtraProduction1(), p1.getInputExtraProduction1());
        assertEquals(clone.getInputExtraProduction2(), p1.getInputExtraProduction2());
        assertEquals(clone.isReady(), p1.isReady());
        assertEquals(clone.getLeaderCardsToDiscard(), p1.getLeaderCardsToDiscard());
        assertEquals(clone.getWhiteConversion1(), p1.getWhiteConversion1());
        assertEquals(clone.getVictoryPoints(), p1.getVictoryPoints());

    }




}
