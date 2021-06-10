package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.SceneList;
import it.polimi.ingsw.message.ActionMessages.BuyDevCardMessage;
import it.polimi.ingsw.model.Communication;
import it.polimi.ingsw.model.Turn;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class PlaceDevCardScene implements GUIController{
    public Button firstSlot;
    public Button secondSlot;
    public Button thirdSlot;
    public ImageView back;
    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    @Override
    public void setup() {
        back.setDisable(true);
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

    public void sendFirstSlot(ActionEvent actionEvent) {
        back.setDisable(false);
        mainController.send(new BuyDevCardMessage(-1, mainController.getGui().getID(), true, 0));

    }

    public void sendSecondSlot(ActionEvent actionEvent) {
        back.setDisable(false);
        mainController.send(new BuyDevCardMessage(-1, mainController.getGui().getID(), true, 1));
    }

    public void sendThirdSlot(ActionEvent actionEvent) {
        back.setDisable(false);
        mainController.send(new BuyDevCardMessage(-1, mainController.getGui().getID(), true, 2));
    }

    public void back(ActionEvent actionEvent) {
        mainController.getGui().changeScene(SceneList.MAINSCENE);
    }
}
