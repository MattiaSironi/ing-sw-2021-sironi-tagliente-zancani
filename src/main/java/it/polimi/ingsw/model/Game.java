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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.OptionalInt;

public class Game extends Observable<Message> implements Serializable {

    private int gameID;
    private int numPlayer;
    private int currPlayer;
    private int nextPlayer;
    private ArrayList<Player> players;
    private Board board;
    private boolean firstvatican = false;
    private boolean secondvatican = false;
    private boolean thirdvatican = false;
    private boolean gameOver;
    private Turn turn;
    private Communication communication;

    public boolean isGameOver() {
        return gameOver;
    }

    public int getGameID() {
        return gameID;
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

    public Communication getCommunication() {
        return communication;
    }

    public void setCommunication(Communication communication) {
        this.communication = communication;
    }

    public Game(boolean single, int ID) {
        gameID = ID;
        players = new ArrayList<>();
        board = new Board(single);
        turn = new Turn();
        communication = new Communication();
    }


    public Game(int numPlayer, int currPlayer, int nextPlayer, ArrayList<Player> players,  Board board) {
        this.numPlayer = numPlayer;
        this.currPlayer = currPlayer;
        this.nextPlayer = nextPlayer;
        this.players = players;

        this.board = board;
    }

    public void setCurrPlayer(int currPlayer) {
        this.currPlayer = currPlayer;
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



    public Board getBoard() {
        return board;
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

    public void setPlayerByID(int ID, Player pl){
        int i=0;
        for (Player p : this.players) {
            if (p.getId() == ID) {
                this.getPlayers().set(i,pl);
            }
            else
                i++;
        }
    }

    public Game clone() throws CloneNotSupportedException {
        Game clone = new Game (this.numPlayer, this.currPlayer, this.nextPlayer, this.players, this.board);
        return clone;
    }

    public void setMarketHand(ArrayList<Marble> resources){
        getBoard().getMarket().setHand(resources);
        notify(new ObjectMessage(getBoard().getMarket().clone(), 1, -1));
    }

    public void removeFromMarketHand(){
        getBoard().getMarket().getHand().remove(0);
        notify(new ObjectMessage(getBoard().getMarket().clone(), 1, -1));
    }



    public void setTurn(int ID, ActionPhase phase, boolean error, ErrorList errorType){
        getTurn().setPlayerPlayingID(ID);
        getTurn().setPhase(phase);
        getTurn().setError(error);
        getTurn().setErrorType(errorType);
        System.out.println("is playing " + turn.getPlayerPlayingID() + " phase: " + turn.getPhase());
        notify(new ObjectMessage(getTurn().clone(), 10, -1));

    }

    public void setCommunication(int ID, CommunicationList cl)  {
        getCommunication().setAddresseeID(ID);
        getCommunication().setCommunication(cl);
        notify(new ObjectMessage(getCommunication().clone(), 9, -1));
    }

    public void moveFaithPosByID(int ID, int faith){
        getPlayerById(ID).getPersonalBoard().getFaithTrack().moveFaithMarkerPos(faith);
        setCommunication(ID, CommunicationList.FP);
        notify(new ObjectMessage(getPlayerById(ID).getPersonalBoard().getFaithTrack().clone(), 12, ID));
        checkVatican();

    }

    public boolean swapShelvesByID(int s1, int s2, int ID){
        ShelfWarehouse temp = getPlayerById(ID).getPersonalBoard().getWarehouse().clone();
        if(temp.swap(s1, s2)) {
            getPlayerById(ID).getPersonalBoard().setWarehouse(temp);
            notify(new ObjectMessage(getPlayerById(ID).getPersonalBoard().getWarehouse().clone(), 0, ID));
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

    public void setBoughtResByBasic(ResourceType r, int ID){
        getPlayerById(ID).getPersonalBoard().getWarehouse().setBought(r);
        notify(new ObjectMessage(getPlayerById(ID).getPersonalBoard().getWarehouse().clone(), 0, ID));
    }

    public void setPaidResForBasic(ArrayList<ResourceType> r){
        getBoard().getMatrix().setResToPay(r);
        notify(new ObjectMessage(getBoard().getMatrix(), 2, -1));
    }

    public void checkVatican() {
        int maxP=0;
        if (numPlayer == 1) {

            int playerPos = getPlayerById(0).getPersonalBoard().getFaithTrack().getMarker();
            int loriPos = getPlayerById(0).getPersonalBoard().getFaithTrack().getLoriPos();
            maxP = Math.max(playerPos, loriPos);

        } else {
            OptionalInt maxPos = this.players.stream().mapToInt(x -> x.getPersonalBoard().getFaithTrack().getMarker()).max();
            if (maxPos.isPresent()) {
                maxP = maxPos.getAsInt();
            }
        }
                if (maxP >= 8 && !firstvatican) {
                    setCommunication(-1, CommunicationList.VATICAN);
                    checkEveryPlayerPos(8, 0);
                    setFirstvatican(true);
                    notify(new ObjectMessage(true, 11, 0));

                } else if (maxP >= 16 && !secondvatican) {
                    setCommunication(-1, CommunicationList.VATICAN);
                    checkEveryPlayerPos(16, 1);
                    setSecondvatican(true);
                    notify(new ObjectMessage(true, 11, 1));
                } else if (maxP == 24 && !thirdvatican) {
                    setCommunication(-1, CommunicationList.VATICAN);
                    checkEveryPlayerPos(24, 2);
                    setThirdvatican(true);
                    setGameOver(true);
                    notify(new ObjectMessage(true, 11, 2));

                }
            }




    public void checkEveryPlayerPos(int popeSpace, int vatican)  {
        for (Player p : this.players)  {
            if (p.getPersonalBoard().getFaithTrack().getMarker() >= (popeSpace-3-vatican)){
                p.getPersonalBoard().getFaithTrack().setFavorTile(vatican);
                setCommunication(p.getId(), CommunicationList.VATICAN_YES);
                notify(new ObjectMessage(p.getPersonalBoard().getFaithTrack().clone(), 12, p.getId()));
            }
            else setCommunication(p.getId(), CommunicationList.VATICAN_NOPE);

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
        if(numPlayer == -1){ //TODO
           handleSoloActionToken();
           if (gameOver) {}
        }
        else {
            int position = this.players.indexOf(getPlayerById(lastPlayerID));
            if (position == (this.players.size() - 1)) {
                if (!gameOver) {
                    position = -1;

                } else {
                    int winnerID = findWinner().getId();
                    setTurn(winnerID, ActionPhase.GAME_OVER, false, null);
                    notify(new GameOverMessage(winnerID));
                    return;
                }
            }
            setTurn(players.get(position + 1).getId(), ActionPhase.WAITING_FOR_ACTION, false, null);
        }
    }

    public void handleSoloActionToken(){
        if(board.getTokenArray().get(0).isMoveBlack2()) {
            moveLoriPos(2);
            board.getTokenArray().remove(0);
        }
        else if(board.getTokenArray().get(0).isMoveBlackAndShuffle()) {
            moveLoriPos(1);
            board.setTokenArray(board.createTokensArray());
        }
        else {
            discardTwoDevCards(board.getTokenArray().get(0).getDiscard2Card());
            discardTwoDevCards(board.getTokenArray().get(0).getDiscard2Card());
            board.getTokenArray().remove(0);
            if(checkColumnEmpty()){
                gameOver = true;
            }
        }
        notify(new ObjectMessage(board.getTokenArray(), 14, -1));
    }

    public void moveLoriPos(int number){
        getPlayerById(0).getPersonalBoard().getFaithTrack().moveLoriPos(number);

        notify(new ObjectMessage(getPlayerById(0).getPersonalBoard().getFaithTrack(), 12, 0));
        checkVatican();

    }

    public boolean checkColumnEmpty(){
        int col1, col2, col3, col4;
        col1 = getBoard().getMatrix().getDevDecks().get(0).getCards().size()
                + getBoard().getMatrix().getDevDecks().get(4).getCards().size()
                + getBoard().getMatrix().getDevDecks().get(8).getCards().size();
        col2 = getBoard().getMatrix().getDevDecks().get(1).getCards().size()
                + getBoard().getMatrix().getDevDecks().get(5).getCards().size()
                + getBoard().getMatrix().getDevDecks().get(9).getCards().size();
        col3 =  getBoard().getMatrix().getDevDecks().get(2).getCards().size()
                + getBoard().getMatrix().getDevDecks().get(6).getCards().size()
                + getBoard().getMatrix().getDevDecks().get(10).getCards().size();
        col4 =  getBoard().getMatrix().getDevDecks().get(3).getCards().size()
                + getBoard().getMatrix().getDevDecks().get(7).getCards().size()
                + getBoard().getMatrix().getDevDecks().get(11).getCards().size();

        if(col1 == 0 || col2 == 0 || col3 == 0 || col4 == 0)
            return true;
        else
            return false;
    }

    public void discardTwoDevCards(CardColor cardColor){
        if(cardColor == CardColor.GREEN){
            if(getBoard().getMatrix().getDevDecks().get(0).getCards().size() != 0){
                getBoard().getMatrix().getDevDecks().get(0).getCards().remove(0);
            }
            else if(getBoard().getMatrix().getDevDecks().get(4).getCards().size() != 0){
                getBoard().getMatrix().getDevDecks().get(4).getCards().remove(0);
            }
            else if(getBoard().getMatrix().getDevDecks().get(8).getCards().size() != 0){
                getBoard().getMatrix().getDevDecks().get(8).getCards().remove(0);
            }
        }
        if(cardColor == CardColor.BLUE){
            if(getBoard().getMatrix().getDevDecks().get(1).getCards().size() != 0){
                getBoard().getMatrix().getDevDecks().get(1).getCards().remove(0);
            }
            else if(getBoard().getMatrix().getDevDecks().get(5).getCards().size() != 0){
                getBoard().getMatrix().getDevDecks().get(5).getCards().remove(0);
            }
            else if(getBoard().getMatrix().getDevDecks().get(9).getCards().size() != 0){
                getBoard().getMatrix().getDevDecks().get(9).getCards().remove(0);
            }
        }
        if(cardColor == CardColor.YELLOW){
            if(getBoard().getMatrix().getDevDecks().get(2).getCards().size() != 0){
                getBoard().getMatrix().getDevDecks().get(2).getCards().remove(0);
            }
            else if(getBoard().getMatrix().getDevDecks().get(6).getCards().size() != 0){
                getBoard().getMatrix().getDevDecks().get(6).getCards().remove(0);
            }
            else if(getBoard().getMatrix().getDevDecks().get(10).getCards().size() != 0){
                getBoard().getMatrix().getDevDecks().get(10).getCards().remove(0);
            }
        }
        if(cardColor == CardColor.PURPLE){
            if(getBoard().getMatrix().getDevDecks().get(3).getCards().size() != 0){
                getBoard().getMatrix().getDevDecks().get(3).getCards().remove(0);
            }
            else if(getBoard().getMatrix().getDevDecks().get(7).getCards().size() != 0){
                getBoard().getMatrix().getDevDecks().get(7).getCards().remove(0);
            }
            else if(getBoard().getMatrix().getDevDecks().get(11).getCards().size() != 0){
                getBoard().getMatrix().getDevDecks().get(11).getCards().remove(0);
            }
        }
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
        getPlayerById(ID).getPersonalBoard().addDevCard(getBoard().getMatrix().getChosenCard(), pos, ID);

        notify(new ObjectMessage(getBoard().getMatrix(), 2, -1));
        notify(new ObjectMessage(getPlayerById(ID).getPersonalBoard().getCardSlot(), 4, ID));
    }

    public void setResToPay(DevCard d, int ID){
        ArrayList<ResourceType> resToPay = new ArrayList<>();
        int count;
        for (count = 0; count < d.getCostRes()[0]; count++){
            resToPay.add(ResourceType.COIN);
        }
        for (count = 0; count < d.getCostRes()[1]; count++){
            resToPay.add(ResourceType.STONE);
        }
        for (count = 0; count < d.getCostRes()[2]; count++){
            resToPay.add(ResourceType.SERVANT);
        }
        for (count = 0; count < d.getCostRes()[3]; count++){
            resToPay.add(ResourceType.SHIELD);
        }
        if(getPlayerById(ID).getResDiscount1() == ResourceType.COIN && d.getCostRes()[0] > 0) {
            resToPay.remove(ResourceType.COIN);
            setCommunication(ID, CommunicationList.COINDISCOUNT);
        }
        else if(getPlayerById(ID).getResDiscount1() == ResourceType.STONE && d.getCostRes()[1] > 0) {
            resToPay.remove(ResourceType.STONE);
            setCommunication(ID, CommunicationList.STONEDISCOUNT);
        }
        else if(getPlayerById(ID).getResDiscount1() == ResourceType.SERVANT && d.getCostRes()[2] > 0) {
            resToPay.remove(ResourceType.SERVANT);
            setCommunication(ID, CommunicationList.SERVANTDISCOUNT);
        }
        else if(getPlayerById(ID).getResDiscount1() == ResourceType.SHIELD && d.getCostRes()[3] > 0) {
            resToPay.remove(ResourceType.SHIELD);
            setCommunication(ID, CommunicationList.SHIELDDISCOUNT);
        }
        if(getPlayerById(ID).getResDiscount2() == ResourceType.COIN && d.getCostRes()[0] > 0) {
            resToPay.remove(ResourceType.COIN);
            setCommunication(ID, CommunicationList.COINDISCOUNT);
        }
        else if(getPlayerById(ID).getResDiscount2() == ResourceType.STONE && d.getCostRes()[1] > 0) {
            resToPay.remove(ResourceType.STONE);
            setCommunication(ID, CommunicationList.STONEDISCOUNT);
        }
        else if(getPlayerById(ID).getResDiscount2() == ResourceType.SERVANT && d.getCostRes()[2] > 0) {
            resToPay.remove(ResourceType.SERVANT);
            setCommunication(ID, CommunicationList.SERVANTDISCOUNT);
        }
        else if(getPlayerById(ID).getResDiscount2() == ResourceType.SHIELD && d.getCostRes()[3] > 0) {
            resToPay.remove(ResourceType.SHIELD);
            setCommunication(ID, CommunicationList.SHIELDDISCOUNT);
        }

        getBoard().getMatrix().setResToPay(resToPay);
        notify(new ObjectMessage(getBoard().getMatrix(), 2, -1));
    }

    public void removeResToPay(){
        getBoard().getMatrix().getResToPay().remove(0);
        notify(new ObjectMessage(getBoard().getMatrix(), 2, -1));
    }

    public void payFromFirstExtraShelf(int ID, int q){
        getPlayerById(ID).getPersonalBoard().getWarehouse().payFromFirstExtraShelf(q);
        notify(new ObjectMessage(getPlayerById(ID).getPersonalBoard().getWarehouse(), 0, ID));
    }

    public void payFromSecondExtraShelf(int ID, int q){
        getPlayerById(ID).getPersonalBoard().getWarehouse().payFromFirstExtraShelf(q);
        notify(new ObjectMessage(getPlayerById(ID).getPersonalBoard().getWarehouse(), 0, ID));
    }

    public void addEarnedResourcesByID(int ID, int numCoin, int numStone, int numServant, int numShield){
        getPlayerById(ID).getPersonalBoard().getStrongbox().setEarnedCoin(numCoin);
        getPlayerById(ID).getPersonalBoard().getStrongbox().setEarnedStone(numStone);
        getPlayerById(ID).getPersonalBoard().getStrongbox().setEarnedServant(numServant);
        getPlayerById(ID).getPersonalBoard().getStrongbox().setEarnedShield(numShield);
        notify(new ObjectMessage(getPlayerById(ID).getPersonalBoard().getStrongbox(), 3, ID));
    }



    public void setStartResCountByID(int id, int i) {
        getPlayerById(id).setStartResCount(i);
        notify(new ObjectMessage(i, 5, id));
    }
    public void setLeaderCardsToDiscard(int id, int i) {
        getPlayerById(id).setLeaderCardsToDiscard(i);
        notify(new ObjectMessage(i, 13, id));
        if (getPlayerById(id).getLeaderCardsToDiscard() == 0) {
            getPlayerById(id).setReady(true);
            if (checkReadyPlayers())  setTurn(getPlayers().get(0).getId(), ActionPhase.WAITING_FOR_ACTION, false, null);
        }
    }


    public synchronized boolean checkReadyPlayers(){
        return getPlayers().stream().filter(x -> x.isReady()).count() == getNumPlayer();
    }
    public void setChosenLeader(LeaderCard leader, int ID){
        getPlayerById(ID).getPersonalBoard().setLeaderChosen((ExtraProdLCard) leader);
        notify(new ObjectMessage(getPlayerById(ID).getPersonalBoard().getLeaderChosen(), 7, ID));
    }

    public void setNewPlayerCards (int ID, LeaderCard lc){

        int i=0;
        for(LeaderCard l : getPlayerById(ID).getLeaderDeck().getCards())
        {
            if(l.equals(lc))
                break;
            else
                i++;
        }

//        getPlayerById(ID).getLeaderDeck().getCards().remove(plc); //toglie dal leader deck
        getPlayerById(ID).getLeaderDeck().getCards().remove(i);
      //  getPlayerById(ID).getLeaderDeck().getCards().remove(getPlayerById(ID).getLeaderDeck().getCards().get(i));
        getPlayerById(ID).getPersonalBoard().getActiveLeader().getCards().add(lc); //aggiunge ai leader attivi

        notify(new ObjectMessage(getPlayerById(ID).getPersonalBoard().getWarehouse(), 0, ID));
        notify((new ObjectMessage(getPlayerById(ID).getLeaderDeck(), 8, ID)));
        notify((new ObjectMessage(getPlayerById(ID).getPersonalBoard().getActiveLeader(),6,ID)));
        notify(new ObjectMessage(this.getPlayerById(ID),15,ID));

        setTurn(getTurn().getPlayerPlayingID(), ActionPhase.WAITING_FOR_ACTION,false,null);
    }




    public void discard(int ID,LeaderDeck newLd){
        getPlayerById(ID).setLeaderDeck(newLd);
       // System.out.println("nuovo mazzo modificato");
       // this.getPlayerById(ID).getLeaderDeck().print();
        notify((new ObjectMessage(getPlayerById(ID).getLeaderDeck(), 8, ID)));
      //  moveFaithPosByID(ID,1);
    }

    public  synchronized void  giveLeaderCards() {
        for (Player p : players)  {
            p.getLeaderDeck().getCards().add(getBoard().getLeaderDeck().getCards().get(0));
            p.getLeaderDeck().getCards().add(getBoard().getLeaderDeck().getCards().get(1));
            p.getLeaderDeck().getCards().add(getBoard().getLeaderDeck().getCards().get(2));
            p.getLeaderDeck().getCards().add(getBoard().getLeaderDeck().getCards().get(3));
            getBoard().getLeaderDeck().getCards().remove(0);
            getBoard().getLeaderDeck().getCards().remove(0);
            getBoard().getLeaderDeck().getCards().remove(0);
            getBoard().getLeaderDeck().getCards().remove(0);
        }

    }
}
