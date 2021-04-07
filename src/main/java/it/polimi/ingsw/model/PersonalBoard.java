package it.polimi.ingsw.model;

import java.util.ArrayList;

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

public class PersonalBoard {
    private final ArrayList<Slot> faithTrack;
    private ArrayList<DevDeck> cardSlot;
    private LeaderDeck activeLeader;
    private int numDevCards;
    private boolean favorTile1;
    private boolean favorTile2;
    private boolean favorTile3;
    private Strongbox strongbox;
    private ShelfWarehouse warehouse;

    public PersonalBoard(ArrayList<Slot> faithTrack, ArrayList<DevDeck> cardSlot, LeaderDeck activeLeader, int numDevCards, boolean favorTile1, boolean favorTile2, boolean favorTile3, Strongbox strongbox, ShelfWarehouse warehouse) {
        this.faithTrack = faithTrack;
        this.cardSlot = cardSlot;
        this.activeLeader = activeLeader;
        this.numDevCards = numDevCards;
        this.favorTile1 = favorTile1;
        this.favorTile2 = favorTile2;
        this.favorTile3 = favorTile3;
        this.strongbox = strongbox;
        this.warehouse = warehouse;
    }


    public boolean checkLCardRequirements(LeaderCard lc)  {
        switch (lc.getType())  {
            case 1:   return this.checkDiscountCardRequirements((DiscountLCard) lc);
            case 2:   return this.checkExtraDepotRequirements((ExtraDepotLCard) lc);
            case 3:   return this.checkExtraProdRequirements((ExtraProdLCard) lc);
            case 4:   return this.checkWTrayCardRequirements((WhiteTrayLCard) lc);
            default:  return false;
        }
    }
    /**
     * Method checkLCardRequirements checks if the player meets the requirements of a Leader Card with WhiteTray Ability
     * @param lc is the LeaderCard whose we want to check the requirements
     * @return boolean true if the player has met the requirements
     */
    public boolean checkWTrayCardRequirements(WhiteTrayLCard lc)  {
        CardColor c1= lc.getX2Color(); //2 cards
        CardColor c2= lc.getX1Color(); //1 card
        int x1c=0;
        int x2c=0;

        for (DevDeck d : this.cardSlot)  {
            for (DevCard dv : d.getCards() )  {
                if (dv.getColor().equals(c1))  {
                    x1c++;
                }
                if (dv.getColor().equals(c2))  {
                    x2c++;
                }
            }
        }
        return (x1c>=2 && x2c>=1);



    }

    /**
     * Method checkLCardRequirements checks if the player meets the requirements of a Leader Card with ExtraProduction Ability
     * @param lc is the LeaderCard whose we want to check the requirements
     * @return boolean true if the player has met the requirements
     */
    public boolean checkExtraProdRequirements(ExtraProdLCard lc)  {
        CardColor c1= lc.getColor(); //1 cards of lv2 (minimum?)
        int x1c=0;
        for (DevDeck d : this.cardSlot)  {
            for (DevCard dv : d.getCards() )  {
                if (dv.getColor().equals(c1) && dv.getLevel()>=2)  {
                    x1c++;
                }
            }
        }
        return (x1c>=1);


    }

    /**
     * Method checkLCardRequirements checks if the player meets the requirements of a Leader Card with ExtraDepot Ability
     * @param lc is the LeaderCard whose we want to check the requirements
     * @return boolean true if the player has met the requirements
     */


    public boolean checkExtraDepotRequirements(ExtraDepotLCard lc)  {
        int sum;
        ResourceType r = lc.getResType();
        sum = this.strongbox.getResCount(r) + this.warehouse.getResCount(r);
        return (sum>=5);
    }

    /**
     * Method checkLCardRequirements checks if the player meets the requirements of a Leader Card with Discount Ability
     * @param lc is the LeaderCard whose we want to check the requirements
     * @return boolean true if the player has met the requirements
     */
    public boolean checkDiscountCardRequirements(DiscountLCard lc)  {
        CardColor c1= lc.getColor1(); //2 cards
        CardColor c2= lc.getColor2(); //1 card
        int x1c=0;
        int x2c=0;
        for (DevDeck d : this.cardSlot)  {
            for (DevCard dv : d.getCards() )  {
                if (dv.getColor().equals(c1))  {
                    x1c++;
                }
                if (dv.getColor().equals(c2))  {
                    x2c++;
                }
            }
        }
        return (x1c>=1 && x2c>=1);

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

    public void setFavorTile1(boolean favorTile1) {
        this.favorTile1 = favorTile1;
    }

    public void setFavorTile2(boolean favorTile2) {
        this.favorTile2 = favorTile2;
    }

    public void setFavorTile3(boolean favorTile3) {
        this.favorTile3 = favorTile3;
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

    public boolean isFavorTile1() {
        return favorTile1;
    }

    public boolean isFavorTile2() {
        return favorTile2;
    }

    public boolean isFavorTile3() {
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

}
