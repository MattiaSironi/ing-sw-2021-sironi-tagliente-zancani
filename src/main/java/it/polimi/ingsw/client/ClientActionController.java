package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.constants.Color;
import it.polimi.ingsw.message.ActionMessages.*;
import it.polimi.ingsw.message.CommonMessages.*;
import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

import java.io.Console;
import java.io.IOException;
import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Stream;

/**
 * Class ClientActionController is the Class which contains for CLI mode part of Game's rules and user's input parsing. It parses and creates Message to send to the Server.
 * @author Mattia Sironi
 */
public class ClientActionController extends Observable<Message> implements Observer<Message>, UI {
    private final ModelMultiplayerView mmv;
    private final SocketServerConnection serverConnection;
    private int ID;
    private final CLI cli;
    private List<Actions> actions;
    private boolean singlePlayer;
    private final boolean local;




    public ClientActionController(CLI cli, ModelMultiplayerView mmv, SocketServerConnection socketServerConnection, boolean singlePlayer, boolean local) {
        this.serverConnection = socketServerConnection;
        this.mmv = mmv;
        this.cli = cli;
        this.local = local;
        setActions();
        this.singlePlayer = singlePlayer;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setupMultiplayer() throws IOException {
        serverConnection.run();

    }


    /**
     * Method setNumberOfPlayers let the host select the number of player for the next game.
     */
    public void setNumberOfPlayers() {
        String input;
        int numPlayers;
        boolean valid = false;
        do {
            cli.printToConsole("Choose number of players:");
            input = cli.readFromInput().replaceAll("[^0-9]", "");
            if (input.equals("")) numPlayers = -1;
            else numPlayers = Integer.parseInt(input);
            if (numPlayers >= 1 && numPlayers <= 4) {
                if (numPlayers == 1) singlePlayer = true;
                valid = true;
                cli.printToConsole("Number of players set to " + numPlayers);
            } else {
                cli.printErrorToConsole("Error! Number must be between 1 and 4");
            }
        } while (!valid);
        send(new ChooseNumberOfPlayer(numPlayers));
    }

    public void setActions() {
        actions = new ArrayList<>(Arrays.asList(Actions.values()));
    }

    /**
     * Method chooseAction permits the user to select a game Action.
     * @see Actions
     */

    public void chooseAction() {

        boolean actionEnded = false;
        while (!actionEnded) {
            String chosenAction;
            cli.printToConsole("\nSelect one action\nType:");
            for (Actions a : actions) {
                if (a.isEnable())
                    cli.printToConsole("\u2738 " + Color.ANSI_YELLOW + a.toString() + Color.ANSI_RESET + " to " + a.getString());
            }
            chosenAction = cli.readFromInput().toUpperCase(Locale.ROOT);
            if (Stream.of(Actions.values()).anyMatch(v -> v.name().equals(chosenAction))) {
                Actions selectedAction = Actions.valueOf(chosenAction);
                switch (selectedAction) {
                    case M -> {
                        if (Actions.M.isEnable()) {

                            actionEnded = goToMarket();



                        } else cli.printErrorToConsole("You cannot do this move twice or more in a single turn!");
                    }

                    case B -> {
                        if (Actions.B.isEnable()) {
                            actionEnded = chooseCard();


                        } else cli.printErrorToConsole("You cannot do this move twice or more in a single turn!");
                    }
                    case A -> {
                        if (Actions.A.isEnable()) {
                            activateProd();
                            actionEnded = true;
                        } else cli.printErrorToConsole("You cannot do this move twice or more in a single turn!");
                    }
                    case SM -> mmv.printMarket();
                    case SF -> printFaithTrack();
                    case SD -> this.mmv.printDevMatrix();
                    case SP -> printProd();
                    case SL -> actionEnded = printLeaders();
                    case SR ->  printShelves();
                    case MR -> {

                        actionEnded = manageResources();
                    }
                    case END -> {
                        actionEnded = true;
                        resetEnable();
                        send(new EndTurnMessage(ID));
                    }
                    default -> cli.printErrorToConsole("Invalid input");
                }
            } else
                cli.printErrorToConsole("Invalid input");
        }
    }

    /**
     * Method activateProd shows a list of the productions that the playr can choose
     */

    private void activateProd() {
        boolean valid = false;

        cli.printToConsole("Choose the productions you want to activate in this turn:\nType:\n1 to activate the basic production" +
                "\n2 to activate the leader production\n3 to activate the development card production\n0 to end the production phase");
        while (!valid) {
            String input = cli.readFromInput().replaceAll("[^0-9]", "");
            if (input.equals(""))
                cli.printErrorToConsole("Invalid input, try again");
            else {
                int prod = Integer.parseInt(input);
                switch (prod) {
                    case 1 -> {
                        valid = true;
                        chooseBasicRes();
                    }
                    case 2 -> {
                        valid = true;
                        useLeaderProduction();
                    }
                    case 3 -> {
                        valid = true;
                        try {
                            useDevProduction();
                        }catch(IndexOutOfBoundsException e){
                            cli.printErrorToConsole("You do not have any development card!");
                            activateProd();
                        }
                    }
                    case 0 -> {
                        valid = true;
                        send(new ProductionMessage(ID, true, -1, false));
                    }
                    default -> {
                        valid = false;
                        cli.printErrorToConsole("Invalid input, try again");
                    }
                }
            }
        }
    }

    /**
     * Method printShelves permits the user to see someone's ShelfWarehouse and Strongbox.
     */

    private void printShelves() {

        int chosenID = chooseID();
        mmv.printShelves(chosenID);
        mmv.printStrongbox(chosenID);
    }

    /**
     * Method printFaithTrack permits the user to see someone's FaithTrack.
     */

    private void printFaithTrack() {
        int chosenID = chooseID();
        mmv.printFaithTrack(chosenID);
    }

    /**
     * Method chooseID permits the user to select the ID of a player. This method is called by print methods to know which is the selected player.
     * if game is in SoloMode, method returns always 0, without asking for input user.
     * @return the ID of the selected player.
     */

    public int chooseID() {
        if(singlePlayer){
            return 0;
        }
        String input;
        int tempID = -1;
        cli.printToConsole("Choose the ID: ");
        boolean valid = false;
        while (!valid) {
            input = cli.readFromInput().replaceAll("[^0-9]", "");
            if (!input.equals("") && input.length() < 10) tempID = Integer.parseInt(input);
            if (mmv.getGame().getPlayerById(tempID) == null)
                cli.printErrorToConsole("there is no player with this ID associated. try another ID");
            else valid = true;
        }
        return tempID;
    }

    /**
     * Method goToMarket permits te user to start Action "Buy Resources". It prints the market, it parses user input, creates a MarketMessage and sends to the controller.
     * @return true if Action has been completed.
     */

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
            inputcleaned = input.replaceAll("[0-9]", " ");
            if (inputcleaned.equalsIgnoreCase("q")) return false;
            if (inputcleaned.equalsIgnoreCase("r ") || inputcleaned.equalsIgnoreCase("c ")) {
                row = (input.charAt(0) == 'r' || input.charAt(0) == 'R');
                index = Integer.parseInt(input.replaceAll("[^0-9]", ""));
                if ((row && index >= 1 && index <= 3) || (!row && index >= 1 && index <= 4)) {
                    valid = true;
                } else cli.printErrorToConsole("Invalid int, you selected " + index);
            } else cli.printErrorToConsole("Invalid command");
        }
        noMoreActions();
        send(new MarketMessage(row, index - 1, ID));
        return true;
    }

    /**
     * Method chooseCard handles the phase of buying a development card
     * @return true is the action is ended, false instead.
     */
    public boolean chooseCard() {
        String input;
        int index;
        this.mmv.printDevMatrix();
        cli.printToConsole("Choose the Development Card you want to buy");
        input = cli.readFromInput();
        try {
            index = Integer.parseInt(input);
            if (!(index >= 0 && index <= 12)) {
                cli.printErrorToConsole("Invalid input");
                return false;
            }
            cli.printToConsole("This card has cost:" + mmv.getGame().getBoard().getMatrix().getDevDecks().get(index - 1).getCards().get(0).getCostRes()[0]
                    + " " + ResourceType.COIN.printResourceColouredName() + " " + mmv.getGame().getBoard().getMatrix().getDevDecks().get(index - 1).getCards().get(0).getCostRes()[1]
                    + " " + ResourceType.STONE.printResourceColouredName() + " " + mmv.getGame().getBoard().getMatrix().getDevDecks().get(index - 1).getCards().get(0).getCostRes()[2]
                    + " " + ResourceType.SERVANT.printResourceColouredName() + " " + mmv.getGame().getBoard().getMatrix().getDevDecks().get(index - 1).getCards().get(0).getCostRes()[3]
                    + " " + ResourceType.SHIELD.printResourceColouredName());
            cli.printToConsole("Continue? [y/n]");
            input = cli.readFromInput();
            if (input.equalsIgnoreCase("y")) {
                send(new BuyDevCardMessage(index - 1, ID, false, -1));
                return true;
            } else if (input.equalsIgnoreCase("n")) {
                cli.printErrorToConsole("Aborted");
                return false;
            } else {
                cli.printErrorToConsole("Invalid input, try again");
                return false;
            }
        }catch(NumberFormatException  | IndexOutOfBoundsException e){
            cli.printErrorToConsole("Invalid input, try again!");
            return false;
        }
    }

    /**
     * Method chooseResToProduce asks the user the resource he want to produce during a basic production or a leader production
     * @param turn  of type boolean - true if is basicProductionPhase, false instead.
     */

    public void chooseResToProduce(boolean turn) {
        ResourceType bought;
        boolean valid = false;
        mmv.printShelves(ID);
        mmv.printStrongbox(ID);
        cli.printToConsole("Choose the Resource you want to produce\n(Choose between COIN,STONE,SERVANT,SHIELD)");
        String input = cli.readFromInput().toUpperCase(Locale.ROOT);
        if (Stream.of(ResourceType.values()).anyMatch(v -> v.name().equals(input))) {
            bought = ResourceType.valueOf(input);
            if (turn) {
                send(new BasicProductionMessage(null, null, bought, ID, false));
            }
            else {
                send(new LeaderProductionMessage(0, ID, bought));
            }
        } else {
            chooseResToProduce(turn);
        }
    }

    /**
     * Method chooseBasicRes asks the player to choose two resource he want to pay for the basic production
     */

    public void chooseBasicRes() {
        ResourceType chosen1, chosen2;
        mmv.printShelves(ID);
        mmv.printStrongbox(ID);
        cli.printToConsole("Now choose two resources you want to use\n[resource,resource]");
        try {
            String[] input = cli.readFromInput().toUpperCase(Locale.ROOT).split(",", 2);
            String part1 = input[0];
            String part2 = input[1];
            if (Stream.of(ResourceType.values()).anyMatch(v -> v.name().equals(part1)) &&
                    Stream.of(ResourceType.values()).anyMatch(v -> v.name().equals(part2))) {
                chosen1 = ResourceType.valueOf(part1);
                chosen2 = ResourceType.valueOf(part2);
                send(new BasicProductionMessage(chosen1, chosen2, null, ID, false));
            }
            else
                chooseBasicRes();
        } catch(PatternSyntaxException | ArrayIndexOutOfBoundsException e){
            cli.printErrorToConsole("Invalid Input, try again!");
            chooseBasicRes();
        }
    }

    /**
     * Method useLeaderProduction asks the user which leader card to use for his productions
     */
    public void useLeaderProduction() {
        this.mmv.printActiveLeaders(ID);
        cli.printToConsole("Choose the leader you want to use");
        String input = cli.readFromInput().replaceAll("[^0-9]", "");
        if (input.equals("1") || input.equals("2")) {
            int index = Integer.parseInt(input);
            send(new LeaderProductionMessage(index - 1, ID, null));
        } else {
            cli.printErrorToConsole("Invalid input, try again");
            useLeaderProduction();
        }
    }

    /**
     * Method askForResource permits the user to keep or not a ResourceType.
     * @param res is the ResourceType.
     */
    public void askForResource(ResourceType res) {

        cli.printToConsole("Do you want to keep it or not? [y/n]");
        boolean valid = false;
        while (!valid) {
            String input = cli.readFromInput();
            if (input.equalsIgnoreCase("y")) {
                whereToPut(res, false);
                valid = true;


            } else if (input.equalsIgnoreCase("n")) {
                discardRes(res);
                valid = true;
            } else cli.printErrorToConsole("Invalid input! Retry!");
        }
    }

    /**
     * Method chooseInitialResources permits the user to select initial resources.
     */
    public void chooseInitialResources() {
        ArrayList<ResourceType> possibleRes = new ArrayList<>();
        possibleRes.add(ResourceType.COIN);
        possibleRes.add(ResourceType.STONE);
        possibleRes.add(ResourceType.SERVANT);
        possibleRes.add(ResourceType.SHIELD);
        ResourceType selectedRes = null;
        cli.printToConsole("Choose your starting Resource");
        selectedRes = getResourceType(possibleRes, false, null);
        whereToPut(selectedRes, true);
    }


    /**
     * Method discardRes creates and sends a PlaceResourceMessage with discard parameters.
     * @param res is the resource to discard.
     */

    private void discardRes(ResourceType res) {
        send(new PlaceResourceMessage(res, -1, ID, false, true));
        cli.printErrorToConsole("Other players will receive one faith point.");
    }

    /**
     * Method pay handles all the payment phases of the game and allows the player to choose where he want to take the resource from
     */
    public void pay() {
        boolean payFrom = false, valid = false;
        while (!valid) {
            cli.printToConsole("Resource " + this.mmv.getGame().getBoard().getMatrix().getResToPay().get(0).printResourceColouredName());
            cli.printToConsole("Type 'W' to pick it from your WAREHOUSE or type 'S' to pick it from your Strongbox");
            String input = cli.readFromInput().toUpperCase(Locale.ROOT);
            switch (input) {
                case "W" -> {
                    payFrom = true;
                    valid = true;
                }
                case "S" -> valid = true;
                default -> cli.printErrorToConsole("Invalid input");
            }
        }
        if (this.mmv.getGame().getTurn().getPhase() == ActionPhase.B_PAYMENT)
            send(new BuyDevCardMessage(-1, ID, payFrom, -1));
        else if(this.mmv.getGame().getTurn().getPhase() == ActionPhase.D_PAYMENT)
            send(new ProductionMessage(this.ID, false, -1, payFrom));
        else
            send(new BasicProductionMessage(null, null, null, ID, payFrom));
    }


    /**
     * Method placeDevCard asks the player the index of the slot where he want to place his new development card
     */
    public void placeDevCard() {
        this.mmv.printProd(ID, ID);
        cli.printToConsole("Where would you like to place your new development card? (Choose slot 1, 2 or 3)");
        try {
            String input = cli.readFromInput();
            int chosenIndex = Integer.parseInt(input);
            if (chosenIndex >= 1 && chosenIndex <= 3) {
                send(new BuyDevCardMessage(-1, ID, false, chosenIndex - 1));
            }
        } catch(NumberFormatException e){
            cli.printErrorToConsole("Invalid input, try again");
            placeDevCard();
        }
    }


    public CLI getCli() {
        return cli;
    }

    /**
     * Method whereToPut asks the user where to put initial or market's resource.
     * @param res is the ResourceType.
     * @param initialPhase is true if res is a initial resource.
     */

    private void whereToPut(ResourceType res, boolean initialPhase) {
        int s1 = 0;
        boolean valid = false;
        String s;
        while (!valid) {
            cli.printToConsole("Choose the shelf where to put your " + res.printResourceColouredName() + " [1,2,3,4,5]");
            s = cli.readFromInput().replaceAll("[^0-9]", "");
            if (!(s.equals("")) && s.length() == 1 ) {
                s1 = Integer.parseInt(s);
            }
            if (1 <= s1 && s1 <= 5) valid = true;
            else cli.printErrorToConsole("Invalid input! retry!");
        }
        send(new PlaceResourceMessage(res, s1 - 1, ID, initialPhase, false));
    }

    /**
     * Method noMoreActions does not permit the user to select a macro action if in its turn he already called one of them.
     */


    private void noMoreActions() {
        Actions.A.setEnable(false);
        Actions.B.setEnable(false);
        Actions.M.setEnable(false);
    }

    /**
     * Method manageResources permits the user to swap his shelves. It parses input, creates a ManageResourceMessage and sends it.
     * @return true whether move was completed.
     */

    private boolean manageResources() {
        mmv.printShelves(ID);
        String input;
        int s1 = 0;
        int s2 = 0;
        boolean valid = false;
        while (!valid) {
            cli.printToConsole("Select the first shelf or press q to quit:");
            input = cli.readFromInput();
            if (input.equalsIgnoreCase("q") )return false;
            else input = input.replaceAll("[^0-9]", "");
            if (input.equals("") || input.length() != 1) s1 = -1;
            else s1 = Integer.parseInt(input);

            cli.printToConsole("Select the second shelf:");
            input = cli.readFromInput().replaceAll("[^0-9]", "");
            if (input.equals("") || input.length() != 1) s2 = -1;
            else s2 = Integer.parseInt(input);

            if (s1 != s2 && s1 >= 1 && s1 <= 5 && s2 >= 1 && s2 <= 5) {
                valid = true;
            } else cli.printErrorToConsole("Invalid input! Retry!");

        }
        send(new ManageResourceMessage(s1 - 1, s2 - 1, ID));
        return true;
    }

    /**
     * Method resetEnable permits the user to re-select macro actions. This method is called after user selects to end his turn.
     */
    private void resetEnable() {
        for (Actions a : actions) {
            a.setEnable(true);
        }
    }

    /**
     * Method printProd permits the user to see someone's DevCards.
     */

    public void printProd() {
        int chosenID = chooseID();
        mmv.printProd(chosenID, this.ID);
    }


    /**
     * This method manages the interaction with the CLI user to activate or discard a leader card.
     * It checks some basic input errors before calling the method to send the proper message to the server.
     * @return true if the user action has been correctly executed, false otherwise.
     */
    public boolean printLeaders() {
        boolean ok = true;
        int p;
        p = this.chooseID();
        String in;
//
//        cli.printToConsole("Choose the ID of the player whose leaders you want to see");
//        p = Integer.parseInt(cli.readFromInput());

        cli.printToConsole("Leaders in hand : ");
        mmv.getGame().getPlayerById(p).getLeaderDeck().print();

        cli.printToConsole("Active Leaders : ");
        mmv.getGame().getPlayerById(p).getPersonalBoard().getActiveLeader().print();

        if (p == this.ID) {
            boolean valid = false;
            String inp;
            int idx;
            cli.printToConsole("Type :\n'a' to activate one Leader, \n'd' to discard one Leader Card or \n'x' to return to the action menu");
            while (!valid) {
                String input = cli.readFromInput();
                if (input.equalsIgnoreCase("a")) {
                    if (this.mmv.getGame().getPlayerById(ID).getPersonalBoard().getActiveLeader().getCards().size() <= 2) {
                        cli.printToConsole("Select the leader you want to activate [1/2]");
                        inp = cli.readFromInput().replaceAll("[^0-9]", "");
                        if (inp.equals("")) idx = -1;
                        else {
                            idx = Integer.parseInt(inp);
                        }
                      //  idx = Integer.parseInt(cli.readFromInput());
                        if ((idx == 1 || idx == 2 )) {
                            //   System.out.println("send");
                            send(new PlayLeaderMessage(ID, idx, true, mmv.getGame().getPlayerById(ID).getLeaderDeck().getCards().get(idx - 1), false));
                            return true;
                        } else
                            cli.printErrorToConsole("Invalid input");
                    } else
                        cli.printErrorToConsole("you already have 2 active leaders");
                    valid = true;

                } else if (input.equalsIgnoreCase("d")) {
                    cli.printToConsole("Select the leader you want to discard [1/2]");
                    inp = cli.readFromInput().replaceAll("[^0-9]", "");
                    if (inp.equals("")) idx = -1;
                    else {
                        idx = Integer.parseInt(inp);
                    }
                   // idx = Integer.parseInt(cli.readFromInput());
                    if ((idx == 1 || idx == 2) && idx <= mmv.getGame().getPlayerById(ID).getLeaderDeck().getCards().size()) {
                        //   System.out.println("send");
                        send(new PlayLeaderMessage(ID, idx, false, this.mmv.getGame().getPlayerById(ID).getLeaderDeck().getCards().get(idx - 1), false));
                        return true;
                    } else {
                        cli.printErrorToConsole("Invalid input");
                        if (idx > mmv.getGame().getPlayerById(ID).getLeaderDeck().getCards().size())
                            return false;
                    }
                    valid = true;
                } else if (input.equalsIgnoreCase("x")) {
                    valid = true;
                } else cli.printErrorToConsole("Invalid input! Retry!");
            }
        }
        return false;
    }


    /**
     * Method discardLead permits the user in initial phase to discard two of the four cards given to him.
     * It parses user input, creates a PlayLeaderMessage and sends it.
     *
     */

    private void discardLead(int remaining) {

        mmv.getGame().getPlayerById(ID).getLeaderDeck().print();
        cli.printToConsole(" REMAINING CARD(S) TO DISCARD :  " + remaining);
        boolean valid = false;
        String input;
        int idx = 0;
        while (!valid) {
            cli.printToConsole("select one card to discard by typing its index : ");
            input = cli.readFromInput().replaceAll("[^0-9]", "");
            if (input.equals("") || input.length() != 1 ) idx = -1;
            else idx = Integer.parseInt(input);
            if (idx > 0 && idx <= mmv.getGame().getPlayerById(ID).getLeaderDeck().getCards().size()) valid = true;
            else cli.printErrorToConsole("Invalid value!");


        }

        send(new PlayLeaderMessage(ID, idx, false, this.mmv.getGame().getPlayerById(ID).getLeaderDeck().getCards().get(idx - 1), true));


    }

    /**
     * Method useDevProduction asks the user which card to use for his development productions
     */
    public void useDevProduction(){
        cli.printToConsole("Choose the slot (1,2 or 3):");
        String input = cli.readFromInput();
        try{
            int slot = Integer.parseInt(input);
            send(new ProductionMessage(this.ID, false, slot - 1, false));
        }catch(PatternSyntaxException e){
            useDevProduction();
        }
    }

    /**
     * @param i is the index used in the arrays for strongbox/warehouse shelves for a resource
     * @return ResourceType that corresponds to index i
     */
    public ResourceType FromIntToRes(int i) {
        switch (i) {
            case 0 -> {
                return ResourceType.COIN;
            }
            case 1 -> {
                return ResourceType.STONE;
            }
            case 2 -> {
                return ResourceType.SERVANT;
            }
            case 3 -> {
                return ResourceType.SHIELD;
            }
            case 4 -> {
                return ResourceType.FAITH_POINT;
            }
            default -> {
                return ResourceType.EMPTY;
            }
        }
    }

    @Override
    public void handleAction(Object o) {
        if (o instanceof ChooseNumberOfPlayer) {
            if(((ChooseNumberOfPlayer) o).getNumberOfPlayers() == -1)
                setNumberOfPlayers();
            else {
                cli.printToConsole("Number of players : " + ((ChooseNumberOfPlayer) o).getNumberOfPlayers());
                nicknameSetUp();
            }

        } else if (o instanceof ObjectMessage) {
            handleObject((ObjectMessage) o);
        }

    }

    @Override
    public void disconnect() {
        System.exit(0);
    }


    /**
     * Method handleObject is in charge to update Model's part of Client.
     * @param message is a ObjectMessage, containing part of Model.
     */

    private void handleObject(ObjectMessage message) {
        if (message.getObjectID() == -1) { //GAME
            this.mmv.setGame((Game) message.getObject());
            this.mmv.printPlayers(ID);
        } else if (message.getObjectID() == 0) { //WAREHOUSE
            this.mmv.getGame().getPlayerById(message.getID()).getPersonalBoard().setWarehouse((ShelfWarehouse) message.getObject());
        } else if (message.getObjectID() == 1)   //MARKET
            this.mmv.getGame().getBoard().setMarket((Market) message.getObject());
        else if (message.getObjectID() == 2) //MATRIX OF DEV CARD
            this.mmv.getGame().getBoard().setMatrix((DevelopmentCardMatrix) message.getObject());
        else if (message.getObjectID() == 3) //STRONGBOX
            this.mmv.getGame().getPlayerById(message.getID()).getPersonalBoard().setStrongbox((Strongbox) message.getObject());
        else if (message.getObjectID() == 4) //CARDSLOT
            this.mmv.getGame().getPlayerById(message.getID()).getPersonalBoard().setCardSlot((ArrayList<DevDeck>) message.getObject());
        else if (message.getObjectID() == 5) //initialResource
        {
            this.mmv.getGame().getPlayerById(message.getID()).setStartResCount((int) message.getObject());
            if (message.getID() == ID) {
                if (this.mmv.getGame().getPlayerById(ID).getStartResCount() > 0 && this.mmv.getGame().getPlayerById(ID).getLeaderCardsToDiscard() == 0) chooseInitialResources();
            }
        } else if (message.getObjectID() == 6) { //LEADER
            this.mmv.getGame().getPlayerById(message.getID()).getPersonalBoard().setActiveLeader((LeaderDeck) message.getObject());
        } else if (message.getObjectID() == 7) {
            this.mmv.getGame().getPlayerById(message.getID()).getPersonalBoard().setLeaderChosen((ExtraProdLCard) message.getObject());
        } else if (message.getObjectID() == 8) { //leaderDeck

            this.mmv.getGame().getPlayerById(message.getID()).setLeaderDeck((LeaderDeck) message.getObject());
        } else if (message.getObjectID() == 9) { //Communication
            this.mmv.getGame().setCommunication((Communication) message.getObject());
            handleCommunication(mmv.getGame().getCommunication());
        } else if (message.getObjectID() == 10) {
            this.mmv.getGame().setTurn((Turn) message.getObject());
            handleTurn((mmv.getGame().getTurn()));
        } else if (message.getObjectID() == 11) {
            if (message.getID() == 0)
                this.mmv.getGame().setFirstVatican(true);
            else if (message.getID() == 1)
                this.mmv.getGame().setSecondVatican(true);
            else
                this.mmv.getGame().setThirdVatican(true);
        } else if (message.getObjectID() == 12)
            this.mmv.getGame().getPlayerById(message.getID()).getPersonalBoard().setFaithTrack((FaithTrack) message.getObject());
        else if (message.getObjectID() == 13) {
            this.mmv.getGame().getPlayerById(message.getID()).setLeaderCardsToDiscard((int) message.getObject());
            if (message.getID() == ID && this.mmv.getGame().getPlayerById(message.getID()).getLeaderCardsToDiscard() > 0)
                discardLead(this.mmv.getGame().getPlayerById(message.getID()).getLeaderCardsToDiscard());
        }
        else if(message.getObjectID() == 14){
            this.mmv.getGame().getBoard().setTokenArray((ArrayList<SoloActionToken>) message.getObject());
        }
        else if(message.getObjectID()==15){
            this.mmv.getGame().setPlayerByID(message.getID(),(Player)message.getObject());
        }
    }


    /**
     * Method handleCommunication prints the Communication if the Player is the addressee Player.
     */
    private void handleCommunication(Communication communication) {

        if (communication.getAddresseeID() == ID || communication.getAddresseeID() == -1) {

            cli.printToConsole(Color.ANSI_YELLOW +communication.getCommunication().getString() + Color.ANSI_RESET);
            if (communication.getCommunication() == CommunicationList.NICK_NOT_VALID) nicknameSetUp();
        }
    }

    /**
     * Method handleTurn looks at @param turn and according to Turn phase it calls parsing methods.
     */

    public void handleTurn(Turn turn) {
        if (turn.getPhase() == ActionPhase.GAME_OVER) {
            if (turn.getPlayerPlayingID() == ID) cli.printToConsole(Color.ANSI_RED + "You win!" + Color.ANSI_RESET);
            else {
                if (turn.getPlayerPlayingID() == -1) {
                    cli.printToConsole(Color.ANSI_RED + "Lorenzo Il Magnifico won!" + Color.ANSI_RESET);
                    cli.printToConsole(Color.ANSI_RED + "Better luck next time!" + Color.ANSI_RESET);
                    System.exit(0);

                } else {
                    cli.printToConsole(Color.ANSI_BLUE + this.mmv.getGame().getPlayerById(turn.getPlayerPlayingID()).getNickname() + " " + turn.getPhase().getOthers() + Color.ANSI_RESET);
                    cli.printToConsole(Color.ANSI_RED + "You lose!" + Color.ANSI_RESET);


                }
            }
        }


         else if (turn.getPlayerPlayingID() == ID) {

                switch (turn.getPhase()) {
                    case WAITING_FOR_ACTION -> {
                        chooseAction();
                    }
                    case MARKET -> {

                        selectResourceFromHand();
                    }

                    case A_PAYMENT -> {
                        activateProd();
                    }

                    case PAYMENT, B_PAYMENT, D_PAYMENT -> {
                        noMoreActions();
                        pay();
                    }
                    case CHOOSE_SLOT -> {
                        placeDevCard();
                    }
                    case BASIC -> {
                        noMoreActions();
                        chooseResToProduce(true);
                    }
                    case SELECT_RES -> {
                        noMoreActions();
                        chooseResToProduce(false);
                    }
                }
            } else
            cli.printToConsole( Color.ANSI_BLUE + this.mmv.getGame().getPlayerById(turn.getPlayerPlayingID()).getNickname() + " " + turn.getPhase().getOthers() + Color.ANSI_RESET);
        }


    /**
     * Method selectResourceFromHand looks at the first resource to place and calls parsing methods.
     */
    private void selectResourceFromHand() {
        ResourceType res = mmv.getGame().getBoard().getMarket().getHand().get(0).getRes();

        switch (res) {
            case COIN, STONE, SERVANT, SHIELD -> {
                mmv.printShelves(ID);
                cli.printToConsole("you received a " + res.printResourceColouredName() + "!");
                askForResource(res);

            }
            case FAITH_POINT -> send(new PlaceResourceMessage(ResourceType.FAITH_POINT, -1, ID, false, false));
            default -> whiteMarbleCase();
        }

    }

    /**
     * Method whiteMarbleCase is called when the first marble in Market Hand is a EMPTY Marble.
     * It looks at the Model and chooses which case show to Player.
     */

    private void whiteMarbleCase() {
        ArrayList<ResourceType> possibleRes = new ArrayList<>();
        possibleRes.add(this.mmv.getGame().getPlayerById(ID).getWhiteConversion1());
        possibleRes.add(this.mmv.getGame().getPlayerById(ID).getWhiteConversion2());
        int count = (int) possibleRes.stream().filter(Objects::nonNull).count();

        switch (count) {
            case 0 -> {
                getCli().printToConsole("You did not receive any Resource Type from the white marble");
                send(new PlaceResourceMessage(ResourceType.EMPTY, -1, ID, false, false));
            }
            case 1 -> {

                getCli().printToConsole("due to your Leader Cards you received a " + possibleRes.get(0).printResourceColouredName() + " from the white marble!");
                mmv.printShelves(ID);
                askForResource(possibleRes.get(0));


            }
            default -> {

                mmv.printShelves(ID);
                getCli().printToConsole("due to your Leader Cards you can generate one of the following Resources from the white marble : "
                        + possibleRes.get(0).printResourceColouredName() + " or "
                        + possibleRes.get(1).printResourceColouredName() + ". ");
                getCli().printToConsole("Select one of them: ");


                ResourceType selectedRes;

                selectedRes = getResourceType(possibleRes, false, null);

                askForResource(selectedRes);
            }
        }


    }

    /**
     * Method getResourceType parses user input and finds a match with possible ResourceType.
     * @param possibleRes is a Collection containing all possibleRes user can select.
     * @return the ResourceType selected
     */

    private ResourceType getResourceType(ArrayList<ResourceType> possibleRes, boolean valid, ResourceType selectedRes) {
        String input;
        while (!valid) {
            input = cli.readFromInput();
            String finalInput = input;
            if (possibleRes.stream().anyMatch(v -> v.name().equals(finalInput.toUpperCase()))) {
                selectedRes = ResourceType.valueOf(finalInput.toUpperCase());
                valid = true;
            } else cli.printErrorToConsole("Invalid input, try again!");
        }
        return selectedRes;
    }

    /**
     * Method nicknameSetUp permits the user to choose a nickname.
     */

    public void nicknameSetUp() {
        cli.printToConsole("Choose your nickname:");
        String nickname = cli.readFromInput();
        send(new Nickname(nickname, ID));
    }

    /**
     * Method send sends Message created by parser methods. it calls notify if game is local. If not, it calls SocketServerConnection's send.
     * @param o is the Message to send.
     * @see it.polimi.ingsw.server.SocketClientConnection
     */

    public void send(Message o){
        if(local) {
            if (o instanceof PlaceResourceMessage) {
                notify((PlaceResourceMessage) o);
            } else if (o instanceof Nickname) {
                notify((Nickname) o);
            } else if (o instanceof MarketMessage) {
                notify((MarketMessage) o);
            } else if (o instanceof BuyDevCardMessage) {
                notify((BuyDevCardMessage) o);
            } else if (o instanceof ProductionMessage) {
                notify((ProductionMessage) o);
            } else if (o instanceof PlayLeaderMessage) {
                notify((PlayLeaderMessage) o);
            } else if (o instanceof ManageResourceMessage) {
                notify((ManageResourceMessage) o);
            } else if (o instanceof EndTurnMessage) {
                notify((EndTurnMessage) o);
            } else if (o instanceof BasicProductionMessage) {
                notify((BasicProductionMessage) o);
            } else if (o instanceof LeaderProductionMessage) {
                notify((LeaderProductionMessage) o);
            }
        }
        else
            serverConnection.send(o);
    }

    @Override
    public void update(ObjectMessage message) {
        handleObject(message);
    }

    @Override
    public void update(ManageResourceMessage message) {

    }

    @Override
    public void update(MarketMessage message) {

    }

    @Override
    public void update(PlaceResourceMessage message) {

    }

    @Override
    public void update(BuyDevCardMessage message) {

    }

    @Override
    public void update(PlayLeaderMessage message) {

    }

    @Override
    public void update(ProductionMessage message) {

    }

    @Override
    public void update(EndTurnMessage message) {

    }

    @Override
    public void update(BasicProductionMessage message) {

    }

    @Override
    public void update(LeaderProductionMessage message) {

    }

    @Override
    public void update(Message message) {

    }

    @Override
    public void update(Nickname message) {

    }




}




