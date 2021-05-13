package it.polimi.ingsw.view;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.message.ActionMessages.*;
import it.polimi.ingsw.message.CommonMessages.*;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.SocketClientConnection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class RemoteView extends Observable<Message> implements Observer<Message>, Runnable {

    private SocketClientConnection clientConnection;
    private int ID;
    private static int size;
    private int turnPhase = 0;
    private int gamePhase = 0;
    private boolean active = true;

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

        while(isActive()){

        }

    }

//    public int setNumPlayers() {
//        int numPlayers = 0;
//        try {
//            numPlayers = ((ChooseNumberOfPlayer)clientConnection.receive()).getNumberOfPlayers();
//            clientConnection.getServer().setReady(true);
//        } catch (/*IOException | */ NoSuchElementException e) {
//            System.err.println("Error!" + e.getMessage());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return numPlayers;
//    }

    private void goToMarket(MarketMessage message) throws IOException, ClassNotFoundException { //  TODO
        notify(message);
        boolean done = false;

        while (!done) {
            Object obj = clientConnection.receive();
            if (obj instanceof ErrorMessage) {
                if (((ErrorMessage) obj).getString().equals("discard")) {
                    notify((ErrorMessage) obj);
                } else if (((ErrorMessage) obj).getString().equals("resources finished")) done = true;
            } else if (obj instanceof PlaceResourceMessage) {
                notify((PlaceResourceMessage) obj);
            }

        }

    }

    @Override
    public void update(Message message) {
    }

    @Override
    public void update(Nickname message) {
        if(message.getID() == this.ID) {
            if(message.getValid()) gamePhase = 1;
            clientConnection.send(message);
        }
        else if(message.getValid())
            clientConnection.send(new OutputMessage("One of your opponents is " + message.getString()));
    }

    @Override
    public void update(InputMessage message) {

    }

    @Override
    public void update(IdMessage message) {




    }

    @Override
    public void update(ErrorMessage message) {
        if (message.getID()==ID) clientConnection.send(message);

    }

    @Override
    public void update(OutputMessage message) {

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
        turnPhase = 0;
        clientConnection.send(message);

    }

    @Override
    public void update(EndActionMessage message) {
        if (message.getID()==ID)   {
            turnPhase = 0;
            clientConnection.send(message);
        }
    }

    @Override
    public void update(ResourceListMessage message) {
        if (message.getID() == ID)
        clientConnection.send(message);


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

    public void handleAction(Object o){
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
        if (o instanceof PlaceResourceMessage)  {
            notify((PlaceResourceMessage)o);

        }
        if(gamePhase == 0){
            if(o instanceof Nickname){
                notify((Nickname)o);
            }
        }
         else if(gamePhase == 1){
            if(turnPhase == 0) {
                turnPhase = 1;
                if (o instanceof MarketMessage) {
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
                }
            }
        }
    }
}
