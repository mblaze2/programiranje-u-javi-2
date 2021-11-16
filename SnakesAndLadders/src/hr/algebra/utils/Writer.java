/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 *
 * @author Marijo
 */
public class Writer {
    public static boolean write(String msg) {
         try (FileWriter htmlWriter = new FileWriter("dokumentacija.html")) {
            htmlWriter.write(msg);
            return true;
        } catch (IOException ex) {
             System.out.println(ex.getMessage());
             return false;
        }
    }
}
