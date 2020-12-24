package pl.wydmuch.dovecot.websocket.gameroom.room;


import pl.wydmuch.dovecot.websocket.gameroom.game.api.GameManager;

import java.util.*;

public class Room {
    private String id;
    private GameManager gameManager;
    private List<RoomUser> roomUsers;
    private int neededPlayersNumber;

    public Room(GameManager gameManager) {
        this.gameManager = gameManager;
        roomUsers = new ArrayList<>();
        this.neededPlayersNumber = gameManager.getPlayersNumber();
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
            gameManager.addPlayer(playerName,playerNumber);
            roomUsers.set(playerNumber, new RoomUser(playerName));
        }

    }

    public void removeRoomUser(String playerSessionId) {
        gameManager.removePlayer(playerSessionId);
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

    public GameManager getGameManager() {
        return gameManager;
    }

//    public void setGameManager(GameManager gameManager) {
//        this.gameManager = gameManager;
//    }

    public List<RoomUser> getRoomUsers() {
        return roomUsers;
    }

    public void setRoomUsers(List<RoomUser> roomUsers) {
        this.roomUsers = roomUsers;
    }

    private boolean playerCanJoinGame(String playerName, int playerNumber) {
        if ((playerNumber+1) > neededPlayersNumber) throw new RuntimeException("Player number is too high, max playerNumber is " + (neededPlayersNumber -1));
        return roomUsers.get(playerNumber) == null &&
                roomUsers.stream()
                        .filter(Objects::nonNull)
                        .map(RoomUser::getName)
                        .noneMatch(playerName::equals);
    }


}
