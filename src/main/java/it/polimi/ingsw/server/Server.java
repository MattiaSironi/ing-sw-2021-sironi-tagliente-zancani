package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.message.CommonMessages.ChooseNumberOfPlayer;
import it.polimi.ingsw.message.CommonMessages.IdMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.client.RemoteView;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Server {

    private static final int port = 1234;
    private final ServerSocket serverSocket;
    private final ExecutorService executor = Executors.newFixedThreadPool(128);
    private final ArrayList<RemoteView> waitingConnection = new ArrayList<>();
    private final Map<Game, ArrayList<RemoteView>> gameList = new HashMap<>();
    private int gameID=0;
    private int numPlayers = -1;

    public static void main( String[] args )
    {
        Server server;
        try {
            server = new Server();
            server.run();
        } catch (IOException e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
        }
    }

    public synchronized void waitingRoom(RemoteView rv) {
        System.out.println("gew");
        waitingConnection.add(rv);
        if (waitingConnection.size() == 1) {
            System.out.println("rg");
            waitingConnection.get(0).getClientConnection().send(new ChooseNumberOfPlayer(-1));
        }
    }

//    public void hostSetup(RemoteView rv){
//        while(waitingConnection.size() != numPlayers) {
//        }
//        gameSetup();
//
//    }

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

    public void gameSetup(SocketClientConnection scc) throws InterruptedException {
        while (waitingConnection.size() < numPlayers && scc.isActive()) {
            TimeUnit.MILLISECONDS.sleep(500);
        }
        if (scc.isActive()) {
            ArrayList<RemoteView> remoteViews = new ArrayList<>();
            Game game;
            if (numPlayers==1)  game = new Game(true, gameID);
            else game = new Game(false, gameID);

            Controller controller = new Controller(game);
            RemoteView rv1 = waitingConnection.get(0);
            rv1.setID(0);
            rv1.setGameID(gameID);
            waitingConnection.remove(0);
            remoteViews.add(rv1);
            rv1.getClientConnection().send(new IdMessage(0));
            rv1.getClientConnection().send(new ChooseNumberOfPlayer(numPlayers));
            rv1.addObserver(controller);
            game.addObserver(rv1);
            if (numPlayers >= 2) {
//            RemoteView.setSize(2); // per adesso
                RemoteView rv2 = waitingConnection.get(0);
                rv2.setID(1);
                rv2.setGameID(gameID);
                waitingConnection.remove(0);
                remoteViews.add(rv2);
                rv2.getClientConnection().send(new IdMessage(1));
                rv2.getClientConnection().send(new ChooseNumberOfPlayer(numPlayers));
                rv2.addObserver(controller);
                game.addObserver(rv2);
            }
            if (numPlayers >= 3) {
//            RemoteView.setSize(3); // per adesso
                RemoteView rv3 = waitingConnection.get(0);
                rv3.setID(2);
                rv3.setGameID(gameID);
                remoteViews.add(rv3);
                waitingConnection.remove(0);
                rv3.getClientConnection().send(new IdMessage(2));
                rv3.getClientConnection().send(new ChooseNumberOfPlayer(numPlayers));
                rv3.addObserver(controller);
                game.addObserver(rv3);
            }
            if (numPlayers == 4) {
//            RemoteView.setSize(4); // per adesso
                RemoteView rv4 = waitingConnection.get(0);
                rv4.setID(3);
                rv4.setGameID(gameID);
                remoteViews.add(rv4);
                waitingConnection.remove(0);
                rv4.getClientConnection().send(new IdMessage(3));
                rv4.getClientConnection().send(new ChooseNumberOfPlayer(numPlayers));
                rv4.addObserver(controller);
                game.addObserver(rv4);
            }
            game.setNumPlayer(numPlayers);
            addGame(game, remoteViews);
            gameID++;
            numPlayers = -1;
            if (waitingConnection.size() >= 1) {
                System.out.println("rg");
                waitingConnection.get(0).getClientConnection().send(new ChooseNumberOfPlayer(-1));
            }
        }
    }

    public void addGame(Game game, ArrayList<RemoteView> remoteViews) {
        gameList.put(game, remoteViews);
    }


    public void run() {
        while (true) {
            try {
                Socket newSocket = serverSocket.accept();
//                  newSocket.setSoTimeout(20000);
                SocketClientConnection socketConnection = new SocketClientConnection(newSocket, this);
                RemoteView remoteView = new RemoteView(socketConnection);
                executor.submit(remoteView);
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
    }


    public void setNumPlayers(int numPlayers, SocketClientConnection scc) {
        this.numPlayers = numPlayers;
        try {
            gameSetup(scc);
        } catch (InterruptedException ignored) {

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



    public boolean logOutFromWaiting(RemoteView remoteView) {
        boolean wasInside;
        if (waitingConnection.indexOf(remoteView) == 0) {
             wasInside = this.waitingConnection.remove(remoteView);
            if (!waitingConnection.isEmpty()) {
                waitingConnection.get(0).getClientConnection().send(new ChooseNumberOfPlayer(-1));
            }

        } else  wasInside = this.waitingConnection.remove(remoteView);

        return wasInside;
    }

    public synchronized void logOutFromGame(int gameID) {

        Game game = getGameByID(gameID);
        if (game != null)  {

            for (RemoteView rv : gameList.get(game))  {
                if (!rv.getClientConnection().getSocket().isClosed())
                rv.getClientConnection().closeConnection();
            }
            gameList.remove(game);



        }

    }

    private Game getGameByID(int gameID) {
        Game game = null;
        for (Game g : gameList.keySet())  {
            if (g.getGameID()==gameID) game = g;
        }
        return game;
    }
}

