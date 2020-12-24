package pl.wydmuch.dovecot.games;

import pl.wydmuch.dovecot.websocket.gameroom.game.api.Move;
import pl.wydmuch.dovecot.websocket.gameroom.game.api.Player;

public abstract class AbstractPlayer extends Player {

    public AbstractPlayer(String playerName, int playerNumber) {
        super(playerName, playerNumber);
    }

    public AbstractPlayer() {
    }

    protected abstract void makeMovePlayerSpecific(Move move);



}
