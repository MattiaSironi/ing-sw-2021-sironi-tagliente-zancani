package it.polimi.ingsw.view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketServerConnection {
    private Socket socket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private Thread pinger;
    private boolean isActive = true;


    public SocketServerConnection() {
        pinger = new Thread(() -> {
            try {
                System.out.println("thread partito");
                Socket pingerSocket = new Socket("127.0.0.1", 12345);
                ObjectInputStream pingerIn = new ObjectInputStream(pingerSocket.getInputStream());
                ObjectOutputStream pingerOut = new ObjectOutputStream(pingerSocket.getOutputStream());
                while (isActive()) {
                    for (int i = 0; i < 10000; i++);
                    System.out.println(pingerIn.readObject());
                    pingerOut.reset();
                    pingerOut.writeObject("ping");
                    pingerOut.flush();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }

    public Thread getPinger() {
        return pinger;
    }

    public void run() throws IOException {
        socket = new Socket("127.0.0.1", 1234);
        System.out.println("prestart");
        pinger.start();
        socketIn = new ObjectInputStream(socket.getInputStream());
        socketOut = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("Connection established");
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
}
