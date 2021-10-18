/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.models;

import java.awt.Point;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 *
 * @author Marijo
 */
public class Player {
    
    private final Circle figure;
    private final Paint paint;
    private Point location;
    private final boolean win;
    
    public Player(Circle c, Paint p) {
        this.figure = c;
        this.win = false;
        this.location = new Point(0,9);
        this.paint = p;
        figure.setFill(p);
    }

    public Paint getPaint() {
        return paint;
    }

    public Point getLocation() {
        return location;
    }
    
    public Circle getFigure() {
        return figure;
    }
        
    public boolean getWin(){
        return location.equals(new Point());
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
