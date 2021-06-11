package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.SceneList;
import it.polimi.ingsw.message.ActionMessages.BuyDevCardMessage;
import it.polimi.ingsw.message.ActionMessages.EndTurnMessage;
import it.polimi.ingsw.message.ActionMessages.ManageResourceMessage;
import it.polimi.ingsw.message.ActionMessages.PlayLeaderMessage;
import it.polimi.ingsw.model.*;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class MainSceneController implements  GUIController {
    public ImageView activateButton;
    public ImageView discardButton;
    public ImageView backButton;
    public ImageView devMarket;
    public Label phase;
    public Label comMessages;
    public ImageView market;
    public ImageView dev1;
    public ImageView dev2;
    public ImageView dev3;
    public ImageView endTurnButton;
    public ImageView shelf1pos1;
    public ImageView shelf4pos1;
    public ImageView shelf4pos2;
    public ImageView shelf5pos1;
    public ImageView shelf5pos2;
    public ImageView shelf3pos1;
    public ImageView shelf3pos2;
    public ImageView shelf3pos3;
    public ImageView shelf2pos1;
    public ImageView shelf2pos2;
    public Button shelf2;
    public Button shelf3;
    public Button shelf4;
    public Button shelf5;
    public Button shelf1;
    public Label shieldNum;
    public Label servantNum;
    public Label coinNum;
    public Label stoneNum;
    public ImageView ft0;
    public ImageView ft1;
    public ImageView ft2;
    public ImageView ft3;
    public ImageView ft4;
    public ImageView ft5;
    public ImageView ft6;
    public ImageView ft7;
    public ImageView ft8;
    public ImageView ft9;
    public ImageView ft10;
    public ImageView ft11;
    public ImageView ft12;
    public ImageView ft13;
    public ImageView ft14;
    public ImageView ft15;
    public ImageView ft16;
    public ImageView ft17;
    public ImageView ft18;
    public ImageView ft19;
    public ImageView ft20;
    public ImageView ft21;
    public ImageView ft22;
    public ImageView ft23;
    public ImageView ft24;
    private LeaderCard lc1;
    private LeaderCard lc2;
    private Image l1 ;
    private Image l2 ;
    boolean active1 = false;
    boolean active2 = false;
    public ImageView leader1;
    public ImageView leader2;
    private GUI gui;
    int choosing = 0;
    private boolean firstTurn = true;
    Integer s1;
    ArrayList<Button> shelves;
    ArrayList<ImageView> faithTrack;


    private MainController mainController;


    public void Leader1OpacityUp(MouseEvent mouseEvent) {
        if(!active1&&choosing==0)
             leader1.setEffect(new Glow(0.3));
    }
    public void Leader1OpacityDown(MouseEvent mouseEvent) { leader1.setEffect(new DropShadow(20, Color.BLACK));}
    public void Leader2OpacityUp(MouseEvent mouseEvent) {
        if(!active2&&choosing==0)
             leader2.setEffect(new Glow(0.3));
    }
    public void Leader2OpacityDown(MouseEvent mouseEvent) { leader2.setEffect(new DropShadow(20, Color.BLACK));}
    public void ActivateOpacityUp(MouseEvent mouseEvent) {activateButton.setOpacity(1);}
    public void ActivateOpacityDown(MouseEvent mouseEvent) { activateButton.setOpacity(0.75);}
    public void DiscardOpacityUp(MouseEvent mouseEvent) { discardButton.setOpacity(1);}
    public void DiscardOpacityDown(MouseEvent mouseEvent) { discardButton.setOpacity(0.75);}
    public void BackOpacityUp(MouseEvent mouseEvent) { backButton.setOpacity(1);}
    public void BackOpacityDown(MouseEvent mouseEvent) { backButton.setOpacity(0.75);}


    public void Leader1Clicked(MouseEvent mouseEvent) {
        if(!active1&&choosing==0) {
            activateButton.setVisible(true);
            activateButton.setDisable(false);
            discardButton.setVisible(true);
            discardButton.setDisable(false);
            backButton.setVisible(true);
            backButton.setDisable(false);
            leader1.setImage(l1);
            choosing=1;
        }
    }

    public void Leader2Clicked(MouseEvent mouseEvent) {
        if(!active2 && choosing==0){
            activateButton.setVisible(true);
            activateButton.setDisable(false);
            discardButton.setVisible(true);
            discardButton.setDisable(false);
            backButton.setVisible(true);
            backButton.setDisable(false);
            leader2.setImage(l2);
            choosing = 2;
        }
    }

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
        this.gui=m.getGui();

    }

    public void setup() {
        System.out.println(mainController.getGui().getID());
        if (firstTurn) {
            l1 = new Image(getClass().getResource("/images/Leaders/" + this.mainController.getGame().getPlayerById(gui.getID()).getLeaderDeck().getCards().get(0).toString() + ".png").toExternalForm());
            l2 = new Image(getClass().getResource("/images/Leaders/" + this.mainController.getGame().getPlayerById(gui.getID()).getLeaderDeck().getCards().get(1).toString() + ".png").toExternalForm());
            lc1 = this.mainController.getGame().getPlayerById(gui.getID()).getLeaderDeck().getCards().get(0);
            lc2 = this.mainController.getGame().getPlayerById(gui.getID()).getLeaderDeck().getCards().get(1);
            firstTurn = false;
        }
        if(mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getCardSlot().get(0).getCards().size() != 0){
            dev1.setImage(new Image(getClass().getResource("/images/Devs/FRONT/" +
                    mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getCardSlot().get(0).getCards().get(0) +
                    ".png").toExternalForm()));

        }
        if(mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getCardSlot().get(1).getCards().size() != 0){
            dev2.setImage(new Image(getClass().getResource("/images/Devs/FRONT/" +
                    mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getCardSlot().get(1).getCards().get(0) +
                    ".png").toExternalForm()));
        }
        if(mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getCardSlot().get(2).getCards().size() != 0){
            dev3.setImage(new Image(getClass().getResource("/images/Devs/FRONT/" +
                    mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getCardSlot().get(2).getCards().get(0) +
                    ".png").toExternalForm()));
        }

        groupShelves();
        groupFaithSlots();
        showFaithTrack();
        showShelves();
        showStrongbox();
    }

    public void showFaithTrack() {
        int pos = 0;
        int loripos=0;
        pos = mainController.getGame().getPlayerById(gui.getID()).getPersonalBoard().getFaithTrack().getMarker();
        this.faithTrack.get(pos).setImage((new Image(getClass().getResource("/images/PunchBoard/faith_point.png").toExternalForm())));
        for (int i=0;i<pos;i++){
            this.faithTrack.get(i).setImage(null);
        }
        if(mainController.getGame().getNumPlayer()==1){
            loripos = mainController.getGame().getPlayerById(gui.getID()).getPersonalBoard().getFaithTrack().getLoriPos();
            if(loripos==pos) {  //nuova immagine}
            }
            this.faithTrack.get(loripos).setImage((new Image(getClass().getResource("/images/PunchBoard/croce.png").toExternalForm())));
        }


    }


    @Override
    public void print(Turn turn) {
        if (turn.getPlayerPlayingID() != gui.getID()) {

            phase.setText(this.mainController.getGame().getPlayerById(turn.getPlayerPlayingID()).getNickname() + " " + turn.getPhase().getOthers());
        } else {
            phase.setText("Your turn");

        }
    }

    @Override
    public void print(Communication communication) {
            comMessages.setText(communication.getCommunication().getString());
    }

    @Override
    public void disable() {
        activateButton.setMouseTransparent(true);
        activateButton.setOpacity(0.5);
        discardButton.setMouseTransparent(true);
        discardButton.setOpacity(0.5);
        devMarket.setDisable(true);
        leader1.setDisable(true);
        leader2.setDisable(true);
        endTurnButton.setDisable(true);
        groupShelves();
        disableShelves();
    }

    @Override
    public void enable() {
        activateButton.setMouseTransparent(false);
        activateButton.setOpacity(1);
        discardButton.setMouseTransparent(false);
        discardButton.setOpacity(1);
        devMarket.setDisable(false);
        leader1.setDisable(false);
        leader2.setDisable(false);
        endTurnButton.setDisable(false);
        groupShelves();
        enableShelves();
    }

    public void buyDevCard(MouseEvent mouseEvent) {
        gui.changeScene(SceneList.BUYDEVCARD);
    }

    public void goToMarket(MouseEvent mouseEvent) {
        gui.changeScene(SceneList.MARKET);
    }

    public void back(MouseEvent mouseEvent) {
        activateButton.setVisible(false);
        activateButton.setDisable(true);
        discardButton.setVisible(false);
        discardButton.setDisable(true);
        backButton.setVisible(false);
        backButton.setDisable(true);
        if(!active1)
             leader1.setImage(new Image(getClass().getResource("/images/Leaders/BACK.png").toExternalForm()));
        if(!active2)
             leader2.setImage(new Image(getClass().getResource("/images/Leaders/BACK.png").toExternalForm()));
        choosing=0;
    }

    public void activate(MouseEvent mouseEvent) {
        if (choosing == 1) {
            mainController.send(new PlayLeaderMessage(gui.getID(), choosing, true, lc1, false));
            showLeaders();
        }
        else{
            mainController.send(new PlayLeaderMessage(gui.getID(), choosing, true, lc2, false));
            showLeaders();
        }

            activateButton.setVisible(false);
            activateButton.setDisable(true);
            discardButton.setVisible(false);
            discardButton.setDisable(true);
            backButton.setVisible(false);
            backButton.setDisable(true);
            choosing = 0;
        }


    public void discard(MouseEvent mouseEvent) {

        if(choosing==1){
            mainController.send(new PlayLeaderMessage(gui.getID(), choosing, false, lc1, false));
            active1 = true;
            leader1.setImage(new Image(getClass().getResource("/images/Leaders/BACK.png").toExternalForm()));
            leader1.setDisable(true);
            leader1.setOpacity(0.5);
        }
        else{
            mainController.send(new PlayLeaderMessage(gui.getID(), choosing, false, lc2, false));
            active2 = true;
            leader2.setImage(new Image(getClass().getResource("/images/Leaders/BACK.png").toExternalForm()));
            leader2.setDisable(true);
            leader2.setOpacity(0.5);
        }
        activateButton.setVisible(false);
        activateButton.setDisable(true);
        discardButton.setVisible(false);
        discardButton.setDisable(true);
        backButton.setVisible(false);
        backButton.setDisable(true);
        choosing=0;
    }


    public void endTurn(MouseEvent mouseEvent) {
        mainController.setFirstAction(false);
        mainController.send(new EndTurnMessage(gui.getID())); //todo
    }


    public void swap(MouseEvent mouseEvent) {
        if (s1 == null) s1 = selectedShelves(((Button) mouseEvent.getTarget()).getId());
        else {
            int s2 = selectedShelves(((Button) mouseEvent.getTarget()).getId());
            mainController.send(new ManageResourceMessage(s1, s2, gui.getID()));
            s1 = null;

        }
    }
    private int selectedShelves(String id) {
        switch (id) {
            case "shelf1" -> {
                return 0;
            }
            case "shelf2" -> {
                return 1;
            }
            case "shelf3" -> {
                return 2;
            }
            case "shelf4" -> {
                return 3;
            }
            default -> {
                return 4;
            }
        }
    }
    private void groupShelves() {
        shelves = new ArrayList<>();
        shelves.add(shelf1);
        shelves.add(shelf2);
        shelves.add(shelf3);
        shelves.add(shelf4);
        shelves.add(shelf5);
    }

    private void groupFaithSlots() {
        faithTrack = new ArrayList<>();
        faithTrack.add(ft0);
        faithTrack.add(ft1);
        faithTrack.add(ft2);
        faithTrack.add(ft3);
        faithTrack.add(ft4);
        faithTrack.add(ft5);
        faithTrack.add(ft6);
        faithTrack.add(ft7);
        faithTrack.add(ft8);
        faithTrack.add(ft9);
        faithTrack.add(ft10);
        faithTrack.add(ft11);
        faithTrack.add(ft12);
        faithTrack.add(ft13);
        faithTrack.add(ft14);
        faithTrack.add(ft15);
        faithTrack.add(ft16);
        faithTrack.add(ft17);
        faithTrack.add(ft18);
        faithTrack.add(ft19);
        faithTrack.add(ft20);
        faithTrack.add(ft0);
        faithTrack.add(ft21);
        faithTrack.add(ft22);
        faithTrack.add(ft23);
        faithTrack.add(ft24);
    }

    private void enableShelves() {
        for (Button shelf : shelves) shelf.setDisable(false);
    }

    private void disableShelves() {
        for (Button shelf : shelves) shelf.setDisable(true);

    }

    public void showShelves() {
        ShelfWarehouse myShelves = mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getWarehouse();
        ArrayList<ImageView> shelf2 = new ArrayList<>();
        ArrayList<ImageView> shelf3 = new ArrayList<>();
        ArrayList<ImageView> shelf4 = new ArrayList<>();
        ArrayList<ImageView> shelf5 = new ArrayList<>();
        int max;
        ResourceType r;
        shelf2.add(shelf2pos1);
        shelf2.add(shelf2pos2);
        shelf3.add(shelf3pos1);
        shelf3.add(shelf3pos2);
        shelf3.add(shelf3pos3);
        shelf4.add(shelf4pos1);
        shelf4.add(shelf4pos2);
        shelf5.add(shelf5pos1);
        shelf5.add(shelf5pos2);

        if (myShelves.getShelves().get(0).getResType() != ResourceType.EMPTY)
            shelf1pos1.setImage(new Image(getClass().getResource("/images/PunchBoard/" + myShelves.getShelves().get(0).getResType().toString().toLowerCase() + ".png").toExternalForm()));
        else shelf1pos1.setImage(null);


        r = myShelves.getShelves().get(1).getResType();
        max = myShelves.getShelves().get(1).getCount();
        for (ImageView iv : shelf2) {
            if (shelf2.indexOf(iv) <= max - 1)
                iv.setImage(new Image(getClass().getResource("/images/PunchBoard/" + r.toString().toLowerCase() + ".png").toExternalForm()));
            else iv.setImage(null);
        }


        r = myShelves.getShelves().get(2).getResType();
        max = myShelves.getShelves().get(2).getCount();
        for (ImageView iv : shelf3) {
            if (shelf3.indexOf(iv) <= max - 1)
                iv.setImage(new Image(getClass().getResource("/images/PunchBoard/" + r.toString().toLowerCase() + ".png").toExternalForm()));
            else iv.setImage(null);
        }

        if (myShelves.getShelves().get(3).getResType() != null) {
            r = myShelves.getShelves().get(3).getResType();
            max = myShelves.getShelves().get(3).getCount();
            for (ImageView iv : shelf4) {
                iv.setImage(new Image(getClass().getResource("/images/PunchBoard/" + r.toString().toLowerCase() + ".png").toExternalForm()));
                if (!(shelf4.indexOf(iv) <= max - 1)) iv.setOpacity(0.5);
                else iv.setOpacity(1.0);
            }
        }
        if (myShelves.getShelves().get(4).getResType() != null) {
            r = myShelves.getShelves().get(4).getResType();
            max = myShelves.getShelves().get(4).getCount();
            for (ImageView iv : shelf5) {
                iv.setImage(new Image(getClass().getResource("/images/PunchBoard/" + r.toString().toLowerCase() + ".png").toExternalForm()));
                if (!(shelf5.indexOf(iv) <= max - 1)) iv.setOpacity(0.5);
                else iv.setOpacity(1.0);
            }
        }
    }
    public void showStrongbox(){
        coinNum.setText("" + mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getStrongbox().getResCount(ResourceType.COIN));
        stoneNum.setText("" + mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getStrongbox().getResCount(ResourceType.STONE));
        servantNum.setText("" + mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getStrongbox().getResCount(ResourceType.SERVANT));
        shieldNum.setText("" + mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getStrongbox().getResCount(ResourceType.SHIELD));
    }

    public void showLeaders() {
        if(mainController.getGame().getPlayerById(gui.getID()).getPersonalBoard().getActiveLeader().isPresent(lc1)){
            leader1.setImage(l1);
            active1=true;
            leader1.setDisable(true);
        }else{
            leader1.setImage(new Image(getClass().getResource("/images/Leaders/BACK.png").toExternalForm()));
        }
        if(mainController.getGame().getPlayerById(gui.getID()).getPersonalBoard().getActiveLeader().isPresent(lc2)){
            leader2.setImage(l2);
            active2=true;
            leader2.setDisable(true);
        }else{
            leader2.setImage(new Image(getClass().getResource("/images/Leaders/BACK.png").toExternalForm()));
        }
    }
}
