package pl.wydmuch.dovecot.games.tictactoe.engine;
import pl.wydmuch.dovecot.games.GameState;
import pl.wydmuch.dovecot.games.tictactoe.engine.Field;

public class TicTacToeGameState implements GameState {
    private Field[][] board;

    private boolean isWinner;
    private boolean isDraw;

    public Field[][] getBoard() {
        return board;
    }

    public void setBoard(Field[][] board) {
        this.board = board;
    }

    public boolean getIsWinner() {
        return isWinner;
    }

    public void setIsWinner(boolean winner) {
        isWinner = winner;
    }

    public boolean getIsDraw() {
        return isDraw;
    }

    public void setIsDraw(boolean draw) {
        isDraw = draw;
    }

}
