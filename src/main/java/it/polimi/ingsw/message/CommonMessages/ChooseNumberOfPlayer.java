package it.polimi.ingsw.message.CommonMessages;

import it.polimi.ingsw.message.Message;

/**
 * ChooseNumberOfPlayer Message is sent from Server to Client if the selected Client has to choose how many players will play.
 *
 */

public class ChooseNumberOfPlayer extends Message {
    private int numPlayers;


    public ChooseNumberOfPlayer(int num) {
        this.numPlayers = num;
    }

    public int getNumberOfPlayers() {
        return numPlayers;
    }

}
