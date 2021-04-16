package it.polimi.ingsw;

import it.polimi.ingsw.MessageReceiver.ClientMessageReceiver;
import it.polimi.ingsw.view.CLI;
import it.polimi.ingsw.view.ModelMultiplayerView;

public class ClientMain {
    public static void main(String[] args) {
        CLI cli = new CLI();
        ModelMultiplayerView view = new ModelMultiplayerView();
        ClientMessageReceiver cms = new ClientMessageReceiver(view);
        view.addObserver(cli);
        cli.addObserver(cms);
        cli.run();
    }
}