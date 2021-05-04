package it.polimi.ingsw.view;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.message.ActionMessages.*;
import it.polimi.ingsw.message.CommonMessages.*;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Mattia Sironi
 */
public class ClientActionController {
    private ModelMultiplayerView mmv;
    private SocketServerConnection serverConnection;
    private boolean active = true;
    private int ID;
    private CLI cli;
    private String ready;
    private Boolean nameConfirmed = false;
    private List<Actions> actions;
    private boolean actionDone = false;


    public ClientActionController(CLI cli, ModelMultiplayerView mmv, SocketServerConnection socketServerConnection) {
        this.serverConnection = socketServerConnection;
        this.mmv = mmv;
        this.cli = cli;
        setActions();
    }

    public synchronized boolean isActive() {
        return active;
    }

    public synchronized void setActive(boolean active) {
        this.active = active;
    }



    public void setup() throws IOException, ClassNotFoundException {
        String string;
        cli.printToConsole("Welcome to the game. This is an alpha version so you will be connected to the loopback address");
        cli.printToConsole("Type any key if you are ready to this experience:");
        string = cli.readFromInput();
        serverConnection.run();
        this.ID = ((IdMessage) serverConnection.receive()).getID();
        System.out.println(this.ID);
        if (ID == 0) {
            setNumberOfPlayers();
        } else {
            cli.printToConsole("Waiting for the host...");
        }
        ModelMultiplayerView.setSize(((ChooseNumberOfPlayer)serverConnection.receive()).getNumberOfPlayers());
        cli.printToConsole("The match is set to " + ModelMultiplayerView.getSize());
        while (!nameConfirmed) {
            cli.printToConsole("Choose your nickname");
            String nickname = cli.readFromInput();
            serverConnection.send(new Nickname(nickname, ID, false));
            if (((ErrorMessage) serverConnection.receive()).getString().equals("ko")) {
                cli.printToConsole("This nickname is already chosen");

            } else {
                serverConnection.send(new Nickname("", ID, true));
                nameConfirmed = true;
            }
        }


        cli.printToConsole(((OutputMessage) serverConnection.receive()).getString());
        if (ModelMultiplayerView.getSize() >= 2) {
            cli.printToConsole(((OutputMessage) serverConnection.receive()).getString());
        }
        if (ModelMultiplayerView.getSize() >= 3) {
            cli.printToConsole(((OutputMessage) serverConnection.receive()).getString());
        }
        if (ModelMultiplayerView.getSize() == 4) {
            cli.printToConsole(((OutputMessage) serverConnection.receive()).getString());
        }

        this.serverConnection.getPinger().start();

        while (isActive()) {

        }
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
        serverConnection.send(new ChooseNumberOfPlayer(numPlayers));
    }

    public void setActions() {
        actions = new ArrayList<Actions>(Arrays.asList(Actions.values()));
    }

    public void chooseAction() {
        resetEnable();
        boolean isEndTurn = false;
        while (!isEndTurn) {
            String chosenAction;
            cli.printToConsole("Select one action");
            for (Actions a : actions) {
                if (a.isEnable())
                    cli.printToConsole("Type " + a.toString() + " to " + a.getString());
            }
            chosenAction = cli.readFromInput();
            if (Stream.of(Actions.values()).anyMatch(v -> v.name().equals(chosenAction))) {
                Actions selectedAction = Actions.valueOf(chosenAction);
                switch (selectedAction) {
                    case M -> {
                        if (Actions.M.isEnable()) {
//                            noMoreActions(); just for testing multiple shifts
                            goToMarket();

                            // do things
                        } else cli.printToConsole("You cannot do this move twice or more in a single turn!");
                    }

                    case B -> {
                        if (Actions.B.isEnable()) {
//                            noMoreActions();
                            buyDevCard();

                            // do things
                        } else cli.printToConsole("You cannot do this move twice or more in a single turn!");
                    }
                    case A -> {
                        if (Actions.A.isEnable()) {
                            noMoreActions();
                            // do things
                        } else cli.printToConsole("You cannot do this move twice or more in a single turn!");
                    }
                    case SM -> mmv.printMarket();
                    //case SF ->;
                    //case SD ->;
                    //case SP ->;
                    //case SL ->;
                    case SR -> mmv.printShelves(0);
                    case MR -> manageResources();
                    //case END -> resetEnable();
                    default -> cli.printToConsole("Invalid input");
                }
            } else
                cli.printToConsole("Invalid input");
        }
    }

