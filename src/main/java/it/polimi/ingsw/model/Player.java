package it.polimi.ingsw.model;

/**
 * Class Player represents the player
 *
 * @author Lea Zancani
 */

public class Player {
    private int id;
    private String nickname;
    private int faithMarkerPos;
    private boolean inkwell;
    private LeaderDeck leaderDeck;
    private int victoryPoints;
    private ResourceType resDiscount1;
    private ResourceType resDiscount2;
    private ResourceType whiteConversion1;

    public void setFaithMarkerPos(int faithMarkerPos) {
        this.faithMarkerPos = faithMarkerPos;
    }

    public void setInkwell(boolean inkwell) {
        this.inkwell = inkwell;
    }

    public void setPersonalBoard(PersonalBoard personalBoard) {
        this.personalBoard = personalBoard;
    }

    private ResourceType whiteConversion2;
    private ResourceType inputExtraProduction1;
    private ResourceType inputExtraProduction2;
    private boolean vaticanSection;
    private PersonalBoard personalBoard;

    public Player(String nickname, int id){
        this.nickname = nickname;
        this.id=id;
    }

    public Player(int id, String nickname, int faithMarkerPos, boolean inkwell, LeaderDeck leaderDeck, int victoryPoints, ResourceType resDiscount1, ResourceType resDiscount2, ResourceType whiteConversion1, ResourceType whiteConversion2, ResourceType inputExtraProduction1, ResourceType inputExtraProduction2, boolean vaticanSection, PersonalBoard personalBoard) {
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

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void moveFaithMarkerPos(int q) {
        this.faithMarkerPos += q;
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

    public boolean checkVaticanSection() {
        return vaticanSection;
    }

    public boolean isVaticanSection() {
        return vaticanSection;
    }

    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }
}
