package it.polimi.ingsw.client.gui;

/**
 * enum SceneList contains all the scenes of the gui with the string to get the corresponding fxml file
 */

public enum SceneList {

    SETUP("setup.fxml"),
    START("start.fxml"),
    FIRSTDRAW("firstDraw.fxml"),
    BUYDEVCARD("buyDevCardScene.fxml"),
    MAINSCENE("mainScene.fxml"),
    PAYDEVCARDSCENE("paymentScene.fxml"),
    MARKET("marketScene.fxml"),
    PLACEDEVCARDSCENE("placeDevCardScene.fxml"),
    OPPONENTS("othersScene.fxml"),
    GAMEOVERSCENE("gameOver.fxml"),
    GENERALPRODSCENE("productionScene.fxml"),
    BASICSCENE("basicProductionScene.fxml");



    private final String sceneName;

    SceneList(String sceneName) {
        this.sceneName = sceneName;
    }

    public String getSceneName() {
        return sceneName;
    }
}
