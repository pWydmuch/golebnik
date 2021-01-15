package pl.wydmuch.dovecot.room;

import pl.wydmuch.dovecot.activity.ActivityManagerFactory;
import pl.wydmuch.dovecot.activity.ActivityManager;


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class RoomService {
//TODO rooms.get(roomId) moze zwroc null
    private Map<String, Room> rooms = new HashMap<>();

    private ActivityManagerFactory activityManagerFactory;

    public RoomService(ActivityManagerFactory activityManagerFactory) {
        this.activityManagerFactory = activityManagerFactory;
    }

    public List<RoomDto> getRoomDtos() {
        return rooms.values()
                .stream()
                .map(RoomDto::new)
                .collect(Collectors.toList());
    }

    public String getRoomActivityId(String roomId) {
        return rooms.get(roomId).getRoomActivityId();
    }

    public List<RoomDto> getRoomDtos(String roomActivityId) {
        return rooms.values()
                .stream()
                .filter(r->r.getRoomActivityId().equals(roomActivityId))
                .map(RoomDto::new)
                .collect(Collectors.toList());
    }

    public void doAction(String roomId, String action, String playerName) {
        Room room = rooms.get(roomId);
        ActivityManager activityManager = room.getActivityManager();
        if (!activityManager.isActivityStarted()) throw new RuntimeException("Game is not started yet");
        if (!isRoomUserWithName(playerName, roomId)) throw new RuntimeException("You don't play");
        activityManager.doAction(action, playerName);
        if (activityManager.isActivityFinished()) room.cancelRoomUsersConfirmations();
    }

    public String getRoomActivityState(String roomId){
        Room room = rooms.get(roomId);
        ActivityManager activityManager = room.getActivityManager();
        return activityManager.getActivityState();
    }

    public List<Room> getRoomsWithoutUsersOlderThanMinutes(long minutes){
        return rooms.values().stream()
                .filter(r-> r.getRoomUsers().stream().allMatch(Objects::isNull))
                .filter(r->r.getCreationTime().isBefore(LocalDateTime.now().minusMinutes(minutes)))
                .collect(Collectors.toList());
    }

    public void createRoom(String gameName) {
        ActivityManager activityManager = activityManagerFactory.createActivityManager(gameName);
        Room room = new Room(activityManager);
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

    public ActivityManager getGame(String roomId) {
        return rooms.get(roomId).getActivityManager();
    }

    public String onSubscription(String roomId) {
        return rooms.get(roomId).getActivityManager().getActivityState();
    }

    public void resetRoomActivity(String roomId) {
        ActivityManager manager = rooms.get(roomId).getActivityManager();
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

    public boolean confirmRoomUser(String playerSessionId, String roomId) {
        Room room = rooms.get(roomId);
        room.confirmRoomUser(playerSessionId);
        return room.getActivityManager().isActivityStarted();
    }


    public boolean isGameStarted(String roomId) {
        return rooms.get(roomId).allRoomUserConfirmedParticipation();
    }
}
