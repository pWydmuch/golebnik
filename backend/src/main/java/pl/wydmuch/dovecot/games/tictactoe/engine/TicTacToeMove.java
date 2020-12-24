package pl.wydmuch.dovecot.games.tictactoe.engine;


import pl.wydmuch.dovecot.websocket.gameroom.game.api.Move;

public class TicTacToeMove implements Move {

    private int row;
    private int column;
    private FieldContent playerSign;

    public TicTacToeMove(int row, int column, FieldContent playerSign) {
        this.row = row;
        this.column = column;
        this.playerSign = playerSign;
    }

    public TicTacToeMove() {
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
