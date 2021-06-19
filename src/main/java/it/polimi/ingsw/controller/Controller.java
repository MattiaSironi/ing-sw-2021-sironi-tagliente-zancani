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
    private boolean actionDone;

    public Controller(Game game) {
        this.game = game;
    }

    public boolean checkTurn(int ID){
        return ID == game.getTurn().getPlayerPlayingID();

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
            game.setCommunication(nickname.getID(), CommunicationList.NICK_NOT_VALID);
        } else {
            game.setCommunication(nickname.getID(), CommunicationList.NICK_VALID);
            game.getPlayers().add(new Player(nickname.getID(), nickname.getString()));
            if (game.getNumPlayer() == 1) {
                game.getPlayerById(0).getPersonalBoard().getFaithTrack().setLoriPos(0);
            }
            if (game.getPlayers().size() == game.getNumPlayer()) {

                game.giveLeaderCards();


                /* TESTING */
//
//                LeaderCard leaderCard = new ExtraProdLCard(3, 4, CardColor.YELLOW, ResourceType.SHIELD);
//                game.getPlayerById(0).getPersonalBoard().getActiveLeader().getCards().add(leaderCard);
//
//
//                game.getPlayerById(0).getPersonalBoard().getWarehouse().getShelves().get(3).setResType(ResourceType.COIN);
//                game.getPlayerById(0).getPersonalBoard().getWarehouse().getShelves().get(4).setResType(ResourceType.SERVANT);
//                game.getPlayerById(1).getPersonalBoard().getWarehouse().getShelves().get(3).setResType(ResourceType.SHIELD);
//                game.getPlayerById(1).getPersonalBoard().getWarehouse().getShelves().get(4).setResType(ResourceType.STONE);
//                game.getPlayerById(0).getPersonalBoard().getWarehouse().getShelves().get(3).setCount(2);
//                game.getPlayerById(0).getPersonalBoard().getWarehouse().getShelves().get(4).setCount(1);
//                game.getPlayerById(1).getPersonalBoard().getWarehouse().getShelves().get(3).setCount(1);
//
////                LeaderCard leaderCard1 = new WhiteTrayLCard(4,5,ResourceType.SERVANT,CardColor.BLUE,CardColor.YELLOW);
////                LeaderCard leaderCard2 = new WhiteTrayLCard(4,5,ResourceType.COIN,CardColor.GREEN,CardColor.PURPLE);
////                game.getPlayerById(0).getPersonalBoard().getActiveLeader().getCards().add(leaderCard1);
////                game.getPlayerById(0).getPersonalBoard().getActiveLeader().getCards().add(leaderCard2);
////                game.getPlayerById(0).setWhiteConversion2(ResourceType.COIN);
////                game.getPlayerById(0).setWhiteConversion1(ResourceType.SERVANT);
//
//
//                game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.COIN, 10);
//                game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.STONE, 10);
//                game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.SERVANT, 10);
//                game.getPlayerById(0).getPersonalBoard().getStrongbox().addResource(ResourceType.SHIELD, 10);
//                game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.COIN, 0);
//                game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.STONE, 1);
//                game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.SHIELD, 2);
//                game.getPlayerById(0).getPersonalBoard().getWarehouse().addResource(ResourceType.SHIELD, 2);
//game.getPlayerById(0).getPersonalBoard().getFaithTrack().setMarker(23);
//game.setFirstVatican(true);
//game.setSecondVatican(true);


                /* END TESTING */


                initialPhase();
            }
        }
    }

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

    public void placeDevCard(int ID, int slot) {
        if (game.getPlayerById(ID).getPersonalBoard().getCardSlot().get(slot).getCards().size() == 0 && game.getBoard().getMatrix().getChosenCard().getLevel() == 1) {
            game.addDevCardToPlayer(ID, slot);
            actionDone = true;
            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.WAITING_FOR_ACTION);
            if (game.getPlayers().size() == 1 && game.checkColumnEmpty()) {
                game.setGameOver(true);
            }
        } else if (game.getPlayerById(ID).getPersonalBoard().getCardSlot().get(slot).getCards().size() != 0 &&
                game.getPlayerById(ID).getPersonalBoard().getCardSlot().get(slot).getCards().get(0).getLevel() == game.getBoard().getMatrix().getChosenCard().getLevel() - 1) {
            game.addDevCardToPlayer(ID, slot);
            actionDone = true;
            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.WAITING_FOR_ACTION);
        } else {
            game.setCommunication(ID, CommunicationList.INVALID_MOVE);
            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.CHOOSE_SLOT);
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
        int i;
        int h;
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
                r2 = -1;
        }
        res1 = this.FromIntToRes(r1);
        res2 = this.FromIntToRes(r2);


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
                    case 1 -> this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().
                            setEarnedCoin(this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().getEarnedCoin() + d.getOutputRes()[i]);
                    case 2 -> this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().
                            setEarnedStone(this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().getEarnedStone() + d.getOutputRes()[i]);
                    case 3 -> this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().
                            setEarnedServant(this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().getEarnedServant() + d.getOutputRes()[i]);
                    case 4 -> this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().
                            setEarnedShield(this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().getEarnedShield() + d.getOutputRes()[i]);

                }
            }
        }

        this.game.moveFaithPosByID(ID, d.getOutputRes()[4]);


    }


