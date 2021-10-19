/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controllers;

import hr.algebra.models.Player;
import hr.algebra.repository.Repository;
import hr.algebra.utils.Randomiser;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

/**
 * FXML Controller class
 *
 * @author Marijo
 */
public class GameMenuController implements Initializable {

    @FXML
    private TextField tfNickname;
    @FXML
    private Button btnPlay;
    @FXML
    private TextField tfPlayers;
    @FXML
    private Slider slPlayers;
    
    private final int RADIUS = 26;
    @FXML
    private ColorPicker cpColor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        addIntegerMask(tfPlayers);
        addListeners();
        addBindings();
        handlePlayButtonReleased();
    }

    private void addIntegerMask(TextField tf) {
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String input = change.getText();
            if (input.matches("\\d*")) {
                return change;
            }
            return null;
        };
        // first we must convert integer to string, and then we apply filter
        tf.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 0, integerFilter));
    }

    @FXML
    private void btnPlayClick(ActionEvent event) throws IOException {

        Repository.addPlayer(new Player(tfNickname.getText().trim(), new Circle(RADIUS), Paint.valueOf(cpColor.getValue().toString())));

        // Add computers
        for (int i = 0; i < (int) slPlayers.getValue(); i++) {
            Circle ca = new Circle(RADIUS);
            ca.setId("COM" + String.valueOf(i + 1));
            Repository.addPlayer(new Player("COM" + String.valueOf(i + 1), ca, Randomiser.paint()));
        }

        // Close the menu
        Stage menuStage = (Stage) btnPlay.getScene().getWindow();
        menuStage.close();

        // Open the game
        Parent root = FXMLLoader.load(getClass().getResource("/hr/algebra/view/Singleplayer.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Snakes And Ladders - Singleplayer");
        // Adjust the height for non-resizable window 600 - 10 = 590
        stage.setScene(new Scene(root, 990, 790));
        stage.setResizable(false);
        stage.show();
    }

    private void addListeners() {
        slPlayers.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            handlePlayButtonReleased();
        });
    }

    private void addBindings() {
        tfPlayers.textProperty().bindBidirectional(slPlayers.valueProperty(), NumberFormat.getIntegerInstance());
    }

    @FXML
    private void handlePlayButtonReleased() {
        btnPlay.setDisable(
                tfNickname.getText().trim().isEmpty()
                || tfPlayers.getText().trim().isEmpty()
                || Integer.valueOf(tfPlayers.getText()) < slPlayers.getMin()
                || Integer.valueOf(tfPlayers.getText()) > slPlayers.getMax()
        );
    }

}
