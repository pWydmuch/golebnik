package pl.wydmuch.dovecot.room;

import org.mockito.Mockito;
import pl.wydmuch.dovecot.activity.ActivityManager;
import pl.wydmuch.dovecot.activity.ActivityManagerFactory;

import static org.junit.jupiter.api.Assertions.*;

class RoomServiceTest {




    ActivityManagerFactory getFactory(){
        ActivityManager manager = Mockito.mock(ActivityManager.class);
    }

}