package it.polimi.ingsw.server;

import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.Nickname;
import it.polimi.ingsw.view.RemoteView;

public interface ClientConnection{

    void closeConnection();



    void asyncSend(Object message);

    void setID(int ID);

    void addObserver(Observer<Nickname> observer);
}
