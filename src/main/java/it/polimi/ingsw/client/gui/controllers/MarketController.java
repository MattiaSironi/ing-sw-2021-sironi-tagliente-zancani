package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.SceneList;
import it.polimi.ingsw.message.ActionMessages.ManageResourceMessage;
import it.polimi.ingsw.message.ActionMessages.MarketMessage;
import it.polimi.ingsw.message.ActionMessages.PlaceResourceMessage;
import it.polimi.ingsw.model.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Objects;

/**
 * MarketController controls MarketScene and his main goal is to control "Buy Resources" action.
 */

public class MarketController implements GUIController{

    public ImageView resourceSelector;
    public ImageView handPos1;
    public ImageView handPos2;
    public ImageView handPos3;
    public ImageView handPos4;
    public ImageView res1;
    public Label slash;
    public ImageView res2;
    public ImageView discard;
    public Pane background;
    public ImageView bgImage;
    public ImageView marketTray;
    public ImageView back;
    public ImageView marketBoard;
    public ImageView marketHand;
    public ImageView warehouse;
    public ImageView shelf1pos1;
    public ImageView shelf4pos1;
    public ImageView shelf4pos2;
    public ImageView shelf5pos1;
    public ImageView shelf5pos2;
    public ImageView marble11;
    public ImageView marble12;
    public ImageView marble13;
    public ImageView marble14;
    public ImageView marble21;
    public ImageView marble22;
    public ImageView marble23;
    public ImageView marble24;
    public ImageView marble31;
    public ImageView marble32;
    public ImageView marble33;
    public ImageView marble34;
    public ImageView marbleOut;
    public ImageView shelf3pos1;
    public ImageView shelf3pos2;
    public ImageView shelf3pos3;
    public ImageView shelf2pos1;
    public ImageView shelf2pos2;
    public Button shelf1;
    public Button shelf2;
    public Button shelf3;
    public Button shelf4;
    public Button shelf5;
    public Button row1;
    public Button row2;
    public Button row3;
    public Button column1;
    public Button column2;
    public Button column3;
    public Button column4;
    public Label phase = new Label();
    public Label comMessages = new Label();
    private MainController mainController;
    private ArrayList<Button> arrows;
    private ArrayList<Button> shelves;
    private ArrayList<ImageView> res;
    private ArrayList<ImageView> hand;
    private ResourceType selectedRes;
    private GUI gui;
    private Integer s1;


    @Override
    public void setup(int num) {
        if (!mainController.isMarketValid() || !mainController.isWareValid()) {
            mainController.setMarketValid(true);
            mainController.setWareValid(true);
            groupArrows();
            groupRes();
            groupShelves();
            groupHand();
            showShelves();
            showMarket();






        }
        back.setDisable(false);


    }


    @Override
    public void print(Turn turn) {
        if (turn.getPlayerPlayingID() != gui.getID()) {
            phase.setText(this.mainController.getGame().getPlayerById(turn.getPlayerPlayingID()).getNickname() + " " + turn.getPhase().getOthers());
        } else
            phase.setText("Your turn");
    }

