package pl.wydmuch.dovecot.games;

import pl.wydmuch.dovecot.websocket.gameroom.game.api.RoomActivityManager;
import pl.wydmuch.dovecot.websocket.gameroom.game.api.RoomActivityState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public  class GenericRoomActivityManager implements RoomActivityManager {
    private List<AbstractPlayer> players;
    private static final int PLAYERS_NUMBER = 2;
    private GameEngine engine;
    private int startingPlayerIndex;
    private AbstractGameFactory abstractGameFactory;

    public GenericRoomActivityManager(String gameName) {
        abstractGameFactory = AbstractGameFactoryFactory.createGameFactory(gameName);
        players = new ArrayList<>();
        for (int i = 0; i < PLAYERS_NUMBER; i++) {
            players.add(null);
        }
    }

    @Override
    public String getManagerId() {
        return abstractGameFactory.getGameName();
    }

    @Override
    public void createNewActivity() {
        if (allPlayersArePresent() && engine == null) {
            engine = abstractGameFactory.createGameEngine();
        }
    }

    @Override
    public void resetActivity() {
        engine = abstractGameFactory.createGameEngine();
        changeStaringPlayerIndex();
    }

    @Override
    public void doAction(String action, String participantName) {
        if (engine.isGameEnded()) throw new RuntimeException("Game is finished");
        checkIfPlayerCanMakeMove(participantName);
        AbstractPlayer playerMakingMove = getPlayerWithName(participantName);
        Move move = abstractGameFactory.parseGameMove(action);
        playerMakingMove.makeMovePlayerSpecific(move);
        engine.makeMove(move);
    }

    @Override
    public RoomActivityState getActivityState() {
        return engine != null ? engine.getState() : abstractGameFactory.createGameEngine().getState();
    }

    @Override
    public boolean isActivityStarted() {
        return engine != null;
    }

    @Override
    public int getParticipantsNumber(){
        return PLAYERS_NUMBER;
    }

    @Override
    public void addActivityParticipant(String participantName, int participantNumber) {
        AbstractPlayer player = abstractGameFactory.createPlayer(participantName, participantNumber);
        players.set(participantNumber,player);
        createNewActivity();
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
