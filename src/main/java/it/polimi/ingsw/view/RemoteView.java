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
        this.clientConnection.run();
        clientConnection.getServer().waitingRoom(this); //FASE 1
        nicknameSetUp();
        //            while (isActive()) {
//
//                Object actionMessage = in.readObject();
//                if (actionMessage instanceof MarketMessage)  {
//                    goToMarket((MarketMessage) actionMessage);
//                }

        clientConnection.getPinger().start(); //ping



    }

    public void nicknameSetUp() {
        boolean setup = false;
        while (!setup) {
            try {
                Nickname nickname = (Nickname)clientConnection.receive();
                if (nickname.getValid()) {
                    setup = true;
                } else {
                    notify(nickname);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public int setNumPlayers() {
        int numPlayers = 0;
        try {
            numPlayers = ((ChooseNumberOfPlayer)clientConnection.receive()).getNumberOfPlayers();
            clientConnection.getServer().setReady(true);
        } catch (/*IOException | */ NoSuchElementException e) {
            System.err.println("Error!" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return numPlayers;
    }

    private void goToMarket(MarketMessage message) throws IOException, ClassNotFoundException {
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
        if (message.getID() == this.ID) {
            clientConnection.send(new OutputMessage("Your nickname is " + message.getString()));
        } else {
            clientConnection.send(new OutputMessage("One of your opponents nickname is " + message.getString()));
        }
    }

    @Override
    public void update(InputMessage message) {

    }

    @Override
    public void update(IdMessage message) {
        if (message.getID()==size)  {
            notify(new ChooseNumberOfPlayer(size));
            notify(new ErrorMessage("all set", this.ID));
        }

    }

    @Override
    public void update(ErrorMessage message) {
        if(message.getID() == this.ID){
            clientConnection.send(message);
        }
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
    public void update(ResourceListMessage message) {
        clientConnection.send(message);


    }

    @Override
    public void update(PlaceResourceMessage message) {

    }

    @Override
    public void update(BuyDevCardMessage message) {

    }


    public RemoteView(SocketClientConnection c, int ID) {
        this.clientConnection = c;
        this.clientConnection.setID(ID);
        this.ID = ID;

    }

    public void handleAction(Nickname message) {
        notify(message);
    }

    public void handleAction(MarketMessage message)  {
        notify(message);
    }
}
