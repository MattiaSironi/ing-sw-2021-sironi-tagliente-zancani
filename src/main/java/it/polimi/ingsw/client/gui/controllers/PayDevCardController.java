package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.message.ActionMessages.BasicProductionMessage;
import it.polimi.ingsw.message.ActionMessages.BuyDevCardMessage;
import it.polimi.ingsw.model.Communication;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.ShelfWarehouse;
import it.polimi.ingsw.model.Turn;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class PayDevCardController implements GUIController {

    public HBox hBox;
    public ImageView res1;
    public ImageView res2;
    public ImageView res3;
    public ImageView res4;
    public ImageView res5;
    public ImageView res6;
    public ImageView res7;
    public ImageView res8;
    public ImageView chosenCard;
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
    public Button payFromWarehouse;
    public Button pauFromStrongbox;
    public Label coinNum;
    public Label stoneNum;
    public Label servantNum;
    public Label shieldNum;
    private MainController mainController;
    private GUI gui;
    private ArrayList<ImageView> resToPay;

    public void prepareScene(){
        for(ImageView imageView : resToPay){
            imageView.setImage(null);
        }
        shelf1pos1.setImage(null);
        shelf2pos1.setImage(null);
        shelf2pos2.setImage(null);
        shelf3pos1.setImage(null);
        shelf3pos2.setImage(null);
        shelf3pos3.setImage(null);
        shelf4pos1.setImage(null);
        shelf4pos2.setImage(null);
        shelf5pos1.setImage(null);
        shelf5pos2.setImage(null);

    }

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
        this.gui = m.getGui();

    }

    @Override
    public void setup() {
        resToPay = new ArrayList<>();
        prepareScene();
        showShelves();
        showStrongbox();
        if(!(mainController.getGame().getBoard().getMatrix().getChosenCard() == null)) {
            chosenCard.setImage(new Image(getClass().getResource("/images/Devs/FRONT/" + mainController.getGame().getBoard().getMatrix().getChosenCard().toString() + ".png").toExternalForm()));
        }
        resToPay.add(res1);
        resToPay.add(res2);
        resToPay.add(res3);
        resToPay.add(res4);
        resToPay.add(res5);
        resToPay.add(res6);
        resToPay.add(res7);
        resToPay.add(res8);
        int i = 0;
        if(!(mainController.getGame().getBoard().getMatrix().getResToPay().size() == 0)) {
            resToPay.get(0).setImage(new Image(getClass().getResource("/images/PunchBoard/" + mainController.getGame().getBoard().getMatrix().getResToPay().get(0) + ".png").toExternalForm()));
        }
        //for (ResourceType resource : mainController.getGame().getBoard().getMatrix().getResToPay()) {
        //    resToPay.get(i).setImage(new Image(getClass().getResource("/images/PunchBoard/" + resource.toString() + ".png").toExternalForm()));
        //    i++;
    }

    public void showStrongbox(){
        coinNum.setText("" + mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getStrongbox().getResCount(ResourceType.COIN));
        stoneNum.setText("" + mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getStrongbox().getResCount(ResourceType.STONE));
        servantNum.setText("" + mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getStrongbox().getResCount(ResourceType.SERVANT));
        shieldNum.setText("" + mainController.getGame().getPlayerById(mainController.getGui().getID()).getPersonalBoard().getStrongbox().getResCount(ResourceType.SHIELD));
    }

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

        if (myShelves.getShelves().get(1).getResType() != ResourceType.EMPTY) {
            r = myShelves.getShelves().get(1).getResType();
            max = myShelves.getShelves().get(1).getCount();
            for (ImageView iv : shelf2) {
                if (shelf2.indexOf(iv) <= max - 1)
                    iv.setImage(new Image(getClass().getResource("/images/PunchBoard/" + r.toString().toLowerCase() + ".png").toExternalForm()));
            }
        }
        if (myShelves.getShelves().get(2).getResType() != ResourceType.EMPTY) {
            r = myShelves.getShelves().get(2).getResType();
            max = myShelves.getShelves().get(2).getCount();
            for (ImageView iv : shelf3) {
                if (shelf3.indexOf(iv) <= max - 1)
                    iv.setImage(new Image(getClass().getResource("/images/PunchBoard/" + r.toString().toLowerCase() + ".png").toExternalForm()));
            }
        }
        if (myShelves.getShelves().get(3).getResType() != null) {
            r = myShelves.getShelves().get(3).getResType();
            max = myShelves.getShelves().get(3).getCount();
            for (ImageView iv : shelf4) {
                iv.setImage(new Image(getClass().getResource("/images/PunchBoard/" + r.toString().toLowerCase() + ".png").toExternalForm()));
                if (!(shelf4.indexOf(iv) <= max - 1)) iv.setOpacity(0.5);
            }
        }
        if (myShelves.getShelves().get(4).getResType() != null) {
            r = myShelves.getShelves().get(4).getResType();
            max = myShelves.getShelves().get(4).getCount();
            for (ImageView iv : shelf5) {
                iv.setImage(new Image(getClass().getResource("/images/PunchBoard/" + r.toString().toLowerCase() + ".png").toExternalForm()));
                if (!(shelf5.indexOf(iv) <= max - 1)) iv.setOpacity(0.5);
            }
        }
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

    public void payFromWarehouse(ActionEvent actionEvent) {
        mainController.send(new BuyDevCardMessage(-1, mainController.getGui().getID(), true, -1));
    }

    public void PayFromStrongbox(ActionEvent actionEvent) {
        mainController.send(new BuyDevCardMessage(-1, mainController.getGui().getID(), false, -1));
    }
}
