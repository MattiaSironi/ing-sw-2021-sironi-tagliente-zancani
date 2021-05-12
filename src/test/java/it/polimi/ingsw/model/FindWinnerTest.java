package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        game.getPlayerById(0).getPersonalBoard().setFavorTile(0);
        game.getPlayerById(1).getPersonalBoard().setFavorTile(0);
        game.getPlayerById(1).getPersonalBoard().setFavorTile(2);
        game.getPlayerById(2).getPersonalBoard().setFavorTile(0);
        game.getPlayerById(2).getPersonalBoard().setFavorTile(2);
        game.getPlayerById(2).getPersonalBoard().setFavorTile(1);
        assertEquals(2, game.findWinner().getId());




    }
    @Test
    @DisplayName("faith marker")
    public void faithMarker()  {
        popeFavor();
        game.getPlayerById(0).moveFaithMarkerPos(300);
        game.getPlayerById(1).moveFaithMarkerPos(20);
        game.getPlayerById(2).moveFaithMarkerPos(12);
        assertEquals(0, game.findWinner().getId());




    }


}


