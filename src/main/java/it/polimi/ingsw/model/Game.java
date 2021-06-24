package it.polimi.ingsw.model;



import it.polimi.ingsw.message.ActionMessages.ObjectMessage;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.observer.Observable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.OptionalInt;

/**
 * Class Game contains all the information about the game. It saves the number of players, a reference to the ID of the current player, one to the ID of the next player
 * a list of sorted player. It also contains the main Board of the game and attribute that refers to Lorenzo Il Magnifico if the game is single player.
 *
 * @author Lea Zancani
 * @see Board
 * @see Player
 * @see Turn
 * @see Communication
 *
 */

public class Game extends Observable<Message> implements Serializable {

    private int gameID;
    private int numPlayer;
    private ArrayList<Player> players;
    private Board board;
    private boolean firstVatican = false;
    private boolean secondVatican = false;
    private boolean thirdVatican = false;
    private boolean gameOver;
    private Turn turn;
    private Communication communication;

    public Game() { }

    public Game(int gameID, int numPlayer, boolean firstVatican, boolean secondVatican, boolean thirdVatican, boolean gameOver) {
        this.gameID = gameID;
        this.numPlayer = numPlayer;
        this.firstVatican = firstVatican;
        this.secondVatican = secondVatican;
        this.thirdVatican = thirdVatican;
        this.gameOver = gameOver;
        this.board = new Board();
        this.turn = new Turn();
        this.players = new ArrayList<>();
        this.communication = new Communication();
    }

    public Game(boolean single, int ID) {
        gameID = ID;
        players = new ArrayList<>();
        board = new Board(single);
        turn = new Turn();
        communication = new Communication();
    }

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

    public boolean isFirstVatican() {
        return firstVatican;
    }

    public void setFirstVatican(boolean firstVatican) {
        this.firstVatican = firstVatican;
    }

    public boolean isSecondVatican() {
        return secondVatican;
    }

    public void setSecondVatican(boolean secondVatican) {
        this.secondVatican = secondVatican;
    }

    public boolean isThirdVatican() {
        return thirdVatican;
    }

    public void setThirdVatican(boolean thirdVatican) {
        this.thirdVatican = thirdVatican;
    }

    public Communication getCommunication() {
        return communication;
    }

    public void setCommunication(Communication communication) {
        this.communication = communication;
    }

    public void setNumPlayer(int numPlayer) {
        this.numPlayer = numPlayer;
    }

