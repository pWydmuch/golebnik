package pl.wydmuch.dovecot.games;

import pl.wydmuch.dovecot.game.api.RoomActivityParticipant;

public abstract class AbstractPlayer extends RoomActivityParticipant {

    public AbstractPlayer(String playerName, int playerNumber) {
        super(playerName, playerNumber);
    }

    public AbstractPlayer() {
    }

    protected abstract void makeMovePlayerSpecific(Move move);



}
