package it.polimi.ingsw.client;

import it.polimi.ingsw.message.CommonMessages.ErrorMessage;
import it.polimi.ingsw.message.CommonMessages.IdMessage;
import it.polimi.ingsw.message.CommonMessages.PingMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

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
                Object o = null;
                try {
                    o = receive();
                } catch (IOException e) {
                    close();

                } catch (ClassNotFoundException e) {
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
        System.out.println("Insert IP Address : ");
        System.out.print("> ");
        String s = new Scanner(System.in).nextLine();
        socketInit(s);

    }

    public void socketInit(String s) throws IOException {
        socket = new Socket(s, 1234);
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
            isActive = false;
        }
    }

    public Object receive() throws IOException, ClassNotFoundException {
        Object inputObject = null;

        inputObject = socketIn.readObject();

        return inputObject;


    }

    public void close() {
        isActive = false;


        try {
            closeConnection();

        }
        catch (IOException e) {
            System.out.println("Error closing socket!");
        }
    }

    public synchronized void closeConnection() throws IOException {
        if (!socket.isClosed()) {
            System.out.println("Game is ended. See you next time!");

            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }

    public void messageHandler(Object o){
        if(o instanceof IdMessage){
            cac.getCli().printToConsole("Your ID is " + ((IdMessage) o).getID());
            cac.setID(((IdMessage)o).getID());
        }
        else if(o instanceof PingMessage){

        }
        else if (o instanceof ErrorMessage)  {
            cac.getCli().printToConsole(((ErrorMessage) o).getString());
        }

        else{
            cac.handleAction(o);
        }
    }
}
