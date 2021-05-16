package it.polimi.ingsw.model;

import java.util.List;

public class DevelopmentCardMatrix {

    private List<DevDeck> devDecks;
    private DevCard chosenCard;
    private ErrorList error;

    public DevelopmentCardMatrix(List<DevDeck> devDecks, DevCard chosenCard) {
        this.devDecks = devDecks;
        this.chosenCard = chosenCard;
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

    public ErrorList getError() {
        return error;
    }

    public void setError(ErrorList error) {
        this.error = error;
    }
}
