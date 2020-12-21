package pl.wydmuch.dovecot.games.tictactoe;

import pl.wydmuch.dovecot.games.Game;
import pl.wydmuch.dovecot.games.GameState;
import pl.wydmuch.dovecot.games.Move;
import pl.wydmuch.dovecot.games.tictactoe.engine.TicTacToeGameEngine;
import pl.wydmuch.dovecot.games.tictactoe.engine.TicTacToeGameState;
import pl.wydmuch.dovecot.games.tictactoe.engine.TicTacToeMove;

import java.util.List;


public class TicTacToeGame implements Game {
    private List<TicTacToePlayer> players;
    private TicTacToeGameEngine engine;
    private int startingPlayerIndex;

    @Override
    public void makeMove(Move move, String playerName) {
        TicTacToeMove ticTacToeMove = (TicTacToeMove) move;
        TicTacToePlayer playerMakingMove = getPlayerWithName(playerName);
        ticTacToeMove.setPlayerSign(playerMakingMove.getPlayerSign());

        engine.makeMove(ticTacToeMove);
    }

    @Override
    public GameState getGameState(){
        return engine.getState();
    }

    private TicTacToePlayer getPlayerWithName(String playerName) {
        return players.stream().filter(p -> p.getPlayerName().equals(playerName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("There's no such a player"));
    }


    @Override
    public boolean firstMoveWasMade() {
        return false;
    }
}
