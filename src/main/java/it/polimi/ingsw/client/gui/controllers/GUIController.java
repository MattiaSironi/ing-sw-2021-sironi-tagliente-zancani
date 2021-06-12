package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.model.Communication;
import it.polimi.ingsw.model.Turn;

public interface GUIController {
    
    void setMainController(MainController m);

    void setup(int num);

    void print(Turn turn);

    void print(Communication communication);

    void disable();

    void enable();
}
