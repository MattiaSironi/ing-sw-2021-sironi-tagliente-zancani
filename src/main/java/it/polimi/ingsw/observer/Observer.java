package it.polimi.ingsw.observer;

import it.polimi.ingsw.message.Nickname;

public interface Observer<Message> {

    void update(Message message);

    void update(Nickname message);

}
