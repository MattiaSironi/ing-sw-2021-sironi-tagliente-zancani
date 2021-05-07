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

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.message.ActionMessages.*;
import it.polimi.ingsw.message.CommonMessages.*;
import it.polimi.ingsw.observer.Observable;


import java.util.ArrayList;
import java.util.Optional;

public class Game extends Observable<Message> implements Cloneable {
    private int numPlayer;
    private int currPlayer;
    private int nextPlayer;
    private ArrayList<Player> players;
    private LorenzoIlMagnifico lori = null;
    private Board board = null;

    public Game() {
        players = new ArrayList<>();
        board= new Board();
    }


    public void createNewPlayer(Nickname nickname) {
        this.players.add(new Player(nickname.getString(), nickname.getID()));
        notify(new IdMessage(this.players.size()));
    }

    public void printPlayerNickname(int ID) {
        String realNick = null;
        for (Player p : this.players) {
            if (p.getId() == ID) {
                realNick = p.getNickname();
            }
        }
        notify(new Nickname(realNick
                , ID, true));
    }


    public Game(int numPlayer, int currPlayer, int nextPlayer, ArrayList<Player> players, LorenzoIlMagnifico lori, Board board) {
        this.numPlayer = numPlayer;
        this.currPlayer = currPlayer;
        this.nextPlayer = nextPlayer;
        this.players = players;
        this.lori = lori;
        this.board = board;
    }

    public void addDevCardToPlayer(int ID, DevCard d, int pos ){
        getPlayerById(ID).getPersonalBoard().addDevCard(d, pos, ID);
        notify(new ObjectMessage(getPlayerById(ID).getPersonalBoard().getCardSlot(), 5, ID));
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

    public void setNumPlayer(int numPlayer) {
        this.numPlayer = numPlayer;
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

    public void reportError(ErrorMessage message) {
        notify(message);
    }

    public void sendObject(ObjectMessage message) {
        notify(message);
    }

    public void sendResources(ResourceListMessage message)  {
        notify(message);
    }

    public Board getBoard() {
        return board;
    }

    public void setLori(LorenzoIlMagnifico lori) {
        this.lori = lori;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Player getPlayerById(int ID) {
        Player player = null;
        for (Player p : this.players) {
            if (p.getId() == ID) {
                player = p;
            }
        }
        return player;
    }

    public Game clone() throws CloneNotSupportedException {
        Game clone = new Game (this.numPlayer, this.currPlayer, this.nextPlayer, this.players, this.lori, this.board);
        return clone;
    }

    public void printPlayers(int ID)  {
        for (Player p : this.players)  {
            if (p.getId()== ID) notify(new Nickname(p.getNickname(), p.getId(), true));
            else notify(new Nickname(p.getNickname(), p.getId(), false));

        }
    }
}
