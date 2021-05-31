package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.SceneList;
import it.polimi.ingsw.message.CommonMessages.Nickname;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class StartController implements GUIController{

    public TextField nicknameInput;
    public Label chooseNickLabel;
    public ImageView sendButton;
    @FXML private Button localGame;
    @FXML private Button multiGame;
    private GUI gui;

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    public void playLocalGame(ActionEvent actionEvent) {
        localGame.setDisable(true);
        chooseNickLabel.setVisible(true);
        chooseNickLabel.setDisable(false);
        nicknameInput.setVisible(true);
        nicknameInput.setDisable(false);
        sendButton.setVisible(true);
        sendButton.setDisable(false);
    }



    public void startOnlineGame(ActionEvent actionEvent) {
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
}
