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
    private int expectedActions;

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
            if (game.getPlayers().size() == game.getNumPlayer())  {
                initialPhase();



            }
        }

    }

    public void initialPhase(){
        Collections.shuffle(game.getPlayers(), new Random(game.getNumPlayer()));
        game.getPlayers().get(0).setInkwell(true);
        game.sendObject(new ObjectMessage(game, -1, -1));
        for(Player p : game.getPlayers()){
            switch (game.getPlayers().indexOf(p)){
                case 0 : {
                    p.setReady(true);
                    if(checkReadyPlayers()){
                        game.sendObject(new ObjectMessage(game, -1, -1));
                        game.endTurn(game.getPlayers().get(game.getPlayers().size()-1).getId());
                    }
                    game.reportError(new ErrorMessage("You are the first!", p.getId()));
                    break;
                }
                case 1 : {
                    p.setStartResCount(1);
                    game.sendSingleResource(null, -1, p.getId(), "");
                    break;
                }
                case 2 : {
                    p.setStartResCount(1);
                    p.moveFaithMarkerPos(1);
                    game.reportError(new ErrorMessage("You received 1 Faith Point!", p.getId()));
                    game.sendSingleResource(null, -1, p.getId(), "");
                    break;
                }
                case 3 : {
                    p.setStartResCount(2);
                    p.moveFaithMarkerPos(1);
                    game.reportError(new ErrorMessage("You received 1 Faith Point!", p.getId()));
                    game.sendSingleResource(null, -1, p.getId(), "");
                    game.sendSingleResource(null, -1, p.getId(), "");
                    break;
                }
            }
        }
    }

    public void swapShelves(int s1, int s2, int ID){
        ArrayList<Shelf> shelves = this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().getShelves();
        Shelf temp;
        if(shelves.get(s1).getCount() <= s2 + 1 && shelves.get(s2).getCount() <= s1 + 1 ){
            temp = shelves.get(s1);
            shelves.set(s1, shelves.get(s2));
            shelves.set(s2, temp);
            this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().setShelves(shelves);
            game.sendObject(new ObjectMessage(this.game.getPlayerById(ID).getPersonalBoard().getWarehouse(), 0, ID));
        }
        else{
            game.reportError(new ErrorMessage("invalid move", ID));
        }
        game.sendActionOver(new EndActionMessage(ID));
    }

    public void goToMarket(boolean row, int index, int ID) {

        Market m = this.game.getBoard().getMarket();
        ArrayList<Marble> resources = new ArrayList<>();

        if (row) {
            resources = m.getRow(index);
            for (int j = 3; j >= 0; j--) {
                if (j == 3) m.setMarble(index, j, m.getMarbleOut());
                else  m.setMarble(index, j, resources.get(j + 1));
            }
        }
        else {
            index = 3 - index;
            resources = m.getColumn(index);
            for (int k = 2; k >= 0; k--) {
                if (k == 2) m.setMarble(k, index, m.getMarbleOut());
                else m.setMarble(k, index, resources.get(k + 1));
            }
        }
        m.setMarbleOut(resources.get(0));
//        expectedActions = resources.size();
        int faith = (int) resources.stream().filter(x -> x.getRes().equals(ResourceType.FAITH_POINT)).count();
//        expectedActions -=faith;
//        if (game.getPlayerById(ID).getWhiteConversion1()== null && game.getPlayerById(ID).getWhiteConversion2()==null )
//            expectedActions -= (int) resources.stream().filter(x -> x.getRes().equals(ResourceType.EMPTY)).count();

        this.game.getPlayerById(ID).moveFaithMarkerPos(faith);
        this.game.checkVatican();
        game.getBoard().getMarket().setHand(resources);
        game.sendObject(new ObjectMessage(this.game.getBoard().getMarket(), 1, ID));
        game.getTurn().setPlayerPlayingID(ID); //TODO TEMPORANEO
        game.getTurn().setPhase("MARKET");
        game.sendObject(new ObjectMessage(game.getTurn(), 10, -1));
   //     game.sendResources(new ResourceListMessage(resources, ID));
//        if (expectedActions==0) game.sendActionOver(new EndActionMessage(ID));
    }


    public void placeRes(ResourceType r, int shelfIndex, int ID)  {
         //mette la risorsa al posto giusto se può
         //manda reportError con ok o ko a seconda che rispetti le regole
        if (r.equals(ResourceType.FAITH_POINT))  {
            discardRes(ID);
            expectedActions--;
            game.getBoard().getMarket().getHand().remove(0);
            game.sendObject(new ObjectMessage(game.getBoard().getMarket(), 1, ID));

        }
        else {
            String s = this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().addResource(r, shelfIndex);

            if (s.equals("ok")) {
                game.sendObject(new ObjectMessage(this.game.getPlayerById(ID).getPersonalBoard().getWarehouse(), 0, ID));
                game.getBoard().getMarket().getHand().remove(0);
                game.sendObject(new ObjectMessage(game.getBoard().getMarket(), 1, -1));
            }

            else game.reportError(new ErrorMessage(s, ID));
        }
        if (game.getBoard().getMarket().getHand().size()==0)  {
            game.getTurn().setPhase("WAITING FOR ACTION");

        }
        game.sendObject(new ObjectMessage(game.getTurn(), 10, -1));
    }

    public void discardRes(int ID) {

        ArrayList<Player> others = this.game.getPlayers();
        for (Player p : others) {
            if (p.getId() != ID) {
                p.moveFaithMarkerPos(1);
            }
        }
        this.game.checkVatican();
        //tutti i controlli vittoria , favore papale e ecc.

    }
    public void buyDevCard(int choosenIndex, int ID, DevCard d, ArrayList<ResourceType> resFromWarehouse, ArrayList<ResourceType> resFromStrongbox, int posIndex){
        for(ResourceType r : resFromStrongbox) {
            this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().pay(1, r);
        }
        for(ResourceType r : resFromWarehouse)
            this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().pay(1, r);
        if(this.game.getPlayerById(ID).getPersonalBoard().getCardSlot().get(posIndex).getSize() == 3){
            this.game.reportError(new ErrorMessage("Could not add this card in slot " + posIndex + " because it is full. Try again", ID));
            return;
        }
        this.game.sendObject(new ObjectMessage(game.getPlayerById(ID).getPersonalBoard().getWarehouse(), 3, ID));
        this.game.sendObject(new ObjectMessage(game.getPlayerById(ID).getPersonalBoard().getStrongbox(), 4, ID));
        this.game.addDevCardToPlayer(ID, d, posIndex);
        this.game.getBoard().getDevDecks().get(choosenIndex - 1).removeCardFromCards(d);
        this.game.sendObject(new ObjectMessage(game.getBoard().getDevDecks(), 2, ID));
    }

     public void activateDevProduction(int ID, DevCard d, int num1FromWarehouse, int num1FromStrongbox, int num2FromWarehouse, int num2FromStrongbox){
         int r1 = 0, r2 = 0;
         int i = 0;
         int h = 0;
         ResourceType res1, res2;
         boolean found1 = false;
         boolean found2 = false;
         ArrayList<Shelf> w = this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().getShelves();
         Strongbox st = this.game.getPlayerById(ID).getPersonalBoard().getStrongbox();


         for(i=0;i<4 &&!found1;i++){
             if(d.getInputRes()[i]>0){
                 r1 = i;
                 found1=true;
             }
         }

         for(h=i;i<4 &&!found2;i++){
             if(d.getInputRes()[i]>0){
                 r2 = h;
                 found2=true;
             }
             if(!found2)
                 r2 = -1; //c'è una sola risorsa input
         }
         res1 = this.FromIntToRes(r1);
         res2 = this.FromIntToRes(r2);


         //pagamenti : non vengono fatti controlli sul fatto che si abbiano abbastanza risorse per pagare
         if(num1FromWarehouse>0){
             for (Shelf s:w){
                 if(s.getResType()==res1){
                     s.setCount(s.getCount()-num1FromWarehouse);
                 }
             }
         }
         if(num1FromStrongbox>0){
                Shelf newShelf = new Shelf(res1, st.getInfinityShelf().get(r1).getCount()-num1FromStrongbox);
                st.getInfinityShelf().set(r1,newShelf);

         }
         if(num2FromWarehouse>0){
             for (Shelf s:w){
                 if(s.getResType()==res2){
                     s.setCount(s.getCount()-num2FromWarehouse);
                 }
             }
         }
         if(num2FromStrongbox>0){
             Shelf newShelf = new Shelf(res2, st.getInfinityShelf().get(r2).getCount()-num2FromStrongbox);
             st.getInfinityShelf().set(r2,newShelf);
         }



         //produzione risorse
//
//         for (i=0;i<4;i++){
//             if(d.getOutputRes()[i]>0){
//                 int n = d.getOutputRes()[i];
//                 Shelf newshelf = new Shelf(FromIntToRes(i),
//                         this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().getInfinityShelf().get(i).getCount()+n);
//                 this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().getInfinityShelf().set(i,newshelf);
//             }
//         }

         //nuova implementazione con produzione nel buffer
            for (i=0;i<4;i++) {
                if (d.getOutputRes()[i] > 0) {
                     switch (i+1) {
                         case 1 -> {this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().
                                 setEarnedCoin(this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().getEarnedCoin()+d.getOutputRes()[i]);}
                         case 2 -> {this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().
                                 setEarnedStone(this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().getEarnedStone()+d.getOutputRes()[i]);}
                         case 3 -> {this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().
                                 setEarnedServant(this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().getEarnedServant()+d.getOutputRes()[i]);}
                         case 4 -> {this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().
                                 setEarnedShield(this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().getEarnedShield()+d.getOutputRes()[i]);}

                    }
                }
            }


         //aggiunta punti fede
         this.game.getPlayerById(ID).moveFaithMarkerPos(d.getOutputRes()[4]);

         game.sendObject(new ObjectMessage(this.game.getPlayerById(ID).getPersonalBoard().getWarehouse(), 3, ID));
         game.sendObject(new ObjectMessage(this.game.getPlayerById(ID).getPersonalBoard().getStrongbox(), 4, ID));

     }

     public void addResourceToStrongFromProduction(int ID, ArrayList<ResourceType> resToStrongbox){
        for(ResourceType r : resToStrongbox){
            this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().addResource(r, 1);
        }
     }
     //TODO aggiungere i punti vittoria se richiesti
