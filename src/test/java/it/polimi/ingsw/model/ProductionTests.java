package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import unused.Warehouse;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductionTests {
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
        player = new Player(0, "gigi", 4, true, new LeaderDeck(0, 1, new ArrayList<LeaderCard>()), 10, ResourceType.EMPTY, ResourceType.EMPTY, ResourceType.EMPTY, ResourceType.EMPTY, ResourceType.EMPTY, ResourceType.EMPTY, false, personalBoard);
    }

    @Test
    @DisplayName("Use basic production")
    void useBasicProductionFromWarehouse() {
        game.getPlayers().add(player);
        game.getPlayers().get(0).setId(0);
        game.getPlayerById(0).setPersonalBoard(personalBoard);
        game.getPlayerById(0).getPersonalBoard().setStrongbox(strongbox);
        game.getPlayerById(0).getPersonalBoard().setWarehouse(warehouse);

        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.COIN, 0);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.STONE, 1);
        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.STONE, 1);


        controller.useBasicProduction(0, ResourceType.COIN, ResourceType.STONE, ResourceType.SHIELD, true, false);

        assertEquals(0, game.getPlayerById(0).getPersonalBoard().getWarehouse().getResCount(ResourceType.COIN));
        assertEquals(1, game.getPlayerById(0).getPersonalBoard().getWarehouse().getResCount(ResourceType.STONE));

        assertEquals(1, game.getPlayerById(0).getPersonalBoard().getStrongbox().getResCount(ResourceType.SHIELD));
    }

    @Test
    @DisplayName("Use basic production")
    void useBasicProductionFromStrongbox() {
        game.getPlayers().add(player);
        game.getPlayers().get(0).setId(0);
        game.getPlayerById(0).setPersonalBoard(personalBoard);
        game.getPlayerById(0).getPersonalBoard().setStrongbox(strongbox);
        game.getPlayerById(0).getPersonalBoard().setWarehouse(warehouse);

        game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.COIN, 1);
        game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.STONE, 1);
        game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.STONE, 1);


        controller.useBasicProduction(0, ResourceType.COIN, ResourceType.STONE, ResourceType.SHIELD, false, true);

        assertEquals(0, game.getPlayerById(0).getPersonalBoard().getStrongbox().getResCount(ResourceType.COIN));
        assertEquals(1, game.getPlayerById(0).getPersonalBoard().getStrongbox().getResCount(ResourceType.STONE));

        assertEquals(1, game.getPlayerById(0).getPersonalBoard().getStrongbox().getResCount(ResourceType.SHIELD));
    }

    @Test
    @DisplayName("Use basic production")
    void useBasicProductionFromStrongboxAndFromWarehouse() {
        game.getPlayers().add(player);
        game.getPlayers().get(0).setId(0);
        game.getPlayerById(0).setPersonalBoard(personalBoard);
        game.getPlayerById(0).getPersonalBoard().setStrongbox(strongbox);
        game.getPlayerById(0).getPersonalBoard().setWarehouse(warehouse);

        game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.COIN, 0);
        game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.STONE, 1);
        game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.STONE, 1);

        //it takes the first resource from warehouse and the second from strongbox
        controller.useBasicProduction(0, ResourceType.COIN, ResourceType.STONE, ResourceType.SHIELD, true, true);

        assertEquals(0, game.getPlayerById(0).getPersonalBoard().getWarehouse().getResCount(ResourceType.COIN));
        assertEquals(1, game.getPlayerById(0).getPersonalBoard().getStrongbox().getResCount(ResourceType.STONE));

        assertEquals(1, game.getPlayerById(0).getPersonalBoard().getStrongbox().getResCount(ResourceType.SHIELD));
    }
}
