package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.Nickname;

import java.util.ArrayList;

public class Controller implements Observer<Nickname> {
    private Game game;

    public Controller(Game game) {
        this.game = game;
    }

    public void setNickname(Nickname nickname){
        String nick= nickname.getNickname();
        boolean found = false;
        ArrayList<Player> p = game.getPlayers();
        for(Player pl : p){
            if(pl.getNickname().equals(nick)){
                found = true;
                break;
            }

        }
        if(found){
            game.reportError(new Nickname("ERRORE505", nickname.getID()));
        }
        else
        game.createNewPlayer(nickname);
    }

    @Override
    public void update(Nickname message) {
        setNickname(message);
    }
}
