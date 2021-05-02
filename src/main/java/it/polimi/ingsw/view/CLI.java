package it.polimi.ingsw.view;


import it.polimi.ingsw.message.*;
import it.polimi.ingsw.message.ActionMessages.*;
import it.polimi.ingsw.message.CommonMessages.*;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

import java.util.Scanner;

public class CLI extends Observable<Message> implements Observer<Message> {
    private Scanner in = new Scanner(System.in);

    public void run(){

    }
    public String readFromInput(){
        System.out.print("> ");
        String input = in.nextLine();
        return input;
    }

    public void printToConsole(String message){
        System.out.println(message);
    }

    @Override
    public void update(Message message) {

    }

    @Override
    public void update(Nickname message) {

    }

    @Override
    public void update(InputMessage message) {

    }

    @Override
    public void update(IdMessage message) {

    }

    @Override
    public void update(ErrorMessage message) {
//        System.out.println(message.getString()); useless for local changes //TODO

    }

    @Override
    public void update(OutputMessage message) {
        System.out.println(message.getString());
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
    public void update(ResourceListMessage message) {

    }

    @Override
    public void update(PlaceResourceMessage message) {

    }
}
