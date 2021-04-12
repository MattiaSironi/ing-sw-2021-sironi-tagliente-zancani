package it.polimi.ingsw.model;

/**
 * Class Game contains all the information about the game. It saves the number of players, a reference to the ID of the current player, one to the ID of the next player
 * a list of sorted player. It also contains the main Board of the game and attribute that refers to Lorenzo Il Magnifico if the game is single player.
 *
 * @author Lea Zancani
 * @see Board
 * @see Player
 * @see LorenzoIlMagnifico
 */

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.view.Nickname;

import java.util.ArrayList;

public class Game extends Observable<Nickname> {
    private int numPlayer;
    private int currPlayer;
    private int nextPlayer;
    private ArrayList<Player> players;
    private LorenzoIlMagnifico lori = null;
    private Board board = null;

    public Game(){
        players = new ArrayList<>();
    }

    public void createNewPlayer(Nickname nickname){
        this.players.add(new Player(nickname.getNickname(), nickname.getID()));
        notify(nickname);
    }



    public Game(int numPlayer, int currPlayer, int nextPlayer, ArrayList<Player> players, LorenzoIlMagnifico lori, Board board) {
        this.numPlayer = numPlayer;
        this.currPlayer = currPlayer;
        this.nextPlayer = nextPlayer;
        this.players = players;
        this.lori = lori;
        this.board = board;
    }


    public void setCurrPlayer(int currPlayer) {
        this.currPlayer = currPlayer;
    }

    public void setNextPlayer(int nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getNumPlayer() {
        return numPlayer;
    }

    public int getCurrPlayer() {
        return currPlayer;
    }

    public int getNextPlayer() {
        return nextPlayer;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void reportError(Nickname message){
        notify(message);
    }
}
