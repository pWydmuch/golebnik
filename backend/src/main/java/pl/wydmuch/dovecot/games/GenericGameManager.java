package pl.wydmuch.dovecot.games;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.wydmuch.dovecot.activity.ActivityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public  class GenericGameManager implements ActivityManager {
    private List<AbstractPlayer> players;
    private static final int PLAYERS_NUMBER = 2;
    private GameEngine engine;
    private int startingPlayerNumber = 0;
    private GameFactory gameFactory;
    private int nextTurnPlayerNumber;
    private boolean firstGameWasStarted = false;

    public GenericGameManager(String gameName) {
        gameFactory = GameFactoryFactory.createGameFactory(gameName);
        nextTurnPlayerNumber = startingPlayerNumber;
        players = new ArrayList<>();
        for (int i = 0; i < PLAYERS_NUMBER; i++) {
            players.add(null);
        }
    }

    @Override
    public String getManagerId() {
        return gameFactory.getGameName();
    }

    @Override
    public void createNewActivity() {
        if (allPlayersArePresent()) {
            engine = gameFactory.createGameEngine();
            if (firstGameWasStarted) {
                changeStartingPlayerIndex();
            }
            firstGameWasStarted = true;
        }
    }

    @Override
    public void resetActivity() {
        engine = gameFactory.createGameEngine();
        changeStartingPlayerIndex();
    }

    @Override
    public void doAction(String action, String participantName) {
        if (engine.isGameEnded()) throw new RuntimeException("Game is finished");
        checkIfPlayerCanMakeMove(participantName);
        AbstractPlayer playerMakingMove = getPlayerWithName(participantName);
        Move move = gameFactory.parseGameMove(action);
        playerMakingMove.makeMovePlayerSpecific(move);
        engine.makeMove(move);
        if (!engine.isGameEnded())
            findNextTurnPlayerName(playerMakingMove.getPlayerNumber());
    }
    private void changeStartingPlayerIndex() {
        startingPlayerNumber = (startingPlayerNumber + 1) % PLAYERS_NUMBER;
        nextTurnPlayerNumber = startingPlayerNumber;
    }
    private void findNextTurnPlayerName(int playerMakingMoveNumber) {
        nextTurnPlayerNumber = players.stream()
                .filter(Objects::nonNull)
                .map(AbstractPlayer::getPlayerNumber)
                .filter(n -> n != playerMakingMoveNumber)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("There's no other players"));
    }

    @Override
    public String getActivityState() {
        try {
            Object gameState = engine != null ? engine.getState(nextTurnPlayerNumber) : gameFactory.createGameEngine().getState(nextTurnPlayerNumber);
            return new ObjectMapper().writeValueAsString(gameState);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isActivityStarted() {
        return engine != null;
    }

    @Override
    public boolean isActivityFinished() {
      return this.engine.isGameEnded();
    }

    @Override
    public int getParticipantsNumber(){
        return PLAYERS_NUMBER;
    }

    @Override
    public void addActivityParticipant(String participantName, int participantNumber) {
        AbstractPlayer player = gameFactory.createPlayer(participantName, participantNumber);
        players.set(participantNumber,player);
    }

    @Override
    public void removeActivityParticipant(String playerName) {
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
        return !players.get(startingPlayerNumber).getPlayerName().equals(playerName);
    }
}
