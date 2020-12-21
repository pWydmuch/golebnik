package pl.wydmuch.dovecot.games.tictactoe;

import pl.wydmuch.dovecot.games.Player;
import pl.wydmuch.dovecot.games.tictactoe.engine.FieldContent;

public class TicTacToePlayer extends Player {
    private FieldContent playerSign;
    public FieldContent getPlayerSign() {
        return playerSign;
    }
    public void setPlayerSign(FieldContent playerSign) {
        this.playerSign = playerSign;
    }
}
