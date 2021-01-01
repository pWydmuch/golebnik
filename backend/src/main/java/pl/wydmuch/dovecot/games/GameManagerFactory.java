package pl.wydmuch.dovecot.games;

import pl.wydmuch.dovecot.game.api.RoomActivityManager;
import pl.wydmuch.dovecot.game.api.RoomActivityManagerFactory;


public class GameManagerFactory implements RoomActivityManagerFactory {
    public  RoomActivityManager createGame(String gameName) {
        if (!gameName.equals("TicTacToe") && !gameName.equals("Connect4"))
            throw new RuntimeException("There is no such a game as: " + gameName);
        return new GenericGameManager(gameName);
    }
}
