package pl.wydmuch.dovecot.websocket.gameroom.room;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;
import pl.wydmuch.dovecot.games.GameManagerFactory;
import pl.wydmuch.dovecot.websocket.gameroom.game.api.RoomActivityManager;
import pl.wydmuch.dovecot.websocket.gameroom.game.api.RoomActivityState;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private Map<String, Room> rooms = new HashMap<>();

    public List<RoomDto> getRoomDtos() {
        return rooms.values()
                .stream()
                .map(RoomDto::new)
                .collect(Collectors.toList());
    }

    public void doAction(String roomId, String action, SimpMessageHeaderAccessor headerAccessor) {
        Room room = rooms.get(roomId);
        RoomActivityManager roomActivityManager = room.getRoomActivityManager();
        String playerSessionId = headerAccessor.getSessionId();
        if (!roomActivityManager.isActivityStarted()) throw new RuntimeException("Game is not started yet");
        if (!isRoomUserWithName(playerSessionId, roomId)) throw new RuntimeException("You don't play");
        roomActivityManager.doAction(action, playerSessionId);
    }

    public RoomActivityState getRoomActivityState(String roomId){
        Room room = rooms.get(roomId);
        RoomActivityManager roomActivityManager = room.getRoomActivityManager();
        return roomActivityManager.getActivityState();
    }

    public void createRoom(String gameName) {
        RoomActivityManager roomActivityManager = GameManagerFactory.createGame(gameName) ;
        Room room = new Room(roomActivityManager);
        room.setId(UUID.randomUUID().toString());
        rooms.put(room.getId(), room);
    }

    public void deleteRoom(String roomId) {
        rooms.remove(roomId);
    }

    public void addRoomUser(String playerSessionId, String roomId, int playerNumber) {
        Room room = rooms.get(roomId);
        room.addRoomUser(playerSessionId, playerNumber);
    }

    public void removeRoomUser(String playerSessionId, String roomId) {
        Room room = rooms.get(roomId);
        room.removeRoomUser(playerSessionId);
    }

    public RoomActivityManager getGame(String roomId) {
        return rooms.get(roomId).getRoomActivityManager();
    }

    public RoomActivityState onSubscription(String roomId) {
        return rooms.get(roomId).getRoomActivityManager().getActivityState();
    }

    public void resetRoomActivity(String roomId) {
        RoomActivityManager manager = rooms.get(roomId).getRoomActivityManager();
        manager.resetActivity();

    }

    public List<RoomUser> getRoomUsers(String roomId) {
        return rooms.get(roomId).getRoomUsers();
    }

    private boolean isRoomUserWithName(String roomUserName, String roomId) {
        return rooms.get(roomId).getRoomUsers().stream()
                .map(RoomUser::getName)
                .anyMatch(s -> s.equals(roomUserName));
    }
}
