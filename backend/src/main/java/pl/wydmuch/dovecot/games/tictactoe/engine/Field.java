package pl.wydmuch.dovecot.games.tictactoe.engine;

import java.util.Arrays;

public class Field {
    private FieldContent fieldContent = FieldContent.EMPTY;
    private boolean isInWinningLine = false;

    boolean allHaveSameSign(Field... otherFields) {
        boolean allInLineSameSign = fieldContent != FieldContent.EMPTY && Arrays.stream(otherFields).map(Field::getFieldContent).allMatch(fieldContent -> fieldContent == this.fieldContent);
        if (allInLineSameSign) markWinningLine(otherFields);
        return allInLineSameSign;
    }

    public boolean isInWinningLine() {
        return isInWinningLine;
    }

    public void setInWinningLine(boolean inWinningLine) {
        isInWinningLine = inWinningLine;
    }

    public FieldContent getFieldContent() {
        return fieldContent;
    }

    public void setFieldContent(FieldContent fieldContent) {
        this.fieldContent = fieldContent;
    }

    private void markWinningLine(Field... otherFields) {
        isInWinningLine = true;
        Arrays.stream(otherFields).forEach(f -> f.isInWinningLine = true);
    }


}
