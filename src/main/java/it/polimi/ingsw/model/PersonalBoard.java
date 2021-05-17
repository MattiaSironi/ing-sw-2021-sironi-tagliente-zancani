package it.polimi.ingsw.model;

import com.google.gson.Gson;
import it.polimi.ingsw.message.ActionMessages.ObjectMessage;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class PersonalBoard represents the board of each player
 *
 * @author Lea Zancani
 * @see DevCard
 * @see LeaderDeck
 * @see Strongbox
 * @see ShelfWarehouse
 * @see Slot
 */

public class PersonalBoard implements Serializable {
    private  ArrayList<Slot> faithTrack;
    private ArrayList<DevDeck> cardSlot;
    private LeaderDeck activeLeader;
    private int numDevCards;
    private int favorTile1;
    private int favorTile2;
    private int favorTile3;
    private Strongbox strongbox;
    private ShelfWarehouse warehouse;
    private ResourceType extraShelfRes1;
    private ResourceType extraShelfRes2;
    private int extraShelfNum1;
    private int extraShelfNum2;
    private ExtraProdLCard leaderChosen;


    public PersonalBoard(ArrayList<Slot> faithTrack, ArrayList<DevDeck> cardSlot, LeaderDeck activeLeader, int numDevCards, Strongbox strongbox, ShelfWarehouse warehouse) {
        this.faithTrack = faithTrack;
        this.cardSlot = cardSlot;
        this.activeLeader = activeLeader;
        this.numDevCards = numDevCards;

        this.strongbox = strongbox;
        this.warehouse = warehouse;

    }

    private ArrayList<Slot> createFaithTrack() {
        Gson gson = new Gson();
        Reader reader =  new InputStreamReader(PersonalBoard.class.getResourceAsStream("/json/slot.json"));
        Slot[]  slots  = gson.fromJson(reader, Slot[].class);
        ArrayList<Slot> faithTrack = new ArrayList<>();
        faithTrack.addAll(Arrays.asList(slots));
        return  faithTrack;

    }

    public PersonalBoard()  {
        warehouse = new ShelfWarehouse();
        strongbox = new Strongbox();
        cardSlot = new ArrayList<DevDeck>();
        activeLeader = new LeaderDeck(0,1,new ArrayList<>()); //lea modifica pure
        cardSlot.add(new DevDeck(0, 0, new ArrayList<DevCard>()));
        cardSlot.add(new DevDeck(0, 0, new ArrayList<DevCard>()));
        cardSlot.add(new DevDeck(0, 0, new ArrayList<DevCard>()));
        this.faithTrack = createFaithTrack();
    }

    public boolean totalPaymentChecker(int resArray[]){
        int totCoin, totStone, totServant, totShield;
        totCoin = strongbox.getResCount(ResourceType.COIN) + warehouse.getResCount(ResourceType.COIN);
        totStone = strongbox.getResCount(ResourceType.STONE) + warehouse.getResCount(ResourceType.STONE);
        totServant = strongbox.getResCount(ResourceType.SERVANT) + warehouse.getResCount(ResourceType.SERVANT);
        totShield = strongbox.getResCount(ResourceType.SHIELD) + warehouse.getResCount(ResourceType.SHIELD);

        if(totCoin < resArray[0] || totStone < resArray[1] || totServant < resArray[2] || totShield < resArray[3]) return false;
        else return true;
    }

    public ExtraProdLCard getLeaderChosen() {
        return leaderChosen;
    }

    public void setLeaderChosen(ExtraProdLCard leaderChosen) {
        this.leaderChosen = leaderChosen;
    }

    public boolean checkLCardRequirements(LeaderCard lc) {
        switch (lc.getType()) {
            case 1:
                return this.checkDiscountCardRequirements((DiscountLCard) lc);
            case 2:
                return this.checkExtraDepotRequirements((ExtraDepotLCard) lc);
            case 3:
                return this.checkExtraProdRequirements((ExtraProdLCard) lc);
            case 4:
                return this.checkWTrayCardRequirements((WhiteTrayLCard) lc);
            default:
                return false;
        }
    }

    /**
     * Method checkLCardRequirements checks if the player meets the requirements of a Leader Card with WhiteTray Ability
     *
     * @param lc is the LeaderCard whose we want to check the requirements
     * @return boolean true if the player has met the requirements
     */
    public boolean checkWTrayCardRequirements(WhiteTrayLCard lc) {
        CardColor c1 = lc.getX2Color(); //2 cards
        CardColor c2 = lc.getX1Color(); //1 card
        int x1c = 0;
        int x2c = 0;

        for (DevDeck d : this.cardSlot) {
            for (DevCard dv : d.getCards()) {
                if (dv.getColor().equals(c1)) {
                    x1c++;
                }
                if (dv.getColor().equals(c2)) {
                    x2c++;
                }
            }
        }
        return (x1c >= 2 && x2c >= 1);


    }


    public ResourceType getExtraShelfRes1() {
        return extraShelfRes1;
    }

