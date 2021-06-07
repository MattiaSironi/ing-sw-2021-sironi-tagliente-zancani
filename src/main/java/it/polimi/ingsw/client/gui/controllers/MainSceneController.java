package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.SceneList;
import it.polimi.ingsw.message.ActionMessages.BuyDevCardMessage;
import it.polimi.ingsw.message.ActionMessages.PlayLeaderMessage;
import it.polimi.ingsw.model.*;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.sql.SQLOutput;

public class MainSceneController implements  GUIController {
    public ImageView activateButton;
    public ImageView discardButton;
    public ImageView backButton;
    public ImageView devMarket;
    public Label phase;
    public Label comMessages;
    ImageView l1;
    ImageView l2;
    boolean active1 = false;
    boolean active2 = false;
    public ImageView leader1;
    public ImageView leader2;
    private GUI gui;
    int choosing = 0;
    boolean stop = false;


    private MainController mainController;


    public void Leader1OpacityUp(MouseEvent mouseEvent) {
        if(!active1&&choosing==0)
             leader1.setEffect(new Glow(0.3));
    }
    public void Leader1OpacityDown(MouseEvent mouseEvent) { leader1.setEffect(new DropShadow(20, Color.BLACK));}
    public void Leader2OpacityUp(MouseEvent mouseEvent) {
        if(!active2&&choosing==0)
             leader2.setEffect(new Glow(0.3));
    }
    public void Leader2OpacityDown(MouseEvent mouseEvent) { leader2.setEffect(new DropShadow(20, Color.BLACK));}
    public void ActivateOpacityUp(MouseEvent mouseEvent) {activateButton.setOpacity(1);}
    public void ActivateOpacityDown(MouseEvent mouseEvent) { activateButton.setOpacity(0.75);}
    public void DiscardOpacityUp(MouseEvent mouseEvent) { discardButton.setOpacity(1);}
    public void DiscardOpacityDown(MouseEvent mouseEvent) { discardButton.setOpacity(0.75);}
    public void BackOpacityUp(MouseEvent mouseEvent) { backButton.setOpacity(1);}
    public void BackOpacityDown(MouseEvent mouseEvent) { backButton.setOpacity(0.75);}


    public void Leader1Clicked(MouseEvent mouseEvent) {
        if(!active1&&choosing==0) {
            activateButton.setVisible(true);
            activateButton.setDisable(false);
            discardButton.setVisible(true);
            discardButton.setDisable(false);
            backButton.setVisible(true);
            backButton.setDisable(false);
            leader1.setImage(new Image(getClass().getResource("/images/Leaders/"+this.mainController.getGame().getPlayerById(gui.getID()).getLeaderDeck().getCards().get(0).toString()+".png").toExternalForm()));
            choosing=1;
        }
    }

    public void Leader2Clicked(MouseEvent mouseEvent) {
        if(!active2 && choosing==0){
            activateButton.setVisible(true);
            activateButton.setDisable(false);
            discardButton.setVisible(true);
            discardButton.setDisable(false);
            backButton.setVisible(true);
            backButton.setDisable(false);
            leader2.setImage(new Image(getClass().getResource("/images/Leaders/"+this.mainController.getGame().getPlayerById(gui.getID()).getLeaderDeck().getCards().get(1).toString()+".png").toExternalForm()));
            choosing = 2;
        }
    }

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
        this.gui=m.getGui();

    }

    public void setup() {
        System.out.println(mainController.getGui().getID());
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

    public void buyDevCard(MouseEvent mouseEvent) {
        gui.changeScene(SceneList.BUYDEVCARD);
    }

    public void goToMarket(MouseEvent mouseEvent) {
        gui.changeScene(SceneList.MARKET);
    }

    public void Back(MouseEvent mouseEvent) {
        activateButton.setVisible(false);
        activateButton.setDisable(true);
        discardButton.setVisible(false);
        discardButton.setDisable(true);
        backButton.setVisible(false);
        backButton.setDisable(true);
        if(!active1)
             leader1.setImage(new Image(getClass().getResource("/images/Leaders/BACK.png").toExternalForm()));
        if(!active2)
             leader2.setImage(new Image(getClass().getResource("/images/Leaders/BACK.png").toExternalForm()));
        choosing=0;
    }

    public void activate(MouseEvent mouseEvent) {
        mainController.send(new PlayLeaderMessage(gui.getID(), choosing, true, mainController.getGame().getPlayerById(gui.getID()).getLeaderDeck().getCards().get(choosing - 1), false));

        if(!stop) {
            if (choosing == 1) {
                active1 = true;
                leader1.setImage(new Image(getClass().getResource("/images/Leaders/" + this.mainController.getGame().getPlayerById(gui.getID()).getLeaderDeck().getCards().get(0).toString() + ".png").toExternalForm()));
            } else {
                active2 = true;
                if(!active1) {
                    leader2.setImage(new Image(getClass().getResource("/images/Leaders/" + this.mainController.getGame().getPlayerById(gui.getID()).getLeaderDeck().getCards().get(1).toString() + ".png").toExternalForm()));
                }
                else{
                    leader2.setImage(new Image(getClass().getResource("/images/Leaders/" + this.mainController.getGame().getPlayerById(gui.getID()).getLeaderDeck().getCards().get(0).toString() + ".png").toExternalForm()));
                }
            }

        }
        stop = false;
        activateButton.setVisible(false);
        activateButton.setDisable(true);
        discardButton.setVisible(false);
        discardButton.setDisable(true);
        backButton.setVisible(false);
        backButton.setDisable(true);
        choosing=0;
    }

    public void discard(MouseEvent mouseEvent) {
        mainController.send(new PlayLeaderMessage(gui.getID(), choosing, false, this.mainController.getGame().getPlayerById(gui.getID()).getLeaderDeck().getCards().get(choosing - 1), false));
        if(choosing==1){
            active1 = true;
            leader1.setImage(new Image(getClass().getResource("/images/Leaders/BACK.png").toExternalForm()));
            leader1.setDisable(true);
            leader1.setOpacity(0.5);
        }
        else{
            active2 = true;
            leader2.setImage(new Image(getClass().getResource("/images/Leaders/BACK.png").toExternalForm()));
            leader2.setDisable(true);
            leader2.setOpacity(0.5);
        }
        activateButton.setVisible(false);
        activateButton.setDisable(true);
        discardButton.setVisible(false);
        discardButton.setDisable(true);
        backButton.setVisible(false);
        backButton.setDisable(true);
        choosing=0;
    }



}
