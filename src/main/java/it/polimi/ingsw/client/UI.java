package it.polimi.ingsw.client;

import it.polimi.ingsw.message.Message;

public interface UI {

    void setID(int ID);

    void handleAction(Object message);

}
