package pl.wydmuch.dovecot.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.wydmuch.dovecot.activity.ActivityManagerFactory;
import pl.wydmuch.dovecot.games.GameManagerFactory;
import pl.wydmuch.dovecot.room.RoomService;

@Configuration
public class BeanFactory {

    @Bean
    public RoomService getRoomService(){
        return new RoomService(getRoomActivityManagerFactory());
    }

    @Bean
    public ActivityManagerFactory getRoomActivityManagerFactory(){
        return new GameManagerFactory();
    }

}
