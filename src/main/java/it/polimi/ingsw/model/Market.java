package it.polimi.ingsw.model;



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

    public Marble getTrayOut() {
        return marbleOut;
    }

    public void setMarketBoard(Marble[][] marketBoard) {
        this.marketBoard = marketBoard;
    }

    public void setTrayOut(Marble trayOut) {
        this.marbleOut = trayOut;
    }

    @Override
    public void print() {
        for (int i=0; i<3; i++)  {
            for (int j=0; j<4; j++)  {
                System.out.print(marketBoard[i][j].getRes() + "    ");
            }
            System.out.println("");
        }
        System.out.println("left out marble is " + this.marbleOut.getRes());
    }
}
