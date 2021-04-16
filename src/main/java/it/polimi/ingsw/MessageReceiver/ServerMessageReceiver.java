package it.polimi.ingsw.MessageReceiver;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.message.Nickname;
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
        System.out.println("Received: " + message.getString() + " send by " + message.getID());
        rv.handleAction(message);

    }

    @Override
    public void update(Nickname message) {
        System.out.println("Received a nickname: " + message.getString() + " send by " + message.getID());
        rv.handleAction(message);
    }
}

