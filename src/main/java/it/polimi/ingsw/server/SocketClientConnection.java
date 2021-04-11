package it.polimi.ingsw.server;

import it.polimi.ingsw.observer.Observable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class SocketClientConnection extends Observable<String> implements ClientConnection, Runnable {

    private Socket socket;
    private ObjectOutputStream out;
    private Server server;

    private boolean active = true;

    public SocketClientConnection(Socket socket, Server server) {
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
        Scanner in;
        try{
            in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            server.initialPhaseHandler(this);
            nicknameSetUp();
                while (true) {
                    send("Type any word");
                    String read;
                    read = in.nextLine();
                    notify(read);
                    if (read.equals("quit")) {
                    break;
                    }
                }
            close();
        } catch (IOException | NoSuchElementException e){
                System.err.println("Error!" + e.getMessage());
            }

    }

    public void nicknameSetUp(){
        Scanner in;
        String nickname;
        try{
            in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            asyncSend("Welcome to wonderful alpha version of MoR\nChoose your nickname:\n");
            nickname = in.nextLine();
            notify(nickname);
        } catch (IOException | NoSuchElementException e){
            System.err.println("Error!" + e.getMessage());
        }
    }

    public int setNumPlayers(){
        int numPlayers = 0;
        String input;
        Scanner in;
        try{
            in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            asyncSend("Choose number of player:");
            input = in.nextLine();
            numPlayers = Integer.valueOf(input);
        } catch (IOException | NoSuchElementException e){
            System.err.println("Error!" + e.getMessage());
        }
        return numPlayers;
    }
}
