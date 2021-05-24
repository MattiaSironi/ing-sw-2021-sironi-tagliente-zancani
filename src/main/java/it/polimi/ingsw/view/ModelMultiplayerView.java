package it.polimi.ingsw.view;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.message.ActionMessages.*;
import it.polimi.ingsw.message.CommonMessages.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;


import java.util.ArrayList;

public class ModelMultiplayerView extends Observable<Message> implements Observer<Message> {

    private Game game;
    private ClientActionController cac; //just for local testing

    public void setGame(Game game) {
        this.game = game;
    }

    public ClientActionController getCac() { //just for local testing
        return cac;
    }

    public void setCac(ClientActionController cac) { //just for local testing
        this.cac = cac;
    }

    public ModelMultiplayerView(Game game) {
        this.game = game;
    }

    public static int getSize() {
        return size;
    }

    public static void setSize(int size) {
        ModelMultiplayerView.size = size;
    }

    private static int size ;

    public ModelMultiplayerView() {}

    public void sendNotify(ErrorMessage message) {
        notify(message);
    }

    public void sendNotify(Message message) {
        notify(message);
    }

    public void sendNotify(PlaceResourceMessage message) {
        notify(message);
    }

    public void sendNotify(ManageResourceMessage message)  {
        notify(message);
    } //local testing

    public void sendNotify(MarketMessage message)  {
        notify(message);
    } //local testing

    public void sendNotify(BuyDevCardMessage message){ notify(message); }

    public void sendNotify(PlayLeaderMessage message) {
        notify((PlayLeaderMessage)message);
    }
    public void sendNotify(ProductionMessage message){
        notify(message);
    }



    public Game getGame() {
        return game;
    }

    public void printMarket(){
        notify(new PrintableMessage(this.game.getBoard().getMarket()));
    }

    public void printShelves(int ID)  {
        notify(new PrintableMessage(this.game.getPlayerById(ID).getPersonalBoard().getWarehouse()));
    }
    public void printStrongbox(int ID)  {
        notify(new PrintableMessage(this.game.getPlayerById(ID).getPersonalBoard().getStrongbox()));
    }

    public void printDevMatrix(){
        int index = 1;
        for(DevDeck d : this.game.getBoard().getMatrix().getDevDecks()){
            if(d.getSize() != 0) {
                System.out.println("Card number: " + index);
                index++;
                notify(new PrintableMessage((d.getCards().get(0))));
            }
        }
    }

    public void printProd(int chosenID, int ID){
        int slot = 1;
        int pos = 1;
        for(DevDeck dd : this.game.getPlayerById(chosenID).getPersonalBoard().getCardSlot()){
            System.out.println("SLOT: " + slot);
            for(DevCard dc : dd.getCards()){
                System.out.println("POSITION: " + pos);
                notify(new PrintableMessage(dc));
                pos++;
            }
            slot++;
        }
    }

    @Override
    public void update(Message message) {

    }

    @Override
    public void update(Nickname message) {
        notify(message);

    }



    @Override
    public void update(IdMessage message) {

    }

    @Override
    public void update(ErrorMessage message) {
//        notify(message); //socket

//        if (message.getString().equals("ko"))  {
//            this.cac.getCli().printToConsole("Invalid move. Try another one!");
//        }
//        else if (!message.getString().equals("ok")) {
//            this.cac.getCli().printToConsole(message.getString());
//            this.cac.getCli().printToConsole("Try another shelf or discard it!");
//            this.cac.askForResource(message.getR());
//
//        }
        if(message.getID() == this.getCac().getID())
            this.cac.getCli().printToConsole(message.getString());
    }



    @Override
    public void update(ChooseNumberOfPlayer message) {

    }

    @Override
    public void update(PrintableMessage message) {

    }

    @Override
    public void update(ObjectMessage message) {
        if (message.getObjectID()==0)
        this.game.getPlayerById(message.getID()).getPersonalBoard().setWarehouse((ShelfWarehouse)message.getObject());
        else if (message.getObjectID()==1)
            this.game.getBoard().setMarket((Market) message.getObject());
        else if(message.getObjectID()==2)
            this.game.getBoard().setDevDecks((ArrayList<DevDeck>)message.getObject());
        else if(message.getObjectID()==3)
            this.game.getPlayerById(message.getID()).getPersonalBoard().setWarehouse((ShelfWarehouse)message.getObject());
        else if(message.getObjectID()==4)
            this.game.getPlayerById(message.getID()).getPersonalBoard().setStrongbox((Strongbox)message.getObject());
        else if(message.getObjectID()==5)
            this.game.getPlayerById(message.getID()).getPersonalBoard().setCardSlot((ArrayList<DevDeck>)message.getObject());
        else if(message.getObjectID()==6) {
            this.game.getPlayerById(message.getID()).getPersonalBoard().setActiveLeader((LeaderDeck) message.getObject());
        }
        else if(message.getObjectID()==8) {
            this.game.getPlayerById(message.getID()).setLeaderDeck((LeaderDeck) message.getObject());
        }

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


//    @Override
//    public void update(LeaderProdMessage message){
//
//    }




    public void printFaithTrack(int ID) {
       notify(new PrintableMessage(this.getGame().getPlayerById(ID).getPersonalBoard().getFaithTrack()));
    }

    public void printActiveLeaders(int ID) {
        for(LeaderCard d : this.game.getPlayerById(ID).getPersonalBoard().getActiveLeader().getCards()){
            notify(new PrintableMessage(d));
        }
    }
}

