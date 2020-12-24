package pl.wydmuch.dovecot.games.tictactoe;

import pl.wydmuch.dovecot.games.AbstractPlayer;
import pl.wydmuch.dovecot.games.tictactoe.engine.TicTacToeMove;
import pl.wydmuch.dovecot.websocket.gameroom.game.api.Move;
import pl.wydmuch.dovecot.games.tictactoe.engine.FieldContent;

public class TicTacToePlayer extends AbstractPlayer {
    private FieldContent playerSign;


    public TicTacToePlayer(String playerName, int playerNumber) {
        super(playerName,playerNumber);
        playerSign = getPlayerNumber() == 0 ? FieldContent.X : FieldContent.O;
    }

    @Override
    protected void makeMovePlayerSpecific(Move move) {
        TicTacToeMove ticTacToeMove = (TicTacToeMove) move;
        ticTacToeMove.setPlayerSign(playerSign);
    }
}
