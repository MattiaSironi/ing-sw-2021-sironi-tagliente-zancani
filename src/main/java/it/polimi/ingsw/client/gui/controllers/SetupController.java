package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.SocketServerConnection;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.message.CommonMessages.ChooseNumberOfPlayer;
import it.polimi.ingsw.message.CommonMessages.Nickname;
import it.polimi.ingsw.model.Communication;
import it.polimi.ingsw.model.Turn;
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

/**
 * SetupController class handles the setup.fxml by performing actions from the GUI
 */

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

    /**
     * Method setupConnection opens the connection to the server
     * @param actionEvent of type ActionEvent - the event received.
     */

    public void setupConnection(MouseEvent actionEvent) {
        try {
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
        } catch(IOException ignored){

        }
    }

//    public void playClickSound(){
//        AudioClip clickSound = new AudioClip(this.getClass().getResource("sound/" + clickSoundPath).toString());
//        clickSound.play();
//    }

    /**
     * method updateHostScene sets up the scene for the host of the game
     */

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

    /**
     * Method setNumberOfPlayers sends the number of player chosen by the host of the game
     */

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
        } catch(NumberFormatException | NullPointerException ignored){

        }
    }


    /**
     * method askForNickname sets up the scene for the nickname input
     */

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

    /**
     * Method sendNickname sends the nickname chosen by the player
     * @param actionEvent of type ActionEvent - the event received.
     */

    public void sendNickname(MouseEvent actionEvent) {
        nicknameInput.setDisable(true);
        sendButton.setMouseTransparent(true);
        gui.getMainController().send(new Nickname(nicknameInput.getText(), gui.getID()));
    }

    /**
     * method setDuplicatedNickname shows a label if the nickname chosen by he player already exists
     */

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

    @Override
    public void setMainController(MainController m) {
        this.gui = m.getGui();
    }

    @Override
    public void setup(int num) {

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
