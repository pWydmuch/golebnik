package pl.wydmuch.dovecot.room;

import java.util.List;

public class RoomDto {
    private String roomId;
    private String activityManagerId;
    private List<RoomUser> roomUsers;

    public RoomDto(Room room) {
        roomId = room.getId();
        activityManagerId = room.getRoomActivityId();
        roomUsers = room.getRoomUsers();
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getActivityManagerId() {
        return activityManagerId;
    }

    public void setActivityManagerId(String activityManagerId) {
        this.activityManagerId = activityManagerId;
    }

    public List<RoomUser> getRoomUsers() {
        return roomUsers;
    }

    public void setRoomUsers(List<RoomUser> roomUsers) {
        this.roomUsers = roomUsers;
    }
}
