package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.model.Communication;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Turn;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameOverController implements GUIController {

    public VBox box;
    public Label first;
    public Label second;
    public Label third;
    public Label fourth;
    public ImageView finalMessage;
    private MainController mainController;
    private GUI gui;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
        this.gui=m.getGui();
    }

    @Override
    public void setup(int num) {
    }

    public void findWinner(boolean doIWin) {
        ArrayList<Player> players = new ArrayList<>();
        players.addAll(mainController.getGame().getPlayers());
        Map<Integer, Map<Player,Integer>> orderedPlayers = new HashMap<>();
        Map<Player,Integer> playerPoints = new HashMap<>();
        for(int i=0;i<mainController.getGame().getPlayers().size();i++){
            playerPoints=findFirst(players);
            orderedPlayers.put(i,playerPoints);
            players.remove(playerPoints.keySet().toArray()[0]);
        }
        this.showScores(orderedPlayers,doIWin);
    }

    private void showScores(Map<Integer, Map<Player, Integer>> orderedPlayers, boolean doIWin) {
        Player p;
        if(doIWin){
            finalMessage.setImage(new Image(getClass().getResource("/images/you_win.png").toExternalForm()));
        }
        else{
            finalMessage.setImage(new Image(getClass().getResource("/images/gameOver.png").toExternalForm()));
        }
        if(mainController.getGame().getNumPlayer()>1) {
            for (int i = 0; i < mainController.getGame().getNumPlayer(); i++) {
                switch (i) {
                    case 0 -> {
                        p = (Player) orderedPlayers.get(0).keySet().toArray()[0];
                        first.setText("1: " + p.getNickname() + "    Points: " + orderedPlayers.get(0).get(p));
                    }
                    case 1 -> {
                        p = (Player) orderedPlayers.get(1).keySet().toArray()[0];
                        second.setText("2: " + p.getNickname() + "    Points: " + orderedPlayers.get(1).get(p));
                    }

                    case 2 -> {
                        p = (Player) orderedPlayers.get(2).keySet().toArray()[0];
                        third.setText("3: " + p.getNickname() + "    Points: " + orderedPlayers.get(2).get(p));
                    }

                    case 3 -> {
                        p = (Player) orderedPlayers.get(3).keySet().toArray()[0];
                        fourth.setText("4: " + p.getNickname() + "    Points: " + orderedPlayers.get(3).get(p));
                    }
                }
            }
        }else{
            p = (Player) orderedPlayers.get(0).keySet().toArray()[0];
            first.setText("My score: " + orderedPlayers.get(0).get(p));
        }
    }

    private Map<Player,Integer> findFirst(ArrayList<Player> players) {
        Player winner = null;
        int maxVictoryPoints = 0;
        int winnerResources = 0;
        Map<Player,Integer> first = new HashMap<>();
        for (Player p : players) {
            int resources = p.getValueResources();
            int victoryPoints = p.sumDevs() + p.sumLeads() + p.sumPope() + p.getValuePos() + resources/5;

            if (victoryPoints > maxVictoryPoints) {
                winner = p;
                maxVictoryPoints = victoryPoints;
                winnerResources = resources;
            } else if (victoryPoints == maxVictoryPoints) {
                if (resources >= winnerResources) {
                    winner = p;
                    winnerResources = resources;
                }
            }
        }
        first.put(winner,maxVictoryPoints);
        return first;
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
}
