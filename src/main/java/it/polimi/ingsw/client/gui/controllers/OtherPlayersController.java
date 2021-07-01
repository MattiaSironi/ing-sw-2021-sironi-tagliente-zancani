package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.SceneList;
import it.polimi.ingsw.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

/**
 * OtherPlayersController controls OtherPlayerScene
 * @author Mattia Sironi
 */
public class OtherPlayersController implements GUIController {


    @FXML
    public ImageView dev3;
    @FXML
    public ImageView dev2;
    @FXML
    public ImageView dev1;
    @FXML
    public ImageView shelf2pos2;
    @FXML
    public ImageView shelf2pos1;
    @FXML
    public ImageView shelf3pos3;
    @FXML
    public ImageView shelf3pos2;
    @FXML
    public ImageView shelf3pos1;
    @FXML
    public ImageView shelf5pos2;
    @FXML
    public ImageView shelf5pos1;
    @FXML
    public ImageView shelf4pos2;
    @FXML
    public ImageView shelf4pos1;
    @FXML
    public ImageView shelf1pos1;
    @FXML
    public ImageView leader2;
    @FXML
    public ImageView leader1;
    @FXML
    public ImageView ft0;
    @FXML
    public ImageView ft1;
    @FXML
    public ImageView ft2;
    @FXML
    public ImageView ft3;
    @FXML
    public ImageView ft5;
    @FXML
    public ImageView ft6;
    @FXML
    public ImageView ft7;
    @FXML
    public ImageView ft8;
    @FXML
    public ImageView ft9;
    @FXML
    public ImageView ft10;
    @FXML
    public ImageView ft11;
    @FXML
    public ImageView ft12;
    @FXML
    public ImageView ft13;
    @FXML
    public ImageView ft14;
    @FXML
    public ImageView ft15;
    @FXML
    public ImageView ft16;
    @FXML
    public ImageView ft17;
    @FXML
    public ImageView ft18;
    @FXML
    public ImageView ft19;
    @FXML
    public ImageView ft20;
    @FXML
    public ImageView ft21;
    @FXML
    public ImageView ft22;
    @FXML
    public ImageView ft23;
    @FXML
    public ImageView ft24;
    @FXML
    public ImageView ft4;
    @FXML
    public ImageView tile1;
    @FXML
    public ImageView tile2;
    @FXML
    public ImageView tile3;
    @FXML
    public ImageView back;
    @FXML
    public Label shieldNum;
    @FXML
    public Label servantNum;
    @FXML
    public Label coinNum;
    @FXML
    public Label stoneNum;
    public Label name;


    MainController mainController;
    GUI gui;
    private  int ID;
    ArrayList<ImageView> faithTrack;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setMainController(MainController m) {
        this.mainController = m;
        this.gui= m.getGui();

    }


    public void setup(int num) {
        setID(num);
        name.setText(mainController.getGame().getPlayerById(getID()).getNickname() + "'s BOARD");





        if(mainController.getGame().getPlayerById(getID()).getPersonalBoard().getCardSlot().get(0).getCards().size() != 0){
            dev1.setImage(new Image(getClass().getResource("/images/Devs/FRONT/" +
                    mainController.getGame().getPlayerById(getID()).getPersonalBoard().getCardSlot().get(0).getCards().get(0) +
                    ".png").toExternalForm()));

        }
        else dev1.setImage(null);
        if(mainController.getGame().getPlayerById(getID()).getPersonalBoard().getCardSlot().get(1).getCards().size() != 0){
            dev2.setImage(new Image(getClass().getResource("/images/Devs/FRONT/" +
                    mainController.getGame().getPlayerById(getID()).getPersonalBoard().getCardSlot().get(1).getCards().get(0) +
                    ".png").toExternalForm()));
        }
        else dev2.setImage(null);
        if(mainController.getGame().getPlayerById(getID()).getPersonalBoard().getCardSlot().get(2).getCards().size() != 0){
            dev3.setImage(new Image(getClass().getResource("/images/Devs/FRONT/" +
                    mainController.getGame().getPlayerById(getID()).getPersonalBoard().getCardSlot().get(2).getCards().get(0) +
                    ".png").toExternalForm()));
        }
        else dev3.setImage(null);

        groupFaithSlots();
        showFaithTrack();
        showShelves();
        showStrongbox();
        showLeaders();
    }

    @Override
    public void print(Turn turn) {

    }

    @Override
    public void print(Communication communication) {

    }

    @Override
    public void disable() {

    }

    @Override
    public void enable() {

    }


    public void backButtonOpacityUp(MouseEvent mouseEvent) {
        back.setOpacity(1);
    }

