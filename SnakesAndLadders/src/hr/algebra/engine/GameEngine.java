/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.engine;

import java.awt.Point;

/**
 *
 * @author Marijo
 */
public final class GameEngine {
    
    public static Point getNextPoint(Point oldLocation, int roll, int rowsCount) {
        Point newLocation = new Point(oldLocation);
        // Check if we move from left to right
        boolean rollRight = newLocation.y % 2 != 0;
        // Figure out the next X point based where we move
        final int nextXPoint = rollRight ? newLocation.x + roll : newLocation.x - roll;
        if (nextXPoint > (rowsCount - 1) && rollRight) {
            newLocation.y -= 1;
            // If we move to the next row we have to add the rest of points
            newLocation.x = (rowsCount - 1) - (nextXPoint - rowsCount);
        } else {
            if (nextXPoint < 0) {
                // Make sure we dont overshoot!
                if (newLocation.y != 0) {
                    newLocation.y -= 1;
                    // If we move to the next row we have to add the rest of points
                    newLocation.x = Math.abs(nextXPoint) - 1;
                }
            } else {
                // Figure out if we move to right or to the left
                if (rollRight) {
                    newLocation.x += roll;
                } else {
                    newLocation.x -= roll;
                }
            }
        }
        return newLocation;
    }
    
    public static int getScoreFromPoint(Point point){
        boolean rollRight = point.y % 2 != 0;
        int columnScore = (9 - point.y) * 10;
        int rowScore = rollRight ? (point.x + 1) : (10 - point.x);
        return columnScore + rowScore;
    }
}
