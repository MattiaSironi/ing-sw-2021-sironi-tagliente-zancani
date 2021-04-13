package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.view.CLI;
import it.polimi.ingsw.view.ModelMultiplayerView;
import it.polimi.ingsw.view.RemoteView;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) {
        CLI cli = new CLI();
        ModelMultiplayerView view = new ModelMultiplayerView(cli);
        view.addObserver(cli);

        cli.run();
    }
}