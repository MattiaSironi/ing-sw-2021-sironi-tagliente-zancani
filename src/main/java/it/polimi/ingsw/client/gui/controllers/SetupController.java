package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.SocketServerConnection;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.message.CommonMessages.ChooseNumberOfPlayer;
import it.polimi.ingsw.message.CommonMessages.Nickname;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;

import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class SetupController implements GUIController{


    public ImageView sendButton;
    public ImageView playButton;
    public ImageView startButton;
    @FXML private Label waitingForPlayers;
    @FXML private Label duplicateNickLabel;
    @FXML private Label chooseNickLabel;
    @FXML private TextField nicknameInput;
    private GUI gui;
    @FXML private TextField addressIn;
    @FXML private Label label1;
    @FXML private Label waitingLabel;
    @FXML private ChoiceBox choiceBox;
    @FXML private Label playerLabel;
//    private static final String clickSoundPath = "ClickSound.wav";




    public void setupConnection(MouseEvent actionEvent) {
        try {
//            playClickSound();
            addressIn.setDisable(false);
            startButton.setDisable(true);
            waitingLabel.setDisable(false);
            waitingLabel.setVisible(true);
            gui.getMainController().setServerConnection(new SocketServerConnection());
            gui.getMainController().getServerConnection().setUI(gui.getMainController());
            gui.getMainController().getServerConnection().socketInit(addressIn.getText());
            addressIn.setDisable(true);
            addressIn.setVisible(false);
            startButton.setVisible(false);
            startButton.setMouseTransparent(true);
            label1.setVisible(false);

        } catch(IOException e){

        }
    }

//    public void playClickSound(){
//        AudioClip clickSound = new AudioClip(this.getClass().getResource("sound/" + clickSoundPath).toString());
//        clickSound.play();
//    }

    public void updateHostScene(){

        waitingLabel.setDisable(true);
        waitingLabel.setVisible(false);
        playerLabel.setVisible(true);
        playerLabel.setDisable(false);
        choiceBox.setVisible(true);
        choiceBox.setDisable(false);
        playButton.setDisable(false);
        playButton.setVisible(true);
        choiceBox.getItems().add(0,"1");
        choiceBox.getItems().add(0,"2");
        choiceBox.getItems().add(0,"3");
        choiceBox.getItems().add(0,"4");
    }

    public void setNumberOfPlayers(){
        try {

            gui.getMainController().send(new ChooseNumberOfPlayer(Integer.parseInt(choiceBox.getValue().toString())));
            waitingForPlayers.setVisible(true);
            waitingForPlayers.setDisable(false);
            playerLabel.setVisible(false);
            playerLabel.setDisable(true);
            choiceBox.setDisable(true);
            choiceBox.setVisible(false);
            playButton.setDisable(true);
            playButton.setVisible(false);
        } catch(NumberFormatException e){

        }
    }

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }


    public void askForNickname() {
        waitingForPlayers.setDisable(true);
        waitingForPlayers.setVisible(false);
        playButton.setMouseTransparent(true);
        waitingLabel.setVisible(false);
        waitingLabel.setDisable(true);
        chooseNickLabel.setVisible(true);
        chooseNickLabel.setDisable(false);
        nicknameInput.setVisible(true);
        nicknameInput.setDisable(false);
        sendButton.setVisible(true);
        sendButton.setDisable(false);
    }

    public void sendNickname(MouseEvent actionEvent) {
        nicknameInput.setDisable(true);
        sendButton.setMouseTransparent(true);
        gui.getMainController().send(new Nickname(nicknameInput.getText(), gui.getID()));
    }

    public void setDuplicatedNickname(){
        this.duplicateNickLabel.setVisible(true);
        this.duplicateNickLabel.setDisable(false);
        nicknameInput.setDisable(false);
        this.nicknameInput.clear();
        sendButton.setMouseTransparent(false);
    }

    public void opacityUp(MouseEvent mouseEvent) {
        startButton.setOpacity(1);
    }

    public void opacityDown(MouseEvent mouseEvent) {
        startButton.setOpacity(0.5);
    }

    public void opacityUpSend(MouseEvent mouseEvent) {
        sendButton.setOpacity(1);
    }

    public void opacityDownSend(MouseEvent mouseEvent) {
        sendButton.setOpacity(0.5);
    }

    public void opacityUpPlay(MouseEvent mouseEvent) {
        playButton.setOpacity(1);
    }

    public void opacityDownPlay(MouseEvent mouseEvent) {
        playButton.setOpacity(0.5);
    }
}
