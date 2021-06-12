package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.SceneList;
import it.polimi.ingsw.message.ActionMessages.BuyDevCardMessage;
import it.polimi.ingsw.model.Communication;
import it.polimi.ingsw.model.Turn;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class DevCardController implements GUIController{


    public ImageView card7;
    public ImageView card8;
    public ImageView card9;
    public ImageView card4;
    public ImageView card5;
    public ImageView card6;
    public ImageView card1;
    public ImageView card2;
    public ImageView card3;
    public ImageView card10;
    public ImageView card11;
    public ImageView card12;
    public ArrayList<ImageView> matrix;
    public GUI gui;
    public MainController mainController;
    public ImageView chosenCard;
    public ImageView cancelButton;
    public ImageView buyButton;
    public Label uselessLabel;
    public GridPane gridPane;
    public Label message;
    public ImageView backButton;
    private int chosenIndex;
    public Label phase;
    public Label comMessages;

    public void setup(int num){
        this.gui = mainController.getGui();
        this.matrix = new ArrayList<>();
        this.matrix.add(card1);
        this.matrix.add(card2);
        this.matrix.add(card3);
        this.matrix.add(card4);
        this.matrix.add(card5);
        this.matrix.add(card6);
        this.matrix.add(card7);
        this.matrix.add(card8);
        this.matrix.add(card9);
        this.matrix.add(card10);
        this.matrix.add(card11);
        this.matrix.add(card12);
        int i = 0;
        for (ImageView imageView : matrix) {
            imageView.setOnMouseEntered(mouseEvent -> {
                imageView.setEffect(new Glow(0.3));
            });
            imageView.setOnMouseExited(mouseEvent -> {
                imageView.setEffect(new DropShadow(20, Color.BLACK));
            });
            if(mainController.getGame().getBoard().getMatrix().getDevDecks().get(i).getCards().size() != 0) {
                imageView.setImage(new Image(getClass().getResource("/images/Devs/FRONT/" + this.mainController.getGame().getBoard().getMatrix().getDevDecks().get(i).getCards().get(0).toString() + ".png").toExternalForm()));
                imageView.setEffect(new DropShadow(20, Color.BLACK));
                imageView.setOnMouseClicked(mouseEvent -> {
                    chosenCard.setImage(imageView.getImage());
                    chosenIndex = matrix.indexOf(imageView);
                    System.out.println(chosenIndex);
                    activateButtons();
                });
            }
            else{
                imageView.setImage(null);
            }
            i++;
        }
    }


    public void activateButtons(){
        uselessLabel.setVisible(true);
        cancelButton.setVisible(true);
        buyButton.setVisible(true);
        cancelButton.setDisable(false);
        buyButton.setDisable(false);
    }

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
        gui = m.getGui();
    }

    public void cancel(MouseEvent mouseEvent) {
        uselessLabel.setVisible(false);
        chosenCard.setImage(null);
        cancelButton.setVisible(false);
        cancelButton.setDisable(true);
        buyButton.setVisible(false);
        buyButton.setDisable(true);
    }

    public void buyCard(MouseEvent mouseEvent) {
        cancelButton.setMouseTransparent(true);
        buyButton.setMouseTransparent(true);
        mainController.send(new BuyDevCardMessage(chosenIndex, gui.getID(), false, -1));
    }

    public void cancelOpacityUp(MouseEvent mouseEvent) {
        cancelButton.setOpacity(1);
    }

    public void cancelOpacityDown(MouseEvent mouseEvent) {
        cancelButton.setOpacity(0.75);
    }

    public void buyOpacityUp(MouseEvent mouseEvent) {
        buyButton.setOpacity(1);
    }

    public void buyOpacityDown(MouseEvent mouseEvent) {
        buyButton.setOpacity(0.75);
    }

    @Override
    public void print(Turn turn) {
        if (turn.getPlayerPlayingID() != gui.getID()) {

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
        buyButton.setDisable(true);
        buyButton.setMouseTransparent(true);
    }

    @Override
    public void enable() {
        buyButton.setDisable(false);
        buyButton.setMouseTransparent(false);
    }

    public void goBack(MouseEvent mouseEvent) {
        mainController.getGui().changeScene(SceneList.MAINSCENE);
    }
}
