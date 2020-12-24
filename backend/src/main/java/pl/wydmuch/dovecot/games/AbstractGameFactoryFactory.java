package pl.wydmuch.dovecot.games;

import pl.wydmuch.dovecot.games.connect4.Connect4Factory;
import pl.wydmuch.dovecot.games.connect4.engine.Connect4GameEngine;
import pl.wydmuch.dovecot.games.tictactoe.TicTacToeFactory;
import pl.wydmuch.dovecot.games.tictactoe.engine.TicTacToeGameEngine;

public class AbstractGameFactoryFactory {

    static AbstractGameFactory createGameFactory(String gameName){
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
