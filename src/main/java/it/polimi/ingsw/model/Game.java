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


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.OptionalInt;

public class Game extends Observable<Message> implements Cloneable , Serializable {
    private int numPlayer;
    private int currPlayer;
    private int nextPlayer;
    private ArrayList<Player> players;
    private LorenzoIlMagnifico lori = null;
    private Board board;
    private boolean firstvatican = false;
    private boolean secondvatican = false;
    private boolean thirdvatican = false;
    private boolean gameOver;
    private Turn turn;

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Turn getTurn() {
        return turn;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

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
        turn = new Turn();
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

    public void sendGame(){
        notify(new ObjectMessage(this, -1, -1));
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

    public void setMarketHand(ArrayList<Marble> resources){
        getBoard().getMarket().setHand(resources);
        notify(new ObjectMessage(getBoard().getMarket(), 1, -1));
    }

    public void removeFromMarketHand(){
        getBoard().getMarket().getHand().remove(0);
        notify(new ObjectMessage(getBoard().getMarket(), 1, -1));
    }

    public void setTurn(int ID, ActionPhase phase, boolean error, ErrorList errorType){
        getTurn().setPlayerPlayingID(ID);
        getTurn().setPhase(phase);
        getTurn().setError(error);
        getTurn().setErrorType(errorType);
        System.out.println("is playing " + turn.getPlayerPlayingID() + " phase: " + turn.getPhase());
        notify(new ObjectMessage(getTurn(), 10, -1));

    }

    public void moveFaithPosByID(int ID, int faith){
        getPlayerById(ID).moveFaithMarkerPos(faith);
        notify(new ObjectMessage(getPlayerById(ID).getFaithMarkerPos(), 13, ID));
        checkVatican();

    }

    public boolean swapShelvesByID(int s1, int s2, int ID){
        ShelfWarehouse temp = getPlayerById(ID).getPersonalBoard().getWarehouse().clone();
        if(temp.swap(s1, s2)) {
            getPlayerById(ID).getPersonalBoard().setWarehouse(temp);
            notify(new ObjectMessage(getPlayerById(ID).getPersonalBoard().getWarehouse(), 0, ID));
            return true;
        }
        else
            return false;
    }

    public void setChosenDevCard(DevCard d, int chosenIndex){
        getBoard().getMatrix().setChosenIndex(chosenIndex);
        getBoard().getMatrix().setChosenCard(d);
        notify(new ObjectMessage(getBoard().getMatrix(), 2, -1));

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
                notify(new ObjectMessage(true, 11, 0));

            }
            else if (maxP >= 16 && !secondvatican)  {
                checkEveryPlayerPos(16, 1);
                setSecondvatican(true);
                notify(new ObjectMessage(true, 11, 1));
            }
            else if (maxP ==24 && !thirdvatican)  {
                checkEveryPlayerPos(24, 2);
                setThirdvatican(true);
                notify(new ObjectMessage(true, 11, 2));
                // END OF GAME(?)
            }
        }

    }

    public void checkEveryPlayerPos(int popeSpace, int vatican)  {
        for (Player p : this.players)  {
            if (p.getFaithMarkerPos() >= (popeSpace-3-vatican)){
                p.getPersonalBoard().setFavorTile(vatican);
                notify(new ObjectMessage(vatican, 12, p.getId()));
            }

        }
    }

    public Player findWinner() {
        Player winner = null;
        int maxVictoryPoints = 0;
        int winnerResources = 0;

        for (Player p : this.players) {
            int resources = p.getValueResources();
            int victoryPoints = p.sumDevs() + p.sumLeads() + p.sumPope() + p.getValuePos() + resources/5;

            if (victoryPoints > maxVictoryPoints) {
                winner = p;
                maxVictoryPoints = victoryPoints;
                winnerResources = resources;
            } else if (victoryPoints == maxVictoryPoints) {
                if (resources >= winnerResources) {
                    winner = p;
                    winnerResources = resources;
                }
            }
        }
        return winner;

    }

