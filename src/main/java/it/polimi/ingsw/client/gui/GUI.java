package it.polimi.ingsw.client.gui;


import it.polimi.ingsw.client.gui.controllers.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GUI extends Application {


    private final HashMap<String, Scene> nameMapScene = new HashMap<>();
    private final HashMap<String, GUIController> nameMapController = new HashMap<>();
    private MainController mainController = new MainController(this);
    private Scene currentScene;
    private int ID;
    private boolean local;
    private Stage stage;




    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public HashMap<String, Scene> getNameMapScene() {
        return nameMapScene;
    }

    public HashMap<String, GUIController> getNameMapController() {
        return nameMapController;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }

    public void updateHostScene() {
        SetupController controller = (SetupController) nameMapController.get(SceneList.SETUP.getSceneName());
        controller.updateHostScene();
    }

    public void askForNickname() {
        SetupController controller = (SetupController) nameMapController.get(SceneList.SETUP.getSceneName());
        controller.askForNickname();
    }

    public void setDuplicatedNickname() {
        SetupController controller = (SetupController) nameMapController.get(SceneList.SETUP.getSceneName());
        controller.setDuplicatedNickname();
    }

    @Override
    public void start(Stage stage) throws Exception {
        setup();
        this.stage = stage;
        stage.setResizable(true);
        stage.setFullScreen(false);
        stage.setTitle("Master of Renaissance");
        stage.setScene(currentScene);
        stage.setResizable(false);
        stage.show();
    }

    public void setup() {
        try {
            List<SceneList> fxmList = new ArrayList<>(Arrays.asList(SceneList.values()));
            for (SceneList path : fxmList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + path.getSceneName()));
                nameMapScene.put(path.getSceneName(), new Scene(loader.load()));
                GUIController controller = loader.getController();
                controller.setMainController(this.mainController);
                nameMapController.put(path.getSceneName(), controller);
                }
        }catch(IOException e){

        }
        currentScene = nameMapScene.get(SceneList.START.getSceneName());
    }

    public void changeScene(SceneList sceneName){
        GUIController controller = nameMapController.get(sceneName.getSceneName());
        controller.setup();
        Platform.runLater(() -> {
            currentScene = nameMapScene.get(sceneName.getSceneName());
            stage.setScene(currentScene);
            stage.show();
            }
        );
    }


    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setResCounterLabel(int startResCount) {
        Platform.runLater(() -> {
                    System.out.println(startResCount+ "sos");
            FirstDrawController controller = (FirstDrawController) nameMapController.get(SceneList.FIRSTDRAW.getSceneName());
            controller.setResCountLabel(startResCount);
        }
        );
    }

//    public void printError(ErrorList errorType) {
//        MainSceneController controller = (MainSceneController) nameMapController.get(SceneList.MAINSCENE.getSceneName());
//        controller.showErrors(errorType);
//    }

    public void showMarket() {
        Platform.runLater(() -> {
            ((MarketController)nameMapController.get(SceneList.MARKET.getSceneName())).showMarket();
        });
    }

//    public void playButtonSound(){
//        AudioClip audioClip = new AudioClip(getClass().getResource("/sounds/ClickSound.wav").toExternalForm());
//        audioClip.play();
//    }
}

