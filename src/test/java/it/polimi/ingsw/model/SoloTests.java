package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        int count = 0;

        game.discardTwoDevCards(CardColor.GREEN);
        game.discardTwoDevCards(CardColor.GREEN);
        game.discardTwoDevCards(CardColor.YELLOW);
        game.discardTwoDevCards(CardColor.YELLOW);
        game.discardTwoDevCards(CardColor.BLUE);
        game.discardTwoDevCards(CardColor.BLUE);
        game.discardTwoDevCards(CardColor.PURPLE);
        game.discardTwoDevCards(CardColor.PURPLE);
        game.discardTwoDevCards(CardColor.GREEN);
        game.discardTwoDevCards(CardColor.GREEN);
        game.discardTwoDevCards(CardColor.YELLOW);
        game.discardTwoDevCards(CardColor.YELLOW);
        game.discardTwoDevCards(CardColor.BLUE);
        game.discardTwoDevCards(CardColor.BLUE);
        game.discardTwoDevCards(CardColor.PURPLE);
        game.discardTwoDevCards(CardColor.PURPLE);
        game.discardTwoDevCards(CardColor.GREEN);
        game.discardTwoDevCards(CardColor.GREEN);
        game.discardTwoDevCards(CardColor.YELLOW);
        game.discardTwoDevCards(CardColor.YELLOW);
        game.discardTwoDevCards(CardColor.BLUE);
        game.discardTwoDevCards(CardColor.BLUE);
        game.discardTwoDevCards(CardColor.PURPLE);
        game.discardTwoDevCards(CardColor.PURPLE);
        game.discardTwoDevCards(CardColor.GREEN);
        game.discardTwoDevCards(CardColor.GREEN);
        game.discardTwoDevCards(CardColor.YELLOW);
        game.discardTwoDevCards(CardColor.YELLOW);
        game.discardTwoDevCards(CardColor.BLUE);
        game.discardTwoDevCards(CardColor.BLUE);
        game.discardTwoDevCards(CardColor.PURPLE);
        game.discardTwoDevCards(CardColor.PURPLE);
        game.discardTwoDevCards(CardColor.GREEN);
        game.discardTwoDevCards(CardColor.GREEN);
        game.discardTwoDevCards(CardColor.YELLOW);
        game.discardTwoDevCards(CardColor.YELLOW);
        game.discardTwoDevCards(CardColor.BLUE);
        game.discardTwoDevCards(CardColor.BLUE);
        game.discardTwoDevCards(CardColor.PURPLE);
        game.discardTwoDevCards(CardColor.PURPLE);
        game.discardTwoDevCards(CardColor.GREEN);
        game.discardTwoDevCards(CardColor.GREEN);
        game.discardTwoDevCards(CardColor.YELLOW);
        game.discardTwoDevCards(CardColor.YELLOW);
        game.discardTwoDevCards(CardColor.BLUE);
        game.discardTwoDevCards(CardColor.BLUE);
        game.discardTwoDevCards(CardColor.PURPLE);
        game.discardTwoDevCards(CardColor.PURPLE);
        game.discardTwoDevCards(CardColor.GREEN);
        game.discardTwoDevCards(CardColor.GREEN);
        game.discardTwoDevCards(CardColor.YELLOW);
        game.discardTwoDevCards(CardColor.YELLOW);
        game.discardTwoDevCards(CardColor.BLUE);
        game.discardTwoDevCards(CardColor.BLUE);
        game.discardTwoDevCards(CardColor.PURPLE);
        game.discardTwoDevCards(CardColor.PURPLE);

        for (DevDeck dd : game.getBoard().getMatrix().getDevDecks()){
            count += dd.getCards().size();
        }

        assertEquals(0, count);
    }
}
