package pl.wydmuch.golebnik.games.tictactoe;

import java.util.Arrays;

enum FieldContent {
    O,X,EMPTY;

    boolean allHaveSameSign(FieldContent... otherFields){
        return this != EMPTY && Arrays.stream(otherFields).allMatch(fieldContent -> fieldContent == this);
    }
}
