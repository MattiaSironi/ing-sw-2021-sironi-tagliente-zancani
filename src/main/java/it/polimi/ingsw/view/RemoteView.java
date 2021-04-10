package it.polimi.ingsw.view;

import it.polimi.ingsw.MessageReceiver.MessageReceiver;
import it.polimi.ingsw.server.ClientConnection;

public class RemoteView {
    private ClientConnection clientConnection;
    private MessageReceiver messageReceiver;

    public RemoteView(ClientConnection clientConnection, MessageReceiver messageReceiver) {
        this.clientConnection = clientConnection;
        this.messageReceiver = messageReceiver;
    }
}
