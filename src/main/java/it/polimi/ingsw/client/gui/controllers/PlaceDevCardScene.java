package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.SceneList;
import it.polimi.ingsw.message.ActionMessages.BuyDevCardMessage;
import it.polimi.ingsw.model.Communication;
import it.polimi.ingsw.model.Turn;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * PlaceDevCardScene handles the placeDevCardScene.fxml by performing actions from the GUI
 */

public class PlaceDevCardScene implements GUIController{
    public Button firstSlot;
    public Button secondSlot;
    public Button thirdSlot;
    public ImageView back;
    public ImageView dev1;
    public ImageView dev2;
    public ImageView dev3;
    private MainController mainController;
    public Label phase;
    public Label comMessages;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    /**
     * Method setup prepares the scene when opened
     * @param num
     */

    @Override
    public void setup(int num) {
        firstSlot.setDisable(false);
        secondSlot.setDisable(false);
        thirdSlot.setDisable(false);
        back.setDisable(true);
        updateDevSlot();
    }

    @Override
    public void print(Turn turn) {
        if (turn.getPlayerPlayingID() != mainController.getGui().getID()) {

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

    }

    @Override
    public void enable() {

    }
    /**
     * Method sendFirstSlot send the message when the first slot is chosen
     */
    public void sendFirstSlot(ActionEvent actionEvent) {
        firstSlot.setDisable(true);
        secondSlot.setDisable(true);
        thirdSlot.setDisable(true);
        back.setDisable(false);
        mainController.send(new BuyDevCardMessage(-1, mainController.getGui().getID(), true, 0));

    }

    /**
     * Method sendFirstSlot send the message when the second slot is chosen
     */
    public void sendSecondSlot(ActionEvent actionEvent) {
        firstSlot.setDisable(true);
        secondSlot.setDisable(true);
        thirdSlot.setDisable(true);
        back.setDisable(false);
        mainController.send(new BuyDevCardMessage(-1, mainController.getGui().getID(), true, 1));
    }

    /**
     * Method sendFirstSlot send the message when the third slot is chosen
     */
    public void sendThirdSlot(ActionEvent actionEvent) {
        firstSlot.setDisable(true);
        secondSlot.setDisable(true);
        thirdSlot.setDisable(true);
        back.setDisable(false);
        mainController.send(new BuyDevCardMessage(-1, mainController.getGui().getID(), true, 2));
    }

    /**
     * Method back switches to the main scene of the game
     * @param actionEvent of type ActionEvent - the event received.
     */
    public void back(ActionEvent actionEvent) {
        mainController.noMoreActions();
        mainController.getGui().changeScene(SceneList.MAINSCENE);
    }

    /**
     * Method updateDevSlot handles the visualization of the development cards on the player's personal board
     */
    public void updateDevSlot() {
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
    }
}
