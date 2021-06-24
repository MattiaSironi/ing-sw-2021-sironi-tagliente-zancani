package it.polimi.ingsw.client;

/**
 * Interface UI is implemented by ClientActionController (CLI) and MainController (GUI).
 * @see it.polimi.ingsw.client.gui.controllers.MainController
 * @see ClientActionController
 */

public interface UI {

    void setID(int ID);

    void handleAction(Object message);

    void disconnect();
}