//    public void payResources(int ID, ArrayList<ResourceType> paidResFromWarehouse, ArrayList<ResourceType> paidResFromStrongbox, ArrayList<ResourceType> boughtRes, int index) {
//
//        int coinWare = (int) paidResFromWarehouse.stream().filter(x -> x.equals(ResourceType.COIN)).count();
//        int stoneWare = (int) paidResFromWarehouse.stream().filter(x -> x.equals(ResourceType.STONE)).count();
//        int servantWare = (int) paidResFromWarehouse.stream().filter(x -> x.equals(ResourceType.SERVANT)).count();
//        int shieldWare = (int) paidResFromWarehouse.stream().filter(x -> x.equals(ResourceType.SHIELD)).count();
//        int coinStrong = (int) paidResFromStrongbox.stream().filter(x -> x.equals(ResourceType.COIN)).count();
//        int stoneStrong = (int) paidResFromStrongbox.stream().filter(x -> x.equals(ResourceType.STONE)).count();
//        int servantStrong = (int) paidResFromStrongbox.stream().filter(x -> x.equals(ResourceType.SERVANT)).count();
//        int shieldStrong = (int) paidResFromStrongbox.stream().filter(x -> x.equals(ResourceType.SHIELD)).count();
//
//        if(game.getPlayerById(ID).isDev1ProdDone() && index == 0){
//            game.setCommunication(ID, CommunicationList.INVALID_MOVE);
//            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.A_PAYMENT);
//            return;
//        }
//        else if(game.getPlayerById(ID).isDev2ProdDone() && index == 1){
//            game.setCommunication(ID, CommunicationList.INVALID_MOVE);
//            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.A_PAYMENT);
//            return;
//        }
//        else if(game.getPlayerById(ID).isDev3ProdDone() && index == 2){
//            game.setCommunication(ID, CommunicationList.INVALID_MOVE);
//            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.A_PAYMENT);
//            return;
//        }
//
//
//
//        if (!(this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().canIPay(coinWare, ResourceType.COIN)) ||
//                !(this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().canIPay(stoneWare, ResourceType.STONE)) ||
//                !(this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().canIPay(servantWare, ResourceType.SERVANT)) ||
//                !(this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().canIPay(shieldWare, ResourceType.SHIELD))) {
//            game.setCommunication(ID, CommunicationList.NOT_ENOUGH_RES);
//            this.game.setTurn(game.getTurn().getPlayerPlayingID(), game.getTurn().getPhase());
//        } else if (!(this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().canIPay(coinStrong, ResourceType.COIN)) ||
//                !(this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().canIPay(stoneStrong, ResourceType.STONE)) ||
//                !(this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().canIPay(servantStrong, ResourceType.SERVANT)) ||
//                !(this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().canIPay(shieldStrong, ResourceType.SHIELD))) {
//            game.setCommunication(ID, CommunicationList.NOT_ENOUGH_RES);
//            this.game.setTurn(game.getTurn().getPlayerPlayingID(), game.getTurn().getPhase());
//        } else {
//            int faithPoints = (int) boughtRes.stream().filter(x -> x.equals(ResourceType.FAITH_POINT)).count();
//            if (!(faithPoints == 0)) {
//                game.moveFaithPosByID(ID, faithPoints);
//            }
//            for (ResourceType r : paidResFromWarehouse) {
//                if (game.getPlayerById(ID).getPersonalBoard().getWarehouse().getShelves().get(3).getResType() == r &&
//                        game.getPlayerById(ID).getPersonalBoard().getWarehouse().getShelves().get(3).getCount() != 0) {
//                    this.game.payFromFirstExtraShelf(ID, 1);
//                } else if (game.getPlayerById(ID).getPersonalBoard().getWarehouse().getShelves().get(4).getResType() == r &&
//                        game.getPlayerById(ID).getPersonalBoard().getWarehouse().getShelves().get(4).getCount() != 0) {
//                    this.game.payFromSecondExtraShelf(ID, 1);
//                } else {
//                    game.payFromWarehouse(1, r, ID);
//                }
//            }
//            for (ResourceType r : paidResFromStrongbox) {
//                game.payFromStrongbox(1, r, ID);
//            }
//
//            int earnedCoin = (int) boughtRes.stream().filter(x -> x.equals(ResourceType.COIN)).count();
//            int earnedStone = (int) boughtRes.stream().filter(x -> x.equals(ResourceType.STONE)).count();
//            int earnedServant = (int) boughtRes.stream().filter(x -> x.equals(ResourceType.SERVANT)).count();
//            int earnedShield = (int) boughtRes.stream().filter(x -> x.equals(ResourceType.SHIELD)).count();
//
//            game.addEarnedResourcesByID(ID, earnedCoin, earnedStone, earnedServant, earnedShield);
//            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.A_PAYMENT);
//        }
//    }

    public void collectNewRes(int ID) {
        game.addResourceToStrongbox(ID);
        game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.WAITING_FOR_ACTION);
    }

    public void isExtraProd(int index, int ID) {
            try {
                if((index == 0 && !game.getPlayerById(ID).isLeader1ProdDone()) || (index == 1 && !game.getPlayerById(ID).isLeader2ProdDone())) {
                    LeaderCard leaderCard = game.getPlayerById(ID).getPersonalBoard().getActiveLeader().getCards().get(index);
                    if (!(leaderCard instanceof ExtraProdLCard)) {
                        System.out.println("gig1");
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
                    System.out.println("gig0");
                    game.setCommunication(ID, CommunicationList.INVALID_MOVE);
                    game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.A_PAYMENT);
                }
            } catch (IndexOutOfBoundsException e) {
                game.setCommunication(ID, CommunicationList.INVALID_MOVE);
                game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.A_PAYMENT);
            }
        }

    public void PlayLeaderCard(int ID, DiscountLCard dc) {

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
        RemoveLeaderFromDeck(ID, dc);
    }

    public void PlayLeaderCard(int ID, ExtraDepotLCard sc) {
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
        RemoveLeaderFromDeck(ID, sc);
    }

    public void PlayLeaderCard(int ID, ExtraProdLCard c) {
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
        RemoveLeaderFromDeck(ID, c);
    }

    public void PlayLeaderCard(int ID, WhiteTrayLCard wc) {
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

        RemoveLeaderFromDeck(ID, wc);

    }


    public void RemoveLeaderFromDeck(int ID, LeaderCard lc) {
        this.game.setNewPlayerCards(ID, lc);
    }

    public synchronized void DiscardLeaderCard(int ID, LeaderCard lc, boolean initialPhase) {
        int i = 0;
        LeaderDeck newLD;
        boolean found = false;
        while (!(lc.equals(this.game.getPlayerById(ID).getLeaderDeck().getCards().get(i)))) {
            i++;
        }
        if (this.game.getPlayerById(ID).getLeaderDeck().getCards().size() > 0) {
            newLD = new LeaderDeck(this.game.getPlayerById(ID).getLeaderDeck().getCards());
            newLD.getCards().remove(i);
            game.discard(ID, newLD);
            // this.game.getPlayerById(ID).setLeaderDeck(newLD);

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

    public void update(BuyDevCardMessage message) {
        if(!checkTurn(message.getID()))
            return;
        if (actionDone) {
            game.setCommunication(message.getID(), CommunicationList.INVALID_MOVE);
            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.WAITING_FOR_ACTION);
        }
        else {
            if (game.getTurn().getPhase().equals(ActionPhase.WAITING_FOR_ACTION)) {
                handleChosenDevCard(message.getChosenIndex(), message.getID());
            } else if (game.getTurn().getPhase().equals(ActionPhase.B_PAYMENT)) {
                payRes(message.isPayFrom(), message.getID(), ActionPhase.B_PAYMENT);
            } else if (game.getTurn().getPhase().equals(ActionPhase.CHOOSE_SLOT)) {
                placeDevCard(message.getID(), message.getSlot());
            }
        }

    }

    @Override
    public void update(PlayLeaderMessage message) {
        if(checkTurn(message.getID()) || message.isInitialPhase()) {
            if (message.getAction()) {
                LeaderCard c = message.getLc();
                switch (c.getType()) {
                    case 1 -> PlayLeaderCard(message.getID(), (DiscountLCard) c);
                    case 2 -> PlayLeaderCard(message.getID(), (ExtraDepotLCard) c);
                    case 3 -> PlayLeaderCard(message.getID(), (ExtraProdLCard) c);
                    case 4 -> PlayLeaderCard(message.getID(), (WhiteTrayLCard) c);
                }
            } else {
                DiscardLeaderCard(message.getID(), message.getLc(), message.isInitialPhase());
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

    private void setDevPayment(int ID, int index) {
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
                for (ResourceType resource : game.getBoard().getMatrix().getResToPay()
                ) {
                    System.out.println(resource.toString());

                }
                for (int i = 0; i < game.getPlayerById(ID).getPersonalBoard().getCardSlot().get(index).getCards().get(0).getOutputRes()[0]; i++)
                    setBoughtRes(ResourceType.COIN, ID);
                for (int i = 0; i < game.getPlayerById(ID).getPersonalBoard().getCardSlot().get(index).getCards().get(0).getOutputRes()[1]; i++)
                    setBoughtRes(ResourceType.STONE, ID);
                for (int i = 0; i < game.getPlayerById(ID).getPersonalBoard().getCardSlot().get(index).getCards().get(0).getOutputRes()[2]; i++)
                    setBoughtRes(ResourceType.SERVANT, ID);
                for (int i = 0; i < game.getPlayerById(ID).getPersonalBoard().getCardSlot().get(index).getCards().get(0).getOutputRes()[3]; i++)
                    setBoughtRes(ResourceType.SHIELD, ID);
                if(game.getPlayerById(ID).getPersonalBoard().getCardSlot().get(index).getCards().get(0).getOutputRes()[4] != 0){
                    game.moveFaithPosByID(ID, game.getPlayerById(ID).getPersonalBoard().getCardSlot().get(index).getCards().get(0).getOutputRes()[4]);
                }
                game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.D_PAYMENT);
            }
        }catch(IndexOutOfBoundsException e){
            game.setCommunication(ID, CommunicationList.INVALID_MOVE);
            game.setTurn(game.getTurn().getPlayerPlayingID(), ActionPhase.A_PAYMENT);
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

    @Override
    public void update(GameOverMessage message) {

    }


}












