package it.polimi.ingsw.server;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.message.ActionMessages.*;
import it.polimi.ingsw.message.CommonMessages.*;
import it.polimi.ingsw.observer.Observable;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;



public class SocketClientConnection extends Observable<Message> implements Runnable {
    private boolean first;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Server server;
    private int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    private boolean active = true;

    public SocketClientConnection(boolean first, Socket socket, Server server) {
        this.first = first;
        this.socket = socket;
        this.server = server;
    }

    private synchronized boolean isActive() {
        return active;
    }

    public synchronized void send(Message message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    public synchronized void closeConnection() {
        //send("Connection closed!");
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
    }


    public void close() {
        closeConnection();
        System.out.println("Deregistering client...");
        System.out.println("Done!");
    }

    public void run() {

        try {
            out = new ObjectOutputStream(socket.getOutputStream()); // SE LI INVERTO NON FUNZIONA?
            in = new ObjectInputStream(socket.getInputStream());
            server.initialPhaseHandler(this); //FASE 1
            nicknameSetUp(in);
            while (isActive()) {

                Object actionMessage = in.readObject();
                if (actionMessage instanceof MarketMessage)  {
                    goToMarket((MarketMessage) actionMessage);
                }





            }
            close();
        } catch (IOException | NoSuchElementException | ClassNotFoundException e) {
            System.err.println("Error!" + e.getMessage());
        }

    }

    private void goToMarket(MarketMessage message) {
        notify(message);




    }

    public void nicknameSetUp(ObjectInputStream in) {
        Boolean setup = false;
        while (!setup) {
            try {
                Nickname nickname = (Nickname) in.readObject();
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
            numPlayers = ((ChooseNumberOfPlayer)in.readObject()).getNumberOfPlayers();
            server.setReady(true);
        } catch (/*IOException | */ NoSuchElementException e) {
            System.err.println("Error!" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return numPlayers;
    }
}
