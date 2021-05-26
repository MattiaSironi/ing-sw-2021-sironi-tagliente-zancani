package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class PersonalBoard represents the board of each player
 *
 * @author Lea Zancani
 * @see DevCard
 * @see LeaderDeck
 * @see Strongbox
 * @see ShelfWarehouse
 * @see FaithTrack
 */

public class PersonalBoard implements Serializable {
    private FaithTrack faithTrack;
    private ArrayList<DevDeck> cardSlot;
    private int numDevCards;
    private Strongbox strongbox;
    private ShelfWarehouse warehouse;
    private LeaderDeck activeLeader;
    private ExtraProdLCard leaderChosen;


    public PersonalBoard(FaithTrack faithTrack, ArrayList<DevDeck> cardSlot, LeaderDeck activeLeader, int numDevCards, Strongbox strongbox, ShelfWarehouse warehouse) {
        this.faithTrack = faithTrack;
        this.cardSlot = cardSlot;
        this.activeLeader = activeLeader;
        this.numDevCards = numDevCards;
        this.strongbox = strongbox;
        this.warehouse = warehouse;

    }

    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    public void setFaithTrack(FaithTrack faithTrack) {
        this.faithTrack = faithTrack;
    }

    public PersonalBoard()  {
        warehouse = new ShelfWarehouse();
        strongbox = new Strongbox();
        cardSlot = new ArrayList<DevDeck>();
        activeLeader = new LeaderDeck(new ArrayList<>()); //lea modifica pure
        cardSlot.add(new DevDeck(new ArrayList<DevCard>()));
        cardSlot.add(new DevDeck(new ArrayList<DevCard>()));
        cardSlot.add(new DevDeck(new ArrayList<DevCard>()));
        this.faithTrack = new FaithTrack(null);
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




    public ArrayList<DevDeck> getCardSlot() {
        return cardSlot;
    }

    public LeaderDeck getActiveLeader() {
        return activeLeader;
    }

    public int getNumDevCards() {
        return numDevCards;
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

    public PersonalBoard clone() {
        PersonalBoard clone = new PersonalBoard();
        clone.numDevCards = numDevCards;
        clone.faithTrack = faithTrack.clone();
        clone.warehouse = warehouse.clone();
        clone.strongbox = strongbox.clone();
        clone.cardSlot = new ArrayList<>();
        clone.activeLeader = activeLeader.clone();
        clone.leaderChosen = leaderChosen;
        for (DevDeck d : this.cardSlot) clone.cardSlot.add(d.clone());
        return clone;
    }
}
