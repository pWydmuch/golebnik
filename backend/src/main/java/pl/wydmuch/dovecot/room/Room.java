package pl.wydmuch.dovecot.room;

import pl.wydmuch.dovecot.game.api.RoomActivityManager;

import java.util.*;
import java.util.function.Predicate;

public class Room {
    private String id;
    private RoomActivityManager roomActivityManager;
    private List<RoomUser> roomUsers;
    private int neededPlayersNumber;
    private String roomActivityId;

    public Room(RoomActivityManager roomActivityManager) {
        this.roomActivityManager = roomActivityManager;
        roomActivityId = this.roomActivityManager.getManagerId();
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


    public String getRoomActivityId() {
        return roomActivityId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addRoomUser(String playerName, int playerNumber) {
        if (playerCanJoinGame(playerName, playerNumber)){
//            roomActivityManager.addActivityParticipant(playerName,playerNumber);
            roomUsers.set(playerNumber, new RoomUser(playerName,playerNumber));
        }
        else {
            throw new RuntimeException("You can't sit here");
        }
    }

    public boolean allUsersSitting(){
        return isEnoughRoomUsersMeetingCondition(Objects::nonNull);
    }


    public void confirmRoomUser(String userName) {
        roomUsers.stream()
                .filter(u -> u.getName().equals(userName))
                .findFirst()
                .ifPresent(u -> u.setConfirmationDone(true));
        if (allRoomUserConfirmedParticipation()) startGame();
    }

    public void removeRoomUser(String playerSessionId) {
        if(roomActivityManager.isActivityStarted()) throw new RuntimeException("You can't leave game while playing");

        roomActivityManager.removeActivityParticipant(playerSessionId);
        for (int i = 0; i < roomUsers.size(); i++) {
            RoomUser player = roomUsers.get(i);
            if (player != null && player.getName().equals(playerSessionId)) {
                roomUsers.set(i, null);
            }
        }
        roomUsers.stream().filter(Objects::nonNull).forEach(r->r.setConfirmationDone(false));
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

    private void startGame(){
        if (allRoomUserConfirmedParticipation()){
            roomUsers.forEach(u->roomActivityManager.addActivityParticipant(u.getName(),u.getNumber()));
            roomActivityManager.createNewActivity();
        }
    }

    boolean allRoomUserConfirmedParticipation() {
        return isEnoughRoomUsersMeetingCondition(RoomUser::isConfirmationDone);

    }

    private boolean isEnoughRoomUsersMeetingCondition(Predicate<RoomUser> condition) {
        return roomUsers.stream()
                .filter(condition)
                .count() == neededPlayersNumber;
    }

    private boolean playerCanJoinGame(String playerName, int playerNumber) {
        if ((playerNumber+1) > neededPlayersNumber) throw new RuntimeException("RoomActivityParticipant number is too high, max playerNumber is " + neededPlayersNumber);
        return roomUsers.get(playerNumber) == null &&
                thereIsNoUserWithName(playerName);
    }

    private boolean thereIsNoUserWithName(String playerName) {
        return roomUsers.stream()
                .filter(Objects::nonNull)
                .map(RoomUser::getName)
                .noneMatch(playerName::equals);
    }

    public void cancelRoomUsersConfirmations() {
        roomUsers.stream().filter(Objects::nonNull).forEach(u->{
            u.setConfirmationDone(false);
            roomActivityManager.removeActivityParticipant(u.getName());
        });
    }
}
