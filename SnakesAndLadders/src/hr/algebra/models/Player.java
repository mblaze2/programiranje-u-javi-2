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
    Point location;

    public Player(Paint color, Point location) {
        this.paint = color;
        this.location = location;
    }

    public Paint getColor() {
        return paint;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
    
    
}
