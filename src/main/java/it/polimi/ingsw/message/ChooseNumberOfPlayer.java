package it.polimi.ingsw.message;

public class ChooseNumberOfPlayer extends Message {
    private int numPlayers;


    public ChooseNumberOfPlayer(int num) {
        this.numPlayers = num;
    }

    public int getNumberOfPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int num) {

    }
}
