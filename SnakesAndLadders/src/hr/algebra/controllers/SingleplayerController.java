/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controllers;

import hr.algebra.engine.GameEngine;
import hr.algebra.models.Ladder;
import hr.algebra.models.Player;
import hr.algebra.models.Snake;
import hr.algebra.repository.Repository;
import hr.algebra.utils.Randomiser;
import hr.algebra.utils.Serialization;
import java.awt.Point;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Marijo
 */
public class SingleplayerController implements Initializable {

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
    private TableColumn<Player, String> tcNick, tcScore;

    private final List<Player> Players = Repository.getPlayers();
    private final List<Snake> Snakes = Repository.SNAKES;
    private final List<Ladder> Ladders = Repository.LADDERS;

    private final long ROLL_DELAY = 1;
    @FXML
    private MenuItem miSaveGame;
    @FXML
    private MenuItem miQuitGame;

    /**
     * Initialises the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTableCells();
        initObservables();
        displayPlayers();
    }

    private void initTableCells() {
        tcNick.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        tcScore.setCellValueFactory(new PropertyValueFactory<>("score"));
    }

    private void initObservables() {
        tvPlayers.setItems(Repository.getPlayers());
    }

    @FXML
    private void btnRollClick(ActionEvent event) throws InterruptedException {
        playRound();
    }

    private void removeGridPanelChildren(Node... nodes) {
        for (Node node : nodes) {
            gpGrid.getChildren().remove(node);
        }
    }

    private Point validatePosition(Point playerNextPosition) {
        for (Snake s : Snakes) {
            if (s.getStartLocation().equals(playerNextPosition)) {
                // Display Info
                new Thread() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            lblSnakeInfo.setText("SNAKE!");
                        });
                        try {
                            Thread.sleep(ROLL_DELAY);
                        } catch (InterruptedException ex) {
                            System.out.println(ex.getMessage());
                        }
                        Platform.runLater(() -> {
                            lblSnakeInfo.setText("");
                        });
                    }
                }.start();
                return s.getEndLocation();
            }
        }

        for (Ladder l : Ladders) {
            if (l.getStartLocation().equals(playerNextPosition)) {
                // Display Info
                new Thread() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            lblLadderInfo.setText("LADDER!");
                        });
                        try {
                            Thread.sleep(ROLL_DELAY);
                        } catch (InterruptedException ex) {
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
        for (Player player : Players) {
            if (player.getWin()) {
                btnRoll.setText("Play Again!");
                lblEndMessage.setText(player.getNickname() + " Win!");
                return true;
            }
        }
        return false;
    }

    private void playRound() {
        int index = 0;
        blockButton(ROLL_DELAY * Players.size());
        spinProgress(ROLL_DELAY * Players.size());

        for (Player player : Players) {
            index++;
            updatePlayer(player, Randomiser.roll(), ROLL_DELAY * index);
        }
    }

    private void updatePlayer(Player p, int roll, long sleep) {
        new Thread() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    btnRoll.setDisable(true);
                    if (checkWin()) {
                        btnRoll.setDisable(false);
                        clearGame();
                    }
                });
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
                Platform.runLater(() -> {
                    p.setLocation((Point) validatePosition(GameEngine.getNextPoint(p.getLocation(), roll, gpGrid.getRowConstraints().size())).clone());

                    // Remove the player form the grid then add it back to new location
                    removeGridPanelChildren(p.getFigure());
                    lblRolled.setTextFill(p.getPaint());
                    lblRolled.setText(String.valueOf(roll));
                    p.setScore(GameEngine.getScoreFromPoint(p.getLocation()));
                    gpGrid.add(p.getFigure(), p.getLocation().x, p.getLocation().y);
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
            gpGrid.add(player.getFigure(), player.getLocation().x, player.getLocation().y);
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
                } catch (InterruptedException ex) {
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
                } catch (InterruptedException ex) {
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
        if (alert.showAndWait().get() == ButtonType.OK) {
            clearGame();
        }
    }

    @FXML
    private void handleSaveGame(ActionEvent event) {
        
        String FILE_NAME = "players.ser";
        
        try {
            Serialization.write(new ArrayList<>(Players), FILE_NAME);
//            ArrayList<Player> ps = (ArrayList<Player>) Serialization.read(FILE_NAME);
//            System.out.println(ps);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succesfully saved!");
            alert.setHeaderText(null);
            alert.setContentText("Game is saved! file: " + FILE_NAME);

            alert.showAndWait();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void handleQuitGame(ActionEvent event) throws IOException {
        
        // Close the Sp
        Stage sps = (Stage) btnRoll.getScene().getWindow();
        sps.close();
        
        // Open the main menu
        Parent root = FXMLLoader.load(getClass().getResource("/hr/algebra/view/MainMenu.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Snakes And Ladders - Main Menu");
        // Adjust the height for non-resizable window 600 - 10 = 590
        stage.setScene(new Scene(root, 600, 400));
        stage.setResizable(false);
        stage.show();
        
    }

    private void displayPlayers() {
        Players.stream().forEachOrdered((player) -> {
            gpGrid.add(player.getFigure(), player.getLocation().x, player.getLocation().y);
        });
    }
}
