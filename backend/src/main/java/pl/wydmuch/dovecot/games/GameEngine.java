package pl.wydmuch.dovecot.games;

import pl.wydmuch.dovecot.websocket.gameroom.game.api.RoomActivityState;

public interface GameEngine {
    boolean isGameEnded();
    void makeMove(Move move);
    RoomActivityState getState();
    boolean firstMoveWasMade();
}
