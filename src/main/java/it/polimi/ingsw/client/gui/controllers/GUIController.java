package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.model.Communication;
import it.polimi.ingsw.model.Turn;

/**
 * GUIController interface is implemented by every fxml controller.
 */

public interface GUIController {
    
    void setMainController(MainController m);

    /**
     * Method setup prepares scene.
     * @param num is used by OthersPlayersController
     *
     */

    void setup(int num);

    /**
     * Method print writes on a label the Turn state.

     */

    void print(Turn turn);

    /**
     * Method print writes on a label Communications received.

     */

    void print(Communication communication);

    /**
     * Method disable is used to disable part of the scenes when player is not the playing player.
     */

    void disable();

    /**
     * Method enable is used to enable part of the scenes when player is the playing player.
     */

    void enable();
}
