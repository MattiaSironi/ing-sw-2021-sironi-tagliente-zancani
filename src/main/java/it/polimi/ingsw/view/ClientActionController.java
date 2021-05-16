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
        int numPlayers;
        Boolean valid = false;
        do {
            cli.printToConsole("Choose number of players:");
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
                            noMoreActions();
                            goToMarket();
                            actionEnded = true;

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
                        } else cli.printToConsole("You cannot do this move twice or more in a single turn!");
                    }
                    case SM -> mmv.printMarket();
                    case SF -> mmv.printFaithTrack(ID);
                    case SD -> this.mmv.printDevMatrix();
                    case SP -> printProd();
                    case SL -> printLeaders();
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

        while(!stop) {
            cli.printToConsole("Choose the productions you want to activate in this turn:\nType:\n1 to activate the basic prodcution" +
                    "\n2 to activate the leader production\n3 to activate the development card production\n0 to end the production phase");
            int prod = Integer.parseInt(cli.readFromInput());
            switch (prod) {
                case 1 -> {
                    ProductionMessage p1 = useBasicProduction();
                    Str.addAll(p1.getResFromStrongbox());
                    Ware.addAll(p1.getResFromWarehouse());
                }
                case 2 -> {
                    ProductionMessage p2 = useLeaderProduction();
                }
                case 3 -> {
                    ProductionMessage p3 = useDevProduction();
                }
                case 0-> stop = true;
            }
        }
        this.mmv.sendNotify(new ProductionMessage(Ware,Str,buy,ID));
    }

    private void printShelves() {

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
            serverConnection.send(new MarketMessage(row, index-1, ID)); // socket
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
        cli.printToConsole("This card has cost:" + mmv.getGame().getBoard().getDevDecks().get(index - 1).getCards().get(0).getCostRes()[0]
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





//        public void buyDevCard(){
//            boolean buyFromWarehouse = false,
//                    buyFromStrongbox = false,
//                    validChoice = false,
//                    coinNumValid = false,
//                    stoneNumValid = false,
//                    servantNumValid = false,
//                    shieldNumValid = false;
//            ArrayList<ResourceType> resFromWarehouse = new ArrayList<>();
//            ArrayList<ResourceType> resFromStrongbox = new ArrayList<>();
//            int index = 0;
//            this.mmv.printDevMatrix();
//            cli.printToConsole("Are you sure you want to go on? type 'quit' to return\n");
//            String string = cli.readFromInput();
//            if(string.equals("quit")) return;
//            cli.printToConsole("Choose the Development Card you want to buy\n");
//            while(!validChoice) {
//                index = Integer.parseInt(cli.readFromInput());
//                cli.printToConsole("This card has cost:" + mmv.getGame().getBoard().getDevDecks().get(index - 1).getCards().get(0).getCostRes()[0]
//                        + " " + ResourceType.COIN.printResourceColouredName() + " " + mmv.getGame().getBoard().getDevDecks().get(index - 1).getCards().get(0).getCostRes()[1]
//                        + " " + ResourceType.STONE.printResourceColouredName() + " " + mmv.getGame().getBoard().getDevDecks().get(index - 1).getCards().get(0).getCostRes()[2]
//                        + " " + ResourceType.SERVANT.printResourceColouredName() + " " + mmv.getGame().getBoard().getDevDecks().get(index - 1).getCards().get(0).getCostRes()[3]
//                        + " " + ResourceType.SHIELD.printResourceColouredName());
//            }
//            DevCard d = mmv.getGame().getBoard().getDevDecks().get(index - 1).getCards().get(0);
//            if(d.getCostRes()[0] != 0){
//                while(!coinNumValid) {
//                    if (d.getCostRes()[0] == 1)
//                        System.out.println("Required 1 " + ResourceType.COIN.printResourceColouredName());
//                    else
//                        System.out.println("Required " + d.getCostRes()[0] + " " + ResourceType.COIN.printResourceColouredName() + "(s)");
//                    System.out.println("Choose how many " + ResourceType.COIN.printResourceColouredName() + " you want to take from the Warehouse");
//                    int numCoinFromWarehouse = Integer.parseInt(cli.readFromInput());
//
//                    if(numCoinFromWarehouse != d.getCostRes()[0]) {
//                        System.out.println("Choose how many " + ResourceType.COIN.printResourceColouredName() + "(s) you want to take from the Strongbox");
//                        int numCoinFromStrongbox = Integer.parseInt(cli.readFromInput());
//
//                        if (numCoinFromStrongbox + numCoinFromWarehouse == d.getCostRes()[0])
//                            coinNumValid = true;
//                        else
//                            cli.printToConsole(ResourceType.COIN.printResourceColouredName() + "(s) must be " + d.getCostRes()[0]);
//                    if (coinNumValid) {
//                        for (int num1 = 0; num1 < numCoinFromStrongbox; num1++) {
//                            resFromStrongbox.add(ResourceType.COIN);
//                        }
//                    }
//                }
//                else coinNumValid = true;
//                for (int num0 = 0; num0 < numCoinFromWarehouse; num0++) {
//                    resFromWarehouse.add(ResourceType.COIN);
//                }
//            }
//        }
//        if(d.getCostRes()[1] != 0) {
//            while (!stoneNumValid) {
//                if (d.getCostRes()[1] == 1) {
//                    System.out.println("Required 1 " + ResourceType.STONE.printResourceColouredName());
//                } else {
//                    System.out.println("Required " + d.getCostRes()[0] + " " +  ResourceType.STONE.printResourceColouredName() + "(s)");
//                }
//                System.out.println("Choose how many " + ResourceType.STONE.printResourceColouredName() + "(s) you want to take from the Warehouse");
//                int numStoneFromWarehouse = Integer.parseInt(cli.readFromInput());
//
//                if(numStoneFromWarehouse != d.getCostRes()[1]) {
//                    System.out.println("Choose how many " + ResourceType.STONE.printResourceColouredName() + "(s) you want to take from the Strongbox");
//                    int numStoneFromStrongbox = Integer.parseInt(cli.readFromInput());
//                    if (numStoneFromWarehouse + numStoneFromStrongbox == d.getCostRes()[1])
//                        stoneNumValid = true;
//                    else
//                        cli.printToConsole(ResourceType.STONE.printResourceColouredName() + "(s) must be " + d.getCostRes()[1]);
//                    if(stoneNumValid) {
//                        for (int num = 0; num < numStoneFromStrongbox; num++) {
//                            resFromStrongbox.add(ResourceType.STONE);
//                        }
//                    }
//                }
//                else stoneNumValid = true;
//                for (int num = 0; num < numStoneFromWarehouse; num++) {
//                    resFromWarehouse.add(ResourceType.STONE);
//                }
//            }
//
//        }
//        if(d.getCostRes()[2] != 0) {
//            while(!servantNumValid) {
//                if (d.getCostRes()[2] == 1) {
//                    System.out.println("Required 1" +  ResourceType.SERVANT.printResourceColouredName());
//                } else {
//                    System.out.println("Required " + d.getCostRes()[0] + " " + ResourceType.SERVANT.printResourceColouredName() + "(s)");
//                }
//                System.out.println("Choose how many " +  ResourceType.SERVANT.printResourceColouredName() + "(s) you want to take from the Warehouse");
//                int numServantFromWarehouse = Integer.parseInt(cli.readFromInput());
//                if(numServantFromWarehouse != d.getCostRes()[2]) {
//                    System.out.println("Choose how many" + ResourceType.SERVANT.printResourceColouredName() + "(s) you want to take from the Strongbox");
//                    int numServantFromStrongbox = Integer.parseInt(cli.readFromInput());
//                    if (numServantFromStrongbox + numServantFromWarehouse == d.getCostRes()[2])
//                        servantNumValid = true;
//                    else
//                        cli.printToConsole(ResourceType.SERVANT.printResourceColouredName() + "(s) must be " + d.getCostRes()[2]);
//                    if(servantNumValid) {
//                        for (int num = 0; num < numServantFromStrongbox; num++) {
//                            resFromStrongbox.add(ResourceType.SERVANT);
//                        }
//                    }
//                }
//                else servantNumValid = true;
//                for (int num = 0; num < numServantFromWarehouse; num++) {
//                    resFromWarehouse.add(ResourceType.SERVANT);
//                }
//            }
//        }
//        if(d.getCostRes()[3] != 0) {
//            while(!shieldNumValid) {
//                if (d.getCostRes()[3] == 1) {
//                    System.out.println("Required 1 " + ResourceType.SHIELD.printResourceColouredName());
//                } else {
//                    System.out.println("Required " + d.getCostRes()[3] + " " + ResourceType.SHIELD.printResourceColouredName() + "(s)");
//                }
//                System.out.println("Choose how many " + ResourceType.SHIELD.printResourceColouredName() + "(s) you want to take from the Warehouse");
//                int numShieldFromWarehouse = Integer.parseInt(cli.readFromInput());
//                while (!(mmv.getGame().getPlayerById(ID).getPersonalBoard().getWarehouse().canIPay(numShieldFromWarehouse, ResourceType.SHIELD))) {
//                    cli.printToConsole("You don't have " + numShieldFromWarehouse + " " + ResourceType.SHIELD.printResourceColouredName() + " in your warehouse\nChoose a proper number");
//                    numShieldFromWarehouse = Integer.parseInt(cli.readFromInput());
//                }
//                if (numShieldFromWarehouse != d.getCostRes()[3]) {
//                    System.out.println("Choose how many " + ResourceType.SHIELD.printResourceColouredName() + "(s) you want to take from the Strongbox");
//                    int numShieldFromStrongbox = Integer.parseInt(cli.readFromInput());
//                    while (!(mmv.getGame().getPlayerById(ID).getPersonalBoard().getStrongbox().canIPay(numShieldFromStrongbox, ResourceType.SHIELD))) {
//                        cli.printToConsole("You don't have " + numShieldFromStrongbox + " " + ResourceType.SHIELD.printResourceColouredName() + " in your strongbox\nChoose a proper number");
//                        numShieldFromStrongbox = Integer.parseInt(cli.readFromInput());
//                    }
//                    if (numShieldFromStrongbox + numShieldFromWarehouse == d.getCostRes()[3])
//                        shieldNumValid = true;
//                    else
//                        cli.printToConsole(ResourceType.SHIELD.printResourceColouredName() + "(s) must be " + d.getCostRes()[3]);
//                    if(shieldNumValid) {
//                        for (int num = 0; num < numShieldFromStrongbox; num++) {
//                            resFromStrongbox.add(ResourceType.SHIELD);
//                        }
//                    }
//                }
//                else shieldNumValid = true;
//                for (int num = 0; num < numShieldFromWarehouse; num++) {
//                    resFromWarehouse.add(ResourceType.SHIELD);
//                }
//
//
//            }
//
//        }
//        cli.printToConsole("Place your new Development Card. Select the number of the slot (1, 2 or 3)");
//        int slot = Integer.parseInt(cli.readFromInput());
//        while(slot < 1 || slot > 3){
//            cli.printToConsole("Invalid input! Choose another slot");
//            slot = Integer.parseInt(cli.readFromInput());
//        }
//        this.mmv.sendNotify(new BuyDevCardMessage(index, ID, d, resFromWarehouse, resFromStrongbox, slot - 1));
//
//    }

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

    public ProductionMessage useBasicProduction(){
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
//            switch(chosenRes){
//                case 1 -> newRes = ResourceType.COIN;
//                case 2 -> newRes = ResourceType.STONE;
//                case 3 -> newRes = ResourceType.SERVANT;
//                case 4 -> newRes = ResourceType.SHIELD;
//                default -> cli.printToConsole("Invalid input, try again");
//            }
        valid = true;
        if(chosenRes == 1) newRes = ResourceType.COIN;
        else if(chosenRes == 2) newRes = ResourceType.STONE;
        else if(chosenRes == 3) newRes = ResourceType.SERVANT;
        else if(chosenRes == 4) newRes = ResourceType.SHIELD;
        else valid = false;

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

        m = new ProductionMessage(resFromWarehouse, resFromStrongbox,resToBuy, this.ID);
        this.mmv.sendNotify(m);
        return m;
    }

    public ProductionMessage useLeaderProduction(){
        ArrayList<ResourceType> resToBuy = new ArrayList<>();
        ArrayList<ResourceType> resFromWarehouse = new ArrayList<>();
        ArrayList<ResourceType> resFromStrongbox = new ArrayList<>();
        boolean valid = false;
        //this.mmv.getGame().getPlayerById(ID).getLeaderDeck();
        //this.mmv.printActiveLeaders();
        cli.printToConsole("Choose the leader you want to use");
        String input = cli.readFromInput();
        if (input.equals("1") || input.equals("2")){
            int index = Integer.parseInt(input);
            switch(index){
                case 1 -> {
                    LeaderCard leaderCard = this.mmv.getGame().getPlayerById(ID).getLeaderDeck().getCards().get(index - 1);
                    if(!(leaderCard instanceof ExtraProdLCard)){
                        cli.printToConsole("This card does not provide you an extra production");
                        return null;
                    }
                    ExtraDepotLCard extraProdLCard = (ExtraDepotLCard) leaderCard;
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
                    if(chosenRes == 1) resToBuy.add(ResourceType.COIN);
                    else if(chosenRes == 2) resToBuy.add(ResourceType.STONE);
                    else if(chosenRes == 3) resToBuy.add(ResourceType.SERVANT);
                    else if(chosenRes == 4) resToBuy.add(ResourceType.SHIELD);

                    cli.printToConsole("Where would you like to take the " + extraProdLCard.getResType().printResourceColouredName()+ " from? (WARE -> warehouse / STRONG -> strongbox");
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



                }
            }
        }
        return(new ProductionMessage(resFromWarehouse, resFromStrongbox, resToBuy, ID));
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
        System.out.println("carta ritirata -------------");
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
        this.mmv.printProd(0, ID);
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
            cli.printToConsole("Choose the shelf where to put your " + res.printResourceColouredName() + " [1,2,3]");
            s = cli.readFromInput().replaceAll("[^0-9]", "");
            if(!(s.equals(""))){
                s1 = Integer.parseInt(s);
            }
            if (1 <= s1 && s1 <= 3) valid = true;
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
        serverConnection.send(new ManageResourceMessage(s1-1, s2-1,  ID));
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
//        ArrayList<LeaderCard> act = new ArrayList<LeaderCard>();
//        act.add(0,l3);
//        act.add(1,l4);
//        vett.add(0,l1);
//        vett.add(1,l2);
//        LeaderDeck actDeck = new LeaderDeck(2,1,act);
//        LeaderDeck deck = new LeaderDeck(2,1,vett);
//        mmv.getGame().getPlayerById(0).setLeaderDeck(deck);
//        mmv.getGame().getPlayerById(0).getPersonalBoard().setActiveLeader(actDeck);

        cli.printToConsole("Leaders in your hand : ");
        mmv.getGame().getPlayerById(this.ID).getLeaderDeck().print();

        cli.printToConsole("Your active Leaders : ");
        mmv.getGame().getPlayerById(this.ID).getPersonalBoard().getActiveLeader().print();


        boolean valid = false;
        String s;
        int idx;
        cli.printToConsole("Digit :\n'a' to activate one Leader, \n'd' to discard one Leader Card or \n'x' to return to the action menu");
        while (!valid) {
                String input= cli.readFromInput();
                if (input.equalsIgnoreCase("a")) {
                    System.out.println("a");
                    if(this.mmv.getGame().getPlayerById(ID).getPersonalBoard().getActiveLeader().getCards().size()<=2) {
                        cli.printToConsole("Select the leader you want to activate [1/2]");
                        idx = Integer.parseInt(cli.readFromInput());
                        if (idx == 1 || idx == 2) {
                            this.mmv.sendNotify(new PlayLeaderMessage(ID, idx, true, mmv.getGame().getPlayerById(ID).getLeaderDeck().getCards().get(idx - 1)));
                        } else
                            cli.printToConsole("Invalid input");
                    }
                    else
                        cli.printToConsole("you already have 2 active leaders");
                    valid = true;

                } else if (input.equalsIgnoreCase("d")) {
                    System.out.println("d");
                    cli.printToConsole("Select the leader you want to discard [1/2]");
                    idx = Integer.parseInt(cli.readFromInput());
                    if(idx==1 || idx==2){
                    this.mmv.sendNotify(new PlayLeaderMessage(ID,idx,false,this.mmv.getGame().getPlayerById(ID).getLeaderDeck().getCards().get(idx-1)));
                    }
                    else
                        cli.printToConsole("Invalid input");
                    valid = true;
                } else if (input.equalsIgnoreCase("x")){
                    System.out.println("x");
                    valid = true;
                } else cli.printToConsole("Invalid input! Retry!");
        }
    }


    public ProductionMessage useDevProduction() {
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


        m = new ProductionMessage(resFromWarehouse, resFromStrongbox,resToBuy, this.ID);
        this.mmv.sendNotify(m);
        return m;

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
        else if(message.getObjectID()==6) //LEADER
            this.mmv.getGame().getPlayerById(message.getID()).getPersonalBoard().setActiveLeader((LeaderDeck)message.getObject());
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
        else if(message.getObjectID() == 12){
            this.mmv.getGame().getPlayerById(message.getID()).getPersonalBoard().setFavorTile((int)message.getObject());
        }
        else if(message.getObjectID() == 13)
            this.mmv.getGame().getPlayerById(message.getID()).setFaithMarkerPos((int)message.getObject());
    }

    public void handleTurn(Turn turn) {

        if (mmv.getGame().getTurn().getPlayerPlayingID() == ID) {
            if (turn.isError()) {
                cli.printToConsole(turn.getErrorType().getString());
            }
            switch (turn.getPhase()) {
                case WAITING_FOR_ACTION: {
                    chooseAction();
                    break;
                }
                case MARKET: {
                    selectResourceFromHand();
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




