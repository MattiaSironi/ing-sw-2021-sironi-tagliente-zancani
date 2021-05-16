package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.List;

public class DevelopmentCardMatrix implements Serializable {

    private List<DevDeck> devDecks;
    private DevCard chosenCard;
    private int chosenIndex;

    public DevelopmentCardMatrix(List<DevDeck> devDecks) {
        this.devDecks = devDecks;
    }

    public List<DevDeck> getDevDecks() {
        return devDecks;
    }

    public void setDevDecks(List<DevDeck> devDecks) {
        this.devDecks = devDecks;
    }

    public DevCard getChosenCard() {
        return chosenCard;
    }

    public void setChosenCard(DevCard chosenCard) {
        this.chosenCard = chosenCard;
    }

    public int getChosenIndex() {
        return chosenIndex;
    }

    public void setChosenIndex(int chosenIndex) {
        this.chosenIndex = chosenIndex;
    }

    public void printDevMatrix(){
        for(DevDeck dd : devDecks){
            dd.print();
        }
    }
}
