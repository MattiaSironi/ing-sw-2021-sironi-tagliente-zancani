package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.SocketServerConnection;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.message.CommonMessages.ChooseNumberOfPlayer;
import it.polimi.ingsw.message.CommonMessages.Nickname;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SetupController implements GUIController{


    @FXML private Label duplicateNickLabel;
    @FXML private Label chooseNickLabel;
    @FXML private TextField nicknameInput;
    @FXML private Button sendNickname;
    @FXML private Button playButton;
    private GUI gui;
    @FXML private TextField addressIn;
    @FXML private Label label1;
    @FXML private Button openConnection;
    @FXML private Label waitingLabel;
    @FXML private ChoiceBox choiceBox;
    @FXML private Label playerLabel;




    public void setupConnection(ActionEvent actionEvent) {
        try {
            openConnection.setDisable(true);
            waitingLabel.setDisable(false);
            waitingLabel.setVisible(true);
            System.out.println(addressIn.getText());
            gui.setServerConnection(new SocketServerConnection());
            gui.getServerConnection().setUI(gui);
            gui.getServerConnection().socketInit(addressIn.getText());

        } catch(IOException e){

        }
    }

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
            gui.getServerConnection().send(new ChooseNumberOfPlayer(Integer.parseInt(choiceBox.getValue().toString())));
        } catch(NumberFormatException e){

        }
    }

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }


    public void askForNickname() {
        chooseNickLabel.setVisible(true);
        chooseNickLabel.setDisable(false);
        nicknameInput.setVisible(true);
        nicknameInput.setDisable(false);
        sendNickname.setVisible(true);
        sendNickname.setDisable(false);
    }

    public void sendNickname(ActionEvent actionEvent) {
        nicknameInput.setDisable(true);
        gui.getServerConnection().send(new Nickname(nicknameInput.getText(), gui.getID()));
    }

    public void setDuplicatedNickname(){
        this.duplicateNickLabel.setVisible(true);
        this.duplicateNickLabel.setDisable(false);
        nicknameInput.setDisable(false);
        this.nicknameInput.clear();
    }
}
