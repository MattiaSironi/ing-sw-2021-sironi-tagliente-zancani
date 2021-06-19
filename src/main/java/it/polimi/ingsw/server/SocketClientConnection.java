package it.polimi.ingsw.server;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.message.CommonMessages.*;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.client.RemoteView;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.lang.*;


public class SocketClientConnection extends Observable<Message> implements Runnable {
    private final Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private final Server server;
    private int ID;
    private final Thread socketListener;
    private final Thread pingSender;
    private RemoteView remoteView;

    public RemoteView getRemoteView() {
        return remoteView;
    }

    private boolean active = true;

    public int getID() {
        return ID;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setRemoteView(RemoteView remoteView) {
        this.remoteView = remoteView;
    }



    public SocketClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        this.socketListener = new Thread(() -> {
            try {
                while (isActive()) {
                    Object message = receive();
                    messageHandler(message);
                }
            } catch (IOException | ClassNotFoundException e) {
                close();

                System.out.println("Player #" + this.getRemoteView().getID() + " in game #" + this.getRemoteView().getGameID() + " has been disconnected!");
            }
        });
        this.pingSender =  new Thread(() -> {
            try {
                while (isActive()) {
                    Thread.sleep(10000);
                    send(new PingMessage());
                }
            } catch (InterruptedException e) {


                System.out.println("Player #" + this.getRemoteView().getID() + " in game #" + this.getRemoteView().getGameID() + " has  been disconnected!");
            }
        });
    }

    public Server getServer() {
        return server;
    }

    public synchronized boolean isActive() {
        return active;
    }

    public synchronized void send(Message message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            close();
            System.err.println(e.getMessage());
        }
    }

    public Object receive() throws IOException, ClassNotFoundException {

        return in.readObject();

    }

    public synchronized void closeConnection() {

        active = false;
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }

    }

    public void close() {
        if (!socket.isClosed()) {
            closeConnection();
            if (!server.logOutFromWaiting(this.getRemoteView()))
                server.logOutFromGame(this.getRemoteView().getGameID());

            System.out.println("Deregistering client...");
            System.out.println("Done!");
        }
    }

    public void run() {

        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            socketListener.start();
            pingSender.start();
        } catch (IOException | NoSuchElementException e) {
            System.err.println("Error!" + e.getMessage());
        }
    }

    public void messageHandler(Object message){
        if(message instanceof PingMessage){

        }
        else if(message instanceof ChooseNumberOfPlayer){
            server.setNumPlayers(((ChooseNumberOfPlayer) message).getNumberOfPlayers(), this);
        }
        else{
            remoteView.handleAction(message);
        }
    }



    }








