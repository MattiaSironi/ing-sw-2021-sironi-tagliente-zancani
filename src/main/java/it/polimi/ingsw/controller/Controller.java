package it.polimi.ingsw.controller;


import it.polimi.ingsw.message.ActionMessages.*;
import it.polimi.ingsw.message.CommonMessages.*;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.observer.Observer;


import java.util.ArrayList;
import java.util.Arrays;

public class Controller implements Observer<Message> {
    private Game game;

    public Controller(Game game) {
        this.game = game;
    }

    public void setNickname(Nickname nickname) {
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
            game.reportError(new ErrorMessage("ko", nickname.getID()));
        } else {
            game.reportError(new ErrorMessage("ok", nickname.getID()));
            game.createNewPlayer(nickname);
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
            game.reportError(new ErrorMessage("ko", ID));
        }
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
        int faith = (int) resources.stream().filter(x -> x.getRes().equals(ResourceType.FAITH_POINT)).count();
        this.game.getPlayerById(ID).moveFaithMarkerPos(faith);

        game.sendObject(new ObjectMessage(this.game.getBoard().getMarket(), 1, ID));
        game.sendResources(new ResourceListMessage(resources, ID));
    }


    public void placeRes(ResourceType r, int shelfIndex, int ID)  {
         //mette la risorsa al posto giusto se può
         //manda reportError con ok o ko a seconda che rispetti le regole

         String s = this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().addResource(r, shelfIndex);
         game.reportError(new ErrorMessage(s,ID));
    }

    public void discardRes(int ID) {

        ArrayList<Player> others = this.game.getPlayers();
        for (Player p : others) {
            if (p.getId() != ID) {
                p.moveFaithMarkerPos(1);
            }
        }
        //tutti i controlli vittoria , favore papale e ecc.

    }
    public void buyDevCard(int ID, DevCard d, boolean buyFromWarehouse, boolean buyFromStrongbox){
         //paga la carta (toglie risorse dal model) con risorse dal forziere se buyFrom = True, dal Warehouse se False
         //aggiunge DevCard alla mano
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

         //devo cercare le risorse in ingresso e in uscita e salvarne ResType e int
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

         //produzione sempre in strongbox : aggiungo le risorse nella dev card alla shelf corrispondente
         for (i=0;i<4;i++){
             if(d.getOutputRes()[i]>0){
                 int n = d.getOutputRes()[i];
                 Shelf newshelf = new Shelf(FromIntToRes(i),
                         this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().getInfinityShelf().get(i).getCount()+n);
                 this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().getInfinityShelf().set(i,newshelf);
             }
         }

         //aggiunta punti fede
         this.game.getPlayerById(ID).moveFaithMarkerPos(d.getOutputRes()[4]);



         game.sendObject(new ObjectMessage(this.game.getPlayerById(ID).getPersonalBoard().getWarehouse(), 3, ID));
         game.sendObject(new ObjectMessage(this.game.getPlayerById(ID).getPersonalBoard().getStrongbox(), 4, ID));
     }





    public void useBasicProduction(int ID, ResourceType r1, ResourceType r2, ResourceType newRes, boolean buyFromWarehouse, boolean buyFromStrongbox) {
        if (buyFromWarehouse == true && buyFromStrongbox == false) {
            this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().pay(1, r1);
            this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().pay(1, r2);
            this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().addResource(newRes, 1);
        } else if (buyFromWarehouse == false && buyFromStrongbox == true) {
            this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().pay(1, r1);
            this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().pay(1, r2);
            this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().addResource(newRes, 1);
        } else if (buyFromWarehouse == true && buyFromStrongbox == true) { //r1 è la risorsa da prendere da Warehouse e r2 è la risorsa da prendere da Strongbox
            this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().pay(1, r1);
            this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().pay(1, r2);
            this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().addResource(newRes, 1);
        }
        //consuma r1 ed r2 e produce r3 che mette nello strongbox
    }
    public void useLeaderProduction(int ID, ResourceType r, ResourceType newRes, boolean buyFromWarehouse){ //if true -> pay from warehouse
         if(buyFromWarehouse == true){
             this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().pay(1, r);
             this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().addResource(newRes, 1);
             this.game.getPlayerById(ID).moveFaithMarkerPos(1);
         }
         if(buyFromWarehouse == true){
             this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().pay(1, r);
             this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().addResource(newRes, 1);
             this.game.getPlayerById(ID).moveFaithMarkerPos(1);
         }
     }

     public void PlayLeaderCard(int ID, LeaderCard lc){
        //considerando che i controlli per il pagamento della leader card siano gia stati fatti
         this.game.getPlayerById(ID).getPersonalBoard().getActiveLeader().getCards().add(lc);
         switch (lc.getType()){
             case 1->{ //discount
                 DiscountLCard dc = (DiscountLCard) lc;
                 if(this.game.getPlayerById(ID).getResDiscount1()!=ResourceType.EMPTY){
                     this.game.getPlayerById(ID).setResDiscount1(dc.getResType());
                 }
                 else if (this.game.getPlayerById(ID).getResDiscount2()!=ResourceType.EMPTY){
                     this.game.getPlayerById(ID).setResDiscount2(dc.getResType());
                 }
                 else
                     game.reportError(new ErrorMessage("you already have 2 active Leaders", ID));


             }
             case 2->{ //extraShelf
                 ExtraDepotLCard sc = (ExtraDepotLCard)lc;
                    if(this.game.getPlayerById(ID).getPersonalBoard().getExtraShelfRes1()!=ResourceType.EMPTY){
                        this.game.getPlayerById(ID).getPersonalBoard().setExtraShelfRes1(sc.getResDepot());
                        this.game.getPlayerById(ID).getPersonalBoard().setExtraShelfNum1(0);
                    }
                    else if (this.game.getPlayerById(ID).getPersonalBoard().getExtraShelfRes2()!=ResourceType.EMPTY){
                        this.game.getPlayerById(ID).getPersonalBoard().setExtraShelfRes2(sc.getResDepot());
                        this.game.getPlayerById(ID).getPersonalBoard().setExtraShelfNum2(0);
                    }
                    else
                        game.reportError(new ErrorMessage("you already have 2 active Leaders", ID));


             }
             case 3->{ //extraProd
                 ExtraProdLCard c = (ExtraProdLCard) lc;
                    if(this.game.getPlayerById(ID).getInputExtraProduction1()!=ResourceType.EMPTY){
                        this.game.getPlayerById(ID).setInputExtraProduction1(c.getInput());
                    }
                    else if (this.game.getPlayerById(ID).getInputExtraProduction2()!=ResourceType.EMPTY){
                        this.game.getPlayerById(ID).setInputExtraProduction2(c.getInput());
                    }
                    else
                        game.reportError(new ErrorMessage("you already have 2 active Leaders", ID));
             }
             case 4->{ //whiteTray
                    WhiteTrayLCard wc = (WhiteTrayLCard)lc;
                     if(this.game.getPlayerById(ID).getWhiteConversion1()!=ResourceType.EMPTY){
                         this.game.getPlayerById(ID).setWhiteConversion1(wc.getResType());
                     }
                     else if(this.game.getPlayerById(ID).getWhiteConversion2()!=ResourceType.EMPTY){
                         this.game.getPlayerById(ID).setWhiteConversion2(wc.getResType());
                     }
                     else
                         game.reportError(new ErrorMessage("you already have 2 active Leaders", ID));
             }
         }
         game.sendObject(new ObjectMessage(this.game.getPlayerById(ID).getPersonalBoard().getActiveLeader(), 5, ID));
     }

     public void DiscardLeaderCard(int ID){
        this.game.getPlayerById(ID).moveFaithMarkerPos(1);
         //tutti i controlli vittoria , favore papale e ecc.
         game.sendObject(new ObjectMessage(this.game.getPlayerById(ID).getPersonalBoard().getActiveLeader(), 5, ID));
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
        if (message.getString().equals("all set"))  {

            game.printPlayerNickname(message.getID());
        }
        else if (message.getString().equals("discard"))  {
            discardRes(message.getID());
        }


    }

    @Override
    public void update(OutputMessage message) {

    }

    @Override
    public void update (ChooseNumberOfPlayer message)  {
        game.setNumPlayer(message.getNumberOfPlayers());

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
        placeRes(message.getRes(), message.getShelf(), message.getID());

    }


}












