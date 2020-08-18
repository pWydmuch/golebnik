package pl.wydmuch.golebnik;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private SimpMessagingTemplate simpMessagingTemplate;

    private Notification notifications = new Notification(0);

    public NotificationService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void getNotification(){
        // Increment Notification by one
        notifications.increment();

        // Push notifications to front-end
        simpMessagingTemplate.convertAndSend("/topic/notification", notifications);
    }
}
