package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.SceneList;
import it.polimi.ingsw.message.CommonMessages.Nickname;
import it.polimi.ingsw.model.Communication;
import it.polimi.ingsw.model.Turn;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class StartController implements GUIController{

    public TextField nicknameInput;
    public Label chooseNickLabel;
    public ImageView sendButton;
    @FXML private ImageView localGame;
    @FXML private ImageView multiGame;
    private GUI gui;


    public void playLocalGame(MouseEvent actionEvent) {
        gui.setLocal(true);
        multiGame.setDisable(true);
        localGame.setDisable(true);
        multiGame.setVisible(false);
        localGame.setVisible(false);
        chooseNickLabel.setVisible(true);
        chooseNickLabel.setDisable(false);
        nicknameInput.setVisible(true);
        nicknameInput.setDisable(false);
        sendButton.setVisible(true);
        sendButton.setDisable(false);
        gui.createLocalGame();
    }



    public void startOnlineGame(MouseEvent actionEvent) {
//        gui.playButtonSound();
        gui.changeScene(SceneList.SETUP);
    }

    public void sendNickname(MouseEvent mouseEvent) {
        nicknameInput.setDisable(true);
        sendButton.setMouseTransparent(true);
        gui.getMainController().send(new Nickname(nicknameInput.getText(), gui.getID()));
    }

    public void opacityUpSend(MouseEvent mouseEvent) {
        sendButton.setOpacity(1);
    }

    public void opacityDownSend(MouseEvent mouseEvent) {
        sendButton.setOpacity(0.5);
    }

    public void playLocalOpacityUp(MouseEvent mouseEvent) {
        localGame.setOpacity(1);
    }

    public void playLocalOpacityDown(MouseEvent mouseEvent) {
        localGame.setOpacity(0.5);
    }

    public void playOnlineOpacityUp(MouseEvent mouseEvent) {
        multiGame.setOpacity(1);
    }

    public void playOnlineOpacityDown(MouseEvent mouseEvent) {
        multiGame.setOpacity(0.5);
    }

    @Override
    public void setMainController(MainController m) {
        this.gui = m.getGui();
    }

    @Override
    public void setup(int  num) {

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
}
