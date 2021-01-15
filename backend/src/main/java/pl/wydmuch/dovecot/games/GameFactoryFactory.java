package pl.wydmuch.dovecot.games;

import pl.wydmuch.dovecot.games.connect4.Connect4Factory;
import pl.wydmuch.dovecot.games.tictactoe.TicTacToeFactory;

public class GameFactoryFactory {

    static GameFactory createGameFactory(String gameName){
        switch (gameName){
            case "TicTacToe":
                return new TicTacToeFactory();
            case "Connect4":
                return new Connect4Factory();
            default:
                throw new RuntimeException("There is no such a game as: " + gameName);
        }
        }

}
