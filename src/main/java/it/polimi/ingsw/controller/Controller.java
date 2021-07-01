package it.polimi.ingsw.controller;


import it.polimi.ingsw.message.ActionMessages.*;
import it.polimi.ingsw.message.CommonMessages.*;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observer.Observer;


import java.util.ArrayList;
import java.util.Collections;

/**
 * Class Controller contains a lot of Master of Renaissance's rules and logics.
 * It observes ModelMultiplayerView in MultiPlayer Mode, ClientActionController in CLI Solo Mode,
 * MainController in GUI Solo Mode.
 * @see it.polimi.ingsw.client.ModelMultiplayerView
 * @see it.polimi.ingsw.client.gui.controllers.MainController
 * @see it.polimi.ingsw.client.ClientActionController
 * @see Game
 */

public class Controller implements Observer<Message> {
    private Game game;
    private boolean actionDone;

    public Controller(Game game) {
        this.game = game;
    }

    /**
     * Method checkTurn checks whether player doing actions is the player playing.
     * @param ID is the Player who is doing actions' ID.
     * @return true if the Player is playing.
     */

    public boolean checkTurn(int ID){
        return ID == game.getTurn().getPlayerPlayingID();

    }

    /**
     * Method setNickname checks if nickname is valid. if it's valid, it creates the player. If all players selected the nickname, the game starts.
     * @param nickname is a Nickname Message containing Player's ID and chosen nickname.
     */

    public synchronized void setNickname(Nickname nickname) {
        String nick = nickname.getString();
        boolean found = false;
        ArrayList<Player> p = game.getPlayers();
        for (Player pl : p) {
            if (pl.getNickname().equals(nick)) {
                found = true;
                break;
            }
        }
        if (found || nick.length() > 12 || nick.length() == 0) {
            game.setCommunication(nickname.getID(), CommunicationList.NICK_NOT_VALID);
        } else {
            game.setCommunication(nickname.getID(), CommunicationList.NICK_VALID);
            game.getPlayers().add(new Player(nickname.getID(), nickname.getString()));
            if (game.getNumPlayer() == 1) {
                game.getPlayerById(0).getPersonalBoard().getFaithTrack().setLoriPos(0);
            }
            if (game.getPlayers().size() == game.getNumPlayer()) {

                game.giveLeaderCards();
                initialPhase();
            }
        }
    }

    /**
     * Method initialPhase shuffles Players and according to their position and to the Game rules, it gives initial resources, Leader Cards and faith points.
     */

    public void initialPhase() {
        Collections.shuffle(game.getPlayers());
        game.sendGame();
        for (Player p : game.getPlayers()) {
            switch (game.getPlayers().indexOf(p)) {
                case 0 -> {
                    game.setCommunication(p.getId(), CommunicationList.FIRST);
                    game.setLeaderCardsToDiscard(p.getId(), 2);
                    game.setStartResCountByID(p.getId(), 0);
                }
                case 1 -> {
                    game.setCommunication(p.getId(), CommunicationList.SECOND);
                    game.setLeaderCardsToDiscard(p.getId(), 2);
                    game.setStartResCountByID(p.getId(), 1);
                }
                case 2 -> {
                    game.setCommunication(p.getId(), CommunicationList.THIRD);
                    game.moveFaithPosByID(p.getId(), 1);
                    game.setLeaderCardsToDiscard(p.getId(), 2);
                    game.setStartResCountByID(p.getId(), 1);
                }
                case 3 -> {
                    game.setCommunication(p.getId(), CommunicationList.FOURTH);
                    game.moveFaithPosByID(p.getId(), 1);
                    game.setLeaderCardsToDiscard(p.getId(), 2);
                    game.setStartResCountByID(p.getId(), 2);
                }
            }
        }
    }

    /**
     * Method swapShelves checks if move could be valid. if yes, it calls Game's swap methods.
     * @param s1 is the index of the first shelf.
     * @param s2 is the index of the second shelf.
     * @param ID is the Player's ID.
     */

