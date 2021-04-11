package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

import java.util.ArrayList;

public class Controller implements Observer<String> {
    private Game game;

    public Controller(Game game) {
        this.game = game;
    }

    public void setNickname(String nickname){
        boolean found = false;
        ArrayList<Player> p = game.getPlayers();
        for(Player pl : p){
            if(pl.getNickname().equals(nickname)){
                found = true;
                break;
            }
        }
        if(found){
            //game.reportError("This nickname is already chosen!");
        }
        game.createNewPlayer(nickname);
    }

    @Override
    public void update(String message) {
        setNickname(message);
    }
}
