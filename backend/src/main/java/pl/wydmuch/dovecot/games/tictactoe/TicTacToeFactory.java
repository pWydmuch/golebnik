package pl.wydmuch.dovecot.games.tictactoe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.wydmuch.dovecot.games.GameFactory;
import pl.wydmuch.dovecot.games.AbstractPlayer;
import pl.wydmuch.dovecot.games.GameEngine;
import pl.wydmuch.dovecot.games.Move;
import pl.wydmuch.dovecot.games.tictactoe.engine.TicTacToeGameEngine;
import pl.wydmuch.dovecot.games.tictactoe.engine.TicTacToeMove;

public class TicTacToeFactory implements GameFactory {
    @Override
    public AbstractPlayer createPlayer(String playerName, int playerNumber) {
        return new TicTacToePlayer(playerName, playerNumber);
    }

    @Override
    public GameEngine createGameEngine() {
        return new TicTacToeGameEngine();
    }

    @Override
    public Move parseGameMove(String gameMove) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(gameMove, TicTacToeMove.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getGameName() {
        return "TicTacToe";
    }
}
