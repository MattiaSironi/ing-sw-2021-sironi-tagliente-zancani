package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.SceneList;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.ResourceType;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.sql.SQLOutput;

public class MainSceneController implements  GUIController {
    public ImageView activateButton;
    public ImageView discardButton;
    public ImageView backButton;
    LeaderCard l1;
    LeaderCard l2;
    boolean active1 = false;
    boolean active2 = false;
    public ImageView leader1;
    public ImageView leader2;
    private GUI gui;

    private MainController mainController;


    public void Leader1OpacityUp(MouseEvent mouseEvent) { leader1.setOpacity(1);}
    public void Leader1OpacityDown(MouseEvent mouseEvent) { leader1.setOpacity(0.75);}
    public void Leader2OpacityUp(MouseEvent mouseEvent) { leader2.setOpacity(1);}
    public void Leader2OpacityDown(MouseEvent mouseEvent) { leader2.setOpacity(0.75);}
    public void ActivateOpacityUp(MouseEvent mouseEvent) {activateButton.setOpacity(1);}
    public void ActivateOpacityDown(MouseEvent mouseEvent) { activateButton.setOpacity(0.75);}
    public void DiscardOpacityUp(MouseEvent mouseEvent) { discardButton.setOpacity(1);}
    public void DiscardOpacityDown(MouseEvent mouseEvent) { discardButton.setOpacity(0.75);}
    public void BackOpacityUp(MouseEvent mouseEvent) { backButton.setOpacity(1);}
    public void BackOpacityDown(MouseEvent mouseEvent) { backButton.setOpacity(0.75);}


    public void Leader1Clicked(MouseEvent mouseEvent) {
        if(!active1) {
            activateButton.setVisible(true);
            activateButton.setDisable(false);
            discardButton.setVisible(true);
            discardButton.setDisable(false);
            backButton.setVisible(true);
            backButton.setDisable(false);
            leader1.setImage(new Image(getClass().getResource("/images/Leaders/" + leader1.toString() + ".png").toExternalForm()));
        }
    }

    public void Leader2Clicked(MouseEvent mouseEvent) {
        if(!active2){
            activateButton.setVisible(true);
            activateButton.setDisable(false);
            discardButton.setVisible(true);
            discardButton.setDisable(false);
            backButton.setVisible(true);
            backButton.setDisable(false);
            leader2.setImage(new Image(getClass().getResource("/images/Leaders/" + leader2.toString() + ".png").toExternalForm()));
        }
    }

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
        this.gui=m.getGui();
        Platform.runLater(() -> {
            this.setup();
        });

    }

    private void setup() {
        l1 = mainController.getGame().getPlayerById(gui.getID()).getLeaderDeck().getCards().get(0);
        l2 = mainController.getGame().getPlayerById(gui.getID()).getLeaderDeck().getCards().get(1);
    }
}