    private void goToMarket() {
        mmv.printMarket();
        int index = 0;

        boolean row = false;
        boolean valid = false;
        String input;
        String inputcleaned;
        while (!valid) {
            cli.printToConsole("Choose the row or column you prefer. you can choose between r1, r2, r3 and " +
                    " c1,c2,c3,c4");
            input = cli.readFromInput();
            inputcleaned= input.replaceAll("[0-9]", " ");
            if (inputcleaned.equals("r ") || inputcleaned.equals("c ")) {
                row = input.charAt(0) == 'r';
                index = Integer.parseInt(input.replaceAll("[^0-9]", ""));

                if ((row && index >= 1 && index <= 3) || (!row && index >= 1 && index <= 4)) {
                    valid = true;
                } else cli.printToConsole("Invalid int, you selected " + index);
            } else cli.printToConsole("Invalid command");
        }
//        serverConnection.send(new MarketMessage(row, index-1, ID)); // socket
        this.mmv.sendNotify(new MarketMessage(row, index-1, ID)); //local
//        ResourceListMessage resourceList= (ResourceListMessage) serverConnection.receive(); //socket  TODO
//        for (Marble m : resourceList.getMarbles() )  {
//
//            switch (m.getRes())  {
//                case COIN,STONE,SERVANT,SHIELD: askForResource(m.getRes());
//                case FAITH_POINT: cli.printToConsole("you got a faith point!");
//                default: //caso EMPTY, vari controlli!
//            }
//
//        }
//        serverConnection.send(new ErrorMessage("resources finished", this.ID));
//
//
//
//
    }

    public void buyDevCard(){
        this.mmv.printDevMatrix();
    }


    public void PlayLeader (){
        boolean correctInput = false;
        int idx;
        String input;
        while (!correctInput) {
            cli.printToConsole("Choose the Leader you want to activate and select its index");
            idx = Integer.parseInt(cli.readFromInput());

            if (idx>0 && idx<=mmv.getGame().getPlayerById(ID).getLeaderDeck().getSize()) {
                    correctInput = true;
//                    mmv.sendNotify(new PlayLeaderMessage(ID,idx));
                } else cli.printToConsole("Invalid int, you selected " + idx);
            }
        }

    public void askForResource(ResourceType res) { //public for now, then private TODO

        cli.printToConsole("Do you want to keep it or not? [y/n]");
        boolean valid = false;
        while (!valid) {
            String input= cli.readFromInput();
            if (input.equalsIgnoreCase("y")) {
                localWhereToPut(res);
                valid=true;
//                if (whereToPut(res)) valid = true;  //socket
//                else cli.printToConsole("discard it or choose another shelf.");

            } else if (input.equalsIgnoreCase("n")) {
                discardRes();
                valid = true;
            } else cli.printToConsole("Invalid input! Retry!");
        }


    }

    private void localWhereToPut(ResourceType res) { //local
        int s1 = 0;
        boolean valid = false;
        String s;
        while (!valid) {
            cli.printToConsole("Choose the shelf where to put your " + res.printResourceColouredName() + " [1,2,3]");
            s1 = Integer.parseInt(cli.readFromInput());
            if (1 <= s1 && s1 <= 3) valid = true;
            else cli.printToConsole("Invalid input! retry!");
        }
            mmv.sendNotify(new PlaceResourceMessage(res, s1-1, ID));

    }

    private void discardRes() {
        cli.printToConsole("Other players received one faith point.");
//        serverConnection.send(new ErrorMessage("discard" , this.ID)); //socket
        mmv.sendNotify(new ErrorMessage("discard", ID));
    }

    public CLI getCli() {
        return cli;
    }

    private boolean whereToPut(ResourceType res) {
        int s1 = 0;
        boolean valid = false;
        String s;
        while (!valid) {
            cli.printToConsole("Choose the shelf where to put your " + res.printResourceColouredName() + " [1,2,3]");
            s1 = Integer.parseInt(cli.readFromInput());
            if (1 <= s1 && s1 <= 3) valid = true;
            else cli.printToConsole("Invalid input! retry!");
        }

        serverConnection.send(new PlaceResourceMessage(res, s1, ID));
        s = ((ErrorMessage) serverConnection.receive()).getString();
        if (!s.equals("ok")) {
            cli.printToConsole(s);
            return false;
        } else return true;
    }



    private void noMoreActions() {
        Actions.A.setEnable(false);
        Actions.B.setEnable(false);
        Actions.M.setEnable(false);
    }

    private void manageResources() {
        int s1 = 0;
        int s2 = 0;
        boolean valid = false;
        while (!valid) {
            cli.printToConsole("Select the first shelf:");
            s1 = Integer.parseInt(cli.readFromInput());
            cli.printToConsole("Select the second shelf:");
            s2 = Integer.parseInt(cli.readFromInput());
            if (s1 != s2 && s1 >= 1 && s1 <= 3 && s2 >= 1 && s2 <= 3) {
                valid = true;
            } else cli.printToConsole("Invalid input! Retry!");

        }
        this.mmv.sendNotify(new ManageResourceMessage(s1-1, s2-1,  0));
    }

    //metodo per il gotoMarket : seleziona la riga del mercato e aspetta le risorse per decidere cosa farci

    //showDevToBuy : fa vedere le DevCard che puoi comprare e
    // fa selezionare all'utente colore e livello della carta scelta
    //controlla che la carta possa essere pagata e come

    //ActivateProduction : mostra produzioni possibili
    //fa scegliere quale produzione attivare
    //fa scegliere le risorse da usare e cosa ottenere eventualmente

    private void resetEnable() {
        for (Actions a : actions) {
            a.setEnable(true);
        }
    }
}




