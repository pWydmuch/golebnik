package pl.wydmuch.dovecot.games;

import pl.wydmuch.dovecot.websocket.gameroom.game.api.GameManager;


public class GameManagerFactory {
    public static GameManager createGame(String gameName) {
        if (!gameName.equals("TicTacToe") && !gameName.equals("Connect4"))
            throw new RuntimeException("There is no such a game as: " + gameName);
        return new GenericGameManager(gameName);
    }
}
