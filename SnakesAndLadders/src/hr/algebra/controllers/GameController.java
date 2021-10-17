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
    
    Player player;    
    Player computer;

    private final List<Snake> Snakes;
    private final List<Ladder> Ladders;
    
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
    private ProgressIndicator piPlayer;
    @FXML
    private ProgressIndicator piOpponent;
    @FXML
    private Label lblSnakeInfo;
    @FXML
    private Label lblLadderInfo;

    public GameController() {
        this.Snakes = new ArrayList<>();
        this.Ladders = new ArrayList<>();
        
        player = new Player(Paint.valueOf("#5ec4fe"));
        computer = new Player(Paint.valueOf("#5ec4fe"));
    }
            
    /**
     * Initialises the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeGameBoard();
    }    

    @FXML
    private void btnRollClick(ActionEvent event) throws InterruptedException {

        if(player.getWin() || computer.getWin()){
            player.reset();
            computer.reset();
            updateEndMessage();
            lblPlayerRolledNumber.setText("-");
            lblOpponentRolledNumber.setText("-");
            removeGridPanelChildren(cP1, cP2);
            gpGrid.add(cP1,player.getLocation().x,player.getLocation().y);
            gpGrid.add(cP2,computer.getLocation().x,computer.getLocation().y);
            btnRoll.setText("Roll");
            return;
        }
                
        final int PlayerRolledNumber = Dice.roll();
        final int OpponentRolledNumber = Dice.roll();
        
        new Thread() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    btnRoll.setDisable(true);
                    lblPlayerRolledNumber.setText("");
                    piPlayer.setVisible(true);
                });
                try {
                    Thread.sleep(600);
                }
                catch(InterruptedException ex) {
                }
                Platform.runLater(() -> {
                    
                    lblOpponentRolledNumber.setText("");
                    piOpponent.setVisible(true);

                    removeGridPanelChildren(cP1);

                    piPlayer.setVisible(false);
                    lblPlayerRolledNumber.setText(String.valueOf(PlayerRolledNumber));
                    
                    player.setLocation((Point)validatePosition(getPlayerNextPosition(player, PlayerRolledNumber)).clone());
                    gpGrid.add(cP1,player.getLocation().x,player.getLocation().y);
                    setScore(player, lblPlayerScore);
                });
            }
        }.start();
        
        new Thread() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    btnRoll.setDisable(true);
                });
                try {
                    Thread.sleep(1200);
                }
                catch(InterruptedException ex) {
                }
                Platform.runLater(() -> {
                    removeGridPanelChildren(cP2);

                    btnRoll.setDisable(false);
                    piOpponent.setVisible(false);
                    lblOpponentRolledNumber.setText(String.valueOf(OpponentRolledNumber));
                    
                    computer.setLocation((Point)validatePosition(getPlayerNextPosition(computer, OpponentRolledNumber)).clone());
                    gpGrid.add(cP2,computer.getLocation().x,computer.getLocation().y);
                    setScore(computer, lblOpponentScore);

                    if(player.getWin() || computer.getWin()){
                       btnRoll.setText("Play Again!");
                    }
                    
                    updateEndMessage();

                });
            }
        }.start();
    }
    
    private void removeGridPanelChildren(Node... nodes) {
        for(Node node : nodes) gpGrid.getChildren().remove(node);
    }

    /*
                0 1 2 3 4 5 6 7 8 9  X axis
                _ _ _ _ _ _ _ _ _ _
     Y      0 | X - win position
            1 |
     a      2 |         <--
     x      3 |         -->
     i      4 |         <--
     s      5 |     --> FLOW -->
            6 |         <--     
            7 |          -->   
            8 |         <--
            9 | O - default starting position
                                  

    */
    
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

    private void initializeGameBoard() {
        Snakes.add(new Snake(new Point(1,8), new Point(2,9)));        
        Snakes.add(new Snake(new Point(3,6), new Point(4,9)));
        Snakes.add(new Snake(new Point(7,6), new Point(8,9)));
        Snakes.add(new Snake(new Point(5,5), new Point(6,8)));
        Snakes.add(new Snake(new Point(0,1), new Point(3,8)));
        Snakes.add(new Snake(new Point(1,4), new Point(0,6)));
        Snakes.add(new Snake(new Point(7,2), new Point(0,9)));
        Snakes.add(new Snake(new Point(7,0), new Point(9,3)));
        Snakes.add(new Snake(new Point(4,0), new Point(5,3)));
        Snakes.add(new Snake(new Point(2,0), new Point(6,6)));
        
        Ladders.add(new Ladder(new Point(6,9), new Point(7,7)));
        Ladders.add(new Ladder(new Point(4,7), new Point(4,6)));
        Ladders.add(new Ladder(new Point(1,7), new Point(0,2)));
        Ladders.add(new Ladder(new Point(1,2), new Point(1,0)));
        Ladders.add(new Ladder(new Point(4,3), new Point(3,1)));
        Ladders.add(new Ladder(new Point(5,4), new Point(6,0)));
        Ladders.add(new Ladder(new Point(8,5), new Point(8,0)));
    }

    private void updateEndMessage() {
        if(player.getWin()) lblEndMessage.setText("You Win!");
        else if(computer.getWin()) lblEndMessage.setText("You Lose!");
        else lblEndMessage.setText("");
    }

    private void setScore(Player p, Label lbl) {
        lbl.setText(String.valueOf(p.getScore()));
    }
    
    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }
    
}
