/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controllers;

import hr.algebra.utils.Writer;
import hr.algebra.utils.fs;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Marijo
 */
public class SettingsController implements Initializable {

    @FXML
    private TextField tfROLL_DELAY;
    @FXML
    private MenuItem miDocumentation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleGenerateDocumentation(ActionEvent event) {
        
        
        String docs = null;
        try {
            docs = fs.build();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Uspješno spremanje dokumentacije");
            alert.setContentText("Datoteka \"dokumentacija.html\""
                    + " je uspješno generirana!");
            alert.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Writer.write(docs);
        
        
    }

    
}
