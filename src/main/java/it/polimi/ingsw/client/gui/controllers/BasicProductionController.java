package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.SceneList;
import it.polimi.ingsw.message.ActionMessages.BasicProductionMessage;
import it.polimi.ingsw.model.Communication;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.ShelfWarehouse;
import it.polimi.ingsw.model.Turn;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import java.util.ArrayList;

/**
 * BasicProductionController class handles the basicProductionScene.fxml by performing actions from the GUI
 */
public class BasicProductionController implements GUIController{

    public ImageView dev1;
    public ImageView dev2;
    public ImageView dev3;
    public ImageView coinImage;
    public ImageView stoneImage;
    public ImageView servantImage;
    public ImageView shieldImage;
    public ImageView paid1;
    public ImageView paid2;
    public ImageView result;
    public ImageView shelf1pos1;
    public ImageView shelf2pos1;
    public ImageView shelf2pos2;
    public ImageView shelf3pos1;
    public ImageView shelf3pos2;
    public ImageView shelf3pos3;
    public ImageView coinImage1;
    public ImageView stoneImage1;
    public ImageView servantImage1;
    public ImageView shieldImage1;
    public ImageView shelf4pos1;
    public ImageView shelf4pos2;
    public ImageView shelf5pos1;
    public ImageView shelf5pos2;
    public Label coinNum;
    public Label stoneNum;
    public Label servantNum;
    public Label shieldNum;
    public ImageView back;
    public Label resetChoice;
    public HBox choicesHBox;
    public Label confirm1;
    public Label label1;
    public Label label2;
    public Label endBasic;
    public Label resetChoice2;
    public Label comMessages;
    public Label phase;
    private MainController mainController;
    private ArrayList<ImageView> inputChoices;
    private ArrayList<ImageView> outputChoices;
    private ArrayList<ResourceType> chosenResources;
    private ResourceType newResource;
    
    
    
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
        label2.setVisible(true);
        resetChoice2.setDisable(false);
        label1.setVisible(false);
        confirm1.setDisable(false);
        endBasic.setDisable(true);
        choicesHBox.setDisable(true);
        choicesHBox.setVisible(false);
        confirm1.setDisable(true);
        chosenResources = new ArrayList<>();
        showShelves();
        showDevCard();
        showStrongbox();
        resetChoice.setDisable(true);
        paid1.setImage(null);
        paid2.setImage(null);
        result.setImage(null);
        newResource = null;
        chosenResources = new ArrayList<>();
    }

    /**
     * method showStrongbox handles the visualization of the strongbox
     */
    public void showStrongbox(){
        coinNum.setText("" + mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getStrongbox().getResCount(ResourceType.COIN));
        stoneNum.setText("" + mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getStrongbox().getResCount(ResourceType.STONE));
        servantNum.setText("" + mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getStrongbox().getResCount(ResourceType.SERVANT));
        shieldNum.setText("" + mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getStrongbox().getResCount(ResourceType.SHIELD));
    }

    /**
     * Method showDevCard handles the visualization of the slot of development card of the personal board
     */

    public void showDevCard(){
        if (mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getCardSlot().get(0).getCards().size() != 0) {
            dev1.setImage(new Image(getClass().getResource("/images/Devs/FRONT/" +
                    mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getCardSlot().get(0).getCards().get(0) +
                    ".png").toExternalForm()));

        }
        if (mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getCardSlot().get(1).getCards().size() != 0) {
            dev2.setImage(new Image(getClass().getResource("/images/Devs/FRONT/" +
                    mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getCardSlot().get(1).getCards().get(0) +
                    ".png").toExternalForm()));
        }
        if (mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getCardSlot().get(2).getCards().size() != 0) {
            dev3.setImage(new Image(getClass().getResource("/images/Devs/FRONT/" +
                    mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getCardSlot().get(2).getCards().get(0) +
                    ".png").toExternalForm()));
        }
    }

    /**
     * method showShelves handles the visualization of the warehouse
     */
    public void showShelves() {
        ShelfWarehouse myShelves = mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getWarehouse();
        ArrayList<ImageView> shelf2 = new ArrayList<>();
        ArrayList<ImageView> shelf3 = new ArrayList<>();
        ArrayList<ImageView> shelf4 = new ArrayList<>();
        ArrayList<ImageView> shelf5 = new ArrayList<>();
        int max;
        ResourceType r;
        shelf2.add(shelf2pos1);
        shelf2.add(shelf2pos2);
        shelf3.add(shelf3pos1);
        shelf3.add(shelf3pos2);
        shelf3.add(shelf3pos3);
        shelf4.add(shelf4pos1);
        shelf4.add(shelf4pos2);
        shelf5.add(shelf5pos1);
        shelf5.add(shelf5pos2);

        if (myShelves.getShelves().get(0).getResType() != ResourceType.EMPTY)
            shelf1pos1.setImage(new Image(getClass().getResource("/images/PunchBoard/" + myShelves.getShelves().get(0).getResType().toString().toLowerCase() + ".png").toExternalForm()));
        else shelf1pos1.setImage(null);


        r = myShelves.getShelves().get(1).getResType();
        max = myShelves.getShelves().get(1).getCount();
        for (ImageView iv : shelf2) {
            if (shelf2.indexOf(iv) <= max - 1)
                iv.setImage(new Image(getClass().getResource("/images/PunchBoard/" + r.toString().toLowerCase() + ".png").toExternalForm()));
            else iv.setImage(null);
        }


        r = myShelves.getShelves().get(2).getResType();
        max = myShelves.getShelves().get(2).getCount();
        for (ImageView iv : shelf3) {
            if (shelf3.indexOf(iv) <= max - 1)
                iv.setImage(new Image(getClass().getResource("/images/PunchBoard/" + r.toString().toLowerCase() + ".png").toExternalForm()));
            else iv.setImage(null);
        }

        if (myShelves.getShelves().get(3).getResType() != null) {
            r = myShelves.getShelves().get(3).getResType();
            max = myShelves.getShelves().get(3).getCount();
            for (ImageView iv : shelf4) {
                iv.setImage(new Image(getClass().getResource("/images/PunchBoard/" + r.toString().toLowerCase() + ".png").toExternalForm()));
                if (!(shelf4.indexOf(iv) <= max - 1)) iv.setOpacity(0.5);
                else iv.setOpacity(1.0);
            }
        }
        else for (ImageView iv : shelf4) iv.setImage(null);

        if (myShelves.getShelves().get(4).getResType() != null) {
            r = myShelves.getShelves().get(4).getResType();
            max = myShelves.getShelves().get(4).getCount();
            for (ImageView iv : shelf5) {
                iv.setImage(new Image(getClass().getResource("/images/PunchBoard/" + r.toString().toLowerCase() + ".png").toExternalForm()));
                if (!(shelf5.indexOf(iv) <= max - 1)) iv.setOpacity(0.5);
                else iv.setOpacity(1.0);
            }
        }
        else for (ImageView iv : shelf5) iv.setImage(null);
    }

    /**
     * Method back switches to the main scene of the game
     * @param mouseEvent of type ActionEvent - the event received.
     */
    public void goBack(MouseEvent mouseEvent) {
        mainController.getGui().changeScene(SceneList.GENERALPRODSCENE);
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
     * Method chooseCoin handles the choices for the basic production
     * @param mouseEvent of type ActionEvent - the event received.
     */
    public void chooseCoin(MouseEvent mouseEvent) {
        if(chosenResources.size() == 0) {
            chosenResources.add(ResourceType.COIN);
            paid1.setImage(new Image(getClass().getResource("/images/PunchBoard/coin.png").toExternalForm()));
        }
        else if (chosenResources.size() == 1){
            chosenResources.add(ResourceType.COIN);
            paid2.setImage(new Image(getClass().getResource("/images/PunchBoard/coin.png").toExternalForm()));
            confirm1.setDisable(false);
        }
    }

    /**
     * Method chooseStone handles the choices for the basic production
     * @param mouseEvent of type ActionEvent - the event received.
     */
    public void chooseStone(MouseEvent mouseEvent) {
        if(chosenResources.size() == 0) {
            chosenResources.add(ResourceType.STONE);
            paid1.setImage(new Image(getClass().getResource("/images/PunchBoard/stone.png").toExternalForm()));
        }
        else if (chosenResources.size() == 1){
            chosenResources.add(ResourceType.STONE);
            paid2.setImage(new Image(getClass().getResource("/images/PunchBoard/stone.png").toExternalForm()));
            confirm1.setDisable(false);
        }
    }

    /**
     * Method chooseServant handles the choices for the basic production
     * @param mouseEvent of type ActionEvent - the event received.
     */
    public void chooseServant(MouseEvent mouseEvent) {
        if(chosenResources.size() == 0) {
            chosenResources.add(ResourceType.SERVANT);
            paid1.setImage(new Image(getClass().getResource("/images/PunchBoard/servant.png").toExternalForm()));
        }
        else if (chosenResources.size() == 1){
            chosenResources.add(ResourceType.SERVANT);
            paid2.setImage(new Image(getClass().getResource("/images/PunchBoard/servant.png").toExternalForm()));
            confirm1.setDisable(false);
        }
    }

    /**
     * Method chooseShield handles the choices for the basic production
     * @param mouseEvent of type ActionEvent - the event received.
     */
    public void chooseShield(MouseEvent mouseEvent) {
        if(chosenResources.size() == 0) {
            chosenResources.add(ResourceType.SHIELD);
            paid1.setImage(new Image(getClass().getResource("/images/PunchBoard/shield.png").toExternalForm()));
        }
        else if (chosenResources.size() == 1){
            chosenResources.add(ResourceType.SHIELD);
            paid2.setImage(new Image(getClass().getResource("/images/PunchBoard/shield.png").toExternalForm()));
            confirm1.setDisable(false);
        }
    }

    /**
     * Method buyCoin handles the choices for the basic production
     * @param mouseEvent of type ActionEvent - the event received.
     */
    public void buyCoin(MouseEvent mouseEvent) {
        newResource = ResourceType.COIN;
        result.setImage(new Image(getClass().getResource("/images/PunchBoard/coin.png").toExternalForm()));
        endBasic.setDisable(false);
    }

    /**
     * Method buyStone handles the choices for the basic production
     * @param mouseEvent of type ActionEvent - the event received.
     */
    public void buyStone(MouseEvent mouseEvent) {
        newResource = ResourceType.STONE;
        result.setImage(new Image(getClass().getResource("/images/PunchBoard/stone.png").toExternalForm()));
        endBasic.setDisable(false);
    }

    /**
     * Method buyServant handles the choices for the basic production
     * @param mouseEvent of type ActionEvent - the event received.
     */
    public void buyServant(MouseEvent mouseEvent) {
        newResource = ResourceType.SERVANT;
        result.setImage(new Image(getClass().getResource("/images/PunchBoard/servant.png").toExternalForm()));
        endBasic.setDisable(false);
    }

    /**
     * Method buyShield handles the choices for the basic production
     * @param mouseEvent of type ActionEvent - the event received.
     */
    public void buyShield(MouseEvent mouseEvent) {
        newResource = ResourceType.SHIELD;
        result.setImage(new Image(getClass().getResource("/images/PunchBoard/shield.png").toExternalForm()));
        endBasic.setDisable(false);
    }

    /**
     * Method resetChoice resets the choices of the player
     * @param mouseEvent of type ActionEvent - the event received.
     */
    public void resetChoice(MouseEvent mouseEvent) {
        result.setImage(null);
        newResource = null;
        endBasic.setDisable(true);
    }

    /**
     * Method enableHBox handles some Controls in the scene
     */
    public void enableHBox(){
        label2.setVisible(false);
        label1.setVisible(true);
        choicesHBox.setDisable(false);
        choicesHBox.setVisible(true);
        resetChoice.setDisable(false);
        confirm1.setDisable(true);
        resetChoice2.setDisable(true);
        endBasic.setDisable(false);
    }

    /**
     * Method confirmChoice sends the message for the basic production phase
     */
    public void confirmChoice(MouseEvent mouseEvent) {
        mainController.send(new BasicProductionMessage(chosenResources.get(0), chosenResources.get(1), null, mainController.getGui().getID(), false));
        back.setDisable(true);
    }

    /**
     * Method sendResources sends the message for the basic production phase
     */
    public void sendResources(MouseEvent mouseEvent) {

        mainController.send(new BasicProductionMessage(null, null, newResource, mainController.getGui().getID(), false));
        back.setDisable(false);

    }

    /**
     *Method resetSelection resets the selection of th player
     * @param mouseEvent of type ActionEvent - the event received.
     */
    public void resetSelection(MouseEvent mouseEvent) {
        chosenResources = new ArrayList<>();
        paid1.setImage(null);
        paid2.setImage(null);
        confirm1.setDisable(true);
    }
}
