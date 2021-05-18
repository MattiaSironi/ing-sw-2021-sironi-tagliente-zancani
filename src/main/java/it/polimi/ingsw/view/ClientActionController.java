package it.polimi.ingsw.view;

import it.polimi.ingsw.message.ActionMessages.*;
import it.polimi.ingsw.message.CommonMessages.*;
import it.polimi.ingsw.model.*;

import java.io.IOException;
import java.util.*;
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
    private ArrayList<Marble> resourcesList;



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


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setup() throws IOException, ClassNotFoundException {
        String string;
        cli.printToConsole("Welcome to the game. This is an alpha version so you will be connected to the loopback address");
        cli.printToConsole("Type any key if you are ready to this experience:");
        string = cli.readFromInput();
        serverConnection.run();

        while (isActive()) {

        }
    }




    public void setNumberOfPlayers() {
        String input;
        int numPlayers;
        Boolean valid = false;
        do {
            cli.printToConsole("Choose number of players:");
            input = cli.readFromInput().replaceAll("[^0-9]", "");
            if (input.equals("")) numPlayers= -1;
            else numPlayers = Integer.parseInt(input);
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

        boolean actionEnded = false;
        while (!actionEnded) {
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

                            actionEnded = goToMarket();
                            if (actionEnded) noMoreActions();

                            // do things
                        } else cli.printToConsole("You cannot do this move twice or more in a single turn!");
                    }

                    case B -> {
                        if (Actions.B.isEnable()) {
                            noMoreActions();
                            buyDevCard();
                            actionEnded = true;

                            // do things
                        } else cli.printToConsole("You cannot do this move twice or more in a single turn!");
                    }
                    case A -> {
                        if (Actions.A.isEnable()) {
                            noMoreActions();
                            activateProd();
                            actionEnded = true;
                        } else cli.printToConsole("You cannot do this move twice or more in a single turn!");
                    }
                    case SM -> mmv.printMarket();
                    case SF -> printFaithTrack();
                    case SD -> this.mmv.printDevMatrix();
                    case SP -> printProd();
                    case SL -> {
                        actionEnded = printLeaders();
                    }
                    case SR -> /*mmv.printShelves(0);*/ printShelves();
                    case MR -> {
                        manageResources();
                        actionEnded = true;
                    }
                    case END -> {
                        actionEnded = true;
                        resetEnable();
                        serverConnection.send(new EndTurnMessage(ID));
                    }
                    default -> cli.printToConsole("Invalid input");
                }
            } else
                cli.printToConsole("Invalid input");
        }
    }



    private void activateProd() {
        boolean stop = false;
        ArrayList<ResourceType> buy = new ArrayList<>();
        ArrayList<ResourceType> Str = new ArrayList<>();
        ArrayList<ResourceType> Ware = new ArrayList<>();

        cli.printToConsole("Choose the productions you want to activate in this turn:\nType:\n1 to activate the basic prodcution" +
                "\n2 to activate the leader production\n3 to activate the development card production\n0 to end the production phase");
        int prod = Integer.parseInt(cli.readFromInput());
        switch (prod) {
            case 1 -> {
                useBasicProduction();
            }
            case 2 -> {
                useLeaderProduction();
            }
            case 3 -> {
               useDevProduction();
            }
            case 0 -> {
                serverConnection.send(new ProductionMessage(null, null, null, null,null, ID, true));
            }
        }
    }
        //this.mmv.sendNotify(new ProductionMessage(Ware,Str,buy,ID));

    private void printShelves() {

        int chosenID = chooseID();
        mmv.printShelves(chosenID);
        mmv.printStrongbox(chosenID);
    }
    private void printFaithTrack() {
        int chosenID = chooseID();
        mmv.printFaithTrack(chosenID);
    }

    public int chooseID() {
        String input;
        int tempID = -1;
        cli.printToConsole("Choose the ID: ");
        boolean valid = false;
        while (!valid) {
            input = cli.readFromInput().replaceAll("[^0-9]", "");
            if (!input.equals("")) tempID = Integer.parseInt(input);
            if (mmv.getGame().getPlayerById(tempID) == null)
                cli.printToConsole("there is no player with this ID associated. try another ID");
            else valid = true;
        }
        return tempID;
    }

    private boolean goToMarket() {
        mmv.printMarket();
        int index = 0;

        boolean row = false;
        boolean valid = false;
        String input;
        String inputcleaned;
        while (!valid) {
            cli.printToConsole("Choose the row or column you prefer. you can choose between r1, r2, r3 and " +
                    " c1,c2,c3,c4. Type 'q' if you want to quit");
            input = cli.readFromInput();
            inputcleaned= input.replaceAll("[0-9]", " ");
            if (inputcleaned.equals("q")) return false;
            if (inputcleaned.equals("r ") || inputcleaned.equals("c ")) {
                row = input.charAt(0) == 'r';
                index = Integer.parseInt(input.replaceAll("[^0-9]", ""));
                if ((row && index >= 1 && index <= 3) || (!row && index >= 1 && index <= 4)) {
                    valid = true;
                } else cli.printToConsole("Invalid int, you selected " + index);
                } else cli.printToConsole("Invalid command");
            }
            serverConnection.send(new MarketMessage(row, index-1, ID));
            return true;// socket
//        this.mmv.sendNotify(new MarketMessage(row, index-1, ID)); //local
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
        }

    public void activateProduction(){
//        useBasicProduction();
    }

    public void buyDevCard() {
        this.mmv.printDevMatrix();
        while(!chooseCard());
    }

    public boolean chooseCard(){
        String input;
        int index;
        cli.printToConsole("Choose the Development Card you want to buy");
        index = Integer.parseInt(cli.readFromInput().replaceAll("[^0-9]", ""));
        if(!(index >= 0 && index <= 12)){
            cli.printToConsole("Invalid input");
            return false;
        }
        cli.printToConsole("This card has cost:" + mmv.getGame().getBoard().getMatrix().getDevDecks().get(index - 1).getCards().get(0).getCostRes()[0]
                + " " + ResourceType.COIN.printResourceColouredName() + " " + mmv.getGame().getBoard().getDevDecks().get(index - 1).getCards().get(0).getCostRes()[1]
                + " " + ResourceType.STONE.printResourceColouredName() + " " + mmv.getGame().getBoard().getDevDecks().get(index - 1).getCards().get(0).getCostRes()[2]
                + " " + ResourceType.SERVANT.printResourceColouredName() + " " + mmv.getGame().getBoard().getDevDecks().get(index - 1).getCards().get(0).getCostRes()[3]
                + " " + ResourceType.SHIELD.printResourceColouredName());
        cli.printToConsole("Continue? [y/n]");
        input = cli.readFromInput();
        if(input.equals("y")){
            serverConnection.send(new BuyDevCardMessage(index - 1, ID, mmv.getGame().getBoard().getDevDecks().get(index - 1).getCards().get(0), null, null, 0));
            return true;
        }
        else if(input.equals("n")){
            cli.printToConsole("Aborted");
            return false;
        }
        else{
            cli.printToConsole("Invalid input, try again");
            return false;
        }


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

    public void useBasicProduction(){
        ArrayList<ResourceType> resFromWarehouse = new ArrayList<>();
        ArrayList<ResourceType> resFromStrongbox = new ArrayList<>();
        ArrayList<ResourceType> resToBuy = new ArrayList<>();
        boolean valid = false;
        String input = "";
        int chosenRes;
        cli.printToConsole("Choose the first resource you want to use\n1 --> "
                + ResourceType.COIN.printResourceColouredName() + "\n2 --> "
                + ResourceType.STONE.printResourceColouredName() + "\n3 --> "
                + ResourceType.SERVANT.printResourceColouredName() + "\n4 --> "
                + ResourceType.SHIELD.printResourceColouredName());
        while(!valid) {
            input = cli.readFromInput();
            if (input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4")) {
                valid = true;
            }
            else {
                cli.printToConsole(input);
                cli.printToConsole("Invalid input, try again");
            }
        }
        valid = false;
        chosenRes = Integer.parseInt(input);
        cli.printToConsole("Where would you like to take it from? (WARE -> warehouse / STRONG -> strongbox");
        while(!valid) {
            input = cli.readFromInput();
            if (input.equals("WARE")) {
                switch (chosenRes) {
                    case 1 -> resFromWarehouse.add(ResourceType.COIN);
                    case 2 -> resFromWarehouse.add(ResourceType.STONE);
                    case 3 -> resFromWarehouse.add(ResourceType.SERVANT);
                    case 4 -> resFromWarehouse.add(ResourceType.SHIELD);
                }
                valid = true;
            }
            else if(input.equals("STRONG")){
                switch (chosenRes) {
                    case 1 -> resFromStrongbox.add(ResourceType.COIN);
                    case 2 -> resFromStrongbox.add(ResourceType.STONE);
                    case 3 -> resFromStrongbox.add(ResourceType.SERVANT);
                    case 4 -> resFromStrongbox.add(ResourceType.SHIELD);
                }
                valid = true;
            }
            else
                cli.printToConsole("Invalid input, try again");
        }
        valid = false;
        cli.printToConsole("Choose the second resource you want to use\n1 --> "
                + ResourceType.COIN.printResourceColouredName() + "\n2 --> "
                + ResourceType.STONE.printResourceColouredName() + "\n3 --> "
                + ResourceType.SERVANT.printResourceColouredName() + "\n4 --> "
                + ResourceType.SHIELD.printResourceColouredName());
        while(!valid) {
            input = cli.readFromInput();
            if (input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4")) {
                valid = true;
            }
            else {
                cli.printToConsole(input);
                cli.printToConsole("Invalid input, try again");
            }
        }
        valid = false;
        chosenRes = Integer.parseInt(input);
        cli.printToConsole("Where would you like to take it from? (WARE -> warehouse / STRONG -> strongbox");
        while(!valid) {
            input = cli.readFromInput();
            if (input.equals("WARE")) {
                switch (chosenRes) {
                    case 1 -> resFromWarehouse.add(ResourceType.COIN);
                    case 2 -> resFromWarehouse.add(ResourceType.STONE);
                    case 3 -> resFromWarehouse.add(ResourceType.SERVANT);
                    case 4 -> resFromWarehouse.add(ResourceType.SHIELD);
                }
                valid = true;
            }
            else if(input.equals("STRONG")){
                switch (chosenRes) {
                    case 1 -> resFromStrongbox.add(ResourceType.COIN);
                    case 2 -> resFromStrongbox.add(ResourceType.STONE);
                    case 3 -> resFromStrongbox.add(ResourceType.SERVANT);
                    case 4 -> resFromStrongbox.add(ResourceType.SHIELD);
                }
                valid = true;
            }
            else
                cli.printToConsole("Invalid input, try again");
        }
        valid = false;
        cli.printToConsole("Choose a new resource to produce\n1 --> "
                + ResourceType.COIN.printResourceColouredName() + "\n2 --> "
                + ResourceType.STONE.printResourceColouredName() + "\n3 --> "
                + ResourceType.SERVANT.printResourceColouredName() + "\n4 --> "
                + ResourceType.SHIELD.printResourceColouredName());
        while(!valid) {
            input = cli.readFromInput();
            if (input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4")) {
                valid = true;
            }
            else {
                cli.printToConsole(input);
                cli.printToConsole("Invalid input, try again");
            }
        }
        chosenRes = Integer.parseInt(input);
        if(chosenRes == 1) resToBuy.add(ResourceType.COIN);
        else if(chosenRes == 2) resToBuy.add(ResourceType.STONE);
        else if(chosenRes == 3) resToBuy.add(ResourceType.SERVANT);
        else if(chosenRes == 4) resToBuy.add(ResourceType.SHIELD);
        serverConnection.send(new ProductionMessage(resFromWarehouse, resFromStrongbox, resToBuy, null,null, ID, false));
    }



    public void useBasicProductionOLD(){
        boolean valid = false, validInput = false, validNum = false;
        ResourceType newRes;
        String input = null;
        ProductionMessage m;
        ArrayList<ResourceType> resToBuy = new ArrayList<>();
        ArrayList<ResourceType> resFromWarehouse = new ArrayList<>();
        ArrayList<ResourceType> resFromStrongbox = new ArrayList<>();
        cli.printToConsole("Choose a new resource to produce\n1 --> COIN\n2 --> STONE\n3 --> SERVANT\n4 --> SCHIELD\n(type 1, 2, 3, or 4)");
        while(!valid) {
        input = cli.readFromInput();
            if (input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4")) {
                valid = true;
            }
            else {
                cli.printToConsole(input);
                cli.printToConsole("Invalid input, try again");
            }
        }
        int chosenRes = Integer.parseInt(input);
        if(chosenRes == 1) newRes = ResourceType.COIN;
        else if(chosenRes == 2) newRes = ResourceType.STONE;
        else if(chosenRes == 3) newRes = ResourceType.SERVANT;
        else if(chosenRes == 4) newRes = ResourceType.SHIELD;

        valid = false;
        mmv.printShelves(ID);
        mmv.printStrongbox(ID);
        cli.printToConsole("Choose the first resource you want to use\n1 --> COIN\n2 --> STONE\n3 --> SERVANT\n4 --> SCHIELD\n(type 1, 2, 3, or 4)");

        while(!valid) {
            input = cli.readFromInput();
            if (input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4")) {
                valid = true;
            }
            else {
                cli.printToConsole(input);
                cli.printToConsole("Invalid input, try again");
            }
        }
        valid = false;
        chosenRes = Integer.parseInt(input);
        cli.printToConsole("Where would you like to take it from? (WARE -> warehouse / STRONG -> strongbox");
        while(!valid) {
            input = cli.readFromInput();
            if (input.equals("WARE")) {
                switch (chosenRes) {
                    case 1 -> resFromWarehouse.add(ResourceType.COIN);
                    case 2 -> resFromWarehouse.add(ResourceType.STONE);
                    case 3 -> resFromWarehouse.add(ResourceType.SERVANT);
                    case 4 -> resFromWarehouse.add(ResourceType.SHIELD);
                }
                valid = true;
            }
            else if(input.equals("STRONG")){
                switch (chosenRes) {
                    case 1 -> resFromStrongbox.add(ResourceType.COIN);
                    case 2 -> resFromStrongbox.add(ResourceType.STONE);
                    case 3 -> resFromStrongbox.add(ResourceType.SERVANT);
                    case 4 -> resFromStrongbox.add(ResourceType.SHIELD);
                }
                valid = true;
            }
            else
                cli.printToConsole("Invalid input, try again");
        }
        valid = false;
        cli.printToConsole("Choose the second resource you want to use\n1 --> COIN\n2 --> STONE\n3 --> SERVANT\n4 --> SCHIELD\n(type 1, 2, 3, or 4)");
        while(!valid) {
            input = cli.readFromInput();
            if (input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4")) {
                valid = true;
            }
            else {
                cli.printToConsole(input);
                cli.printToConsole("Invalid input, try again");
            }
        }
        valid = false;
        chosenRes = Integer.parseInt(input);
        cli.printToConsole("Where would you like to take it from? (WARE -> warehouse / STRONG -> strongbox");
        while(!valid) {
            input = cli.readFromInput();;
            if (input.equals("WARE")) {
                switch (chosenRes) {
                    case 1 -> resFromWarehouse.add(ResourceType.COIN);
                    case 2 -> resFromWarehouse.add(ResourceType.STONE);
                    case 3 -> resFromWarehouse.add(ResourceType.SERVANT);
                    case 4 -> resFromWarehouse.add(ResourceType.SHIELD);
                }
                valid = true;
            }
            else if(input.equals("STRONG")){
                switch (chosenRes) {
                    case 1 -> resFromStrongbox.add(ResourceType.COIN);
                    case 2 -> resFromStrongbox.add(ResourceType.STONE);
                    case 3 -> resFromStrongbox.add(ResourceType.SERVANT);
                    case 4 -> resFromStrongbox.add(ResourceType.SHIELD);
                }
                valid = true;
            }
            else
                cli.printToConsole("Invalid input, try again");
        }

//        m = new ProductionMessage(resFromWarehouse, resFromStrongbox,resToBuy, this.ID);
//        this.mmv.sendNotify(m);
//        return m;
    }

    public void useLeaderProduction(){
        ArrayList<ResourceType> resToBuy = new ArrayList<>();
        ArrayList<ResourceType> resFromWarehouse = new ArrayList<>();
        ArrayList<ResourceType> resFromStrongbox = new ArrayList<>();
        boolean valid = false;
        //this.mmv.getGame().getPlayerById(ID).getLeaderDeck();
     //   this.mmv.printActiveLeaders(ID);
        cli.printToConsole("Choose the leader you want to use");
        String input = cli.readFromInput();
        if (input.equals("1") || input.equals("2")){
            int index = Integer.parseInt(input);
            LeaderCard leaderCard = this.mmv.getGame().getPlayerById(ID).getPersonalBoard().getActiveLeader().getCards().get(index - 1);
            serverConnection.send(new ProductionMessage(null, null, null, leaderCard,null, ID, false));
        }
    }



    public void askForResource(ResourceType res) { //public for now, then private TODO

        cli.printToConsole("Do you want to keep it or not? [y/n]");
        boolean valid = false;
        while (!valid) {
            String input= cli.readFromInput();
            if (input.equalsIgnoreCase("y")) {
                whereToPut(res, false);
                valid=true;


            } else if (input.equalsIgnoreCase("n")) {
                discardRes(res);
                valid = true;
            } else cli.printToConsole("Invalid input! Retry!");
        }
    }

    public void chooseInitialResources(){
        ArrayList<ResourceType> possibleRes = new ArrayList<>();
        possibleRes.add(ResourceType.COIN);
        possibleRes.add(ResourceType.STONE);
        possibleRes.add(ResourceType.SERVANT);
        possibleRes.add(ResourceType.SHIELD);
        ResourceType selectedRes = null;
        boolean valid = false;
        String input;
        cli.printToConsole("Choose your starting Resource");
        selectedRes = getResourceType(possibleRes, false, null);
        whereToPut(selectedRes, true);
    }

//    private void localWhereToPut(ResourceType res) { //local
//        int s1 = 0;
//        boolean valid = false;
//        String s;
//        while (!valid) {
//            cli.printToConsole("Choose the shelf where to put your " + res.printResourceColouredName() + " [1,2,3]");
//            s = cli.readFromInput().replaceAll("[^0-9]", "");
//            if(!(s.equals(""))){
//                s1 = Integer.parseInt(s);
//            }
//            if (1 <= s1 && s1 <= 3) valid = true;
//            else cli.printToConsole("Invalid input! retry!");
//        }
//            mmv.sendNotify(new PlaceResourceMessage(res, s1-1, ID));
//    }

    private void discardRes(ResourceType res) {
        serverConnection.send(new PlaceResourceMessage(res, -1, ID, false, true));
        cli.printToConsole("Other players will receive one faith point.");
    }

    public void payDevCard(){
        boolean valid = false;
        ArrayList<ResourceType> resFromWarehouse = new ArrayList<>();
        ArrayList<ResourceType> resFromStrongbox = new ArrayList<>();
        DevCard d = mmv.getGame().getBoard().getMatrix().getChosenCard();
        if (d.getCostRes()[0] != 0) {
            cli.printToConsole("You have to pick " + d.getCostRes()[0] + " " + ResourceType.COIN.printResourceColouredName());
            cli.printToConsole("How many " + ResourceType.COIN.printResourceColouredName() + " would you like to pick from your warehouse?");
            String input = cli.readFromInput();
            int numCoinFromWarehouse = Integer.parseInt(input);
            for (int i = 0; i < numCoinFromWarehouse; i++) {
                resFromWarehouse.add(ResourceType.COIN);
            }
            cli.printToConsole("How many " + ResourceType.COIN.printResourceColouredName() + " would you like to pick from your strongbox?");
            input = cli.readFromInput();
            int numCoinFromStrongbox = Integer.parseInt(input);
            for (int i = 0; i < numCoinFromStrongbox; i++) {
                resFromStrongbox.add(ResourceType.COIN);
            }
        }
        if (d.getCostRes()[1] != 0) {
            cli.printToConsole("You have to pick " + d.getCostRes()[1] + " " + ResourceType.STONE.printResourceColouredName());
            cli.printToConsole("How many " + ResourceType.STONE.printResourceColouredName() + " would you like to pick from your warehouse?");
            String input = cli.readFromInput();
            int numStoneFromWarehouse = Integer.parseInt(input);
            for (int i = 0; i < numStoneFromWarehouse; i++) {
                resFromWarehouse.add(ResourceType.STONE);
            }
            cli.printToConsole("How many " + ResourceType.STONE.printResourceColouredName() + " would you like to pick from your strongbox?");
            input = cli.readFromInput();
            int numStoneFromStrongbox = Integer.parseInt(input);
            for (int i = 0; i < numStoneFromStrongbox; i++) {
                resFromStrongbox.add(ResourceType.STONE);
            }
        }
        if (d.getCostRes()[2] != 0) {
            cli.printToConsole("You have to pick " + d.getCostRes()[2] + " " + ResourceType.SERVANT.printResourceColouredName());
            cli.printToConsole("How many " + ResourceType.SERVANT.printResourceColouredName() + " would you like to pick from your warehouse?");
            String input = cli.readFromInput();
            int numServantFromWarehouse = Integer.parseInt(input);
            for (int i = 0; i < numServantFromWarehouse; i++) {
                resFromWarehouse.add(ResourceType.SERVANT);
            }
            cli.printToConsole("How many " + ResourceType.SERVANT.printResourceColouredName() + " would you like to pick from your strongbox?");
            input = cli.readFromInput();
            int numServantFromStrongbox = Integer.parseInt(input);
            for (int i = 0; i < numServantFromStrongbox; i++) {
                resFromStrongbox.add(ResourceType.SERVANT);
            }
        }
        if (d.getCostRes()[3] != 0) {
            cli.printToConsole("You have to pick " + d.getCostRes()[3] + " " + ResourceType.SHIELD.printResourceColouredName());
            cli.printToConsole("How many " + ResourceType.SHIELD.printResourceColouredName() + " would you like to pick from your warehouse?");
            String input = cli.readFromInput();
            int numShieldFromWarehouse = Integer.parseInt(input);
            for (int i = 0; i < numShieldFromWarehouse; i++) {
                resFromWarehouse.add(ResourceType.SHIELD);
            }
            cli.printToConsole("How many " + ResourceType.SHIELD.printResourceColouredName() + " would you like to pick from your strongbox?");
            input = cli.readFromInput();
            int numShieldFromStrongbox = Integer.parseInt(input);
            for (int i = 0; i < numShieldFromStrongbox; i++) {
                resFromStrongbox.add(ResourceType.SHIELD);
            }
        }

        serverConnection.send(new BuyDevCardMessage(-1, ID, mmv.getGame().getBoard().getMatrix().getChosenCard(), resFromWarehouse, resFromStrongbox, -1));
    }

    public void placeDevCard(){
        this.mmv.printProd(ID, ID);
        cli.printToConsole("Where would you like to place your new development card? (Choose slot 1, 2 or 3)");
        String input = cli.readFromInput();
        int chosenIndex = Integer.parseInt(input);
        serverConnection.send(new BuyDevCardMessage(-1, ID, null, null, null, chosenIndex));
    }



    public CLI getCli() {
        return cli;
    }

    private void whereToPut(ResourceType res, boolean initialPhase) {
        int s1 = 0;
        boolean valid = false;
        String s;
        while (!valid) {
            cli.printToConsole("Choose the shelf where to put your " + res.printResourceColouredName() + " [1,2,3,4,5]");
            s = cli.readFromInput().replaceAll("[^0-9]", "");
            if(!(s.equals(""))){
                s1 = Integer.parseInt(s);
            }
            if (1 <= s1 && s1 <= 5) valid = true;
            else cli.printToConsole("Invalid input! retry!");
        }
        serverConnection.send(new PlaceResourceMessage(res, s1-1, ID, initialPhase, false));
    }



    private void noMoreActions() {
        Actions.A.setEnable(false);
        Actions.B.setEnable(false);
        Actions.M.setEnable(false);
    }

    private void manageResources() {
        mmv.printShelves(ID);
        String input;
        int s1 = 0;
        int s2 = 0;
        boolean valid = false;
        while (!valid) {
            cli.printToConsole("Select the first shelf:");
            input = cli.readFromInput().replaceAll("[^0-9]", "");
            if (input.equals("")) s1 = -1;
            else s1=Integer.parseInt(input);

            cli.printToConsole("Select the second shelf:");
            input = cli.readFromInput().replaceAll("[^0-9]", "");
            if (input.equals("")) s2 = -1;
            else s2=Integer.parseInt(input);

            if (s1 != s2 && s1 >= 1 && s1 <= 5 && s2 >= 1 && s2 <= 5) {
                valid = true;
            } else cli.printToConsole("Invalid input! Retry!");

        }
        serverConnection.send(new ManageResourceMessage(s1-1, s2-1,  ID));
    }



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


    public boolean printLeaders(){
        boolean ok=true;
        int p = 0;
        p= this.chooseID();
//
//        cli.printToConsole("Choose the ID of the player whose leaders you want to see");
//        p = Integer.parseInt(cli.readFromInput());

        cli.printToConsole("Leaders in hand : ");
        mmv.getGame().getPlayerById(p).getLeaderDeck().print();

        cli.printToConsole("Active Leaders : ");
        mmv.getGame().getPlayerById(p).getPersonalBoard().getActiveLeader().print();

        if(p==this.ID) {
            boolean valid = false;
            String s;
            int idx;
            cli.printToConsole("Digit :\n'a' to activate one Leader, \n'd' to discard one Leader Card or \n'x' to return to the action menu");
            while (!valid) {
                String input = cli.readFromInput();
                if (input.equalsIgnoreCase("a")) {
                    if (this.mmv.getGame().getPlayerById(ID).getPersonalBoard().getActiveLeader().getCards().size() <= 2) {
                        cli.printToConsole("Select the leader you want to activate [1/2]");
                        idx = Integer.parseInt(cli.readFromInput());
                        if (idx == 1 || idx == 2) {
                            //   System.out.println("send");
                            serverConnection.send(new PlayLeaderMessage(ID, idx, true, mmv.getGame().getPlayerById(ID).getLeaderDeck().getCards().get(idx - 1)));
                            return true;
                        } else
                            cli.printToConsole("Invalid input");
                    } else
                        cli.printToConsole("you already have 2 active leaders");
                    valid = true;

                } else if (input.equalsIgnoreCase("d")) {
                    cli.printToConsole("Select the leader you want to discard [1/2]");
                    idx = Integer.parseInt(cli.readFromInput());
                    if ((idx == 1 || idx == 2)&&idx<=mmv.getGame().getPlayerById(ID).getLeaderDeck().getCards().size()) {
                        //   System.out.println("send");
                        serverConnection.send(new PlayLeaderMessage(ID, idx, false, this.mmv.getGame().getPlayerById(ID).getLeaderDeck().getCards().get(idx - 1)));
                        return true;
                    } else{
                        cli.printToConsole("Invalid input");
                        if(idx>mmv.getGame().getPlayerById(ID).getLeaderDeck().getCards().size())
                            return false;
                    }
                    valid = true;
                } else if (input.equalsIgnoreCase("x")) {
                    valid = true;
                } else cli.printToConsole("Invalid input! Retry!");
            }
            return false;
        }
        else
            return false;
    }


    public void useDevProduction() {
        boolean valid = false, validInput = false, validNum = false;
        int input = 0;
        ProductionMessage m;
        DevCard d;
        ArrayList<ResourceType> resFromWarehouse = new ArrayList<>();
        ArrayList<ResourceType> resFromStrongbox = new ArrayList<>();
        ArrayList<ResourceType> resToBuy = new ArrayList<>();

        cli.printToConsole("Choose the production card you want to activate : [1/2/3]");
        mmv.printProd(this.ID, this.ID);
        while (!valid) {
            input = Integer.parseInt(cli.readFromInput());
            if (!(input >= 1 && input <= mmv.getGame().getPlayerById(ID).getPersonalBoard().getCardSlot().size())) {
                cli.printToConsole("Invalid input, try again");
            } else
                valid = true;
        }
        d = mmv.getGame().getPlayerById(ID).getPersonalBoard().getCardSlot().get(input - 1).getCards().get(0);
        for (int i = 0; i < 4; i++) {
            int n = d.getInputRes()[i];
            while (n > 0) {
                valid = false;
                cli.printToConsole("You have to pay 1 " + FromIntToRes(i).toString() + "\nType 1 if you want to take it from your Warehouse\n" +
                        "Type 2 if you want to take it from your Strongbox");
                while (!valid) {
                    input = Integer.parseInt(cli.readFromInput());
                    if (!(input == 1 || input == 2)) {
                        cli.printToConsole("Invalid input, try again");
                    } else {
                        valid = true;
                        if (input == 1)
                            resFromWarehouse.add(FromIntToRes(i));
                        else
                            resFromStrongbox.add(FromIntToRes(i));
                    }
                }
                n--;
            }
        }
        for (int i = 0; i < 5; i++) {
            int n = d.getOutputRes()[i];
            while (n > 0) {
                resToBuy.add(FromIntToRes(i));
                n--;
            }
        }
        serverConnection.send(new ProductionMessage(resFromWarehouse, resFromStrongbox,resToBuy, null, d,this.ID, false));

    }

    public ResourceType FromIntToRes(int i){
        switch(i){
            case 0-> {return ResourceType.COIN;}
            case 1-> {return ResourceType.STONE;}
            case 2-> {return ResourceType.SERVANT;}
            case 3-> {return ResourceType.SHIELD;}
            case 4-> {return ResourceType.FAITH_POINT;}
            default -> {return ResourceType.EMPTY;}
        }
    }



    public void handleAction(Object o) {
        if(o instanceof ChooseNumberOfPlayer){
            ModelMultiplayerView.setSize(((ChooseNumberOfPlayer)o).getNumberOfPlayers());
            cli.printToConsole("The match is set to " + ModelMultiplayerView.getSize());
            nicknameSetUp();
        }
        else if(o instanceof Nickname){
            if(((Nickname)o).getValid()){
                cli.printToConsole("Your nickname is " + ((Nickname) o).getString());
            }
            else{
                cli.printToConsole("This nickname is already chosen\nTry again");
                serverConnection.send(new Nickname(cli.readFromInput(), ID, false));
            }
        }
        else if(o instanceof OutputMessage){
            cli.printToConsole(((OutputMessage) o).getString());
        }
        else if (o instanceof ObjectMessage)  {
            handleObject((ObjectMessage)o);
        }
    }





    private void handleObject(ObjectMessage message)  {
        if (message.getObjectID() == -1)  { //GAME
            this.mmv.setGame((Game) message.getObject());
        }
        else if (message.getObjectID()==0){
            this.mmv.getGame().getPlayerById(message.getID()).getPersonalBoard().setWarehouse((ShelfWarehouse) message.getObject());
        }
        else if (message.getObjectID()==1) {  //MARKET
            this.mmv.getGame().getBoard().setMarket((Market) message.getObject());
        }
        else if(message.getObjectID()==2) //MATRIX OF DEV CARD
            this.mmv.getGame().getBoard().setMatrix((DevelopmentCardMatrix) message.getObject());
        else if(message.getObjectID()==3) //STRONGBOX
            this.mmv.getGame().getPlayerById(message.getID()).getPersonalBoard().setStrongbox((Strongbox)message.getObject());
        else if(message.getObjectID()==4) //CARDSLOT
            this.mmv.getGame().getPlayerById(message.getID()).getPersonalBoard().setCardSlot((ArrayList<DevDeck>) message.getObject());
        else if(message.getObjectID()==5) //initialResource
        {
            this.mmv.getGame().getPlayerById(message.getID()).setStartResCount((int) message.getObject());
            if (message.getID() == ID)  {
                if (this.mmv.getGame().getPlayerById(ID).getStartResCount() > 0) chooseInitialResources();
            }
        }
        else if(message.getObjectID()==6) { //LEADER
            this.mmv.getGame().getPlayerById(message.getID()).getPersonalBoard().setActiveLeader((LeaderDeck) message.getObject());
        }
            else if(message.getObjectID() == 7){
            this.mmv.getGame().getPlayerById(message.getID()).getPersonalBoard().setLeaderChosen((ExtraProdLCard) message.getObject());
        }
        else if(message.getObjectID()==8){ //leaderDeck

            this.mmv.getGame().getPlayerById(message.getID()).setLeaderDeck((LeaderDeck) message.getObject());
        }
        else if(message.getObjectID() == 9) { //Communication
            this.mmv.getGame().setCommunication((Communication) message.getObject());
            handleCommunication(mmv.getGame().getCommunication());
        }
        else if (message.getObjectID()==10)  {
            this.mmv.getGame().setTurn((Turn) message.getObject());
            handleTurn((mmv.getGame().getTurn()));
        }
        else if(message.getObjectID() == 11) {
            if (message.getID() == 0)
                this.mmv.getGame().setFirstvatican(true);
            else if (message.getID() == 1)
                this.mmv.getGame().setSecondvatican(true);
            else
                this.mmv.getGame().setThirdvatican(true);
        }
        else if(message.getObjectID() == 12)
            this.mmv.getGame().getPlayerById(message.getID()).getPersonalBoard().setFaithTrack((FaithTrack) message.getObject());
    }

    private void handleCommunication(Communication communication) {

        if (communication.getAddresseeID() == ID) cli.printToConsole(communication.getCommunication().getString());
    }

    public void handleTurn(Turn turn) {
        if (turn.getPhase() == ActionPhase.GAME_OVER)  {
            if (turn.getPlayerPlayingID()==ID) cli.printToConsole("you win!");
            else cli.printToConsole("you lose!");
        }


         if (turn.getPlayerPlayingID() == ID) {
            if (turn.isError()) {
                cli.printToConsole(turn.getErrorType().getString());
            }
            switch (turn.getPhase()) {
                case WAITING_FOR_ACTION: {
                    chooseAction();
                    break;
                }
                case MARKET: {
                    mmv.printShelves(ID);
                    selectResourceFromHand();
                    break;
                }

                case A_PAYMENT: {
                    activateProd();
                    break;
                }

                case B_PAYMENT: {
                    payDevCard();
                    break;
                }
                case CHOOSE_SLOT: {
                    placeDevCard();
                    break;
                }
                case P_LEADER:{
                    printLeaders();
                    break;
                }
                case D_LEADER:{
                    printLeaders();
                    break;
                }
            }
        }
    }


    private void selectResourceFromHand() {
        ResourceType res = mmv.getGame().getBoard().getMarket().getHand().get(0).getRes();

        switch (res) {
            case COIN, STONE, SERVANT, SHIELD: {
                cli.printToConsole("you received a " + res.printResourceColouredName() + "!");
                askForResource(res);

                break;
            }
            case FAITH_POINT: {
                cli.printToConsole("you received a " + res.printResourceColouredName() + "!");
                serverConnection.send(new PlaceResourceMessage(ResourceType.FAITH_POINT, -1, ID, false, false));
                break;
            }
            default: {
                whiteMarbleCase();
                break;
            } //caso EMPTY, vari controlli!
        }

    }

    private void whiteMarbleCase() {
        ArrayList<ResourceType> possibleRes = new ArrayList<>();
        possibleRes.add(this.mmv.getGame().getPlayerById(ID).getWhiteConversion1());
        possibleRes.add(this.mmv.getGame().getPlayerById(ID).getWhiteConversion2());
        int count = (int) possibleRes.stream().filter(Objects::nonNull).count();

        switch (count) {
            case 0: {
                getCli().printToConsole("You did not receive any Resource Type from the white marble");
                serverConnection.send(new PlaceResourceMessage(ResourceType.EMPTY, -1, ID, false, false));
                break;
            }
            case 1: {

                getCli().printToConsole("due to your Leader Cards you received a " + possibleRes.get(0).printResourceColouredName()  + " from the white marble!");
                askForResource(possibleRes.get(0));
                break;


            }
            default: {
                getCli().printToConsole("due to your Leader Cards you can generate one of the following Resources from the white marble : "
                        + possibleRes.get(0).printResourceColouredName() +" or "
                        + possibleRes.get(1).printResourceColouredName() + ". "  );
                getCli().printToConsole("Select one of them: ");


                ResourceType selectedRes;

                selectedRes = getResourceType(possibleRes, false, null);
                askForResource(selectedRes);
                break;
            }






            }


        }

    private ResourceType getResourceType(ArrayList<ResourceType> possibleRes, boolean valid, ResourceType selectedRes) {
        String input;
        while(!valid) {
            input = cli.readFromInput();
            String finalInput = input;
            if (possibleRes.stream().anyMatch(v -> v.name().equals(finalInput))) {
                selectedRes = ResourceType.valueOf(finalInput);
                valid = true;
            }
            else cli.printToConsole("Invalid input, try again!");
        }
        return selectedRes;
    }


    private void nicknameSetUp() {
        cli.printToConsole("Choose your nickname:");
        String nickname = cli.readFromInput();
        serverConnection.send(new Nickname(nickname, ID, false));
    }
}




