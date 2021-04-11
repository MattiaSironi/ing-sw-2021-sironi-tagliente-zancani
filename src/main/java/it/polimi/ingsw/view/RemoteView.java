package it.polimi.ingsw.view;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.ClientConnection;

public class RemoteView extends Observable<Nickname> implements Observer<Nickname> {
    private ClientConnection clientConnection;
    private int ID;

    @Override
    public void update(Nickname message) {
        if (message.getNickname().equals("ERRORE505") ) {
            if ( message.getID()==this.ID)
            clientConnection.asyncSend("This nickname is already chosen!\n");
        }
        else if(message.getID() == this.ID){
            clientConnection.asyncSend("Your nickname is " + message.getNickname());
        }
        else{
            clientConnection.asyncSend("One of your opponents nickname is " + message.getNickname());
        }
    }

    private class MessageReceiver implements Observer<Nickname> {

        @Override
        public void update(Nickname message) {
            System.out.println("Received: " + message.getNickname() +" send by " + message.getID() );
            handleAction(message);

        }
    }

    public RemoteView(ClientConnection c, int ID) {
        this.clientConnection = c;
        this.clientConnection.setID(ID);
        this.ID = ID;
        c.addObserver(new MessageReceiver());
    }

    public void handleAction(Nickname message){
        notify(message);
    }
}
