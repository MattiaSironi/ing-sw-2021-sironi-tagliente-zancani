package it.polimi.ingsw.client.gui;


import it.polimi.ingsw.client.gui.controllers.*;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.message.ActionMessages.MarketMessage;
import it.polimi.ingsw.model.Communication;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Turn;
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
    private final HashMap<Integer, OtherPlayersController> others = new HashMap<>();
    private MainController mainController = new MainController(this);
    private Scene currentScene;
    private int ID;
    private boolean local;
    private Stage stage;
    private Scene othersScene;




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
        Platform.runLater(() -> {
            controller.setup(getID());
            currentScene = nameMapScene.get(sceneName.getSceneName());
            stage.setScene(currentScene);
            stage.show();
            }
        );
    }

    public void changeOtherScene(int userData) {
        GUIController controller = nameMapController.get(SceneList.OPPONENTS.getSceneName());
        Platform.runLater(() -> {
                    controller.setup(userData);
                    currentScene = nameMapScene.get(SceneList.OPPONENTS.getSceneName());
                    stage.setScene(currentScene);
                    stage.show();
                }
        );
    }


    public void showTokenPlayed(String s) {
        Platform.runLater(() -> {
            ((MainSceneController) nameMapController.get(SceneList.MAINSCENE.getSceneName())).showTokenPlayed(s);
        });
    }


    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setResCounterLabel(int startResCount) {
        Platform.runLater(() -> {
            FirstDrawController controller = (FirstDrawController) nameMapController.get(SceneList.FIRSTDRAW.getSceneName());
            controller.setResCountLabel(startResCount);
        });
    }




    public void showMarket() {
        Platform.runLater(() -> {
            ((MarketController) nameMapController.get(SceneList.MARKET.getSceneName())).showMarket();
        });
    }

    public void showShelves() { //TODO FOR OTHERS PLAYERS and OTHER SCENES
        Platform.runLater(() -> {
            ((MarketController) nameMapController.get(SceneList.MARKET.getSceneName())).showShelves();
            ((MainSceneController) nameMapController.get(SceneList.MAINSCENE.getSceneName())).showShelves();

        });
    }

    public void showLeaders(){
        Platform.runLater(()->{
            ((MainSceneController) nameMapController.get(SceneList.MAINSCENE.getSceneName())).showLeaders();
        });
    }

    public void printMessage (Turn turn){
        Platform.runLater(() -> {
            for (GUIController c : nameMapController.values()) {
                c.print(turn);
            }
        });
    }

    public void printMessage (Communication communication){
        Platform.runLater(() -> {
            for (GUIController c : nameMapController.values()) {
                c.print(communication);
            }
        });
    }

    public void controllerSetup(){
        Platform.runLater(() -> {
            for (GUIController controller : nameMapController.values()) {
                controller.setup(getID());
            }
        });

    }

    public void disable() {
        Platform.runLater(() -> {
            for (GUIController controller : nameMapController.values()) {
                controller.disable();
            }
        });
    }

    public void showTokens() {
        Platform.runLater(() -> {
            ((MainSceneController) nameMapController.get(SceneList.MAINSCENE.getSceneName())).showTokens();
        });
    }

    public void enable() {
        Platform.runLater(() -> {
            for (GUIController controller : nameMapController.values()) {
                controller.enable();
            }
        });
    }

    public void showStrongbox() {
        Platform.runLater(() -> {
            ((MainSceneController) nameMapController.get(SceneList.MAINSCENE.getSceneName())).showStrongbox();
        });

    }

    public void showFaithTrack() {
        Platform.runLater(() -> {
            ((MainSceneController) nameMapController.get(SceneList.MAINSCENE.getSceneName())).showFaithTrack();
        });
    }


    public void updateDevSlot(SceneList placedevcardscene) {
        Platform.runLater(() -> {
            ((PlaceDevCardScene) nameMapController.get(SceneList.PLACEDEVCARDSCENE.getSceneName())).updateDevSlot();
        });
    }

    public void createLocalGame() {
        Game game = new Game(true, 0);
        game.setNumPlayer(1);
        Controller controller = new Controller(game);
        mainController.addObserver(controller);
        game.addObserver(mainController); }

    public void gameOver(boolean doIWin) {
        Platform.runLater(() -> {
            ((GameOverController) nameMapController.get(SceneList.GAMEOVERSCENE.getSceneName())).findWinner(doIWin);
        });
    }

    public void activateChoiceBox() {
        Platform.runLater(() -> {
            ((BasicProductionController) nameMapController.get(SceneList.BASICSCENE.getSceneName())).enableHBox();
        });
    }

    public void disableMarket() {
        Platform.runLater(() -> {
            ((MarketController) nameMapController.get(SceneList.MARKET.getSceneName())).disableArrows();
        });
    }

    public void disableProduction() {
        Platform.runLater(() -> {
            ((MainSceneController) nameMapController.get(SceneList.MAINSCENE.getSceneName())).disableProductionButton();
        });
    }

    public void disableBackProduction() {
        Platform.runLater(() -> {
            ((ProductionController) nameMapController.get(SceneList.GENERALPRODSCENE.getSceneName())).disableBackButton();
        });
    }

    public void enableHBoxRes() {
        Platform.runLater(() -> {
            ((ProductionController) nameMapController.get(SceneList.GENERALPRODSCENE.getSceneName())).enableHBoxRes();
        });
    }
}



//    public void playButtonSound(){
//        AudioClip audioClip = new AudioClip(getClass().getResource("/sounds/ClickSound.wav").toExternalForm());
//        audioClip.play();
//    }




