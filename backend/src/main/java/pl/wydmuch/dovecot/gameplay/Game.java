package pl.wydmuch.dovecot.gameplay;

import pl.wydmuch.dovecot.games.tictactoe.GameDto;
import pl.wydmuch.dovecot.games.tictactoe.Move;

import java.util.Set;

public abstract class Game {
    protected String roomId;
    protected Set<Player> players;
    protected int numberOfNeededPlayers;
    public boolean areAllPlayersPresent(){
        return players.size() == numberOfNeededPlayers;
    };
    public abstract void makeMove(Move move);
    //TODO trzeba jakos wymyslic, zeby to bylo bardziej generyczne GameState
    public abstract GameDto getState();

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
