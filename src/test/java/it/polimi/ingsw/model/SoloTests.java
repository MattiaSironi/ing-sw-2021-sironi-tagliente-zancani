package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
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
        ArrayList<Marble> hand = new ArrayList<>();
        hand.add(new Marble(ResourceType.COIN));
        game.setMarketHand(hand);
        assertEquals(0, game.getPlayerById(0).getPersonalBoard().getFaithTrack().getLoriPos());
        controller.placeRes(ResourceType.COIN, -1, 0, true, false);
        assertEquals(1, game.getPlayerById(0).getPersonalBoard().getFaithTrack().getLoriPos());

    }
}
