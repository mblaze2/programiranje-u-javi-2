/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.models;

import java.awt.Point;

/**
 *
 * @author Marijo
 */
public class Ladder {

    private final Point startLocation;
    private final Point endLocation;

    public Ladder(Point startLocation, Point endLocation) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
    }

    public Point getStartLocation() {
        return startLocation;
    }

    public Point getEndLocation() {
        return endLocation;
    }
}
