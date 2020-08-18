package pl.wydmuch.golebnik;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {

    NotificationService notificationService;

    public ScheduleTask(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Scheduled(cron = "*/10 * * * * *")
    public void executeNotification(){
        notificationService.getNotification();
    }
}
