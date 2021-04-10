package it.polimi.ingsw.MessageReceiver;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.ClientConnection;

public class MessageReceiver implements Observer<String> {

    private ClientConnection clientConnection;
    @Override
    public void update(String message) {
        System.out.println("Received: " + message);
        clientConnection.asyncSend("You typed: " + message);
        }

    public MessageReceiver(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }
}
