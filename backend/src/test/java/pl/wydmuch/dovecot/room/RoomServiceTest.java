//package pl.wydmuch.dovecot.room;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import pl.wydmuch.dovecot.activity.ActivityManager;
//import pl.wydmuch.dovecot.activity.ActivityManagerFactory;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class RoomServiceTest implements ActivityManager {
//
//    @InjectMocks
//    RoomService roomService;
//
//    @BeforeEach
//    void setUp() {
//
//    }
//
//
//    private ActivityManagerFactory getFactory(){
//        ActivityManager manager = getActivityManager();
//        ActivityManagerFactory factory =mock(ActivityManagerFactory.class);
//        when(factory.createActivityManager(anyString())).thenReturn(this);
//        return factory;
//    }
//
////    private ActivityManager getActivityManager() {}
//
//    @Override
//    public String getManagerId() {
//        return "DUMMY";
//    }
//
//    @Override
//    public void createNewActivity() {
//
//    }
//
//    @Override
//    public void resetActivity() {
//
//    }
//
//    @Override
//    public boolean isActivityStarted() {
//        return false;
//    }
//
//    @Override
//    public boolean isActivityFinished() {
//        return false;
//    }
//
//    @Override
//    public int getParticipantsNumber() {
//        return 0;
//    }
//
//    @Override
//    public String getActivityState() {
//        return null;
//    }
//
//    @Override
//    public void doAction(String action, String participantName) {
//
//    }
//
//    @Override
//    public void addActivityParticipant(String participantName, int participantNumber) {
//
//    }
//
//    @Override
//    public void removeActivityParticipant(String playerName) {
//
//    }
//}