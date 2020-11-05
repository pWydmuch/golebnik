package pl.wydmuch.dovecot.games.tictactoe;
import pl.wydmuch.dovecot.games.tictactoe.Field.FieldContent;

public class Move {

    private int row;
    private int column;
    private FieldContent playerSign;

    public Move(int row, int column, FieldContent playerSign) {
        this.row = row;
        this.column = column;
        this.playerSign = playerSign;
    }

    public Move() {
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
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
