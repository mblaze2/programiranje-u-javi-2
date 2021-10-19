/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.repository;

import hr.algebra.models.Ladder;
import hr.algebra.models.Player;
import hr.algebra.models.Snake;
import java.awt.Point;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Marijo
 */
public class Repository {
    
    private Repository() {		
    }
    
    private static final ObservableList<Player> PLAYERS = FXCollections.observableArrayList();
    public static final ObservableList<Ladder> LADDERS = FXCollections.observableArrayList(
        new Ladder(new Point(6,9), new Point(7,7)),
        new Ladder(new Point(4,7), new Point(4,6)),
        new Ladder(new Point(1,7), new Point(0,2)),
        new Ladder(new Point(1,2), new Point(1,0)),
        new Ladder(new Point(4,3), new Point(3,1)),
        new Ladder(new Point(5,4), new Point(6,0)),
        new Ladder(new Point(8,5), new Point(8,0))
    );

    public static final ObservableList<Snake> SNAKES = FXCollections.observableArrayList(
        new Snake(new Point(1,8), new Point(2,9)),        
        new Snake(new Point(3,6), new Point(4,9)),
        new Snake(new Point(7,6), new Point(8,9)),
        new Snake(new Point(5,5), new Point(6,8)),
        new Snake(new Point(0,1), new Point(3,8)),
        new Snake(new Point(1,4), new Point(0,6)),
        new Snake(new Point(7,2), new Point(0,9)),
        new Snake(new Point(7,0), new Point(9,3)),
        new Snake(new Point(4,0), new Point(5,3)),
        new Snake(new Point(2,0), new Point(6,6))
    );

    public static ObservableList<Player> getPlayers() {
        return PLAYERS;
    }
        
    public static Player getPlayer(String nickname) {
        return (Player)PLAYERS.stream().filter(player -> (player.getNickname() == null ? nickname == null : player.getNickname().equals(nickname))).findFirst().orElse(null);
    }
    
    public static void addPlayer(Player player) {
        PLAYERS.add(player);
    }
}
