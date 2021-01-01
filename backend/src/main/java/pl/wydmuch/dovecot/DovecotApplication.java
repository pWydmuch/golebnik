package pl.wydmuch.dovecot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
@EnableScheduling
public class DovecotApplication {

    public static void main(String[] args) {
        SpringApplication.run(DovecotApplication.class, args);
    }

}
