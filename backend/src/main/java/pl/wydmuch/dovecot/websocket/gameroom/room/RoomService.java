package pl.wydmuch.dovecot.websocket.gameroom.room;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;
import pl.wydmuch.dovecot.games.GameManagerFactory;
import pl.wydmuch.dovecot.websocket.gameroom.game.api.GameManager;
import pl.wydmuch.dovecot.websocket.gameroom.game.api.GameState;
import pl.wydmuch.dovecot.websocket.gameroom.game.api.Move;

import java.util.*;

@Service
public class RoomService {

    private Map<String, Room> rooms = new HashMap<>();

    public Set<String> getRoomsIds() {
        return rooms.keySet();
    }

    public void makeMove(String roomId, Move move, SimpMessageHeaderAccessor headerAccessor) {
        Room room = rooms.get(roomId);
        GameManager gameManager = room.getGameManager();
        String playerSessionId = headerAccessor.getSessionId();
        if (!gameManager.isGameStarted()) throw new RuntimeException("Game is not started yet");
        if (!isRoomUserPlaying(playerSessionId, roomId)) throw new RuntimeException("You don't play");
        gameManager.makeMove(move, playerSessionId);
    }

    public GameState getGameState(String roomId){
        Room room = rooms.get(roomId);
        GameManager gameManager = room.getGameManager();
        return gameManager.getGameState();
    }

    public void createRoom(String gameName) {
        GameManager gameManager = GameManagerFactory.createGame(gameName) ;
        Room room = new Room(gameManager);
        room.setId(UUID.randomUUID().toString());
        rooms.put(room.getId(), room);
    }

    public void deleteRoom(String roomId) {
        rooms.remove(roomId);
    }

    public void addPlayer(String playerSessionId, String roomId, int playerNumber) {
        Room room = rooms.get(roomId);
        room.addRoomUser(playerSessionId, playerNumber);
    }

    public void removePlayer(String playerSessionId, String roomId) {
        Room room = rooms.get(roomId);
        room.removeRoomUser(playerSessionId);
    }

    public GameManager getGame(String roomId) {
        return rooms.get(roomId).getGameManager();
    }

    public GameState onSubscription(String roomId) {
        return rooms.get(roomId).getGameManager().getGameState();
    }

    public void resetGame(String roomId) {
        GameManager manager = rooms.get(roomId).getGameManager();
        manager.resetGame();

    }

    public List<RoomUser> getRoomUsers(String roomId) {
        return rooms.get(roomId).getRoomUsers();
    }

    private boolean isRoomUserPlaying(String playerSessionId, String roomId) {
        return rooms.get(roomId).getRoomUsers().stream()
                .map(RoomUser::getName)
                .anyMatch(s -> s.equals(playerSessionId));
    }
}
