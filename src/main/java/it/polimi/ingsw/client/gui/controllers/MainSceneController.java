package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.SceneList;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.ResourceType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class MainSceneController implements  GUIController {
    private GUI gui;
    public ImageView leader1;
    public ImageView leader2;



    public void setup() {
        gui.changeScene(SceneList.MAINSCENE);
    }

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    public void Leader1OpacityUp(MouseEvent mouseEvent) { leader1.setOpacity(1);}
    public void Leader1OpacityDown(MouseEvent mouseEvent) { leader1.setOpacity(0.75);}
    public void Leader2OpacityUp(MouseEvent mouseEvent) { leader2.setOpacity(1);}
    public void Leader2OpacityDown(MouseEvent mouseEvent) { leader1.setOpacity(0.75);}

    public void Leader1Clicked(MouseEvent mouseEvent) {
        if(leader1.getImage().equals("\"@../images/Leaders/BACK.png\"")){ //azione solo se il leader è ancora in mano

        }

    }

    public void Leader2Clicked(MouseEvent mouseEvent) {
    }
}
