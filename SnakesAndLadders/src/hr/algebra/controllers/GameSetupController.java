/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controllers;

import hr.algebra.models.Player;
import hr.algebra.repository.Repository;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Marijo
 */
public class GameSetupController implements Initializable {

    @FXML
    private TextField tfNickname;
    @FXML
    private RadioButton rbBlue;
    @FXML
    private ToggleGroup tgColors;
    @FXML
    private RadioButton rbGreen;
    @FXML
    private RadioButton rbRed;
    @FXML
    private RadioButton rbYellow;
    @FXML
    private ComboBox<?> cbPlayerCount;
    @FXML
    private Button btnPlay;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnPlayClick(ActionEvent event) throws IOException {
        
        
        Repository.addPlayer(new Player(tfNickname.getText().trim(), new Circle(18), Paint.valueOf(Color.CADETBLUE.toString())));
        Repository.addPlayer(new Player("Computer1" ,new Circle(18), Paint.valueOf(Color.CORAL.toString())));
        
        // Close the menu
        Stage menuStage = (Stage) btnPlay.getScene().getWindow();
        menuStage.close();
        
        // Open the game
        Parent root = FXMLLoader.load(getClass().getResource("/hr/algebra/view/Game.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Snakes And Ladders");
        // Adjust the height for non-resizable window 600 - 10 = 590
        stage.setScene(new Scene(root, 790, 590));
        stage.setResizable(false);
        stage.show();
    }
    
}
