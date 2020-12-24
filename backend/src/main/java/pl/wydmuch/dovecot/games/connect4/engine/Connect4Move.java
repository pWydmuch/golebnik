package pl.wydmuch.dovecot.games.connect4.engine;

import pl.wydmuch.dovecot.websocket.gameroom.game.api.Move;

public class Connect4Move implements Move {
    private int column;
    private FieldContent playerSign;

    public Connect4Move(int row, int column, FieldContent playerSign) {
        this.column = column;
        this.playerSign = playerSign;
    }

    public Connect4Move() {
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public FieldContent getPlayerSign() {
        return playerSign;
    }

    public void setPlayerSign(FieldContent playerSign) {
        this.playerSign = playerSign;
    }
}
