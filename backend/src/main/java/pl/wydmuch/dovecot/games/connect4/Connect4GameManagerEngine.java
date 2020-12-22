package pl.wydmuch.dovecot.games.connect4;

import org.springframework.stereotype.Service;
import pl.wydmuch.dovecot.games.GameManager;
import pl.wydmuch.dovecot.games.GameState;
import pl.wydmuch.dovecot.games.Move;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class Connect4GameManagerEngine  {
    private Field[][] board;
    private static final int ROW_NR = 7;
    private static final int COLUMN_NR = 6;
    private FieldContent lastAddedSign = null;

    private int lastRow;
    private int lastColumn;


    public Connect4GameManagerEngine() {
        board = new Field[ROW_NR][COLUMN_NR];
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                board[row][column] = new Field();
            }
        }
    }

    public Field[][] getBoard() {
        return board;
    }


    public void makeMove(Move move) {
        Connect4Move connect4Move = (Connect4Move) move;
        int column = connect4Move.getColumn();
        FieldContent playerSign = connect4Move.getPlayerSign();
        checkIfColumnIsNotFull(column);
        int lowestEmptyCell = getLowestEmptyCellIndex(column);
        board[lowestEmptyCell][column].setFieldContent(playerSign);
        lastRow = lowestEmptyCell;
        lastColumn = column;
        lastAddedSign = playerSign;
    }


    public GameState getState() {
        return null;
    }


    public boolean firstMoveWasMade() {
        return lastAddedSign != null;
    }

    private void checkIfColumnIsNotFull(int column) {
        for (int i = COLUMN_NR - 1; i >= 0; i--) {
            if (board[i][column].getFieldContent() == FieldContent.EMPTY) return;
        }
        throw new RuntimeException("This column is already full");
    }

    private int getLowestEmptyCellIndex(int column) {
        for (int i = COLUMN_NR - 1; i >= 0; i--) {
            if (board[i][column].getFieldContent() == FieldContent.EMPTY) return i;
        }
        return -1;
    }

    private boolean isWinningPlay() {
        if (lastColumn == -1) {
//            System.err.println("No move has been made yet");
            return false;
        }

        String symbol = board[lastRow][lastColumn].getFieldContent().toString();
        // winning streak with the last play symbol
        String streak = String.format("%s%s%s%s", symbol, symbol, symbol, symbol);

        return getLastRow().contains(streak) ||
                getLastColumn().contains(streak) ||
                getLastAscDiagonal().contains(streak) ||
                getLastDescDiagonal().contains(streak);
    }

    public FieldContent getWinner() {
        if (isWinningPlay()) return board[lastRow][lastColumn].getFieldContent();
        else return FieldContent.EMPTY;
    }

    public int getLastColumnNumber() {
        return lastColumn;
    }

    private String getLastColumn() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < COLUMN_NR; i++) {
            stringBuilder.append(board[i][lastColumn].getFieldContent());
        }
        return stringBuilder.toString();
    }

    private String getLastRow() {
        return Arrays.stream(board[lastRow]).map(Field::getFieldContent)
                .map(FieldContent::toString)
                .collect(Collectors.joining(""));
    }

    private String getLastAscDiagonal() {
        return getLastDiagonal(lastColumn + lastRow);
    }

    private String getLastDescDiagonal() {
        return getLastDiagonal(lastColumn - lastRow);
    }

    private String getLastDiagonal(int lastColRowFactor) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int rowNr = 0; rowNr < COLUMN_NR; rowNr++) {
            int colNr = lastColRowFactor - rowNr;

            if (0 <= colNr && colNr < ROW_NR) {
                stringBuilder.append(board[rowNr][colNr].getFieldContent());
            }
        }
        return stringBuilder.toString();
    }

    public boolean isGameOver() {
        if (isWinningPlay()) return true;
//        if (checkIfGirdIsFull()) return true;
        return false;
    }

//    public boolean checkIfGirdIsFull() {
//        for (int i = 0; i < ROW_NR; i++) {
//            if (checkIfColumnIsNotFull(i)) return false;
//        }
//        return true;
//    }




//    public String[][] transform(){
//        String[][] boardStringArr = new String[COLUMN_NR][ROW_NR];
//        for(int i = 0; i< board.length; i++){
//            for(int j = 0; j< board[i].length; j++){
//                if (board[i][j]=='.') boardStringArr[i][j]="0";
//                if (board[i][j]== PLAYER_SIGN_X) boardStringArr[i][j]="black";
//                if (board[i][j]== PLAYER_SIGN_O) boardStringArr[i][j]="red";
//            }
//        }
//        return boardStringArr;
//    }


}
