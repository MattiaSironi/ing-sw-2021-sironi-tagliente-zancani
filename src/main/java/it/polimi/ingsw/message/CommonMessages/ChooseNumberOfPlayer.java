package it.polimi.ingsw.message.CommonMessages;

import it.polimi.ingsw.message.Message;

public class ChooseNumberOfPlayer extends Message {
    private int numPlayers;


    public ChooseNumberOfPlayer(int num) {
        this.numPlayers = num;
    }

    public int getNumberOfPlayers() {
        return numPlayers;
    }

}
