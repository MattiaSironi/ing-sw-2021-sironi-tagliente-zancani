package it.polimi.ingsw.server;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.message.CommonMessages.*;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.view.RemoteView;


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
    private Thread socketListener;
    private RemoteView remoteView;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setRemoteView(RemoteView remoteView) {
        this.remoteView = remoteView;
    }

    private boolean active = true;

    public SocketClientConnection(boolean first, Socket socket, Server server) {
        this.first = first;
        this.socket = socket;
        this.server = server;

        this.socketListener = new Thread(() -> {
            try {
                while (isActive()) {
                    Object message = receive();
                    messageHandler(message);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("Player # " + this.ID + "has  been disconnected!");
            }
        });
    }

    public Server getServer() {
        return server;
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

    public Object receive() throws IOException, ClassNotFoundException {
        return in.readObject();
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
            socketListener.start();
        } catch (IOException | NoSuchElementException e) {
            System.err.println("Error!" + e.getMessage());
        }
    }

    public void messageHandler(Object message){
//        if(message instanceof PingMessage){
//            send((PingMessage)message);
//        }
        if(message instanceof ChooseNumberOfPlayer){
            server.setNumPlayers(((ChooseNumberOfPlayer)message).getNumberOfPlayers());
            server.setReady(true);
        }
        else{
            remoteView.handleAction(message);
        }
    }


//    public Thread ping(){
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    ServerSocket pingSocket = new ServerSocket(12346);
//                    Socket  newPingSocket  =  pingSocket.accept();
//                    newPingSocket.setSoTimeout(16000);
//                    pingOut = new ObjectOutputStream(newPingSocket.getOutputStream()); // SE LI INVERTO NON FUNZIONA?
//                    pingIn = new ObjectInputStream(newPingSocket.getInputStream());
//                    while (isActive()) {
//                        pingOut.reset();
//                        pingOut.write(0);
//                        pingOut.flush();
//                        pingIn.read();
//
//                    }
//
//
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    System.out.println("Player # "  +  );
//                }
//
//
//            }
//        });
//        t.start();
//        return t;
    }








