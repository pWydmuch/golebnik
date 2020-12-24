package pl.wydmuch.dovecot.games.tictactoe;

import pl.wydmuch.dovecot.games.AbstractGameFactory;
import pl.wydmuch.dovecot.games.AbstractPlayer;
import pl.wydmuch.dovecot.games.GameEngine;
import pl.wydmuch.dovecot.games.tictactoe.TicTacToePlayer;
import pl.wydmuch.dovecot.games.tictactoe.engine.TicTacToeGameEngine;

public class TicTacToeFactory implements AbstractGameFactory {
    @Override
    public AbstractPlayer createPlayer(String playerName, int playerNumber) {
        return new TicTacToePlayer(playerName, playerNumber);
    }

    @Override
    public GameEngine createGameEngine() {
        return new TicTacToeGameEngine();
    }
}
