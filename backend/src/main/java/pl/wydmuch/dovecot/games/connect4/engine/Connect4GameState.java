package pl.wydmuch.dovecot.games.connect4.engine;



public class Connect4GameState {
    private Field[][] board;

    private boolean isWinner;
    private boolean isDraw;
    private int nextTurnPlayerNumber;

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


    public int getNextTurnPlayerNumber() {
        return nextTurnPlayerNumber;
    }

    public void setNextTurnPlayerNumber(int nextTurnPlayerNumber) {
        this.nextTurnPlayerNumber = nextTurnPlayerNumber;
    }
}
