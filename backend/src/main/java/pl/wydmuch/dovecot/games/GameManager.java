package pl.wydmuch.dovecot.games;



public interface GameManager {
    void createNewGame();
    void resetGame();
    boolean isGameStarted();
    int getPlayersNumber();
    GameState getGameState();
    void makeMove(Move move, String playerName);
    void addPlayer(String playerName, int playerNumber);
    void removePlayer(String playerName);
}
