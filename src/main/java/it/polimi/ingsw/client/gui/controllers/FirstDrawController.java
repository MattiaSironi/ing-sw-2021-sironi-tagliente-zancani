package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.SceneList;
import it.polimi.ingsw.model.LeaderCard;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class FirstDrawController implements GUIController {

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
}