    @Override
    public void print(Communication communication) {
        comMessages.setText(communication.getCommunication().getString());
    }

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
        this.gui=m.getGui();

    }

    private void groupShelves() {
        shelves = new ArrayList<>();
        shelves.add(shelf1);
        shelves.add(shelf2);
        shelves.add(shelf3);
        shelves.add(shelf4);
        shelves.add(shelf5);
    }
    private void groupRes() {
        res = new ArrayList<>();
        res.add(res1);
        res.add(res2);
        res.add(discard);
    }

    private void groupArrows() {
        arrows = new ArrayList<>();
        arrows.add(row1);
        arrows.add(row2);
        arrows.add(row3);
        arrows.add(column1);
        arrows.add(column2);
        arrows.add(column3);
        arrows.add(column4);
    }
    private void groupHand() {
        hand = new ArrayList<>();
        hand.add(handPos1);
        hand.add(handPos2);
        hand.add(handPos3);
        hand.add(handPos4);
    }


    /**
     * Method showShelves shows GUI Player's ShelfWarehouse
     */
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
        else for (ImageView iv : shelf4) iv.setImage(null);
        if (myShelves.getShelves().get(4).getResType() != null) {
            r = myShelves.getShelves().get(4).getResType();
            max = myShelves.getShelves().get(4).getCount();
            for (ImageView iv : shelf5) {
                iv.setImage(new Image(getClass().getResource("/images/PunchBoard/" + r.toString().toLowerCase() + ".png").toExternalForm()));
                if (!(shelf5.indexOf(iv) <= max - 1)) iv.setOpacity(0.5);
                else iv.setOpacity(1.0);
            }
        }
        else for (ImageView iv : shelf5) iv.setImage(null);
    }

    /**
     * Method showMarket show Game Market
     */

    public void showMarket() {

        Market market = mainController.getGame().getBoard().getMarket();
        Marble[][] marketBoard = market.getMarketBoard();

        marble11.setImage(new Image(getClass().getResource("/images/PunchBoard/market/" + marketBoard[0][0].getRes().toString().toLowerCase() + ".png").toExternalForm()));
        marble12.setImage(new Image(getClass().getResource("/images/PunchBoard/market/" + marketBoard[0][1].getRes().toString().toLowerCase() + ".png").toExternalForm()));
        marble13.setImage(new Image(getClass().getResource("/images/PunchBoard/market/" + marketBoard[0][2].getRes().toString().toLowerCase() + ".png").toExternalForm()));
        marble14.setImage(new Image(getClass().getResource("/images/PunchBoard/market/" + marketBoard[0][3].getRes().toString().toLowerCase() + ".png").toExternalForm()));
        marble21.setImage(new Image(getClass().getResource("/images/PunchBoard/market/" + marketBoard[1][0].getRes().toString().toLowerCase() + ".png").toExternalForm()));
        marble22.setImage(new Image(getClass().getResource("/images/PunchBoard/market/" + marketBoard[1][1].getRes().toString().toLowerCase() + ".png").toExternalForm()));
        marble23.setImage(new Image(getClass().getResource("/images/PunchBoard/market/" + marketBoard[1][2].getRes().toString().toLowerCase() + ".png").toExternalForm()));
        marble24.setImage(new Image(getClass().getResource("/images/PunchBoard/market/" + marketBoard[1][3].getRes().toString().toLowerCase() + ".png").toExternalForm()));
        marble31.setImage(new Image(getClass().getResource("/images/PunchBoard/market/" + marketBoard[2][0].getRes().toString().toLowerCase() + ".png").toExternalForm()));
        marble32.setImage(new Image(getClass().getResource("/images/PunchBoard/market/" + marketBoard[2][1].getRes().toString().toLowerCase() + ".png").toExternalForm()));
        marble33.setImage(new Image(getClass().getResource("/images/PunchBoard/market/" + marketBoard[2][2].getRes().toString().toLowerCase() + ".png").toExternalForm()));
        marble34.setImage(new Image(getClass().getResource("/images/PunchBoard/market/" + marketBoard[2][3].getRes().toString().toLowerCase() + ".png").toExternalForm()));
        marbleOut.setImage(new Image(getClass().getResource("/images/PunchBoard/market/" + market.getMarbleOut().getRes().toString().toLowerCase() + ".png").toExternalForm()));

        res1.setImage(null);
        res2.setImage(null);
        slash.setText("");
        res1.setUserData(null);
        res2.setUserData(null);
        for (ImageView iv : this.hand) iv.setImage(null);

        if (mainController.getGame().getTurn().getPlayerPlayingID() == gui.getID()) showHand();



    }

    /**
     * Method showHand shows Market Hand if GUI player is playing and Hand is not empty.
     */


    private void showHand() {
        int i = 0;
        ArrayList<Marble> hand = mainController.getGame().getBoard().getMarket().getHand();

        for (Marble m : hand) {
            this.hand.get(i).setImage((new Image(getClass().getResource("/images/PunchBoard/market/" + m.getRes().toString().toLowerCase() + ".png").toExternalForm())));
            i++;

        }
        for (; i < 4; i++) this.hand.get(i).setImage(null);

        if (hand.size() == 0){
            shelvesForSwap();
            back.setDisable(false);
            discard.setDisable(true);

        }
        else showResourcesToSelect();
    }

    /**
     * Method showResourcesToSelect shows the resource associated to the first Marble in Hand, if present.
     */

    private void showResourcesToSelect() {

        discard.setDisable(false);
        Marble first = mainController.getGame().getBoard().getMarket().getHand().get(0);

        if (first.getRes() != ResourceType.EMPTY) {
            res1.setImage(new Image(getClass().getResource("/images/PunchBoard/" + first.getRes().toString().toLowerCase() + ".png").toExternalForm()));
            slash.setText("");
            res2.setImage(null);
            res1.setUserData(first.getRes().toString().toLowerCase());
            res2.setUserData(null);
        } else whiteMarbleCase();


    }

    /**
     * Method whiteMarbleCase is called when first resource in Hand in EMPTY. According to Model, it shows one of the three possible cases.
     */

    private void whiteMarbleCase() {
        ArrayList<ResourceType> possibleRes = new ArrayList<>();
        possibleRes.add(mainController.getGame().getPlayerById(gui.getID()).getWhiteConversion1());
        possibleRes.add(mainController.getGame().getPlayerById(gui.getID()).getWhiteConversion2());
        int count = (int) possibleRes.stream().filter(Objects::nonNull).count();

        switch (count) {
            case 0 -> {
                res1.setImage(new Image(getClass().getResource("/images/PunchBoard/nothing.png").toExternalForm()));
                res1.setUserData("empty");
                slash.setText("");
                res2.setImage(null);

                res2.setUserData(null);

            }
            case 1 -> {
                res1.setImage(new Image(getClass().getResource("/images/PunchBoard/" + possibleRes.get(0).toString().toLowerCase() + ".png").toExternalForm()));
                res1.setUserData(possibleRes.get(0).toString().toLowerCase());
                slash.setText("");
                res2.setImage(null);
                res2.setUserData(null);
            }
            default -> {
                res1.setImage(new Image(getClass().getResource("/images/PunchBoard/" + possibleRes.get(0).toString().toLowerCase() + ".png").toExternalForm()));
                res1.setUserData(possibleRes.get(0).toString().toLowerCase());
                slash.setText("/");
                res2.setImage(new Image(getClass().getResource("/images/PunchBoard/" + possibleRes.get(1).toString().toLowerCase() + ".png").toExternalForm()));
                res2.setUserData(possibleRes.get(1).toString().toLowerCase());
            }
        }
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
     * Method goToMarket is triggered when the playing Player has clicked one arrow. It creates a MarketMessage and sends it.
     * @param mouseEvent is used to understand which arrow has been clicked.
     */


    public void goToMarket(MouseEvent mouseEvent) {
        mainController.noMoreActions();
        back.setDisable(true);

        boolean row;
        int index;
        Button selected = (Button) (mouseEvent.getTarget());
        switch (selected.getId()) {
            case "row1" -> {
                row = true;
                index = 0;

            }
            case "row2" -> {
                row = true;
                index = 1;
            }
            case "row3" -> {
                row = true;
                index = 2;
            }
            case "column1" -> {
                row = false;
                index = 0;
            }
            case "column2" -> {
                row = false;
                index = 1;
            }
            case "column3" -> {
                row = false;
                index = 2;
            }
            default -> {
                row = false;
                index = 3;
            }
        }
        mainController.send(new MarketMessage(row, index, mainController.getGui().getID()));
        shelvesForMarket();


        }


    /**
     * Method saveRes is triggered when Player clicked on Resource appeared next to Market Hand.
     * @param mouseEvent is used to understand which resource has been clicked.
     */
    public void saveRes(MouseEvent mouseEvent) {
        Image image = ((ImageView) (mouseEvent.getTarget())).getImage();
        if (image == null) return;
        String s =  (String)((ImageView) (mouseEvent.getTarget())).getUserData();
        if (!s.equals("empty") && !s.equals("faith_point")) {
            selectedRes = ResourceType.valueOf(s.toUpperCase());


        }
        else mainController.send(new PlaceResourceMessage(ResourceType.valueOf(s.toUpperCase()), -1, gui.getID(), false, false));


    }

    /**
     * Method chooseShelf is triggered when a Player clicked on a shelf during "Buy Resources" action.
     * @param mouseEvent is used to understand which shelf has been clicked.
     */

    public void chooseShelf(MouseEvent mouseEvent) {

        if (selectedRes == null) return;
        int shelf;
        Button selected = (Button) (mouseEvent.getTarget());
        shelf = selectedShelves(selected.getId());

        mainController.send(new PlaceResourceMessage(selectedRes, shelf, gui.getID(), false, false  ));
        selectedRes = null;




    }

    /**
     * Method selectedShelves is used to have the index of the shelf, according its ID
     * @param id is the ID of the shelf
     * @return its index.
     */

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

    /**
     * Method discardRes is called when "Trash" is clicked. it discards the resource.
     */

    public void discardRes(MouseEvent mouseEvent) {
        if (selectedRes == null) return;
        mainController.send(new PlaceResourceMessage(selectedRes, -1, gui.getID(), false, true));
        selectedRes = null;
        discard.setDisable(true);

    }

    /**
     * Method swap is triggered after of before "Buy Resources" action. it creates and sends a ManageResource message.
     * @param mouseEvent
     */

    public void swap(MouseEvent mouseEvent) {
        if (s1 == null) s1 = selectedShelves(((Button) mouseEvent.getTarget()).getId());
        else {
            int s2 = selectedShelves(((Button) mouseEvent.getTarget()).getId());
            mainController.send(new ManageResourceMessage(s1, s2, gui.getID()));
            s1 = null;

        }
    }

    public void disable() {
        groupArrows();
        groupRes();
        groupShelves();
        disableArrows();
        disableRes();
        disableShelves();



    }

    public void enable() {
        groupHand();
        groupArrows();
        groupRes();
        groupShelves();
        enableArrows();
        enableRes();
        enableShelves();
        shelvesForSwap();
        discard.setDisable(true);

    }

    private void enableShelves() {
        for (Button shelf : shelves) shelf.setDisable(false);
    }

    private void disableShelves() {
        for (Button shelf : shelves) shelf.setDisable(true);

    }

    /**
     * Method shelvesForSwap changes function triggered on MouseClicked
     */
    private void shelvesForSwap() {
        for (Button shelf : shelves) shelf.setOnMouseClicked(this::swap);
    }

    /**
     * Method shelvesForMarket changes function triggered on MouseClicked
     */
    private void shelvesForMarket() {
        for (Button shelf : shelves) shelf.setOnMouseClicked(this::chooseShelf);
    }

    private void enableRes() {
        for (ImageView res : this.res) res.setDisable(false);
    }

    private void disableRes() {
        for (ImageView res : this.res) res.setDisable(true);

    }

    private void enableArrows() {
        for (Button arrow : arrows) arrow.setDisable(false);
    }

    public void disableArrows() {
        for (Button arrow : arrows) arrow.setDisable(true);
    }

}
