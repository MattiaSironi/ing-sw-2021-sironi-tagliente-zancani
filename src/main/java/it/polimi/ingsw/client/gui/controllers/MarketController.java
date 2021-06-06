package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.SceneList;
import it.polimi.ingsw.model.Market;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class MarketController implements GUIController{

    public Pane background;
    public ImageView bgImage;
    public ImageView marketTray;
    public ImageView back;
    public ImageView marketBoard;
    public ImageView marketHand;
    public ImageView shelves;
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
    private MainController mainController;
    private ArrayList<ImageView> marbles;

    public void setup() {
        showMarket();


    }






    @Override
    public void setMainController(MainController m) {
        this.mainController = m;

    }

    public void showShelves() {}

    public void showMarket() {

         Market market = mainController.getGame().getBoard().getMarket();


        marble11.setImage(new Image(getClass().getResource("/images/PunchBoard/market/" + market.getMarketBoard()[0][0].getRes().toString().toLowerCase() + ".png").toExternalForm()));
        marble12.setImage(new Image(getClass().getResource("/images/PunchBoard/market/" + market.getMarketBoard()[0][1].getRes().toString().toLowerCase() + ".png").toExternalForm()));
        marble13.setImage(new Image(getClass().getResource("/images/PunchBoard/market/" + market.getMarketBoard()[0][2].getRes().toString().toLowerCase() + ".png").toExternalForm()));
        marble14.setImage(new Image(getClass().getResource("/images/PunchBoard/market/" + market.getMarketBoard()[0][3].getRes().toString().toLowerCase() + ".png").toExternalForm()));
        marble21.setImage(new Image(getClass().getResource("/images/PunchBoard/market/" + market.getMarketBoard()[1][0].getRes().toString().toLowerCase() + ".png").toExternalForm()));
        marble22.setImage(new Image(getClass().getResource("/images/PunchBoard/market/" + market.getMarketBoard()[1][1].getRes().toString().toLowerCase() + ".png").toExternalForm()));
        marble23.setImage(new Image(getClass().getResource("/images/PunchBoard/market/" + market.getMarketBoard()[1][2].getRes().toString().toLowerCase() + ".png").toExternalForm()));
        marble24.setImage(new Image(getClass().getResource("/images/PunchBoard/market/" + market.getMarketBoard()[1][3].getRes().toString().toLowerCase() + ".png").toExternalForm()));
        marble31.setImage(new Image(getClass().getResource("/images/PunchBoard/market/" + market.getMarketBoard()[2][0].getRes().toString().toLowerCase() + ".png").toExternalForm()));
        marble32.setImage(new Image(getClass().getResource("/images/PunchBoard/market/" + market.getMarketBoard()[2][1].getRes().toString().toLowerCase() + ".png").toExternalForm()));
        marble33.setImage(new Image(getClass().getResource("/images/PunchBoard/market/" + market.getMarketBoard()[2][2].getRes().toString().toLowerCase() + ".png").toExternalForm()));
        marble34.setImage(new Image(getClass().getResource("/images/PunchBoard/market/" + market.getMarketBoard()[2][3].getRes().toString().toLowerCase() + ".png").toExternalForm()));
        marbleOut.setImage(new Image(getClass().getResource("/images/PunchBoard/market/" + market.getMarbleOut().getRes().toString().toLowerCase() + ".png").toExternalForm()));


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


}
