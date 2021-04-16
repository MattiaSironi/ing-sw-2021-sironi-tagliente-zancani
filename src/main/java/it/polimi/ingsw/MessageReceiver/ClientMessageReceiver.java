package it.polimi.ingsw.MessageReceiver;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.message.Nickname;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.ModelMultiplayerView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Mattia Sironi
 */
public class ClientMessageReceiver implements Observer<Message> {
    private ModelMultiplayerView mmv;
    private Socket socket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private boolean active = true;

    public ClientMessageReceiver(ModelMultiplayerView mmv) {
        this.mmv = mmv;

    }

    public synchronized boolean isActive() {
        return active;
    }

    public synchronized void setActive(boolean active) {
        this.active = active;
    }


    private synchronized void send(Object message) {
        try {
            socketOut.reset();
            socketOut.writeObject(message);
            socketOut.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(Message message) {

    }

    @Override
    public void update(Nickname message) {
        if (message.getString().equals("YES")) {
            try {
                runna();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("aooooooo");
            }
        } else {
            send(message);
        }
    }

    public void runna() throws IOException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);
        socket = new Socket("127.0.0.1", 1234);
        System.out.println("Connection established");
        socketIn = new ObjectInputStream(socket.getInputStream());
        socketOut = new ObjectOutputStream(socket.getOutputStream());
        String inputObject = (String) socketIn.readObject();
        mmv.sendnotify(inputObject);
        String scannerata = scan.nextLine();
        send(scannerata);
        inputObject = (String) socketIn.readObject();
        mmv.sendnotify(inputObject);
        inputObject = (String) socketIn.readObject();
        mmv.sendnotify(inputObject);
        scannerata = scan.nextLine();
        send(scannerata);
        inputObject = (String) socketIn.readObject();
        mmv.sendnotify(inputObject);
        inputObject = (String) socketIn.readObject();
        mmv.sendnotify(inputObject);
        inputObject = (String) socketIn.readObject();
        mmv.sendnotify(inputObject);


        socketIn.close();
        socketOut.close();
        socket.close();

    }

    public Thread asyncReadFromSocket(final ObjectInputStream socketIn) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        Object inputObject = socketIn.readObject();
//                            if(inputObject instanceof String){
                        mmv.sendnotify((String) inputObject);
//                            }  else {
//                                throw new IllegalArgumentException();
//                            }
                    }
                } catch (Exception e) {
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }


}


