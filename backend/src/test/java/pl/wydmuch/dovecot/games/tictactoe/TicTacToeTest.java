//package pl.wydmuch.dovecot.games.tictactoe;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import pl.wydmuch.dovecot.games.tictactoe.engine.Field.FieldContent;
//
//class TicTacToeTest {
//
//    private TicTacToeGameEngine ticTacToe;
//
//    @BeforeEach
//    void setUp() {
//        ticTacToe = new TicTacToeGameEngine();
//    }
//
//    @Test
//    void constructor_shouldFillBoardOnlyWithEmptyField_whenCalled() {
//        Field[][] board = ticTacToe.getBoard();
//        List<FieldContent> fields = flattenBoard(board);
//
//        assertThat(fields).allMatch(f -> f == FieldContent.EMPTY);
//    }
//
//    @Test
//    void setField_shouldSetBoardFieldWithSign_whenThatSignPassedAsParameter() {
//        int row = 1;
//        int column = 1;
//        FieldContent sign = FieldContent.O;
//
//        ticTacToe.doAction(new TicTacToeMove(row, column, sign));
////        FieldContent[][] board = ticTacToe.getBoard();
//
//        assertThat(board[row][column]).isEqualTo(sign);
//    }
//
//    @Test
//    void setField_shouldThrowEmptyFieldSettingException_whenSettingEmptyField() {
//        assertThrows(TicTacToeGameEngine.EmptyFieldSettingException.class, () -> ticTacToe.doAction(new TicTacToeMove(1, 1, FieldContent.EMPTY)));
//    }
//
//    @Test
//    void setField_shouldThrowRepeatedSignException_whenSetTwoSameSignsInRow() {
//        ticTacToe.doAction(new TicTacToeMove(1, 1, FieldContent.O));
//        assertThrows(TicTacToeGameEngine.RepeatedSignException.class, () -> ticTacToe.doAction(new TicTacToeMove(2, 2, FieldContent.O)));
//    }
//
//    @Test
//    void isDraw_shouldReturnTrue_whenWholeBoardFilledAndNoLineHasSameSign() {
//        ticTacToe.doAction(new TicTacToeMove(0, 0, FieldContent.O));
//        ticTacToe.doAction(new TicTacToeMove(0, 2, FieldContent.X));
//        ticTacToe.doAction(new TicTacToeMove(0, 1, FieldContent.O));
//
//        ticTacToe.doAction(new TicTacToeMove(1, 0, FieldContent.X));
//        ticTacToe.doAction(new TicTacToeMove(1, 2, FieldContent.O));
//        ticTacToe.doAction(new TicTacToeMove(1, 1, FieldContent.X));
//
//        ticTacToe.doAction(new TicTacToeMove(2, 0, FieldContent.O));
//        ticTacToe.doAction(new TicTacToeMove(2, 1, FieldContent.X));
//        ticTacToe.doAction(new TicTacToeMove(2, 2, FieldContent.O));
//
//        boolean isDraw = ticTacToe.isDraw();
//
//        assertThat(isDraw).isTrue();
//    }
//
//    @Test
//    void isDraw_shouldReturnFalse_whenLineHasSameSign() {
//        ticTacToe.doAction(new TicTacToeMove(0, 0, FieldContent.O));
//        ticTacToe.doAction(new TicTacToeMove(1, 0, FieldContent.X));
//        ticTacToe.doAction(new TicTacToeMove(0, 1, FieldContent.O));
//        ticTacToe.doAction(new TicTacToeMove(1, 1, FieldContent.X));
//        ticTacToe.doAction(new TicTacToeMove(0, 2, FieldContent.O));
//
//        boolean isDraw = ticTacToe.isDraw();
//
//        assertThat(isDraw).isFalse();
//    }
//
//    @Test
//    void isDraw_shouldReturnFalse_whenThereIsEmptyField() {
//        ticTacToe.doAction(new TicTacToeMove(0, 0, FieldContent.O));
//        ticTacToe.doAction(new TicTacToeMove(0, 2, FieldContent.X));
//        ticTacToe.doAction(new TicTacToeMove(0, 1, FieldContent.O));
//
//        ticTacToe.doAction(new TicTacToeMove(1, 0, FieldContent.X));
//        ticTacToe.doAction(new TicTacToeMove(1, 2, FieldContent.O));
//        ticTacToe.doAction(new TicTacToeMove(1, 1, FieldContent.X));
//
//        ticTacToe.doAction(new TicTacToeMove(2, 0, FieldContent.O));
//        ticTacToe.doAction(new TicTacToeMove(2, 1, FieldContent.X));
//
//        boolean isDraw = ticTacToe.isDraw();
//
//        assertThat(isDraw).isFalse();
//    }
//
//    @Test
//    void isGameWon_shouldReturnFalse_whenAnyOfLinesAreEmpty() {
//        boolean isGameWon = ticTacToe.isGameWon();
//
//        assertThat(isGameWon).isFalse();
//    }
//
//    @Test
//    void isGameWon_shouldReturnTrue_whenWholeColumnHasSameSign() {
//        ticTacToe.doAction(new TicTacToeMove(0, 1, FieldContent.O));
//        ticTacToe.doAction(new TicTacToeMove(0, 0, FieldContent.X));
//        ticTacToe.doAction(new TicTacToeMove(1, 1, FieldContent.O));
//        ticTacToe.doAction(new TicTacToeMove(0, 2, FieldContent.X));
//        ticTacToe.doAction(new TicTacToeMove(2, 1, FieldContent.O));
//
//        boolean isGameWon = ticTacToe.isGameWon();
//
//        assertThat(isGameWon).isTrue();
//    }
//
//    @Test
//    void isGameWon_shouldReturnTrue_whenWholeRowHasSameSign() {
//        ticTacToe.doAction(new TicTacToeMove(0, 0, FieldContent.X));
//        ticTacToe.doAction(new TicTacToeMove(1, 0, FieldContent.O));
//        ticTacToe.doAction(new TicTacToeMove(0, 1, FieldContent.X));
//        ticTacToe.doAction(new TicTacToeMove(1, 1, FieldContent.O));
//        ticTacToe.doAction(new TicTacToeMove(0, 2, FieldContent.X));
//
//        boolean isGameWon = ticTacToe.isGameWon();
//
//        assertThat(isGameWon).isTrue();
//    }
//
//    @Test
//    void isGameWon_shouldReturnTrue_whenWholeSlashDiagonalHasSameSign() {
//        ticTacToe.doAction(new TicTacToeMove(0, 0, FieldContent.O));
//        ticTacToe.doAction(new TicTacToeMove(1, 0, FieldContent.X));
//        ticTacToe.doAction(new TicTacToeMove(1, 1, FieldContent.O));
//        ticTacToe.doAction(new TicTacToeMove(2, 1, FieldContent.X));
//        ticTacToe.doAction(new TicTacToeMove(2, 2, FieldContent.O));
//
//        boolean isGameWon = ticTacToe.isGameWon();
//
//        assertThat(isGameWon).isTrue();
//    }
//
//    @Test
//    void isGameWon_shouldReturnTrue_whenWholeBackslashDiagonalHasSameSign() {
//        ticTacToe.doAction(new TicTacToeMove(0, 2, FieldContent.X));
//        ticTacToe.doAction(new TicTacToeMove(0, 0, FieldContent.O));
//        ticTacToe.doAction(new TicTacToeMove(1, 1, FieldContent.X));
//        ticTacToe.doAction(new TicTacToeMove(0, 1, FieldContent.O));
//        ticTacToe.doAction(new TicTacToeMove(2, 0, FieldContent.X));
//
//        boolean isGameWon = ticTacToe.isGameWon();
//
//        assertThat(isGameWon).isTrue();
//    }
//
//    @Test
//    void getWinnerSign_shouldReturnWinnerSign_whenWholeLineHasSameSign() {
//        ticTacToe.doAction(new TicTacToeMove(0, 0, FieldContent.X));
//        ticTacToe.doAction(new TicTacToeMove(2, 0, FieldContent.O));
//        ticTacToe.doAction(new TicTacToeMove(0, 1, FieldContent.X));
//        ticTacToe.doAction(new TicTacToeMove(1, 1, FieldContent.O));
//        ticTacToe.doAction(new TicTacToeMove(0, 2, FieldContent.X));
//
//        FieldContent winnerSign = ticTacToe.getWinnerSign();
//
//        assertThat(winnerSign).isEqualTo(FieldContent.X);
//    }
//
//    @Test
//    void getWinnerSign_shouldReturnNull_whenNoLineHasSameSign() {
//        ticTacToe.doAction(new TicTacToeMove(0, 0, FieldContent.X));
//        ticTacToe.doAction(new TicTacToeMove(0, 2, FieldContent.O));
//        ticTacToe.doAction(new TicTacToeMove(0, 1, FieldContent.X));
//
//        FieldContent winnerSign = ticTacToe.getWinnerSign();
//
//        assertThat(winnerSign).isNull();
//    }
//
//    private List<Field> flattenBoard(FieldContent[][] board) {
//        return Stream.of(board)
//                .map(Arrays::asList)
//                .flatMap(List::stream)
//                .collect(Collectors.toList());
//    }
//
//
//}