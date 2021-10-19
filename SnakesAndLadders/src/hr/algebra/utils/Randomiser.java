/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.utils;

import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 *
 * @author Marijo
 */

public final class Randomiser {

    /**
     * Number of sides the dice have
     */
    private static final int SIDES = 6; 
    
    public static int roll(){
        Random  r = new Random();
        return r.nextInt(SIDES)+1;
    }
    
    public static Paint paint(){
        Random rand = new Random();
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);
        return Paint.valueOf(String.format("#%02x%02x%02x", r, g, b));
    }
}