package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.message.ActionMessages.EndTurnMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * SoloTests contains test on Lorenzo Il Magnifico's actions.
 * @author Mattia Sironi
 */
public class SoloTests {

    Game game;
    Controller controller;

    @BeforeEach()
    public void init()  {
        game = new Game(true, 0);
        controller = new Controller(game);
        game.getPlayers().add(new Player(0, "GIGI"));
        game.getPlayerById(0).getPersonalBoard().getFaithTrack().setLoriPos(0);
        game.setTurn(0, ActionPhase.WAITING_FOR_ACTION);

    }


    @Test
    @DisplayName("parsing GSON correctly")
    public void GSON()  {
        ArrayList<SoloActionToken> sat = game.getBoard().getTokenArray();
        for (SoloActionToken s : sat)  {
            for (SoloActionToken ss: sat.subList(
                   sat.indexOf(s)+1, sat.size()))  {
                assertEquals(false, s.equals(ss));
            }
        }
    }

    @Test
    @DisplayName("DiscardDevCard Test")
    public void discard(){

        game.discardTwoDevCards(CardColor.GREEN);
        game.discardTwoDevCards(CardColor.YELLOW);
        game.discardTwoDevCards(CardColor.PURPLE);
        game.discardTwoDevCards(CardColor.YELLOW);
        game.discardTwoDevCards(CardColor.GREEN);
        game.discardTwoDevCards(CardColor.BLUE);
        game.discardTwoDevCards(CardColor.GREEN);
        game.discardTwoDevCards(CardColor.BLUE);
        game.discardTwoDevCards(CardColor.GREEN);
        game.discardTwoDevCards(CardColor.BLUE);
        game.discardTwoDevCards(CardColor.YELLOW);
        game.discardTwoDevCards(CardColor.PURPLE);
        game.discardTwoDevCards(CardColor.GREEN);
        game.discardTwoDevCards(CardColor.GREEN);
        game.discardTwoDevCards(CardColor.PURPLE);
        game.discardTwoDevCards(CardColor.GREEN);
        game.discardTwoDevCards(CardColor.GREEN);
        game.discardTwoDevCards(CardColor.YELLOW);
        game.discardTwoDevCards(CardColor.GREEN);
        game.discardTwoDevCards(CardColor.BLUE);
        game.discardTwoDevCards(CardColor.PURPLE);
        game.discardTwoDevCards(CardColor.YELLOW);
        game.discardTwoDevCards(CardColor.PURPLE);
        game.discardTwoDevCards(CardColor.PURPLE);
        game.discardTwoDevCards(CardColor.PURPLE);
        game.discardTwoDevCards(CardColor.PURPLE);
        game.discardTwoDevCards(CardColor.PURPLE);
        game.discardTwoDevCards(CardColor.PURPLE);
        game.discardTwoDevCards(CardColor.PURPLE);
        game.discardTwoDevCards(CardColor.PURPLE);
        game.discardTwoDevCards(CardColor.PURPLE);
        game.discardTwoDevCards(CardColor.PURPLE);
        game.discardTwoDevCards(CardColor.PURPLE);
        game.discardTwoDevCards(CardColor.GREEN);
        game.discardTwoDevCards(CardColor.BLUE);
        game.discardTwoDevCards(CardColor.YELLOW);
        game.discardTwoDevCards(CardColor.PURPLE);
        game.discardTwoDevCards(CardColor.GREEN);
        game.discardTwoDevCards(CardColor.YELLOW);
        game.discardTwoDevCards(CardColor.BLUE);
        game.discardTwoDevCards(CardColor.YELLOW);
        game.discardTwoDevCards(CardColor.GREEN);
        game.discardTwoDevCards(CardColor.YELLOW);
        game.discardTwoDevCards(CardColor.GREEN);
        game.discardTwoDevCards(CardColor.YELLOW);
        game.discardTwoDevCards(CardColor.BLUE);
        game.discardTwoDevCards(CardColor.GREEN);
        game.discardTwoDevCards(CardColor.BLUE);
        game.discardTwoDevCards(CardColor.GREEN);

        assertTrue(game.checkColumnEmpty());

    }

    @Test
    @DisplayName("giving points to Lori discarding a res")
    public void discardRes() {
        game.getPlayerById(0).getPersonalBoard().getFaithTrack().setLoriPos(23);
        ArrayList<Marble> hand = new ArrayList<>();
        hand.add(new Marble(ResourceType.COIN));
        game.setMarketHand(hand);
        assertEquals(23, game.getPlayerById(0).getPersonalBoard().getFaithTrack().getLoriPos());
        controller.placeRes(ResourceType.COIN, -1, 0, true, false);
        assertEquals(24, game.getPlayerById(0).getPersonalBoard().getFaithTrack().getLoriPos());
        assertEquals(-1 , game.findSoloWinner());

    }

    @Test
    @DisplayName("player wins")
    public void playerWins() {
        game.setFirstVatican(true);
        game.setSecondVatican(true);
        game.moveFaithPosByID(0,24);
        controller.update(new EndTurnMessage(0));
        assertEquals(true, game.isGameOver());
        assertEquals(0, game.findSoloWinner());

    }

