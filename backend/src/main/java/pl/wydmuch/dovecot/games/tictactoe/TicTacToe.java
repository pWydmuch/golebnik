package pl.wydmuch.dovecot.games.tictactoe;

import pl.wydmuch.dovecot.gameplay.Game;
import pl.wydmuch.dovecot.games.tictactoe.Field.FieldContent;

import java.util.*;
import java.util.stream.Stream;

public class TicTacToe extends Game {

    private Field[][] board;
    private FieldContent lastAddedSign = FieldContent.EMPTY;
    private List<FieldContent> availableSigns;
    private static final int ROW_NR = 3;
    private static final int COLUMN_NR = 3;

    public TicTacToe() {
        availableSigns = new ArrayList<>();
        availableSigns.add(FieldContent.O);
        availableSigns.add(FieldContent.X);
        initializeBoard();
    }

    @Override
    public GameDto getState() {
        return GameToDtoConverter.convertToDto(this);
    }

    @Override
    public void makeMove(Move move) {
        checkIfMoveIsInvalid(move);
        int row = move.getRow();
        int column = move.getColumn();
        FieldContent playerSign = move.getPlayerSign();
        board[row][column].setFieldContent(playerSign);
        lastAddedSign = playerSign;
    }

    public FieldContent getLastAddedSign() {
        return lastAddedSign;
    }

    public FieldContent getWinnerSign() {
        return isGameWon() ? lastAddedSign : null;
    }

    public boolean isGameEnded() {
        return isDraw() || isGameWon();
    }

    public boolean isDraw() {
        return boardIsFull() && !isGameWon();
    }

    public boolean isGameWon() {
        return isHorizontallyWon() || isVerticallyWon() || isDiagonallyWon();
    }

    public Field[][] getBoard() {
        return board;
    }

    private void initializeBoard() {
        board = new Field[ROW_NR][COLUMN_NR];
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                board[row][column] = new Field();
            }
        }
    }

    private boolean isHorizontallyWon() {
        Set<Boolean> rowsResults = new HashSet<>();
        for (int row = 0; row < board.length; row++)
            rowsResults.add(allFieldsInRowHaveSameSign(row));
        return rowsResults.contains(true);
    }

    private boolean allFieldsInRowHaveSameSign(int row) {
        return board[row][0].allHaveSameSign(board[row][1], board[row][2]);
    }

    private boolean isVerticallyWon() {
        Set<Boolean> columnsResults = new HashSet<>();
        for (int column = 0; column < board[0].length; column++)
            columnsResults.add(allFieldsInColumnHaveSameSign(column));
        return columnsResults.contains(true);
    }

    private boolean allFieldsInColumnHaveSameSign(int column) {
        return board[0][column].allHaveSameSign(board[1][column], board[2][column]);
    }

    private boolean isDiagonallyWon() {
        return isDiagonallySlashedWon() || isDiagonallyBackSlashedWon();
    }

    private boolean isDiagonallySlashedWon() {
        return board[0][0].allHaveSameSign(board[1][1], board[2][2]);
    }

    private boolean isDiagonallyBackSlashedWon() {
        return board[0][2].allHaveSameSign(board[1][1], board[2][0]);
    }

    private boolean boardIsFull() {
        return Stream.of(board)
                .map(Arrays::asList)
                .flatMap(List::stream)
                .noneMatch(field -> field.getFieldContent() == FieldContent.EMPTY);
    }

    private void checkIfMoveIsInvalid(Move move) {
        int row = move.getRow();
        int column = move.getColumn();
        FieldContent playerSign = move.getPlayerSign();
        FieldContent fieldContent = board[row][column].getFieldContent();

        if (isGameEnded()) throw new GameAlreadyEndedException("Game is already ended");
        if (playerSign == lastAddedSign) throw new RepeatedSignException("It's not your turn");
        if (playerSign == FieldContent.EMPTY) throw new EmptyFieldSettingException("You can't set empty sign");
        if (fieldContent != FieldContent.EMPTY) throw new FieldAlreadyTakenException("This field is already taken");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicTacToe other = (TicTacToe) o;

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (!Objects.equals(board[row][column], other.board[row][column])) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(board);
    }

    @Override
    public String toString() {
        return "TicTacToe{" +
                "board=" + Arrays.toString(board) +
                '}';
    }

    public List<FieldContent> getAvailableSigns() {
        return availableSigns;
    }

    public class FieldAlreadyTakenException extends RuntimeException {
        public FieldAlreadyTakenException() {
        }

        public FieldAlreadyTakenException(String message) {
            super(message);
        }
    }

    public class RepeatedSignException extends RuntimeException {
        public RepeatedSignException() {
        }

        public RepeatedSignException(String message) {
            super(message);
        }
    }

    public class EmptyFieldSettingException extends RuntimeException {
        public EmptyFieldSettingException() {
        }

        public EmptyFieldSettingException(String message) {
            super(message);
        }
    }

    public class GameAlreadyEndedException extends RuntimeException {
        public GameAlreadyEndedException() {
        }

        public GameAlreadyEndedException(String message) {
            super(message);
        }
    }
}
