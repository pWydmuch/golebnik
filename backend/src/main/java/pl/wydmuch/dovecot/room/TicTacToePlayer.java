package pl.wydmuch.dovecot.room;

import pl.wydmuch.dovecot.games.tictactoe.Field;

public class TicTacToePlayer extends RoomUser {
    Field.FieldContent playerSign;

    public Field.FieldContent getPlayerSign() {
        return playerSign;
    }

    public void setPlayerSign(Field.FieldContent playerSign) {
        this.playerSign = playerSign;
    }
}
