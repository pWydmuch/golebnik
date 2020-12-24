package pl.wydmuch.dovecot.games;

public interface AbstractGameFactory {
    AbstractPlayer createPlayer(String playerName, int playerNumber);
    GameEngine createGameEngine();
}
