package it.polimi.ingsw.server;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.message.ActionMessages.*;
import it.polimi.ingsw.message.CommonMessages.*;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.SocketClientConnection;

/**
 * RemoteView Class is used to complete MVC pattern Server side. it notifies to the Controller users Message and it observes Game changes.
 */

public class RemoteView extends Observable<Message> implements Observer<Message>, Runnable {

    private final SocketClientConnection clientConnection;
    private int ID;
    private int gameID;

    public RemoteView(SocketClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

    public SocketClientConnection getClientConnection() {
        return clientConnection;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void run(){
        this.clientConnection.setRemoteView(this);
        this.clientConnection.run();
        clientConnection.getServer().waitingRoom(this); //FASE 1
    }



    @Override
    public void update(Message message) {
    }

    @Override
    public void update(Nickname message) {

    }

    @Override
    public void update(ObjectMessage message) {
        clientConnection.send(message);
    }

    @Override
    public void update(ManageResourceMessage message) {

    }

    @Override
    public void update(MarketMessage message) {

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
    public void update(PlaceResourceMessage message) {


    }

    @Override
    public void update(BuyDevCardMessage message) {

    }

    public void handleAction(Object o) {

        if (o instanceof PlaceResourceMessage) {
            notify((PlaceResourceMessage) o);
        }
        else if (o instanceof Nickname) {
            notify((Nickname) o);
        }
        else if (o instanceof MarketMessage) {
            notify((MarketMessage) o);
        }
        else if (o instanceof BuyDevCardMessage) {
            notify((BuyDevCardMessage) o);
        }
        else if (o instanceof ProductionMessage) {
            notify((ProductionMessage) o);
        }
        else if (o instanceof PlayLeaderMessage) {
            notify((PlayLeaderMessage) o);
        }
        else if (o instanceof ManageResourceMessage) {
            notify((ManageResourceMessage) o);
        }
        else if (o instanceof EndTurnMessage) {
            notify((EndTurnMessage) o);
        }
        else if (o instanceof BasicProductionMessage){
            notify((BasicProductionMessage) o);
        }

        else if(o instanceof LeaderProductionMessage){
            notify((LeaderProductionMessage) o);
        }
    }
}
