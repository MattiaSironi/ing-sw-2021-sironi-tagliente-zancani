package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Player {
    private int id;
    private final String nickname;
    private int faithMarkerPos;
    private final boolean inkwell;
    private LeaderDeck leaderDeck;
    private int victoryPoints;
    private Resource resDiscount1;
    private Resource resDiscount2;
    private Resource whiteConversion1;
    private Resource whiteConversion2;
    private Resource inputExtraProduction1;
    private Resource inputExtraProduction2;
    private boolean vaticanSection;
    private final PersonalBoard personalBoard;

    public Player(int id, String nickname, int faithMarkerPos, boolean inkwell, LeaderDeck leaderDeck, int victoryPoints, Resource resDiscount1, Resource resDiscount2, Resource whiteConversion1, Resource whiteConversion2, Resource inputExtraProduction1, Resource inputExtraProduction2, boolean vaticanSection, PersonalBoard personalBoard) {
        this.id = id;
        this.nickname = nickname;
        this.faithMarkerPos = faithMarkerPos;
        this.inkwell = inkwell;
        this.leaderDeck = leaderDeck;
        this.victoryPoints = victoryPoints;
        this.resDiscount1 = resDiscount1;
        this.resDiscount2 = resDiscount2;
        this.whiteConversion1 = whiteConversion1;
        this.whiteConversion2 = whiteConversion2;
        this.inputExtraProduction1 = inputExtraProduction1;
        this.inputExtraProduction2 = inputExtraProduction2;
        this.vaticanSection = vaticanSection;
        this.personalBoard = personalBoard;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void moveFaithMarkerPos(int faithMarkerPos) {
        this.faithMarkerPos = faithMarkerPos;
    }

    public void setLeaderDeck(LeaderDeck leaderDeck) {
        this.leaderDeck = leaderDeck;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public void setResDiscount1(Resource resDiscount1) {
        this.resDiscount1 = resDiscount1;
    }

    public void setResDiscount2(Resource resDiscount2) {
        this.resDiscount2 = resDiscount2;
    }

    public void setWhiteConversion1(Resource whiteConversion1) {
        this.whiteConversion1 = whiteConversion1;
    }

    public void setWhiteConversion2(Resource whiteConversion2) {
        this.whiteConversion2 = whiteConversion2;
    }

    public void setInputExtraProduction1(Resource inputExtraProduction1) {
        this.inputExtraProduction1 = inputExtraProduction1;
    }

    public void setInputExtraProduction2(Resource inputExtraProduction2) {
        this.inputExtraProduction2 = inputExtraProduction2;
    }

    public void setVaticanSection(boolean vaticanSection) {
        this.vaticanSection = vaticanSection;
    }

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public int getFaithMarkerPos() {
        return faithMarkerPos;
    }

    public boolean isInkwell() {
        return inkwell;
    }

    public LeaderDeck getLeaderDeck() {
        return leaderDeck;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public Resource getResDiscount1() {
        return resDiscount1;
    }

    public Resource getResDiscount2() {
        return resDiscount2;
    }

    public Resource getWhiteConversion1() {
        return whiteConversion1;
    }

    public Resource getWhiteConversion2() {
        return whiteConversion2;
    }

    public Resource getInputExtraProduction1() {
        return inputExtraProduction1;
    }

    public Resource getInputExtraProduction2() {
        return inputExtraProduction2;
    }

    public boolean checkVaticanSection() {
        return vaticanSection;
    }
}
