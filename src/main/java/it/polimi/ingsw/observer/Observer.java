package it.polimi.ingsw.observer;

import it.polimi.ingsw.message.*;


public interface Observer<Message> {

    void update(Message message);

    void update(Nickname message);

    void update(InputMessage message);

    void update(IdMessage message);

    void update(ErrorMessage message);

    void update(OutputMessage message);

    void update (ChooseNumberOfPlayer message);

    void update (PrintableMessage message);
}
