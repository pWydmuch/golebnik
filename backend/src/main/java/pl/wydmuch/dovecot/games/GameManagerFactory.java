package pl.wydmuch.dovecot.games;

import pl.wydmuch.dovecot.activity.ActivityManager;
import pl.wydmuch.dovecot.activity.ActivityManagerFactory;


public class GameManagerFactory implements ActivityManagerFactory {
    public ActivityManager createActivityManager(String activityName) {
        if (!activityName.equals("TicTacToe") && !activityName.equals("Connect4"))
            throw new RuntimeException("There is no such a game as: " + activityName);
        return new GenericGameManager(activityName);
    }
}
