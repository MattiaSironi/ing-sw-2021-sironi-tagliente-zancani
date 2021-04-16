package it.polimi.ingsw.view;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.message.Nickname;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.ClientConnection;

public class ModelMultiplayerView extends RemoteView implements Observer<Message> {

    public ModelMultiplayerView() {}
    private void lastchance() {
        notify(new Nickname("tutt ok, scrivi pure il numero:", 00));
    }

    public void sendnotify(String c) {
        notify(new Nickname(c, 00));
    }

    public ModelMultiplayerView(ClientConnection c, int ID) {
        super(c, ID);
    }

    public void iKnowMyNickname(String name) {
        notify(new Nickname("I know you Nickname! it's " + name, 0));
    }
}

