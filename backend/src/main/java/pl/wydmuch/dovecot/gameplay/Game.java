package pl.wydmuch.dovecot.gameplay;

import pl.wydmuch.dovecot.games.tictactoe.engine.TicTacToeGameState;
import pl.wydmuch.dovecot.games.tictactoe.engine.TicTacToeMove;

import java.util.Set;

public abstract class Game {
    protected String roomId;
    protected Set<Player> players;
    protected int numberOfNeededPlayers;
    public boolean areAllPlayersPresent(){
        return players.size() == numberOfNeededPlayers;
    };
    public abstract void makeMove(TicTacToeMove ticTacToeMove);
    //TODO trzeba jakos wymyslic, zeby to bylo bardziej generyczne GameState
    public abstract TicTacToeGameState getState();

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
