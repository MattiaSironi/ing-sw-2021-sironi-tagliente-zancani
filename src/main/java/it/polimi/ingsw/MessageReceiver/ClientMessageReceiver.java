package it.polimi.ingsw.MessageReceiver;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.CLI;
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
    private int ID;
    private CLI cli;
    private String ready;


    public ClientMessageReceiver(CLI cli, ModelMultiplayerView mmv) {
        this.mmv = mmv;
        this.cli = cli;

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
                setup();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("aooooooo");
            }
        } else {
            send(message);
        }
    }

    @Override
    public void update(InputMessage message) {

    }

    @Override
    public void update(IdMessage message) {

    }

    @Override
    public void update(ErrorMessage message) {

    }

    @Override
    public void update(OutputMessage message) {

    }


    public void setup() throws IOException, ClassNotFoundException {
        String string;
        cli.printToConsole("welcome to the game. this is an alpha version so you will be connected to the loopback address");
        cli.printToConsole("type any key if you are ready to this experience:");
        string = cli.readFromInput();
        socket = new Socket("127.0.0.1", 1234);
        System.out.println("Connection established");
        socketIn = new ObjectInputStream(socket.getInputStream());
        socketOut = new ObjectOutputStream(socket.getOutputStream());

        this.ID = ((IdMessage) asyncReadFromSocket()).getID();
        if (ID == 0) {
            cli.printToConsole("Choose number of player");
            String numPlayers;
            numPlayers = cli.readFromInput();
            send(new ChooseNumberOfPlayer(numPlayers));
        } else {
            cli.printToConsole("waiting for the host...");
        }
        cli.printToConsole("sono qui");
        this.ready = ((OutputMessage) asyncReadFromSocket()).getString();
        cli.printToConsole("Choose your nickname");
        String nickname = cli.readFromInput();
        send(new Nickname(nickname, ID));
        cli.printToConsole(((OutputMessage)asyncReadFromSocket()).getString());
        cli.printToConsole(((OutputMessage)asyncReadFromSocket()).getString());
        cli.printToConsole(((OutputMessage)asyncReadFromSocket()).getString());

        while(isActive()){

        }

        //String inputObject = (String) socketIn.readObject();
        //mmv.sendnotify(inputObject);
        //String scannerata = scan.nextLine();
        //send(scannerata);
        //inputObject = (String) socketIn.readObject();
        //mmv.sendnotify(inputObject);
        //inputObject = (String) socketIn.readObject();
        //mmv.sendnotify(inputObject);
        //scannerata = scan.nextLine();
        //send(scannerata);
        //inputObject = (String) socketIn.readObject();
        //mmv.sendnotify(inputObject);
        //inputObject = (String) socketIn.readObject();
        //mmv.sendnotify(inputObject);
        //inputObject = (String) socketIn.readObject();
        //mmv.sendnotify(inputObject);


        socketIn.close();
        socketOut.close();
        socket.close();

    }

    public Object asyncReadFromSocket() {
        Object inputObject = null;
        try {
            inputObject = socketIn.readObject();

        }
        catch (Exception e) {
        }
        return inputObject;
    }

}


