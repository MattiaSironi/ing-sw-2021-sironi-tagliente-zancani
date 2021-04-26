package it.polimi.ingsw;

import it.polimi.ingsw.MessageReceiver.ClientMessageReceiver;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.LorenzoIlMagnifico;
import it.polimi.ingsw.view.CLI;
import it.polimi.ingsw.view.ModelMultiplayerView;

import java.io.IOException;
import java.util.ArrayList;

public class ClientMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        CLI cli = new CLI();
        ModelMultiplayerView view = new ModelMultiplayerView(new Game(0, 0, 0, new ArrayList<>(), new LorenzoIlMagnifico(), new Board()));
        ClientMessageReceiver cms = new ClientMessageReceiver(cli, view);
        view.addObserver(cli);
        cli.addObserver(cms);
        cms.chooseAction();
        //cms.setup();
    }
}