package pl.wydmuch.dovecot.games;

import pl.wydmuch.dovecot.activity.ActivityManager;
import pl.wydmuch.dovecot.activity.ActivityManagerFactory;


public class GameManagerFactory implements ActivityManagerFactory {
    public ActivityManager createGame(String gameName) {
        if (!gameName.equals("TicTacToe") && !gameName.equals("Connect4"))
            throw new RuntimeException("There is no such a game as: " + gameName);
        return new GenericGameManager(gameName);
    }
}
