package pl.wydmuch.dovecot.games.tictactoe.engine;

import java.util.Arrays;

class Field {
    private FieldContent fieldContent = FieldContent.EMPTY;
    private boolean isInWinningLine = false;

    boolean allHaveSameSign(Field... otherFields) {
        if (isWinningLine(otherFields)) markWinningLine(otherFields);
        return fieldContentIsNotEmpty() && isAllInLineSameSign(otherFields);
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

    private boolean isWinningLine(Field... otherFields) {
        boolean allInLineSameSign = isAllInLineSameSign(otherFields);
        return fieldContentIsNotEmpty() && allInLineSameSign;
    }

    private boolean fieldContentIsNotEmpty() {
        return fieldContent != FieldContent.EMPTY;
    }

    private boolean isAllInLineSameSign(Field[] otherFields) {
        return Arrays.stream(otherFields)
                .map(Field::getFieldContent)
                .allMatch(fieldContent -> fieldContent == this.fieldContent);
    }

    private void markWinningLine(Field... otherFields) {
        isInWinningLine = true;
        Arrays.stream(otherFields).forEach(f -> f.isInWinningLine = true);
    }


}
