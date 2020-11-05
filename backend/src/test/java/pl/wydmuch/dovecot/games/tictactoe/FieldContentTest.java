//package pl.wydmuch.dovecot.games.tictactoe;
//
//import org.junit.jupiter.api.Test;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//class FieldContentTest {
//
//    @Test
//    void allHaveSameSign_shouldReturnFalse_whenFieldIsEmpty() {
//        FieldContent fieldContent = FieldContent.EMPTY;
//        boolean result = fieldContent.allHaveSameSign(FieldContent.O, FieldContent.O);
//        assertThat(result).isFalse();
//    }
//
//    @Test
//    void allHaveSameSign_shouldReturnFalse_whenFieldsHaveDifferentSigns() {
//        FieldContent fieldContent = FieldContent.O;
//        boolean result = fieldContent.allHaveSameSign(FieldContent.O, FieldContent.X);
//        assertThat(result).isFalse();
//    }
//
//    @Test
//    void allHaveSameSign_shouldReturnFalse_whenAllFieldsHaveSameSigns() {
//        FieldContent fieldContent = FieldContent.O;
//        boolean result = fieldContent.allHaveSameSign(FieldContent.O, FieldContent.O);
//        assertThat(result).isTrue();
//    }
//}