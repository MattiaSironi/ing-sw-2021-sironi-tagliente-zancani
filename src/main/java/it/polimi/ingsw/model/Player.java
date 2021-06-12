package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class Player represents the player
 *
 * @author Lea Zancani
 */

public class Player implements Serializable, Cloneable{
    private boolean ready = false;
    private int startResCount;
    private int leaderCardsToDiscard;
    private int id;
    private String nickname;
    private LeaderDeck leaderDeck;
    private int victoryPoints;
    private ResourceType resDiscount1;
    private ResourceType resDiscount2;
    private ResourceType whiteConversion1;
    private ResourceType whiteConversion2;
    private ResourceType inputExtraProduction1;
    private ResourceType inputExtraProduction2;
    private PersonalBoard personalBoard;
    private boolean basicProdDone;
    private boolean leader1ProdDone;
    private boolean leader2ProdDone;
    private boolean dev1ProdDone;
    private boolean dev2ProdDone;
    private boolean dev3ProdDone;


    public int getLeaderCardsToDiscard() {
        return leaderCardsToDiscard;
    }

    public void setLeaderCardsToDiscard(int leaderCardsToDiscard) {
        this.leaderCardsToDiscard = leaderCardsToDiscard;
    }

    public void setPersonalBoard(PersonalBoard personalBoard) {
        this.personalBoard = personalBoard;
    }

    public Player(int id, String nickname){
        this.id=id;
        this.nickname = nickname;
        this.personalBoard = new PersonalBoard();
        this.leaderDeck = new LeaderDeck(new ArrayList<>());
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLeaderDeck(LeaderDeck leaderDeck) {
        this.leaderDeck = leaderDeck;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public void setResDiscount1(ResourceType resDiscount1) {
        this.resDiscount1 = resDiscount1;
    }

    public void setResDiscount2(ResourceType resDiscount2) {
        this.resDiscount2 = resDiscount2;
    }

    public void setWhiteConversion1(ResourceType whiteConversion1) {
        this.whiteConversion1 = whiteConversion1;
    }

    public void setWhiteConversion2(ResourceType whiteConversion2) {
        this.whiteConversion2 = whiteConversion2;
    }

    public void setInputExtraProduction1(ResourceType inputExtraProduction1) {
        this.inputExtraProduction1 = inputExtraProduction1;
    }

    public void setInputExtraProduction2(ResourceType inputExtraProduction2) {
        this.inputExtraProduction2 = inputExtraProduction2;
    }

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public LeaderDeck getLeaderDeck() {
        return leaderDeck;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public ResourceType getResDiscount1() {
        return resDiscount1;
    }

    public ResourceType getResDiscount2() {
        return resDiscount2;
    }

    public ResourceType getWhiteConversion1() {
        return whiteConversion1;
    }

    public ResourceType getWhiteConversion2() {
        return whiteConversion2;
    }

    public ResourceType getInputExtraProduction1() {
        return inputExtraProduction1;
    }

    public ResourceType getInputExtraProduction2() {
        return inputExtraProduction2;
    }

    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }

    public int sumDevs() {
        int value = 0;
        for (DevDeck dd : this.getPersonalBoard().getCardSlot()) {
            for (DevCard dc : dd.getCards()) {
                value += dc.getVictoryPoints();
            }
        }
        return value;
    }

    public int sumLeads() {
        int value = 0;
        for (LeaderCard lc : this.personalBoard.getActiveLeader().getCards()) {
            value += lc.getVictoryPoints();
        }
        return value;
    }

    public int sumPope() {

        return ((personalBoard.getFaithTrack().getFavorTile1() * 2) + (personalBoard.getFaithTrack().getFavorTile2() * 3) + (personalBoard.getFaithTrack().getFavorTile3() * 4));

    }

    public int getValuePos() {
        return this.getPersonalBoard().getFaithTrack().getFaithTrackSlot().get(this.getPersonalBoard().getFaithTrack().getMarker()).getCurrentVictoryPoints();
    }

    public int getValueResources() {
        return (this.personalBoard.getStrongbox().numberOfResources() + this.personalBoard.getWarehouse().numberOfResources());
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public int getStartResCount() {
        return startResCount;
    }

    public void setStartResCount(int startResCount) {
        this.startResCount = startResCount;
    }

    public boolean isBasicProdDone() {
        return basicProdDone;
    }

    public void setBasicProdDone(boolean basicProdDone) {
        this.basicProdDone = basicProdDone;
    }

    public boolean isLeader1ProdDone() {
        return leader1ProdDone;
    }

    public void setLeader1ProdDone(boolean leader1ProdDone) {
        this.leader1ProdDone = leader1ProdDone;
    }

    public boolean isLeader2ProdDone() {
        return leader2ProdDone;
    }

    public void setLeader2ProdDone(boolean leader2ProdDone) {
        this.leader2ProdDone = leader2ProdDone;
    }

    public boolean isDev1ProdDone() {
        return dev1ProdDone;
    }

    public void setDev1ProdDone(boolean dev1ProdDone) {
        this.dev1ProdDone = dev1ProdDone;
    }

    public boolean isDev2ProdDone() {
        return dev2ProdDone;
    }

    public void setDev2ProdDone(boolean dev2ProdDone) {
        this.dev2ProdDone = dev2ProdDone;
    }

    public boolean isDev3ProdDone() {
        return dev3ProdDone;
    }

    public void setDev3ProdDone(boolean dev3ProdDone) {
        this.dev3ProdDone = dev3ProdDone;
    }

    public void setAllFalse(){
        setBasicProdDone(false);
        setLeader1ProdDone(false);
        setLeader2ProdDone(false);
        setDev1ProdDone(false);
        setDev2ProdDone(false);
        setDev3ProdDone(false);
    }

    public Player clone(){
        Player clone = new Player(this.id, this.nickname);
        clone.ready = this.ready;
        clone.startResCount = this.startResCount;
        clone.nickname = this.nickname;
        clone.leaderCardsToDiscard = this.leaderCardsToDiscard;
        clone.victoryPoints = this.victoryPoints;
        clone.resDiscount1 = this.resDiscount1;
        clone.resDiscount2 = this.resDiscount2;
        clone.whiteConversion1 = this.whiteConversion1;
        clone.whiteConversion2 = this.whiteConversion2;
        clone.inputExtraProduction1 = this.inputExtraProduction1;
        clone.inputExtraProduction2 = this.inputExtraProduction2;
        clone.leaderDeck = this.leaderDeck.clone();
        clone.personalBoard = this.personalBoard.clone();
        return clone;

    }
}
