package it.polimi.ingsw.server;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.message.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class SocketClientConnection extends Observable<Message> implements ClientConnection, Runnable {
    private boolean first;
    private Socket socket;
    private ObjectOutputStream out;
    private Scanner in;
    private Server server;
    private int ID;

    public int getID() {
        return ID;
    }
    @Override
    public void setID(int ID) {
        this.ID = ID;
    }

    public Scanner getIn() {
        return in;
    }

    private boolean active = true;

    public SocketClientConnection(boolean first, Socket socket, Server server) {
        this.first=first;
        this.socket = socket;
        this.server = server;
    }

    private synchronized boolean isActive() {
        return active;
    }

    private synchronized void send(Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    public synchronized void closeConnection() {
        send("Connection closed!");
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
    }



    private void close() {
        closeConnection();
        System.out.println("Deregistering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

    @Override
    public void asyncSend(final Object message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }

    @Override
    public void run() {
        //Scanner in;
        try{

            in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            if (this.first)  {
                setNumPlayers(in);
            }
            else {
                send("waiting the host selects the number of players...");
                while (!server.isReady()) {
                }
            }
            server.initialPhaseHandler(this);
            nicknameSetUp(in);
            while (isActive()) {
////                    send("Type any word");
//                    String read;
//                    read = in.nextLine();
//                    notify(read);
//                    if (read.equals("quit")) {
//                    break;
            }
//                }
            close();
        } catch (IOException | NoSuchElementException e){
            System.err.println("Error!" + e.getMessage());
        }

    }

    public void nicknameSetUp(Scanner in){
//        Scanner in;
        String nickname;
        try{
//            in = new Scanner(socket.getInputStream());
//            out = new ObjectOutputStream(socket.getOutputStream());
            asyncSend("Welcome to wonderful alpha version of MoR\nChoose your nickname:\n");
            nickname = in.nextLine();
            notify(new Nickname(nickname, this.ID));
        } catch (/*IOException | */ NoSuchElementException e){
            System.err.println("Error!" + e.getMessage());
        }
    }

    public int setNumPlayers(Scanner in){
        int numPlayers = 0;
        String input;
//        Scanner in;
        try{
//            in = new Scanner(socket.getInputStream());
//            out = new ObjectOutputStream(socket.getOutputStream());
            asyncSend("Choose number of player:");
            input = in.nextLine();
            numPlayers = Integer.parseInt(input);
            server.setNumPlayers(numPlayers);
            server.setReady(true);
        } catch (/*IOException | */ NoSuchElementException e){
            System.err.println("Error!" + e.getMessage());
        }
        return numPlayers;
    }
}
