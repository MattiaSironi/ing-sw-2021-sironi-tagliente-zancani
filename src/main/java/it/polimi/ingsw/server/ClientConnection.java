package it.polimi.ingsw.server;

import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.message.*;

public interface ClientConnection {

    void closeConnection();

    void asyncSend(Object message);

    void setID(int ID);

    void addObserver(Observer<Message> observer);
}
