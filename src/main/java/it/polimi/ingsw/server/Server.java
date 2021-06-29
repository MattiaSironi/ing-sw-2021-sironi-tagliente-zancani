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

/**
 * Server class is the main class of the server side. It runs the server and handles the queue of the players
 */

public class Server {

    private static final int port = 1234;
    private final ServerSocket serverSocket;
    private final ArrayList<RemoteView> waitingConnection = new ArrayList<>();
    private final Map<Game, ArrayList<RemoteView>> gameList = new HashMap<>();
    private int gameID=0;
    private int numPlayers = -1;

    /**
     * Method main creates and runs the new server
     * @param args
     */

    public static void main( String[] args ) {
        Server server;
        try {
            server = new Server();
            server.run();
        } catch (IOException e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
        }
    }

    /**
     * Method waitingRoom adds player to the waiting room of the game, and sends the request of the number of the player to the first player of the list
     * @param rv
     */
    public synchronized void waitingRoom(RemoteView rv) {
        waitingConnection.add(rv);
        if (waitingConnection.size() == 1) {
            waitingConnection.get(0).getClientConnection().send(new ChooseNumberOfPlayer(-1));
        }
    }

    /**
     * Method gameSetup handles the creation of the MVC pattern. It creates the game, the controller and one remote view for each client. It also assign
     * the ID to each remote view and send it to the client
     * @param scc   of type SocketClientConnection - it's the SocketClientConnection which is added in to game and removed from the waiting room
     * @throws InterruptedException
     */
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
                waitingConnection.get(0).getClientConnection().send(new ChooseNumberOfPlayer(-1));
            }
        }
    }

    /**
     * Method addGame adds one active game to the list of games
     * @param game          of type Game - It's the game to be added
     * @param remoteViews   of type ArrayList<RemoteView>, it's the list of remoteViews which are connected to the game
     */
    public void addGame(Game game, ArrayList<RemoteView> remoteViews) {
        gameList.put(game, remoteViews);
    }

    /**
     * Method run establishes a new connection creating a new SocketClientConnection and creating a new RemoteView after request of the client
     */
    public void run() {
        while (true) {
            try {
                Socket newSocket = serverSocket.accept();
                System.out.println("Client connected");
                newSocket.setSoTimeout(20000);
                SocketClientConnection socketConnection = new SocketClientConnection(newSocket, this);
                RemoteView remoteView = new RemoteView(socketConnection);
                new Thread (() -> {
                    remoteView.run();
                }).start();
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
    }

    /**
     * Method setNumPlayers calls the game setup when the number of player chosen by the hot is reached
     * @param numPlayers    of type int - number of player chosen bu the host
     * @param scc           of type SocketClientConnection - SocketCLientConnection of the host
     */
    public void setNumPlayers(int numPlayers, SocketClientConnection scc) {
        this.numPlayers = numPlayers;
        try {
            gameSetup(scc);
        } catch (InterruptedException ignored) {
        }
    }

    /**
     * Method Server is the constructor of the ServerClass
     * @throws IOException
     */
    public Server() throws IOException {
        this.serverSocket = new ServerSocket(port);
        System.err.println("Server now is on");
    }

    /**
     * Method logOutFromWaiting removes the remoteView from the waiting room when it disconnects from the server.
     * If there's already a remoteview in waiting room,  this becomes the new host.
     * @param remoteView    of type RemoteView - RemoteView to be logged out
     * @return  wasInside, which is true if the remote view was inside the waiting room, false instead
     */
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

    /**
     * Method logOutFromGame removes all the remote views in a game
     * @param gameID    of type int - it is the ID of the game to be closed
     */
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

    /**
     * Method getGameByID return the game with the ID given
     * @param gameID    of type int - it is the ID of the game looked for
     * @return
     */

    private Game getGameByID(int gameID) {
        Game game = null;
        for (Game g : gameList.keySet())  {
            if (g.getGameID()==gameID) game = g;
        }
        return game;
    }
}

