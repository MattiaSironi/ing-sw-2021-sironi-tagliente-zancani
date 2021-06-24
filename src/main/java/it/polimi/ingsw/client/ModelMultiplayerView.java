package it.polimi.ingsw.client;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.message.ActionMessages.*;
import it.polimi.ingsw.message.CommonMessages.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;


import java.util.ArrayList;

/**
 * Class ModelMultiplayerView is a part of the Game that a Player can see. It is used for prints in CLI mode.
 */

public class ModelMultiplayerView extends Observable<Message> implements Observer<Message> {

    private Game game;


    public void setGame(Game game) {
        this.game = game;
    }

    public ModelMultiplayerView(Game game) {
        this.game = game;
    }


    public Game getGame() {
        return game;
    }

    /**
     * Method printFaithTrack calls FaithTrack.print of the selected Player.
     * @param ID is the ID of the selected player.
     * @see FaithTrack
     */

    public void printFaithTrack(int ID) {
        this.getGame().getPlayerById(ID).getPersonalBoard().getFaithTrack().print();
    }
    /**
     * Method printActiveLeaders calls every LeaderCard.print of the selected Player.
     * @param ID is the ID of the selected player.
     * @see LeaderCard
     */

    public void printActiveLeaders(int ID) {
        for(LeaderCard d : this.game.getPlayerById(ID).getPersonalBoard().getActiveLeader().getCards()){
            d.print();
        }
    }


    /**
     * Method printMarket calls Market.print
     * @see Market
     */

    public void printMarket(){
        this.game.getBoard().getMarket().print();
    }

    /**
     * Method printFaithTrack calls ShelfWarehouse.print of the selected Player.
     * @param ID is the ID of the selected player.
     * @see ShelfWarehouse
     */

    public void printShelves(int ID)  {
        this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().print();
    }

    /**
     * Method printStrongbox calls Strongbox.print of the selected Player.
     * @param ID is the ID of the selected player.
     * @see Strongbox
     */
    public void printStrongbox(int ID)  {
        this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().print();
    }

    /**
     * Method printDevMatrix calls every first DevCard in every DevDeck
     * @see DevCard
     * @see DevDeck
     */

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

    /**
     * Method printProd calls every print of Cards bought by the selected Player.
     * @param ID is the ID of the selected player.
     * @see DevDeck
     * @see DevCard
     */

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

    /**
     * Method printPlayers prints every Player with his ID.
     * @param ID is the ID of the player who asked the print.
     */

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

