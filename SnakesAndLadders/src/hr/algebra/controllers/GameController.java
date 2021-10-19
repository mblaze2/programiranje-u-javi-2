/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controllers;

import hr.algebra.models.Ladder;
import hr.algebra.utils.Dice;
import hr.algebra.models.Player;
import hr.algebra.models.Snake;
import hr.algebra.repository.Repository;
import java.awt.Point;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
        
/**
 * FXML Controller class
 *
 * @author Marijo
 */
public class GameController implements Initializable {

    @FXML
    private Button btnRoll;
    @FXML
    private GridPane gpGrid;
    @FXML
    private Label lblEndMessage, lblRolled;
    @FXML
    private Label lblSnakeInfo, lblLadderInfo;
    @FXML
    private MenuItem miReset;
    @FXML
    private ProgressIndicator piRolling;
    @FXML
    private TableView<Player> tvPlayers;
    @FXML
    private TableColumn<Player, String> tcNick, tcRoll, tcScore;
    
    private final List<Player> Players = Repository.getPlayers();
    private final List<Snake> Snakes = Repository.SNAKES;
    private final List<Ladder> Ladders = Repository.LADDERS;
    
    /**
     * Initialises the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTableCells();
        initObservables();
        clearGame();
    }    
        
    private void initTableCells() {
        tcNick.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        tcScore.setCellValueFactory(new PropertyValueFactory<>("score"));
        tcRoll.setCellValueFactory(new PropertyValueFactory<>("roll"));
    }

    private void initObservables() {
        tvPlayers.setItems(Repository.getPlayers());
    }
    
    @FXML
    private void btnRollClick(ActionEvent event) throws InterruptedException {
        playRound();
    }
    
    private void removeGridPanelChildren(Node... nodes) {
        for(Node node : nodes) gpGrid.getChildren().remove(node);
    }
    
    private Point getPlayerNextPosition(Point location, int roll) {
        // Grid size
        final int rowsCount = gpGrid.getRowConstraints().size();
        // Check if we move from left to right
        boolean rollRight = location.y % 2 != 0;
        // Figure out the next X point based where we move
        final int nextXPoint = rollRight ? location.x + roll : location.x - roll;
        if(nextXPoint > (rowsCount - 1) && rollRight)
        {
            location.y -= 1;
            // If we move to the next row we have to add the rest of points
            location.x = (rowsCount - 1) - (nextXPoint - rowsCount) ;
        }
        else 
        {
            if(nextXPoint < 0){
                // Make sure we dont overshoot!
                if(location.y != 0) {
                    location.y -= 1;
                    // If we move to the next row we have to add the rest of points
                    location.x = Math.abs(nextXPoint) - 1;
                }
            }else{
                // Figure out if we move to right or to the left
                if(rollRight) location.x += roll;
                else location.x -= roll;
            }
        }
        return location;
    }

    private Point validatePosition(Point playerNextPosition) {
        for (Snake s : Snakes){
            if(s.getStartLocation().equals(playerNextPosition)){
                // Display Info
                new Thread() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            lblSnakeInfo.setText("SNAKE!");
                        });
                        try {
                            Thread.sleep(1200);
                        }
                        catch(InterruptedException ex) {
                            System.out.println(ex.getMessage());
                        }
                        Platform.runLater(() -> {
                            lblSnakeInfo.setText("");
                        });
                    }
                }.start();
                return  s.getEndLocation();
            }
        }
        
        for (Ladder l : Ladders){
            if(l.getStartLocation().equals(playerNextPosition)){
                // Display Info
                new Thread() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            lblLadderInfo.setText("LADDER!");
                        });
                        try {
                            Thread.sleep(1200);
                        }
                        catch(InterruptedException ex) {
                            System.out.println(ex.getMessage());
                        }
                        Platform.runLater(() -> {
                            lblLadderInfo.setText("");
                        });
                    }
                }.start();
                return l.getEndLocation();
            }
        }
        return playerNextPosition;
    }

    private boolean checkWin() {
        for (Player player : Players){
            if(player.getWin()){
                lblEndMessage.setText(player.getNickname() + " Win!");
                return true;
            }
        }
        return false;
    }

    private void playRound() {
        long deplayPerPlayer = 800;
        
        int index = 0;
        blockButton(deplayPerPlayer * Players.size());
        spinProgress(deplayPerPlayer * Players.size());
        
        for (Player player : Players){
            index ++;
            updatePlayer(player,  Dice.roll(), deplayPerPlayer * index);
        }
    }

    private void updatePlayer(Player p, int roll, long sleep) {
        new Thread() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    btnRoll.setDisable(true);
                    if(checkWin()) clearGame();
                });
                try {
                    Thread.sleep(sleep);
                }
                catch(InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
                Platform.runLater(() -> {
                    p.setLocation((Point)validatePosition(getPlayerNextPosition(p.getLocation(), roll)).clone());

                    // Remove the player form the grid then add it back to new location
                    removeGridPanelChildren(p.getFigure());
                    lblRolled.setTextFill(p.getPaint());
                    lblRolled.setText(String.valueOf(roll));
                    gpGrid.add(p.getFigure(),p.getLocation().x,p.getLocation().y);
                    tvPlayers.refresh();
                    checkWin();
                });
            }
        }.start();
    }

    private void clearGame() {
        Players.stream().map((player) -> {
            player.reset();
            return player;
        }).map((player) -> {
            removeGridPanelChildren(player.getFigure());
            return player;
        }).forEachOrdered((player) -> {
            gpGrid.add(player.getFigure(),player.getLocation().x,player.getLocation().y);
        });
        
        // Clear the player data table
        tvPlayers.refresh();
        
        // Clear the lables
        lblRolled.setText("");
        lblEndMessage.setText("");

        // Reset action button text
        btnRoll.setText("Roll");
    }

    private void blockButton(long sleep) {
        new Thread() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    btnRoll.setDisable(true);
                });
                try {
                    Thread.sleep(sleep);
                }
                catch(InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
                Platform.runLater(() -> {
                    btnRoll.setDisable(false);
                });
            }
        }.start();
    }

    private void spinProgress(long sleep) {
        new Thread() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    piRolling.setVisible(true);
                });
                try {
                    Thread.sleep(sleep);
                }
                catch(InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
                Platform.runLater(() -> {
                    piRolling.setVisible(false);
                });
            }
        }.start();
    }

    @FXML
    private void miResetClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("You are about to reset your progress!");

        alert.setContentText("Really want a fresh start?");
        if (alert.showAndWait().get() == ButtonType.OK){
            clearGame(); 
        }
    }
}
