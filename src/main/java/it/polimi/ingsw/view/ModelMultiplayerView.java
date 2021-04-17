package it.polimi.ingsw.view;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.SocketClientConnection;

public class ModelMultiplayerView extends RemoteView implements Observer<Message> {

    public ModelMultiplayerView() {}

    private void lastchance() {
        notify(new Nickname("tutt ok, scrivi pure il numero:", 00));
    }

    public void sendNotify(Message message) {
        notify(message);
    }

    public ModelMultiplayerView(SocketClientConnection c, int ID) {
        super(c, ID);
    }

    public void iKnowMyNickname(String name) {
        notify(new Nickname("I know you Nickname! it's " + name, 0));
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
}

