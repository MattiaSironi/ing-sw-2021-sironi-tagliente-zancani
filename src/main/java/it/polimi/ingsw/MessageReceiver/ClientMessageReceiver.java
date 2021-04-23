package it.polimi.ingsw.MessageReceiver;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.*;
import org.apache.maven.model.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    private Boolean nameConfirmed = false;
    private List<Actions> actions;


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

    @Override
    public void update(ChooseNumberOfPlayer message) {

    }

    @Override
    public void update(PrintableMessage message) {

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
        System.out.println(this.ID);
        if (ID == 0) {
            setNumberOfPlayers();
        } else {
            cli.printToConsole("waiting for the host...");
        }
        ModelMultiplayerView.setSize(((ChooseNumberOfPlayer) asyncReadFromSocket()).getNumberOfPlayers());
        cli.printToConsole("the match is set to " + ModelMultiplayerView.getSize());
        while (!nameConfirmed) {
            cli.printToConsole("Choose your nickname");
            String nickname = cli.readFromInput();
            send(new Nickname(nickname, ID, false));
            if (((ErrorMessage) asyncReadFromSocket()).getString().equals("ko")) {
                cli.printToConsole("This nickname is already chosen");

            } else {
                send(new Nickname("", ID, true));
                nameConfirmed = true;
            }
        }


        cli.printToConsole(((OutputMessage) asyncReadFromSocket()).getString());
        if (ModelMultiplayerView.getSize() >= 2) {
            cli.printToConsole(((OutputMessage) asyncReadFromSocket()).getString());
        }
        if (ModelMultiplayerView.getSize() >= 3) {
            cli.printToConsole(((OutputMessage) asyncReadFromSocket()).getString());
        }
        if (ModelMultiplayerView.getSize() == 4) {
            cli.printToConsole(((OutputMessage) asyncReadFromSocket()).getString());
        }


        while (isActive()) {

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
        } catch (Exception e) {
        }
        return inputObject;
    }


    public void setNumberOfPlayers() {
        int numPlayers;
        Boolean valid = false;
        do {
            cli.printToConsole("Choose number of player");
            numPlayers = Integer.parseInt(cli.readFromInput());
            if (numPlayers > 1 && numPlayers <= 4) {
                valid = true;
                cli.printToConsole("Number of players set to " + numPlayers);
            } else {
                cli.printToConsole("Error! Number must be between 2 and 4");
            }
        } while (!valid);
        send(new ChooseNumberOfPlayer(numPlayers));
    }

    public void resetActions() {
        actions = new ArrayList<>();
        actions.add(Actions.M);
        actions.add(Actions.B);
        actions.add(Actions.A);
        actions.add(Actions.SM);
        actions.add(Actions.SF);
        actions.add(Actions.SD);
        actions.add(Actions.SP);
        actions.add(Actions.SL);
        actions.add(Actions.SR);
        actions.add(Actions.MR);
    }

    public void chooseAction() {
        resetActions();
        boolean isEndTurn = false;
        while (!isEndTurn) {
            String chosenAction;
            cli.printToConsole("Select one action");
            for (Actions a : actions) {
                cli.printToConsole("Type " + a.toString() + " to " + a.getString());
            }
            chosenAction = cli.readFromInput();
            Actions selectedAction = Actions.valueOf(chosenAction);
            switch (selectedAction) {
                case M -> actions.remove(selectedAction);
                case B -> actions.remove(selectedAction);
                case A -> actions.remove(selectedAction);
                case SM -> mmv.printMarket();
                //case SF ->;
                //case SD ->;
                //case SP ->;
                //case SL ->;
                //case SR ->;
                //case MR ->;
                //case END -> isEndTurn = true;
                default -> cli.printToConsole("Unvalid input");
            }
        }
    }
}




