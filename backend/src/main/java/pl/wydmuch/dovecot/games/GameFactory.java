package pl.wydmuch.dovecot.games;

import pl.wydmuch.dovecot.games.connect4.Connect4GameEngine;
import pl.wydmuch.dovecot.games.tictactoe.engine.TicTacToeGameEngine;

public class GameFactory {
    public static Game createGame(String gameName) {
        switch (gameName) {
            case "TicTacToeGameEngine":
                return new TicTacToeGameEngine();
            case "Connect4":
                return new Connect4GameEngine();
            default:
                throw new RuntimeException("There is no such a game as: " + gameName);
        }
    }
}
