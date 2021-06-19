package it.polimi.ingsw.client;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.message.ActionMessages.*;
import it.polimi.ingsw.message.CommonMessages.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;


import java.util.ArrayList;

public class ModelMultiplayerView extends Observable<Message> implements Observer<Message> {

    private Game game;


    public void setGame(Game game) {
        this.game = game;
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

    public Game getGame() {
        return game;
    }

    public void printFaithTrack(int ID) {
        this.getGame().getPlayerById(ID).getPersonalBoard().getFaithTrack().print();
    }

    public void printActiveLeaders(int ID) {
        for(LeaderCard d : this.game.getPlayerById(ID).getPersonalBoard().getActiveLeader().getCards()){
            d.print();
        }
    }

    public void printMarket(){
        this.game.getBoard().getMarket().print();
    }

    public void printShelves(int ID)  {
        this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().print();
    }
    public void printStrongbox(int ID)  {
        this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().print();
    }

    public void printDevMatrix(){
        int index = 1;
        for(DevDeck d : this.game.getBoard().getMatrix().getDevDecks()){
            if(d.getCards().size() != 0) {
                System.out.println("Card number: " + index);
                d.getCards().get(0).print();
            }
            else{
                System.out.println("NO CARDS HERE");
            }
            index++;
        }
    }

    public void printProd(int chosenID, int ID){
        int slot = 1;
        int pos = 1;
        for(DevDeck dd : this.game.getPlayerById(chosenID).getPersonalBoard().getCardSlot()){
            System.out.println("SLOT: " + slot);
            for(DevCard dc : dd.getCards()){
                System.out.println("POSITION: " + pos);
                dc.print();
                pos++;
            }
            slot++;
        }
    }

    public void printPlayers(int ID) {
        for (Player p : this.game.getPlayers())  {
            if (p.getId() != ID) System.out.println("One of your opponents' nickname is " + p.getNickname() +
                    " and his ID is " + p.getId() );
            else System.out.println("Your nickname is " + p.getNickname() + " and your ID is " + p.getId());
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



}

