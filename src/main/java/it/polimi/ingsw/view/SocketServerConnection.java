package it.polimi.ingsw.view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketServerConnection {
    private Socket socket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;

    public void run() throws IOException {
        socket = new Socket("127.0.0.1", 1234);
        System.out.println("Connection established");
        socketIn = new ObjectInputStream(socket.getInputStream());
        socketOut = new ObjectOutputStream(socket.getOutputStream());
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
