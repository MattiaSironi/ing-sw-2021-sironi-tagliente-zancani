package it.polimi.ingsw.model;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class Board represents the main game board of "Master of Renaissance". It's composed by the Market, the Leader Deck, the matrix of Development Cards and the pile of Solo
 * Action Token if the game is single player.
 *
 * @author Simone Tagliente
 */

public class Board implements Serializable {
    private Market market;
    private LeaderDeck leaderDeck;
    private List<DevDeck> devDecks;
    private ArrayList<SoloActionToken> tokenArray;
    private DevelopmentCardMatrix matrix;

    public Board(boolean single) {
        market = createMarket();
        devDecks = createDevDecks();
        leaderDeck = createLeaderDeck();
        matrix = new DevelopmentCardMatrix(devDecks);
        if (single) tokenArray = createTokensArray();
    }

    public Board()  {

    }

    /**
     * This method parses a JSON file containing all possible tokens. It shuffles the tokens.
     * @return a Collection of SoloActionToken shuffled.
     */

    public ArrayList<SoloActionToken> createTokensArray() {
        Gson gson = new Gson();
        ArrayList<SoloActionToken> solo = new ArrayList<>();
        Reader reader = new InputStreamReader(Board.class.getResourceAsStream("/json/solo.json"));
        SoloActionToken[] sat = gson.fromJson(reader, SoloActionToken[].class);
        for (SoloActionToken s : sat) {
            solo.add(s);
            Collections.shuffle(solo);
        }
        return solo;
    }

    /**
     * This method parses four JSON files containing all possible LeaderCards. It shuffles the deck created with these cards.
     * @return a LeaderDeck shuffled.
     */


    private LeaderDeck createLeaderDeck() {
                Gson gson= new Gson();
        LeaderDeck ret = new LeaderDeck(new ArrayList<>());
        Reader reader1 = new InputStreamReader(Board.class.getResourceAsStream("/json/discount.json"));
        DiscountLCard[] deck1= gson.fromJson(reader1, DiscountLCard[].class);
        for (DiscountLCard d : deck1)  {
            ret.getCards().add(d);
        }
        Reader reader2 = new InputStreamReader(Board.class.getResourceAsStream("/json/extrashelf.json"));
        ExtraDepotLCard[] deck2= gson.fromJson(reader2, ExtraDepotLCard[].class);
        for (ExtraDepotLCard d : deck2)  {
            ret.getCards().add(d);
        }
        Reader reader3 = new InputStreamReader(Board.class.getResourceAsStream("/json/extraprod.json"));
        ExtraProdLCard[] deck3= gson.fromJson(reader3, ExtraProdLCard[].class);
        for (ExtraProdLCard d : deck3)  {
            ret.getCards().add(d);
        }
        Reader reader4 = new InputStreamReader(Board.class.getResourceAsStream("/json/whitetray.json"));
        WhiteTrayLCard[] deck4= gson.fromJson(reader4, WhiteTrayLCard[].class);
        for (WhiteTrayLCard d : deck4)  {
            ret.getCards().add(d);
        }
        Collections.shuffle(ret.getCards());

        return ret;
    }

    /**
     * This method parses a JSON file containing all possible marbles. It shuffle them, the first 12 are put in the marketBoard. The last one is put in the left out spot.
     * @return a Market shuffled with 12 marbles in the marketBoard, 0 marbles in marketHand and one marble in the left out spot.
     */

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




