package pl.wydmuch.dovecot.games.tictactoe;

import pl.wydmuch.dovecot.games.GameManager;
import pl.wydmuch.dovecot.games.GameState;
import pl.wydmuch.dovecot.games.Move;
import pl.wydmuch.dovecot.games.tictactoe.engine.FieldContent;
import pl.wydmuch.dovecot.games.tictactoe.engine.TicTacToeGameEngine;
import pl.wydmuch.dovecot.games.tictactoe.engine.TicTacToeMove;

import java.util.ArrayList;
import java.util.List;


public class TicTacToeGameManager implements GameManager {
    private static final int PLAYERS_NUMBER = 2;
    private List<TicTacToePlayer> players;
    private TicTacToeGameEngine engine;
    private int startingPlayerIndex;

    public TicTacToeGameManager() {
        players = new ArrayList<>();
        for (int i = 0; i < PLAYERS_NUMBER; i++) {
            players.add(null);
        }
    }

    @Override
    public void createNewGame() {
        if (allPlayersArePresent() && engine == null) {
            engine = new TicTacToeGameEngine();
        }
    }

    @Override
    public void resetGame() {
        engine = new TicTacToeGameEngine();
        changeStaringPlayerIndex();
    }

    @Override
    public void makeMove(Move move, String playerName) {
        if (engine.isGameEnded()) throw new RuntimeException("Game is finished");
        checkIfPlayerCanMakeMove(playerName);
        TicTacToeMove ticTacToeMove = (TicTacToeMove) move;
        TicTacToePlayer playerMakingMove = getPlayerWithName(playerName);
        ticTacToeMove.setPlayerSign(playerMakingMove.getPlayerSign());
        engine.makeMove(ticTacToeMove);
    }

    @Override
    public GameState getGameState() {
        return engine != null ? engine.getState() : new TicTacToeGameEngine().getState();
    }

    @Override
    public void addPlayer(String playerName, int playerNumber) {
        TicTacToePlayer player = new TicTacToePlayer();
        player.setPlayerName(playerName);
        FieldContent playerSign = playerNumber == 0 ? FieldContent.X : FieldContent.O;
        player.setPlayerSign(playerSign);
        players.set(playerNumber,player);
        createNewGame();
    }

    @Override
    public void removePlayer(String playerName) {
        for (int i = 0; i < players.size(); i++) {
            TicTacToePlayer player = players.get(i);
            if (player != null && player.getPlayerName().equals(playerName)) {
                players.set(i, null);
            }
        }
    }

    public boolean isGameStarted(){
        return engine != null;
    }

    public int getPlayersNumber(){
        return PLAYERS_NUMBER;
    }

    private boolean allPlayersArePresent() {
        return players.size() == PLAYERS_NUMBER;
    }

    private TicTacToePlayer getPlayerWithName(String playerName) {
        return players.stream().filter(p -> p.getPlayerName().equals(playerName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("There's no such a player"));
    }

    private void checkIfPlayerCanMakeMove(String playerName) {
        if (!engine.firstMoveWasMade() && playerWantingMakeMoveIsNotPlayerWhoStarts(playerName)) {
            throw new RuntimeException("It's not your turn to start");
        }
    }

    private boolean playerWantingMakeMoveIsNotPlayerWhoStarts(String playerName) {
        return !players.get(startingPlayerIndex).getPlayerName().equals(playerName);
    }

    private void changeStaringPlayerIndex() {
        startingPlayerIndex = (startingPlayerIndex + 1) % PLAYERS_NUMBER;
    }

}
