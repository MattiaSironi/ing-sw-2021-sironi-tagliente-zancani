package it.polimi.ingsw.controller;

import it.polimi.ingsw.message.ActionMessages.ManageResourceMessage;
import it.polimi.ingsw.message.ActionMessages.ObjectMessage;
import it.polimi.ingsw.message.CommonMessages.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.message.*;

import java.util.ArrayList;

public class Controller implements Observer<Message> {
    private Game game;

    public Controller(Game game) {
        this.game = game;
    }

    public void setNickname(Nickname nickname) {
        String nick = nickname.getString();
        boolean found = false;
        ArrayList<Player> p = game.getPlayers();
        for (Player pl : p) {
            if (pl.getNickname().equals(nick)) {
                found = true;
                break;
            }

        }
        if (found) {
            game.reportError(new ErrorMessage("ko", nickname.getID()));
        } else {
            game.reportError(new ErrorMessage("ok", nickname.getID()));
            game.createNewPlayer(nickname);
        }
    }

    public void swapShelves(int s1, int s2, int ID){
        ArrayList<Shelf> shelves = this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().getShelves();
        Shelf temp;
        if(shelves.get(s1).getCount() <= s2 + 1 && shelves.get(s2).getCount() <= s1 + 1 ){
            temp = shelves.get(s1);
            shelves.set(s1, shelves.get(s2));
            shelves.set(s2, temp);
            this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().setShelves(shelves);
            game.sendObject(new ObjectMessage(this.game.getPlayerById(ID).getPersonalBoard().getWarehouse(), 0, ID));
        }
        else{
            game.reportError(new ErrorMessage("ko", ID));
        }
    }


    public void goToMarket(boolean type, int index, int ID){
        //riceve booleano riga o colonna e indice
        //setta il nuovo mercato e la tray out nel model e il model invia l'array comprato con la notify
    }

     public void placeRes(ResourceType r, int shelfIndex, int ID){
         //mette la risorsa al posto giusto se può
         //manda reportError con ok o ko a seconda che rispetti le regole
     }

     public void discardRes(int ID){
        //mette 1 punto fede a tutti gli altri giocatori
     }

     public void buyDevCard(int ID, DevCard d, boolean buyFrom){
         //paga la carta (toglie risorse dal model) con risorse dal forziere se buyFrom = True, dal Warehouse se False
         //aggiunge DevCard alla mano
     }

     public void activateDevProduction(int ID, DevCard d, int Res1FromWarehouse, int Res1FromStrongbox, int Res2FromWarehouse, int Res2FromStrongbox){
        //paga e produce
     }

     public void activateBasicProduction(int ID, ResourceType r1, ResourceType r2, ResourceType newRes, boolean buyFrom1, boolean buyFrom2){
        //consuma r1 ed r2 e produce r3 che mette nello strongbox
     }

     public void activateLeaderProduction(int ID, ResourceType r, ResourceType newRes, boolean buyFrom){
         //paga con r1
         //produce newRes e un punto vittoria
     }


//    Shelf temp;
//        if (this.shelves.get(s1).getCount() <= s2 + 1 && this.shelves.get(s2).getCount() <= s1 + 1) {
//        temp = this.shelves.get(s1);
//        this.shelves.set(s1, this.shelves.get(s2));
//        this.shelves.set(s2, temp);
//        notify();
//    } else System.out.println("Error. Not a valid operation. Check the game rules!\n ");


    @Override
    public void update(Message message) {
        //     if(message instanceof Nickname) setNickname((Nickname)message);
    }

    @Override public void update(Nickname message) {
        setNickname(message);
    }

    @Override public void update(InputMessage message) {}

    @Override
    public void update(IdMessage message) {
        
    }

    @Override
    public void update(ErrorMessage message) {
        if (message.getString().equals("all set"))  {

            game.printPlayerNickname(message.getID());
        }

    }

    @Override
    public void update(OutputMessage message) {

    }

    @Override
    public void update (ChooseNumberOfPlayer message)  {
        game.setNumPlayer(message.getNumberOfPlayers());

    }

    @Override
    public void update(PrintableMessage message) {

    }

    @Override
    public void update(ObjectMessage message) {

    }

    @Override
    public void update(ManageResourceMessage message) {
        swapShelves(message.getShelf1(), message.getShelf2(), message.getID());
    }




}
