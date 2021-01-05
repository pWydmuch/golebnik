package pl.wydmuch.dovecot.games.tictactoe.engine;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class FieldTest {

    //TODO mozliwe ze tu nadalyby sie testy sparametryzowane

    @Test
    void allHaveSameSign_shouldReturnFalse_whenFieldIsEmpty() {
        Field field1 = createFieldWithContent(FieldContent.EMPTY);
        Field field2 = createFieldWithContent(FieldContent.O);
        Field field3 = createFieldWithContent(FieldContent.O);

        boolean result = field1.allHaveSameSign(field2,field3);
        assertThat(result).isFalse();
    }

    @Test
    void allHaveSameSign_shouldReturnFalse_whenFieldsHaveDifferentSigns() {
        Field field1 = createFieldWithContent(FieldContent.O);
        Field field2 = createFieldWithContent(FieldContent.O);
        Field field3 = createFieldWithContent(FieldContent.X);

        boolean result = field1.allHaveSameSign(field2,field3);
        assertThat(result).isFalse();
    }

    @Test
    void allHaveSameSign_shouldReturnFalse_whenAllFieldsHaveSameSigns() {
        Field field1 = createFieldWithContent(FieldContent.O);
        Field field2 = createFieldWithContent(FieldContent.O);
        Field field3 = createFieldWithContent(FieldContent.O);

        boolean result = field1.allHaveSameSign(field2,field3);
        assertThat(result).isTrue();
    }

    private Field createFieldWithContent(FieldContent content){
        Field field = new Field();
        field.setFieldContent(content);
        return field;
    }
}