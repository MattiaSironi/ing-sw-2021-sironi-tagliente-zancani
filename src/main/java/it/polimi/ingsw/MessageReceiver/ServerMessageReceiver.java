package it.polimi.ingsw.MessageReceiver;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.message.ActionMessages.*;
import it.polimi.ingsw.message.CommonMessages.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.RemoteView;


/**
 * @author Mattia Sironi
 */
public class ServerMessageReceiver implements Observer<Message> {

    private RemoteView rv;

    public ServerMessageReceiver(RemoteView rv) {
        this.rv = rv;
    }

    @Override
    public void update(Message message) {
    }

    @Override
    public void update(Nickname message) {
        System.out.println("Received a nickname: " + message.getString() + " send by " + message.getID());
        rv.handleAction(message);
    }

    @Override
    public void update(InputMessage message) {

    }

    @Override
    public void update(IdMessage message) {

    }

    @Override
    public void update(ErrorMessage message) {

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

    }

    @Override
    public void update(ManageResourceMessage message) {

    }

    @Override
    public void update(MarketMessage message) {
        System.out.println(message.getID() + "wants to go to the market!");
        rv.handleAction(message);

    }

    @Override
    public void update(ResourceListMessage message) {

    }
}

