package it.polimi.ingsw.model;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

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
    @DisplayName("Marble clone")
    public void marbleClone()  {
        Marble original = new Marble(ResourceType.COIN);
        Marble clone = original.clone();

        assertEquals(original.getRes(), clone.getRes());

        clone.setRes(ResourceType.SHIELD);
        original.setRes(ResourceType.SERVANT);

        assertEquals(original.getRes(), ResourceType.SERVANT);
        assertEquals(clone.getRes(), ResourceType.SHIELD);
    }

    @Test
    @DisplayName("Market clone")
    @Disabled("todo")
    public void marketClone()  {

    }

    @Test
    @DisplayName("Devcard clone")
    public void devCardClone()  {
        DevCard original = new DevCard(0, 5, 1, CardColor.GREEN, new int[] {0,0,1,0}, new int[] {0,1,1,0}, new int[] {0,0,0,0,1});
        DevCard clone = original.clone();

        assertEquals(original.getColor(), clone.getColor());
        assertEquals(original.getType(), clone.getType());
        assertEquals(original.getLevel(), clone.getLevel());
        assertEquals(original.getCostRes()[0], clone.getCostRes()[0]);
        assertEquals(original.getCostRes()[1], clone.getCostRes()[1]);
        assertEquals(original.getCostRes()[2], clone.getCostRes()[2]);
        assertEquals(original.getCostRes()[3], clone.getCostRes()[3]);
        assertEquals(original.getInputRes()[0], clone.getInputRes()[0]);
        assertEquals(original.getInputRes()[1], clone.getInputRes()[1]);
        assertEquals(original.getInputRes()[2], clone.getInputRes()[2]);
        assertEquals(original.getInputRes()[3], clone.getInputRes()[3]);
        assertEquals(original.getOutputRes()[0], clone.getOutputRes()[0]);
        assertEquals(original.getOutputRes()[1], clone.getOutputRes()[1]);
        assertEquals(original.getOutputRes()[2], clone.getOutputRes()[2]);
        assertEquals(original.getOutputRes()[3], clone.getOutputRes()[3]);
        assertEquals(original.getOutputRes()[4], clone.getOutputRes()[4]);

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


}
