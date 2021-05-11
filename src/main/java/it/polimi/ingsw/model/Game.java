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
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.OptionalInt;

public class Game extends Observable<Message> implements Cloneable {
    private int numPlayer;
    private int currPlayer;
    private int nextPlayer;
    private ArrayList<Player> players;
    private LorenzoIlMagnifico lori = null;
    private Board board;
    private boolean firstvatican = false;
    private boolean secondvatican = false;
    private boolean thirdvatican = false;

    public LorenzoIlMagnifico getLori() {
        return lori;
    }

    public boolean isFirstvatican() {
        return firstvatican;
    }

    public void setFirstvatican(boolean firstvatican) {
        this.firstvatican = firstvatican;
    }

    public boolean isSecondvatican() {
        return secondvatican;
    }

    public void setSecondvatican(boolean secondvatican) {
        this.secondvatican = secondvatican;
    }

    public boolean isThirdvatican() {
        return thirdvatican;
    }

    public void setThirdvatican(boolean thirdvatican) {
        this.thirdvatican = thirdvatican;
    }

    public Game() {
        players = new ArrayList<>();
        board = new Board();
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

    public void reportError(Nickname message){
        notify(message);
    }

    public void sendObject(ObjectMessage message) { notify(message);
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

    public void setInkwell(){
        Collections.shuffle(this.getPlayers());
    }

    public void checkVatican()  {
        OptionalInt maxPos = this.players.stream().mapToInt(x -> x.getFaithMarkerPos()).max();
        if (maxPos.isPresent())  {
            int maxP = maxPos.getAsInt();
            if (maxP >= 8 && !firstvatican)  {
                checkEveryPlayerPos(8, 0);
                setFirstvatican(true);

            }
            else if (maxP >= 16 && !secondvatican)  {
                checkEveryPlayerPos(16, 1);
                setSecondvatican(true);
            }
            else if (maxP ==24 && !thirdvatican)  {
                checkEveryPlayerPos(24, 2);
                setThirdvatican(true);
                // END OF GAME(?)
            }
        }

    }

    public void checkEveryPlayerPos(int popeSpace, int vatican)  {
        for (Player p : this.players)  {
            if (p.getFaithMarkerPos() >= (popeSpace-3-vatican))  p.getPersonalBoard().setFavorTile(vatican);
        }
    }

}
