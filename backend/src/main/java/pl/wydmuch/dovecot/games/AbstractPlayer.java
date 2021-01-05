package pl.wydmuch.dovecot.games;

import pl.wydmuch.dovecot.activity.ActivityParticipant;

public abstract class AbstractPlayer extends ActivityParticipant {

    public AbstractPlayer(String playerName, int playerNumber) {
        super(playerName, playerNumber);
    }

    public AbstractPlayer() {
    }

    protected abstract void makeMovePlayerSpecific(Move move);



}
