package pl.wydmuch.dovecot.games.tictactoe.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TicTacToeGameEngineTest {

    private TicTacToeGameEngine ticTacToe;

    @BeforeEach
    void setUp() {
        ticTacToe = new TicTacToeGameEngine();
    }

    @Test
    void constructor_shouldFillBoardOnlyWithEmptyField_whenCalled() {
        Field[][] board = ticTacToe.getState(0).getBoard();
        List<Field> fields = flattenBoard(board);
        assertThat(fields).extracting("fieldContent")
                .allMatch(f -> f == FieldContent.EMPTY);
    }

    @Test
    void makeMove_shouldSetBoardFieldWithSign_whenThatSignPassedAsParameter() {
        int row = 1;
        int column = 1;
        FieldContent sign = FieldContent.O;

        ticTacToe.makeMove(new TicTacToeMove(row, column, sign));
        Field[][] board = ticTacToe.getState(0).getBoard();
        assertThat(board[row][column]).extracting("fieldContent").isEqualTo(sign);
    }

    @Test
    void makeMove_shouldThrowEmptyFieldSettingException_whenSettingEmptyField() {
        assertThrows(TicTacToeGameEngine.EmptyFieldSettingException.class,
                () -> ticTacToe.makeMove(new TicTacToeMove(1, 1, FieldContent.EMPTY)));
    }

    @Test
    void makeMove_shouldThrowRepeatedSignException_whenSetTwoSameSignsInRow() {
        ticTacToe.makeMove(new TicTacToeMove(1, 1, FieldContent.O));
        assertThrows(TicTacToeGameEngine.RepeatedSignException.class,
                () -> ticTacToe.makeMove(new TicTacToeMove(2, 2, FieldContent.O)));
    }

    @Test
    void isDraw_shouldReturnTrue_whenWholeBoardFilledAndNoLineHasSameSign() {
        ticTacToe.makeMove(new TicTacToeMove(0, 0, FieldContent.O));
        ticTacToe.makeMove(new TicTacToeMove(0, 2, FieldContent.X));
        ticTacToe.makeMove(new TicTacToeMove(0, 1, FieldContent.O));

        ticTacToe.makeMove(new TicTacToeMove(1, 0, FieldContent.X));
        ticTacToe.makeMove(new TicTacToeMove(1, 2, FieldContent.O));
        ticTacToe.makeMove(new TicTacToeMove(1, 1, FieldContent.X));

        ticTacToe.makeMove(new TicTacToeMove(2, 0, FieldContent.O));
        ticTacToe.makeMove(new TicTacToeMove(2, 1, FieldContent.X));
        ticTacToe.makeMove(new TicTacToeMove(2, 2, FieldContent.O));

        boolean isDraw = ticTacToe.getState(0).getIsDraw();

        assertThat(isDraw).isTrue();
    }

    @Test
    void isDraw_shouldReturnFalse_whenLineHasSameSign() {
        ticTacToe.makeMove(new TicTacToeMove(0, 0, FieldContent.O));
        ticTacToe.makeMove(new TicTacToeMove(1, 0, FieldContent.X));
        ticTacToe.makeMove(new TicTacToeMove(0, 1, FieldContent.O));
        ticTacToe.makeMove(new TicTacToeMove(1, 1, FieldContent.X));
        ticTacToe.makeMove(new TicTacToeMove(0, 2, FieldContent.O));

        boolean isDraw = ticTacToe.getState(0).getIsDraw();

        assertThat(isDraw).isFalse();
    }

    @Test
    void isDraw_shouldReturnFalse_whenThereIsEmptyField() {
        ticTacToe.makeMove(new TicTacToeMove(0, 0, FieldContent.O));
        ticTacToe.makeMove(new TicTacToeMove(0, 2, FieldContent.X));
        ticTacToe.makeMove(new TicTacToeMove(0, 1, FieldContent.O));

        ticTacToe.makeMove(new TicTacToeMove(1, 0, FieldContent.X));
        ticTacToe.makeMove(new TicTacToeMove(1, 2, FieldContent.O));
        ticTacToe.makeMove(new TicTacToeMove(1, 1, FieldContent.X));

        ticTacToe.makeMove(new TicTacToeMove(2, 0, FieldContent.O));
        ticTacToe.makeMove(new TicTacToeMove(2, 1, FieldContent.X));

        boolean isDraw = ticTacToe.getState(0).getIsDraw();

        assertThat(isDraw).isFalse();
    }

    @Test
    void isGameWon_shouldReturnFalse_whenAnyOfLinesAreEmpty() {
        boolean isGameWon = ticTacToe.getState(0).getIsWinner();

        assertThat(isGameWon).isFalse();
    }

    @Test
    void isGameWon_shouldReturnTrue_whenWholeColumnHasSameSign() {
        ticTacToe.makeMove(new TicTacToeMove(0, 1, FieldContent.O));
        ticTacToe.makeMove(new TicTacToeMove(0, 0, FieldContent.X));
        ticTacToe.makeMove(new TicTacToeMove(1, 1, FieldContent.O));
        ticTacToe.makeMove(new TicTacToeMove(0, 2, FieldContent.X));
        ticTacToe.makeMove(new TicTacToeMove(2, 1, FieldContent.O));

        boolean isGameWon = ticTacToe.getState(0).getIsWinner();

        assertThat(isGameWon).isTrue();
    }

    @Test
    void isGameWon_shouldReturnTrue_whenWholeRowHasSameSign() {
        ticTacToe.makeMove(new TicTacToeMove(0, 0, FieldContent.X));
        ticTacToe.makeMove(new TicTacToeMove(1, 0, FieldContent.O));
        ticTacToe.makeMove(new TicTacToeMove(0, 1, FieldContent.X));
        ticTacToe.makeMove(new TicTacToeMove(1, 1, FieldContent.O));
        ticTacToe.makeMove(new TicTacToeMove(0, 2, FieldContent.X));

        boolean isGameWon = ticTacToe.getState(0).getIsWinner();

        assertThat(isGameWon).isTrue();
    }

    @Test
    void isGameWon_shouldReturnTrue_whenWholeSlashDiagonalHasSameSign() {
        ticTacToe.makeMove(new TicTacToeMove(0, 0, FieldContent.O));
        ticTacToe.makeMove(new TicTacToeMove(1, 0, FieldContent.X));
        ticTacToe.makeMove(new TicTacToeMove(1, 1, FieldContent.O));
        ticTacToe.makeMove(new TicTacToeMove(2, 1, FieldContent.X));
        ticTacToe.makeMove(new TicTacToeMove(2, 2, FieldContent.O));

        boolean isGameWon = ticTacToe.getState(0).getIsWinner();

        assertThat(isGameWon).isTrue();
    }

    @Test
    void isGameWon_shouldReturnTrue_whenWholeBackslashDiagonalHasSameSign() {
        ticTacToe.makeMove(new TicTacToeMove(0, 2, FieldContent.X));
        ticTacToe.makeMove(new TicTacToeMove(0, 0, FieldContent.O));
        ticTacToe.makeMove(new TicTacToeMove(1, 1, FieldContent.X));
        ticTacToe.makeMove(new TicTacToeMove(0, 1, FieldContent.O));
        ticTacToe.makeMove(new TicTacToeMove(2, 0, FieldContent.X));

        boolean isGameWon = ticTacToe.getState(0).getIsWinner();

        assertThat(isGameWon).isTrue();
    }


    private List<Field> flattenBoard(Field[][] board) {
        return Stream.of(board)
                .map(Arrays::asList)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}