    public void swapShelves(int s1, int s2, int ID) {

        if ((s1 == s2) || (s1 < 0) || (s1 > 4) || (s2 < 0) || (s2 > 4)) {
            game.setCommunication(ID, CommunicationList.INVALID_MOVE);
            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.WAITING_FOR_ACTION);
            return;
        }
        if (!(game.swapShelvesByID(s1, s2, ID))) {
            game.setCommunication(ID, CommunicationList.INVALID_MOVE);
            game.setTurn(game.getTurn().getPlayerPlayingID(), game.getTurn().getPhase());
        } else
            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.WAITING_FOR_ACTION);
    }

    /**
     * Method goToMarket shifts market Marbles and set Market Hand, according to Game rules.
     * @param row is true if index corresponds to a row.
     * @param index is the index of the selected row/column
     * @param ID is the Player's ID.
     */

    public void goToMarket(boolean row, int index, int ID) {

        Market m = this.game.getBoard().getMarket();
        ArrayList<Marble> resources;

        if (row) {
            resources = m.getRow(index);
            for (int j = 3; j >= 0; j--) {
                if (j == 3) m.setMarble(index, j, m.getMarbleOut());
                else m.setMarble(index, j, resources.get(j + 1));
            }
        } else {
            index = 3 - index;
            resources = m.getColumn(index);
            for (int k = 2; k >= 0; k--) {
                if (k == 2) m.setMarble(k, index, m.getMarbleOut());
                else m.setMarble(k, index, resources.get(k + 1));
            }
        }
        m.setMarbleOut(resources.get(0));

        game.setMarketHand(resources);
        game.setTurn(ID, ActionPhase.MARKET);

    }

    /**
     * Method placeRes permits the player to place a resource bought at the market or in game's initial phase, or to discard it.
     * @param r is the ResourceType
     * @param shelfIndex is the index of the selected shelf.
     * @param ID is the Player's ID
     * @param discard is true if player wants to discard r.
     * @param initialPhase is true if players are placing initial resources.
     */

    public synchronized void placeRes(ResourceType r, int shelfIndex, int ID, boolean discard, boolean initialPhase) {


        if (initialPhase && game.getPlayerById(ID).getStartResCount()>0 ) {
            int resRemaining = game.getPlayerById(ID).getStartResCount();
            if (this.game.addResourceToWarehouse(ID, shelfIndex, r)) resRemaining--;
            game.setStartResCountByID(ID, resRemaining);
        } else {
            if (discard) {
                discardRes(ID);
                game.removeFromMarketHand();

            } else {

                if (r.equals(ResourceType.FAITH_POINT)) {
                    game.moveFaithPosByID(ID, 1);
                    game.removeFromMarketHand();
                } else if (r.equals(ResourceType.EMPTY)) game.removeFromMarketHand();
                else {
                    if (this.game.addResourceToWarehouse(ID, shelfIndex, r))
                        game.removeFromMarketHand();
                    else {
                        game.setCommunication(ID, CommunicationList.INVALID_MOVE);
                        game.setTurn(game.getTurn().getPlayerPlayingID(), game.getTurn().getPhase());
                        return;
                    }
                }
            }

            if (game.getBoard().getMarket().getHand().size() == 0) {
                game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.WAITING_FOR_ACTION);
            } else
                game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.MARKET);
        }
    }


    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    /** Method discardRes moves every players (or Lorenzo Il Magnifico if game is in single mode) beside player that chooses to discard a resource.
     *
     * @param ID is the Player's ID
     */

    public void discardRes(int ID) {
        if (game.getPlayers().size() == 1) {
            game.moveLoriPos(1);
        } else {

            ArrayList<Player> others = this.game.getPlayers();
            for (Player p : others) {
                if (p.getId() != ID) {
                    this.game.moveFaithPosByID(p.getId(), 1);
                }
            }
        }
    }

    /**
     *  Method handleChosenDevCard checks if the card can be placed on the dedicated slots of the personal board of the player
     * @param chosenIndex   of type int - index of the matrix of the development card matrix
     * @param ID            of type int - ID of the player
     */
    public void handleChosenDevCard(int chosenIndex, int ID) {
        try {
            int[] toPay = checkHasDiscount(game.getBoard().getMatrix().getDevDecks().get(chosenIndex).getCards().get(0).getCostRes(), ID);
            if (!game.getPlayerById(ID).getPersonalBoard().totalPaymentChecker(toPay)) {
                game.setCommunication(ID, CommunicationList.NOT_ENOUGH_RES);
                game.setTurn(game.getTurn().getPlayerPlayingID(), game.getTurn().getPhase());
            } else if (!(checkDevCardPlacement(game.getBoard().getMatrix().getDevDecks().get(chosenIndex).getCards().get(0), game.getPlayerById(ID)))) {
                game.setCommunication(ID, CommunicationList.NO_SLOTS);
                game.setTurn(game.getTurn().getPlayerPlayingID(), game.getTurn().getPhase());
            } else {
                game.setResToPay(game.getBoard().getMatrix().getDevDecks().get(chosenIndex).getCards().get(0), ID);
                game.setChosenDevCard(game.getBoard().getMatrix().getDevDecks().get(chosenIndex).getCards().get(0), chosenIndex);
                if (game.getBoard().getMatrix().getResToPay().size() == 0) {
                    game.setCommunication(ID, CommunicationList.NO_PAYMENT_NEEDED);
                    game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.CHOOSE_SLOT);
                } else {
                    game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.B_PAYMENT);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            game.setCommunication(ID, CommunicationList.INVALID_MOVE);
            game.setTurn(game.getTurn().getPlayerPlayingID(), game.getTurn().getPhase());
        }
    }

    /**
     *  Method checkHasDiscount checks if the player has one or more active leaders with the discount ability
     * @param toPay     of type int[] - price of the card
     * @param ID        of type int - ID of the player
     * @return the modified array on case of discount
     */
    public int[] checkHasDiscount(int[] toPay, int ID) {
        int[] toPayCpy = toPay.clone();
        if (game.getPlayerById(ID).getResDiscount1() == ResourceType.COIN && toPay[0] > 0) {
            toPayCpy[0]--;
        } else if (game.getPlayerById(ID).getResDiscount1() == ResourceType.STONE && toPay[1] > 0) {
            toPayCpy[1]--;
        } else if (game.getPlayerById(ID).getResDiscount1() == ResourceType.SERVANT && toPay[2] > 0) {
            toPayCpy[2]--;
        } else if (game.getPlayerById(ID).getResDiscount1() == ResourceType.SHIELD && toPay[3] > 0) {
            toPayCpy[3]--;
        }
        if (game.getPlayerById(ID).getResDiscount2() == ResourceType.COIN && toPay[0] > 0) {
            toPayCpy[0]--;
        } else if (game.getPlayerById(ID).getResDiscount2() == ResourceType.STONE && toPay[1] > 0) {
            toPayCpy[1]--;
        } else if (game.getPlayerById(ID).getResDiscount2() == ResourceType.SERVANT && toPay[2] > 0) {
            toPayCpy[2]--;
        } else if (game.getPlayerById(ID).getResDiscount2() == ResourceType.SHIELD && toPay[3] > 0) {
            toPayCpy[3]--;
        }
        return toPayCpy;
    }

    /**
     * Method payRes performs the payment of one resource for all the actions
     * @param payFrom       of type boolean - true if the player wants to pay from the warehouse, false if he wants to pay from the strongbox
     * @param ID            of type int - id of the player
     * @param actionPhase   of type ActionPhase - is the phase for which the payment is needed
     */
    public void payRes(boolean payFrom, int ID, ActionPhase actionPhase) {
        if (payFrom) {
            if (!(game.getPlayerById(ID).getPersonalBoard().getWarehouse().canIPay(1, game.getBoard().getMatrix().getResToPay().get(0)))) {
                game.setCommunication(ID, CommunicationList.NOT_ENOUGH_RES);
                game.setTurn(game.getTurn().getPlayerPlayingID(), game.getTurn().getPhase());
            } else {
                if (game.getPlayerById(ID).getPersonalBoard().getWarehouse().getShelves().get(3).getResType() == game.getBoard().getMatrix().getResToPay().get(0) &&
                        game.getPlayerById(ID).getPersonalBoard().getWarehouse().getShelves().get(3).getCount() != 0) {
                    this.game.payFromFirstExtraShelf(ID, 1);
                } else if (game.getPlayerById(ID).getPersonalBoard().getWarehouse().getShelves().get(4).getResType() == game.getBoard().getMatrix().getResToPay().get(0) &&
                        game.getPlayerById(ID).getPersonalBoard().getWarehouse().getShelves().get(4).getCount() != 0) {
                    this.game.payFromSecondExtraShelf(ID, 1);
                } else
                    game.payFromWarehouse(1, game.getBoard().getMatrix().getResToPay().get(0), ID);
                isEmpty(actionPhase);
            }
        } else {
            if (!(game.getPlayerById(ID).getPersonalBoard().getStrongbox().canIPay(1, game.getBoard().getMatrix().getResToPay().get(0)))) {
                game.setCommunication(ID, CommunicationList.NOT_ENOUGH_RES);
                game.setTurn(game.getTurn().getPlayerPlayingID(), game.getTurn().getPhase());
            } else {
                game.payFromStrongbox(1, game.getBoard().getMatrix().getResToPay().get(0), ID);
                isEmpty(actionPhase);
            }
        }
    }

    /**
     * method isEmpty checks if the payer has paid all the resources
     * @param actionPhase of type ActionPhase - is the phase for which the payment is needed
     */
    private void isEmpty(ActionPhase actionPhase) {
        game.removeResToPay();
        if (game.getBoard().getMatrix().getResToPay().size() == 0) {
            if (actionPhase == ActionPhase.D_PAYMENT || actionPhase == ActionPhase.PAYMENT)
                game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.A_PAYMENT);
            else if(actionPhase == ActionPhase.B_PAYMENT)
                game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.CHOOSE_SLOT);
        } else {
            if (actionPhase == ActionPhase.D_PAYMENT)
                game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.D_PAYMENT);
            else if (actionPhase == ActionPhase.B_PAYMENT)
                game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.B_PAYMENT);
            else if (actionPhase == ActionPhase.PAYMENT)
                game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.PAYMENT);
        }
    }

    /**
     *  Method placeDevCard places the card in the slot of the personal board chosen by the player
     * @param ID    of type int - id of the player
     * @param slot  of type int - index of the chosen slot
     */
    public void placeDevCard(int ID, int slot) {
        if (game.getPlayerById(ID).getPersonalBoard().getCardSlot().get(slot).getCards().size() == 0 && game.getBoard().getMatrix().getChosenCard().getLevel() == 1) {
            game.addDevCardToPlayer(ID, slot);
            actionDone = true;
            game.setChosenDevCard(null, -1);
            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.WAITING_FOR_ACTION);
            if (game.getPlayers().size() == 1 && game.checkColumnEmpty()) {
                game.setGameOver(true);
            }
        } else if (game.getPlayerById(ID).getPersonalBoard().getCardSlot().get(slot).getCards().size() != 0 &&
                game.getPlayerById(ID).getPersonalBoard().getCardSlot().get(slot).getCards().get(0).getLevel() == game.getBoard().getMatrix().getChosenCard().getLevel() - 1) {
            game.addDevCardToPlayer(ID, slot);
            actionDone = true;
            game.setChosenDevCard(null, -1);
            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.WAITING_FOR_ACTION);
        } else {
            game.setCommunication(ID, CommunicationList.INVALID_MOVE);
            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.CHOOSE_SLOT);
        }
    }

    /**
     * Method checkDevCardPlacement checks if the player can place the development card on his personal board
     * @param devCard   of type DevCard - card to be placed
     * @param player    of type Player - player whose personal board must be checked
     * @return  true if the player has at least one available slot for the bought card, false if not
     */
    public boolean checkDevCardPlacement(DevCard devCard, Player player) {
        for (DevDeck dd : player.getPersonalBoard().getCardSlot()) {
            if (dd.getCards().size() == 0 && devCard.getLevel() == 1) return true;
        }
        for (DevDeck dd : player.getPersonalBoard().getCardSlot()) {
            if (dd.getCards().size() != 0 && dd.getCards().get(0).getLevel() == devCard.getLevel() - 1)
                return true;
        }
        return false;

    }

    /**
     * Method collectNewRes adds all the earned resources once the production as ended
     * @param ID    of type int - id of the player
     */
    public void collectNewRes(int ID) {
        game.addResourceToStrongbox(ID);
        game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.WAITING_FOR_ACTION);
    }

    /**
     * Method isExtraProd checks if the chosen Leader Card for the production is actually a Leader Card with extra production ability. id it's true, it
     * prepares the resource that the player must pay
     * @param index     of type int - index of the chosen card in the active leader deck
     * @param ID        of type int - ID of the player
     */
    public void isExtraProd(int index, int ID) {
            try {
                if((index == 0 && !game.getPlayerById(ID).isLeader1ProdDone()) || (index == 1 && !game.getPlayerById(ID).isLeader2ProdDone())) {
                    LeaderCard leaderCard = game.getPlayerById(ID).getPersonalBoard().getActiveLeader().getCards().get(index);
                    if (!(leaderCard instanceof ExtraProdLCard)) {
                        game.setCommunication(ID, CommunicationList.INVALID_MOVE);
                        game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.A_PAYMENT);
                    } else if (game.getPlayerById(ID).getPersonalBoard().getStrongbox().canIPay(1, ((ExtraProdLCard) leaderCard).getInput()) ||
                            game.getPlayerById(ID).getPersonalBoard().getWarehouse().canIPay(1, ((ExtraProdLCard) leaderCard).getInput())) {
                        ArrayList<ResourceType> resForLeader = new ArrayList<>();
                        resForLeader.add(((ExtraProdLCard) leaderCard).getInput());
                        game.setChosenLeader(leaderCard, ID);
                        if(index == 0){
                            game.getPlayerById(ID).setLeader1ProdDone(true);
                        }
                        else {
                            game.getPlayerById(ID).setLeader2ProdDone(true);
                        }
                        game.setResToPay(resForLeader);
                        game.moveFaithPosByID(ID, 1);
                        game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.SELECT_RES);
                    } else {
                        game.setCommunication(ID, CommunicationList.INVALID_MOVE);
                        game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.A_PAYMENT);
                    }
                }
                else{
                    game.setCommunication(ID, CommunicationList.INVALID_MOVE);
                    game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.A_PAYMENT);
                }
            } catch (IndexOutOfBoundsException e) {
                game.setCommunication(ID, CommunicationList.INVALID_MOVE);
                game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.A_PAYMENT);
            }
        }

    /**
     * this method checks if the card can be activated.
     * If so, it calls a method to do it, otherwise it sets a proper error in the communication.
     */
    public void playLeaderCard(int ID, DiscountLCard dc) {

        if (game.getPlayerById(ID).getPersonalBoard().checkLCardRequirements(dc)) {
            if (this.game.getPlayerById(ID).getResDiscount1() == null) {
                game.getPlayerById(ID).setResDiscount1(dc.getResType());
            } else if (this.game.getPlayerById(ID).getResDiscount2() == null) {
                this.game.getPlayerById(ID).setResDiscount2(dc.getResType());
            } else {
                game.setCommunication(ID, CommunicationList.TWO_LEADERS);
                game.setTurn(ID, ActionPhase.WAITING_FOR_ACTION);
                return;
            }
        } else {
            game.setCommunication(ID, CommunicationList.NO_REQUIREMENTS);
            game.setTurn(ID, ActionPhase.WAITING_FOR_ACTION);
            return;
        }
        removeLeaderFromDeck(ID, dc);
    }

    /**
     * this method checks if the card can be activated.
     * If so, it calls a method to do it, otherwise it sets a proper error in the communication.
     */
    public void playLeaderCard(int ID, ExtraDepotLCard sc) {
        if (game.getPlayerById(ID).getPersonalBoard().checkLCardRequirements(sc)) {
            if (this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().getShelves().get(3).getResType() == null) {
                game.getPlayerById(ID).getPersonalBoard().getWarehouse().getShelves().get(3).setResType(sc.getResDepot());
            } else if (this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().getShelves().get(4).getResType() == null) {
                game.getPlayerById(ID).getPersonalBoard().getWarehouse().getShelves().get(4).setResType(sc.getResDepot());
            } else {
                game.setCommunication(ID, CommunicationList.TWO_LEADERS);
                game.setTurn(ID, ActionPhase.WAITING_FOR_ACTION);
                return;
            }
        } else {
            game.setCommunication(ID, CommunicationList.NO_REQUIREMENTS);
            game.setTurn(ID, ActionPhase.WAITING_FOR_ACTION);
            return;
        }
        removeLeaderFromDeck(ID, sc);
    }

    /**
     * this method checks if the card can be activated.
     * If so, it calls a method to do it, otherwise it sets a proper error in the communication.
     */
    public void playLeaderCard(int ID, ExtraProdLCard c) {
        if (game.getPlayerById(ID).getPersonalBoard().checkLCardRequirements(c)) {
            if (this.game.getPlayerById(ID).getInputExtraProduction1() == null) {
                game.getPlayerById(ID).setInputExtraProduction1(c.getInput());
            } else if (this.game.getPlayerById(ID).getInputExtraProduction2() == null) {
                game.getPlayerById(ID).setInputExtraProduction2(c.getInput());
            } else {
                game.setCommunication(ID, CommunicationList.TWO_LEADERS);
                game.setTurn(ID, ActionPhase.WAITING_FOR_ACTION);
                return;
            }
        } else {
            game.setCommunication(ID, CommunicationList.NO_REQUIREMENTS);
            game.setTurn(ID, ActionPhase.WAITING_FOR_ACTION);
            return;
        }
        removeLeaderFromDeck(ID, c);
    }

    /**
     * this method checks if the card can be activated.
     * If so, it calls a method to do it, otherwise it sets a proper error in the communication.
     */
    public void playLeaderCard(int ID, WhiteTrayLCard wc) {
        if (game.getPlayerById(ID).getPersonalBoard().checkLCardRequirements(wc)) {
            if (this.game.getPlayerById(ID).getWhiteConversion1() == null) {
                game.getPlayerById(ID).setWhiteConversion1(wc.getResType());
            } else if (this.game.getPlayerById(ID).getWhiteConversion2() == null) {
                game.getPlayerById(ID).setWhiteConversion2(wc.getResType());
            } else {
                game.setCommunication(ID, CommunicationList.TWO_LEADERS);
                game.setTurn(ID, ActionPhase.WAITING_FOR_ACTION);
                return;
            }
        } else {
            game.setCommunication(ID, CommunicationList.NO_REQUIREMENTS);
            game.setTurn(ID, ActionPhase.WAITING_FOR_ACTION);
            return;
        }

        removeLeaderFromDeck(ID, wc);

    }


    public void removeLeaderFromDeck(int ID, LeaderCard lc) {
        this.game.setNewPlayerCards(ID, lc);
    }

    /**
     * Method discardLeaderCard checks whether @param lc can be discarded from Player with ID @param ID.
     * @param initialPhase is true if game is in its initial phase.
     */

    public synchronized void discardLeaderCard(int ID, LeaderCard lc, boolean initialPhase) {
        int i = 0;
        LeaderDeck newLD;
        while (!(lc.equals(this.game.getPlayerById(ID).getLeaderDeck().getCards().get(i)))) {
            i++;
        }
        if (this.game.getPlayerById(ID).getLeaderDeck().getCards().size() > 0) {
            newLD = new LeaderDeck(this.game.getPlayerById(ID).getLeaderDeck().getCards());
            newLD.getCards().remove(i);
            game.discard(ID, newLD);


            if (initialPhase) {
                game.setLeaderCardsToDiscard(ID, game.getPlayerById(ID).getLeaderCardsToDiscard() - 1);
            } else {
                game.moveFaithPosByID(ID, 1);
                game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.WAITING_FOR_ACTION);
            }
        } else {
            game.setCommunication(ID, CommunicationList.ZERO_CARDS);
            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.WAITING_FOR_ACTION);

        }
    }


    /**
     * Method setBoughtRes addsa all the earned resource in the temporary containers in the Strongbox class
     * @param r     of type ResourceType - resource to be added
     * @param ID    of type int - id of the player
     */
    public void setBoughtRes(ResourceType r, int ID) {
            if (r == ResourceType.COIN)
                game.addEarnedResourcesByID(ID, 1, 0, 0, 0);
            else if (r == ResourceType.STONE)
                game.addEarnedResourcesByID(ID, 0, 1, 0, 0);
            else if (r == ResourceType.SERVANT)
                game.addEarnedResourcesByID(ID, 0, 0, 1, 0);
            else if (r == ResourceType.SHIELD)
                game.addEarnedResourcesByID(ID, 0, 0, 0, 1);
        }


    /**
     * Method setBasicProdPayment adds the resources chosen for the basic production
      * @param resources    of type ArrayList<ResourceType> - is the array that contains the resources to be added
     * @param ID            of type int - id of the player
     */
    public void setBasicProdPayment(ArrayList<ResourceType> resources, int ID) {
        int[] resArray = new int[4];
        resArray[0] = (int) resources.stream().filter(x -> x.equals(ResourceType.COIN)).count();
        resArray[1] = (int) resources.stream().filter(x -> x.equals(ResourceType.STONE)).count();
        resArray[2] = (int) resources.stream().filter(x -> x.equals(ResourceType.SERVANT)).count();
        resArray[3] = (int) resources.stream().filter(x -> x.equals(ResourceType.SHIELD)).count();

        if (!game.getPlayerById(ID).getPersonalBoard().totalPaymentChecker(resArray)) {
            game.setCommunication(ID, CommunicationList.NOT_ENOUGH_RES);
            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.A_PAYMENT);
        } else {
            game.setResToPay(resources);
            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.BASIC);
        }
    }

    /**
     * method setDevPayment prepares the input resources needed for the production of a development card
     * @param ID        of type int - id of the player
     * @param index     of type int - index of the chosen slot of the player's personal board
     */
    public void setDevPayment(int ID, int index) {
        try {
            if (game.getPlayerById(ID).isDev1ProdDone() && index == 0) {
                game.setCommunication(ID, CommunicationList.INVALID_MOVE);
                game.setTurn(ID, ActionPhase.A_PAYMENT);
            } else if (game.getPlayerById(ID).isDev2ProdDone() && index == 1) {
                game.setCommunication(ID, CommunicationList.INVALID_MOVE);
                game.setTurn(ID, ActionPhase.A_PAYMENT);
            } else if (game.getPlayerById(ID).isDev3ProdDone() && index == 2) {
                game.setCommunication(ID, CommunicationList.INVALID_MOVE);
                game.setTurn(ID, ActionPhase.A_PAYMENT);
            } else {
                switch (index) {
                    case 0 -> game.getPlayerById(ID).setDev1ProdDone(true);
                    case 1 -> game.getPlayerById(ID).setDev2ProdDone(true);
                    case 2 -> game.getPlayerById(ID).setDev3ProdDone(true);
                }
                ArrayList<ResourceType> resources = new ArrayList<>();
                for (int i = 0; i < game.getPlayerById(ID).getPersonalBoard().getCardSlot().get(index).getCards().get(0).getInputRes()[0]; i++)
                    resources.add(ResourceType.COIN);
                for (int i = 0; i < game.getPlayerById(ID).getPersonalBoard().getCardSlot().get(index).getCards().get(0).getInputRes()[1]; i++)
                    resources.add(ResourceType.STONE);
                for (int i = 0; i < game.getPlayerById(ID).getPersonalBoard().getCardSlot().get(index).getCards().get(0).getInputRes()[2]; i++)
                    resources.add(ResourceType.SERVANT);
                for (int i = 0; i < game.getPlayerById(ID).getPersonalBoard().getCardSlot().get(index).getCards().get(0).getInputRes()[3]; i++)
                    resources.add(ResourceType.SHIELD);
                game.setResToPay(resources);
                int[] toCheck = {(int)resources.stream().filter(x -> x == ResourceType.COIN).count(),
                        (int)resources.stream().filter(x -> x == ResourceType.STONE).count(),
                        (int)resources.stream().filter(x -> x == ResourceType.SERVANT).count(),
                        (int)resources.stream().filter(x -> x == ResourceType.SHIELD).count()};
                if(game.getPlayerById(ID).getPersonalBoard().totalPaymentChecker(toCheck)) {
                    for (int i = 0; i < game.getPlayerById(ID).getPersonalBoard().getCardSlot().get(index).getCards().get(0).getOutputRes()[0]; i++)
                        setBoughtRes(ResourceType.COIN, ID);
                    for (int i = 0; i < game.getPlayerById(ID).getPersonalBoard().getCardSlot().get(index).getCards().get(0).getOutputRes()[1]; i++)
                        setBoughtRes(ResourceType.STONE, ID);
                    for (int i = 0; i < game.getPlayerById(ID).getPersonalBoard().getCardSlot().get(index).getCards().get(0).getOutputRes()[2]; i++)
                        setBoughtRes(ResourceType.SERVANT, ID);
                    for (int i = 0; i < game.getPlayerById(ID).getPersonalBoard().getCardSlot().get(index).getCards().get(0).getOutputRes()[3]; i++)
                        setBoughtRes(ResourceType.SHIELD, ID);
                    if (game.getPlayerById(ID).getPersonalBoard().getCardSlot().get(index).getCards().get(0).getOutputRes()[4] != 0) {
                        game.moveFaithPosByID(ID, game.getPlayerById(ID).getPersonalBoard().getCardSlot().get(index).getCards().get(0).getOutputRes()[4]);
                    }
                    game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.D_PAYMENT);
                }
                else{
                    game.setCommunication(ID, CommunicationList.INVALID_MOVE);
                    game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.A_PAYMENT);
                }
            }
        }catch(IndexOutOfBoundsException e){
            game.setCommunication(ID, CommunicationList.INVALID_MOVE);
            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.A_PAYMENT);
        }

    }

    @Override
    public void update(Message message) {

    }

    @Override
    public void update(Nickname message) {
        setNickname(message);
    }




    @Override
    public void update(ObjectMessage message) {

    }

    @Override
    public void update(ManageResourceMessage message) {
        if(!checkTurn(message.getID()))
            return;
        swapShelves(message.getShelf1(), message.getShelf2(), message.getID());
    }

    @Override
    public void update(MarketMessage message) {
        if (!checkTurn(message.getID()))
            return;
        if (actionDone) {
            game.setCommunication(message.getID(), CommunicationList.INVALID_MOVE);
            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.WAITING_FOR_ACTION);
        } else {
            actionDone = true;
            goToMarket(message.isRow(), message.getIndex(), message.getID());
        }
    }



    @Override
    public void update(PlaceResourceMessage message) {
        if(checkTurn(message.getID()) || message.isInitialPhase()) {
            placeRes(message.getRes(), message.getShelf(), message.getID(), message.isDiscard(), message.isInitialPhase());
        }
    }

    @Override
    public void update(BuyDevCardMessage message) {
        if (!checkTurn(message.getID()))
            return;
        if (actionDone) {
            game.setCommunication(message.getID(), CommunicationList.INVALID_MOVE);
            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.WAITING_FOR_ACTION);
        } else {
            if (game.getTurn().getPhase().equals(ActionPhase.WAITING_FOR_ACTION)) {
                handleChosenDevCard(message.getChosenIndex(), message.getID());
            } else if (game.getTurn().getPhase().equals(ActionPhase.B_PAYMENT)) {
                payRes(message.isPayFrom(), message.getID(), ActionPhase.B_PAYMENT);
            } else if (game.getTurn().getPhase().equals(ActionPhase.CHOOSE_SLOT)) {
                placeDevCard(message.getID(), message.getSlot());
            }
        }

    }

    /**
     * this method manages the PlayLeaderMessages, redirecting to the method to discard the card or to the proper method to activate it.
     */
    @Override
    public void update(PlayLeaderMessage message) {
        if(checkTurn(message.getID()) || message.isInitialPhase()) {
            if (message.getAction()) {
                LeaderCard c = message.getLc();
                switch (c.getType()) {
                    case 1 -> playLeaderCard(message.getID(), (DiscountLCard) c);
                    case 2 -> playLeaderCard(message.getID(), (ExtraDepotLCard) c);
                    case 3 -> playLeaderCard(message.getID(), (ExtraProdLCard) c);
                    case 4 -> playLeaderCard(message.getID(), (WhiteTrayLCard) c);
                }
            } else {
                discardLeaderCard(message.getID(), message.getLc(), message.isInitialPhase());
            }
        }
    }

    @Override
    public void update(ProductionMessage message) {
        if (!checkTurn(message.getID()))
            return;
        if (actionDone) {
            game.setCommunication(message.getID(), CommunicationList.INVALID_MOVE);
            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.WAITING_FOR_ACTION);
        } else {
            if (message.isEndAction()) {
                game.getPlayerById(message.getID()).setAllFalse();
                collectNewRes(message.getID());
                actionDone = true;
            } else if (game.getTurn().getPhase() == ActionPhase.WAITING_FOR_ACTION || game.getTurn().getPhase() == ActionPhase.A_PAYMENT) {
                setDevPayment(message.getID(), message.getIndex());
            } else if (game.getTurn().getPhase() == ActionPhase.D_PAYMENT)
                payRes(message.isPayFrom(), message.getID(), ActionPhase.D_PAYMENT);
        }
    }


    @Override
    public void update(EndTurnMessage message) {
        if(!checkTurn(message.getID()))
            return;

        actionDone = false;
        game.endTurn(message.getID());


    }

    @Override
    public void update(BasicProductionMessage message) {
        if (!checkTurn(message.getID()))
            return;
        if (actionDone) {
            game.setCommunication(message.getID(), CommunicationList.INVALID_MOVE);
            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.WAITING_FOR_ACTION);
        } else {
            if (game.getTurn().getPhase().equals((ActionPhase.BASIC))) {
                if (!game.getPlayerById(message.getID()).isBasicProdDone()) {
                    game.getPlayerById(message.getID()).setBasicProdDone(true);
                    setBoughtRes(message.getBoughtRes(), game.getTurn().getPlayerPlayingID());
                    game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.PAYMENT);
                } else {
                    game.setCommunication(message.getID(), CommunicationList.INVALID_MOVE);
                    game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.A_PAYMENT);
                }

            } else if (game.getTurn().getPhase().equals(ActionPhase.WAITING_FOR_ACTION) || game.getTurn().getPhase().equals(ActionPhase.A_PAYMENT)) {
                ArrayList<ResourceType> resources = new ArrayList<>();
                resources.add(message.getPaidRes1());
                resources.add(message.getPaidRes2());
                setBasicProdPayment(resources, game.getTurn().getPlayerPlayingID());
            } else if (game.getTurn().getPhase().equals(ActionPhase.PAYMENT)) {
                payRes(message.isPayFrom(), message.getID(), ActionPhase.PAYMENT);
            }
        }
    }

    @Override
    public void update(LeaderProductionMessage message) {
        if (!checkTurn(message.getID()))
            return;

        if (actionDone) {
            game.setCommunication(message.getID(), CommunicationList.INVALID_MOVE);
            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.WAITING_FOR_ACTION);
        } else {
            if (game.getTurn().getPhase() == ActionPhase.WAITING_FOR_ACTION || game.getTurn().getPhase() == ActionPhase.A_PAYMENT) {
                if(message.getIndex() == 0 && !game.getPlayerById(message.getID()).isLeader1ProdDone()){
                    isExtraProd(message.getIndex(), message.getID());
                }
                else if(message.getIndex() == 1 && !game.getPlayerById(message.getID()).isLeader2ProdDone()){
                    isExtraProd(message.getIndex(), message.getID());
                }
                else{
                    game.setCommunication(message.getID(), CommunicationList.INVALID_MOVE);
                    game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.A_PAYMENT);
                }
            } else if (game.getTurn().getPhase() == ActionPhase.SELECT_RES) {
                setBoughtRes(message.getWantedRes(), message.getID());
                game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.PAYMENT);
            }
        }
    }




}












