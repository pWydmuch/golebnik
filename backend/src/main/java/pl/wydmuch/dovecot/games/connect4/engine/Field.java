package pl.wydmuch.dovecot.games.connect4.engine;

public class Field {
    private FieldContent fieldContent = FieldContent.EMPTY;
    private boolean isInWinningLine = false;


    public FieldContent getFieldContent() {
        return fieldContent;
    }

    public void setFieldContent(FieldContent fieldContent) {
        this.fieldContent = fieldContent;
    }

    public boolean isInWinningLine() {
        return isInWinningLine;
    }

    public void setInWinningLine(boolean inWinningLine) {
        isInWinningLine = inWinningLine;
    }


}