    public void endTurn(int lastPlayerID) {

        int position = this.players.indexOf(getPlayerById(lastPlayerID));
        if (position == (this.players.size()-1))  {
            if (!gameOver) {
                position=-1;

            }
            // else GAMEOVER
        }
        setTurn(players.get(position+1).getId(), ActionPhase.WAITING_FOR_ACTION, false, null);
    }

    public boolean addResourceToWarehouse(int ID, int shelfIndex, ResourceType r){
        if(getPlayerById(ID).getPersonalBoard().getWarehouse().addResource(r, shelfIndex)) {
            notify(new ObjectMessage(getPlayerById(ID).getPersonalBoard().getWarehouse(), 0, ID));
            return true;
        }
        else
            return false;
    }

    public void addResourceToStrongbox(int ID){
        getPlayerById(ID).getPersonalBoard().getStrongbox().addResource(ResourceType.COIN, getPlayerById(ID).getPersonalBoard().getStrongbox().getEarnedCoin());
        getPlayerById(ID).getPersonalBoard().getStrongbox().addResource(ResourceType.STONE, getPlayerById(ID).getPersonalBoard().getStrongbox().getEarnedStone());
        getPlayerById(ID).getPersonalBoard().getStrongbox().addResource(ResourceType.SERVANT, getPlayerById(ID).getPersonalBoard().getStrongbox().getEarnedServant());
        getPlayerById(ID).getPersonalBoard().getStrongbox().addResource(ResourceType.SHIELD, getPlayerById(ID).getPersonalBoard().getStrongbox().getEarnedShield());
        getPlayerById(ID).getPersonalBoard().getStrongbox().setEarnedCoin(0);
        getPlayerById(ID).getPersonalBoard().getStrongbox().setEarnedStone(0);
        getPlayerById(ID).getPersonalBoard().getStrongbox().setEarnedServant(0);
        getPlayerById(ID).getPersonalBoard().getStrongbox().setEarnedShield(0);
        notify(new ObjectMessage(getPlayerById(ID).getPersonalBoard().getStrongbox(), 3, ID));
    }

    public void payFromWarehouse(int q, ResourceType r, int ID){
        getPlayerById(ID).getPersonalBoard().getWarehouse().pay(q, r);
        notify(new ObjectMessage(getPlayerById(ID).getPersonalBoard().getWarehouse(), 0, ID));
    }

    public void payFromStrongbox(int q, ResourceType r, int ID){
        getPlayerById(ID).getPersonalBoard().getStrongbox().pay(q, r);
        notify(new ObjectMessage(getPlayerById(ID).getPersonalBoard().getStrongbox(), 3, ID));
    }

    public void addDevCardToPlayer(int ID, int pos ){
        getBoard().getMatrix().getDevDecks().get(getBoard().getMatrix().getChosenIndex()).removeCardFromCards();
        getPlayerById(ID).getPersonalBoard().addDevCard(getBoard().getMatrix().getChosenCard(), pos - 1, ID);
        notify(new ObjectMessage(getBoard().getMatrix(), 2, -1));
        notify(new ObjectMessage(getPlayerById(ID).getPersonalBoard().getCardSlot(), 4, ID));
    }

    public void addEarnedResourcesByID(int ID, int numCoin, int numStone, int numServant, int numShield){
        getPlayerById(ID).getPersonalBoard().getStrongbox().setEarnedCoin(numCoin);
        getPlayerById(ID).getPersonalBoard().getStrongbox().setEarnedStone(numStone);
        getPlayerById(ID).getPersonalBoard().getStrongbox().setEarnedServant(numServant);
        getPlayerById(ID).getPersonalBoard().getStrongbox().setEarnedShield(numShield);
        notify(new ObjectMessage(getPlayerById(ID).getPersonalBoard().getStrongbox(), 3, ID));
    }

    public void sendActionOver(EndActionMessage message) {
        notify(message);
    }

    public void setStartResCountByID(int id, int i) {
        getPlayerById(id).setStartResCount(i);
        notify(new ObjectMessage(i, 5, id));
    }
}
