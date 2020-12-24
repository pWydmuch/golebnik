package pl.wydmuch.dovecot.games.connect4;


import pl.wydmuch.dovecot.games.AbstractPlayer;
import pl.wydmuch.dovecot.games.connect4.engine.Connect4Move;
import pl.wydmuch.dovecot.games.connect4.engine.FieldContent;
import pl.wydmuch.dovecot.games.Move;

public class Connect4Player extends AbstractPlayer {
    private FieldContent playerSign;

    public Connect4Player(String playerName, int playerNumber) {
        super(playerName, playerNumber);
        this.playerSign = getPlayerNumber() == 0 ? FieldContent.RED : FieldContent.BLACK;
    }

    @Override
    protected void makeMovePlayerSpecific(Move move) {
        Connect4Move ticTacToeMove = (Connect4Move) move;
        ticTacToeMove.setPlayerSign(playerSign);
    }
}
