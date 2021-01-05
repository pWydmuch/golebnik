package pl.wydmuch.dovecot.room;

import pl.wydmuch.dovecot.activity.ActivityManager;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;

public class Room {
    private String id;
    private ActivityManager activityManager;
    private List<RoomUser> roomUsers;
    private int neededPlayersNumber;
    private LocalDateTime creationTime;
    private String roomActivityId;

    public Room(ActivityManager activityManager) {
        this.activityManager = activityManager;
        roomActivityId = this.activityManager.getManagerId();
        roomUsers = new ArrayList<>();
        creationTime = LocalDateTime.now();
        this.neededPlayersNumber = activityManager.getParticipantsNumber();
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
//            activityManager.addActivityParticipant(playerName,playerNumber);
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

    public void removeRoomUser(String userName) {
        if(activityManager.isActivityStarted() && !activityManager.isActivityFinished()) throw new RuntimeException("You can't leave game while playing");

        activityManager.removeActivityParticipant(userName);
        for (int i = 0; i < roomUsers.size(); i++) {
            RoomUser player = roomUsers.get(i);
            if (player != null && player.getName().equals(userName)) {
                roomUsers.set(i, null);
            }
        }
        roomUsers.stream().filter(Objects::nonNull).forEach(r->r.setConfirmationDone(false));
    }

    public void removeRoomUser(RoomUser player) {
        roomUsers.remove(player);
    }

    public ActivityManager getActivityManager() {
        return activityManager;
    }

//    public void setGameManager(ActivityManager activityManager) {
//        this.activityManager = activityManager;
//    }

    public List<RoomUser> getRoomUsers() {
        return roomUsers;
    }

    public void setRoomUsers(List<RoomUser> roomUsers) {
        this.roomUsers = roomUsers;
    }

    private void startGame(){
        if (allRoomUserConfirmedParticipation()){
            roomUsers.forEach(u-> activityManager.addActivityParticipant(u.getName(),u.getNumber()));
            activityManager.createNewActivity();
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
        if ((playerNumber+1) > neededPlayersNumber) throw new RuntimeException("ActivityParticipant number is too high, max playerNumber is " + neededPlayersNumber);
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
            activityManager.removeActivityParticipant(u.getName());
        });
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }
}
