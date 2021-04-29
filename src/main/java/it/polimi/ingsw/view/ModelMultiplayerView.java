package it.polimi.ingsw.view;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.message.ActionMessages.*;
import it.polimi.ingsw.message.CommonMessages.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Market;
import it.polimi.ingsw.model.ShelfWarehouse;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.SocketClientConnection;

public class ModelMultiplayerView extends Observable<Message> implements Observer<Message> {

    private Game game;


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

    public void sendNotify(Message message) {
        notify(message);
    }

    public void sendNotify(ManageResourceMessage message)  {
        notify(message);
    }

    public void sendNotify(MarketMessage message)  {
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
        notify(message);

    }

    @Override
    public void update(OutputMessage message) {

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

    }

    @Override
    public void update(ManageResourceMessage message) {

    }

    @Override
    public void update(MarketMessage message) {

    }

    @Override
    public void update(ResourceListMessage message)  {

    }


}

