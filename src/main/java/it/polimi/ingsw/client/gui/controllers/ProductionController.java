package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.SceneList;
import it.polimi.ingsw.message.ActionMessages.LeaderProductionMessage;
import it.polimi.ingsw.message.ActionMessages.ProductionMessage;
import it.polimi.ingsw.model.Communication;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.ShelfWarehouse;
import it.polimi.ingsw.model.Turn;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class ProductionController implements GUIController{
    public Button basicProduction;
    public ImageView back;
    public Label coinNum;
    public Label stoneNum;
    public Label servantNum;
    public Label shieldNum;
    public Label end;
    public Label comMessages;
    public Label phase;
    public ImageView shelf1pos1;
    public ImageView shelf2pos1;
    public ImageView shelf2pos2;
    public ImageView shelf3pos1;
    public ImageView shelf3pos2;
    public ImageView shelf3pos3;
    public ImageView shelf4pos1;
    public ImageView shelf4pos2;
    public ImageView shelf5pos1;
    public ImageView shelf5pos2;
    public ImageView dev1;
    public ImageView dev2;
    public ImageView dev3;
    public ImageView leader1;
    public ImageView leader2;
    public Label coinStrong;
    public Label stoneStrong;
    public Label servantStrong;
    public Label shieldStrong;
    public Button dev2Prod;
    public Button dev3Prod;
    public Button dev1Prod;
    public Button secondLeaderProd;
    public Button firstLeaderProd;
    public HBox hBoxRes;
    public ImageView sendCoin;
    public ImageView sendStone;
    public ImageView sendServant;
    public ImageView sendShield;
    private MainController mainController;
    private boolean enableEndAction;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    @Override
    public void setup(int num) {
        hBoxRes.setVisible(false);
        hBoxRes.setDisable(true);
        showDevCards();
        showWarehouse();
        showLeaders();
        showStrongbox();
        if(enableEndAction) {
            end.setDisable(false);
            end.setMouseTransparent(false);
        }
        else {
            end.setDisable(true);
            end.setMouseTransparent(true);
        }

        coinNum.setText("" + mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getStrongbox().getEarnedCoin());
        stoneNum.setText("" + mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getStrongbox().getEarnedStone());
        servantNum.setText("" + mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getStrongbox().getEarnedServant());
        shieldNum.setText("" + mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getStrongbox().getEarnedShield());
    }

    public void showStrongbox(){
        coinStrong.setText("" + mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getStrongbox().getResCount(ResourceType.COIN));
        stoneStrong.setText("" + mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getStrongbox().getResCount(ResourceType.STONE));
        servantStrong.setText("" + mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getStrongbox().getResCount(ResourceType.SERVANT));
        shieldStrong.setText("" + mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getStrongbox().getResCount(ResourceType.SHIELD));
    }

    private void showLeaders() {
        if(mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getActiveLeader().getCards().size() != 0) {
            leader1.setImage(new Image(getClass().getResource("/images/Leaders/" + mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getActiveLeader().getCards().get(0).toString() + ".png").toExternalForm()));
            if (mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getActiveLeader().getCards().size() == 2) {
                leader2.setImage(new Image(getClass().getResource("/images/Leaders/" + mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getActiveLeader().getCards().get(1).toString() + ".png").toExternalForm()));
            }
        }
        else{
            leader1.setImage(null);
            leader2.setImage(null);
        }

    }

    private void showWarehouse() {
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
        }else for (ImageView iv : shelf5) iv.setImage(null);
    }

    private void showDevCards() {
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

    public void useBasicProduction(ActionEvent actionEvent) {
        mainController.getGui().changeScene(SceneList.BASICSCENE);
    }

    public void goBack(MouseEvent mouseEvent) {
        mainController.getGui().changeScene(SceneList.MAINSCENE);
    }

    public void endProductionPhase(MouseEvent mouseEvent) {
        back.setDisable(false);
        back.setMouseTransparent(false);
        end.setDisable(true);
        end.setMouseTransparent(true);
        enableEndAction = false;
        mainController.send(new ProductionMessage(mainController.getGui().getID(), true, -1, false));
    }

    public void disableBackButton() {
        back.setDisable(true);
        back.setMouseTransparent(true);
        enableEndAction = true;
    }

    public void useFirstDevProduction(ActionEvent actionEvent) {
        mainController.send(new ProductionMessage(mainController.getGui().getID(), false, 0, false));
    }

    public void useSecondDevProduction(ActionEvent actionEvent) {
        mainController.send(new ProductionMessage(mainController.getGui().getID(), false, 1, false));
    }

    public void useThirdDevProduction(ActionEvent actionEvent) {
        mainController.send(new ProductionMessage(mainController.getGui().getID(), false, 2, false));
    }

    public void useSecondLeaderProduction(ActionEvent actionEvent) {
        mainController.send(new LeaderProductionMessage(1, mainController.getGui().getID(), null));
    }

    public void useFirstLeaderProduction(ActionEvent actionEvent) {
        mainController.send(new LeaderProductionMessage(0, mainController.getGui().getID(), null));
    }

    public void enableHBoxRes() {
        hBoxRes.setDisable(false);
        hBoxRes.setVisible(true);
    }

    public void chooseCoin(MouseEvent mouseEvent) {
        mainController.send(new LeaderProductionMessage(0, mainController.getGui().getID(), ResourceType.COIN));
    }

    public void chooseStone(MouseEvent mouseEvent) {
        mainController.send(new LeaderProductionMessage(0, mainController.getGui().getID(), ResourceType.STONE));
    }

    public void chooseServant(MouseEvent mouseEvent) {
        mainController.send(new LeaderProductionMessage(0, mainController.getGui().getID(), ResourceType.SERVANT));
    }

    public void chooseShield(MouseEvent mouseEvent) {
        mainController.send(new LeaderProductionMessage(0, mainController.getGui().getID(), ResourceType.SHIELD));
    }
}
