package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.SceneList;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.ResourceType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class FirstDrawController implements GUIController {

    public ImageView coin;
    public ImageView stone;
    public ImageView servant;
    public ImageView shield;
    public Label chooseResLabel;
    public ImageView nextButton;
    public ImageView resetButton;
    private GUI gui;
    public ImageView card1;
    public ImageView card2;
    public ImageView card3;
    public ImageView card4;
    private MainController mainController;
    private ArrayList<ResourceType> resources;
    private ArrayList<Integer> index;

    public void setup(MainController mainController) {
        this.resources = new ArrayList<>();
        this.index = new ArrayList<>();
        this.mainController = mainController;
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


    public void prepareScene(){
        if(mainController.getGame().getPlayerById(gui.getID()).getStartResCount() == 0){
            coin.setMouseTransparent(true);
            stone.setMouseTransparent(true);
            servant.setMouseTransparent(true);
            shield.setMouseTransparent(true);
            chooseResLabel.setOpacity(0.75);

        }
    }

    public void setResCountLabel(int count) {
        switch (count) {
            case 0 -> chooseResLabel.setText("");
            case 1 -> chooseResLabel.setText("Choose " + count + " starting Resource");
            case 2 -> chooseResLabel.setText("Choose " + count + " starting Resources");
            default -> System.out.println("qui");
        }
        prepareScene();
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

    public void deactivateResourcesButton(){
        coin.setMouseTransparent(true);
        stone.setMouseTransparent(true);
        servant.setMouseTransparent(true);
        shield.setMouseTransparent(true);
    }
    public void addCoin(MouseEvent mouseEvent) {
        this.resources.add(ResourceType.COIN);
        if(resources.size() < mainController.getGame().getPlayerById(gui.getID()).getStartResCount()){
            deactivateResourcesButton();
        }
    }

    public void addStone(MouseEvent mouseEvent) {
        this.resources.add(ResourceType.STONE);
        if(resources.size() < mainController.getGame().getPlayerById(gui.getID()).getStartResCount()){
            deactivateResourcesButton();
        }
    }

    public void addServant(MouseEvent mouseEvent) {
        this.resources.add(ResourceType.SERVANT);
        if(resources.size() < mainController.getGame().getPlayerById(gui.getID()).getStartResCount()){
            deactivateResourcesButton();
        }
    }

    public void addShield(MouseEvent mouseEvent) {
        this.resources.add(ResourceType.SHIELD);
        if(resources.size() < mainController.getGame().getPlayerById(gui.getID()).getStartResCount()){
            deactivateResourcesButton();
        }
    }

    public void deactivateCards(){
        card1.setMouseTransparent(true);
        card2.setMouseTransparent(true);
        card3.setMouseTransparent(true);
        card4.setMouseTransparent(true);
    }

    public void addCard1(MouseEvent mouseEvent) {
        index.add(1);
        card1.setMouseTransparent(true);
        if(index.size() == 2)
            deactivateCards();
    }

    public void addCard2(MouseEvent mouseEvent) {
        index.add(2);
        card2.setMouseTransparent(true);
    }

    public void addCard3(MouseEvent mouseEvent) {
        index.add(3);
        card3.setMouseTransparent(true);
    }

    public void addCard4(MouseEvent mouseEvent) {
        index.add(4);
        card4.setMouseTransparent(true);
    }

    public void next(MouseEvent mouseEvent) {
    }

    public void nextButtonOpacityUp(MouseEvent mouseEvent) {
        nextButton.setOpacity(1);
    }

    public void nextButtonOpacityDown(MouseEvent mouseEvent) {
        nextButton.setOpacity(0.5);
    }

    public void resetChoices(MouseEvent mouseEvent) {
        this.resources = new ArrayList<>();
        this.index = new ArrayList<>();


    }

    public void resetButtonOpacityUp(MouseEvent mouseEvent) {
        resetButton.setOpacity(1);
    }

    public void resetButtonOpacityDown(MouseEvent mouseEvent) {
        resetButton.setOpacity(0.5);
    }
}
