package it.polimi.ingsw.view;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.message.ActionMessages.*;
import it.polimi.ingsw.message.CommonMessages.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.SocketClientConnection;

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

    public Game getGame() {
        return game;
    }

    public void printMarket(){
        notify(new PrintableMessage(this.game.getBoard().getMarket()));
    }

    public void printShelves(int ID)  {
        notify(new PrintableMessage(this.game.getPlayerById(ID).getPersonalBoard().getWarehouse()));
    }

    public void printDevMatrix(){
        int index = 1;
        for(DevDeck d : this.game.getBoard().getDevDecks()){
            System.out.println("Card numeber: " + index);
            index++;
            notify(new PrintableMessage((d.getCards().get(0))));
        }
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
//        notify(message); //socket

        if (message.getString().equals("ko"))  {
            this.cac.getCli().printToConsole("Invalid move. Try another one!");
        }
        else if (!message.getString().equals("ok")) {
            this.cac.getCli().printToConsole(message.getString());
            this.cac.getCli().printToConsole("Try another shelf or discard it!");
            this.cac.askForResource(message.getR());

        }
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
    public void update(ResourceListMessage message) { //just for local testing
        this.game.getPlayerById(0).getPersonalBoard().getWarehouse().print();

        for (Marble m : message.getMarbles()) {


            switch (m.getRes()) {
                case COIN, STONE, SERVANT, SHIELD: {
                    this.cac.getCli().printToConsole("you received a " + m.getRes().printResourceColouredName() + "!");
                    this.cac.askForResource(m.getRes());
                    this.game.getPlayerById(0).getPersonalBoard().getWarehouse().print(); //local TODO
                    break;
                }
                case FAITH_POINT: {
                    cac.getCli().printToConsole("you received a " + m.getRes().printResourceColouredName());
                    break;
                }
                default: {
                    cac.getCli().printToConsole("you got nothing from White tray :(");
                    break;
                } //caso EMPTY, vari controlli!
            }

        }
    }

    @Override
    public void update(PlaceResourceMessage message) {

    }


}

