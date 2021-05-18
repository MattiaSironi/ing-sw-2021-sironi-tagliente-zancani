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
    private int id;
    private String nickname;
    private boolean inkwell;
    private LeaderDeck leaderDeck;
    private int victoryPoints;
    private ResourceType resDiscount1;
    private ResourceType resDiscount2;
    private ResourceType whiteConversion1;
    private int actionPhase;
    private ResourceType whiteConversion2;
    private ResourceType inputExtraProduction1;
    private ResourceType inputExtraProduction2;
    private boolean vaticanSection;
    private PersonalBoard personalBoard;



    public void setInkwell(boolean inkwell) {
        this.inkwell = inkwell;
    }

    public void setPersonalBoard(PersonalBoard personalBoard) {
        this.personalBoard = personalBoard;
    }

    public Player(int id, String nickname){
        this.id=id;
        this.nickname = nickname;
        this.personalBoard = new PersonalBoard();
        this.leaderDeck = new LeaderDeck(0,0,new ArrayList<>());
        this.actionPhase = 0;
    }


    public Player(int id, String nickname, boolean inkwell, LeaderDeck leaderDeck, int victoryPoints, ResourceType resDiscount1, ResourceType resDiscount2, ResourceType whiteConversion1, ResourceType whiteConversion2, ResourceType inputExtraProduction1, ResourceType inputExtraProduction2, boolean vaticanSection, PersonalBoard personalBoard) {
        this.id = id;
        this.nickname = nickname;
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

    public Player(boolean ready, int startResCount, int id, String nickname, boolean inkwell,
                  LeaderDeck leaderDeck, int victoryPoints, ResourceType resDiscount1, ResourceType resDiscount2,
                  ResourceType whiteConversion1, int actionPhase, ResourceType whiteConversion2,
                  ResourceType inputExtraProduction1, ResourceType inputExtraProduction2, boolean vaticanSection,
                  PersonalBoard personalBoard) {
        this.ready = ready;
        this.startResCount = startResCount;
        this.id = id;
        this.nickname = nickname;
        this.inkwell = inkwell;
        this.leaderDeck = leaderDeck;
        this.victoryPoints = victoryPoints;
        this.resDiscount1 = resDiscount1;
        this.resDiscount2 = resDiscount2;
        this.whiteConversion1 = whiteConversion1;
        this.actionPhase = actionPhase;
        this.whiteConversion2 = whiteConversion2;
        this.inputExtraProduction1 = inputExtraProduction1;
        this.inputExtraProduction2 = inputExtraProduction2;
        this.vaticanSection = vaticanSection;
        this.personalBoard = personalBoard;
    }


    public Player clone(){
        Player p = new Player(this.ready,this.startResCount,this.id,this.nickname,this.inkwell,
                this.leaderDeck,this.victoryPoints, this.resDiscount1,this.resDiscount2,this.whiteConversion1,this.actionPhase,
                this.whiteConversion2,this.inputExtraProduction1,this.inputExtraProduction2,this.vaticanSection,this.personalBoard);
        return p;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public int getActionPhase() {
        return actionPhase;
    }

    public void setActionPhase(int actionPhase) {
        this.actionPhase = actionPhase;
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
}
