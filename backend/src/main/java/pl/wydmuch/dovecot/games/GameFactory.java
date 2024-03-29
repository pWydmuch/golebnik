package pl.wydmuch.dovecot.games;

public interface GameFactory {
    AbstractPlayer createPlayer(String playerName, int playerNumber);
    GameEngine createGameEngine();
    Move parseGameMove(String gameMove);
    String getGameName();
}
