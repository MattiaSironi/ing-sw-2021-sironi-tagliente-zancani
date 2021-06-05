package it.polimi.ingsw.client.gui;

public enum SceneList {

    SETUP("setup.fxml"),
    START("start.fxml"),
    FIRSTDRAW("firstDraw.fxml"),
    MAINSCENE("mainScene.fxml"),
    MARKET("marketScene.fxml");

    private final String sceneName;

    SceneList(String sceneName) {
        this.sceneName = sceneName;
    }

    public String getSceneName() {
        return sceneName;
    }
}
