package it.polimi.ingsw.client.cli;


import it.polimi.ingsw.client.ClientActionController;
import it.polimi.ingsw.client.ModelMultiplayerView;
import it.polimi.ingsw.client.SocketServerConnection;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.message.ActionMessages.*;
import it.polimi.ingsw.message.CommonMessages.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

import java.io.IOException;
import java.util.Scanner;

public class CLI extends Observable<Message> implements Observer<Message> {
    private Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Choose MODE:\n0->SINGLEPLAYER\n1->MULTIPLAYER");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if(input.equals("0")){
            startSinglePlayer();
        }
        else{
            startMultiPlayer();
        }

    }
    /*--------- INITIAL PHASE MULTIPLAYER MATCH ------------*/
    public static void startMultiPlayer() throws IOException, ClassNotFoundException {
        CLI cli = new CLI();
        ModelMultiplayerView view = new ModelMultiplayerView(new Game(false, -1));
        SocketServerConnection ssc = new SocketServerConnection();
        ClientActionController cms = new ClientActionController(cli, view, ssc, false);
        ssc.setCac(cms);
        view.addObserver(cli);
        cms.setupMultiplayer();
    }


    public static void startSinglePlayer(){
        Game game = new Game(true, 0);
        game.setNumPlayer(1);
        Controller controller = new Controller(game);
        CLI cli = new CLI();
        ModelMultiplayerView modelMultiplayerView = new ModelMultiplayerView(new Game(true, 0));
        ClientActionController clientActionController = new ClientActionController(cli, modelMultiplayerView, null, true);
        modelMultiplayerView.addObserver(cli);
        clientActionController.addObserver(controller);
        game.addObserver(clientActionController);
        clientActionController.nicknameSetUp();

    }

    public synchronized String readFromInput (){
        System.out.print("> ");
        String input = in.nextLine();
        return input;
    }

    public synchronized void printToConsole(String message){
        System.out.println(message);
    }

    @Override
    public void update(Message message) {

    }

    @Override
    public void update(Nickname message) {

        if (message.getValid())  {
            System.out.println("Your nickname is " + message.getString() + " and your ID is " + message.getID());
        }
        else System.out.println("One of your opponent's nickname is "  + message.getString() + " and his ID is " + message.getID());

    }


    @Override
    public void update(IdMessage message) {

    }

    @Override
    public void update(ErrorMessage message) {


    }


    @Override
    public void update(ChooseNumberOfPlayer message) {

    }

    @Override
    public void update(PrintableMessage message) {
        message.getP().print();
    }

    @Override
    public void update(ObjectMessage message) {

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
    public void update(GameOverMessage message) {

    }


}
