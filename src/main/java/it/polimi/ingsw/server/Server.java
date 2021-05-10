package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.message.CommonMessages.ChooseNumberOfPlayer;
import it.polimi.ingsw.message.CommonMessages.IdMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.RemoteView;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {

    private static final int port = 1234;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private Map<Integer, RemoteView> waitingConnection = new HashMap<>();
    private Map<SocketClientConnection, SocketClientConnection> playingConnection = new HashMap<>();
    private static boolean isFirst = true;
    private volatile int numPlayers;
    private int usedID = 0;
    private volatile boolean ready;
    private ServerSocket pingerSocket;

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean isReady() {
        return ready;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public void waitingRoom(RemoteView rv){
        int id = usedID;
        waitingConnection.put(id, rv);
        rv.getClientConnection().send(new IdMessage(id));
        this.usedID++;
        if(id == 0){
            hostSetup(rv);
        }
        else{
            while(!isReady()){

            }
        }
    }

    public void hostSetup(RemoteView rv){
        while(waitingConnection.size() != numPlayers) {
        }
        gameSetup();

    }

//    public void initialPhaseHandler(RemoteView rv) {
//        System.out.println("sonoqui");
//        int id = usedID;
//        System.out.println(id);
//        waitingConnection.put(id, rv);
//        rv.getClientConnection().send(new IdMessage(id));
//        this.usedID++;
//        if (id == 0) {
//            numPlayers = rv.setNumPlayers();
//        } else {
//            //send("waiting the host selects the number of players...");
//            System.out.println("prewhile");
//            while(!isReady()){
//
//            }
//            System.out.println("postwhile");
//        }
//        if(waitingConnection.size() == numPlayers) {
//            Game game = new Game();
//            Controller controller = new Controller(game);
//            List<Integer> keys = new ArrayList<>(waitingConnection.keySet());
//            RemoteView rv1 = waitingConnection.get(keys.get(0));
//            rv1.setID(keys.get(0));
//            rv1.getClientConnection().send(new ChooseNumberOfPlayer(numPlayers));
//            rv1.addObserver(controller);
//            game.addObserver(rv1);
//
//            if (numPlayers >= 2) {
//                RemoteView.setSize(2); // per adesso
//                RemoteView rv2 = waitingConnection.get(keys.get(1));
//                rv2.setID(keys.get(1));
//                rv2.getClientConnection().send(new ChooseNumberOfPlayer(numPlayers));
//                rv2.addObserver(controller);
//                game.addObserver(rv2);
//            }
//            if (numPlayers >= 3) {
//                RemoteView.setSize(3); // per adesso
//                RemoteView rv3 = waitingConnection.get(keys.get(2));
//                rv3.setID(keys.get(2));
//                rv3.getClientConnection().send(new ChooseNumberOfPlayer(numPlayers));
//                rv3.addObserver(controller);
//                game.addObserver(rv3);
//            }
//            if (numPlayers == 4) {
//                RemoteView.setSize(4); // per adesso
//                RemoteView rv4 = waitingConnection.get(keys.get(3));
//                rv4.setID(keys.get(3));
//                rv4.getClientConnection().send(new ChooseNumberOfPlayer(numPlayers));
//                rv4.addObserver(controller);
//                game.addObserver(rv4);
//            }
//            ServerSocket pingSocket = new ServerSocket(12345);
//            Socket newPingSocket = serverSocket.accept();
//
//        }
//
//        if(waitingConnection.size() > numPlayers){
//            waitingConnection.remove(rv);
//            rv.getClientConnection().close();
//        }
//    }

    public void gameSetup(){
        Game game = new Game();
        Controller controller = new Controller(game);
        List<Integer> keys = new ArrayList<>(waitingConnection.keySet());
        RemoteView rv1 = waitingConnection.get(keys.get(0));
        rv1.setID(keys.get(0));
        rv1.getClientConnection().send(new ChooseNumberOfPlayer(numPlayers));
        rv1.addObserver(controller);
        game.addObserver(rv1);
        if (numPlayers >= 2) {
            RemoteView.setSize(2); // per adesso
            RemoteView rv2 = waitingConnection.get(keys.get(1));
            rv2.setID(keys.get(1));
            rv2.getClientConnection().send(new ChooseNumberOfPlayer(numPlayers));
            rv2.addObserver(controller);
            game.addObserver(rv2);
        }
        if (numPlayers >= 3) {
            RemoteView.setSize(3); // per adesso
            RemoteView rv3 = waitingConnection.get(keys.get(2));
            rv3.setID(keys.get(2));
            rv3.getClientConnection().send(new ChooseNumberOfPlayer(numPlayers));
            rv3.addObserver(controller);
            game.addObserver(rv3);
        }
        if (numPlayers == 4) {
            RemoteView.setSize(4); // per adesso
            RemoteView rv4 = waitingConnection.get(keys.get(3));
            rv4.setID(keys.get(3));
            rv4.getClientConnection().send(new ChooseNumberOfPlayer(numPlayers));
            rv4.addObserver(controller);
            game.addObserver(rv4);
        }
    }




    public void run() {
        while (true) {
            try {
                Socket newSocket = serverSocket.accept();
                if (isFirst) {
                    isFirst = false;
                    SocketClientConnection socketConnection = new SocketClientConnection(true, newSocket, this);
                    RemoteView remoteView = new RemoteView(socketConnection);
                    executor.submit(remoteView);
                } else {
                    SocketClientConnection socketConnection = new SocketClientConnection(false, newSocket, this);
                    RemoteView remoteView = new RemoteView(socketConnection);
                    executor.submit(remoteView);
                }
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
    }

//    public synchronized void deregisterConnection(SocketClientConnection c) {
//        SocketClientConnection opponent = playingConnection.get(c);
//        if(opponent != null) {
//            opponent.closeConnection();
//        }
//        playingConnection.remove(c);
//        playingConnection.remove(opponent);
//        Iterator<Integer> iterator = waitingConnection.keySet().iterator();
//        while(iterator.hasNext()){
//            if(waitingConnection.get(iterator.next())==c){
//                iterator.remove();
//
//            }
//        }
//    }

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(port);
    }


}
