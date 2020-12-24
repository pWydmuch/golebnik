package pl.wydmuch.dovecot.games.connect4;

import pl.wydmuch.dovecot.games.AbstractGameFactory;
import pl.wydmuch.dovecot.games.AbstractPlayer;
import pl.wydmuch.dovecot.games.GameEngine;
import pl.wydmuch.dovecot.games.connect4.Connect4Player;
import pl.wydmuch.dovecot.games.connect4.engine.Connect4GameEngine;

public class Connect4Factory implements AbstractGameFactory {
    @Override
    public AbstractPlayer createPlayer(String playerName, int playerNumber) {
        return new Connect4Player(playerName,playerNumber);
    }

    @Override
    public GameEngine createGameEngine() {
        return new Connect4GameEngine();
    }
}
