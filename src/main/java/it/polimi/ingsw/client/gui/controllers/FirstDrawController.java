package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.SceneList;
import it.polimi.ingsw.message.ActionMessages.PlaceResourceMessage;
import it.polimi.ingsw.message.ActionMessages.PlayLeaderMessage;
import it.polimi.ingsw.model.Communication;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.Turn;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;

/**
 * FirstDrawController class handles the firstDrawScene.fxml by performing action from the GUI
 */

public class FirstDrawController implements GUIController {

    public ImageView coin;
    public ImageView stone;
    public ImageView servant;
    public ImageView shield;
    public Label chooseResLabel;
    public ImageView nextButton;
    public ImageView resetButton;
    public ImageView res1;
    public ImageView res2;
    public Label yourRes;
    private GUI gui;
    public ImageView card1;
    public ImageView card2;
    public ImageView card3;
    public ImageView card4;
    private MainController mainController;
    private ArrayList<ResourceType> resources;
    private ArrayList<LeaderCard> chosenLeaders;

    /**
     * Method setup prepares the scene when opened
     * @param num
     */
    public void setup(int num) {
        this.resources = new ArrayList<>();
        this.chosenLeaders = new ArrayList<>();
        gui = mainController.getGui();
        coin.setImage(new Image(getClass().getResource("/images/PunchBoard/coin.png").toExternalForm()));
        stone.setImage(new Image(getClass().getResource("/images/PunchBoard/stone.png").toExternalForm()));
        servant.setImage(new Image(getClass().getResource("/images/PunchBoard/servant.png").toExternalForm()));
        shield.setImage(new Image(getClass().getResource("/images/PunchBoard/shield.png").toExternalForm()));
        ArrayList<LeaderCard> leaderCards = gui.getMainController().getGame().getPlayerById(gui.getID()).getLeaderDeck().getCards();
        for (LeaderCard leaderCard : leaderCards) {
            switch (leaderCards.indexOf(leaderCard)) {
                case 0 -> card1.setImage(new Image(getClass().getResource("/images/Leaders/" + leaderCard.toString() + ".png").toExternalForm()));
                case 1 -> card2.setImage(new Image(getClass().getResource("/images/Leaders/" + leaderCard.toString() + ".png").toExternalForm()));
                case 2 -> card3.setImage(new Image(getClass().getResource("/images/Leaders/" + leaderCard.toString() + ".png").toExternalForm()));
                case 3 -> card4.setImage(new Image(getClass().getResource("/images/Leaders/" + leaderCard.toString() + ".png").toExternalForm()));
            }
        }
    }

    /**
     * Method prepareScene handles some controls in the scene
     */
    public void prepareScene() {
        if (mainController.getGame().getPlayerById(gui.getID()).getStartResCount() == 0) {
            coin.setMouseTransparent(true);
            coin.setVisible(false);
            stone.setMouseTransparent(true);
            stone.setVisible(false);
            servant.setMouseTransparent(true);
            servant.setVisible(false);
            shield.setMouseTransparent(true);
            shield.setVisible(false);
            yourRes.setVisible(false);
            chooseResLabel.setOpacity(0.75);
            System.out.println("gigi");
        }
    }

    /**
     * method setResCountLabel handles some controls in the scene
     * @param count
     */

