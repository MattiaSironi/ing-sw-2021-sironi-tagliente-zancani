package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.SceneList;
import it.polimi.ingsw.model.LeaderCard;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class FirstDrawController implements GUIController {

    public ImageView coin;
    public ImageView stone;
    public ImageView servant;
    public ImageView shield;
    private GUI gui;
    public ImageView card1;
    public ImageView card2;
    public ImageView card3;
    public ImageView card4;

    public void setup() {
        ArrayList<LeaderCard> leaderCards = gui.getMainController().getGame().getPlayerById(gui.getID()).getLeaderDeck().getCards();
        for (LeaderCard leaderCard : leaderCards) {
            switch(leaderCards.indexOf(leaderCard)){
                case 0 -> card1.setImage(new Image(getClass().getResource("/images/Leaders/" + leaderCard.toString() + ".png").toExternalForm()));
                case 1 -> card2.setImage(new Image(getClass().getResource("/images/Leaders/" + leaderCard.toString() + ".png").toExternalForm()));
                case 2 -> card3.setImage(new Image(getClass().getResource("/images/Leaders/" + leaderCard.toString() + ".png").toExternalForm()));
                case 3 -> card4.setImage(new Image(getClass().getResource("/images/Leaders/" + leaderCard.toString() + ".png").toExternalForm()));
            }
        }
        gui.changeScene(SceneList.FIRSTDRAW);
    }

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    public void coinOpacityUp(MouseEvent mouseEvent) {
        coin.setOpacity(1);
    }

    public void coinOpacityDown(MouseEvent mouseEvent) {
        coin.setOpacity(0.75);
    }

    public void stoneOpacityUp(MouseEvent mouseEvent) {
        stone.setOpacity(1);
    }

    public void stoneOpacityDown(MouseEvent mouseEvent) {
        stone.setOpacity(0.75);
    }

    public void servantOpacityUp(MouseEvent mouseEvent) {
        servant.setOpacity(1);
    }

    public void servantOpacityDown(MouseEvent mouseEvent) {
        servant.setOpacity(0.75);
    }

    public void shieldOpacityUp(MouseEvent mouseEvent) {
        shield.setOpacity(1);
    }

    public void shieldOpacityDown(MouseEvent mouseEvent) {
        shield.setOpacity(0.75);
    }

    public void card1OpacityUp(MouseEvent mouseEvent) {
        card1.setOpacity(1);
    }

    public void card1OpacityDown(MouseEvent mouseEvent) {
        card1.setOpacity(0.75);
    }

    public void card2OpacityUp(MouseEvent mouseEvent) {
        card2.setOpacity(1);
    }

    public void card2OpacityDown(MouseEvent mouseEvent) {
        card2.setOpacity(0.75);
    }

    public void card3OpacityUp(MouseEvent mouseEvent) {
        card3.setOpacity(1);
    }

    public void card3OpacityDown(MouseEvent mouseEvent) {
        card3.setOpacity(0.75);
    }

    public void card4OpacityUp(MouseEvent mouseEvent) {
        card4.setOpacity(1);
    }

    public void card4OpacityDown(MouseEvent mouseEvent) {
        card4.setOpacity(0.75);
    }
}
