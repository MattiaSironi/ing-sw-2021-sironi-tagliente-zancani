package it.polimi.ingsw.server;

import it.polimi.ingsw.MessageReceiver.ServerMessageReceiver;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.Game;
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
    private Map<Integer, SocketClientConnection> waitingConnection = new HashMap<>();
    private Map<SocketClientConnection, SocketClientConnection> playingConnection = new HashMap<>();
    private static boolean isFirst = true;
    private int numPlayers;
    private int usedID = 0;
    private boolean ready;

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean isReady() {
        return ready;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public synchronized void initialPhaseHandler(SocketClientConnection c){
        int id = usedID;
        System.out.println(id);
        waitingConnection.put(id, c);
        c.send(new IdMessage(id));
        this.usedID++;
        if (id == 0) {
            numPlayers = c.setNumPlayers();
        } else {
            //send("waiting the host selects the number of players...");
            while (!isReady()) {
            }
        }
        if(waitingConnection.size() == numPlayers) {
            Game game = new Game();
            Controller controller = new Controller(game);
            List<Integer> keys = new ArrayList<>(waitingConnection.keySet());
            SocketClientConnection c1 = waitingConnection.get(keys.get(0));
            RemoteView remoteView1 = new RemoteView(c1, keys.get(0));
            ServerMessageReceiver smr1 = new ServerMessageReceiver(remoteView1);
            c1.addObserver(smr1);
            c1.send(new OutputMessage("ready"));
            remoteView1.addObserver(controller);
            game.addObserver(remoteView1);

            if (numPlayers >= 2) {
                SocketClientConnection c2 = waitingConnection.get(keys.get(1));
                RemoteView remoteView2 = new RemoteView(c2, keys.get(1));
                ServerMessageReceiver smr2 = new ServerMessageReceiver(remoteView2);
                c2.addObserver(smr2);
                c2.send(new OutputMessage("ready"));
                remoteView2.addObserver(controller);
                game.addObserver(remoteView2);

            }
            if (numPlayers >= 3) {
                SocketClientConnection c3 = waitingConnection.get(keys.get(2));
                RemoteView remoteView3 = new RemoteView(c3, keys.get(2));
                ServerMessageReceiver smr3 = new ServerMessageReceiver(remoteView3);
                c3.addObserver(smr3);
                c3.send(new OutputMessage("ready"));
                remoteView3.addObserver(controller);
                game.addObserver(remoteView3);
            }
            if (numPlayers == 4) {
                SocketClientConnection c4 = waitingConnection.get(keys.get(3));
                RemoteView remoteView4 = new RemoteView(c4, keys.get(3));
                ServerMessageReceiver smr4 = new ServerMessageReceiver(remoteView4);
                c4.addObserver(smr4);
                c4.send(new OutputMessage("ready"));
                remoteView4.addObserver(controller);
                game.addObserver(remoteView4);
            }
        }
        if(waitingConnection.size() > numPlayers){
            waitingConnection.remove(c);
            c.close();
        }
    }

    public void run() {
        while (true) {
            try {
                Socket newSocket = serverSocket.accept();
                if (isFirst) {
                    isFirst = false;
                    SocketClientConnection socketConnection = new SocketClientConnection(true, newSocket, this);
                    executor.submit(socketConnection);
                } else {

                    SocketClientConnection socketConnection = new SocketClientConnection(false, newSocket, this);
                    executor.submit(socketConnection);
                }
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
    }

    public synchronized void deregisterConnection(SocketClientConnection c) {
        SocketClientConnection opponent = playingConnection.get(c);
        if(opponent != null) {
            opponent.closeConnection();
        }
        playingConnection.remove(c);
        playingConnection.remove(opponent);
        Iterator<Integer> iterator = waitingConnection.keySet().iterator();
        while(iterator.hasNext()){
            if(waitingConnection.get(iterator.next())==c){
                iterator.remove();

            }
        }
    }

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(port);
    }


}
