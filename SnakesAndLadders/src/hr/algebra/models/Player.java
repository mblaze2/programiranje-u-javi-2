/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.models;

import java.awt.Point;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 *
 * @author Marijo
 */
public class Player implements Externalizable {
    
    private static final long serialVersionUID = 5L;

    private static final Point DEFAULT_LOCATION = new Point(0, 9);

    private String nickname;
    private int score;
    private Circle figure;
    private Paint paint;
    private Point location;
    
    public Player() {
    }
    
    public Player(String n, Circle c, Paint p) {
        this.nickname = n;
        this.figure = c;
        this.paint = p;
        this.location = DEFAULT_LOCATION;
        this.score = 0;
        figure.setFill(p);
    }

    public String getNickname() {
        return nickname;
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

    public boolean getWin() {
        return location.equals(new Point());
    }

    public void reset() {
        location = new Point(0, 9);
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

    @Override
    public String toString() {
        return "Player {" + "nickname: " + nickname + ", score: "+ score + ", location: " + location + "}"; 
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(nickname);
        out.writeInt(score);
        out.writeObject(location);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        nickname = in.readUTF();
        score = in.readInt();
        location = (Point)in.readObject();
    }

}
