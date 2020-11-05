package pl.wydmuch.dovecot.room;

import pl.wydmuch.dovecot.gameplay.Game;
import pl.wydmuch.dovecot.games.tictactoe.TicTacToe;

import java.util.HashSet;
import java.util.Set;

public class Room {
  private String id;
  private TicTacToe game;
  private Set<RoomUser> players;
  private int neededPlayersNumber = 2;
  private RoomUser currentPlayer;

    public Room() {
        players = new HashSet<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public boolean allPlayersArePresent(){
        return players.size() == neededPlayersNumber;
    }

    public void addPlayer(RoomUser player){
        if (players.size()<neededPlayersNumber)
        players.add(player);
    }

    public void removePlayer(String playerSessionId){
        players.removeIf(p->p.getSessionId().equals(playerSessionId));
    }

    public void removePlayer(RoomUser player){
        players.remove(player);
    }

    public TicTacToe getGame() {
        return game;
    }

    public void setGame(TicTacToe game) {
        this.game = game;
    }

    public Set<RoomUser> getPlayers() {
        return players;
    }

    public void setPlayers(Set<RoomUser> players) {
        this.players = players;
    }

    public RoomUser getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(RoomUser currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
