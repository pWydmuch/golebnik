package pl.wydmuch.dovecot.websocket.gameroom.room;

public class RoomDto {
    private String roomId;
    private String activityManagerId;

    public RoomDto(Room room) {
        roomId = room.getId();
        activityManagerId = room.getRoomActivityManager().getManagerId();
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
}
