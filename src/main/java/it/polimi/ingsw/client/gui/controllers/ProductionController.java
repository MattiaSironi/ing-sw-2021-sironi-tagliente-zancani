package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.SceneList;
import it.polimi.ingsw.message.ActionMessages.ProductionMessage;
import it.polimi.ingsw.model.Communication;
import it.polimi.ingsw.model.Turn;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ProductionController implements GUIController{
    public Button basicProduction;
    public Button devProduction;
    public Button leaderProduction;
    public ImageView back;
    public Label coinNum;
    public Label stoneNum;
    public Label servantNum;
    public Label shieldNum;
    public Label end;
    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    @Override
    public void setup(int num) {
        coinNum.setText("" + mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getStrongbox().getEarnedCoin());
        stoneNum.setText("" + mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getStrongbox().getEarnedStone());
        servantNum.setText("" + mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getStrongbox().getEarnedServant());
        shieldNum.setText("" + mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getStrongbox().getEarnedShield());
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

    public void useBasicProduction(ActionEvent actionEvent) {
        mainController.getGui().changeScene(SceneList.BASICSCENE);
    }

    public void useDevProduction(ActionEvent actionEvent) {
    }

    public void useLeaderProduction(ActionEvent actionEvent) {
    }

    public void goBack(MouseEvent mouseEvent) {
        mainController.getGui().changeScene(SceneList.MAINSCENE);
    }

    public void endProductionPhase(MouseEvent mouseEvent) {
        mainController.send(new ProductionMessage(mainController.getGui().getID(), true, -1, false));
    }
}
