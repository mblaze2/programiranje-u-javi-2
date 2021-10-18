/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.repository;

import hr.algebra.models.Player;
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
