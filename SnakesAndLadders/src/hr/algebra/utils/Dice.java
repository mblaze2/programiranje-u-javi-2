/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.utils;
import java.util.Random;

/**
 *
 * @author Marijo
 */
public final class Dice {

    /**
     * Number of sides the dice have
     */
    private static final int SIDES = 6; 
    
    public static int roll(){
        Random  r = new Random();
        return r.nextInt(SIDES)+1;
    }
}
