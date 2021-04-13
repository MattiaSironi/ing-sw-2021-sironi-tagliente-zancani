package it.polimi.ingsw.view;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.ClientConnection;

public class RemoteView extends Observable<Message> implements Observer<Message> {
    public RemoteView() {

    }

    public ClientConnection getClientConnection() {
        return clientConnection;
    }

    public void setClientConnection(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    private ClientConnection clientConnection;
    private int ID;

    @Override
    public void update(Message message) {
        if (message.getString().equals("ERRORE505")) {
            if (message.getID() == this.ID)
                clientConnection.asyncSend("This nickname is already chosen!\n");
        } else if (message.getID() == this.ID) {
            clientConnection.asyncSend("Your nickname is " + message.getString());
        } else {
            clientConnection.asyncSend("One of your opponents nickname is " + message.getString());
        }
    }

    @Override
    public void update(Nickname message) {
        if (message.getString().equals("ERRORE505")) {
            if (message.getID() == this.ID)
                clientConnection.asyncSend("This nickname is already chosen!\n");
        } else if (message.getID() == this.ID) {
            clientConnection.asyncSend("Your nickname is " + message.getString());
        } else {
            clientConnection.asyncSend("One of your opponents nickname is " + message.getString());
        }
    }

    private class MessageReceiver implements Observer<Message> {

        @Override
        public void update(Message message) {
            System.out.println("Received: " + message.getString() + " send by " + message.getID());
            handleAction(message);

        }

        @Override
        public void update(Nickname message) {
            System.out.println("Received a nickname: " + message.getString() + " send by " + message.getID());
            handleAction(message);
        }
    }

    public RemoteView(ClientConnection c, int ID) {
        this.clientConnection = c;
        this.clientConnection.setID(ID);
        this.ID = ID;
        c.addObserver(new MessageReceiver());
    }

    public void handleAction(Message message) {
        notify(new Nickname(message.getString(), message.getID()));
    }
}
