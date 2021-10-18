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
import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
        
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
    private Circle cP1;
    @FXML
    private Circle cP2;
    @FXML
    private Label lblEndMessage;
    @FXML
    private Label lblPlayerScore;
    @FXML
    private Label lblOpponentScore;
    @FXML
    private Label lblPlayerRolledNumber;
    @FXML
    private Label lblOpponentRolledNumber;
    @FXML
    private Label lblSnakeInfo;
    @FXML
    private Label lblLadderInfo;
    @FXML
    private ProgressIndicator piRolling;
    
    Player player;    
    Player computer;

    private final List<Snake> Snakes;
    private final List<Ladder> Ladders;

    public GameController() {
        this.Snakes = new ArrayList<>();
        this.Ladders = new ArrayList<>();
    }
    
    /**
     * Initialises the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializePlayers();
        initializeColors();
        initializeGameBoard();
    }    

    private void initializePlayers() {
        player = new Player(cP1, Paint.valueOf(Color.CADETBLUE.toString()));
        computer = new Player(cP2, Paint.valueOf(Color.CORAL.toString()));
    }
    private void initializeColors() {
        lblPlayerScore.setTextFill(player.getPaint());
        lblOpponentScore.setTextFill(computer.getPaint());
        lblPlayerRolledNumber.setTextFill(player.getPaint());
        lblOpponentRolledNumber.setTextFill(computer.getPaint());
    }

    private void initializeGameBoard() {
        
        Snakes.add(new Snake(new Point(1,8), new Point(2,9)));        
//        Snakes.add(new Snake(new Point(3,6), new Point(4,9)));
//        Snakes.add(new Snake(new Point(7,6), new Point(8,9)));
//        Snakes.add(new Snake(new Point(5,5), new Point(6,8)));
//        Snakes.add(new Snake(new Point(0,1), new Point(3,8)));
//        Snakes.add(new Snake(new Point(1,4), new Point(0,6)));
//        Snakes.add(new Snake(new Point(7,2), new Point(0,9)));
//        Snakes.add(new Snake(new Point(7,0), new Point(9,3)));
//        Snakes.add(new Snake(new Point(4,0), new Point(5,3)));
//        Snakes.add(new Snake(new Point(2,0), new Point(6,6)));
//        
//        Ladders.add(new Ladder(new Point(6,9), new Point(7,7)));
//        Ladders.add(new Ladder(new Point(4,7), new Point(4,6)));
//        Ladders.add(new Ladder(new Point(1,7), new Point(0,2)));
//        Ladders.add(new Ladder(new Point(1,2), new Point(1,0)));
//        Ladders.add(new Ladder(new Point(4,3), new Point(3,1)));
//        Ladders.add(new Ladder(new Point(5,4), new Point(6,0)));
        Ladders.add(new Ladder(new Point(8,5), new Point(8,0)));
    }
        
    @FXML
    private void btnRollClick(ActionEvent event) throws InterruptedException {
        playRound();
    }
    
    private void removeGridPanelChildren(Node... nodes) {
        for(Node node : nodes) gpGrid.getChildren().remove(node);
    }
    
    private Point getPlayerNextPosition(Player p, int roll) {
        
        // Grid size
        final int rowsCount = gpGrid.getRowConstraints().size();

        // Default starting position
        Point location = p.getLocation();
        
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
        if(player.getWin()) {
            btnRoll.setText("Play Again!");
            lblEndMessage.setText("You Win!");
            return true;
        } else if(computer.getWin()) {
            btnRoll.setText("Play Again!");
            lblEndMessage.setText("You Lose!");
            return true;
        } else lblEndMessage.setText("");
        return false;
    }

    private void setScore(Player p, Label lbl) {
        lbl.setText(String.valueOf(p.getScore()));
    }

    private void playRound() {
        long deplayPerPlayer = 80;
        
        blockButton(deplayPerPlayer * 2);
        spinProgress(deplayPerPlayer * 2);
        updatePlayer(player, lblPlayerRolledNumber, lblPlayerScore, Dice.roll(), deplayPerPlayer);
        updatePlayer(computer, lblOpponentRolledNumber, lblOpponentScore, Dice.roll(), deplayPerPlayer * 2);
        

    }

    private void updatePlayer(Player p, Label lblRolled, Label lblScore, int roll, long sleep) {
        // Run the player
        new Thread() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    btnRoll.setDisable(true);
                    lblRolled.setText("-");
                    if(checkWin()) clearGame();
                });
                try {
                    Thread.sleep(sleep);
                }
                catch(InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
                Platform.runLater(() -> {
                    lblRolled.setText(String.valueOf(roll));
                    p.setLocation((Point)validatePosition(getPlayerNextPosition(p, roll)).clone());

                    // Remove the player form the grid then add it back to new location
                    removeGridPanelChildren(p.getFigure());
                    gpGrid.add(p.getFigure(),p.getLocation().x,p.getLocation().y);
                    setScore(p, lblScore);
                    checkWin();
                });
            }
        }.start();
    }

    private void clearGame() {
        player.reset();
        computer.reset();
        lblPlayerRolledNumber.setText("-");
        lblOpponentRolledNumber.setText("-");
        removeGridPanelChildren(cP1, cP2);
        gpGrid.add(cP1,player.getLocation().x,player.getLocation().y);
        gpGrid.add(cP2,computer.getLocation().x,computer.getLocation().y);
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
}
