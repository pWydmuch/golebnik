package pl.wydmuch.golebnik.games.tictactoe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class TicTacToeTest {

    private TicTacToe ticTacToe;

    @BeforeEach
    void setUp() {
        ticTacToe = new TicTacToe();
    }

    @Test
    void constructor_shouldFillBoardOnlyWithEmptyField_whenCalled() {
        FieldContent[][] board = ticTacToe.getBoard();
        List<FieldContent> fields = flattenBoard(board);

        assertThat(fields).allMatch(f -> f == FieldContent.EMPTY);
    }

    @Test
    void setField_shouldSetBoardFieldWithSign_whenThatSignPassedAsParameter() {
        int row = 1;
        int column = 1;
        FieldContent sign = FieldContent.O;

        ticTacToe.setField(row, column, sign);
        FieldContent[][] board = ticTacToe.getBoard();

        assertThat(board[row][column]).isEqualTo(sign);
    }

    @Test
    void setField_shouldThrowEmptyFieldSettingException_whenSettingEmptyField() {
        assertThrows(TicTacToe.EmptyFieldSettingException.class, () -> ticTacToe.setField(1, 1, FieldContent.EMPTY));
    }

    @Test
    void setField_shouldThrowRepeatedSignException_whenSetTwoSameSignsInRow() {
        ticTacToe.setField(1, 1, FieldContent.O);
        assertThrows(TicTacToe.RepeatedSignException.class, () -> ticTacToe.setField(2, 2, FieldContent.O));
    }

    @Test
    void isDraw_shouldReturnTrue_whenWholeBoardFilledAndNoLineHasSameSign() {
        ticTacToe.setField(0, 0, FieldContent.O);
        ticTacToe.setField(0, 2, FieldContent.X);
        ticTacToe.setField(0, 1, FieldContent.O);

        ticTacToe.setField(1, 0, FieldContent.X);
        ticTacToe.setField(1, 2, FieldContent.O);
        ticTacToe.setField(1, 1, FieldContent.X);

        ticTacToe.setField(2, 0, FieldContent.O);
        ticTacToe.setField(2, 1, FieldContent.X);
        ticTacToe.setField(2, 2, FieldContent.O);

        boolean isDraw = ticTacToe.isDraw();

        assertThat(isDraw).isTrue();
    }

    @Test
    void isDraw_shouldReturnFalse_whenLineHasSameSign() {
        ticTacToe.setField(0, 0, FieldContent.O);
        ticTacToe.setField(1, 0, FieldContent.X);
        ticTacToe.setField(0, 1, FieldContent.O);
        ticTacToe.setField(1, 1, FieldContent.X);
        ticTacToe.setField(0, 2, FieldContent.O);

        boolean isDraw = ticTacToe.isDraw();

        assertThat(isDraw).isFalse();
    }

    @Test
    void isDraw_shouldReturnFalse_whenThereIsEmptyField() {
        ticTacToe.setField(0, 0, FieldContent.O);
        ticTacToe.setField(0, 2, FieldContent.X);
        ticTacToe.setField(0, 1, FieldContent.O);


        ticTacToe.setField(1, 0, FieldContent.X);
        ticTacToe.setField(1, 2, FieldContent.O);
        ticTacToe.setField(1, 1, FieldContent.X);


        ticTacToe.setField(2, 0, FieldContent.O);
        ticTacToe.setField(2, 1, FieldContent.X);

        boolean isDraw = ticTacToe.isDraw();

        assertThat(isDraw).isFalse();
    }

    @Test
    void isGameWon_shouldReturnFalse_whenAnyOfLinesAreEmpty() {
        boolean isGameWon = ticTacToe.isGameWon();

        assertThat(isGameWon).isFalse();
    }

    @Test
    void isGameWon_shouldReturnTrue_whenWholeColumnHasSameSign() {
        ticTacToe.setField(0, 1, FieldContent.O);
        ticTacToe.setField(0, 0, FieldContent.X);
        ticTacToe.setField(1, 1, FieldContent.O);
        ticTacToe.setField(0, 2, FieldContent.X);
        ticTacToe.setField(2, 1, FieldContent.O);

        boolean isGameWon = ticTacToe.isGameWon();

        assertThat(isGameWon).isTrue();
    }

    @Test
    void isGameWon_shouldReturnTrue_whenWholeRowHasSameSign() {
        ticTacToe.setField(0, 0, FieldContent.X);
        ticTacToe.setField(1, 0, FieldContent.O);
        ticTacToe.setField(0, 1, FieldContent.X);
        ticTacToe.setField(1, 1, FieldContent.O);
        ticTacToe.setField(0, 2, FieldContent.X);

        boolean isGameWon = ticTacToe.isGameWon();

        assertThat(isGameWon).isTrue();
    }

    @Test
    void isGameWon_shouldReturnTrue_whenWholeSlashDiagonalHasSameSign() {
        ticTacToe.setField(0, 0, FieldContent.O);
        ticTacToe.setField(1, 0, FieldContent.X);
        ticTacToe.setField(1, 1, FieldContent.O);
        ticTacToe.setField(2, 1, FieldContent.X);
        ticTacToe.setField(2, 2, FieldContent.O);

        boolean isGameWon = ticTacToe.isGameWon();

        assertThat(isGameWon).isTrue();
    }

    @Test
    void isGameWon_shouldReturnTrue_whenWholeBackslashDiagonalHasSameSign() {
        ticTacToe.setField(0, 2, FieldContent.X);
        ticTacToe.setField(0, 0, FieldContent.O);
        ticTacToe.setField(1, 1, FieldContent.X);
        ticTacToe.setField(0, 1, FieldContent.O);
        ticTacToe.setField(2, 0, FieldContent.X);

        boolean isGameWon = ticTacToe.isGameWon();

        assertThat(isGameWon).isTrue();
    }

    @Test
    void getWinnerSing_shouldReturnWinnerSign_whenWholeLineHasSameSign() {
        ticTacToe.setField(0, 0, FieldContent.X);
        ticTacToe.setField(2, 0, FieldContent.O);
        ticTacToe.setField(0, 1, FieldContent.X);
        ticTacToe.setField(1, 1, FieldContent.O);
        ticTacToe.setField(0, 2, FieldContent.X);

        FieldContent winnerSign = ticTacToe.getWinnerSign();

        assertThat(winnerSign).isEqualTo(FieldContent.X);
    }

    @Test
    void getWinnerSing_shouldReturnNull_whenNoLineHasSameSign() {
        ticTacToe.setField(0, 0, FieldContent.X);
        ticTacToe.setField(0, 2, FieldContent.O);
        ticTacToe.setField(0, 1, FieldContent.X);

        FieldContent winnerSign = ticTacToe.getWinnerSign();

        assertThat(winnerSign).isNull();
    }

    private List<FieldContent> flattenBoard(FieldContent[][] board) {
        return Stream.of(board)
                .map(Arrays::asList)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }


}