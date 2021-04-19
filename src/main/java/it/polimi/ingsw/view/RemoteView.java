package it.polimi.ingsw.view;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.SocketClientConnection;

public class RemoteView extends Observable<Message> implements Observer<Message> {

    private SocketClientConnection clientConnection;
    private int ID;

    public RemoteView() {}

    public SocketClientConnection getClientConnection() {
        return clientConnection;
    }

    public void setClientConnection(SocketClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }



    @Override
    public void update(Message message) {
    }

    @Override
    public void update(Nickname message) {
        if (message.getID() == this.ID) {
            clientConnection.send(new OutputMessage("Your nickname is " + message.getString()));
        } else {
            clientConnection.send(new OutputMessage("One of your opponents nickname is " + message.getString()));
        }
    }

    @Override
    public void update(InputMessage message) {

    }

    @Override
    public void update(IdMessage message) {

    }

    @Override
    public void update(ErrorMessage message) {
        if(message.getID() == this.ID){
            clientConnection.send(message);
        }
    }

    @Override
    public void update(OutputMessage message) {

    }


    public RemoteView(SocketClientConnection c, int ID) {
        this.clientConnection = c;
        this.clientConnection.setID(ID);
        this.ID = ID;

    }

    public void handleAction(Nickname message) {
        notify(new Nickname(message.getString(), message.getID()));
    }
}
