package pl.wydmuch.dovecot.games;

import pl.wydmuch.dovecot.games.connect4.Connect4GameManagerEngine;
import pl.wydmuch.dovecot.games.tictactoe.TicTacToeGameManager;


public class GameManagerFactory {
    public static GameManager createGame(String gameName) {
        switch (gameName) {
            case "TicTacToe":
                return new TicTacToeGameManager();
//            case "Connect4":
//                return new Connect4GameManagerEngine();
            default:
                throw new RuntimeException("There is no such a game as: " + gameName);
        }
    }
}