//
//    public void payResources(int ID, ArrayList<ResourceType> paidResFromWarehouse, ArrayList<ResourceType> paidResFromStrongbox){
//        for(ResourceType r : paidResFromWarehouse){
//            this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().pay(1, r);
//        }
//        for(ResourceType r : paidResFromStrongbox){
//            this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().pay(1, r);
//        }
//    }

    public void addResources(int ID, ArrayList<ResourceType> boughtRes){
        for(ResourceType r : boughtRes){
            this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().addResource(r, 1);
        }
    }

    public boolean payResources(int ID, ArrayList<ResourceType> paidResFromWarehouse, ArrayList<ResourceType> paidResFromStrongbox) {
        if(!(this.game.getPlayerById(ID).getResDiscount1().equals(ResourceType.EMPTY))){

        }
        if (!(this.game.getPlayerById(ID).getPersonalBoard()
                .getWarehouse().canIPay((int) paidResFromWarehouse.stream()
                        .filter(x -> x.equals(ResourceType.COIN)).count(), ResourceType.COIN)) || !(this.game.getPlayerById(ID).getPersonalBoard()
                .getWarehouse().canIPay((int) paidResFromWarehouse.stream()
                        .filter(x -> x.equals(ResourceType.STONE)).count(), ResourceType.STONE)) || !(this.game.getPlayerById(ID).getPersonalBoard()
                .getWarehouse().canIPay((int) paidResFromWarehouse.stream()
                        .filter(x -> x.equals(ResourceType.SERVANT)).count(), ResourceType.SERVANT)) || !(this.game.getPlayerById(ID).getPersonalBoard()
                .getWarehouse().canIPay((int) paidResFromWarehouse.stream()
                        .filter(x -> x.equals(ResourceType.SHIELD)).count(), ResourceType.SHIELD))) {
            this.game.reportError(new ErrorMessage("You don't have enough resources!", ID));
            return false;
        } else if (!(this.game.getPlayerById(ID).getPersonalBoard()
                .getStrongbox().canIPay((int) paidResFromStrongbox.stream()
                        .filter(x -> x.equals(ResourceType.COIN)).count(), ResourceType.COIN)) || !(this.game.getPlayerById(ID).getPersonalBoard()
                .getStrongbox().canIPay((int) paidResFromStrongbox.stream()
                        .filter(x -> x.equals(ResourceType.STONE)).count(), ResourceType.STONE)) || !(this.game.getPlayerById(ID).getPersonalBoard()
                .getStrongbox().canIPay((int) paidResFromStrongbox.stream()
                        .filter(x -> x.equals(ResourceType.SERVANT)).count(), ResourceType.SERVANT)) || !(this.game.getPlayerById(ID).getPersonalBoard()
                .getStrongbox().canIPay((int) paidResFromStrongbox.stream()
                        .filter(x -> x.equals(ResourceType.SHIELD)).count(), ResourceType.SHIELD))) {
            this.game.reportError(new ErrorMessage("You don't have enough resources!", ID));
            return false;
        } else {
            for (ResourceType r : paidResFromWarehouse) {
                this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().pay(1, r);
            }

            for (ResourceType r : paidResFromStrongbox) {
                this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().pay(1, r);
            }
            return true;
        }
    }


    public void useLeaderProduction(int ID, ResourceType r, ResourceType newRes, boolean buyFromWarehouse){ //if true -> pay from warehouse
         if(buyFromWarehouse == true){
             if(!(this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().canIPay(1, r))){
                 this.game.reportError(new ErrorMessage("You don't have enough resources!", ID));
             }
             else {
                 this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().pay(1, r);
                 this.game.getPlayerById(ID).moveFaithMarkerPos(1);
             }
         }
         if(buyFromWarehouse == false){
             if(!(this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().canIPay(1, r))){
                 this.game.reportError(new ErrorMessage("You don't have enough resources!", ID));
             }
             else {
                 this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().pay(1, r);
                 this.game.getPlayerById(ID).moveFaithMarkerPos(1);
             }
         }
     }




     public void PlayLeaderCard(int ID, DiscountLCard dc){
       //  this.game.getPlayerById(ID).getPersonalBoard().getActiveLeader().getCards().add(dc);
         if(this.game.getPlayerById(ID).getResDiscount1()==ResourceType.EMPTY){
             this.game.getPlayerById(ID).setResDiscount1(dc.getResType());
         }
         else if (this.game.getPlayerById(ID).getResDiscount2()==ResourceType.EMPTY){
             this.game.getPlayerById(ID).setResDiscount2(dc.getResType());
         }
         else
             System.out.println(new ErrorMessage("you already have 2 active Leaders", ID));
         RemoveLeaderFromDeck(ID,dc);
     }

     public void PlayLeaderCard (int ID, ExtraDepotLCard sc){
       //  this.game.getPlayerById(ID).getPersonalBoard().getActiveLeader().getCards().add(sc);
         if(this.game.getPlayerById(ID).getPersonalBoard().getExtraShelfRes1()==ResourceType.EMPTY){
             this.game.getPlayerById(ID).getPersonalBoard().setExtraShelfRes1(sc.getResDepot());
             this.game.getPlayerById(ID).getPersonalBoard().setExtraShelfNum1(0);
         }
         else if (this.game.getPlayerById(ID).getPersonalBoard().getExtraShelfRes2()==ResourceType.EMPTY){
             this.game.getPlayerById(ID).getPersonalBoard().setExtraShelfRes2(sc.getResDepot());
             this.game.getPlayerById(ID).getPersonalBoard().setExtraShelfNum2(0);
         }
         else
             System.out.println(new ErrorMessage("you already have 2 active Leaders", ID));
         RemoveLeaderFromDeck(ID,sc);
     }

    public void PlayLeaderCard(int ID, ExtraProdLCard c){
     //   this.game.getPlayerById(ID).getPersonalBoard().getActiveLeader().getCards().add(c);
        if(this.game.getPlayerById(ID).getInputExtraProduction1()==ResourceType.EMPTY){

            this.game.getPlayerById(ID).setInputExtraProduction1(c.getInput());
        }
        else if (this.game.getPlayerById(ID).getInputExtraProduction2()==ResourceType.EMPTY){
            this.game.getPlayerById(ID).setInputExtraProduction2(c.getInput());
        }
        else
            System.out.println(new ErrorMessage("you already have 2 active Leaders", ID));
        RemoveLeaderFromDeck(ID,c);
    }

    public void PlayLeaderCard(int ID, WhiteTrayLCard wc){
      //  this.game.getPlayerById(ID).getPersonalBoard().getActiveLeader().getCards().add(wc);
        if(this.game.getPlayerById(ID).getWhiteConversion1()==ResourceType.EMPTY){
            this.game.getPlayerById(ID).setWhiteConversion1(wc.getResType());
        }
        else if(this.game.getPlayerById(ID).getWhiteConversion2()==ResourceType.EMPTY){
            this.game.getPlayerById(ID).setWhiteConversion2(wc.getResType());
        }
        else
            System.out.println("you already have 2 active Leaders");
        RemoveLeaderFromDeck(ID,wc);

    }


    public void RemoveLeaderFromDeck(int ID,LeaderCard lc){
        this.game.getPlayerById(ID).getLeaderDeck().getCards().remove(lc); //toglie dal leader deck
        this.game.getPlayerById(ID).getLeaderDeck().setSize(this.game.getPlayerById(ID).getLeaderDeck().getSize()-1);
        this.game.getPlayerById(ID).getPersonalBoard().getActiveLeader().getCards().add(lc); //aggiunge ai leader attivi
        this.game.getPlayerById(ID).getPersonalBoard().getActiveLeader().setSize(this.game.getPlayerById(ID).getPersonalBoard().getActiveLeader().getSize()+1);
        game.sendObject(new ObjectMessage(this.game.getPlayerById(ID).getLeaderDeck(), 2, ID)); //invio leader della mano
        game.sendObject(new ObjectMessage(this.game.getPlayerById(ID).getPersonalBoard().getActiveLeader(),6,ID));
    }


     public void DiscardLeaderCard(int ID,LeaderCard lc){
         int i = 0;
         LeaderDeck newLD;
         while(this.game.getPlayerById(ID).getLeaderDeck().getCards().get(i).getType()!=lc.getType() || this.game.getPlayerById(ID).getLeaderDeck().getCards().get(i).getVictoryPoints()!=lc.getVictoryPoints() )
         {i++;}
         if (i<=this.game.getPlayerById(ID).getLeaderDeck().getSize()) //se trovo la carta leader nel mazzo
         {
             newLD = new LeaderDeck((this.game.getPlayerById(ID).getLeaderDeck().getSize())-1,1,this.game.getPlayerById(ID).getLeaderDeck().getCards());
             newLD.getCards().remove(i);
             this.game.getPlayerById(ID).setLeaderDeck(newLD);


         }
        this.game.getPlayerById(ID).moveFaithMarkerPos(1);
         //tutti i controlli vittoria , favore papale e ecc.

         game.sendObject(new ObjectMessage(this.game.getPlayerById(ID).getLeaderDeck(), 2, ID));
     }

     public void placeInitialRes(ResourceType res, int shelfIndex, int ID){

        String s = this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().addResource(res, shelfIndex);
            if (s.equals("ok")) {
                game.getPlayerById(ID).setStartResCount(game.getPlayerById(ID).getStartResCount() - 1);
                if(game.getPlayerById(ID).getStartResCount() == 0){
                    game.getPlayerById(ID).setReady(true);
                    if(checkReadyPlayers()){
                        game.sendObject(new ObjectMessage(game, -1, -1));
                        game.endTurn(game.getPlayers().get(game.getPlayers().size()-1).getId());
                    }
                }
            }
            else game.sendSingleResource(null, shelfIndex, ID, s);

    }

    public synchronized boolean checkReadyPlayers(){
        return game.getPlayers().stream().filter(x -> x.isReady()).count() == game.getNumPlayer();
    }


     public ResourceType FromIntToRes (int i){
        switch (i){
            case 0 -> { return ResourceType.COIN; }
            case 1 -> { return ResourceType.STONE; }
            case 2 -> { return ResourceType.SERVANT; }
            case 3 -> { return ResourceType.SHIELD; }
            default -> {return ResourceType.EMPTY;}
        }
     }

     private boolean checkPaymentFromWarehouse(){
        return true;
     }

     private boolean checkPaymentFromStrongbox(){
        return true;
     }


