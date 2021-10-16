/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controllers;

import hr.algebra.models.Player;
import java.awt.Point;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
    
    Player p1;
    
    @FXML
    private Circle cP1;
            
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         p1 = new Player(Paint.valueOf("#5ec4fe"),new Point(1,1));
    }    

    @FXML
    private void btnRollClick(ActionEvent event) {
        cP1.setVisible(true);
        cP1.setFill(p1.getColor());
        ObservableList<Node> players = gpGrid.getChildren();
        players
    }
    
}
