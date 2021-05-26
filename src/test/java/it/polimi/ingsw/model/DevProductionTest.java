package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DevProductionTest {
    Controller controller;
    Game game;
    PersonalBoard personalBoard;
    Strongbox strongbox;
    ShelfWarehouse warehouse;
    Player player;

    @BeforeEach
    void init() {
        game = new Game(false, 0);
        controller = new Controller(game);
        personalBoard = new PersonalBoard();
        strongbox = new Strongbox();
        warehouse = new ShelfWarehouse();
        player = new Player(0, "lea");
        game.getPlayers().add(player);
        game.getPlayers().get(0).setId(0);
        game.getPlayerById(0).setPersonalBoard(personalBoard);

        Shelf coins = new Shelf(ResourceType.COIN,3);
        Shelf stones = new Shelf(ResourceType.STONE,2);
        Shelf servants = new Shelf(ResourceType.SERVANT,4);
        Shelf shields = new Shelf(ResourceType.SHIELD,1);
        ArrayList<Shelf> vett = new ArrayList<>();
        vett.add(0,coins);
        vett.add(1,stones);
        vett.add(2,servants);
        vett.add(3,shields);
        game.getPlayerById(0).getPersonalBoard().getStrongbox().setInfinityShelf(vett);


        Shelf one = new Shelf(ResourceType.STONE, 1);
        Shelf two = new Shelf(ResourceType.EMPTY, 2);
        Shelf three = new Shelf(ResourceType.COIN, 2);
        warehouse.getShelves().add(0,one);
        warehouse.getShelves().add(1,two);
        warehouse.getShelves().add(2,three);
        game.getPlayerById(0).getPersonalBoard().setWarehouse(warehouse);

    }

    @Test
    @DisplayName("DevProd paid with Strongbox only")
        void PayDevProdWithStrongbox(){
        int[] cost = {1,2,3,4};
        int[] input = {1,1,0,0};
        int[] output = {0,0,2,0,0};

        DevCard d = new DevCard(3,1,CardColor.GREEN,cost,input,output);

        controller.activateDevProduction(0,d,0,1,0,1);


        assertEquals(2, this.game.getPlayerById(0).getPersonalBoard().getStrongbox().getInfinityShelf().get(0).getCount());
        assertEquals(1, this.game.getPlayerById(0).getPersonalBoard().getStrongbox().getInfinityShelf().get(1).getCount());
        assertEquals(4, this.game.getPlayerById(0).getPersonalBoard().getStrongbox().getInfinityShelf().get(2).getCount());
        assertEquals(1, this.game.getPlayerById(0).getPersonalBoard().getStrongbox().getInfinityShelf().get(3).getCount());

          //verifica produzione output
        assertEquals(0,this.game.getPlayerById(0).getPersonalBoard().getStrongbox().getEarnedCoin());
        assertEquals(0,this.game.getPlayerById(0).getPersonalBoard().getStrongbox().getEarnedStone());
        assertEquals(2,this.game.getPlayerById(0).getPersonalBoard().getStrongbox().getEarnedServant());
        assertEquals(0,this.game.getPlayerById(0).getPersonalBoard().getStrongbox().getEarnedShield());
    }






}



