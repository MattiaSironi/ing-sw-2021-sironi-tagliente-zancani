package it.polimi.ingsw.client;



import it.polimi.ingsw.message.CommonMessages.IdMessage;
import it.polimi.ingsw.message.CommonMessages.PingMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Class SocketServerConnection handles the communication between client and server in the client side.
 * It is able to send and receive messages to the server and it also sends a ping message
 */
public class SocketServerConnection {
    private Socket socket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private final Thread socketListener;
    private final Thread pingSender;
    private boolean isActive = true;
    private UI ui;


    /**
     * Method SocketServerConnection is the constructor of the class and it define two thread, one receiving objects and one sending ping messages
     */
    public SocketServerConnection() {
        socketListener = new Thread(() -> {
            while (isActive()) {
                Object o = null;
                try {
                    o = receive();
                } catch (IOException | ClassNotFoundException e) {
                    close();

                }
                messageHandler(o);
            }
        });

        pingSender = new Thread(() -> {
            while (isActive()) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                send(new PingMessage());
            }
        });
    }

    public void setUI(UI ui) {
        this.ui = ui;
    }

    public boolean isActive() {
        return isActive;
    }

    /**
     * Method run establishes the connection to the server and it call socketInit method
     * @throws IOException
     */
    public void run() throws IOException {
        System.out.println("Insert IP Address : ");
        System.out.print("> ");
        String s = new Scanner(System.in).nextLine();
        socketInit(s);

    }

    /**
     * Method socketInit open the communication channels to the server and runs the two threads defined in the constructor
     * @param s
     * @throws IOException
     */
    public void socketInit(String s) throws IOException {
        try {
            socket = new Socket(s, 1234);
            socketIn = new ObjectInputStream(socket.getInputStream());
            socketOut = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Connection established");
            socketListener.start();
            pingSender.start();
        } catch (UnknownHostException | SocketException e) {

            System.out.println("Server not available at this IP address. Closing the application...");
            System.exit(0);
        }
    }


    /**
     * method send sends objects to the server
     * @param message of type Object - message to be sent
     */
    public synchronized void send(Object message) {
        try {
            socketOut.reset();
            socketOut.writeObject(message);
            socketOut.flush();
        } catch (IOException e) {
            isActive = false;
        }
    }

    /**
     * Method receive receives object sent by the server
     * @return  the object received
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Object receive() throws IOException, ClassNotFoundException {
        Object inputObject;

        inputObject = socketIn.readObject();

        return inputObject;


    }

    /**
     * Method close handles the connection to the server
     */
    public void close() {
        isActive = false;
        try {
            closeConnection();
        }
        catch (IOException e) {
            System.out.println("Error closing socket!");
        }
    }
    /**
     * Method closeConnection disconnects the client from the server and it closes the connection
     */
    public synchronized void closeConnection() throws IOException {
        if (!socket.isClosed()) {
            System.out.println("Game is ended. See you next time!");
            ui.disconnect();

            socketIn.close();
            socketOut.close();
            socket.close();

        }
    }

    /**
     * Method messageHandler handles the messages received from the server.
     * It forwards the received message to the UserInterface except id it is ad IdMessage
     * @param o of type Object - the object received
     */
    public void messageHandler(Object o){
        if(o instanceof IdMessage){
            ui.setID(((IdMessage)o).getID());
        }
        else{
            ui.handleAction(o);
        }
    }
}
