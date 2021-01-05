package pl.wydmuch.dovecot.activity;



public interface ActivityManager {
    String getManagerId();
    void createNewActivity();
    void resetActivity();
    boolean isActivityStarted();
    boolean isActivityFinished();
    int getParticipantsNumber();
    String getActivityState();
    void doAction(String action, String participantName);
    void addActivityParticipant(String participantName, int participantNumber);
    void removeActivityParticipant(String playerName);
}
