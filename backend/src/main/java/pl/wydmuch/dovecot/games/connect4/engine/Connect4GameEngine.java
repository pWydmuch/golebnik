package pl.wydmuch.dovecot.games.connect4.engine;

import pl.wydmuch.dovecot.games.GameEngine;
import pl.wydmuch.dovecot.websocket.gameroom.game.api.GameState;
import pl.wydmuch.dovecot.websocket.gameroom.game.api.Move;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Connect4GameEngine implements GameEngine {
    private static final int CONSECUTIVE_SAME_SIGNS_TO_WIN = 4;
    private Field[][] board;
    private static final int ROW_NR = 6;
    private static final int COLUMN_NR = 7;
    private FieldContent lastAddedSign = null;

    private int lastRow;
    private int lastColumn= -1;

    public Connect4GameEngine() {
        board = new Field[ROW_NR][COLUMN_NR];
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                board[row][column] = new Field();
            }
        }
    }

    public void makeMove(Move move) {
        Connect4Move connect4Move = (Connect4Move) move;
        checkIfMoveIsInvalid(connect4Move);
        int column = connect4Move.getColumn();
        FieldContent playerSign = connect4Move.getPlayerSign();
        checkIfColumnIsNotFull(column);
        int lowestEmptyCell = getLowestEmptyCellIndex(column);
        board[lowestEmptyCell][column].setFieldContent(playerSign);
        lastRow = lowestEmptyCell;
        lastColumn = column;
        lastAddedSign = playerSign;
    }

    public boolean isGameEnded(){
       return isGameWon() || isDraw();
    }

    public boolean isDraw() {
        return boardIsFull() && !isGameWon();
    }


    public GameState getState() {
        Connect4GameState connect4GameState = new Connect4GameState();
        connect4GameState.setBoard(board);
        connect4GameState.setIsWinner(isGameWon());
        connect4GameState.setIsDraw(isDraw());
        return connect4GameState;
    }


    public boolean firstMoveWasMade() {
        return lastAddedSign != null;
    }

    private void checkIfMoveIsInvalid(Connect4Move connect4Move) {
        int column = connect4Move.getColumn();
        FieldContent playerSign = connect4Move.getPlayerSign();
        int row = getLowestEmptyCellIndex(column);
        FieldContent fieldContent = board[row][column].getFieldContent();

        if (isGameEnded()) throw new GameAlreadyEndedException("Game is already ended");
        if (playerSign == lastAddedSign) throw new RepeatedSignException("It's not your turn");
        if (playerSign == FieldContent.EMPTY) throw new EmptyFieldSettingException("You can't set empty sign");
        if (fieldContent != FieldContent.EMPTY) throw new FieldAlreadyTakenException("This field is already taken");
    }

    private void checkIfColumnIsNotFull(int column) {
        for (int i = ROW_NR - 1; i >= 0; i--) {
            if (board[i][column].getFieldContent() == FieldContent.EMPTY) return;
        }
        throw new RuntimeException("This column is already full");
    }

    private int getLowestEmptyCellIndex(int column) {
        for (int i = ROW_NR - 1; i >= 0; i--) {
            if (board[i][column].getFieldContent() == FieldContent.EMPTY) return i;
        }
        return -1;
    }

    private boolean isGameWon() {
        if (lastColumn == -1) {
            return false;
        }
        FieldContent playerSign = board[lastRow][lastColumn].getFieldContent();
        return isLineWinning(getLastRow(),playerSign) ||
                isLineWinning(getLastColumn(),playerSign) ||
                isLineWinning(getLastAscDiagonal(),playerSign)||
                isLineWinning(getLastDescDiagonal(),playerSign);
    }


    private boolean isLineWinning(List<Field> line, FieldContent playerSign){
        List<Field> consecutiveSameSigns = new ArrayList<>();
        for (Field field : line) {
            if (field.getFieldContent() == playerSign){
                consecutiveSameSigns.add(field);
            }else{
                consecutiveSameSigns.clear();
            }
        }
        if (consecutiveSameSigns.size()== CONSECUTIVE_SAME_SIGNS_TO_WIN){
            markWinningSigns(consecutiveSameSigns);
            return true;
        }
        return false;
    }

    private void markWinningSigns(List<Field> winningSigns) {
        winningSigns.forEach(sign -> sign.setInWinningLine(true));
    }

    public FieldContent getWinner() {
        if (isGameWon()) return board[lastRow][lastColumn].getFieldContent();
        else return FieldContent.EMPTY;
    }

    public int getLastColumnNumber() {
        return lastColumn;
    }

    private List<Field> getLastColumn() {
        List<Field> columnFields = new ArrayList<>();
        for (int i = 0; i < ROW_NR; i++) {
            columnFields.add(board[i][lastColumn]);
        }
        return columnFields;
    }

    private List<Field> getLastRow() {
        return Arrays.stream(board[lastRow])
                .collect(Collectors.toList());
    }

    private List<Field> getLastAscDiagonal() {
        return getLastDiagonal(lastColumn + lastRow);
    }

    private List<Field> getLastDescDiagonal() {
        return getLastDiagonal(lastColumn - lastRow);
    }

    private List<Field> getLastDiagonal(int lastColRowFactor) {
        List<Field> diagonalFields = new ArrayList<>();
        for (int rowNr = 0; rowNr < ROW_NR; rowNr++) {
            int colNr = lastColRowFactor - rowNr;

            if (0 <= colNr && colNr < COLUMN_NR) {
                diagonalFields.add(board[rowNr][colNr]);
            }
        }
        return diagonalFields;
    }



    public boolean boardIsFull() {
        return Stream.of(board)
                .map(Arrays::asList)
                .flatMap(List::stream)
                .noneMatch(field -> field.getFieldContent() == FieldContent.EMPTY);
    }

    static class FieldAlreadyTakenException extends RuntimeException {
        FieldAlreadyTakenException() {
        }

        FieldAlreadyTakenException(String message) {
            super(message);
        }
    }

    static class RepeatedSignException extends RuntimeException {
        RepeatedSignException() {
        }

        RepeatedSignException(String message) {
            super(message);
        }
    }

    static class EmptyFieldSettingException extends RuntimeException {
        EmptyFieldSettingException() {
        }

        EmptyFieldSettingException(String message) {
            super(message);
        }
    }

    static class GameAlreadyEndedException extends RuntimeException {
        GameAlreadyEndedException() {
        }

        GameAlreadyEndedException(String message) {
            super(message);
        }
    }

}
