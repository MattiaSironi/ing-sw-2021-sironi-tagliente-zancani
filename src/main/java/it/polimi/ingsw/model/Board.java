package it.polimi.ingsw.model;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Class Board represents the main game board of "Master of Renaissance. It's composed by the Market, the Leader Deck, the matrix of Development Cards and the pile of Solo
 * Action Token if the game is single player.
 *
 * @author Simone Tagliente
 */

public class Board {
    private Market market;
    private LeaderDeck leaderDeck;
    private ArrayList<DevDeck> devDecks;
    private ArrayList<SoloActionToken> tokenArray;

    public Board() { //for now just for multiplayer
        market = createMarket();
        devDecks= createDevDecks();
    }

    private Market createMarket() {
        Gson gson = new Gson();
        Reader reader =  new InputStreamReader(Board.class.getResourceAsStream("/json/tray.json"));
        Marble[]  marbles  = gson.fromJson(reader, Marble[].class);
        Collections.shuffle(Arrays.asList(marbles));
        Marble[][] marbleTray = new Marble[3][4];
        for (int i=0, k=0; i<3; i++)  {
            for (int j=0 ; j<4 ; j++ )  {
                marbleTray[i][j]= marbles[k];
                k++;
            }
        }

        Market m = new Market(marbleTray, marbles[12]);

        return m;


    }

    public ArrayList<DevDeck> createDevDecks()  {
        return null;
    }



    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public LeaderDeck getLeaderDeck() {
        return leaderDeck;
    }

    public void setLeaderDeck(LeaderDeck leaderDeck) {
        this.leaderDeck = leaderDeck;
    }

    public ArrayList<DevDeck> getDevDecks() {
        return devDecks;
    }

    public void setDevDecks(ArrayList<DevDeck> devDecks) {
        this.devDecks = devDecks;
    }

    public ArrayList<SoloActionToken> getTokenArray() {
        return tokenArray;
    }

    public void setTokenArray(ArrayList<SoloActionToken> tokenArray) {
        this.tokenArray = tokenArray;
    }


    public Board(Market market, LeaderDeck leaderDeck, ArrayList<DevDeck> devDecks, ArrayList<SoloActionToken> tokenArray) {
        this.market = market;
        this.leaderDeck = leaderDeck;
        this.devDecks = devDecks;
        this.tokenArray = tokenArray;
    }

    public void printDevMatrix(){

    }


}

