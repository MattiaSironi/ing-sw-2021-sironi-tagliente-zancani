package it.polimi.ingsw.view;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.ClientConnection;

public class RemoteView extends Observable<String> implements Observer<Nickname> {
    private ClientConnection clientConnection;
    private int ID;

    @Override
    public void update(Nickname message) {
        if(message.getID() == this.ID){
            clientConnection.asyncSend("Your nickname is " + message.getNickname());
        }
        else{
            clientConnection.asyncSend("One of your opponents nickname is " + message.getNickname());
        }
    }

    private class MessageReceiver implements Observer<String> {

        @Override
        public void update(String message) {
            System.out.println("Received: " + message);
            handleAction(message);

        }
    }

    public RemoteView(ClientConnection c, int ID) {
        this.clientConnection = c;
        this.ID = ID;
        c.addObserver(new MessageReceiver());
    }

    public void handleAction(String message){
        notify(message);
    }
}
