package it.polimi.ingsw.client.gui;


import it.polimi.ingsw.client.gui.controllers.*;
import it.polimi.ingsw.controller.Controller;
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

/**
 * Class GUI is what user launches to play in GUI mode.
 */

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

    /**
     * Method updateHostScene calls SetupController updateHostScene method.
     * @see SetupController
     */

    public void updateHostScene() {
        Platform.runLater(() -> {
            SetupController controller = (SetupController) nameMapController.get(SceneList.SETUP.getSceneName());
            controller.updateHostScene();
            }
        );
    }

    /**
     * Method askForNickname calls SetupController askForNickname method.
     * @see SetupController
     */

    public void askForNickname() {
        Platform.runLater(() -> {
            SetupController controller = (SetupController) nameMapController.get(SceneList.SETUP.getSceneName());
            controller.askForNickname();
            }
        );
    }

    /**
     * Method setDuplicatedNickname calls SetupController setDuplicatedNickname method.
     * @see SetupController
     */
    public void setInvalidNickname() {
        Platform.runLater(() -> {
            SetupController controller1 = (SetupController) nameMapController.get(SceneList.SETUP.getSceneName());
            StartController controller2 = (StartController) nameMapController.get(SceneList.START.getSceneName());
            controller1.setInvalidNickname();
            controller2.setInvalidNickname();

            }
        );
    }

    /**
     * Method start sets @param stage parameters and makes the Application visible.
     */

    @Override
    public void start(Stage stage) {
        setup();
        this.stage = stage;
        stage.setResizable(true);
        stage.setFullScreen(false);
        stage.setTitle("Master of Renaissance");
        this.stage.setOnCloseRequest(windowEvent -> {
            Platform.exit();
            if(!local)
                try{
                mainController.getServerConnection().close();}
            catch (NullPointerException e) {
                    System.exit(0);
            }
        });
        stage.setScene(currentScene);
        stage.setResizable(false);
        stage.show();


    }

    /**
     * Method setup loads with a FXMLLoader all .fxml files. it associate every file with his controller. Then, it sets the Starting Scene
     * @see FXMLLoader
     * @see GUIController
     */

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


        }catch(IOException ignored){

        }
        currentScene = nameMapScene.get(SceneList.START.getSceneName());
    }

    /**
     * Method changeScene changes Scene, calling Scene's controller's setup.
     * @param sceneName is the next scene.
     */

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

    /**
     * method ChangeOtherScene is a special ChangeScene for OtherPlayersController
     * @param userData is used to distinguish opponents.
     */

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

    /**
     * Method showTokenPlayed calls MainSceneController's showTokenPlayed method.
     * @param s is the token played toString.
     * @see MainSceneController
     */
    public void showTokenPlayed(String s) {
        Platform.runLater(() -> ((MainSceneController) nameMapController.get(SceneList.MAINSCENE.getSceneName())).showTokenPlayed(s));
    }


    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    /**
     * Method setResCounterLabel calls FirstDrawController's setResCountLabel method.
     * @param startResCount is the number of startResCount remaining.
     * @see FirstDrawController
     */

    public void setResCounterLabel(int startResCount) {
        Platform.runLater(() -> {
            FirstDrawController controller = (FirstDrawController) nameMapController.get(SceneList.FIRSTDRAW.getSceneName());
            controller.setResCountLabel(startResCount);
        });
    }


    /**
     * Method showMarket calls MarketController's showMarket method.
     * @see MarketController
     */

    public void showMarket() {
        Platform.runLater(() -> ((MarketController) nameMapController.get(SceneList.MARKET.getSceneName())).showMarket());
    }

    /**
     * Method showShelves calls MarketController and MainSceneController's showShelves.
     * @see MarketController
     * @see MainSceneController
     */

    public void showShelves() {
        Platform.runLater(() -> {
            ((MarketController) nameMapController.get(SceneList.MARKET.getSceneName())).showShelves();
            ((MainSceneController) nameMapController.get(SceneList.MAINSCENE.getSceneName())).showShelves();

        });
    }

    /**
     * Method showLeaders calls MainSceneController's showLeaders.
     * @see MainSceneController
     */

    public void showLeaders(){
        Platform.runLater(()-> ((MainSceneController) nameMapController.get(SceneList.MAINSCENE.getSceneName())).showLeaders());
    }

    /**
     * Method printMessage calls every GUIController's print.
     * @param turn is the Turn to print
     */
    public void printMessage (Turn turn){
        Platform.runLater(() -> {
            for (GUIController c : nameMapController.values()) {
                c.print(turn);
            }
        });
    }
    /**
     * Method printMessage calls every GUIController's print.
     * @param communication is the Communication to print
     */
    public void printMessage (Communication communication){
        Platform.runLater(() -> {
            for (GUIController c : nameMapController.values()) {
                c.print(communication);
            }
        });
    }

    /**
     * Method controllerSetup calls every GUIController's setup.
     */

    public void controllerSetup(){
        Platform.runLater(() -> {
            for (GUIController controller : nameMapController.values()) {
                controller.setup(getID());
            }
        });

    }
    /**
     * Method controllerSetup calls every GUIController's disable.
     */

    public void disable() {
        Platform.runLater(() -> {
            for (GUIController controller : nameMapController.values()) {
                controller.disable();
            }
        });
    }
    /**
     * Method showTokens calls MainSceneController's showTokens.
     * @see MainSceneController
     */

    public void showTokens() {
        Platform.runLater(() -> ((MainSceneController) nameMapController.get(SceneList.MAINSCENE.getSceneName())).showTokens());
    }

    /**
     * Method controllerSetup calls every GUIController's enable.
     */
    public void enable() {
        Platform.runLater(() -> {
            for (GUIController controller : nameMapController.values()) {
                controller.enable();
            }
        });
    }
    /**
     * Method showStrongbox calls ProductionController and MainSceneController's showStrongbox.
     * @see ProductionController
     * @see MainSceneController
     */

    public void showStrongbox() {
        Platform.runLater(() -> {
            ((MainSceneController) nameMapController.get(SceneList.MAINSCENE.getSceneName())).showStrongbox();
            ((ProductionController)nameMapController.get(SceneList.GENERALPRODSCENE.getSceneName())).showStrongbox();
        });

    }

    /**
     * Method showFaithTrack calls MainSceneController's showFaithTrack.
     * @see MainSceneController
     */

    public void showFaithTrack() {
        Platform.runLater(() -> ((MainSceneController) nameMapController.get(SceneList.MAINSCENE.getSceneName())).showFaithTrack());
    }

    /**
     * Method showFaithTrack calls PlaceDevCardScene's updateDevSlot.
     * @see PlaceDevCardScene
     */
    public void updateDevSlot(SceneList placeDevCardScene) {
        Platform.runLater(() -> ((PlaceDevCardScene) nameMapController.get(SceneList.PLACEDEVCARDSCENE.getSceneName())).updateDevSlot());
    }

    /**
     * Method createLocalGame creates a local game and creates a MVC pattern.
     * @see MainController
     * @see Controller
     * @see Game
     */

    public void createLocalGame() {
        Game game = new Game(true, 0);
        game.setNumPlayer(1);
        Controller controller = new Controller(game);
        mainController.addObserver(controller);
        game.addObserver(mainController); }


    /**
     * Method gameOver calls GameOverController's findWinner
     * @param doIWin is true if Player won.
     * @see GameOverController
     */
    public void gameOver(boolean doIWin) {
        Platform.runLater(() -> ((GameOverController) nameMapController.get(SceneList.GAMEOVERSCENE.getSceneName())).findWinner(doIWin));
    }


    public void activateChoiceBox() {
        Platform.runLater(() -> ((BasicProductionController) nameMapController.get(SceneList.BASICSCENE.getSceneName())).enableHBox());
    }

    public void disableMarket() {
        Platform.runLater(() -> ((MarketController) nameMapController.get(SceneList.MARKET.getSceneName())).disableArrows());
    }

    public void disableProduction() {
        Platform.runLater(() -> ((MainSceneController) nameMapController.get(SceneList.MAINSCENE.getSceneName())).disableProductionButton());
    }

    public void disableBackProduction() {
        Platform.runLater(() -> ((ProductionController) nameMapController.get(SceneList.GENERALPRODSCENE.getSceneName())).disableBackButton());
    }

    public void enableHBoxRes() {
        Platform.runLater(() -> ((ProductionController) nameMapController.get(SceneList.GENERALPRODSCENE.getSceneName())).enableHBoxRes());
    }
}



//    public void playButtonSound(){
//        AudioClip audioClip = new AudioClip(getClass().getResource("/sounds/ClickSound.wav").toExternalForm());
//        audioClip.play();
//    }




