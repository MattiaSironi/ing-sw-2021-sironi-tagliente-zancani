package it.polimi.ingsw.server;

//import it.polimi.ingsw.MessageReceiver.MessageReceiver;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.RemoteView;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Remote;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int port = 1234;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private Map<Integer, ClientConnection> waitingConnection = new HashMap<>();
    private Map<ClientConnection, ClientConnection> playingConnection = new HashMap<>();
    private static boolean isFirst = true;
    private int numPlayers;
    private int usedID = 0;


    public synchronized void initialPhaseHandler(ClientConnection c){
        waitingConnection.put(usedID, c);
        this.usedID++;
        if(waitingConnection.size() == numPlayers){
            Game game = new Game();
            Controller controller = new Controller(game);
            List<Integer> keys = new ArrayList<>(waitingConnection.keySet());
            ClientConnection c1 = waitingConnection.get(keys.get(0));
            RemoteView remoteView1 = new RemoteView(c1, 0);
            remoteView1.addObserver(controller);
            game.addObserver(remoteView1);
            if(numPlayers >= 2){
                ClientConnection c2 = waitingConnection.get(keys.get(1));
                RemoteView remoteView2 = new RemoteView(c2, 1);
                remoteView2.addObserver(controller);
                game.addObserver(remoteView2);
            }
            if(numPlayers >= 3){
                ClientConnection c3 = waitingConnection.get(keys.get(2));
                RemoteView remoteView3 = new RemoteView(c3, 2);
                remoteView3.addObserver(controller);
                game.addObserver(remoteView3);
            }
            if(numPlayers == 4){
                ClientConnection c4 = waitingConnection.get(keys.get(3));
                RemoteView remoteView4 = new RemoteView(c4, 3);
                remoteView4.addObserver(controller);
                game.addObserver(remoteView4);
            }

        }
    }
    /*public synchronized void initialPhaseHandler(ClientConnection c, String nickname){
        MessageReceiver messageReceiver = new MessageReceiver(c);
        RemoteView remoteView = new RemoteView(c, messageReceiver);
        c.addObserver(messageReceiver);
        Game game = new Game();
        Controller controller = new Controller(game);
        controller.addObserver(remoteView);
        game.addObserver(remoteView);
    }*/

    public void run(){
        while(true){
            try {
                Socket newSocket = serverSocket.accept();
                SocketClientConnection socketConnection = new SocketClientConnection(newSocket, this);
                if(isFirst){
                    isFirst = false;
                    numPlayers = socketConnection.setNumPlayers();
                }
                executor.submit(socketConnection);
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
    }

    public synchronized void deregisterConnection(ClientConnection c) {
        ClientConnection opponent = playingConnection.get(c);
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
