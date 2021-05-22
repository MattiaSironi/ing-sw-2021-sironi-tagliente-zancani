package it.polimi.ingsw.view;


import it.polimi.ingsw.message.*;
import it.polimi.ingsw.message.ActionMessages.*;
import it.polimi.ingsw.message.CommonMessages.*;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

import java.io.IOException;
import java.util.Scanner;

public class CLI extends Observable<Message> implements Observer<Message> {
    private Scanner in = new Scanner(System.in);

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


    public synchronized String readFromInput1 () throws IOException {
        System.out.print("> ");
        String input = in.nextLine();
        return input;
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


}
