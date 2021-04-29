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

        game.sendObject(new ObjectMessage(this.game.getBoard().getMarket(), 1, ID));
//        game.sendResources(new ResourceListMessage(resources, ID));
    }


    public void placeRes(ResourceType r, int shelfIndex, int ID)  {
         //mette la risorsa al posto giusto se può
         //manda reportError con ok o ko a seconda che rispetti le regole

         String s = this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().addResource(r, shelfIndex);
         game.reportError(new ErrorMessage(s,ID));
    }

     public void discardRes(int ID)  {

          ArrayList<Player> others= this.game.getPlayers();
          for (Player p : others)  {
              if (p.getId()!=ID)  {
                  p.moveFaithMarkerPos();
              }
          }
          //tutti i controlli vittoria , favore papale e ecc.

     }

     public void buyDevCard(int ID, DevCard d, boolean buyFromWarehouse, boolean buyFromStrongbox){
         //paga la carta (toglie risorse dal model) con risorse dal forziere se buyFrom = True, dal Warehouse se False
         //aggiunge DevCard alla mano
     }

     public void activateDevProduction(int ID, DevCard d, int num1FromWarehouse, int num1FromStrongbox, int num2FromWarehouse, int num2FromStrongbox, ResourceType res1FromWarehouse, ResourceType res1FromStrongbox, ResourceType res2FromWarehouse, ResourceType res2FromStrongbox){
        //paga e produce
     }

     public void useBasicProduction(int ID, ResourceType r1, ResourceType r2, ResourceType newRes, boolean buyFromWarehouse, boolean buyFromStrongbox){
        if(buyFromWarehouse == true && buyFromStrongbox == false){
            this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().pay(1, r1);
            this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().pay(1, r2);
            this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().addResource(newRes);
        }
        else  if(buyFromWarehouse == false && buyFromStrongbox == true){
            this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().pay(1, r1);
            this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().pay(1, r2);
            this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().addResource(newRes);
        }
        else if(buyFromWarehouse == true && buyFromStrongbox == true){ //r1 è la risorsa da prendere da Warehouse e r2 è la risorsa da prendere da Strongbox
            this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().pay(1, r1);
            this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().pay(1, r2);
            this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().addResource(newRes);
        }
        //consuma r1 ed r2 e produce r3 che mette nello strongbox
     }

     public void useLeaderProduction(int ID, ResourceType r, ResourceType newRes, boolean buyFromWarehouse){ //if true -> pay from warehouse
         if(buyFromWarehouse == true){
             this.game.getPlayerById(ID).getPersonalBoard().getWarehouse().pay(1, r);
             this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().addResource(newRes);
             this.game.getPlayerById(ID).moveFaithMarkerPos();
         }
         if(buyFromWarehouse == true){
             this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().pay(1, r);
             this.game.getPlayerById(ID).getPersonalBoard().getStrongbox().addResource(newRes);
             this.game.getPlayerById(ID).moveFaithMarkerPos();
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
        //     if(message instanceof Nickname) setNickname((Nickname)message);
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


}












