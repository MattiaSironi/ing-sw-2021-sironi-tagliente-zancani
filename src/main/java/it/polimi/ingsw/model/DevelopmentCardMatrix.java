package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DevelopmentCardMatrix implements Serializable {

    private List<DevDeck> devDecks;
    private DevCard chosenCard;
    private int chosenIndex;
    private ArrayList<ResourceType> resToPay;

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

    public ArrayList<ResourceType> getResToPay() {
        return resToPay;
    }

    public void setResToPay(ArrayList<ResourceType> resToPay) {
        this.resToPay = resToPay;
    }

    public void printDevMatrix(){
        for(DevDeck dd : devDecks){
            dd.print();
        }
    }

    public DevelopmentCardMatrix clone()  {
        DevelopmentCardMatrix clone = new DevelopmentCardMatrix(new ArrayList<>());
        clone.chosenIndex = chosenIndex;
        for (DevDeck d : devDecks) clone.devDecks.add(d.clone());
        if (chosenCard!= null) clone.chosenCard= chosenCard.clone();
        else clone.chosenCard = null;
        clone.resToPay = new ArrayList<>();
        clone.resToPay.addAll(resToPay); //TODO testing for deep copy
        return clone;

    }

}
