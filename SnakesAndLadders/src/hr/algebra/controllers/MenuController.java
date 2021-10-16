/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Marijo
 */
public class MenuController implements Initializable {

    @FXML
    private Button btnSingleplayer;
    @FXML
    private Button btnQuit;

    /**
     * Initialises the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnQuitClick(ActionEvent event) {
        Stage stage = (Stage) btnQuit.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void btnSingleplayerClick(ActionEvent event) throws IOException {
        
        // Close the menu
        Stage menuStage = (Stage) btnSingleplayer.getScene().getWindow();
        menuStage.close();
        
        // Open the game
        Parent root = FXMLLoader.load(getClass().getResource("/hr/algebra/view/Game.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Snakes And Ladders");
        // Adjust the height for non-resizable window 600 - 10 = 590
        stage.setScene(new Scene(root, 800, 590));
        stage.setResizable(false);
        stage.show();
    }
    
}
