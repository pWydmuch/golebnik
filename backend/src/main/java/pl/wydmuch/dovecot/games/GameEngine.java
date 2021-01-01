package pl.wydmuch.dovecot.games;


public interface GameEngine<T> {
    boolean isGameEnded();
    void makeMove(Move move);
    T getState(int nextTurnPlayerNumber);
    boolean firstMoveWasMade();
}
