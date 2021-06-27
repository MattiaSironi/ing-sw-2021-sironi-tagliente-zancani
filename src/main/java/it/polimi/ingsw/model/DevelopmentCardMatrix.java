package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class DevelopmentCardMatrix represents the matrix of development cards of the game. I has also other attributes which are used as temporary containers and slots
 * for actions performed by the player. devDecks attribute is the matrix of development cards, chosenCard is the card chosen by a player during the buyDevCard action, chosenIndex
 * is the index of the chosen card and resToPay is a container used for any payment phase
 */

public class DevelopmentCardMatrix implements Serializable {

    private final List<DevDeck> devDecks;
    private DevCard chosenCard;
    private int chosenIndex;
    private ArrayList<ResourceType> resToPay;

    public DevelopmentCardMatrix(List<DevDeck> devDecks) {
        this.devDecks = devDecks;
        resToPay = new ArrayList<>();
    }

    public List<DevDeck> getDevDecks() {
        return devDecks;
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

    public ArrayList<ResourceType> getResToPay() {
        return resToPay;
    }

    public void setResToPay(ArrayList<ResourceType> resToPay) {
        this.resToPay = resToPay;
    }

    /**
     * This method makes a deep copy of an entire DevelopmentCardMatrix instance.
     * @return a deep clone of the  DevelopmentCardMatrix instance.
     */

    public DevelopmentCardMatrix clone()  {
        DevelopmentCardMatrix clone = new DevelopmentCardMatrix(new ArrayList<>());
        clone.chosenIndex = chosenIndex;
        for (DevDeck d : devDecks) clone.devDecks.add(d.clone());
        if (chosenCard!= null) clone.chosenCard= chosenCard;
        else clone.chosenCard = null;
        if (resToPay==null) clone.resToPay= null;
        else {
            clone.resToPay = new ArrayList<>();
            clone.resToPay.addAll(resToPay);
        }
        return clone;
    }

}