    public void backButtonOpacityDown(MouseEvent mouseEvent) {
        back.setOpacity(0.5);
    }


    public void goBack(MouseEvent mouseEvent) {
        mainController.getGui().changeScene(SceneList.MAINSCENE);
    }

    /**
     * Method showFaithTrack shows selected opponent's FaithTrack
     */

    public void showFaithTrack() {
        FaithTrack ft = this.mainController.getGame().getPlayerById(getID()).getPersonalBoard().getFaithTrack();
        for (ImageView slot : this.faithTrack) {
            if (faithTrack.indexOf(slot) == ft.getMarker())
                slot.setImage((new Image(getClass().getResource("/images/PunchBoard/faith_point.png").toExternalForm())));
            else if (mainController.getGame().getPlayers().size() == 1 && faithTrack.indexOf(slot) == ft.getLoriPos())
                slot.setImage((new Image(getClass().getResource("/images/PunchBoard/croce.png").toExternalForm())));
            else slot.setImage(null);
        }

        Integer t1 = ft.getFavorTile1();
        Integer t2 = ft.getFavorTile2();
        Integer t3 = ft.getFavorTile3();

        if (t1 != null)
            tile1.setImage(new Image(getClass().getResource("/images/PunchBoard/t1" + t1 + ".png").toExternalForm()));
        if (t2 != null)
            tile2.setImage(new Image(getClass().getResource("/images/PunchBoard/t2" + t2 + ".png").toExternalForm()));
        if (t3 != null)
            tile3.setImage(new Image(getClass().getResource("/images/PunchBoard/t3" + t3 + ".png").toExternalForm()));
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
        faithTrack.add(ft21);
        faithTrack.add(ft22);
        faithTrack.add(ft23);
        faithTrack.add(ft24);
    }

    /**
     * Method showShelves shows selected opponent's ShelfWarehouse.
     */

    public void showShelves() {
        ShelfWarehouse myShelves = mainController.getGame().getPlayerById(getID()).getPersonalBoard().getWarehouse();
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
        } else for (ImageView iv : shelf4) iv.setImage(null);

        if (myShelves.getShelves().get(4).getResType() != null) {
            r = myShelves.getShelves().get(4).getResType();
            max = myShelves.getShelves().get(4).getCount();
            for (ImageView iv : shelf5) {
                iv.setImage(new Image(getClass().getResource("/images/PunchBoard/" + r.toString().toLowerCase() + ".png").toExternalForm()));
                if (!(shelf5.indexOf(iv) <= max - 1)) iv.setOpacity(0.5);
                else iv.setOpacity(1.0);
            }
        } else for (ImageView iv : shelf5) iv.setImage(null);

    }

    /**
     * Method showStrongbox shows selected opponent's Strongbox.
     */
    public void showStrongbox(){
        coinNum.setText("" + mainController.getGame().getPlayerById(getID()).getPersonalBoard().getStrongbox().getResCount(ResourceType.COIN));
        stoneNum.setText("" + mainController.getGame().getPlayerById(getID()).getPersonalBoard().getStrongbox().getResCount(ResourceType.STONE));
        servantNum.setText("" + mainController.getGame().getPlayerById(getID()).getPersonalBoard().getStrongbox().getResCount(ResourceType.SERVANT));
        shieldNum.setText("" + mainController.getGame().getPlayerById(getID()).getPersonalBoard().getStrongbox().getResCount(ResourceType.SHIELD));
    }

    /**
     * Method showLeaders shows selected opponent's LeaderCards.
     */
    public void showLeaders() {

        ArrayList<ImageView> leaders = new ArrayList<>();
        leaders.add(leader1);
        leaders.add(leader2);
        for (ImageView i : leaders) {
            i.setImage(new Image(getClass().getResource("/images/Leaders/BACK.png" ).toExternalForm()));
            i.setOpacity(1.0);
        }
        int i=0;

        for (LeaderCard l : mainController.getGame().getPlayerById(getID()).getPersonalBoard().getActiveLeader().getCards()) {
            leaders.get(i).setImage(new Image(getClass().getResource("/images/Leaders/" + l.toString() + ".png" ).toExternalForm())); //Active leaders
            leaders.get(i).setOpacity(1.0);
            i++;

        }
        for (LeaderCard l : mainController.getGame().getPlayerById(getID()).getLeaderDeck().getCards()) i++; //inactive leaders

        for (; i<2 ; i++) {
            leaders.get(i).setOpacity(0.5); //discarded leaders
            leaders.get(i).setImage(new Image(getClass().getResource("/images/Leaders/BACK.png" ).toExternalForm()));
        }
    }

}

