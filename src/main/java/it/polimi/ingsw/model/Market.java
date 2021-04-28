package it.polimi.ingsw.model;


import it.polimi.ingsw.message.ActionMessages.ObjectMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Class Market represents the matrix of trays used by each player
 *
 * @author Simone Tagliente
 * @see Marble
 */

public class Market implements Printable {
    private Marble[][] marketBoard;
    private Marble marbleOut;

    public Market(Marble[][] marketBoard, Marble trayOut) {
        this.marketBoard = marketBoard;
        this.marbleOut = trayOut;
    }

    public Market() {
        marketBoard = new Marble[3][4];
        marbleOut = new Marble();

    }

    public Marble[][] getMarketBoard() {
        return marketBoard;
    }

    public Marble getMarbleOut() {
        return marbleOut;
    }

    public void setMarketBoard(Marble[][] marketBoard) {
        this.marketBoard = marketBoard;
    }

    public void setMarbleOut(Marble marbleOut) {
        this.marbleOut = marbleOut;
    }

    public ArrayList<Marble> getRow(int index) {
        ArrayList<Marble> row = new ArrayList<Marble>(Arrays.asList(this.marketBoard[index]));
        return row;
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

    @Override
    public void print() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(marketBoard[i][j].getRes() + "    ");
            }
            System.out.println("");
        }
        System.out.println("left out marble is " + this.marbleOut.getRes());
    }
}






    /*---------------------LOCAL VERSION OF SHIFT METHOD. IMPLEMENTED IN Controller.java ------------------*/


    //    public void shift(boolean row, int index, int id) {
//        ArrayList<Marble> resources = new ArrayList<>();
//
//        if (row)  {
//            resources.addAll(Arrays.asList(this.marketBoard[index]));
//
//            for (int j=3; j>=0 ; j--)  {
//                if (j==3)  {
//                    this.marketBoard[index][j]= marbleOut;
//
//                }
//                else  {
//                    this.marketBoard[index][j]= resources.get(j+1);
//                }
//
//
//            }
//            marbleOut= resources.get(0);
//
//
//        }
//        else {
//            index = 3 - index;
//            for (int i=0; i<3; i++)  {
//                resources.add(marketBoard[i][index]);
//
//            }
//
//            for (int k = 2; k >=0; k-- )  {
//                if (k==2) this.marketBoard[k][index]=marbleOut;
//                else this.marketBoard[k][index]= resources.get(k+1);
//            }
//            marbleOut= resources.get(0);
//        }



















