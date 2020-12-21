package pl.wydmuch.dovecot.games;



public interface Game {
    GameState getGameState();
    void makeMove(Move move, String playerName);
    boolean firstMoveWasMade();
}