    public void setExtraShelfRes1(ResourceType extraShelfRes1) {
        this.extraShelfRes1 = extraShelfRes1;
    }

    public ResourceType getExtraShelfRes2() {
        return extraShelfRes2;
    }

    public void setExtraShelfRes2(ResourceType extraShelfRes2) {
        this.extraShelfRes2 = extraShelfRes2;
    }

    public int getExtraShelfNum1() {
        return extraShelfNum1;
    }

    public void setExtraShelfNum1(int extraShelfNum1) {
        this.extraShelfNum1 = extraShelfNum1;
    }

    public int getExtraShelfNum2() {
        return extraShelfNum2;
    }

    public void setExtraShelfNum2(int extraShelfNum2) {
        this.extraShelfNum2 = extraShelfNum2;
    }

    /**
     * Method checkLCardRequirements checks if the player meets the requirements of a Leader Card with ExtraProduction Ability
     *
     * @param lc is the LeaderCard whose we want to check the requirements
     * @return boolean true if the player has met the requirements
     */
    public boolean checkExtraProdRequirements(ExtraProdLCard lc) {
        CardColor c1 = lc.getColor(); //1 cards of lv2 (minimum?)
        int x1c = 0;
        for (DevDeck d : this.cardSlot) {
            for (DevCard dv : d.getCards()) {
                if (dv.getColor().equals(c1) && dv.getLevel() >= 2) {
                    x1c++;
                }
            }
        }
        return (x1c >= 1);
    }

    public void addDevCard(DevCard d, int pos, int ID){
        cardSlot.get(pos).getCards().add(0, d);
    }



    /**
     * Method checkLCardRequirements checks if the player meets the requirements of a Leader Card with ExtraDepot Ability
     *
     * @param lc is the LeaderCard whose we want to check the requirements
     * @return boolean true if the player has met the requirements
     */


    public boolean checkExtraDepotRequirements(ExtraDepotLCard lc) {
        int sum;
        ResourceType r = lc.getResType();
        sum = this.strongbox.getResCount(r) + this.warehouse.getResCount(r);
        return (sum >= 5);
    }

    /**
     * Method checkLCardRequirements checks if the player meets the requirements of a Leader Card with Discount Ability
     *
     * @param lc is the LeaderCard whose we want to check the requirements
     * @return boolean true if the player has met the requirements
     */
    public boolean checkDiscountCardRequirements(DiscountLCard lc) {
        CardColor c1 = lc.getColor1(); //2 cards
        CardColor c2 = lc.getColor2(); //1 card
        int x1c = 0;
        int x2c = 0;
        for (DevDeck d : this.cardSlot) {
            for (DevCard dv : d.getCards()) {
                if (dv.getColor().equals(c1)) {
                    x1c++;
                }
                if (dv.getColor().equals(c2)) {
                    x2c++;
                }
            }
        }
        return (x1c >= 1 && x2c >= 1);

    }

    public void setCardSlot(ArrayList<DevDeck> cardSlot) {
        this.cardSlot = cardSlot;
    }

    public void setActiveLeader(LeaderDeck activeLeader) {
        this.activeLeader = activeLeader;
    }

    public void setNumDevCards(int numDevCards) {
        this.numDevCards = numDevCards;
    }


    public void setFavorTile(int ft) {
        switch (ft) {
            case 0:
                this.favorTile1 = 1;
                break;
            case 1:
                this.favorTile2 = 1;
                break;
            case 2:
                this.favorTile3 = 1;
                break;
            default:
        }
    }

    public ArrayList<DevDeck> getCardSlot() {
        return cardSlot;
    }

    public LeaderDeck getActiveLeader() {
        return activeLeader;
    }

    public int getNumDevCards() {
        return numDevCards;
    }

    public int getFavorTile1() {
        return favorTile1;
    }

    public int getFavorTile2() {
        return favorTile2;
    }

    public int getFavorTile3() {
        return favorTile3;
    }

    public ArrayList<Slot> getFaithTrack() {
        return faithTrack;
    }

    public Strongbox getStrongbox() {
        return strongbox;
    }

    public ShelfWarehouse getWarehouse() {
        return warehouse;
    }

    public void setStrongbox(Strongbox strongbox) {
        this.strongbox = strongbox;
    }

    public void setWarehouse(ShelfWarehouse warehouse) {
        this.warehouse = warehouse;
    }

    public void printFaithTrack (int mypoints){
        char empty = '\u2E2C';
        char popeSpace  = '\u271F';
        char pope2 = '\u271D';
        char favorSpace = '\u2735';
        int i=0 ;
        while(i<=24){
            if(i!=mypoints){
                if(i>=0&&i<=4 || i>=9&&i<=11 || i>=17&&i<=18)
                    System.out.print(empty + " ");
                else if(i>=5&&i<=7 || i>=12&&i<=15 || i>=19&&i<=23)
                    System.out.print(favorSpace + " ");
                else
                    System.out.print(pope2 + " ");
            }
           // else
            // {System.out.print("X" + " ");}
            i++;
        }
        System.out.println("");
    }
}
