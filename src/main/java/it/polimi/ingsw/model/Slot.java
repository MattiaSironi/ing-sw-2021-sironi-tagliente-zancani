package it.polimi.ingsw.model;

public class Slot {
    private int posIndex; //apparentemente inutile
    private boolean vaticanReportSection;
    private boolean popeSpace;
    private int currentVictoryPoints;
    private int popeFavor;

    public Slot(int posIndex, boolean vaticanReportSection, boolean popeSpace, int currentVictoryPoints, int popeFavor) {
        this.posIndex = posIndex;
        this.vaticanReportSection = vaticanReportSection;
        this.popeSpace = popeSpace;
        this.currentVictoryPoints = currentVictoryPoints;
        this.popeFavor = popeFavor;
    }

    public int getPosIndex() {
        return posIndex;
    }

    public void setPosIndex(int posIndex) {
        this.posIndex = posIndex;
    }

    public boolean isVaticanReportSection() {
        return vaticanReportSection;
    }

    public void setVaticanReportSection(boolean vaticanReportSection) {
        this.vaticanReportSection = vaticanReportSection;
    }

    public boolean isPopeSpace() {
        return popeSpace;
    }

    public void setPopeSpace(boolean popeSpace) {
        this.popeSpace = popeSpace;
    }

    public int getCurrentVictoryPoints() {
        return currentVictoryPoints;
    }

    public void setCurrentVictoryPoints(int currentVictoryPoints) {
        this.currentVictoryPoints = currentVictoryPoints;
    }

    public int getPopeFavor() {
        return popeFavor;
    }

    public void setPopeFavor(int popeFavor) {
        this.popeFavor = popeFavor;
    }
}
