package pl.wydmuch.dovecot.websocket.gameroom.room;


import pl.wydmuch.dovecot.websocket.gameroom.game.api.RoomActivityManager;

import java.util.*;

public class Room {
    private String id;
    private RoomActivityManager roomActivityManager;
    private List<RoomUser> roomUsers;
    private int neededPlayersNumber;

    public Room(RoomActivityManager roomActivityManager) {
        this.roomActivityManager = roomActivityManager;
        roomUsers = new ArrayList<>();
        this.neededPlayersNumber = roomActivityManager.getParticipantsNumber();
        for (int i = 0; i < neededPlayersNumber; i++) {
            roomUsers.add(null);
        }
    }

//    public TicTacToePlayer getPlayer(String playerId) {
//        return roomUsers.stream().filter(p -> p.getSessionId().equals(playerId))
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("There's no such a player"));
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public void addRoomUser(String playerName, int playerNumber) {
        if (playerCanJoinGame(playerName, playerNumber)){
            roomActivityManager.addActivityParticipant(playerName,playerNumber);
            roomUsers.set(playerNumber, new RoomUser(playerName));
        }

    }

    public void removeRoomUser(String playerSessionId) {
        roomActivityManager.removeActivityParticipant(playerSessionId);
        for (int i = 0; i < roomUsers.size(); i++) {
            RoomUser player = roomUsers.get(i);
            if (player != null && player.getName().equals(playerSessionId)) {
                roomUsers.set(i, null);
            }
        }
    }

    public void removeRoomUser(RoomUser player) {
        roomUsers.remove(player);
    }

    public RoomActivityManager getRoomActivityManager() {
        return roomActivityManager;
    }

//    public void setGameManager(RoomActivityManager roomActivityManager) {
//        this.roomActivityManager = roomActivityManager;
//    }

    public List<RoomUser> getRoomUsers() {
        return roomUsers;
    }

    public void setRoomUsers(List<RoomUser> roomUsers) {
        this.roomUsers = roomUsers;
    }

    private boolean playerCanJoinGame(String playerName, int playerNumber) {
        if ((playerNumber+1) > neededPlayersNumber) throw new RuntimeException("RoomActivityParticipant number is too high, max playerNumber is " + (neededPlayersNumber -1));
        return roomUsers.get(playerNumber) == null &&
                roomUsers.stream()
                        .filter(Objects::nonNull)
                        .map(RoomUser::getName)
                        .noneMatch(playerName::equals);
    }


}