    public int getNumPlayer() {
        return numPlayer;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void sendGame(){
        notify(new ObjectMessage(this.clone(), -1, -1));
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

    /**
     * It notifies changes to the RemoteView (if MultiPlayer) or UI (if SinglePlayer) creating a deep copy.
     *
     */

    public void setMarketHand(ArrayList<Marble> resources){
        getBoard().getMarket().setHand(resources);
        notify(new ObjectMessage(getBoard().getMarket().clone(), 1, -1));
    }

    /**
     * Method removeFromMarketHand removes first Marble in Market Hand.
     * It notifies changes to the RemoteView (if MultiPlayer) or UI (if SinglePlayer) creating a deep copy.
     */

    public void removeFromMarketHand(){
        getBoard().getMarket().getHand().remove(0);
        notify(new ObjectMessage(getBoard().getMarket().clone(), 1, -1));
    }

    /**
     * It notifies changes to the RemoteView (if MultiPlayer) or UI (if SinglePlayer) creating a deep copy.
     */
    public void setTurn(int ID, ActionPhase phase) {
        getTurn().setPlayerPlayingID(ID);
        getTurn().setPhase(phase);
        notify(new ObjectMessage(getTurn().clone(), 10, -1));

    }

    /**
     * It notifies changes to the RemoteView (if MultiPlayer) or UI (if SinglePlayer) creating a deep copy.
     */

    public void setCommunication(int ID, CommunicationList cl)  {
        getCommunication().setAddresseeID(ID);
        getCommunication().setCommunication(cl);
        notify(new ObjectMessage(getCommunication().clone(), 9, -1));
    }

    /**
     * Method swapShelvesByID is called from Controller's method swapShelves. It checks if swap move is valid, calling ShelfWareHouse's swap.
     * It notifies changes to the RemoteView (if MultiPlayer) or UI (if SinglePlayer) creating a deep copy.
     * @param s1 is the index of the first shelf.
     * @param s2 is the index of the second shelf.
     * @param ID is the Player's ID.
     * @return true if move was valid.
     */



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
        notify(new ObjectMessage(getBoard().getMatrix().clone(), 2, -1));

    }

    public void setResToPay(ArrayList<ResourceType> r){
        getBoard().getMatrix().setResToPay(r);
        notify(new ObjectMessage(getBoard().getMatrix().clone(), 2, -1));
    }



    /**
     * Method endTurn is called whether a Player ends his turn. it checks if game is over. If not, it sets the turn with the next Player.
     * @param lastPlayerID is the Player that ends his turn nickname.
     */

    public void endTurn(int lastPlayerID) {
        if(players.size()==1) {
            if (gameOver) {
                setTurn(findSoloWinner(), ActionPhase.GAME_OVER);

            } else {
                handleSoloActionToken();
                if (gameOver) {
                    setTurn(findSoloWinner(), ActionPhase.GAME_OVER);



                } else setTurn(0, ActionPhase.WAITING_FOR_ACTION);
            }
        }
        else {
            int position = this.players.indexOf(getPlayerById(lastPlayerID));
            if (position == (this.players.size() - 1)) {
                if (!gameOver) {
                    position = -1;

                } else {
                    int winnerID = findWinner().getId();
                    setTurn(winnerID, ActionPhase.GAME_OVER);
                    return;
                }
            }
            setTurn(players.get(position + 1).getId(), ActionPhase.WAITING_FOR_ACTION);
        }
    }

    /**
     * Method handleSoloActionToken draws a SoloActionToken and executes its effect.
     * It notifies changes to the RemoteView (if MultiPlayer) or UI (if SinglePlayer) creating a deep copy.
     */
    public void handleSoloActionToken(){
        notify(new ObjectMessage(board.getTokenArray().get(0), 16, -1));
        if(board.getTokenArray().get(0).isMoveBlack2()) {
            setCommunication(0, CommunicationList.LORI_MOVE2);
            moveLoriPos(2);
            board.getTokenArray().remove(0);
        }
        else if(board.getTokenArray().get(0).isMoveBlackAndShuffle()) {
            setCommunication(0, CommunicationList.LORI_MOVE);
            moveLoriPos(1);
            board.setTokenArray(board.createTokensArray());
        }
        else {
            switch (board.getTokenArray().get(0).getDiscard2Card()) {
                case BLUE -> setCommunication(0, CommunicationList.LORI_BLUE);
                case GREEN -> setCommunication(0, CommunicationList.LORI_GREEN);
                case PURPLE -> setCommunication(0, CommunicationList.LORI_PURPLE);
                default -> setCommunication(0, CommunicationList.LORI_YELLOW);
            }
            discardTwoDevCards(board.getTokenArray().get(0).getDiscard2Card());
            discardTwoDevCards(board.getTokenArray().get(0).getDiscard2Card());
            board.getTokenArray().remove(0);
            if(checkColumnEmpty()){
                gameOver = true;
            }
        }

        notify(new ObjectMessage(board.getMatrix().clone(), 2, 0));
        notify(new ObjectMessage(board.getTokenArrayClone(), 14, -1));

    }

    /**
     * Method moveFatihPosByID moves by param faith positions of the Player whose ID is param ID.
     * Method checks whether Player reached the end of Faith Track and calls checkVatican method.
     * It notifies changes to the RemoteView (if MultiPlayer) or UI (if SinglePlayer) creating a deep copy.
     * @param ID Player's ID.
     * @param faith amount of slots Player can move.
     */

    public void moveFaithPosByID(int ID, int faith){
        getPlayerById(ID).getPersonalBoard().getFaithTrack().moveFaithMarkerPos(faith);
        setCommunication(ID, CommunicationList.FP);
        if (getPlayerById(ID).getPersonalBoard().getFaithTrack().getMarker()==24) {
            setCommunication(ID, CommunicationList.PLAYER_CAP);
        }
        notify(new ObjectMessage(getPlayerById(ID).getPersonalBoard().getFaithTrack().clone(), 12, ID));
        checkVatican();

    }

    /**
     * Method moveLoriPos moves Lorenzo il Magnifico position according to parameter value.
     * Method checks if Lorenzo il Magnifico arrives at the end of the FaithTrack and calls checkVatican method.
     *It notifies changes to the RemoteView (if MultiPlayer) or UI (if SinglePlayer) creating a deep copy.
     * @param number is the amount of steps on the FaithTrack Lorenzo il Magnifico can do.
     */

    public void moveLoriPos(int number){
        getPlayerById(0).getPersonalBoard().getFaithTrack().moveLoriPos(number);
        if (getPlayerById(0).getPersonalBoard().getFaithTrack().getLoriPos()==24) {
            setCommunication(0, CommunicationList.LORI_CAP );
        }

        notify(new ObjectMessage(getPlayerById(0).getPersonalBoard().getFaithTrack().clone(), 12, 0));
        checkVatican();

    }

    /**
     * Method checkVatican check if the Player nearest to the FaithTrack end is in a Pope Favour Space. If true, methods calls checkEveryPlayerPos
     * It notifies changes to the RemoteView (if MultiPlayer) or UI (if SinglePlayer) creating a deep copy.
     */
    public void checkVatican() {
        int maxP=0;
        if (players.size()==1) {

            int playerPos = getPlayerById(0).getPersonalBoard().getFaithTrack().getMarker();
            int loriPos = getPlayerById(0).getPersonalBoard().getFaithTrack().getLoriPos();
            maxP = Math.max(playerPos, loriPos);

        } else {
            OptionalInt maxPos = this.players.stream().mapToInt(x -> x.getPersonalBoard().getFaithTrack().getMarker()).max();
            if (maxPos.isPresent()) {
                maxP = maxPos.getAsInt();
            }
        }
        if (maxP >= 8 && !firstVatican) {
            checkEveryPlayerPos(8, 0);
            setFirstVatican(true);
            notify(new ObjectMessage(true, 11, 0));

        } else if (maxP >= 16 && !secondVatican) {
            checkEveryPlayerPos(16, 1);
            setSecondVatican(true);
            notify(new ObjectMessage(true, 11, 1));
        } else if (maxP == 24 && !thirdVatican) {
            checkEveryPlayerPos(24, 2);
            setThirdVatican(true);
            setGameOver(true);
            notify(new ObjectMessage(true, 11, 2));

        }
    }

    /**
     * Method checkEveryPlayerPos checks if everyPlayer is in a Vatican section. If true, it sets to 1 the related FavorTile. Else it sets to 0.
     * It notifies changes to the RemoteView (if MultiPlayer) or UI (if SinglePlayer) creating a deep copy.
     * @param popeSpace it the position of the selected Pope favour.
     * @param vatican represents the length difference between the selected Vatican section and the first one.
     */

    public void checkEveryPlayerPos(int popeSpace, int vatican)  {
        for (Player p : this.players)  {
            if (p.getPersonalBoard().getFaithTrack().getMarker() >= (popeSpace-3-vatican)){
                p.getPersonalBoard().getFaithTrack().setFavorTile(vatican, 1);
                setCommunication(p.getId(), CommunicationList.VATICAN_YES);
                notify(new ObjectMessage(p.getPersonalBoard().getFaithTrack().clone(), 12, p.getId()));
            }
            else {
                p.getPersonalBoard().getFaithTrack().setFavorTile(vatican, 0);
                setCommunication(p.getId(), CommunicationList.VATICAN_NOPE);
                notify(new ObjectMessage(p.getPersonalBoard().getFaithTrack().clone(), 12, p.getId()));

            }

        }
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

        if(col1 == 0 || col2 == 0 || col3 == 0 || col4 == 0) {
            setCommunication(0, CommunicationList.EMPTYCOLUMN);
            return true;
        }
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
            notify(new ObjectMessage(getPlayerById(ID).getPersonalBoard().getWarehouse().clone(), 0, ID));
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
        notify(new ObjectMessage(getPlayerById(ID).getPersonalBoard().getStrongbox().clone(), 3, ID));
    }


    public void payFromWarehouse(int q, ResourceType r, int ID){
        getPlayerById(ID).getPersonalBoard().getWarehouse().pay(q, r);
        notify(new ObjectMessage(getPlayerById(ID).getPersonalBoard().getWarehouse().clone(), 0, ID));
    }

    public void payFromStrongbox(int q, ResourceType r, int ID){
        getPlayerById(ID).getPersonalBoard().getStrongbox().pay(q, r);
        notify(new ObjectMessage(getPlayerById(ID).getPersonalBoard().getStrongbox().clone(), 3, ID));
    }

    public void addDevCardToPlayer(int ID, int pos ){
        getBoard().getMatrix().getDevDecks().get(getBoard().getMatrix().getChosenIndex()).removeCardFromCards();
        getPlayerById(ID).getPersonalBoard().addDevCard(getBoard().getMatrix().getChosenCard(), pos, ID);
        if(checkDevCardNumber(ID)){
            setCommunication(ID, CommunicationList.SEVENCARDS);
            gameOver = true;
        }
        notify(new ObjectMessage(getBoard().getMatrix().clone(), 2, -1));
        notify(new ObjectMessage(getPlayerById(ID).getPersonalBoard().getCardSlotClone() ,4, ID));
    }

    public boolean checkDevCardNumber(int ID){
        int counter = getPlayerById(ID).getPersonalBoard().getCardSlot().get(0).getCards().size() +
                getPlayerById(ID).getPersonalBoard().getCardSlot().get(1).getCards().size() +
                getPlayerById(ID).getPersonalBoard().getCardSlot().get(2).getCards().size();
        return counter >= 7;
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
        notify(new ObjectMessage(getBoard().getMatrix().clone(), 2, -1));
    }

    public void removeResToPay(){
        getBoard().getMatrix().getResToPay().remove(0);
        notify(new ObjectMessage(getBoard().getMatrix().clone(), 2, -1));
    }

    public void payFromFirstExtraShelf(int ID, int q){
        getPlayerById(ID).getPersonalBoard().getWarehouse().payFromFirstExtraShelf();
        notify(new ObjectMessage(getPlayerById(ID).getPersonalBoard().getWarehouse().clone(), 0, ID));
    }

    public void payFromSecondExtraShelf(int ID, int q){
        getPlayerById(ID).getPersonalBoard().getWarehouse().payFromSecondExtraShelf();
        notify(new ObjectMessage(getPlayerById(ID).getPersonalBoard().getWarehouse().clone(), 0, ID));
    }

    public void addEarnedResourcesByID(int ID, int numCoin, int numStone, int numServant, int numShield){
        getPlayerById(ID).getPersonalBoard().getStrongbox().setEarnedCoin(getPlayerById(ID).getPersonalBoard().getStrongbox().getEarnedCoin() + numCoin);
        getPlayerById(ID).getPersonalBoard().getStrongbox().setEarnedStone(getPlayerById(ID).getPersonalBoard().getStrongbox().getEarnedStone() + numStone);
        getPlayerById(ID).getPersonalBoard().getStrongbox().setEarnedServant(getPlayerById(ID).getPersonalBoard().getStrongbox().getEarnedServant() + numServant);
        getPlayerById(ID).getPersonalBoard().getStrongbox().setEarnedShield(getPlayerById(ID).getPersonalBoard().getStrongbox().getEarnedShield() + numShield);
        notify(new ObjectMessage(getPlayerById(ID).getPersonalBoard().getStrongbox().clone(), 3, ID));
    }

    /**
     * Method setStartResCountByID checks if Player has to place other initial resources.
     * It notifies changes to the RemoteView (if MultiPlayer) or UI (if SinglePlayer) creating a deep copy.
     *
     * @param id is Player's ID
     * @param i is the quantity of initial resources yet to place.
     */

    public void setStartResCountByID(int id, int i) {
        getPlayerById(id).setStartResCount(i);
        notify(new ObjectMessage(i, 5, id));
        checkReadyPlayer(id);
    }

    /**
     * Method setLeaderCardsToDiscard  checks if Player has to discard other LeaderCards during initial phase.
     * It notifies changes to the RemoteView (if MultiPlayer) or UI (if SinglePlayer) creating a deep copy.
     * @param id is Player's ID
     * @param i is the quantity of LeaderCards yet to discard.
     */
    public void setLeaderCardsToDiscard(int id, int i) {
        getPlayerById(id).setLeaderCardsToDiscard(i);
        notify(new ObjectMessage(i, 13, id));
        if (getPlayerById(id).getLeaderCardsToDiscard()==0) notify(new ObjectMessage(getPlayerById(id).getStartResCount(), 5 , id));
        checkReadyPlayer(id);
    }

    /**
     * Method checkReadyPlayer checks if a player is ready. if true and all others player are ready, method set the first Turn.
     * @param id is the Player's ID
     */

    private synchronized void checkReadyPlayer(int id) {
        if (getPlayerById(id).getStartResCount() ==0 && getPlayerById(id).getLeaderCardsToDiscard() == 0) {
            getPlayerById(id).setReady(true);
            if (checkReadyPlayers()) {
                setTurn(getPlayers().get(0).getId(), ActionPhase.FIRST_ROUND);
                setTurn(getPlayers().get(0).getId(), ActionPhase.WAITING_FOR_ACTION);
            }
        }
    }

    /**
     * Method checkReadyPlayers checks if all players are ready.
     * @return true if all players are ready.
     */
    public synchronized boolean checkReadyPlayers(){
        return getPlayers().stream().filter(Player::isReady).count() == getNumPlayer();
    }

    public void setChosenLeader(LeaderCard leader, int ID){
        getPlayerById(ID).getPersonalBoard().setLeaderChosen((ExtraProdLCard) leader);
        notify(new ObjectMessage(getPlayerById(ID).getPersonalBoard().getLeaderChosen(), 7, ID));
    }

    /**
     * Method setNewPlayerCards removes the leader card from the hand of the player and adds it to his active leaders.
     * It then notifies all the changes and sets the turn phase to go back to the action menu.
     */
    public  synchronized void setNewPlayerCards (int ID, LeaderCard lc) {
        try {

            int i = 0;
            for (LeaderCard l : getPlayerById(ID).getLeaderDeck().getCards()) {
                if (l.equals(lc))
                    break;
                else
                    i++;
            }

            getPlayerById(ID).getLeaderDeck().getCards().remove(i);
            getPlayerById(ID).getLeaderDeck().print();
            getPlayerById(ID).getPersonalBoard().getActiveLeader().getCards().add(lc); //aggiunge ai leader attivi
            getPlayerById(ID).getPersonalBoard().getActiveLeader().print();

            notify(new ObjectMessage(getPlayerById(ID).getPersonalBoard().getWarehouse().clone(), 0, ID));
            notify((new ObjectMessage(getPlayerById(ID).getLeaderDeck().clone(), 8, ID)));
            notify((new ObjectMessage(getPlayerById(ID).getPersonalBoard().getActiveLeader().clone(), 6, ID)));
            notify(new ObjectMessage(this.getPlayerById(ID).clone(), 15, ID));

            setTurn(getTurn().getPlayerPlayingID(), ActionPhase.WAITING_FOR_ACTION);
        }
        catch (IndexOutOfBoundsException e) {
            setCommunication(ID, CommunicationList.INVALID_MOVE);
            setTurn(getTurn().getPlayerPlayingID(), ActionPhase.WAITING_FOR_ACTION);
        }
    }


    /**
     * sets the new leader deck after discarding a leader card and notifies it.
     */
    public void discard(int ID,LeaderDeck newLd){
        getPlayerById(ID).setLeaderDeck(newLd);
        notify((new ObjectMessage(getPlayerById(ID).getLeaderDeck().clone(), 8, ID)));
    }

    /**
     * Method giveLeaderCards gives 4 card to each playing Player.
     */
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

    /**
     * Method clone creates a deep copy of a Game instance.
     * @return a deep copy of a Game instance.
     */

    public Game clone(){
        Game clone = new Game();
        clone.gameID = this.gameID;
        clone.numPlayer = this.numPlayer;
        clone.firstVatican = this.firstVatican;
        clone.secondVatican = this.secondVatican;
        clone.thirdVatican = this.thirdVatican;
        clone.gameOver = this.gameOver;
        clone.board = board.clone();
        clone.turn  = turn.clone();
        clone.communication = communication.clone();
        clone.players = new ArrayList<>();

        for (Player p: this.players){
            clone.players.add(p.clone());
        }

        return clone;
    }

    /**
     * Method findWinner checks who wins in a MultiPlayer mode Game, according to Game rules.
     * @return winner's ID
     */

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

    /**
     * Method findSoloWinner check who wins between the player and Lorenzo Il Magnifico.
     * @return 0 if the Player won. Return -1 if not.
     */
    public int findSoloWinner() {
        if (getPlayerById(0).getPersonalBoard().getFaithTrack().getMarker() == 24 || checkDevCardNumber(0))
            return 0;
        else return -1;
    }

}
