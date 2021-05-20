package it.polimi.ingsw.controller;


import it.polimi.ingsw.message.ActionMessages.*;
import it.polimi.ingsw.message.CommonMessages.*;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observer.Observer;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Controller implements Observer<Message> {
    private Game game;

    public Controller(Game game) {
        this.game = game;
    }

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
        if (found) {
            game.reportError(new Nickname(nickname.getString(), nickname.getID(), false));
        } else {
            game.reportError(new Nickname(nickname.getString(), nickname.getID(), true));
            game.getPlayers().add(new Player(nickname.getID(), nickname.getString()));

            if (game.getPlayers().size() == game.getNumPlayer()) {

                /* testing */

                game.giveLeaderCards();

//                game.getPlayerById(0).setWhiteConversion1(ResourceType.SHIELD);
//                game.getPlayerById(0).getPersonalBoard().getWarehouse().getShelves().set(3, new Shelf(ResourceType.COIN, 0));
//
//
//                game.getPlayerById(1).setWhiteConversion1(ResourceType.COIN);
//                game.getPlayerById(1).setWhiteConversion2(ResourceType.SERVANT);
//
//                LeaderCard l1 = new DiscountLCard(1,3,CardColor.BLUE,CardColor.YELLOW,ResourceType.COIN);
//                LeaderCard l2 = new ExtraDepotLCard(2,2,ResourceType.SERVANT,ResourceType.SHIELD);
//                LeaderCard l3 = new ExtraProdLCard(3,3,CardColor.GREEN,ResourceType.STONE);
////                LeaderCard l4 = new WhiteTrayLCard(4,4,ResourceType.COIN,CardColor.YELLOW,CardColor.BLUE);
////                ArrayList<LeaderCard> vett = new ArrayList<LeaderCard>();
//                ArrayList<LeaderCard> act = new ArrayList<LeaderCard>();
//                act.add(0,l2);
//                act.add(1,l3);
////                vett.add(0,l1);
////                vett.add(1,l2);
//                LeaderDeck actDeck = new LeaderDeck(2,1,act);
////                LeaderDeck deck = new LeaderDeck(2,1,vett);
////                game.getPlayerById(0).setLeaderDeck(deck);
//                game.getPlayerById(1).getPersonalBoard().setActiveLeader(actDeck);
//
////                game.getPlayerById(2).getPersonalBoard().getWarehouse().getShelves().set(4, new Shelf(ResourceType.SERVANT, 0));
////                game.getPlayerById(2).getPersonalBoard().getWarehouse().getShelves().set(3, new Shelf(ResourceType.STONE, 0));
//                game.getPlayerById(0).getPersonalBoard().getActiveLeader().setCards(new ArrayList<>());
//                game.getPlayerById(0).getPersonalBoard().getActiveLeader().getCards().add(game.getBoard().getLeaderDeck().getCards().get(0));
//                game.getPlayerById(0).getPersonalBoard().getStrongbox().getInfinityShelf().get(2).setCount(4); // 4 servant
//             //   game.getPlayerById(0).getPersonalBoard().getActiveLeader().getCards().add(new ExtraProdLCard(3, 4, CardColor.YELLOW, ResourceType.SHIELD));


//                game.getPlayerById(0).setWhiteConversion1(ResourceType.SHIELD);
//                game.getPlayerById(0).getPersonalBoard().getWarehouse().getShelves().set(3, new Shelf(ResourceType.COIN, 0));
//
//
//                game.getPlayerById(1).setWhiteConversion1(ResourceType.COIN);
//                game.getPlayerById(1).setWhiteConversion2(ResourceType.SERVANT);
//
//                LeaderCard l1 = new DiscountLCard(1,3,CardColor.BLUE,CardColor.YELLOW,ResourceType.COIN);
//                LeaderCard l2 = new ExtraDepotLCard(2,2,ResourceType.SERVANT,ResourceType.SHIELD);
//                LeaderCard l3 = new ExtraProdLCard(3,3,CardColor.GREEN,ResourceType.STONE);
//                LeaderCard l4 = new WhiteTrayLCard(4,4,ResourceType.COIN,CardColor.YELLOW,CardColor.BLUE);
//                ArrayList<LeaderCard> vett = new ArrayList<LeaderCard>();
//                ArrayList<LeaderCard> act = new ArrayList<LeaderCard>();
//                act.add(0,l3);
//               // act.add(1,l4);
//                vett.add(0,l1);
//                vett.add(1,l2);
//                LeaderDeck actDeck = new LeaderDeck(2,1,act);
//                LeaderDeck deck = new LeaderDeck(2,1,vett);
//                game.getPlayerById(0).setLeaderDeck(deck);
//                game.getPlayerById(0).getPersonalBoard().setActiveLeader(actDeck);
//
//                game.getPlayerById(1).getPersonalBoard().getWarehouse().getShelves().set(4, new Shelf(ResourceType.SERVANT, 0));
//                game.getPlayerById(1).getPersonalBoard().getWarehouse().getShelves().set(3, new Shelf(ResourceType.STONE, 0));
//                game.getPlayerById(0).getPersonalBoard().getActiveLeader().setCards(new ArrayList<>());
//                game.getPlayerById(0).getPersonalBoard().getActiveLeader().getCards().add(game.getBoard().getLeaderDeck().getCards().get(0));
//                game.getPlayerById(0).getPersonalBoard().getStrongbox().getInfinityShelf().get(2).setCount(4); // 4 servant
//             //   game.getPlayerById(0).getPersonalBoard().getActiveLeader().getCards().add(new ExtraProdLCard(3, 4, CardColor.YELLOW, ResourceType.SHIELD));
//


//                game.sendGame();
//                game.setTurn(game.getPlayers().get(0).getId(), ActionPhase.WAITING_FOR_ACTION, false, null);
                initialPhase();


            }
        }

    }

    public void initialPhase() {
        Collections.shuffle(game.getPlayers(), new Random(game.getNumPlayer()));
        game.getPlayers().get(0).setInkwell(true);
        game.sendGame();

        for (Player p : game.getPlayers()) {


            switch (game.getPlayers().indexOf(p)) {
                case 0: {

                    game.setCommunication(p.getId(), CommunicationList.FIRST);
                    game.setLeaderCardsToDiscard(p.getId(), 2);


                    break;
                }
                case 1: {
                    game.setCommunication(p.getId(), CommunicationList.SECOND);

                    game.setStartResCountByID(p.getId(), 1);
                    break;
                }
                case 2: {
                    game.setCommunication(p.getId(), CommunicationList.THIRD);
                    game.moveFaithPosByID(p.getId(), 1);
                    game.setStartResCountByID(p.getId(), 1);


                    break;
                }
                case 3: {
                    game.setCommunication(p.getId(), CommunicationList.FOURTH);
                    game.moveFaithPosByID(p.getId(), 1);
                    game.setStartResCountByID(p.getId(), 2);

                    break;
                }
            }
        }

    }

    public void swapShelves(int s1, int s2, int ID) {

        if (((s1 == 3 || s1 == 4) && (s2 == 3 || s2 == 4)) || (s1 == s2) || (s1 < 0) || (s1 > 4) || (s2 < 0) || (s2 > 4)) {
            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.WAITING_FOR_ACTION, true, ErrorList.INVALID_MOVE);
            return;
        }
        if (!(game.swapShelvesByID(s1, s2, ID))) {
            game.setTurn(game.getTurn().getPlayerPlayingID(), game.getTurn().getPhase(), true, ErrorList.INVALID_MOVE);
        } else
            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.WAITING_FOR_ACTION, false, null);
    }

    public void goToMarket(boolean row, int index, int ID) {

        Market m = this.game.getBoard().getMarket();
        ArrayList<Marble> resources = new ArrayList<>();

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
        game.setTurn(ID, ActionPhase.MARKET, false, null);

    }


    public void placeRes(ResourceType r, int shelfIndex, int ID, boolean discard, boolean initialPhase) {

        //mette la risorsa al posto giusto se può
        //manda reportError con ok o ko a seconda che rispetti le regole
        if (initialPhase) {
            int resRemaining = game.getPlayerById(ID).getStartResCount();
            if (this.game.addResourceToWarehouse(ID, shelfIndex, r)) resRemaining--;
            game.setStartResCountByID(ID, resRemaining);
            if (resRemaining == 0) this.game.setLeaderCardsToDiscard(ID, 2);
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
                        game.setTurn(game.getTurn().getPlayerPlayingID(), game.getTurn().getPhase(), true, ErrorList.INVALID_MOVE);
                        return;
                    }
                }
            }

            if (game.getBoard().getMarket().getHand().size() == 0) {
                game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.WAITING_FOR_ACTION, false, null);
            } else
                game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.MARKET, false, null);
        }
    }


    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void discardRes(int ID) {

        ArrayList<Player> others = this.game.getPlayers();
        for (Player p : others) {
            if (p.getId() != ID) {
                this.game.moveFaithPosByID(p.getId(), 1);
            }
        }
    }

    public void handleChosenDevCard(int chosenIndex, int ID) {
        int[] toPay = checkHasDiscount(game.getBoard().getMatrix().getDevDecks().get(chosenIndex).getCards().get(0).getCostRes(), ID);
        if (!game.getPlayerById(ID).getPersonalBoard().totalPaymentChecker(toPay)) {
            game.setTurn(game.getTurn().getPlayerPlayingID(), game.getTurn().getPhase(), true, ErrorList.NOT_ENOUGH_RES);
        } else if (!(checkDevCardPlacement(game.getBoard().getMatrix().getDevDecks().get(chosenIndex).getCards().get(0), game.getPlayerById(ID)))) {
            game.setTurn(game.getTurn().getPlayerPlayingID(), game.getTurn().getPhase(), true, ErrorList.INVALID_MOVE);
        } else {
            game.setResToPay(game.getBoard().getMatrix().getDevDecks().get(chosenIndex).getCards().get(0), ID);
            game.setChosenDevCard(game.getBoard().getMatrix().getDevDecks().get(chosenIndex).getCards().get(0), chosenIndex);
            if (game.getBoard().getMatrix().getResToPay().size() == 0) {
                game.setCommunication(ID, CommunicationList.NO_PAYMENT_NEEDED);
                game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.CHOOSE_SLOT, false, null);
            } else {
                game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.B_PAYMENT, false, null);
            }
        }
    }

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

    public void payRes(boolean payFrom, int ID, boolean turn) {

        if (payFrom) {
            if (!(game.getPlayerById(ID).getPersonalBoard().getWarehouse().canIPay(1, game.getBoard().getMatrix().getResToPay().get(0)))) {
                game.setTurn(game.getTurn().getPlayerPlayingID(), game.getTurn().getPhase(), true, ErrorList.NOT_ENOUGH_RES);
            } else {
                if (game.getPlayerById(ID).getPersonalBoard().getWarehouse().getShelves().get(3).getResType() == game.getBoard().getMatrix().getResToPay().get(0) &&
                        game.getPlayerById(ID).getPersonalBoard().getWarehouse().getShelves().get(3).getCount() != 0) {
                    this.game.payFromFirstExtraShelf(ID, 1);
                } else if (game.getPlayerById(ID).getPersonalBoard().getWarehouse().getShelves().get(4).getResType() == game.getBoard().getMatrix().getResToPay().get(0) &&
                        game.getPlayerById(ID).getPersonalBoard().getWarehouse().getShelves().get(4).getCount() != 0) {
                    this.game.payFromSecondExtraShelf(ID, 1);
                } else
                    game.payFromWarehouse(1, game.getBoard().getMatrix().getResToPay().get(0), ID);
                isEmpty(turn);
            }
        } else {
            if (!(game.getPlayerById(ID).getPersonalBoard().getStrongbox().canIPay(1, game.getBoard().getMatrix().getResToPay().get(0)))) {
                game.setTurn(game.getTurn().getPlayerPlayingID(), game.getTurn().getPhase(), true, ErrorList.NOT_ENOUGH_RES);
            } else {
                game.payFromStrongbox(1, game.getBoard().getMatrix().getResToPay().get(0), ID);
                isEmpty(turn);
            }
        }
    }

    private void isEmpty(boolean turn) {
        game.removeResToPay();
        if (game.getBoard().getMatrix().getResToPay().size() == 0) {
            if (turn)
                game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.A_PAYMENT, false, null);
            else
                game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.CHOOSE_SLOT, false, null);

        } else {
            if (turn)
                game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.PAYMENT, false, null);
            else
                game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.B_PAYMENT, false, null);
        }
    }

    public void placeDevCard(int ID, int slot) {
        if (game.getPlayerById(ID).getPersonalBoard().getCardSlot().get(slot).getCards().size() == 0 && game.getBoard().getMatrix().getChosenCard().getLevel() == 1) {
            game.addDevCardToPlayer(ID, slot);
            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.WAITING_FOR_ACTION, false, null);
        } else if (game.getPlayerById(ID).getPersonalBoard().getCardSlot().get(slot).getCards().size() != 0 &&
                game.getPlayerById(ID).getPersonalBoard().getCardSlot().get(slot).getCards().get(0).getLevel() == game.getBoard().getMatrix().getChosenCard().getLevel() - 1) {
            game.addDevCardToPlayer(ID, slot);
            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.WAITING_FOR_ACTION, false, null);
        } else {
            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.CHOOSE_SLOT, true, ErrorList.INVALID_MOVE);
        }
    }

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

    public void activateDevProduction(int ID, DevCard d, int num1FromWarehouse, int num1FromStrongbox, int num2FromWarehouse, int num2FromStrongbox) {
        int r1 = 0, r2 = 0;
        int i = 0;
        int h = 0;
        ResourceType res1, res2;
        boolean found1 = false;
        boolean found2 = false;
        ArrayList<Shelf> w = this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().getShelves();
        Strongbox st = this.game.getPlayerById(ID).getPersonalBoard().getStrongbox();

        for (i = 0; i < 4 && !found1; i++) {
            if (d.getInputRes()[i] > 0) {
                r1 = i;
                found1 = true;
            }
        }
        for (h = i; i < 4 && !found2; i++) {
            if (d.getInputRes()[i] > 0) {
                r2 = h;
                found2 = true;
            }
            if (!found2)
                r2 = -1; //c'è una sola risorsa input
        }
        res1 = this.FromIntToRes(r1);
        res2 = this.FromIntToRes(r2);


        //pagamenti : non vengono fatti controlli sul fatto che si abbiano abbastanza risorse per pagare
        if (num1FromWarehouse > 0) {
            for (Shelf s : w) {
                if (s.getResType() == res1) {
                    s.setCount(s.getCount() - num1FromWarehouse);
                }
            }
        }
        if (num1FromStrongbox > 0) {
            Shelf newShelf = new Shelf(res1, st.getInfinityShelf().get(r1).getCount() - num1FromStrongbox);
            st.getInfinityShelf().set(r1, newShelf);

        }
        if (num2FromWarehouse > 0) {
            for (Shelf s : w) {
                if (s.getResType() == res2) {
                    s.setCount(s.getCount() - num2FromWarehouse);
                }
            }
        }
        if (num2FromStrongbox > 0) {
            Shelf newShelf = new Shelf(res2, st.getInfinityShelf().get(r2).getCount() - num2FromStrongbox);
            st.getInfinityShelf().set(r2, newShelf);
        }

        for (i = 0; i < 4; i++) {
            if (d.getOutputRes()[i] > 0) {
                switch (i + 1) {
                    case 1 -> {
                        this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().
                                setEarnedCoin(this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().getEarnedCoin() + d.getOutputRes()[i]);
                    }
                    case 2 -> {
                        this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().
                                setEarnedStone(this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().getEarnedStone() + d.getOutputRes()[i]);
                    }
                    case 3 -> {
                        this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().
                                setEarnedServant(this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().getEarnedServant() + d.getOutputRes()[i]);
                    }
                    case 4 -> {
                        this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().
                                setEarnedShield(this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().getEarnedShield() + d.getOutputRes()[i]);
                    }

                }
            }
        }

        this.game.moveFaithPosByID(ID, d.getOutputRes()[4]);


    }

    //TODO aggiungere i punti vittoria se richiesti

    public void addResources(int ID, ArrayList<ResourceType> boughtRes) {
        for (ResourceType r : boughtRes) {
            this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().addResource(r, 1);
        }
    }

    public void payResources(int ID, ArrayList<ResourceType> paidResFromWarehouse, ArrayList<ResourceType> paidResFromStrongbox, ArrayList<ResourceType> boughtRes) {

        int coinWare = (int) paidResFromWarehouse.stream().filter(x -> x.equals(ResourceType.COIN)).count();
        int stoneWare = (int) paidResFromWarehouse.stream().filter(x -> x.equals(ResourceType.STONE)).count();
        int servantWare = (int) paidResFromWarehouse.stream().filter(x -> x.equals(ResourceType.SERVANT)).count();
        int shieldWare = (int) paidResFromWarehouse.stream().filter(x -> x.equals(ResourceType.SHIELD)).count();
        int coinStrong = (int) paidResFromStrongbox.stream().filter(x -> x.equals(ResourceType.COIN)).count();
        int stoneStrong = (int) paidResFromStrongbox.stream().filter(x -> x.equals(ResourceType.STONE)).count();
        int servantStrong = (int) paidResFromStrongbox.stream().filter(x -> x.equals(ResourceType.SERVANT)).count();
        int shieldStrong = (int) paidResFromStrongbox.stream().filter(x -> x.equals(ResourceType.SHIELD)).count();

        if (!(this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().canIPay(coinWare, ResourceType.COIN)) ||
                !(this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().canIPay(stoneWare, ResourceType.STONE)) ||
                !(this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().canIPay(servantWare, ResourceType.SERVANT)) ||
                !(this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().canIPay(shieldWare, ResourceType.SHIELD))) {
            this.game.setTurn(game.getTurn().getPlayerPlayingID(), game.getTurn().getPhase(), true, ErrorList.NOT_ENOUGH_RES);
        } else if (!(this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().canIPay(coinStrong, ResourceType.COIN)) ||
                !(this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().canIPay(stoneStrong, ResourceType.STONE)) ||
                !(this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().canIPay(servantStrong, ResourceType.SERVANT)) ||
                !(this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().canIPay(shieldStrong, ResourceType.SHIELD))) {
            this.game.setTurn(game.getTurn().getPlayerPlayingID(), game.getTurn().getPhase(), true, ErrorList.NOT_ENOUGH_RES);
        } else {
            int faithPoints = (int) boughtRes.stream().filter(x -> x.equals(ResourceType.FAITH_POINT)).count();
            if (!(faithPoints == 0)) {
                game.moveFaithPosByID(ID, faithPoints);
            }
            for (ResourceType r : paidResFromWarehouse) {
                if (game.getPlayerById(ID).getPersonalBoard().getWarehouse().getShelves().get(3).getResType() == r &&
                        game.getPlayerById(ID).getPersonalBoard().getWarehouse().getShelves().get(3).getCount() != 0) {
                    this.game.payFromFirstExtraShelf(ID, 1);
                } else if (game.getPlayerById(ID).getPersonalBoard().getWarehouse().getShelves().get(4).getResType() == r &&
                        game.getPlayerById(ID).getPersonalBoard().getWarehouse().getShelves().get(4).getCount() != 0) {
                    this.game.payFromSecondExtraShelf(ID, 1);
                } else {
                    game.payFromWarehouse(1, r, ID);
                }
            }
            for (ResourceType r : paidResFromStrongbox) {
                game.payFromStrongbox(1, r, ID);
            }

            int earnedCoin = (int) boughtRes.stream().filter(x -> x.equals(ResourceType.COIN)).count();
            int earnedStone = (int) boughtRes.stream().filter(x -> x.equals(ResourceType.STONE)).count();
            int earnedServant = (int) boughtRes.stream().filter(x -> x.equals(ResourceType.SERVANT)).count();
            int earnedShield = (int) boughtRes.stream().filter(x -> x.equals(ResourceType.SHIELD)).count();

            game.addEarnedResourcesByID(ID, earnedCoin, earnedStone, earnedServant, earnedShield);
            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.A_PAYMENT, false, null);
        }
    }

    public void collectNewRes(int ID) {
        game.addResourceToStrongbox(ID);
        game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.WAITING_FOR_ACTION, false, null);
    }

    public void isExtraProd(int index, int ID) {
        LeaderCard leaderCard = game.getPlayerById(ID).getPersonalBoard().getActiveLeader().getCards().get(index);
        if (!(leaderCard instanceof ExtraProdLCard)) {
            game.setTurn(game.getTurn().getPlayerPlayingID(), game.getTurn().getPhase(), true, ErrorList.INVALID_MOVE);
        } else {
            ArrayList<ResourceType> resForLeader = new ArrayList<>();
            resForLeader.add(((ExtraProdLCard) leaderCard).getInput());
            game.setChosenLeader(leaderCard, ID);
            game.setPaidResForBasic(resForLeader);
            game.moveFaithPosByID(ID, 1);
            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.SELECT_RES, false, null);
        }

    }

    public void useLeaderProduction(int ID, ResourceType r, ResourceType newRes, boolean buyFromWarehouse) { //if true -> pay from warehouse
        if (buyFromWarehouse == true) {
            if (!(this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().canIPay(1, r))) {
                this.game.reportError(new ErrorMessage("You don't have enough resources!", ID));
            } else {
                this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().pay(1, r);
                this.game.moveFaithPosByID(ID, 1);
            }
        }
        if (buyFromWarehouse == false) {
            if (!(this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().canIPay(1, r))) {
                this.game.reportError(new ErrorMessage("You don't have enough resources!", ID));
            } else {
                this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().pay(1, r);
                this.game.moveFaithPosByID(ID, 1);
            }
        }
    }

    public void PlayLeaderCard(int ID, DiscountLCard dc) {
        Player p = null;
        if (game.getPlayerById(ID).getPersonalBoard().checkLCardRequirements(dc)) {
            if (this.game.getPlayerById(ID).getResDiscount1() == null) {
                game.getPlayerById(ID).setResDiscount1(dc.getResType());
            } else if (this.game.getPlayerById(ID).getResDiscount2() == null) {
                this.game.getPlayerById(ID).setResDiscount2(dc.getResType());
            } else {
                game.setTurn(ID, ActionPhase.P_LEADER, true, ErrorList.TWO_LEADERS);
                return;
            }
        } else {
            game.setTurn(ID, ActionPhase.P_LEADER, true, ErrorList.NO_REQUIREMENTS);
            return;
        }
        RemoveLeaderFromDeck(ID, dc);
    }

    public void PlayLeaderCard(int ID, ExtraDepotLCard sc) {
        if (game.getPlayerById(ID).getPersonalBoard().checkLCardRequirements(sc)) {
            if (this.game.getPlayerById(ID).getPersonalBoard().getExtraShelfRes1() == null) {
                game.getPlayerById(ID).getPersonalBoard().setExtraShelfRes1(sc.getResDepot());
                game.getPlayerById(ID).getPersonalBoard().setExtraShelfNum1(0);
            } else if (this.game.getPlayerById(ID).getPersonalBoard().getExtraShelfRes2() == null) {
                game.getPlayerById(ID).getPersonalBoard().setExtraShelfRes2(sc.getResDepot());
                game.getPlayerById(ID).getPersonalBoard().setExtraShelfNum2(0);
            } else {
                game.setTurn(ID, ActionPhase.P_LEADER, true, ErrorList.TWO_LEADERS);
                return;
            }
        } else {
            game.setTurn(ID, ActionPhase.P_LEADER, true, ErrorList.NO_REQUIREMENTS);
            return;
        }
        RemoveLeaderFromDeck(ID, sc);
    }

    public void PlayLeaderCard(int ID, ExtraProdLCard c) {
        if (game.getPlayerById(ID).getPersonalBoard().checkLCardRequirements(c)) {
            if (this.game.getPlayerById(ID).getInputExtraProduction1() == null) {
                game.getPlayerById(ID).setInputExtraProduction1(c.getInput());
            } else if (this.game.getPlayerById(ID).getInputExtraProduction2() == null) {
                game.getPlayerById(ID).setInputExtraProduction2(c.getInput());
            } else {
                game.setTurn(ID, ActionPhase.P_LEADER, true, ErrorList.TWO_LEADERS);
                return;
            }
        } else {
            game.setTurn(ID, ActionPhase.P_LEADER, true, ErrorList.NO_REQUIREMENTS);
            return;
        }
        RemoveLeaderFromDeck(ID, c);
    }

    public void PlayLeaderCard(int ID, WhiteTrayLCard wc) {
        if (game.getPlayerById(ID).getPersonalBoard().checkLCardRequirements(wc)) {
            if (this.game.getPlayerById(ID).getWhiteConversion1() == null) {
                game.getPlayerById(ID).setWhiteConversion1(wc.getResType());
            } else if (this.game.getPlayerById(ID).getWhiteConversion2() == null) {
                game.getPlayerById(ID).setWhiteConversion2(wc.getResType());
            } else {
                game.setTurn(ID, ActionPhase.P_LEADER, true, ErrorList.TWO_LEADERS);
                return;
            }
        } else {
            game.setTurn(ID, ActionPhase.P_LEADER, true, ErrorList.NO_REQUIREMENTS);
            return;
        }

        RemoveLeaderFromDeck(ID, wc);

    }


    public void RemoveLeaderFromDeck(int ID, LeaderCard lc) {
        this.game.setNewPlayerCards(ID, lc);
    }

    public void DiscardLeaderCard(int ID, LeaderCard lc, boolean initialPhase) {
        int i = 0;
        LeaderDeck newLD;
        boolean found = false;
        while (!(lc.equals(this.game.getPlayerById(ID).getLeaderDeck().getCards().get(i)))) {
            i++;
        }
        if (this.game.getPlayerById(ID).getLeaderDeck().getCards().size() > 0) {
            newLD = new LeaderDeck((this.game.getPlayerById(ID).getLeaderDeck().getSize()) - 1, 1, this.game.getPlayerById(ID).getLeaderDeck().getCards());
            newLD.getCards().remove(i);
            game.discard(ID, newLD);
            // this.game.getPlayerById(ID).setLeaderDeck(newLD);

            if (initialPhase) {
                game.setLeaderCardsToDiscard(ID, game.getPlayerById(ID).getLeaderCardsToDiscard() - 1);
            } else {
                game.moveFaithPosByID(ID, 1);
                game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.WAITING_FOR_ACTION, false, null);
            }
        } else
            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.D_LEADER, true, ErrorList.ZERO_CARDS);

    }


    public ResourceType FromIntToRes(int i) {
        switch (i) {
            case 0 -> {
                return ResourceType.COIN;
            }
            case 1 -> {
                return ResourceType.STONE;
            }
            case 2 -> {
                return ResourceType.SERVANT;
            }
            case 3 -> {
                return ResourceType.SHIELD;
            }
            default -> {
                return ResourceType.EMPTY;
            }
        }
    }

    public void setBoughtRes(ResourceType r, int ID, boolean turn) {
        if (r == ResourceType.COIN)
            game.addEarnedResourcesByID(ID, 1, 0, 0, 0);
        else if (r == ResourceType.STONE)
            game.addEarnedResourcesByID(ID, 0, 1, 0, 0);
        else if (r == ResourceType.SERVANT)
            game.addEarnedResourcesByID(ID, 0, 0, 1, 0);
        else if (r == ResourceType.SHIELD)
            game.addEarnedResourcesByID(ID, 0, 0, 0, 1);
        if(turn)
            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.BASIC, false, null);
        else
            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.PAYMENT, false, null);
    }

    public void setBasicProdPayment(ArrayList<ResourceType> resources, int ID) {
        int[] resArray = new int[4];
        resArray[0] = (int) resources.stream().filter(x -> x.equals(ResourceType.COIN)).count();
        resArray[1] = (int) resources.stream().filter(x -> x.equals(ResourceType.STONE)).count();
        resArray[2] = (int) resources.stream().filter(x -> x.equals(ResourceType.SERVANT)).count();
        resArray[3] = (int) resources.stream().filter(x -> x.equals(ResourceType.SHIELD)).count();

        if (!game.getPlayerById(ID).getPersonalBoard().totalPaymentChecker(resArray)) {
            game.setTurn(game.getTurn().getPlayerPlayingID(), game.getTurn().getPhase(), true, ErrorList.NOT_ENOUGH_RES);
        } else {
            game.setPaidResForBasic(resources);
            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.PAYMENT, false, null);
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
    public void update(InputMessage message) {
    }

    @Override
    public void update(IdMessage message) {

    }

    @Override
    public void update(ErrorMessage message) {


    }

    @Override
    public void update(OutputMessage message) {

    }

    @Override
    public void update(ChooseNumberOfPlayer message) {


    }

    @Override
    public void update(PrintableMessage message) {

    }

    @Override
    public void update(ObjectMessage message) {

    }

    @Override
    public void update(ManageResourceMessage message) {
        swapShelves(message.getShelf1(), message.getShelf2(), message.getID());
    }

    @Override
    public void update(MarketMessage message) {
        goToMarket(message.isRow(), message.getIndex(), message.getID());
    }

    @Override
    public void update(ResourceListMessage message) {

    }

    @Override
    public void update(PlaceResourceMessage message) {
        placeRes(message.getRes(), message.getShelf(), message.getID(), message.isDiscard(), message.isInitialPhase());

    }

    public void update(BuyDevCardMessage message) {
        if (game.getTurn().getPhase().equals(ActionPhase.WAITING_FOR_ACTION)) {
            handleChosenDevCard(message.getChoosenIndex(), message.getID());
        } else if (game.getTurn().getPhase().equals(ActionPhase.B_PAYMENT)) {
            payRes(message.isPayFrom(), message.getID(), false);
        } else if (game.getTurn().getPhase().equals(ActionPhase.CHOOSE_SLOT)) {
            placeDevCard(message.getID(), message.getSlot());
        }

    }

    @Override
    public void update(PlayLeaderMessage message) {
        if (message.getAction()) {
            LeaderCard c = message.getLc();
            switch (c.getType()) {
                case 1 -> {
                    PlayLeaderCard(message.getID(), (DiscountLCard) c);
                }
                case 2 -> {
                    PlayLeaderCard(message.getID(), (ExtraDepotLCard) c);
                }
                case 3 -> {
                    PlayLeaderCard(message.getID(), (ExtraProdLCard) c);
                }
                case 4 -> {
                    PlayLeaderCard(message.getID(), (WhiteTrayLCard) c);
                }
            }
        } else {

            DiscardLeaderCard(message.getID(), message.getLc(), message.isInitialPhase());
        }
    }

    @Override
    public void update(ProductionMessage message) {
        if (message.isEndAction())
            collectNewRes(message.getID());
        else if (!(message.getDc() == null)) {
            payResources(message.getID(), message.getResFromWarehouse(), message.getResFromStrongbox(), message.getResToBuy());
        } else
            payResources(message.getID(), message.getResFromWarehouse(), message.getResFromStrongbox(), message.getResToBuy());
    }


    @Override
    public void update(EndTurnMessage message) {
        game.endTurn(message.getID());

    }

    @Override
    public void update(BasicProductionMessage message) {
        if (game.getTurn().getPhase().equals(ActionPhase.WAITING_FOR_ACTION))
            setBoughtRes(message.getBoughtRes(), game.getTurn().getPlayerPlayingID(), true);
        else if (game.getTurn().getPhase().equals((ActionPhase.BASIC))) {
            ArrayList<ResourceType> resources = new ArrayList<>();
            resources.add(message.getPaidRes1());
            resources.add(message.getPaidRes2());
            setBasicProdPayment(resources, game.getTurn().getPlayerPlayingID());
        } else if (game.getTurn().getPhase().equals(ActionPhase.PAYMENT)) {
            payRes(message.isPayFrom(), message.getID(), true);
        }
    }

    @Override
    public void update(LeaderProductionMessage message) {
        if(game.getTurn().getPhase() == ActionPhase.WAITING_FOR_ACTION){
            isExtraProd(message.getIndex(), message.getID());
        }
        else if(game.getTurn().getPhase() == ActionPhase.SELECT_RES){
            setBoughtRes(message.getWantedRes(), message.getID(), false);
        }
        else if(game.getTurn().getPhase() == ActionPhase.PAYMENT);
    }

    @Override
    public void update(EndActionMessage message) {

    }


}












