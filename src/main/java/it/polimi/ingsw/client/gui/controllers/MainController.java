package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.SocketServerConnection;
import it.polimi.ingsw.client.UI;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.SceneList;
import it.polimi.ingsw.message.ActionMessages.*;
import it.polimi.ingsw.message.CommonMessages.*;
import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.model.Communication;
import it.polimi.ingsw.model.CommunicationList;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

public class MainController extends Observable<Message> implements Observer<Message>, UI {

    private final GUI gui;
    private SocketServerConnection serverConnection;
    private Game game;


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

    public void handleObject(ObjectMessage message) {
        if(message.getObjectID() == -1){
            System.out.println("game ricevuto");
            this.game = (Game)message.getObject();
            gui.setupFirstDraw();
        }
        if (message.getObjectID() == 9) { //Communication
            handleCommunication((Communication) message.getObject());
        }
    }
    public void handleCommunication(Communication communication) {
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
    public void update(IdMessage message) {

    }

    @Override
    public void update(ErrorMessage message) {

    }

    @Override
    public void update(ChooseNumberOfPlayer message) {

    }

    @Override
    public void update(ObjectMessage message) {

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