    public void setResCountLabel(int count) {
        switch (count) {
            case 0 -> {
                chooseResLabel.setText("");
                prepareScene();
                deactivateResourcesButton();
            }
            case 1 -> chooseResLabel.setText("Choose " + count + " starting Resource");
            case 2 -> chooseResLabel.setText("Choose " + count + " starting Resources");
            default -> System.out.println("qui");
        }
        System.out.println(count);
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


    public void deactivateResourcesButton() {
        coin.setMouseTransparent(true);
        stone.setMouseTransparent(true);
        servant.setMouseTransparent(true);
        shield.setMouseTransparent(true);
    }

    public void activateResourcesButton() {
        coin.setMouseTransparent(false);
        stone.setMouseTransparent(false);
        servant.setMouseTransparent(false);
        shield.setMouseTransparent(false);

    }

    /**
     * method addCoin handles the choice of the initial resource(s)
     * @param mouseEvent    of type ActionEvent - the event received.
     */

    public void addCoin(MouseEvent mouseEvent) {
        if (resources.size() == 0)
            res1.setImage(new Image(getClass().getResource("/images/PunchBoard/coin.png").toExternalForm()));
        else
            res2.setImage(new Image(getClass().getResource("/images/PunchBoard/coin.png").toExternalForm()));
        this.resources.add(ResourceType.COIN);
        System.out.println("coin");
        if (resources.size() == mainController.getGame().getPlayerById(gui.getID()).getStartResCount()) {
            deactivateResourcesButton();
        }
    }

    /**
     * method addStone handles the choice of the initial resource(s)
     * @param mouseEvent    of type ActionEvent - the event received.
     */

    public void addStone(MouseEvent mouseEvent) {
        if (resources.size() == 0)
            res1.setImage(new Image(getClass().getResource("/images/PunchBoard/stone.png").toExternalForm()));
        else
            res2.setImage(new Image(getClass().getResource("/images/PunchBoard/stone.png").toExternalForm()));
        this.resources.add(ResourceType.STONE);
        System.out.println("stone");
        if (resources.size() == mainController.getGame().getPlayerById(gui.getID()).getStartResCount()) {
            deactivateResourcesButton();
        }
    }

    /**
     * method addServant handles the choice of the initial resource(s)
     * @param mouseEvent    of type ActionEvent - the event received.
     */

    public void addServant(MouseEvent mouseEvent) {
        if (resources.size() == 0)
            res1.setImage(new Image(getClass().getResource("/images/PunchBoard/servant.png").toExternalForm()));
        else
            res2.setImage(new Image(getClass().getResource("/images/PunchBoard/servant.png").toExternalForm()));
        this.resources.add(ResourceType.SERVANT);
        System.out.println("servant");
        if (resources.size() == mainController.getGame().getPlayerById(gui.getID()).getStartResCount()) {
            deactivateResourcesButton();
        }
    }

    /**
     * method assShield handles the choice of the initial resource(s)
     * @param mouseEvent    of type ActionEvent - the event received.
     */

    public void addShield(MouseEvent mouseEvent) {
        if (resources.size() == 0)
            res1.setImage(new Image(getClass().getResource("/images/PunchBoard/shield.png").toExternalForm()));
        else
            res2.setImage(new Image(getClass().getResource("/images/PunchBoard/shield.png").toExternalForm()));
        this.resources.add(ResourceType.SHIELD);
        System.out.println("shield");
        if (resources.size() == mainController.getGame().getPlayerById(gui.getID()).getStartResCount()) {
            deactivateResourcesButton();
        }
    }

    /**
     * Method deactivateCards handles some controls in the scene
     */
    public void deactivateCards() {
        card1.setMouseTransparent(true);
        card2.setMouseTransparent(true);
        card3.setMouseTransparent(true);
        card4.setMouseTransparent(true);
    }

    /**
     * Method deactivateCards handles some controls in the scene
     */
    public void activateCards() {
        card1.setMouseTransparent(false);
        card2.setMouseTransparent(false);
        card3.setMouseTransparent(false);
        card4.setMouseTransparent(false);
        card1.setOpacity(1);
        card2.setOpacity(1);
        card3.setOpacity(1);
        card4.setOpacity(1);
    }

    /**
     * method addCard1 handles the choice of the initial resource(s)
     * @param mouseEvent    of type ActionEvent - the event received.
     */
    public void addCard1(MouseEvent mouseEvent) {
        chosenLeaders.add(mainController.getGame().getPlayerById(gui.getID()).getLeaderDeck().getCards().get(0));
        card1.setOpacity(0.75);
        card1.setMouseTransparent(true);
        if (chosenLeaders.size() == 2)
            deactivateCards();
        System.out.println("added");
    }

    /**
     * method addCard2 handles the choice of the initial resource(s)
     * @param mouseEvent    of type ActionEvent - the event received.
     */

    public void addCard2(MouseEvent mouseEvent) {
        chosenLeaders.add(mainController.getGame().getPlayerById(gui.getID()).getLeaderDeck().getCards().get(1));
        card2.setOpacity(0.75);
        card2.setMouseTransparent(true);
        if (chosenLeaders.size() == 2)
            deactivateCards();
        System.out.println("added");
    }

    /**
     * method addCard3 handles the choice of the initial resource(s)
     * @param mouseEvent    of type ActionEvent - the event received.
     */

    public void addCard3(MouseEvent mouseEvent) {
        chosenLeaders.add(mainController.getGame().getPlayerById(gui.getID()).getLeaderDeck().getCards().get(2));
        card3.setOpacity(0.75);
        card3.setMouseTransparent(true);
        if (chosenLeaders.size() == 2)
            deactivateCards();
        System.out.println("added");
    }

    /**
     * method addCard4 handles the choice of the initial resource(s)
     * @param mouseEvent    of type ActionEvent - the event received.
     */
    public void addCard4(MouseEvent mouseEvent) {
        chosenLeaders.add(mainController.getGame().getPlayerById(gui.getID()).getLeaderDeck().getCards().get(3));
        card4.setOpacity(0.75);
        card4.setMouseTransparent(true);
        if (chosenLeaders.size() == 2)
            deactivateCards();
        System.out.println("added");
    }

    /**
     * Method next sends the resources chosen by the player and the index of the card that he want to discard
     * @param mouseEvent of type ActionEvent - the event received.
     */

    public void next(MouseEvent mouseEvent) {
        nextButton.setMouseTransparent(true);
        resetButton.setMouseTransparent(true);
        res1.setImage(null);
        res2.setImage(null);
        if (resources.size() == mainController.getGame().getPlayerById(gui.getID()).getStartResCount() && chosenLeaders.size() == 2) {
            if (mainController.getGame().getPlayerById(gui.getID()).getStartResCount() == 2) {
                if (resources.get(0).equals(resources.get(1))) {
                    mainController.send(new PlaceResourceMessage(resources.get(0), 2, gui.getID(), true, false));
                } else {
                    mainController.send(new PlaceResourceMessage(resources.get(0), 1, gui.getID(), true, false));
                }
                mainController.send(new PlaceResourceMessage(resources.get(1), 2, gui.getID(), true, false));
            } else if(mainController.getGame().getPlayerById(gui.getID()).getStartResCount() != 0) {
                mainController.send(new PlaceResourceMessage(resources.get(0), 0, gui.getID(), true, false));
            }
            for(LeaderCard l : chosenLeaders){
                mainController.send(new PlayLeaderMessage(gui.getID(), -1, false, l, true));
            }
        }
        else{
            reset();
        }
    }

    public void nextButtonOpacityUp(MouseEvent mouseEvent) {
        nextButton.setOpacity(1);
    }

    public void nextButtonOpacityDown(MouseEvent mouseEvent) {
        nextButton.setOpacity(0.5);
    }

    public void resetChoices(MouseEvent mouseEvent) {
        reset();
    }

    /**
     * method reset resets the choices of the player
     */
    public void reset(){
        this.resources = new ArrayList<>();
        this.chosenLeaders = new ArrayList<>();
        res1.setImage(null);
        res2.setImage(null);
        activateResourcesButton();
        activateCards();
        nextButton.setMouseTransparent(false);
        resetButton.setMouseTransparent(false);
    }

    public void resetButtonOpacityUp(MouseEvent mouseEvent) {
        resetButton.setOpacity(1);
    }

    public void resetButtonOpacityDown(MouseEvent mouseEvent) {
        resetButton.setOpacity(0.5);
    }

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }
}