        return new Market(marbleTray, marbles[12]);
    }

    /**
     * This method parses a JSON file containing all possible DevCards. it creates 12 decks shuffled. Each card in a deck has the same Color and the same level.
     * @return a Collection of shuffled DevDecks.
     */

    public List<DevDeck> createDevDecks()  {

        Gson gson= new Gson();
        List<DevDeck> devDecks = new ArrayList<>();
        Reader reader = new InputStreamReader(Board.class.getResourceAsStream("/json/devcard.json"));
        DevCard[] allCards = gson.fromJson(reader, DevCard[].class);
        Collections.shuffle(Arrays.asList(allCards));
        DevDeck deck1 = new DevDeck(Arrays.stream(allCards).filter(x -> x.getColor().equals(CardColor.GREEN)
                && x.getLevel()==1).collect(Collectors.toList()));
        devDecks.add(deck1);
        DevDeck deck2 = new DevDeck(Arrays.stream(allCards).filter(x -> x.getColor().equals(CardColor.BLUE)
                && x.getLevel()==1).collect(Collectors.toList()));
        devDecks.add(deck2);
        DevDeck deck3 = new DevDeck(Arrays.stream(allCards).filter(x -> x.getColor().equals(CardColor.YELLOW)
                && x.getLevel()==1).collect(Collectors.toList()));
        devDecks.add(deck3);
        DevDeck deck4 = new DevDeck(Arrays.stream(allCards).filter(x -> x.getColor().equals(CardColor.PURPLE)
                && x.getLevel()==1).collect(Collectors.toList()));
        devDecks.add(deck4);
        DevDeck deck5 = new DevDeck(Arrays.stream(allCards).filter(x -> x.getColor().equals(CardColor.GREEN)
                && x.getLevel()==2).collect(Collectors.toList()));
        devDecks.add(deck5);
        DevDeck deck6 = new DevDeck( Arrays.stream(allCards).filter(x -> x.getColor().equals(CardColor.BLUE)
                && x.getLevel()==2).collect(Collectors.toList()));
        devDecks.add(deck6);
        DevDeck deck7 = new DevDeck(Arrays.stream(allCards).filter(x -> x.getColor().equals(CardColor.YELLOW)
                && x.getLevel()==2).collect(Collectors.toList()));
        devDecks.add(deck7);
        DevDeck deck8 = new DevDeck(Arrays.stream(allCards).filter(x -> x.getColor().equals(CardColor.PURPLE)
                && x.getLevel()==2).collect(Collectors.toList()));
        devDecks.add(deck8);
        DevDeck deck9 = new DevDeck(Arrays.stream(allCards).filter(x -> x.getColor().equals(CardColor.GREEN)
                && x.getLevel()==3).collect(Collectors.toList()));
        devDecks.add(deck9);
        DevDeck deck10 = new DevDeck(Arrays.stream(allCards).filter(x -> x.getColor().equals(CardColor.BLUE)
                && x.getLevel()==3).collect(Collectors.toList()));
        devDecks.add(deck10);
        DevDeck deck11 = new DevDeck( Arrays.stream(allCards).filter(x -> x.getColor().equals(CardColor.YELLOW)
                && x.getLevel()==3).collect(Collectors.toList()));
        devDecks.add(deck11);
        DevDeck deck12 = new DevDeck(Arrays.stream(allCards).filter(x -> x.getColor().equals(CardColor.PURPLE)
                && x.getLevel()==3).collect(Collectors.toList()));
        devDecks.add(deck12);

        return devDecks;

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

    public List<DevDeck> getDevDecks() {
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

    public DevelopmentCardMatrix getMatrix() {
        return matrix;
    }

    public void setMatrix(DevelopmentCardMatrix matrix) {
        this.matrix = matrix;
    }

    /**
     * This method makes a deep copy of the entire Board.
     * @return a deep clone of the Board.
     */

    public Board clone() {
        Board clone = new Board();
        clone.market = market.clone();
        clone.matrix = matrix.clone();
        clone.devDecks = new ArrayList<>();
        for (DevDeck d : this.devDecks) clone.devDecks.add(d.clone());
        clone.leaderDeck = leaderDeck.clone();
        if (tokenArray != null) clone.tokenArray = getTokenArrayClone();
        else clone.tokenArray = null;
        return clone;
    }

    /**
     *  This method makes a deep copy of the Collection of SoloActionTokens.
     * @see Board
     * @return a deep copy of the Collection of SoloActionTokens
     */

    public ArrayList<SoloActionToken> getTokenArrayClone() {
        return new ArrayList<>(this.tokenArray);
    }


}

