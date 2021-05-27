package it.polimi.ingsw.client.gui;


import it.polimi.ingsw.client.SocketServerConnection;
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

public class GUI extends Application {

    public static final String TEST = "test.fxml";
    private final HashMap<String, Scene> nameMapScene = new HashMap<>();
    public TextField addressIn;
    public Label label1;
    public Button openConnection;
    private Scene currentScene;
    private SocketServerConnection serverConnection;



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
            List<String> fxmList = new ArrayList<>(Arrays.asList(TEST));
            for (String path : fxmList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + path));
                nameMapScene.put(path, new Scene(loader.load()));
            }
        }catch(IOException e){

        }
        currentScene = nameMapScene.get(TEST);
    }


    public void setupConnection(ActionEvent actionEvent) {
        try {
            System.out.println(addressIn.getText());
            serverConnection = new SocketServerConnection();
            serverConnection.socketInit(addressIn.getText());

        } catch(IOException e){

        }
    }
}
