package pl.wydmuch.dovecot.websocket.gameroom.game.api;



public interface RoomActivityManager {
    String getManagerId();
    void createNewActivity();
    void resetActivity();
    boolean isActivityStarted();
    int getParticipantsNumber();
    //TODO moze lepiej zrobic tak jak z Movem, czyli zwracac string jako sparsowany obiekt
    RoomActivityState getActivityState();
    void doAction(String action, String participantName);
    void addActivityParticipant(String participantName, int participantNumber);
    void removeActivityParticipant(String playerName);
}
