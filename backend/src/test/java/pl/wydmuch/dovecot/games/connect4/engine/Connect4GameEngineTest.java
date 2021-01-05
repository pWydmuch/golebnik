package pl.wydmuch.dovecot.games.connect4.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.FILE;
import static org.junit.jupiter.api.Assertions.*;

class Connect4GameEngineTest {

    private Connect4GameEngine engine;

    @BeforeEach
    void setUp() {
        engine = new Connect4GameEngine();
    }



    @Test
    void isGameWon_shouldReturnTrue_when4TokensInRowHasSameSign() {
        engine.makeMove(new Connect4Move(3,FieldContent.RED));
        engine.makeMove(new Connect4Move(0,FieldContent.BLACK));
        engine.makeMove(new Connect4Move(4,FieldContent.RED));
        engine.makeMove(new Connect4Move(1,FieldContent.BLACK));
        engine.makeMove(new Connect4Move(5,FieldContent.RED));
        engine.makeMove(new Connect4Move(2,FieldContent.BLACK));
        engine.makeMove(new Connect4Move(6,FieldContent.RED));


        boolean isGameWon = engine.getState(0).getIsWinner();

        assertThat(isGameWon).isTrue();
    }

}