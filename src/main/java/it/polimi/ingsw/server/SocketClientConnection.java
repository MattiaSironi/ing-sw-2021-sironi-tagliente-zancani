package it.polimi.ingsw.server;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.message.ActionMessages.*;
import it.polimi.ingsw.message.CommonMessages.*;
import it.polimi.ingsw.observer.Observable;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;


public class SocketClientConnection extends Observable<Message> implements Runnable {
    private boolean first;
    private Socket socket;
    private Socket socketPinger;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private ObjectOutputStream pingOut;
    private ObjectInputStream pingIn;
    private Server server;
    private int ID;
    private Thread pinger;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    private boolean active = true;

    public SocketClientConnection(boolean first, Socket socket, Server server, Socket socketPinger) {
        this.first = first;
        this.socket = socket;
        this.server = server;
        this.socketPinger = socketPinger;

        this.pinger = new Thread(() -> {
            try {
                pingOut = new ObjectOutputStream(socketPinger.getOutputStream()); // SE LI INVERTO NON FUNZIONA?
                pingIn = new ObjectInputStream(socketPinger.getInputStream());
                while (isActive()) {
                    pingOut.reset();
                    pingOut.writeObject("pong");
                    pingOut.flush();
                    for (int i = 0; i < 10000; i++);
                    System.out.println(pingIn.readObject());
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

    public Thread getPinger() {
        return pinger;
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
            pinger.start();
            out = new ObjectOutputStream(socket.getOutputStream()); // SE LI INVERTO NON FUNZIONA?
            in = new ObjectInputStream(socket.getInputStream());

        } catch (IOException | NoSuchElementException e) {
            System.err.println("Error!" + e.getMessage());
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








