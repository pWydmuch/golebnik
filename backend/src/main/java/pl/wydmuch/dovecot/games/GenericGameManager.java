package pl.wydmuch.dovecot.games;

import pl.wydmuch.dovecot.websocket.gameroom.game.api.GameManager;
import pl.wydmuch.dovecot.websocket.gameroom.game.api.GameState;
import pl.wydmuch.dovecot.websocket.gameroom.game.api.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public  class GenericGameManager implements GameManager {
    private List<AbstractPlayer> players;
    private static final int PLAYERS_NUMBER = 2;
    private GameEngine engine;
    private int startingPlayerIndex;
    private AbstractGameFactory abstractGameFactory;

    public GenericGameManager(String gameName) {
        abstractGameFactory = AbstractGameFactoryFactory.createGameFactory(gameName);
        players = new ArrayList<>();
        for (int i = 0; i < PLAYERS_NUMBER; i++) {
            players.add(null);
        }
    }

    @Override
    public void createNewGame() {
        if (allPlayersArePresent() && engine == null) {
            engine = abstractGameFactory.createGameEngine();
        }
    }

    @Override
    public void resetGame() {
        engine = abstractGameFactory.createGameEngine();
        changeStaringPlayerIndex();
    }

    @Override
    public void makeMove(Move move, String playerName) {
        if (engine.isGameEnded()) throw new RuntimeException("Game is finished");
        checkIfPlayerCanMakeMove(playerName);
        AbstractPlayer playerMakingMove = getPlayerWithName(playerName);
        playerMakingMove.makeMovePlayerSpecific(move);
        engine.makeMove(move);
    }

    @Override
    public GameState getGameState() {
        return engine != null ? engine.getState() : abstractGameFactory.createGameEngine().getState();
    }

    @Override
    public boolean isGameStarted() {
        return engine != null;
    }

    @Override
    public int getPlayersNumber(){
        return PLAYERS_NUMBER;
    }

    @Override
    public void addPlayer(String playerName, int playerNumber) {
        AbstractPlayer player = abstractGameFactory.createPlayer(playerName,playerNumber);
        players.set(playerNumber,player);
        createNewGame();
    }

    @Override
    public void removePlayer(String playerName) {
        for (int i = 0; i < players.size(); i++) {
            AbstractPlayer player = players.get(i);
            if (player != null && player.getPlayerName().equals(playerName)) {
                players.set(i, null);
            }
        }
    }

    private boolean allPlayersArePresent() {
        return players.stream().filter(Objects::nonNull).count()  == PLAYERS_NUMBER;
    }

    private void changeStaringPlayerIndex() {
        startingPlayerIndex = (startingPlayerIndex + 1) % PLAYERS_NUMBER;
    }

    private AbstractPlayer getPlayerWithName(String playerName) {
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
}
