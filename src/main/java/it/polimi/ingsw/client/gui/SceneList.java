package it.polimi.ingsw.client.gui;

public enum SceneList {

    SETUP("setup.fxml"),
    START("start.fxml"),
    FIRSTDRAW("firstDraw.fxml"),
    BUYDEVCARD("buyDevCardScene.fxml"),
    MAINSCENE("mainScene.fxml"),
    PAYDEVCARDSCENE("payDevCardScene.fxml"),
    MARKET("marketScene.fxml"),
    PLACEDEVCARDSCENE("placeDevCardScene.fxml");


    private final String sceneName;

    SceneList(String sceneName) {
        this.sceneName = sceneName;
    }

    public String getSceneName() {
        return sceneName;
    }
}
