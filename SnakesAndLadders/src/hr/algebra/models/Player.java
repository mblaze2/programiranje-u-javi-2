/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.models;

import java.awt.Point;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 *
 * @author Marijo
 */
public class Player {

    private static final Point DEFAULT_LOCATION = new Point(0, 9);

    private final StringProperty nickname;
    private final IntegerProperty score;
    private final ObjectProperty<Circle> figure;
    private final ObjectProperty<Paint> paint;
    private final ObjectProperty<Point> location;

    public Player(String n, Circle c, Paint p) {
        this.nickname = new SimpleStringProperty(n);
        this.figure = new SimpleObjectProperty<>(c);
        this.paint = new SimpleObjectProperty<>(p);
        this.location = new SimpleObjectProperty<>(DEFAULT_LOCATION);
        this.score = new SimpleIntegerProperty(0);
        figure.get().setFill(p);
    }

    public String getNickname() {
        return nickname.get();
    }

    public Paint getPaint() {
        return paint.get();
    }

    public Point getLocation() {
        return location.get();
    }

    public Circle getFigure() {
        return figure.get();
    }

    public boolean getWin() {
        return location.get().equals(new Point());
    }

    public void reset() {
        location.set(new Point(0, 9));
    }

    public void setLocation(Point location) {
        this.location.set(location);
    }

    public void setScore(int score) {
        this.score.set(score);
    }

    public int getScore() {
        return this.score.get();
    }

    @Override
    public String toString() {
        return "Player {" + "location=" + location + '}';
    }

}
