package it.polimi.ingsw;

import it.polimi.ingsw.MessageReceiver.ClientMessageReceiver;
import it.polimi.ingsw.view.CLI;
import it.polimi.ingsw.view.ModelMultiplayerView;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        CLI cli = new CLI();
        ModelMultiplayerView view = new ModelMultiplayerView();
        ClientMessageReceiver cms = new ClientMessageReceiver(cli, view);
        view.addObserver(cli);
        cli.addObserver(cms);
        cms.setup();
    }
}