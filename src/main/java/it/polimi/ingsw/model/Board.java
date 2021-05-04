package it.polimi.ingsw.model;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.io.Reader;
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

public class Board {
    private Market market;
    private LeaderDeck leaderDeck;
    private List<DevDeck> devDecks;
    private ArrayList<SoloActionToken> tokenArray;

    public Board() { //for now just for multiplayer
        market = createMarket();
        devDecks = createDevDecks();
        leaderDeck = createLeaderDeck();
    }

    private LeaderDeck createLeaderDeck() {
                Gson gson= new Gson();
        LeaderDeck returnfinale = new LeaderDeck(16, 00, new ArrayList<LeaderCard>());
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
        List<DevDeck> returnfinale = new ArrayList<>();
        Reader reader = new InputStreamReader(Board.class.getResourceAsStream("/json/devcard.json"));
        DevCard[] tutteLeCarte = gson.fromJson(reader, DevCard[].class);
        Collections.shuffle(Arrays.asList(tutteLeCarte));
        DevDeck mazzo1 = new DevDeck(4, 0, Arrays.stream(tutteLeCarte).filter(x -> x.getColor().equals(CardColor.GREEN)
                && x.getLevel()==1).collect(Collectors.toList()));
        returnfinale.add(mazzo1);
        DevDeck mazzo2 = new DevDeck(4, 0, Arrays.stream(tutteLeCarte).filter(x -> x.getColor().equals(CardColor.BLUE)
                && x.getLevel()==1).collect(Collectors.toList()));
        returnfinale.add(mazzo2);
        DevDeck mazzo3 = new DevDeck(4, 0, Arrays.stream(tutteLeCarte).filter(x -> x.getColor().equals(CardColor.YELLOW)
                && x.getLevel()==1).collect(Collectors.toList()));
        returnfinale.add(mazzo3);
        DevDeck mazzo4 = new DevDeck(4, 0, Arrays.stream(tutteLeCarte).filter(x -> x.getColor().equals(CardColor.PURPLE)
                && x.getLevel()==1).collect(Collectors.toList()));
        returnfinale.add(mazzo4);
        DevDeck mazzo5 = new DevDeck(4, 0, Arrays.stream(tutteLeCarte).filter(x -> x.getColor().equals(CardColor.GREEN)
                && x.getLevel()==2).collect(Collectors.toList()));
        returnfinale.add(mazzo5);
        DevDeck mazzo6 = new DevDeck(4, 0, Arrays.stream(tutteLeCarte).filter(x -> x.getColor().equals(CardColor.BLUE)
                && x.getLevel()==2).collect(Collectors.toList()));
        returnfinale.add(mazzo6);
        DevDeck mazzo7 = new DevDeck(4, 0, Arrays.stream(tutteLeCarte).filter(x -> x.getColor().equals(CardColor.YELLOW)
                && x.getLevel()==2).collect(Collectors.toList()));
        returnfinale.add(mazzo7);
        DevDeck mazzo8 = new DevDeck(4, 0, Arrays.stream(tutteLeCarte).filter(x -> x.getColor().equals(CardColor.PURPLE)
                && x.getLevel()==2).collect(Collectors.toList()));
        returnfinale.add(mazzo8);
        DevDeck mazzo9 = new DevDeck(4, 0, Arrays.stream(tutteLeCarte).filter(x -> x.getColor().equals(CardColor.GREEN)
                && x.getLevel()==3).collect(Collectors.toList()));
        returnfinale.add(mazzo9);
        DevDeck mazzo10 = new DevDeck(4, 0, Arrays.stream(tutteLeCarte).filter(x -> x.getColor().equals(CardColor.BLUE)
                && x.getLevel()==3).collect(Collectors.toList()));
        returnfinale.add(mazzo10);
        DevDeck mazzo11 = new DevDeck(4, 0, Arrays.stream(tutteLeCarte).filter(x -> x.getColor().equals(CardColor.YELLOW)
                && x.getLevel()==3).collect(Collectors.toList()));
        returnfinale.add(mazzo11);
        DevDeck mazzo12 = new DevDeck(4, 0, Arrays.stream(tutteLeCarte).filter(x -> x.getColor().equals(CardColor.PURPLE)
                && x.getLevel()==3).collect(Collectors.toList()));
        returnfinale.add(mazzo12);

        return returnfinale;

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

    public void printDevMatrix(){
        for(DevDeck dd : devDecks){
            dd.print();
        }
    }


}

