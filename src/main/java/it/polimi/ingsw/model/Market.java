package it.polimi.ingsw.model;


import it.polimi.ingsw.constants.Ball;
import it.polimi.ingsw.constants.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class Market represents the matrix of trays used by each player
 *
 * @author Simone Tagliente
 * @see Marble
 */

public class Market implements Printable, Serializable {
    private Marble[][] marketBoard;
    private Marble marbleOut;
    private ArrayList<Marble> hand;

    public ArrayList<Marble> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Marble> hand) {
        this.hand = hand;
    }

    public Market(Marble[][] marketBoard, Marble trayOut) {
        this.marketBoard = marketBoard;
        this.marbleOut = trayOut;
        hand = new ArrayList<>();
    }

    public Marble[][] getMarketBoard() {
        return marketBoard;
    }

    public Market() {
        marketBoard = new Marble[3][4];
        marbleOut = new Marble(null);
        hand= new ArrayList<>();

    }

    public Marble getMarbleOut() {
        return marbleOut;
    }

    public void setMarbleOut(Marble marbleOut) {
        this.marbleOut = marbleOut;
    }

    public ArrayList<Marble> getRow(int index) {
        return new ArrayList<>(Arrays.asList(this.marketBoard[index]));
    }

    public ArrayList<Marble> getColumn(int index) {

        ArrayList<Marble> column = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            column.add(marketBoard[i][index]);
        }
        return column;
    }

    public void setMarble(int r, int c, Marble m) {
        this.marketBoard[r][c] = m;
    }

    /**
     * Method print prints to console a MarketBoard instance.
     */

    @Override
    public void print() {

        for (int i=0; i<3; i++)  {
            printRow(Arrays.asList(this.marketBoard[i]));
            System.out.println();

        }
        System.out.print("Left out marble is ");
        printLeftOut();
    }

    /**
     * Method printLeftOut prints to console Left Out Marble.
     */

    private void printLeftOut() {
        String color = this.marbleOut.getRes().getColor();
        ArrayList<Ball> b= new ArrayList<>(Arrays.asList(Ball.values()));
        for (Ball ball : b)  {
            if (ball.toString().equals("Ball1"))  {
            System.out.println(color +  ball.getBallLine() + Color.ANSI_RESET);
        }
        else {
                System.out.println(color + "                   " +  ball.getBallLine() + Color.ANSI_RESET);
            }
        }
        System.out.println();
    }

    /**
     * Method printRow prints to console a row of Market Board.
     *
     */

    private void printRow(List<Marble> asList) {
        ArrayList<Ball> b = new ArrayList<>(Arrays.asList(Ball.values()));
        for (Ball ball : b) {
            String ballLine = ball.getBallLine();
            System.out.println(asList.get(0).getRes().getColor() + ballLine +
                    asList.get(1).getRes().getColor() + ballLine +
                    asList.get(2).getRes().getColor() + ballLine +
                    asList.get(3).getRes().getColor() + ballLine + Color.ANSI_RESET);
        }
    }

    /**
     * Method clone creates a deep copy of a Market instance.
     * @return a deep copy of a Market instance.
     */

    public Market clone() {

        Market clone = new Market();
        clone.marbleOut = this.marbleOut;
        clone.hand = new ArrayList<>();
        clone.hand.addAll(this.hand);
        clone.marketBoard = new Marble[3][4];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(this.marketBoard[i], 0, clone.marketBoard[i], 0, 4);
        }
        return clone;

    }

}



















