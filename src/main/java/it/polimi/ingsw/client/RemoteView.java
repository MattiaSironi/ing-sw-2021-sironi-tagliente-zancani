package it.polimi.ingsw.client;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.message.ActionMessages.*;
import it.polimi.ingsw.message.CommonMessages.*;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.SocketClientConnection;

public class RemoteView extends Observable<Message> implements Observer<Message>, Runnable {

    private SocketClientConnection clientConnection;
    private int ID;
    private static int size;
    private boolean active = true;
    private int gameID;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public static void setSize(int size) {
        RemoteView.size = size;
    }

    public RemoteView(SocketClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

    public SocketClientConnection getClientConnection() {
        return clientConnection;
    }

    public void setClientConnection(SocketClientConnection clientConnection) {
        this.clientConnection = clientConnection;
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
        if(message.getID() == this.ID) {
            clientConnection.send(message);
        }
        else if(message.getValid())
            clientConnection.send(new OutputMessage("One of your opponents is " + message.getString()));
    }



    @Override
    public void update(IdMessage message) {
    }

    @Override
    public void update(ErrorMessage message) {
        if (message.getID()==ID) clientConnection.send(message);

    }



    @Override
    public void update(ChooseNumberOfPlayer message) {

    }

    @Override
    public void update(PrintableMessage message) {

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
        clientConnection.send(message);
    }

    @Override
    public void update(BasicProductionMessage message) {

    }

    @Override
    public void update(LeaderProductionMessage message) {

    }

    @Override
    public void update(GameOverMessage message) {
        if (message.getWinnerID() == ID) getClientConnection().close();
        }


    @Override
    public void update(PlaceResourceMessage message) {
        if (message.getID()==ID)  {
            clientConnection.send(message);
        }

    }

    @Override
    public void update(BuyDevCardMessage message) {

    }

    public RemoteView(SocketClientConnection c, int ID) {
        this.clientConnection = c;
        this.clientConnection.setID(ID);
        this.ID = ID;

    }

    public void handleAction(Object o) {
//        if (o instanceof EndTurnMessage)  {
//            notify((EndTurnMessage)o);
//        }
//        else if (o instanceof MarketMessage)  {
//            notify((MarketMessage) o);
//        }
//        else if (o instanceof PlaceResourceMessage)  {
//            notify((PlaceResourceMessage )o);
//        }
//        else if (o instanceof ManageResourceMessage)  {
//            notify((ManageResourceMessage) o);
//        }
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
