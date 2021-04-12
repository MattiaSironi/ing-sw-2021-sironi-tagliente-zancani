package it.polimi.ingsw.observer;

public interface Observer<Message> {

    void update(Message message);

}