    @Test
    @DisplayName("Lori wins")
    public void loriWins() {
        game.setFirstVatican(true);
        game.setSecondVatican(true);
        assertEquals(false, game.isGameOver());
        game.moveLoriPos(24);
        assertEquals(true, game.isGameOver());
        controller.update(new EndTurnMessage(0));
        assertEquals(-1, game.findSoloWinner());

    }

    @Test
    @DisplayName("Lori action, move 1 and shuffle")
    public void loriAction1() {
        game.getBoard().getTokenArray().set(0, new SoloActionToken(null, false , true));
        controller.update(new EndTurnMessage(0));
        assertTrue(game.getPlayerById(0).getPersonalBoard().getFaithTrack().getLoriPos()==1);
        assertTrue(game.getBoard().getTokenArray().size()==7);

    }
    @Test
    @DisplayName("Lori action, move 2 ")
    public void loriAction2() {
        game.getBoard().getTokenArray().set(0, new SoloActionToken(null, true , false));
        controller.update(new EndTurnMessage(0));
        assertTrue(game.getPlayerById(0).getPersonalBoard().getFaithTrack().getLoriPos()==2);
        assertTrue(game.getBoard().getTokenArray().size()==6);

    }

    @Test
    @DisplayName("Lori action, discard 2 green ")
    public void loriAction3() {
        game.getBoard().getTokenArray().set(0, new SoloActionToken(CardColor.GREEN, false, false));
        controller.update(new EndTurnMessage(0));
        assertTrue(game.getBoard().getDevDecks().get(0).getCards().size()==2);
        assertTrue(game.getBoard().getTokenArray().size()==6);

    }
    @Test
    @DisplayName("Lori action, discard 2 blue ")
    public void loriAction4() {
        game.getBoard().getTokenArray().set(0, new SoloActionToken(CardColor.BLUE, false, false));
        controller.update(new EndTurnMessage(0));
        assertTrue(game.getBoard().getDevDecks().get(1).getCards().size()==2);
        assertTrue(game.getBoard().getTokenArray().size()==6);

    }
    @Test
    @DisplayName("Lori action, discard 2 yellow ")
    public void loriAction5() {
        game.getBoard().getTokenArray().set(0, new SoloActionToken(CardColor.YELLOW, false, false));
        controller.update(new EndTurnMessage(0));
        assertTrue(game.getBoard().getDevDecks().get(2).getCards().size()==2);
        assertTrue(game.getBoard().getTokenArray().size()==6);

    }
    @Test
    @DisplayName("Lori action, discard 2 purple ")
    public void loriAction6() {
        game.getBoard().getTokenArray().set(0, new SoloActionToken(CardColor.PURPLE, false, false));
        controller.update(new EndTurnMessage(0));
        assertTrue(game.getBoard().getDevDecks().get(3).getCards().size()==2);
        assertTrue(game.getBoard().getTokenArray().size()==6);

    }

    @Test
    @DisplayName("Lori action, discard all purples")
    public void loriAction7() {
        game.getBoard().getTokenArray().set(0, new SoloActionToken(CardColor.PURPLE, false, false));
        game.getBoard().getTokenArray().set(1, new SoloActionToken(CardColor.PURPLE, false, false));
        game.getBoard().getTokenArray().set(2, new SoloActionToken(CardColor.PURPLE, false, false));
        game.getBoard().getTokenArray().set(3, new SoloActionToken(CardColor.PURPLE, false, false));
        game.getBoard().getTokenArray().set(4, new SoloActionToken(CardColor.PURPLE, false, false));
        game.getBoard().getTokenArray().set(5, new SoloActionToken(CardColor.PURPLE, false, false));
        controller.update(new EndTurnMessage(0));
        assertTrue(game.getBoard().getDevDecks().get(3).getCards().size()==2);
        assertTrue(game.getBoard().getTokenArray().size()==6);
        controller.update(new EndTurnMessage(0));
        assertTrue(game.getBoard().getDevDecks().get(3).getCards().size()==0);
        assertTrue(game.getBoard().getTokenArray().size()==5);
        controller.update(new EndTurnMessage(0));
        assertTrue(game.getBoard().getDevDecks().get(7).getCards().size()==2);
        assertTrue(game.getBoard().getTokenArray().size()==4);
        controller.update(new EndTurnMessage(0));
        assertTrue(game.getBoard().getDevDecks().get(7).getCards().size()==0);
        assertTrue(game.getBoard().getTokenArray().size()==3);
        controller.update(new EndTurnMessage(0));
        assertTrue(game.getBoard().getDevDecks().get(11).getCards().size()==2);
        assertTrue(game.getBoard().getTokenArray().size()==2);
        controller.update(new EndTurnMessage(0));
        assertTrue(game.getBoard().getDevDecks().get(11).getCards().size()==0);
        assertTrue(game.getBoard().getTokenArray().size()==1);
        assertTrue(game.isGameOver());

    }






}
//    assertTrue(game.getPlayerById(0).getPersonalBoard().getFaithTrack().getLoriPos() ==1 || game.getPlayerById(0).getPersonalBoard().getFaithTrack().getLoriPos() ==2 ||
//        game.getBoard().getMatrix().getDevDecks().get(0).getCards().size() == 2 || game.getBoard().getMatrix().getDevDecks().get(1).getCards().size() == 2 ||
//        game.getBoard().getMatrix().getDevDecks().get(2).getCards().size() == 2 || game.getBoard().getMatrix().getDevDecks().get(3).getCards().size() == 2);