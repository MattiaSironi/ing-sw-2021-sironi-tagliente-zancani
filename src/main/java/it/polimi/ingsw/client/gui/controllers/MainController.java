package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.SocketServerConnection;
import it.polimi.ingsw.client.UI;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.SceneList;
import it.polimi.ingsw.message.ActionMessages.*;
import it.polimi.ingsw.message.CommonMessages.*;
import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainController extends Observable<Message> implements Observer<Message>, UI {

    private final GUI gui;
    private SocketServerConnection serverConnection;
    private Game game;
    private boolean marketValid;
    private boolean wareValid;
    boolean firstAction;

    public boolean isFirstAction() {
        return firstAction;
    }

    public void setFirstAction(boolean firstAction) {
        this.firstAction = firstAction;
    }

    public boolean isMarketValid() {
        return marketValid;
    }

    public boolean isWareValid() {
        return wareValid;
    }

    public void setWareValid(boolean wareValid) {
        this.wareValid = wareValid;
    }

    public void setMarketValid(boolean marketValid) {
        this.marketValid = marketValid;
    }

    public MainController(GUI gui) {
        this.gui = gui;
    }

    public void setServerConnection(SocketServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public SocketServerConnection getServerConnection() {
        return serverConnection;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void send(Message o){
        if(gui.isLocal()) {
            if (o instanceof PlaceResourceMessage) {
                notify((PlaceResourceMessage) o);
            } else if (o instanceof Nickname) {
                notify((Nickname) o);
            } else if (o instanceof MarketMessage) {
                notify((MarketMessage) o);
            } else if (o instanceof BuyDevCardMessage) {
                notify((BuyDevCardMessage) o);
            } else if (o instanceof ProductionMessage) {
                notify((ProductionMessage) o);
            } else if (o instanceof PlayLeaderMessage) {
                notify((PlayLeaderMessage) o);
            } else if (o instanceof ManageResourceMessage) {
                notify((ManageResourceMessage) o);
            } else if (o instanceof EndTurnMessage) {
                notify((EndTurnMessage) o);
            } else if (o instanceof BasicProductionMessage) {
                notify((BasicProductionMessage) o);
            } else if (o instanceof LeaderProductionMessage) {
                notify((LeaderProductionMessage) o);
            }
        }
        else
            serverConnection.send(o);
    }

    public void handleAction(Object message) {
        if(message instanceof IdMessage) {
            setID(((IdMessage) message).getID());
        }
        else if (message instanceof ChooseNumberOfPlayer){
            if(((ChooseNumberOfPlayer) message).getNumberOfPlayers() == -1) {
                gui.updateHostScene();
            }
            else{
                gui.askForNickname();
            }
        }
        else if (message instanceof ObjectMessage) {
            handleObject((ObjectMessage) message);
        }
    }

    public GUI getGui() {
        return gui;
    }

    public void handleObject(ObjectMessage message) {
        if (message.getObjectID() == -1) {
            this.game = (Game) message.getObject();
            gui.changeScene(SceneList.FIRSTDRAW);
        } else if (message.getObjectID() == 0) { //SHELF WAREHOUSE
            game.getPlayerById(message.getID()).getPersonalBoard().setWarehouse((ShelfWarehouse) message.getObject());

            if (gui.getID() == message.getID()) {
                gui.showShelves();
                wareValid= false;
            }
        } else if (message.getObjectID() == 1) { //MARKET
            game.getBoard().setMarket((Market) message.getObject());
            marketValid = false;
            gui.showMarket();
            marketValid = true;
        } else if (message.getObjectID() == 2) { //MATRIX OF DEV CARD
            this.game.getBoard().setMatrix((DevelopmentCardMatrix) message.getObject());
        }
        else if (message.getObjectID() == 3) {//STRONGBOX
            game.getPlayerById(message.getID()).getPersonalBoard().setStrongbox((Strongbox) message.getObject());
            if (gui.getID() == message.getID()) {
                gui.showStrongbox();

            }
        }
        else if (message.getObjectID() == 4) {//CARDSLOT
            game.getPlayerById(message.getID()).getPersonalBoard().setCardSlot((ArrayList<DevDeck>) message.getObject());
            if(gui.getID() == message.getID()) {
                gui.updateDevSlot(SceneList.PLACEDEVCARDSCENE);
            }
        }

        else if (message.getObjectID() == 9) { //Communication
            handleCommunication((Communication) message.getObject());
        } else if (message.getObjectID() == 5) //initialResource
        {
            this.game.getPlayerById(message.getID()).setStartResCount((int) message.getObject());
            if (message.getID() == gui.getID()) {
                System.out.println("gigi " + game.getPlayerById(gui.getID()).getStartResCount());
                gui.setResCounterLabel(game.getPlayerById(gui.getID()).getStartResCount());
            }
        }
        else if (message.getObjectID() == 8) { //leaderDeck
                game.getPlayerById(message.getID()).setLeaderDeck((LeaderDeck) message.getObject());
                gui.showLeaders();
        }
        else if (message.getObjectID() == 13) {
            this.game.getPlayerById(message.getID()).setLeaderCardsToDiscard((int) message.getObject());
            if (message.getID() == gui.getID()) ;

        } else if (message.getObjectID() == 10) {
            this.game.setTurn((Turn) message.getObject());
            handleTurn((this.game.getTurn()));
        }else if (message.getObjectID() == 12){
            this.game.getPlayerById(message.getID()).getPersonalBoard().setFaithTrack((FaithTrack) message.getObject());
            gui.showFaithTrack();
        }

        else if (message.getObjectID() == 6) { //LEADER
            this.game.getPlayerById(message.getID()).getPersonalBoard().setActiveLeader((LeaderDeck) message.getObject());
        }
        else if (message.getObjectID()==14) { //SoloActionTokens
            this.game.getBoard().setTokenArray((ArrayList<SoloActionToken>) message.getObject());
            gui.showTokens();
        }
        else if(message.getObjectID()==15){ //PLAYER
            this.game.setPlayerByID(message.getID(),(Player)message.getObject());
        }
        else if (message.getObjectID()==16) { //token played
            gui.showTokenPlayed(((SoloActionToken) message.getObject()).toString());
        }
    }

    public void handleTurn (Turn turn) {
        if(turn.getPhase() == ActionPhase.FIRST_ROUND){
            gui.controllerSetup();
            gui.changeScene(SceneList.MAINSCENE);
        }
        else if (turn.getPhase() == ActionPhase.WAITING_FOR_ACTION) {
            if(turn.getPlayerPlayingID() == gui.getID()){
                if(!firstAction) {
                    gui.enable();
                    firstAction = true;
                    gui.changeScene(SceneList.MAINSCENE);
                }
            }
            else{
                gui.disable();
            }
        }
        else if(turn.getPhase() == ActionPhase.B_PAYMENT && turn.getPlayerPlayingID() == gui.getID()){
            gui.changeScene(SceneList.PAYDEVCARDSCENE);
        }
        else if(turn.getPhase() == ActionPhase.CHOOSE_SLOT && turn.getPlayerPlayingID() == gui.getID()){
            gui.changeScene(SceneList.PLACEDEVCARDSCENE);
        }
        else if(turn.getPhase()==ActionPhase.GAME_OVER){
            if (turn.getPlayerPlayingID() == gui.getID()) {
                gui.gameOver(true);
            }
            else {
                gui.gameOver(false);
            }
            gui.changeScene(SceneList.GAMEOVERSCENE);
        }
            gui.printMessage(turn);
    }



    public void handleCommunication(Communication communication) {
        if (communication.getAddresseeID() == gui.getID() || communication.getAddresseeID() == -1) {
            gui.printMessage(communication);

            }
            if (communication.getCommunication() == CommunicationList.NICK_NOT_VALID && communication.getAddresseeID() == gui.getID()) {
                gui.setDuplicatedNickname();
            }
    }

    @Override
    public void setID(int ID) {
        gui.setID(ID);
    }

    @Override
    public void update(Message message) {

    }

    @Override
    public void update(Nickname message) {

    }

    @Override
    public void update(ObjectMessage message) {
        handleObject(message);

    }

    @Override
    public void update(ManageResourceMessage message) {

    }

    @Override
    public void update(MarketMessage message) {

    }

    @Override
    public void update(PlaceResourceMessage message) {

    }

    @Override
    public void update(BuyDevCardMessage message) {

    }

    @Override
    public void update(PlayLeaderMessage message) {

    }

    @Override
    public void update(ProductionMessage message) {

    }

    @Override
    public void update(EndTurnMessage message) {

    }

    @Override
    public void update(BasicProductionMessage message) {

    }

    @Override
    public void update(LeaderProductionMessage message) {

    }

    @Override
    public void update(GameOverMessage message) {

    }
}


