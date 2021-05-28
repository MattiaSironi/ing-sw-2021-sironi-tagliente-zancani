package it.polimi.ingsw.client.gui;


import it.polimi.ingsw.client.SocketServerConnection;
import it.polimi.ingsw.client.UI;
import it.polimi.ingsw.client.gui.controllers.GUIController;
import it.polimi.ingsw.client.gui.controllers.SetupController;
import it.polimi.ingsw.message.ActionMessages.ObjectMessage;
import it.polimi.ingsw.message.CommonMessages.ChooseNumberOfPlayer;
import it.polimi.ingsw.message.CommonMessages.IdMessage;
import it.polimi.ingsw.model.Communication;
import it.polimi.ingsw.model.CommunicationList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class GUI extends Application implements UI {

    public static final String SETUP = "setup.fxml";
    private final HashMap<String, Scene> nameMapScene = new HashMap<>();
    private final HashMap<String, GUIController> nameMapController = new HashMap<>();
    private Scene currentScene;
    private SocketServerConnection serverConnection;
    private int ID;



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        setup();
        stage.setFullScreen(false);
        stage.setTitle("Master of Renaissance");
        stage.setScene(currentScene);
        stage.show();
    }

    public void setup() {
        try {
            List<String> fxmList = new ArrayList<>(Arrays.asList(SETUP));
            for (String path : fxmList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + path));
                nameMapScene.put(path, new Scene(loader.load()));
                GUIController controller = loader.getController();
                controller.setGUI(this);
                nameMapController.put(path, controller);
                }
        }catch(IOException e){

        }
        currentScene = nameMapScene.get(SETUP);
    }

    public SocketServerConnection getServerConnection() {
        return serverConnection;
    }

    public void setServerConnection(SocketServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    @Override
    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    @Override
    public void handleAction(Object message) {
        if(message instanceof IdMessage) {
            setID(((IdMessage) message).getID());
        }
        else if (message instanceof ChooseNumberOfPlayer){
            if(((ChooseNumberOfPlayer) message).getNumberOfPlayers() == -1) {
                SetupController controller = (SetupController) nameMapController.get(SETUP);
                controller.updateHostScene();
            }
            else{
                SetupController controller = (SetupController) nameMapController.get(SETUP);
                controller.askForNickname();
            }
        }
        else if (message instanceof ObjectMessage) {
            handleObject((ObjectMessage) message);
        }
    }

    public void handleObject(ObjectMessage message) {
        if (message.getObjectID() == 9) { //Communication
            handleCommunication((Communication) message.getObject());
        }
    }
    public void handleCommunication(Communication communication){
        if(communication.getCommunication() == CommunicationList.NICK_NOT_VALID && communication.getAddresseeID() == this.ID){
            SetupController controller = (SetupController) nameMapController.get(SETUP);
            controller.setDuplicatedNickname();
        }
    }
}
