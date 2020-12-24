package pl.wydmuch.dovecot.games;

import pl.wydmuch.dovecot.websocket.gameroom.game.api.GameState;
import pl.wydmuch.dovecot.websocket.gameroom.game.api.Move;

public interface GameEngine {
    boolean isGameEnded();
    void makeMove(Move move);
    GameState getState();
    boolean firstMoveWasMade();
}
