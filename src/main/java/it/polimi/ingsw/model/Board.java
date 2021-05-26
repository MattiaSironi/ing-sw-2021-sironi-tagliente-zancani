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
 * Class Board represents the main game board of "Master of Renaissance. It's composed by the Market, the Leader Deck, the matrix of Development Cards and the pile of Solo
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

    public Board(boolean single) { //for now just for multiplayer
        market = createMarket();
        devDecks = createDevDecks();
        leaderDeck = createLeaderDeck();
        matrix = new DevelopmentCardMatrix(devDecks);
        if (single) tokenArray = createTokensArray();
    }
    public Board()  {}

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



    private LeaderDeck createLeaderDeck() {
                Gson gson= new Gson();
        LeaderDeck returnfinale = new LeaderDeck(new ArrayList<LeaderCard>());
        Reader reader1 = new InputStreamReader(Board.class.getResourceAsStream("/json/discount.json"));
        DiscountLCard[] mazzo1= gson.fromJson(reader1, DiscountLCard[].class);
        for (DiscountLCard d : mazzo1)  {
            returnfinale.getCards().add(d);
        }
        Reader reader2 = new InputStreamReader(Board.class.getResourceAsStream("/json/extrashelf.json"));
        ExtraDepotLCard[] mazzo2= gson.fromJson(reader2, ExtraDepotLCard[].class);
        for (ExtraDepotLCard d : mazzo2)  {
            returnfinale.getCards().add(d);
        }
        Reader reader3 = new InputStreamReader(Board.class.getResourceAsStream("/json/extraprod.json"));
        ExtraProdLCard[] mazzo3= gson.fromJson(reader3, ExtraProdLCard[].class);
        for (ExtraProdLCard d : mazzo3)  {
            returnfinale.getCards().add(d);
        }
        Reader reader4 = new InputStreamReader(Board.class.getResourceAsStream("/json/whitetray.json"));
        WhiteTrayLCard[] mazzo4= gson.fromJson(reader4, WhiteTrayLCard[].class);
        for (WhiteTrayLCard d : mazzo4)  {
            returnfinale.getCards().add(d);
        }
        Collections.shuffle(returnfinale.getCards());

        return returnfinale;
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

    public List<DevDeck> createDevDecks()  {

        Gson gson= new Gson();
        List<DevDeck> devDecks = new ArrayList<>();
        Reader reader = new InputStreamReader(Board.class.getResourceAsStream("/json/devcard.json"));
        DevCard[] allCards = gson.fromJson(reader, DevCard[].class);
        Collections.shuffle(Arrays.asList(allCards));
        DevDeck deck1 = new DevDeck(4, 0, Arrays.stream(allCards).filter(x -> x.getColor().equals(CardColor.GREEN)
                && x.getLevel()==1).collect(Collectors.toList()));
        devDecks.add(deck1);
        DevDeck deck2 = new DevDeck(4, 0, Arrays.stream(allCards).filter(x -> x.getColor().equals(CardColor.BLUE)
                && x.getLevel()==1).collect(Collectors.toList()));
        devDecks.add(deck2);
        DevDeck deck3 = new DevDeck(4, 0, Arrays.stream(allCards).filter(x -> x.getColor().equals(CardColor.YELLOW)
                && x.getLevel()==1).collect(Collectors.toList()));
        devDecks.add(deck3);
        DevDeck deck4 = new DevDeck(4, 0, Arrays.stream(allCards).filter(x -> x.getColor().equals(CardColor.PURPLE)
                && x.getLevel()==1).collect(Collectors.toList()));
        devDecks.add(deck4);
        DevDeck deck5 = new DevDeck(4, 0, Arrays.stream(allCards).filter(x -> x.getColor().equals(CardColor.GREEN)
                && x.getLevel()==2).collect(Collectors.toList()));
        devDecks.add(deck5);
        DevDeck deck6 = new DevDeck(4, 0, Arrays.stream(allCards).filter(x -> x.getColor().equals(CardColor.BLUE)
                && x.getLevel()==2).collect(Collectors.toList()));
        devDecks.add(deck6);
        DevDeck deck7 = new DevDeck(4, 0, Arrays.stream(allCards).filter(x -> x.getColor().equals(CardColor.YELLOW)
                && x.getLevel()==2).collect(Collectors.toList()));
        devDecks.add(deck7);
        DevDeck deck8 = new DevDeck(4, 0, Arrays.stream(allCards).filter(x -> x.getColor().equals(CardColor.PURPLE)
                && x.getLevel()==2).collect(Collectors.toList()));
        devDecks.add(deck8);
        DevDeck deck9 = new DevDeck(4, 0, Arrays.stream(allCards).filter(x -> x.getColor().equals(CardColor.GREEN)
                && x.getLevel()==3).collect(Collectors.toList()));
        devDecks.add(deck9);
        DevDeck deck10 = new DevDeck(4, 0, Arrays.stream(allCards).filter(x -> x.getColor().equals(CardColor.BLUE)
                && x.getLevel()==3).collect(Collectors.toList()));
        devDecks.add(deck10);
        DevDeck deck11 = new DevDeck(4, 0, Arrays.stream(allCards).filter(x -> x.getColor().equals(CardColor.YELLOW)
                && x.getLevel()==3).collect(Collectors.toList()));
        devDecks.add(deck11);
        DevDeck deck12 = new DevDeck(4, 0, Arrays.stream(allCards).filter(x -> x.getColor().equals(CardColor.PURPLE)
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


    public Board(Market market, LeaderDeck leaderDeck, ArrayList<DevDeck> devDecks, ArrayList<SoloActionToken> tokenArray) {
        this.market = market;
        this.leaderDeck = leaderDeck;
        this.devDecks = devDecks;
        this.tokenArray = tokenArray;
    }



    public DevelopmentCardMatrix getMatrix() {
        return matrix;
    }

    public void setMatrix(DevelopmentCardMatrix matrix) {
        this.matrix = matrix;
    }


    public Board clone() {
        Board clone = new Board();
        clone.market = market.clone();
        clone.matrix = matrix.clone();
        clone.devDecks = new ArrayList<>();
        for (DevDeck d: this.devDecks) clone.devDecks.add(d.clone());

        //TODO LEADER DECK

        if (tokenArray != null) {
            clone.tokenArray = new ArrayList<>();
            for (SoloActionToken sat : tokenArray) clone.tokenArray.add(sat.clone());
        }
        else clone.tokenArray = null;

        return clone;
    }
}

