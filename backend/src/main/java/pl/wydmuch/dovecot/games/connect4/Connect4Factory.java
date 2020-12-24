package pl.wydmuch.dovecot.games.connect4;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.wydmuch.dovecot.games.AbstractGameFactory;
import pl.wydmuch.dovecot.games.AbstractPlayer;
import pl.wydmuch.dovecot.games.GameEngine;
import pl.wydmuch.dovecot.games.Move;
import pl.wydmuch.dovecot.games.connect4.engine.Connect4GameEngine;
import pl.wydmuch.dovecot.games.connect4.engine.Connect4Move;

public class Connect4Factory implements AbstractGameFactory {
    @Override
    public AbstractPlayer createPlayer(String playerName, int playerNumber) {
        return new Connect4Player(playerName,playerNumber);
    }

    @Override
    public GameEngine createGameEngine() {
        return new Connect4GameEngine();
    }

    @Override
    public Move parseGameMove(String gameMove) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(gameMove, Connect4Move.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getGameName() {
        return "Connect4";
    }
}
