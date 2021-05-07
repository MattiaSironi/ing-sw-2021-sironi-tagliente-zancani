package it.polimi.ingsw.view;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.message.ActionMessages.*;
import it.polimi.ingsw.message.CommonMessages.*;
import it.polimi.ingsw.model.*;
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


    public ClientActionController(CLI cli, ModelMultiplayerView mmv, SocketServerConnection socketServerConnection, int ID) {
        this.serverConnection = socketServerConnection;
        this.mmv = mmv;
        this.cli = cli;
        this.ID = ID;
        setActions();
    }

    public synchronized boolean isActive() {
        return active;
    }

    public synchronized void setActive(boolean active) {
        this.active = active;
    }


    public int getID() {
        return ID;
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
                    case SD -> this.mmv.printDevMatrix();
                    case SP -> printProd();
                    case SL -> printLeaders();
                    case SR -> /*mmv.printShelves(0);*/ printShelves();
                    case MR -> manageResources();
                    //case END -> resetEnable();
                    default -> cli.printToConsole("Invalid input");
                }
            } else
                cli.printToConsole("Invalid input");
        }
    }

    private void printShelves() {
        mmv.getGame().printPlayers(this.ID);
        int tempID=10;
        cli.printToConsole("Choose the ID of the player whom resources you want to see : ");
        boolean valid = false;
        while (!valid) {
            tempID = Integer.parseInt(cli.readFromInput());
            if (mmv.getGame().getPlayerById(tempID) == null) cli.printToConsole("there is no player with this ID associated. try another ID");
            else valid=true;
        }
        mmv.printShelves(tempID);
        mmv.printStrongbox(tempID);
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
        boolean buyFromWarehouse = false,
                buyFromStrongbox = false,
                validChoice = false,
                coinNumValid = false,
                stoneNumValid = false,
                servantNumValid = false,
                shieldNumValid = false;
        ArrayList<ResourceType> resFromWarehouse = new ArrayList<>();
        ArrayList<ResourceType> resFromStrongbox = new ArrayList<>();
        int index = 0;
        this.mmv.printDevMatrix();
        cli.printToConsole("Are you sure you want to go on? type 'quit' to return\n");
        String string = cli.readFromInput();
        if(string.equals("quit")) return;
        cli.printToConsole("Choose the Development Card you want to buy\n");
        while(!validChoice) {
            index = Integer.parseInt(cli.readFromInput());
            System.out.println("This card has cost:" + mmv.getGame().getBoard().getDevDecks().get(index - 1).getCards().get(0).getCostRes()[0]
                    + " " + ResourceType.COIN.printResourceColouredName() + " " + mmv.getGame().getBoard().getDevDecks().get(index - 1).getCards().get(0).getCostRes()[1]
                    + " " + ResourceType.STONE.printResourceColouredName() + " " + mmv.getGame().getBoard().getDevDecks().get(index - 1).getCards().get(0).getCostRes()[2]
                    + " " + ResourceType.SERVANT.printResourceColouredName() + " " + mmv.getGame().getBoard().getDevDecks().get(index - 1).getCards().get(0).getCostRes()[3]
                    + " " + ResourceType.SHIELD.printResourceColouredName());

            if (mmv.getGame().getPlayerById(ID).getPersonalBoard().totalPaymentChecker(mmv.getGame().getBoard().getDevDecks().get(index - 1).getCards().get(0).getCostRes())) {
                validChoice = true;
                for (DevDeck dd : mmv.getGame().getPlayerById(ID).getPersonalBoard().getCardSlot()) {
                    if (dd.getCards().size() == 0 && mmv.getGame().getBoard().getDevDecks().get(index - 1).getCards().get(0).getLevel() == 1)
                        break;
                    else if (dd.getCards().get(0).getLevel() == mmv.getGame().getBoard().getDevDecks().get(index - 1).getCards().get(0).getLevel() - 1)
                        break;
                    else {
                        validChoice = false;
                        cli.printToConsole("You can't buy this Development Card because you don't have an avaiable slot to place it");
                    }
                }
            }
            else System.out.println("You dont have enough resources to pay this card!\nChoose another Development Card");
        }
        DevCard d = mmv.getGame().getBoard().getDevDecks().get(index - 1).getCards().get(0);
        if(d.getCostRes()[0] != 0){
            while(!coinNumValid) {
                if (d.getCostRes()[0] == 1)
                    System.out.println("Required 1 " + ResourceType.COIN.printResourceColouredName());
                else
                    System.out.println("Required " + d.getCostRes()[0] + " " + ResourceType.COIN.printResourceColouredName() + "(s)");
                System.out.println("Choose how many " + ResourceType.COIN.printResourceColouredName() + " you want to take from the Warehouse");
                int numCoinFromWarehouse = Integer.parseInt(cli.readFromInput());
                while(!(mmv.getGame().getPlayerById(ID).getPersonalBoard().getWarehouse().canIPay(numCoinFromWarehouse, ResourceType.COIN))){
                    cli.printToConsole("You don't have " + numCoinFromWarehouse + " " + ResourceType.COIN.printResourceColouredName() + " in your warehouse\nChoose a proper number" );
                    numCoinFromWarehouse = Integer.parseInt(cli.readFromInput());
                }
                if(numCoinFromWarehouse != d.getCostRes()[0]) {
                    System.out.println("Choose how many " + ResourceType.COIN.printResourceColouredName() + "(s) you want to take from the Strongbox");
                    int numCoinFromStrongbox = Integer.parseInt(cli.readFromInput());
                    while(!(mmv.getGame().getPlayerById(ID).getPersonalBoard().getStrongbox().canIPay(numCoinFromWarehouse, ResourceType.COIN))){
                        cli.printToConsole("You don't have " + numCoinFromStrongbox + " " + ResourceType.COIN.printResourceColouredName() + " in your strongbox\nChoose a proper number" );
                        numCoinFromStrongbox = Integer.parseInt(cli.readFromInput());
                    }
                    if (numCoinFromStrongbox + numCoinFromWarehouse == d.getCostRes()[0])
                        coinNumValid = true;
                    else
                        cli.printToConsole(ResourceType.COIN.printResourceColouredName() + "(s) must be " + d.getCostRes()[0]);
                    if (coinNumValid) {
                        for (int num1 = 0; num1 < numCoinFromStrongbox; num1++) {
                            resFromStrongbox.add(ResourceType.COIN);
                        }
                    }
                }
                else coinNumValid = true;
                for (int num0 = 0; num0 < numCoinFromWarehouse; num0++) {
                    resFromWarehouse.add(ResourceType.COIN);
                }
            }
        }
        if(d.getCostRes()[1] != 0) {
            while (!stoneNumValid) {
                if (d.getCostRes()[1] == 1) {
                    System.out.println("Required 1 " + ResourceType.STONE.printResourceColouredName());
                } else {
                    System.out.println("Required " + d.getCostRes()[0] + " " +  ResourceType.STONE.printResourceColouredName() + "(s)");
                }
                System.out.println("Choose how many " + ResourceType.STONE.printResourceColouredName() + "(s) you want to take from the Warehouse");
                int numStoneFromWarehouse = Integer.parseInt(cli.readFromInput());
                while(!(mmv.getGame().getPlayerById(ID).getPersonalBoard().getWarehouse().canIPay(numStoneFromWarehouse, ResourceType.STONE))){
                    cli.printToConsole("You don't have " + numStoneFromWarehouse + " " + ResourceType.STONE.printResourceColouredName() + " in your warehouse\nChoose a proper number" );
                    numStoneFromWarehouse = Integer.parseInt(cli.readFromInput());
                }
                if(numStoneFromWarehouse != d.getCostRes()[1]) {
                    System.out.println("Choose how many " + ResourceType.STONE.printResourceColouredName() + "(s) you want to take from the Strongbox");
                    int numStoneFromStrongbox = Integer.parseInt(cli.readFromInput());
                    while(!(mmv.getGame().getPlayerById(ID).getPersonalBoard().getStrongbox().canIPay(numStoneFromWarehouse, ResourceType.STONE))){
                        cli.printToConsole("You don't have " + numStoneFromStrongbox + " " + ResourceType.STONE.printResourceColouredName() + " in your strongbox\nChoose a proper number" );
                        numStoneFromStrongbox = Integer.parseInt(cli.readFromInput());
                    }
                    if (numStoneFromWarehouse + numStoneFromStrongbox == d.getCostRes()[1])
                        stoneNumValid = true;
                    else
                        cli.printToConsole(ResourceType.STONE.printResourceColouredName() + "(s) must be " + d.getCostRes()[1]);
                    if(stoneNumValid) {
                        for (int num = 0; num < numStoneFromStrongbox; num++) {
                            resFromStrongbox.add(ResourceType.STONE);
                        }
                    }
                }
                else stoneNumValid = true;
                for (int num = 0; num < numStoneFromWarehouse; num++) {
                    resFromWarehouse.add(ResourceType.STONE);
                }
            }

        }
        if(d.getCostRes()[2] != 0) {
            while(!servantNumValid) {
                if (d.getCostRes()[2] == 1) {
                    System.out.println("Required 1" +  ResourceType.SERVANT.printResourceColouredName());
                } else {
                    System.out.println("Required " + d.getCostRes()[0] + " " + ResourceType.SERVANT.printResourceColouredName() + "(s)");
                }
                System.out.println("Choose how many " +  ResourceType.SERVANT.printResourceColouredName() + "(s) you want to take from the Warehouse");
                int numServantFromWarehouse = Integer.parseInt(cli.readFromInput());
                while(!(mmv.getGame().getPlayerById(ID).getPersonalBoard().getWarehouse().canIPay(numServantFromWarehouse, ResourceType.SERVANT))){
                    cli.printToConsole("You don't have " + numServantFromWarehouse + " " + ResourceType.SERVANT.printResourceColouredName() + " in your warehouse\nChoose a proper number" );
                    numServantFromWarehouse = Integer.parseInt(cli.readFromInput());
                }
                if(numServantFromWarehouse != d.getCostRes()[2]) {
                    System.out.println("Choose how many" + ResourceType.SERVANT.printResourceColouredName() + "(s) you want to take from the Strongbox");
                    int numServantFromStrongbox = Integer.parseInt(cli.readFromInput());
                    while(!(mmv.getGame().getPlayerById(ID).getPersonalBoard().getStrongbox().canIPay(numServantFromWarehouse, ResourceType.SERVANT))){
                        cli.printToConsole("You don't have " + numServantFromStrongbox + " " + ResourceType.SERVANT.printResourceColouredName() + " in your strongbox\nChoose a proper number" );
                        numServantFromStrongbox = Integer.parseInt(cli.readFromInput());
                    }
                    if (numServantFromStrongbox + numServantFromWarehouse == d.getCostRes()[2])
                        servantNumValid = true;
                    else
                        cli.printToConsole(ResourceType.SERVANT.printResourceColouredName() + "(s) must be " + d.getCostRes()[2]);
                    if(servantNumValid) {
                        for (int num = 0; num < numServantFromStrongbox; num++) {
                            resFromStrongbox.add(ResourceType.SERVANT);
                        }
                    }
                }
                else servantNumValid = true;
                for (int num = 0; num < numServantFromWarehouse; num++) {
                    resFromWarehouse.add(ResourceType.SERVANT);
                }
            }
        }
        if(d.getCostRes()[3] != 0) {
            while(!shieldNumValid) {
                if (d.getCostRes()[3] == 1) {
                    System.out.println("Required 1 " + ResourceType.SHIELD.printResourceColouredName());
                } else {
                    System.out.println("Required " + d.getCostRes()[3] + " " + ResourceType.SHIELD.printResourceColouredName() + "(s)");
                }
                System.out.println("Choose how many " + ResourceType.SHIELD.printResourceColouredName() + "(s) you want to take from the Warehouse");
                int numShieldFromWarehouse = Integer.parseInt(cli.readFromInput());
                while (!(mmv.getGame().getPlayerById(ID).getPersonalBoard().getWarehouse().canIPay(numShieldFromWarehouse, ResourceType.SHIELD))) {
                    cli.printToConsole("You don't have " + numShieldFromWarehouse + " " + ResourceType.SHIELD.printResourceColouredName() + " in your warehouse\nChoose a proper number");
                    numShieldFromWarehouse = Integer.parseInt(cli.readFromInput());
                }
                if (numShieldFromWarehouse != d.getCostRes()[3]) {
                    System.out.println("Choose how many " + ResourceType.SHIELD.printResourceColouredName() + "(s) you want to take from the Strongbox");
                    int numShieldFromStrongbox = Integer.parseInt(cli.readFromInput());
                    while (!(mmv.getGame().getPlayerById(ID).getPersonalBoard().getStrongbox().canIPay(numShieldFromWarehouse, ResourceType.SHIELD))) {
                        cli.printToConsole("You don't have " + numShieldFromStrongbox + " " + ResourceType.SHIELD.printResourceColouredName() + " in your strongbox\nChoose a proper number");
                        numShieldFromStrongbox = Integer.parseInt(cli.readFromInput());
                    }
                    if (numShieldFromStrongbox + numShieldFromWarehouse == d.getCostRes()[3])
                        shieldNumValid = true;
                    else
                        cli.printToConsole(ResourceType.SHIELD.printResourceColouredName() + "(s) must be " + d.getCostRes()[3]);
                    if(shieldNumValid) {
                        for (int num = 0; num < numShieldFromStrongbox; num++) {
                            resFromStrongbox.add(ResourceType.SHIELD);
                        }
                    }
                }
                else shieldNumValid = true;
                for (int num = 0; num < numShieldFromWarehouse; num++) {
                    resFromWarehouse.add(ResourceType.SHIELD);
                }


            }

        }
        cli.printToConsole("Place your new Development Card. Select the number of the slot (1, 2 or 3)");
        int slot = Integer.parseInt(cli.readFromInput());
        while(slot < 1 || slot > 3){
            cli.printToConsole("Invalid input! Choose another slot");
            slot = Integer.parseInt(cli.readFromInput());
        }
        this.mmv.sendNotify(new BuyDevCardMessage(index, ID, d, resFromWarehouse, resFromStrongbox, slot - 1));

    }


    public void PlayLeader (){
        boolean correctInput = false;
        int idx;
        String input;
        while (!correctInput) {
            cli.printToConsole("These are your available leaders");
            this.printLeaders();
            }

            cli.printToConsole("Choose the Leader you want to activate and select its index");
            idx = Integer.parseInt(cli.readFromInput());

            if (idx>0 && idx<=mmv.getGame().getPlayerById(ID).getLeaderDeck().getSize()) {
                    correctInput = true;
//                    mmv.sendNotify(new PlayLeaderMessage(ID,idx));
                } else cli.printToConsole("Invalid int, you selected " + idx);
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

    public void printProd(){
        cli.printToConsole("Choose the player whose you would like to see the production");
        int chosenID = Integer.parseInt(cli.readFromInput());
        mmv.printProd(chosenID, this.ID);
    }


    public void printLeaders(){
        //inizializzazione per testare
//        LeaderCard l1 = new DiscountLCard(1,3,CardColor.BLUE,CardColor.YELLOW,ResourceType.COIN);
//        LeaderCard l2 = new ExtraDepotLCard(2,2,ResourceType.SERVANT,ResourceType.SHIELD);
//        LeaderCard l3 = new ExtraProdLCard(3,3,CardColor.GREEN,ResourceType.STONE);
//        LeaderCard l4 = new WhiteTrayLCard(4,4,ResourceType.COIN,CardColor.YELLOW,CardColor.BLUE);
//        ArrayList<LeaderCard> vett = new ArrayList<LeaderCard>();
//        vett.add(0,l1);
//        vett.add(1,l2);
//        LeaderDeck deck = new LeaderDeck(1,1,vett);
//        mmv.getGame().getPlayerById(0).setLeaderDeck(deck);

        int i=1;
        for(LeaderCard c : mmv.getGame().getPlayerById(this.ID).getLeaderDeck().getCards()){
            cli.printToConsole("Leader " + i + " : ");
            switch (c.getType()){
                case 1 -> { ((DiscountLCard) c).print();}
                case 2-> { ((ExtraDepotLCard) c).print();}
                case 3-> { ((ExtraProdLCard) c).print();}
                case 4-> { ((WhiteTrayLCard) c).print();}
            }
            i++;
            cli.printToConsole("");
        }
    }
}