//    Shelf temp;
//        if (this.shelves.get(s1).getCount() <= s2 + 1 && this.shelves.get(s2).getCount() <= s1 + 1) {
//        temp = this.shelves.get(s1);
//        this.shelves.set(s1, this.shelves.get(s2));
//        this.shelves.set(s2, temp);
//        notify();
//    } else System.out.println("Error. Not a valid operation. Check the game rules!\n ");


    @Override
    public void update(Message message) {

    }

    @Override public void update(Nickname message) {
        setNickname(message);
    }

    @Override public void update(InputMessage message) {}

    @Override
    public void update(IdMessage message) {
        
    }

    @Override
    public void update(ErrorMessage message) {
        if (message.getString().equals("discard"))  {
            discardRes(message.getID());
        }


    }

    @Override
    public void update(OutputMessage message) {

    }

    @Override
    public void update (ChooseNumberOfPlayer message)  {


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
        System.out.println("prima if");
        if(message.getError().equals("initial phase")) {
            System.out.println("dopo if");
            placeInitialRes(message.getRes(), message.getShelf(), message.getID());
        }
        else{
            placeRes(message.getRes(), message.getShelf(), message.getID());
        }


    }

    public void update(BuyDevCardMessage message){
        buyDevCard(message.getChoosenIndex(), message.getID(), message.getD(), message.getResFromWarehouse(), message.getResFromStrongbox(), message.getSlot());
    }

    @Override
    public void update(PlayLeaderMessage message){
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
            }
            else {
                DiscardLeaderCard(message.getID(), message.getLc());
            }
    }




    @Override
    public void update(ProductionMessage message) {
        if(payResources(message.getID(), message.getResFromWarehouse(), message.getResFromStrongbox())){
            this.addResourceToStrongFromProduction(message.getID(), message.getResToBuy());
        };
       // useBasicProduction(message.getID(), message.getResFromWarehouse(), message.getResFromStrongbox());
    }



    public void checkRequirements(int b,int d,int l){

    }

    @Override
    public void update(EndTurnMessage message) {
        game.endTurn(message.getID());

    }

    @Override
    public void update(EndActionMessage message) {

    }



//
//        @Override
//        public void update(LeaderProdMessage message){
//            this.activateDevProduction(message.getId(), message.getL(),);
//        }

}












