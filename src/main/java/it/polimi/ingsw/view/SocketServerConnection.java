package it.polimi.ingsw.view;

import it.polimi.ingsw.message.CommonMessages.ChooseNumberOfPlayer;
import it.polimi.ingsw.message.CommonMessages.ErrorMessage;
import it.polimi.ingsw.message.CommonMessages.IdMessage;
import it.polimi.ingsw.message.CommonMessages.PingMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketServerConnection {
    private Socket socket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private Thread socketListener, pingSender;
    private boolean isActive = true;
    private ClientActionController cac;




    public SocketServerConnection() {
        socketListener = new Thread(() -> {
            while (isActive()) {
                Object o = receive();
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

    public void setCac(ClientActionController cac) {
        this.cac = cac;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }

    public void run() throws IOException {
        socket = new Socket("127.0.0.1", 1234);
        socketIn = new ObjectInputStream(socket.getInputStream());
        socketOut = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("Connection established");

        socketListener.start();

        pingSender.start();
    }



    public synchronized void send(Object message) {
        try {
            socketOut.reset();
            socketOut.writeObject(message);
            socketOut.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public Object receive() {
        Object inputObject = null;
        try {
            inputObject = socketIn.readObject();
        } catch (Exception e) {
        }
        return inputObject;
    }

    public void closeConnection() throws IOException {
        socketIn.close();
        socketOut.close();
        socket.close();
    }

    public void messageHandler(Object o){
        if(o instanceof IdMessage){
            cac.getCli().printToConsole("Your ID is " + ((IdMessage) o).getID());
            cac.setID(((IdMessage)o).getID());
            if(((IdMessage) o).getID() == 0) {
                cac.setNumberOfPlayers();
            }
            else {
                cac.getCli().printToConsole("Waiting for the host...");
            }
        }
        if(o instanceof PingMessage){

        }
        if (o instanceof ErrorMessage)  {
            cac.getCli().printToConsole(((ErrorMessage) o).getString());
        }

        else{
            cac.handleAction(o);
        }
    }

}
