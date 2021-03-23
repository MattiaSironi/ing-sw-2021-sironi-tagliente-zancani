package it.polimi.ingsw.model;

public class Market {
    private Tray[][] marketBoard;
    private Tray trayOut;

    public Market(Tray[][] marketBoard, Tray trayOut) {
        this.marketBoard = marketBoard;
        this.trayOut = trayOut;
    }

    public Tray[][] getMarketBoard() {
        return marketBoard;
    }

    public Tray getTrayOut() {
        return trayOut;
    }

    public void setMarketBoard(Tray[][] marketBoard) {
        this.marketBoard = marketBoard;
    }

    public void setTrayOut(Tray trayOut) {
        this.trayOut = trayOut;
    }
}