/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.models;

import java.awt.Point;
import javafx.scene.paint.Paint;

/**
 *
 * @author Marijo
 */
public class Player {
    Paint paint;
    Point location = new Point(0,9);
    boolean win = false;
    
    public Player(Paint color) {
        this.paint = color;
    }

    public Paint getColor() {
        return paint;
    }

    public Point getLocation() {
        return location;
    }
    
    public boolean getWin(){
        if(location.equals(new Point())) return true;
        else return false;
    }
    
    public void reset(){
        location = new Point(0,9);
    }

    public void setLocation(Point location) {
        this.location = location;
    }
    
    public int getScore(){
        boolean rollRight = location.getLocation().y % 2 != 0;
        int columnScore = (9 - location.getLocation().y) * 10;
        int rowScore = rollRight ? (location.getLocation().x + 1) : (10 - location.getLocation().x);
        return columnScore + rowScore;
    }

    @Override
    public String toString() {
        return "Player{" + "location=" + location + '}';
    }
    
    
}
